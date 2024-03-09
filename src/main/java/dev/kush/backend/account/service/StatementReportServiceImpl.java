package dev.kush.backend.account.service;

import dev.kush.backend.account.models.*;
import dev.kush.backend.account.repository.*;
import dev.kush.backend.exception.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;

import java.io.*;
import java.time.*;
import java.util.*;

@Service
public class StatementReportServiceImpl implements StatementReportService {
    private final TransactionRepository transactionRepository;

    private final AccountRepository accountRepository;


    public StatementReportServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }


    @Override
    public String generateReport(Long accountId) throws FileNotFoundException, JRException {

        Account account = accountRepository.findById(accountId).orElseThrow(
                () -> new UserNotFoundException("account with id " + accountId + " does not exist")
        );

        String fileName = account.getCustomer().getName() + "_" + account.getId() + "_" +
                LocalDateTime.now() + ".pdf";

        fileName = fileName.replace(" ", "");
        fileName = fileName.replace(":", "-");


        String path = "C:\\Users\\Admin\\Desktop\\studentResult\\"
                + fileName;

        List<Transaction> transactions = transactionRepository.
                findAllReferenceByAccountId(accountId).orElse(List.of());

        List<TransactionDtoForReport> transactionDtoForReports =
                transactions.stream()
                        .map(TransactionDtoForReport::new)
                        .toList();


        File file = ResourceUtils.getFile("classpath:report.jrxml");

        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(transactionDtoForReports);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Easy Bank");

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        JasperExportManager.exportReportToPdfFile(jasperPrint, path);

        return path;
    }
}
