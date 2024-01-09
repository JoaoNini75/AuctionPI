package com.joaonini75.auctionpi;

import com.joaonini75.auctionpi.auctions.Auction;
import com.joaonini75.auctionpi.auctions.AuctionService;
import com.joaonini75.auctionpi.bids.Bid;
import com.joaonini75.auctionpi.bids.BidService;
import com.joaonini75.auctionpi.media.Blob;
import com.joaonini75.auctionpi.media.MediaRepository;
import com.joaonini75.auctionpi.questions.Question;
import com.joaonini75.auctionpi.questions.QuestionService;
import com.joaonini75.auctionpi.users.User;
import com.joaonini75.auctionpi.users.UserRepository;
import com.joaonini75.auctionpi.utils.EmailService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.joaonini75.auctionpi.bids.BidService.DATE_TIME_PATTERN;
import static java.time.Month.*;

@Configuration
public class AppConfig {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository users, MediaRepository media,
                                 AuctionService auctionService, BidService bidService,
                                 QuestionService  questionService) {
        return args -> {
            User john = new User("John Hell", "johnhell",
                    "john.hell@gmail.com", "password1", "photoId1",
                    LocalDate.of(2000, JANUARY, 5), 21);

            User alex = new User("Alex Bruv", "alexbruv",
                    "alex.bruv@gmail.com", "password2", "photoId2",
                    LocalDate.of(2004, JANUARY, 5), 20);

            User anton = new User("Antón Castillo", "antonCastillo",
                    "anton.castillo@gmail.com", "yara", null,
                    LocalDate.of(1945, MARCH, 29), 50);

            users.saveAll(List.of(john, alex, anton));

            Blob blob1 = new Blob("1", "1".getBytes(StandardCharsets.UTF_8));
            Blob blob2 = new Blob("2", "2".getBytes(StandardCharsets.UTF_8));
            media.write(blob1);
            media.write(blob2);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
            LocalDateTime date = LocalDateTime.now().plusDays(5L);
            String endDate = date.format(formatter);
            String deleteBidsLimitTime = date.minusMinutes(1L).format(formatter);

            Auction auction1 = new Auction(1L, "Auction 1 title", "Auction 1 description",
                    null, 2.0f, "", endDate,
                    deleteBidsLimitTime, 0L, true);

            Auction auction2 = new Auction(2L, "Auction 2 title", "Auction 2 description",
                    null, 2.0f, "", endDate,
                    deleteBidsLimitTime, 0L, true);

            Auction auction3 = new Auction(3L, "Auction 3 title", "Auction 3 description",
                    null, 2.0f, "", endDate,
                    deleteBidsLimitTime, 0L, true);

            auctionService.createAuction(auction1);
            auctionService.createAuction(auction2);
            auctionService.createAuction(auction3);


            Bid bid1 = new Bid(1L, 2L, 2.1f, "");
            Bid bid2 = new Bid(1L, 3L, 2.3f, "");
            Bid bid3 = new Bid(2L, 1L, 2.4f, "");
            Bid bid4 = new Bid(2L, 3L, 2.7f, "");
            Bid bid5 = new Bid(3L, 1L, 2.8f, "");
            Bid bid6 = new Bid(3L, 2L, 3.8f, "");

            bidService.createBid(bid1);
            bidService.createBid(bid2);
            bidService.createBid(bid3);
            bidService.createBid(bid4);
            bidService.createBid(bid5);
            bidService.createBid(bid6);


            Question question1 = new Question(1L, 2L,
                    "What is the weight of this graphics card?",
                    null, null, null);

            Question question2 = new Question(2L, 3L,
                    "What is the diameter of this football?",
                    null, null, null);

            Question question3 = new Question(3L, 1L,
                    "How many horsepower does this car have?",
                    null, null, null);

            questionService.createQuestion(question1);
            questionService.createQuestion(question2);
            questionService.createQuestion(question3);

            Question answer1 = new Question(1L, null, null, null,
                    "It weighs around 500 grams.", null, null);

            Question answer2 = new Question(2L, null, null, null,
                    "The diameter is 25 centimeters.", null, null);

            Question answer3 = new Question(3L, null, null, null,
                    "I'm not sure. Last time I put it on a dyno test was 3 years ago and" +
                            " it reached 260HP.", null, null);

            questionService.answerQuestion(answer1);
            questionService.answerQuestion(answer2);
            questionService.answerQuestion(answer3);

            EmailService emailService = new EmailService();
            emailService.sendSimpleMail("nini7500@gmail.com",
                    "Hello from AuctionPI!", "AuctionPI", null);
        };
    }
}
