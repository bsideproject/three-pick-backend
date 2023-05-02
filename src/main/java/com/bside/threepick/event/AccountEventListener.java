package com.bside.threepick.event;

import com.bside.threepick.domain.account.event.EmailAuthRequestedEvent;
import com.bside.threepick.domain.account.event.TempPasswordRequestedEvent;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import javax.mail.internet.InternetAddress;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RequiredArgsConstructor
@Component
public class AccountEventListener {

  private final JavaMailSender javaMailSender;
  private final StringRedisTemplate stringRedisTemplate;
  private final TemplateEngine templateEngine;

  @Async
  @EventListener
  public void onAuthorizedEvent(EmailAuthRequestedEvent emailAuthRequestedEvent) {
    SendMailContext sendMailContext = SendMailContext.builder()
        .email(emailAuthRequestedEvent.getEmail())
        .authCode(makeUUID())
        .subject("Three Pick 회원가입 이메일 인증번호")
        .build();

    sendMail(sendMailContext, true);
  }

  @Async
  @EventListener
  public void onChangedTempPasswordEvent(TempPasswordRequestedEvent tempPasswordRequestedEvent) {
    SendMailContext sendMailContext = SendMailContext.builder()
        .email(tempPasswordRequestedEvent.getEmail())
        .authCode(tempPasswordRequestedEvent.getPassword())
        .subject("Three Pick 임시 비밀번호")
        .build();

    sendMail(sendMailContext, false);
  }

  private void sendMail(SendMailContext sendMailContext, boolean authorized) {
    String email = sendMailContext.getEmail();
    String authCode = sendMailContext.getAuthCode();
    String subject = sendMailContext.getSubject();
    Context context = new Context();

    javaMailSender.send(mimeMessage -> {
      if (authorized) {
        stringRedisTemplate.opsForValue()
            .set(email, authCode);
        stringRedisTemplate.expire(email, 10, TimeUnit.MINUTES);
      }
      MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
      message.setFrom(new InternetAddress("three-pick.contact@gmail.com", "no-reply"));
      message.setTo(email);
      message.setSubject(subject);
      context.setVariable("authCode", authCode);
      message.setText(templateEngine.process("mail/mail", context), true);
      message.addInline("image", new ClassPathResource("static/images/Logo.png"));
    });
  }

  private String makeUUID() {
    return UUID.randomUUID()
        .toString()
        .substring(0, 6)
        .toUpperCase();
  }

  @Data
  private static class SendMailContext {

    private String email;
    private String authCode;
    private String subject;

    @Builder
    public SendMailContext(String email, String authCode, String subject) {
      this.email = email;
      this.authCode = authCode;
      this.subject = subject;
    }
  }
}
