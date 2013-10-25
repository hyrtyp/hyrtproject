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
        mail.setFrom("422207901@qq.com");
        mail.setSubject("中经网-邮件-密码重置");
        mail.setText(content);
        sender.send(mail);
    }
    
}
