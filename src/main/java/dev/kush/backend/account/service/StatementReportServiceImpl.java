package dev.kush.backend.account.service;

import com.amazonaws.services.s3.*;
import com.amazonaws.services.s3.model.*;
import dev.kush.backend.account.models.*;
import dev.kush.backend.account.repository.*;
import dev.kush.backend.customer.model.*;
import dev.kush.backend.customer.service.*;
import dev.kush.backend.exception.*;
import dev.kush.backend.exception.FileNotFoundException;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;

import java.io.*;
import java.time.*;
import java.util.*;

@Service
public class StatementReportServiceImpl implements StatementReportService {
    private final TransactionRepository transactionRepository;

    private final AccountRepository accountRepository;
    private final AmazonS3 s3Client;
    private final EmailService emailService;

    @Value("${aws.s3.bucket}")
    private String bucket;


    public StatementReportServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository, AmazonS3 s3Client, EmailService emailService) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.s3Client = s3Client;
        this.emailService = emailService;
    }


    @Override
    public String generateReport(Long accountId) throws FileNotFoundException, JRException {

        Account account = accountRepository.findById(accountId).orElseThrow(
                () -> new UserNotFoundException("account with id " + accountId + " does not exist")
        );

        final Customer customer = account.getCustomer();
        String fileName = customer.getName() + "_" + account.getId() + "_" +
                LocalDateTime.now();

        fileName = fileName.replace(" ", "");
        fileName = fileName.replace(":", "-");
        fileName = "easybank/" + fileName.replace(".", "-") + ".pdf";


        List<Transaction> transactions = transactionRepository.
                findAllReferenceByAccountId(accountId).orElse(List.of());

        List<TransactionDtoForReport> transactionDtoForReports =
                transactions.stream()
                        .map(TransactionDtoForReport::new)
                        .toList();

        // metadata
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("application/pdf");
        metadata.addUserMetadata("x-amz-meta-title", "Report");
        try {
            File file = ResourceUtils.getFile("classpath:report.jrxml");

            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(transactionDtoForReports);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "Easy Bank");


            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            JasperExportManager.exportReportToPdfStream(jasperPrint, baos);

            final byte[] pdfBytes = baos.toByteArray();
            InputStream inputStream = new ByteArrayInputStream(pdfBytes);
            metadata.setContentLength(pdfBytes.length);
            String url = uploadToDigitalOcean(fileName, inputStream, metadata);
            return sendMail(url, customer.getEmail());
        } catch (JRException e) {
            throw new BadRequestException("Error in generating statement.");
        } catch (java.io.FileNotFoundException e) {
            throw new FileNotFoundException("File not found.");
        }
    }

    private String uploadToDigitalOcean(String fileName,InputStream inputStream,ObjectMetadata metaData) {

        s3Client.putObject(new PutObjectRequest(bucket,fileName,inputStream,metaData)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        return s3Client.getUrl(bucket,fileName).toString();
    }

    private String sendMail(String url,String email) {
        return emailService.sendMail(new EmailDetails(
                email,
                String.format(
                        "<p>Your statement is ready. Click the link to download it: <p><br />" +
                        "<a href=\""
                                + url + "\">Show Statement</a>"),
                "EasyBank Statement"
        ));
    }


}
