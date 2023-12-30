package com.joaonini75.auctionpi.auctions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {

    @Query("SELECT a FROM Auction a WHERE a.endTime > ?1 AND a.endTime < ?2")
    Optional<List<Auction>> listClosingAuctions(String lowerLimit, String upperLimit);

    @Query("SELECT a FROM Auction a WHERE a.userId = ?1")
    Optional<List<Auction>> listUserAuctions(Long id);

}
