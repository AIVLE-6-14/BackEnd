package com.example.AISafety.domain.user.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * HTML 이메일 발송 메소드
     * @param to 수신자 이메일
     * @param subject 메일 제목
     * @param code 인증 코드
     */
    public void sendEmail(String to, String subject, String code) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            String htmlContent = "<div style='font-family: Arial, sans-serif; text-align: center; padding: 20px;'>" +
                    "<h2 style='color: #007bff;'>회원가입 인증 코드</h2>" +
                    "<p>아래 인증 코드를 입력하여 회원가입을 완료하세요.</p>" +
                    "<div style='font-size: 20px; font-weight: bold; padding: 10px; background: #f8f9fa; display: inline-block; border-radius: 5px;'>" +
                    code +
                    "</div>" +
                    "<p style='font-size: 12px; color: gray;'>이 코드는 10분 동안 유효합니다.</p>" +
                    "</div>";

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
