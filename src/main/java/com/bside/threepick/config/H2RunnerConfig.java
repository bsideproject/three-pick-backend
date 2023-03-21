package com.bside.threepick.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@RequiredArgsConstructor
@Component
public class H2RunnerConfig implements ApplicationRunner {
    private final DataSource dataSource;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            String URL = connection.getMetaData().getURL();
            String User = connection.getMetaData().getUserName();

            System.out.println("URL :: " + URL);
            System.out.println("User  :: " + User);
        }
    }
}
