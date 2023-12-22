package com.joaonini75.auctionpi;

import com.joaonini75.auctionpi.media.Blob;
import com.joaonini75.auctionpi.media.MediaRepository;
import com.joaonini75.auctionpi.users.User;
import com.joaonini75.auctionpi.users.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import static java.time.Month.JANUARY;

@Configuration
public class AppConfig {

    @Bean
    CommandLineRunner userConfig(UserRepository users, MediaRepository media) {
        return args -> {
            User john = new User("John Hell", "johnhell",
                    "john.hell@gmail.com", "password1", "photoId1",
                    LocalDate.of(2000, JANUARY, 5), 21);

            User alex = new User("Alex Bruv", "alexbruv",
                    "alex.bruv@gmail.com", "password2", "photoId2",
                    LocalDate.of(2004, JANUARY, 5), 20);

            users.saveAll(List.of(john, alex));

            Blob blob1 = new Blob("1", "1".getBytes(StandardCharsets.UTF_8));
            Blob blob2 = new Blob("2", "2".getBytes(StandardCharsets.UTF_8));
            media.write(blob1);
            media.write(blob2);
        };
    }
}
