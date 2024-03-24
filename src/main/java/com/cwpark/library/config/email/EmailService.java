package com.cwpark.library.config.email;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    private final JavaMailSender emailSender;

    public static final String ePw = createKey();

    private MimeMessage createMessage(String to)throws Exception{
        log.info("보내는 대상 : "+ to);
        log.info("인증 번호 : "+ePw);
        MimeMessage  message = emailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, to);//보내는 대상
        message.setSubject("퍼블릭도서관 이메일 인증");//제목

        StringBuilder sb = new StringBuilder();
        sb.append("<div style='margin:20px;'>");
        sb.append("<h1> 안녕하세요 퍼블릭도서관 입니다. </h1>");
        sb.append("<br>");
        sb.append("<p>아래 코드를 복사해 입력해주세요<p>");
        sb.append("<br>");
        sb.append("<p>감사합니다.<p>");
        sb.append("<br>");
        sb.append("<div align='center' style='border:1px solid black; font-family:verdana';>");
        sb.append("<h3 style='color:blue;'>이메일 인증 코드입니다.</h3>");
        sb.append("<div style='font-size:130%'>");
        sb.append("CODE : <strong>");
        sb.append(ePw+"</strong><div><br/> ");
        sb.append("</div>");

        message.setText(sb.toString(), "utf-8", "html");//내용
        message.setFrom(new InternetAddress("이메일","cksdn4002"));//보내는 사람

        return message;
    }

    private MimeMessage createPassword(String to) throws Exception {
        log.info("보내는 대상 : "+ to);
        log.info("비밀 번호 : "+ePw);
        MimeMessage  message = emailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, to);//보내는 대상
        message.setSubject("퍼블릭도서관 임시 비밀번호 발급");//제목

        StringBuilder sb = new StringBuilder();
        sb.append("<div style='margin:20px;'>");
        sb.append("<h1> 안녕하세요 퍼블릭도서관 입니다. </h1>");
        sb.append("<br>");
        sb.append("<p>아래 임시 비밀번호를 복사해 로그인 해주세요<p>");
        sb.append("<br>");
        sb.append("<p>감사합니다.<p>");
        sb.append("<br>");
        sb.append("<div align='center' style='border:1px solid black; font-family:verdana';>");
        sb.append("<h3 style='color:blue;'>임시 비밀번호 입니다.</h3>");
        sb.append("<div style='font-size:130%'>");
        sb.append("CODE : <strong>");
        sb.append(ePw+"</strong><div><br/> ");
        sb.append("</div>");

        message.setText(sb.toString(), "utf-8", "html");//내용
        message.setFrom(new InternetAddress("이메일","cksdn4002"));//보내는 사람

        return message;
    }

    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤

            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    //  a~z  (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    //  A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }
        return key.toString();
    }

    public String sendSimpleMessage(String to)throws Exception {
        // TODO Auto-generated method stub
        MimeMessage message = createMessage(to);
        try{//예외처리
            emailSender.send(message);
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return ePw;
    }

    public String sendSimplePassword(String to)throws Exception {
        // TODO Auto-generated method stub
        MimeMessage message = createPassword(to);
        try{//예외처리
            emailSender.send(message);
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return ePw;
    }
}