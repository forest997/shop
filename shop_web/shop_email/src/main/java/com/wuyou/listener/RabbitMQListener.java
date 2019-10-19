package com.wuyou.listener;

import com.wuyou.entity.Email;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * @author Forest
 * @Date 2019/10/18
 */
@Component
public class RabbitMQListener {
    @Value("${spring.mail.username}")
    private String from;
    @Autowired
    JavaMailSender javaMailSender;
    @RabbitListener(queues = "email_queue")
    public void sendEmail(Email email){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        try {
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(email.getTo());
            mimeMessageHelper.setSubject(email.getSubject());
            mimeMessageHelper.setText(email.getContent(), true);
            mimeMessageHelper.setSentDate(new Date());
            javaMailSender.send(mimeMessage);
            System.out.println("邮件发送成功");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
