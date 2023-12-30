package com.joaonini75.auctionpi.bids;

import com.joaonini75.auctionpi.auctions.Auction;
import com.joaonini75.auctionpi.auctions.AuctionRepository;
import com.joaonini75.auctionpi.users.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static com.joaonini75.auctionpi.auctions.AuctionService.auctionExists;
import static com.joaonini75.auctionpi.users.UserService.userExists;
import static com.joaonini75.auctionpi.utils.ErrorMessages.*;

@Service
public class BidService {

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd_HH:mm:ss";

    private final BidRepository bids;
    private final UserRepository users;
    private final AuctionRepository auctions;
    private TaskScheduler scheduler;

    @Autowired
    public BidService(BidRepository bids, UserRepository users, AuctionRepository auctions) {
        this.bids = bids;
        this.users = users;
        this.auctions = auctions;
    }

    @Async
    public void scheduleCloseAuction(Auction auction, String dateStr) {
        ScheduledExecutorService localExecutor =
                Executors.newSingleThreadScheduledExecutor();
        scheduler = new ConcurrentTaskScheduler(localExecutor);

        scheduler.schedule(closeAuctionRunnable(auction), stringToInstant(dateStr));
    }

    @Scheduled(cron = "0 */10 * * * *") // 10 minutes interval
    void ensuringAuctionsAreClosed() {
        List<Auction> list = auctions.findAll();
        String now = nowLocalDateTimeToString();
        int cleanCounter = 0;

        for (Auction a : list) {
            if (a.getEndTime().compareTo(now) <= 0 && a.getOpenBool()) {
                a.setOpenBool(false);
                auctions.save(a);
                cleanCounter++;
            }
        }

        System.out.println("ensuringAuctionsAreClosed done: " +  cleanCounter);
    }

    public Bid getBid(Long id) {
        return bidExists(id);
    }

    @Transactional
    public Bid createBid(Bid bid) {
        userExists(users, bid.getUserId());
        Auction auction = auctionExists(auctions, bid.getAuctionId());

        shouldAuctionBeClosed(auctions, auction, CANNOT_BID_CLOSED_AUCTION);

        Long auctionWinnerBidId = auction.getWinnerBidId();

        if (auctionWinnerBidId != null) {
            Optional<Bid> winnerBidOpt = bids.findById(auctionWinnerBidId);
            if (winnerBidOpt.isPresent() && bid.getValue() <= winnerBidOpt.get().getValue())
                throw new IllegalStateException(String.format(LOWER_THAN_WINNING_BID,
                        winnerBidOpt.get().getValue()));
        }

        if (bid.getValue() < auction.getMinPrice())
            throw new IllegalStateException(String.format(LOWER_THAN_AUCTION_MIN,
                    auction.getMinPrice()));

        if (bid.getUserId().equals(auction.getUserId()))
            throw new IllegalStateException(SAME_USER_BIDDING);

        if (auctionWinnerBidId == null)
            scheduleCloseAuction(auction, auction.getEndTime());

        bid.setCreationTime(nowLocalDateTimeToString());

        Bid finalBid = bids.save(bid);
        auction.setWinnerBidId(finalBid.getId());

        return finalBid;
    }

    @Transactional
    public Bid deleteBid(Long id) {
        Bid bid = bidExists(id);
        Auction auction = auctionExists(auctions, bid.getAuctionId());

        String limitTime = auction.getDeleteBidsLimitTime();
        String now = nowLocalDateTimeToString();

        if (now.compareTo(limitTime) > 0)
            throw new IllegalStateException(String.format(TIME_TO_DELETE_BID_EXCEEDED,
                    limitTime));

        if (auction.getWinnerBidId().equals(id))
            auction.setWinnerBidId(null);

        // don't actually delete, so a user is able to recall its bids
        // bids.delete(bid);
        return bid;
    }



    private Bid bidExists(Long id) {
        Optional<Bid> bidOpt = bids.findById(id);
        if (bidOpt.isEmpty())
            throw new IllegalStateException(String.format(BID_NOT_EXISTS, id));
        return bidOpt.get();
    }

    public static String nowLocalDateTimeToString() {
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        return ldt.format(formatter);
    }

    public static boolean isDateFormatValid(String date) {
        try {
            DateTimeFormatter.ofPattern(DATE_TIME_PATTERN).parse(date);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static void shouldAuctionBeClosed(AuctionRepository auctions, Auction auction,
                                             String errorMessage) {
        if (auction.getEndTime().compareTo(nowLocalDateTimeToString()) < 0) {
            if (auction.getOpenBool()) {
                auction.setOpenBool(false);
                auctions.save(auction);
            }

            throw new IllegalStateException(errorMessage);
        }
    }

    private Runnable closeAuctionRunnable(Auction auction) {
        return () -> {
            auction.setOpenBool(false);
            auctions.save(auction);
        };
    }

    private Instant stringToInstant(String dateStr) {
        Date date;
        try {
            date = new SimpleDateFormat(DATE_TIME_PATTERN).parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return date.toInstant();
    }

}
