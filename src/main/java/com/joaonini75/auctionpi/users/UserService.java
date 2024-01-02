package com.joaonini75.auctionpi.users;

import com.joaonini75.auctionpi.auctions.Auction;
import com.joaonini75.auctionpi.auctions.AuctionRepository;
import com.joaonini75.auctionpi.bids.Bid;
import com.joaonini75.auctionpi.bids.BidRepository;
import com.joaonini75.auctionpi.utils.Hash;
import static com.joaonini75.auctionpi.utils.ErrorMessages.*;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository users;
    private final BidRepository bids;
    private final AuctionRepository auctions;

    @Autowired
    public UserService(UserRepository users, BidRepository bids, AuctionRepository auctions) {
        this.users = users;
        this.bids = bids;
        this.auctions = auctions;
    }

    public List<User> listUsers() {
        return users.findAll();
    }

    public User getUser(Long id) {
        return userExists(users, id);
    }

    @Transactional
    public User createUser(User user) {
        if (!isEmailValid(user.getEmail()))
            throw new IllegalStateException(INVALID_EMAIL);

        if (!isPasswordValid(user.getPassword()))
            throw new IllegalStateException(INVALID_PASSWORD);

        user.setPassword(Hash.of(user.getPassword()));

        return users.save(user);
    }

    private boolean isPasswordValid(String password) {
        return password != null && !password.trim().equals("") && password.length() >= 8;
    }

    @Transactional
    public User updateUser(User newUser) {
        User oldUser = userExists(users, newUser.getId());
        User finalUser = processUpdates(oldUser, newUser.getName(), newUser.getEmail());
        return users.save(finalUser);
    }

    @Transactional
    public User deleteUser(Long id) {
        User user = userExists(users, id);
        users.delete(user);
        return user;
    }

    public List<Bid> listUserBids(Long id) {
        userExists(users, id);
        Optional<List<Bid>> userBids = bids.listUserBids(id);
        return userBids.orElse(null);
    }

    public List<Auction> listUserAuctions(Long id) {
        userExists(users, id);
        Optional<List<Auction>> userAuctions = auctions.listUserAuctions(id);
        return userAuctions.orElse(null);
    }

    public List<Auction> listAuctionsWithUserBids(Long id) {
        userExists(users, id);
        Optional<List<Auction>> auctionsWithUserBids = bids.listAuctionsWithUserBids(id);
        return auctionsWithUserBids.orElse(null);
    }



    public static User userExists(UserRepository users, Long id) {
        Optional<User> userOpt = users.findById(id);
        if (userOpt.isEmpty())
            throw new IllegalStateException(String.format(USER_NOT_EXISTS, id));
        return userOpt.get();
    }

    private boolean isEmailValid(String email) {
        if (email == null || email.trim().equals("") || !email.contains("@"))
            return false;

        Optional<User> userOpt = users.findUserByEmail(email);
        return userOpt.isEmpty();
    }

    private User processUpdates(User oldUser, String newName, String newEmail) {
        if (oldUser.getEmail().equals(newEmail))
            throw new IllegalStateException(SAME_EMAIL);

        if (oldUser.getName().equals(newName))
            throw new IllegalStateException(SAME_NAME);

        if (!isEmailValid(newEmail))
            throw new IllegalStateException(INVALID_EMAIL);

        oldUser.setName(newName);
        oldUser.setEmail(newEmail);
        return oldUser;
    }
}
