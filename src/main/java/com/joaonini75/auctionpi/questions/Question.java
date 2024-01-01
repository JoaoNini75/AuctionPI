package com.joaonini75.auctionpi.questions;

import jakarta.persistence.*;

@Entity
@Table
public class Question {

    @Id
    @SequenceGenerator(
            name = "question_sequence",
            sequenceName = "question_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "question_sequence"
    )
    private Long id;
    private Long auctionId, userAskedId;
    private String questionText, answerText, askedTime, answeredTime;

    public Question() {

    }

    public Question(Long auctionId, Long userAskedId, String questionText, String answerText, String askedTime, String answeredTime) {
        this.auctionId = auctionId;
        this.userAskedId = userAskedId;
        this.questionText = questionText;
        this.answerText = answerText;
        this.askedTime = askedTime;
        this.answeredTime = answeredTime;
    }

    public Question(Long id, Long auctionId, Long userAskedId, String questionText, String answerText, String askedTime, String answeredTime) {
        this.id = id;
        this.auctionId = auctionId;
        this.userAskedId = userAskedId;
        this.questionText = questionText;
        this.answerText = answerText;
        this.askedTime = askedTime;
        this.answeredTime = answeredTime;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", auctionId=" + auctionId +
                ", userAskedId=" + userAskedId +
                ", questionText='" + questionText + '\'' +
                ", answerText='" + answerText + '\'' +
                ", askedTime='" + askedTime + '\'' +
                ", answeredTime='" + answeredTime + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Long auctionId) {
        this.auctionId = auctionId;
    }

    public Long getUserAskedId() {
        return userAskedId;
    }

    public void setUserAskedId(Long userAskedId) {
        this.userAskedId = userAskedId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public String getAskedTime() {
        return askedTime;
    }

    public void setAskedTime(String askedTime) {
        this.askedTime = askedTime;
    }

    public String getAnsweredTime() {
        return answeredTime;
    }

    public void setAnsweredTime(String answeredTime) {
        this.answeredTime = answeredTime;
    }
}
