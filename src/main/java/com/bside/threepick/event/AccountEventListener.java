package com.bside.threepick.event;

import com.bside.threepick.domain.account.event.EmailAuthRequestedEvent;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import javax.mail.internet.InternetAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AccountEventListener {

  private final JavaMailSender javaMailSender;
  private final StringRedisTemplate stringRedisTemplate;

  @Async
  @EventListener
  public void onAuthorizedEvent(EmailAuthRequestedEvent emailAuthRequestedEvent) {
    javaMailSender.send(mimeMessage -> {
      String authCode = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
      String email = emailAuthRequestedEvent.getEmail();
      stringRedisTemplate.opsForValue().set(email, authCode);
      stringRedisTemplate.expire(email, 10, TimeUnit.MINUTES);
      MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
      message.setFrom(new InternetAddress("noreply@house.com", "noreply@house.com"));
      message.setTo(email);
      message.setSubject("Three Pick 회원가입 이메일 인증번호");
      message.setText(authCode);
    });
  }
}
