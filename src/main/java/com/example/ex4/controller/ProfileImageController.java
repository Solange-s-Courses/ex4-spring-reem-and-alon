package com.example.ex4.controller;

import com.example.ex4.service.ProviderProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for serving provider profile images as JPEG.
 *
 * @see ProviderProfileService
 */
@RestController
@RequestMapping("/provider-image")
public class ProfileImageController {

    /**
     * Service for provider profile operations.
     */
    @Autowired
    private ProviderProfileService providerProfileService;

    /**
     * Returns the provider profile image as a JPEG for the given provider ID.
     *
     * @param id the provider's ID
     * @return ResponseEntity with the image as a byte array if found, or 404 if not found
     */
    @GetMapping(value = "/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getProfileImage(@PathVariable Long id) {
        byte[] image = providerProfileService.findProfileImage(id);
        if (image == null || image.length == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(image);
    }
}
