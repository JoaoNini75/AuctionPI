package com.joaonini75.auctionpi.utils;

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

    // BidService
    public static final String BID_NOT_EXISTS = "The bid with the id %s does not exist.";
    public static final String INVALID_VALUE = "The value of your bid is less than the minimum price" +
            " set by the auction owner.";
    public static final String TIME_TO_DELETE_BID_EXCEEDED = "You can no longer delete bids on this " +
            "auction, as the owner defined that the limit was until %s";
}
