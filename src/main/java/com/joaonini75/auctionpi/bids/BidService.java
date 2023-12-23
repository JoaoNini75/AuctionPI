package com.joaonini75.auctionpi.bids;

import com.joaonini75.auctionpi.auctions.Auction;
import com.joaonini75.auctionpi.auctions.AuctionRepository;
import com.joaonini75.auctionpi.users.User;
import com.joaonini75.auctionpi.users.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static com.joaonini75.auctionpi.utils.ErrorMessages.*;

@Service
public class BidService {

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd_HH:mm:ss";
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
        System.out.println(userExists(bid.getUserId()));
        Auction auction = auctionExists(bid.getAuctionId());
        System.out.println(auction);

        if (bid.getValue() < auction.getMinPrice())
            throw new IllegalStateException(INVALID_VALUE);

        String creationTime = nowLocalDateTimeToString();
        bid.setCreationTime(creationTime);
        return bids.save(bid);
    }

    @Transactional
    public Bid deleteBid(Long id) {
        Bid bid = bidExists(id);
        Auction auction = auctionExists(bid.getAuctionId());

        String limitTime = auction.getDeleteBidsLimitTime();
        String now = nowLocalDateTimeToString();

        if (now.compareTo(limitTime) < 0)
            throw new IllegalStateException(String.format(TIME_TO_DELETE_BID_EXCEEDED, limitTime));

        bids.delete(bid);
        return bid;
    }


    private Bid bidExists(Long id) {
        Optional<Bid> bidOpt = bids.findById(id);
        if (bidOpt.isEmpty())
            throw new IllegalStateException(String.format(BID_NOT_EXISTS, id));
        return bidOpt.get();
    }

    private User userExists(Long id) {
        Optional<User> userOpt = users.findById(id);
        if (userOpt.isEmpty())
            throw new IllegalStateException(String.format(USER_NOT_EXISTS, id));
        return userOpt.get();
    }

    private Auction auctionExists(Long id) {
        Optional<Auction> auctionOpt = auctions.findById(id);
        if (auctionOpt.isEmpty())
            throw new IllegalStateException(String.format(USER_NOT_EXISTS, id));
        return auctionOpt.get();
    }

    private String nowLocalDateTimeToString() {
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        return ldt.format(formatter);
    }
}
