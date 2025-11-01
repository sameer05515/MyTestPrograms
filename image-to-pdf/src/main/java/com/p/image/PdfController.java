package com.p.image;

// import com.example.pdf.service.ImageToPdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@CrossOrigin(
    origins = "http://localhost:4200"
)
@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    private final ImageToPdfService imageToPdfService;

    public PdfController(ImageToPdfService imageToPdfService) {
        this.imageToPdfService = imageToPdfService;
    }

    @PostMapping("/generate")
    public ResponseEntity<byte[]> generatePdf(
            @RequestParam String folderPath
    ) throws IOException {

        Path pdfPath =
                imageToPdfService.generatePdf(folderPath);

        byte[] pdfBytes =
                Files.readAllBytes(pdfPath);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"images.pdf\""
                )
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(pdfBytes.length)
                .body(pdfBytes);
    }
}