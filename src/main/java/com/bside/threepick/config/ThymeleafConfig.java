package com.bside.threepick.config;

import java.nio.charset.StandardCharsets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

@Configuration
public class ThymeleafConfig {

  @Bean
  public TemplateEngine htmlTemplateEngine() {
    TemplateEngine templateEngine = new SpringTemplateEngine();
    templateEngine.addTemplateResolver(springResourceTemplateResolver());

    return templateEngine;
  }

  @Bean
  public SpringResourceTemplateResolver springResourceTemplateResolver() {
    SpringResourceTemplateResolver springResourceTemplateResolver = new SpringResourceTemplateResolver();
    springResourceTemplateResolver.setOrder(1);
    springResourceTemplateResolver.setPrefix("classpath:templates/");
    springResourceTemplateResolver.setSuffix(".html");
    springResourceTemplateResolver.setTemplateMode(TemplateMode.HTML);
    springResourceTemplateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
    springResourceTemplateResolver.setCacheable(Boolean.FALSE);

    return springResourceTemplateResolver;
  }
}
