package com.joaonini75.auctionpi.media;

import com.joaonini75.auctionpi.users.User;
import com.joaonini75.auctionpi.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/media")
public class MediaController {

    private final MediaService mediaService;

    @Autowired
    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @GetMapping
    public List<String> listBlobsIds() {
        return mediaService.listBlobsIds();
    }

    @GetMapping(path = "{id}")
    public byte[] download(@PathVariable("id") String id) {
        return mediaService.download(id);
    }

    @PostMapping
    public String upload(@RequestBody byte[] blob) {
        return mediaService.upload(blob);
    }

    @DeleteMapping
    public String deleteAllBlobs() {
        return mediaService.deleteBlobs();
    }

}
