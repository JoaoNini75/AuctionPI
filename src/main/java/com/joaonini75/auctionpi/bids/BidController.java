package com.joaonini75.auctionpi.bids;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/bid")
public class BidController {

    private final BidService bidService;

    @Autowired
    public BidController(BidService bidService) {
        this.bidService = bidService;
    }

    @GetMapping(path = "{id}")
    public Bid getBid(@PathVariable("id") Long id) {
        return bidService.getBid(id);
    }

    @PostMapping
    public Bid createBid(@RequestBody Bid bid) {
        return bidService.createBid(bid);
    }

    @DeleteMapping(path = "{id}")
    public Bid deleteBid(@PathVariable("id") Long id) {
        return bidService.deleteBid(id);
    }

}
