package com.joaonini75.auctionpi.auctions;

import com.joaonini75.auctionpi.users.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.joaonini75.auctionpi.bids.BidService.isDateFormatValid;
import static com.joaonini75.auctionpi.bids.BidService.nowLocalDateTimeToString;
import static com.joaonini75.auctionpi.users.UserService.userExists;
import static com.joaonini75.auctionpi.utils.ErrorMessages.*;

@Service
public class AuctionService {

    private final UserRepository users;
    private final AuctionRepository auctions;

    @Autowired
    public AuctionService(UserRepository users, AuctionRepository auctions) {
        this.users = users;
        this.auctions = auctions;
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

    @Transactional
    // parameters that can change: title, description, photoId
    // possible in the future? : endTime, deleteBidsLimitTime
    public Auction updateAuction(Auction auction) {
        Auction oldAuction = auctionExists(auctions, auction.getId());

        // TODO NOT WORKING WHY?
        //  if (!auction.getOpenBool())
        //      throw new IllegalStateException(CANNOT_UPDATE_CLOSED_AUCTION);

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


    public static Auction auctionExists(AuctionRepository auctions, Long id) {
        Optional<Auction> auctionOpt = auctions.findById(id);
        if (auctionOpt.isEmpty())
            throw new IllegalStateException(String.format(AUCTION_NOT_EXISTS, id));
        return auctionOpt.get();
    }
}
