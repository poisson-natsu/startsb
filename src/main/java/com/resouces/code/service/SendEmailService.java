package com.resouces.code.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

@Service
public class SendEmailService {


    @Autowired
    JavaMailSender jms;

    public String send(String sender, String receiver, String title, String text) {

        SimpleMailMessage mainMessage = new SimpleMailMessage();

        mainMessage.setFrom(sender);

        mainMessage.setTo(receiver);

        mainMessage.setSubject(title);

        mainMessage.setText(text);

        try {
            jms.send(mainMessage);
            return "success";
        }catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
            return "failure";
        }

    }

    public String sendHtml(String sender, String receiver, String subject, String content) throws MessagingException {
        MimeMessage message = jms.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(sender);
        helper.setTo(receiver);
        helper.setSubject(subject);
        helper.setText(content, true);
        jms.send(message);

        return "send Html success";
    }

    public String sendMultiMail(String sender, String receiver, String subject, String text, MultipartFile[] files) throws Exception {

        MimeMessage message = jms.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(sender);
        helper.setTo(receiver);
        helper.setSubject(subject);

        Multipart multipart = new MimeMultipart("mixed");//multipart/mixed
        for (MultipartFile file : files) {

            MimeBodyPart fileBody = new MimeBodyPart();
            String filename = file.getOriginalFilename();


            fileBody.setDataHandler(new DataHandler(new ByteArrayDataSource(file.getInputStream(),file.getContentType())));
            fileBody.setFileName(filename);

            multipart.addBodyPart(fileBody);
        }

        message.setContent(multipart);
        message.setText(text);

        jms.send(message);

        return "send multi success";
    }
































}
