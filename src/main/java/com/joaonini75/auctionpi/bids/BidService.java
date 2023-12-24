package com.joaonini75.auctionpi.bids;

import com.joaonini75.auctionpi.auctions.Auction;
import com.joaonini75.auctionpi.auctions.AuctionRepository;
import com.joaonini75.auctionpi.users.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import static com.joaonini75.auctionpi.auctions.AuctionService.auctionExists;
import static com.joaonini75.auctionpi.users.UserService.userExists;
import static com.joaonini75.auctionpi.utils.ErrorMessages.*;

@Service
public class BidService {

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd_HH:mm:ss";

    private final BidRepository bids;
    private final UserRepository users;
    private final AuctionRepository auctions;

    @Autowired
    public BidService(BidRepository bids, UserRepository users, AuctionRepository auctions) {
        this.bids = bids;
        this.users = users;
        this.auctions = auctions;
    }

    public Bid getBid(Long id) {
        return bidExists(id);
    }

    @Transactional
    public Bid createBid(Bid bid) {
        userExists(users, bid.getUserId());
        Auction auction = auctionExists(auctions, bid.getAuctionId());
        Optional<Bid> winnerBidOpt = bids.findById(auction.getWinnerBidId());

        if (bid.getValue() < auction.getMinPrice() || (winnerBidOpt.isPresent() &&
                bid.getValue() < winnerBidOpt.get().getValue()))
            throw new IllegalStateException(String.format(INVALID_VALUE,
                    winnerBidOpt.get().getValue()));

        if (auction.getWinnerBidId() == null)
            scheduleCloseAuction(auction.getId(), auction.getEndTime());

        bid.setCreationTime(nowLocalDateTimeToString());
        auction.setWinnerBidId(bid.getId());

        return bids.save(bid);
    }

    @Transactional
    public Bid deleteBid(Long id) {
        Bid bid = bidExists(id);
        Auction auction = auctionExists(auctions, bid.getAuctionId());

        String limitTime = auction.getDeleteBidsLimitTime();
        String now = nowLocalDateTimeToString();

        if (now.compareTo(limitTime) < 0)
            throw new IllegalStateException(String.format(TIME_TO_DELETE_BID_EXCEEDED, limitTime));

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

    private Instant stringToInstant(String time) {
        return null; // TODO
    }

    public static boolean isDateFormatValid(String date) {
        try {
            DateTimeFormatter.ofPattern(DATE_TIME_PATTERN).parse(date);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private void scheduleCloseAuction(Long id, String time) {
        TaskScheduler scheduler = new ConcurrentTaskScheduler();
        Instant instant = stringToInstant(time);
        scheduler.schedule(new RunnableScheduler(id), instant);
    }

    class RunnableScheduler implements Runnable {
        private final Long id;

        private RunnableScheduler(Long id) {
            this.id = id;
        }

        @Override
        public void run() {
            Auction auction = auctionExists(auctions, this.id);
            auctions.delete(auction);
        }
    }
}
