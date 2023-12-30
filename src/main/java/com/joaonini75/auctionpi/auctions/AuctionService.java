package com.joaonini75.auctionpi.auctions;

import com.joaonini75.auctionpi.bids.Bid;
import com.joaonini75.auctionpi.bids.BidRepository;
import com.joaonini75.auctionpi.users.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static com.joaonini75.auctionpi.bids.BidService.*;
import static com.joaonini75.auctionpi.users.UserService.userExists;
import static com.joaonini75.auctionpi.utils.ErrorMessages.*;

@Service
public class AuctionService {

    private final UserRepository users;
    private final AuctionRepository auctions;
    private final BidRepository bids;

    @Autowired
    public AuctionService(UserRepository users, AuctionRepository auctions, BidRepository bids) {
        this.users = users;
        this.auctions = auctions;
        this.bids = bids;
    }

    public Auction getAuction(Long id) {
        return auctionExists(auctions, id);
    }

    @Transactional
    public Auction createAuction(Auction auction) {
        userExists(users, auction.getUserId());

        String title = auction.getTitle();
        if (title == null || title.trim().equals(""))
            throw new IllegalStateException(TITLE_CANNOT_BE_EMPTY);

        String description = auction.getDescription();
        if (description == null || description.trim().equals(""))
            throw new IllegalStateException(DESCRIPTION_CANNOT_BE_EMPTY);

        String now = nowLocalDateTimeToString();
        String limitBidsTime = auction.getDeleteBidsLimitTime();
        String endTime = auction.getEndTime();

        if (!isDateFormatValid(limitBidsTime) || !isDateFormatValid(endTime))
            throw new IllegalStateException(INVALID_DATES_FORMAT);

        if (endTime.compareTo(now) < 0)
            throw new IllegalStateException(INVALID_AUCTION_END_TIME);

        if (limitBidsTime.compareTo(now) < 0 || limitBidsTime.compareTo(endTime) > 0)
            throw new IllegalStateException(INVALID_LIMIT_BID_TIME);

        if (auction.getMinPrice() < 0.0f)
            throw new IllegalStateException(INVALID_AUCTION_MIN_PRICE);

        auction.setWinnerBidId(null);
        auction.setStartTime(now);
        auction.setOpenBool(true);

        return auctions.save(auction);
    }

    // parameters that can change: title, description, photoId
    // possible in the future? : endTime, deleteBidsLimitTime
    @Transactional
    public Auction updateAuction(Auction auction) {
        Auction oldAuction = auctionExists(auctions, auction.getId());

        shouldAuctionBeClosed(auctions, auction, CANNOT_UPDATE_CLOSED_AUCTION);

        String title = auction.getTitle();
        if (title == null || title.trim().equals(""))
            throw new IllegalStateException(TITLE_CANNOT_BE_EMPTY);

        String description = auction.getDescription();
        if (description == null || description.trim().equals(""))
            throw new IllegalStateException(DESCRIPTION_CANNOT_BE_EMPTY);

        if (title.equals(oldAuction.getTitle()) &&
                description.equals(auction.getDescription()))
            throw new IllegalStateException(NO_CHANGES_MADE);

        oldAuction.setTitle(title);
        oldAuction.setDescription(description);
        oldAuction.setPhotoId(auction.getPhotoId());

        return auctions.save(oldAuction);
    }

    public List<Bid> listAuctionBids(Long id) {
        auctionExists(auctions, id);
        Optional<List<Bid>> auctionBids = bids.listAuctionBids(id);
        return auctionBids.orElse(null);
    }

    // about to close = 1h
    public List<Auction> listClosingAuctions() {
        Optional<List<Auction>> closingAuctions = auctions.listClosingAuctions(
                nowLocalDateTimeToString(),
                limitDate());
        return closingAuctions.orElse(null);
    }



    public static Auction auctionExists(AuctionRepository auctions, Long id) {
        Optional<Auction> auctionOpt = auctions.findById(id);
        if (auctionOpt.isEmpty())
            throw new IllegalStateException(String.format(AUCTION_NOT_EXISTS, id));
        return auctionOpt.get();
    }

    private String limitDate() {
        LocalDateTime ldt = LocalDateTime.now().plusMinutes(60);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        return ldt.format(formatter);
    }
}
