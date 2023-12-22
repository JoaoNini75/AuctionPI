package com.joaonini75.auctionpi.media;

import com.joaonini75.auctionpi.utils.Hash;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.joaonini75.auctionpi.utils.ErrorMessages.*;

import java.util.List;

@Service
public class MediaService {

    private final MediaRepository blobs;

    @Autowired
    public MediaService(MediaRepository blobs) {
        this.blobs = blobs;
    }

    public List<String> listBlobsIds() {
        return blobs.listBlobsIds();
    }

    public byte[] download(String id) {
        byte[] bytes = blobs.read(id);

        if (bytes == null)
            throw new IllegalStateException(String.format(BLOB_NOT_EXISTS, id));

        return bytes;
    }

    @Transactional
    public String upload(byte[] blob) {
        Blob blobObj = new Blob(Hash.of(blob), blob);
        String res = blobs.write(blobObj);

        //if (!res.equals(blobObj.getId()))
            //throw new IllegalStateException(ERROR_UPLOADING_BLOB);

        return res;
    }

    public String deleteBlobs() {
        blobs.deleteBlobs();
        return "Sucess.";
    }
}
