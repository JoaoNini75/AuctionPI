package com.joaonini75.auctionpi.users;

import com.joaonini75.auctionpi.auctions.Auction;
import com.joaonini75.auctionpi.bids.Bid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "{id}")
    public User getUser(@PathVariable("id") Long id) {
        return userService.getUser(id);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @DeleteMapping(path = "{id}")
    public User deleteUser(@PathVariable("id") Long id) {
        return userService.deleteUser(id);
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @GetMapping
    public List<User> listUsers() {
        return userService.listUsers();
    }

    // List of bids of a given user
    @GetMapping(path = "{id}/bids")
    public List<Bid> listUserBids(@PathVariable("id") Long id) {
        return userService.listUserBids(id);
    }

    // List of auctions that a given user created
    @GetMapping(path = "{id}/auctions")
    public List<Auction> listUserAuctions(@PathVariable("id") Long id) {
        return userService.listUserAuctions(id);
    }

    // List of auctions that a given user bid on
    @GetMapping(path = "{id}/mybidsauctions")
    public List<Auction> listAuctionsWithUserBids(@PathVariable("id") Long id) {
        return userService.listAuctionsWithUserBids(id);
    }

}
