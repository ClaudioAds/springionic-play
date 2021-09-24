package com.springionic.java.service;

import com.springionic.java.domain.Cliente;
import com.springionic.java.domain.Pedido;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

public interface EmailService {
    //========= Versão para texto plano
    void sendOrderConfirmationEmail(Pedido obj);

    void sendEmail(SimpleMailMessage obj);

    //=========== Versão para texto html
    void sendOrderConfirmationHtmlEmail(Pedido obj);

    void sendHtmlEmail(MimeMessage msg);

    void sendNewPasswordEmail(Cliente cliente, String newPass);
}

