package com.springionic.java.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;

public class SmtpEmailService extends AbstractEmailService{

    @Autowired
    private MailSender mailSender;//responsável por pegar todos os dados de email do file aplication-dev.properties

    @Autowired
    private JavaMailSender javaMailSender;//JavaMailSender que é capaz de enviar o MimeMessage

    private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);
    @Override
    public void sendEmail(SimpleMailMessage msg) {
        LOG.info("Enviando Email....");
        mailSender.send(msg);
        LOG.info("Email enviado!");
    }

    @Override
    public void sendHtmlEmail(MimeMessage msg) {
        LOG.info("Enviando Email HTML....");
        javaMailSender.send(msg);
        LOG.info("Email enviado!");
    }
}
