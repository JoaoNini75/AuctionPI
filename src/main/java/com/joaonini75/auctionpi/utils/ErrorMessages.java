package com.joaonini75.auctionpi.utils;

import static com.joaonini75.auctionpi.bids.BidService.DATE_TIME_PATTERN;

public class ErrorMessages {

    // UserService
    public static final String INVALID_EMAIL = "The email is taken or invalid.";
    public static final String INVALID_PASSWORD = "The password is invalid.";
    public static final String USER_NOT_EXISTS = "The user with the given id (%s) does not exist.";
    public static final String SAME_NAME = "The new name is the same as the existing one.";
    public static final String SAME_EMAIL = "The new email is the same as the existing one.";

    // MediaService
    public static final String BLOB_NOT_EXISTS = "The blob with the id %s does not exist.";
    public static final String ERROR_UPLOADING_BLOB = "An error happened while uploading the blob.";

    // AuctionService
    public static final String TITLE_CANNOT_BE_EMPTY = "The title of the auction can not be empty.";
    public static final String DESCRIPTION_CANNOT_BE_EMPTY = "The description of the auction can not be empty.";
    public static final String INVALID_AUCTION_END_TIME = "The auction close time is not valid.";
    public static final String INVALID_LIMIT_BID_TIME = "The auction time limit for deleting bids should" +
            " be after the current moment and before the auction close time.";
    public static final String INVALID_AUCTION_MIN_PRICE = "The auction minimum price can not be negative";
    public static final String NO_CHANGES_MADE = "No changes were made to the auction.";
    public static final String CANNOT_UPDATE_CLOSED_AUCTION = "It is not possible to update properties " +
            "regarding a auction that has already closed.";
    public static final String INVALID_DATES_FORMAT = "One or more dates do not respect the specified format "
            + "(" + DATE_TIME_PATTERN + ")";

    // BidService
    public static final String BID_NOT_EXISTS = "The bid with the id %s does not exist.";
    public static final String INVALID_VALUE = "The value of your bid must be higher than the auction minimum" +
            " price and higher than the winning bid (%f).";
    public static final String TIME_TO_DELETE_BID_EXCEEDED = "You can no longer delete bids on this " +
            "auction, as the owner defined that the limit was until %s";
    public static final String AUCTION_NOT_EXISTS = "The auction with the given id (%s) does not exist.";
}
