package com.joaonini75.auctionpi.users;

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

    @Autowired
    public UserService(UserRepository users) {
        this.users = users;
    }

    public List<User> listUsers() {
        return users.findAll();
    }

    public User getUser(Long id) {
        return userExists(id);
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
        User oldUser = userExists(newUser.getId());

        User finalUser = processUpdates(oldUser,
                newUser.getName(), newUser.getEmail());

        users.save(finalUser);
        return finalUser;
    }

    @Transactional
    public User deleteUser(Long id) {
        User user = userExists(id);
        users.delete(user);
        return user;
    }


    private User userExists(Long id) {
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
