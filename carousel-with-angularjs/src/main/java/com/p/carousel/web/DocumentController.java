package com.p.carousel.web;

import com.p.file.search.FileSearcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Replaces legacy {@code my.jsp}: streams a single file or returns JSON for directory listings.
 */
@Controller
public class DocumentController {

    private static final Logger log = LoggerFactory.getLogger(DocumentController.class);

    /**
     * When set, only paths under this directory may be read (recommended for production).
     */
    @Value("${carousel.filesystem.root:}")
    private String filesystemRoot;

    @GetMapping("/my.jsp")
    public void document(
            @RequestParam(required = false) String documentId,
            @RequestParam(required = false) String[] extensions,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        if (!StringUtils.hasText(documentId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "documentId is required");
        }

        Path path = Paths.get(documentId).normalize();
        validateAllowedRoot(path);

        if (!Files.exists(path)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Path not found: " + documentId);
        }

        if (Files.isRegularFile(path)) {
            streamFile(path, response);
            return;
        }

        if (Files.isDirectory(path)) {
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            List<String> extensionList = toExtensionList(extensions);
            String baseUrl = requestBaseUrl(request);
            String json = new FileSearcher().startSearch(documentId, extensionList, baseUrl);
            response.getWriter().write(json);
            return;
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not a file or directory: " + documentId);
    }

    private void validateAllowedRoot(Path path) {
        if (!StringUtils.hasText(filesystemRoot)) {
            log.warn("carousel.filesystem.root is unset; any readable path may be requested. Set this property to restrict access.");
            return;
        }
        Path root = Paths.get(filesystemRoot).toAbsolutePath().normalize();
        Path absolute = path.toAbsolutePath().normalize();
        if (!absolute.startsWith(root)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Path outside carousel.filesystem.root");
        }
    }

    private static void streamFile(Path path, HttpServletResponse response) throws IOException {
        String fileName = path.getFileName().toString();
        String mime = Files.probeContentType(path);
        if (mime == null) {
            mime = "application/octet-stream";
        }
        response.setHeader("Content-Type", mime);
        response.setHeader("Content-Length", String.valueOf(Files.size(path)));
        response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
        Files.copy(path, response.getOutputStream());
        response.flushBuffer();
    }

    private static List<String> toExtensionList(@Nullable String[] extensions) {
        if (extensions == null || extensions.length == 0) {
            return new ArrayList<>();
        }
        return new ArrayList<>(Arrays.asList(extensions));
    }

    private static String requestBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme();
        String host = request.getServerName();
        int port = request.getServerPort();
        String ctx = request.getContextPath();
        StringBuilder sb = new StringBuilder();
        sb.append(scheme).append("://").append(host);
        if (("http".equals(scheme) && port != 80) || ("https".equals(scheme) && port != 443)) {
            sb.append(':').append(port);
        }
        sb.append(ctx);
        return sb.toString();
    }
}
