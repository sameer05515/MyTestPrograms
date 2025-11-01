package com.p.md.to.pdf;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.util.data.MutableDataSet;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class MdToPdf {

    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println(
                    "Usage: java MdToPdf <input.md> <output.pdf>"
            );
            return;
        }

        String inputFile = args[0];
        String outputFile = args[1];

        try {
            convertMarkdownToPdf(inputFile, outputFile);

            System.out.println(
                    "PDF created successfully: " + outputFile
            );

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void convertMarkdownToPdf(
            String inputFile,
            String outputFile) throws Exception {

        Path markdownPath = Path.of(inputFile);

        if (!Files.exists(markdownPath)) {
            throw new IllegalArgumentException(
                    "Markdown file not found: " + inputFile
            );
        }

        // -------------------------------------------------
        // 1. Read Markdown
        // -------------------------------------------------

        String markdown = Files.readString(
                markdownPath,
                StandardCharsets.UTF_8
        );

        // -------------------------------------------------
        // 2. Configure Flexmark
        // -------------------------------------------------

        MutableDataSet options = new MutableDataSet();

        options.set(
                Parser.EXTENSIONS,
                Arrays.asList(
                        TablesExtension.create()
                )
        );

        // -------------------------------------------------
        // 3. Markdown -> HTML
        // -------------------------------------------------

        Parser parser = Parser.builder(options).build();

        HtmlRenderer renderer =
                HtmlRenderer.builder(options).build();

        var document = parser.parse(markdown);

        String htmlContent = renderer.render(document);

        htmlContent = htmlContent
        .replace("<br>", "<br />")
        .replace("<hr>", "<hr />")
        .replaceAll("<img([^>]*?)(?<!/)>", "<img$1 />");

        // -------------------------------------------------
        // 4. Create complete HTML document
        // -------------------------------------------------

        String html = """
        <!DOCTYPE html>
        <html xmlns="http://www.w3.org/1999/xhtml">

        <head>

            <meta charset="UTF-8" />

            <style>

                @page {
                    size: A4;
                    margin: 25mm 20mm 25mm 20mm;
                }

                body {
                    font-family: Arial, Helvetica, sans-serif;
                    font-size: 14px;
                    line-height: 1.6;
                    color: #222;
                }

                h1 {
                    font-size: 28px;
                    border-bottom: 2px solid #333;
                    padding-bottom: 8px;
                    margin-bottom: 20px;
                }

                h2 {
                    font-size: 22px;
                    margin-top: 30px;
                }

                h3 {
                    font-size: 18px;
                    margin-top: 25px;
                }

                p {
                    margin: 10px 0;
                }

                ul,
                ol {
                    margin: 10px 0 10px 25px;
                }

                li {
                    margin-bottom: 5px;
                }

                blockquote {
                    border-left: 4px solid #999;
                    padding-left: 15px;
                    color: #555;
                    margin-left: 0;
                }

                code {
                    background: #f4f4f4;
                    padding: 2px 5px;
                    border-radius: 3px;
                    font-family: monospace;
                }

                pre {
                    background: #f4f4f4;
                    padding: 15px;
                    border-radius: 5px;
                    white-space: pre-wrap;
                    word-wrap: break-word;
                }

                pre code {
                    background: none;
                    padding: 0;
                }

                table {
                    width: 100%;
                    border-collapse: collapse;
                    margin: 20px 0;
                }

                th,
                td {
                    border: 1px solid #ccc;
                    padding: 8px;
                    text-align: left;
                }

                th {
                    background: #eee;
                }

                img {
                    max-width: 100%;
                }

                a {
                    color: #0645ad;
                    text-decoration: none;
                }

            </style>

        </head>

        <body>

        """ + htmlContent + """

        </body>

        </html>
        """;

        // -------------------------------------------------
        // 5. HTML -> PDF
        // -------------------------------------------------

        File pdfFile = new File(outputFile);

        File parent = pdfFile.getParentFile();

        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        try (FileOutputStream outputStream =
                     new FileOutputStream(pdfFile)) {

            PdfRendererBuilder builder =
                    new PdfRendererBuilder();

            builder.useFastMode();

            builder.withHtmlContent(
                    html,
                    markdownPath.toAbsolutePath()
                            .getParent()
                            .toUri()
                            .toString()
            );

            builder.toStream(outputStream);

            builder.run();
        }
    }
}