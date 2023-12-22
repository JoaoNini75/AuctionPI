package com.joaonini75.auctionpi.media;

import org.springframework.stereotype.Repository;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MediaRepository {

    private static final String STORAGE_DIR = System.getenv("AuctionPI_StorageDir");

    public MediaRepository() {

    }

    public String write(Blob blob) {
        try {
            Path path = Paths.get(STORAGE_DIR + blob.getId());
            Files.write(path, blob.getBytes());
            return blob.getId();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage() + " " + e;
        }
    }

    public byte[] read(String blobId) {
        try {
            Path path = Paths.get(STORAGE_DIR + blobId);
            return Files.readAllBytes(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> listBlobsIds() {
        List<String> ids = new ArrayList<>();
        File folder = new File(STORAGE_DIR);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++)
           ids.add(listOfFiles[i].getName());

        return ids;
    }

    public void deleteBlobs() {
        File folder = new File(STORAGE_DIR);
        for (File file: folder.listFiles())
            file.delete();
    }
}
