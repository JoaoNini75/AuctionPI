package com.joaonini75.auctionpi;

import com.joaonini75.auctionpi.auctions.Auction;
import com.joaonini75.auctionpi.auctions.AuctionService;
import com.joaonini75.auctionpi.bids.Bid;
import com.joaonini75.auctionpi.bids.BidService;
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

import static java.time.Month.*;

@Configuration
public class AppConfig {

    @Bean
    CommandLineRunner userConfig(UserRepository users, MediaRepository media,
                                 AuctionService auctionService, BidService bidService) {
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


            String endDate = "2024-01-05_16:00:00";
            String deleteBidsLimitTime = "2024-01-05_15:59:00";

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
        };
    }
}
