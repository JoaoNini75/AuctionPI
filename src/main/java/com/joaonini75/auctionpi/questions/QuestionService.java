package com.joaonini75.auctionpi.questions;

import com.joaonini75.auctionpi.auctions.Auction;
import com.joaonini75.auctionpi.auctions.AuctionRepository;
import com.joaonini75.auctionpi.users.User;
import com.joaonini75.auctionpi.users.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.joaonini75.auctionpi.auctions.AuctionService.auctionExists;
import static com.joaonini75.auctionpi.bids.BidService.nowLocalDateTimeToString;
import static com.joaonini75.auctionpi.users.UserService.userExists;
import static com.joaonini75.auctionpi.utils.ErrorMessages.*;

@Service
public class QuestionService {

    private final QuestionRepository questions;
    private final AuctionRepository auctions;
    private final UserRepository users;

    @Autowired
    public QuestionService(QuestionRepository questions, AuctionRepository auctions, UserRepository users) {
        this.questions = questions;
        this.auctions = auctions;
        this.users = users;
    }

    public Question getQuestion(Long id) {
        return questionExists(questions, id);
    }

    @Transactional
    public Question createQuestion(Question question) {
        userExists(users, question.getUserAskedId());
        Auction auction = auctionExists(auctions, question.getAuctionId());

        if (auction.getUserId().equals(question.getUserAskedId()))
            throw new IllegalStateException(SAME_USER_ASKING_QUESTION);

        String questionText = question.getQuestionText();
        if (questionText == null || questionText.trim().equals(""))
            throw new IllegalStateException(QUESTION_CANNOT_BE_EMPTY);

        question.setAnswerText(null);
        question.setAnsweredTime(null);
        question.setAskedTime(nowLocalDateTimeToString());

        return questions.save(question);
    }

    @Transactional
    public Question answerQuestion(Question question) {
        return null; //TODO
    }

    public List<Question> listAuctionQuestions(Long id) {
        return null; //TODO
    }



    public static Question questionExists(QuestionRepository questions, Long id) {
        Optional<Question> questionOpt = questions.findById(id);
        if (questionOpt.isEmpty())
            throw new IllegalStateException(String.format(QUESTION_NOT_EXISTS, id));
        return questionOpt.get();
    }

}
