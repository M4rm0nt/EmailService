package com.m4rm0nt.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private RabattcodeRepository rabattcodeRepository;

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);


    @Async
    public void sendeRabattcodeEmail(String email) {
        try {
            Rabattcode rabattcode = new Rabattcode();
            rabattcode.setCode(generiereRabattcode());
            rabattcode.setEmail(email); // E-Mail-Adresse setzen
            rabattcodeRepository.save(rabattcode);

            SimpleMailMessage nachricht = new SimpleMailMessage();
            nachricht.setTo(email);
            nachricht.setSubject("Ihr Rabattcode");
            nachricht.setText("Ihr Rabattcode ist: " + rabattcode.getCode());
            emailSender.send(nachricht);
            logger.info("Rabattcode wurde an {} gesendet.", email);
        } catch (MailException e) {
            logger.error("Fehler beim Senden der E-Mail an {}", email, e);
        }
    }

    private String generiereRabattcode() {
        long count = rabattcodeRepository.count();
        return "RABATT" + (count + 1);
    }
}
