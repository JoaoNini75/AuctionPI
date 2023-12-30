package com.joaonini75.auctionpi.bids;

import com.joaonini75.auctionpi.auctions.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {

    @Query("SELECT b FROM Bid b WHERE b.auctionId = ?1")
    Optional<List<Bid>> listAuctionBids(Long auctionId);

    @Query("SELECT b FROM Bid b WHERE b.userId = ?1")
    Optional<List<Bid>> listUserBids(Long userId);

    @Query("WITH aid as (SELECT b.auction_id FROM Bid b WHERE b.user_id = ?1) " +
            "SELECT min_price, open_bool, id, user_id, winner_bid_id, " +
            "delete_bids_limit_time, description, end_time, photo_id, start_time, title " +
            "FROM Auction a INNER JOIN aid ON a.id = aid.auctionId")
    Optional<List<Auction>> listAuctionsWithUserBids(Long userId);

}
