package com.joaonini75.auctionpi.bids;

import jakarta.persistence.*;

@Entity
@Table
public class Bid {
    @Id
    @SequenceGenerator(
            name = "bid_sequence",
            sequenceName = "bid_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "bid_sequence"
    )
    private Long id;
    private Long auctionId, userId;
    private float value;
    private String creationTime;

    public Bid() {

    }

    public Bid(Long auctionId, Long userId, float value, String creationTime) {
        this.auctionId = auctionId;
        this.userId = userId;
        this.value = value;
        this.creationTime = creationTime;
    }

    public Bid(Long id, Long auctionId, Long userId, float value, String creationTime) {
        this.id = id;
        this.auctionId = auctionId;
        this.userId = userId;
        this.value = value;
        this.creationTime = creationTime;
    }

    @Override
    public String toString() {
        return "Bid{" +
                "id=" + id +
                ", auctionId=" + auctionId +
                ", userId=" + userId +
                ", value=" + value +
                ", creationTime=" + creationTime +
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }
}
