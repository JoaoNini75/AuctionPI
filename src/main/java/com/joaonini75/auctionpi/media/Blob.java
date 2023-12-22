package com.joaonini75.auctionpi.media;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;

public class Blob {

    @Id
    private String id;
    @Transient
    private byte[] bytes;

    public Blob() {

    }

    public Blob(String blobId, byte[] blob) {
        this.id = blobId;
        this.bytes = blob;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
