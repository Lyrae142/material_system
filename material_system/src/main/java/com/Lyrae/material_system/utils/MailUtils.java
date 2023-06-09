package com.Lyrae.material_system.utils;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtils {

    public static String sendMail(String msg,String email) throws Exception{
        //1、创建一个程序与邮件服务器会话对象Session
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol","SMTP");
        props.setProperty("mail.host","smtp.qq.com");
        props.setProperty("mail.smtp.auth" , "true");//指定验证为true

        //创建验证器
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication("779478273","cfnxhxzfbalbbdhg");
            }
        };

        Session session = Session.getInstance(props,auth);

        //2、创建一个Message,它相当于是邮件内容
        Message message = new MimeMessage(session);

        message.setFrom(new InternetAddress("779478273@qq.com")); //设置发送者

        message.setRecipient(Message.RecipientType.TO,new InternetAddress(email));//设置发送方式和接受者

        message.setSubject("库存预警");

        message.setContent(msg,"text/html;charset=utf-8");

        //3、创建Transport用于邮件发送

        Transport.send(message);

        return "200";
    }
}
