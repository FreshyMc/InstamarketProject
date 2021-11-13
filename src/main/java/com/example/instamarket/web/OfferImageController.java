package com.example.instamarket.web;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Controller
public class OfferImageController {
    private static final String uploadDir = "D://projImages//";
    private static final String headingImagesPath = uploadDir + "offerImages//";
    private static final String optionImagesPath = uploadDir + "optionImages//";

    @GetMapping("/headingImage/{name}")
    public ResponseEntity showHeadingImage(@PathVariable String name) throws IOException {
        File f = new File(headingImagesPath + name);

        if(!f.exists()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        FileInputStream fileInputStream = new FileInputStream(f);

        InputStreamResource inputStreamResource = new InputStreamResource(fileInputStream);

        return new ResponseEntity<>(inputStreamResource, HttpStatus.OK);
    }

    @GetMapping("/optionImage/{name}")
    public ResponseEntity showOptionImage(@PathVariable String name) throws IOException {
        File f = new File(optionImagesPath + name);

        if(!f.exists()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        FileInputStream fileInputStream = new FileInputStream(f);

        InputStreamResource inputStreamResource = new InputStreamResource(fileInputStream);

        return new ResponseEntity<>(inputStreamResource, HttpStatus.OK);
    }
}
