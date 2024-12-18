package org.example.analyz.controller;

import org.example.analyz.service.CodeAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/analysis")
public class AnalysisController {

    @Autowired
    private CodeAnalysisService codeAnalysisService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String result = codeAnalysisService.analyzeCode(file);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error processing the file: " + e.getMessage());
        }
    }
}
