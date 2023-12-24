package com.joaonini75.auctionpi.auctions;

import com.joaonini75.auctionpi.bids.Bid;
import com.joaonini75.auctionpi.bids.BidService;
import com.joaonini75.auctionpi.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/auction")
public class AuctionController {

    private final AuctionService auctionService;

    @Autowired
    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @GetMapping(path = "{id}")
    public Auction getAuction(@PathVariable("id") Long id) {
        return auctionService.getAuction(id);
    }

    @PostMapping
    public Auction createAuction(@RequestBody Auction auction) {
        return auctionService.createAuction(auction);
    }

    @PutMapping
    public Auction updateAuction(@RequestBody Auction auction) {
        return auctionService.updateAuction(auction);
    }

    /* TODO
    // List of bids of a given auction
    @GetMapping(path = "{id}/bids")
    public List<Bid> listAuctionBids(@PathVariable("id") Long id) {
        return auctionService.listAuctionBids(id);
    }

    // List of auctions about to close
    @GetMapping(path = "/closing")
    public List<Auction> listClosingAuctions() {
        return auctionService.listClosingAuctions();
    }*/

}
