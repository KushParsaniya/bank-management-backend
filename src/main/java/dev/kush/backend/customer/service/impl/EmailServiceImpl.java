package dev.kush.backend.customer.service.impl;

import dev.kush.backend.customer.model.EmailDetails;
import dev.kush.backend.customer.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Value(value = "${spring.mail.username}")
    private String sender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    @Override
    public String sendMail(EmailDetails emailDetails) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(sender);
            helper.setTo(emailDetails.recipient());
            helper.setSubject(emailDetails.subject());
            helper.setText(emailDetails.msgBody(),true);

            javaMailSender.send(message);
            return "Successfully sent email please check your mail box. ";

        } catch (Exception e){
            return "error sending email.";
      }
    }
}
