package com.joaonini75.auctionpi.auctions;

import jakarta.persistence.*;

@Entity
@Table
public class Auction {

    @Id
    @SequenceGenerator(
            name = "auction_sequence",
            sequenceName = "auction_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "auction_sequence"
    )
    private Long id;
    private Long userId, winnerBidId;
    private String title, description, photoId, startTime, endTime, deleteBidsLimitTime;
    private float minPrice;
    private AuctionStatus status;

    public Auction() {

    }

    public Auction(Long userId, String title, String description, String photoId,
                   float minPrice, String startTime, String endTime,
                   String deleteBidsLimitTime, Long winnerBidId, AuctionStatus status) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.photoId = photoId;
        this.minPrice = minPrice;
        this.startTime = startTime;
        this.endTime = endTime;
        this.deleteBidsLimitTime = deleteBidsLimitTime;
        this.winnerBidId = winnerBidId;
        this.status = status;
    }

    public Auction(Long id, Long userId, String title, String description,
                   String photoId, float minPrice, String startTime, String endTime,
                   String deleteBidsLimitTime, Long winnerBidId, AuctionStatus status) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.photoId = photoId;
        this.minPrice = minPrice;
        this.startTime = startTime;
        this.endTime = endTime;
        this.deleteBidsLimitTime = deleteBidsLimitTime;
        this.winnerBidId = winnerBidId;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Auction{" +
                "id=" + id +
                ", ownerId=" + userId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", photoId='" + photoId + '\'' +
                ", minPrice=" + minPrice +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", deleteBidsLimitTime=" + deleteBidsLimitTime +
                ", winnerBidId=" + winnerBidId +
                ", status=" + status +
                '}';
    }

    public String getDeleteBidsLimitTime() {
        return deleteBidsLimitTime;
    }

    public void setDeleteBidsLimitTime(String deleteBidsLimitTime) {
        this.deleteBidsLimitTime = deleteBidsLimitTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public float getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(float minPrice) {
        this.minPrice = minPrice;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Long getWinnerBidId() {
        return winnerBidId;
    }

    public void setWinnerBidId(Long winnerBidId) {
        this.winnerBidId = winnerBidId;
    }

    public AuctionStatus getStatus() {
        return status;
    }

    public void setStatus(AuctionStatus status) {
        this.status = status;
    }
}
