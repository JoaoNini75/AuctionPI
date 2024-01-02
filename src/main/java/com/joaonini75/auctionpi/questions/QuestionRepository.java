package com.joaonini75.auctionpi.questions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("SELECT q FROM Question q WHERE q.auctionId = ?1")
    Optional<List<Question>> listAuctionQuestions(Long auctionId);
}
