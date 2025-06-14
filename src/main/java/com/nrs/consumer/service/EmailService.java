package com.nrs.consumer.service;

import com.nrs.consumer.dto.NotificationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class.getName());

    @Value("${FROM_EMAIL}")
    private String fromEmail;

    @Value("${EMAIL_PASSWORD}")
    private String emailPassword;


    public void send(final NotificationDto notificationDto) {
        final var session = createSession();
        try {
            MimeMessage msg = new MimeMessage(session);

            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress("no_reply@example.com", "NoReply-JD"));

            msg.setReplyTo(InternetAddress.parse("no_reply@example.com", false));

            msg.setSubject("N.R.S email", "UTF-8");

            msg.setText(notificationDto.getMessage(), "UTF-8");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(notificationDto.getReceiver(), false));
            Transport.send(msg);
            log.info("E-mail Sent Successfully to {}!", notificationDto.getReceiver());
        } catch (Exception e) {
            log.error("error when send e-mail", e);
        }
    }

    private Session createSession() {
        validateEmailProps();

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        log.info("TLSEmail start");

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, emailPassword);
            }
        };

        return Session.getInstance(props, auth);
    }

    private void validateEmailProps() {
        var message = "";

        if (fromEmail.isBlank()) {
            throw new IllegalArgumentException(message);
        }

        if (emailPassword.isBlank()) {
            message = "Email password cannot be blank or empty.";
            throw new IllegalArgumentException(message);
        }
    }

}
