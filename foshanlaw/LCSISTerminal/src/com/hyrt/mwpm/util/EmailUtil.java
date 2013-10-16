package com.hyrt.mwpm.util;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/** *//**
 * spring的邮件发送例子
 * @author tanJie
 * 
 */
public class EmailUtil {
    
    /** *//**
     * 测试发送只有文本信息的简单测试
     * @param sender 邮件发送器
     * @throws Exception
     */
    public void sendTextMail(JavaMailSender sender,String to,String content) throws Exception {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(to);
        mail.setFrom("javamail_test2013@163.com");
        mail.setSubject("乐从巡检安监--法律法规条令");
        mail.setText(content);
        sender.send(mail);
    }
    
}
