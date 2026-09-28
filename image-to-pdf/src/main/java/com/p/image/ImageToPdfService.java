package com.p.image;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Service
public class ImageToPdfService {

    private static final Set<String> SUPPORTED_EXTENSIONS =
            Set.of("jpg", "jpeg", "png");

    public Path generatePdf(String folderPath) throws IOException {

        Path folder = Paths.get(folderPath);

        if (!Files.exists(folder)) {
            throw new IllegalArgumentException(
                    "Folder does not exist: " + folderPath
            );
        }

        if (!Files.isDirectory(folder)) {
            throw new IllegalArgumentException(
                    "Provided path is not a directory: " + folderPath
            );
        }

        List<Path> images;

        try (Stream<Path> stream = Files.list(folder)) {

            images = stream
                    .filter(Files::isRegularFile)
                    .filter(this::isSupportedImage)
                    .sorted(Comparator.comparing(
                            path -> path.getFileName()
                                    .toString()
                                    .toLowerCase()
                    ))
                    .toList();
        }

        if (images.isEmpty()) {
            throw new IllegalArgumentException(
                    "No supported images found in folder: " + folderPath
            );
        }

        Path outputPdf = folder.resolve("images.pdf");

        try (PDDocument document = new PDDocument()) {

            for (Path imagePath : images) {
                addImage(document, imagePath);
            }

            document.save(outputPdf.toFile());
        }

        return outputPdf;
    }

    private void addImage(
            PDDocument document,
            Path imagePath
    ) throws IOException {

        PDImageXObject image = PDImageXObject.createFromFile(
                imagePath.toAbsolutePath().toString(),
                document
        );

        float imageWidth = image.getWidth();
        float imageHeight = image.getHeight();

        /*
         * Page size = image size.
         *
         * This prevents unnecessary white borders and
         * keeps every image on its own PDF page.
         */
        PDRectangle pageSize =
                new PDRectangle(imageWidth, imageHeight);

        PDPage page = new PDPage(pageSize);

        document.addPage(page);

        try (PDPageContentStream contentStream =
                     new PDPageContentStream(document, page)) {

            contentStream.drawImage(
                    image,
                    0,
                    0,
                    imageWidth,
                    imageHeight
            );
        }
    }

    private boolean isSupportedImage(Path path) {

        String fileName =
                path.getFileName()
                        .toString()
                        .toLowerCase();

        int dotIndex = fileName.lastIndexOf('.');

        if (dotIndex == -1) {
            return false;
        }

        String extension =
                fileName.substring(dotIndex + 1);

        return SUPPORTED_EXTENSIONS.contains(extension);
    }
}