# Markdown to PDF Converter — Java

A simple Java command-line application to convert a **Markdown (`.md`) file into a PDF**.

The application uses:

* **Flexmark** — Markdown parsing and Markdown → HTML conversion
* **OpenHTMLtoPDF** — HTML/XHTML → PDF conversion
* **PDFBox** — PDF rendering internally used by OpenHTMLtoPDF
* **Maven** — Dependency and build management

---

## Features

* Convert Markdown files to PDF
* A4 PDF output
* Custom input and output filenames
* Support for headings
* Support for paragraphs
* Support for ordered and unordered lists
* Support for bold and italic text
* Support for blockquotes
* Support for inline code
* Support for code blocks
* Support for tables
* Support for images
* Support for hyperlinks
* Custom CSS styling
* Print-friendly PDF layout
* No external browser installation required

---

## Technology Stack

```text
Java
  |
  +-- Flexmark
  |      |
  |      +-- Markdown -> HTML
  |
  +-- OpenHTMLtoPDF
  |      |
  |      +-- HTML/XHTML -> PDF
  |
  +-- PDFBox
         |
         +-- PDF rendering
```

---

## Project Structure

```text
md-to-pdf/
│
├── pom.xml
│
├── input.md
│
├── output.pdf
│
└── src/
    └── main/
        └── java/
            └── com/
                └── p/
                    └── md/
                        └── to/
                            └── pdf/
                                └── MdToPdf.java
```

---

# Prerequisites

## Java

Java 17 or later is recommended.

Check Java version:

```bash
java -version
```

Example:

```text
java version "21.0.x"
```

## Maven

Check Maven:

```bash
mvn -version
```

Example:

```text
Apache Maven 3.x.x
Java version: 21.x.x
```

---

# Maven Dependencies

The project uses the following dependencies.

```xml
<dependencies>

    <!-- Markdown -> HTML -->
    <dependency>
        <groupId>com.vladsch.flexmark</groupId>
        <artifactId>flexmark-all</artifactId>
        <version>0.64.8</version>
    </dependency>

    <!-- HTML/XHTML -> PDF -->
    <dependency>
        <groupId>com.openhtmltopdf</groupId>
        <artifactId>openhtmltopdf-pdfbox</artifactId>
        <version>1.0.10</version>
    </dependency>

</dependencies>
```

---

# Maven Exec Plugin

The application can be executed directly through Maven using the `exec-maven-plugin`.

Add the following to `pom.xml`:

```xml
<build>
    <plugins>

        <plugin>
            <groupId>org.codehaus.mojo</groupId>
            <artifactId>exec-maven-plugin</artifactId>
            <version>3.5.0</version>

            <configuration>
                <mainClass>com.p.md.to.pdf.MdToPdf</mainClass>
            </configuration>
        </plugin>

    </plugins>
</build>
```

---

# Build the Project

Run:

```bash
mvn clean compile
```

---

# Usage

The application accepts two command-line arguments:

```text
<input.md> <output.pdf>
```

For example:

```bash
mvn exec:java '-Dexec.args=input.md output.pdf'
```

This converts:

```text
input.md
```

into:

```text
output.pdf
```

---

# Windows PowerShell

On Windows PowerShell, use:

```powershell
mvn clean compile
```

Then:

```powershell
mvn exec:java '-Dexec.args=input.md output.pdf'
```

Expected output:

```text
PDF created successfully: output.pdf
```

---

# Custom Input and Output

You can specify any Markdown and PDF filenames:

```powershell
mvn exec:java '-Dexec.args=README.md documentation.pdf'
```

For files containing spaces, quote the individual paths:

```powershell
mvn exec:java '-Dexec.args="My Document.md" "My Document.pdf"'
```

---

# Sample Markdown

Create an `input.md` file:

````markdown
# Markdown to PDF

This document is generated using **Java**.

## Technologies

- Java
- Maven
- Flexmark
- OpenHTMLtoPDF
- PDFBox

## Code Example

```java
public class HelloWorld {

    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}
````

## Table

| Technology    | Purpose          |
| ------------- | ---------------- |
| Java          | Programming      |
| Flexmark      | Markdown parsing |
| OpenHTMLtoPDF | PDF generation   |
| PDFBox        | PDF rendering    |

## Important

This is a **Markdown to PDF converter**.

> PDF generation is performed without Chrome or Puppeteer.

## Conclusion

Markdown has been successfully converted to PDF.

````

Run:

```powershell
mvn exec:java '-Dexec.args=input.md output.pdf'
````

---

# How It Works

The conversion process is:

```text
              input.md
                  |
                  v
        Read Markdown File
                  |
                  v
             Flexmark
                  |
                  v
          Markdown -> HTML
                  |
                  v
       XHTML + Custom CSS
                  |
                  v
         OpenHTMLtoPDF
                  |
                  v
             PDFBox
                  |
                  v
              output.pdf
```

---

# Step 1 — Read Markdown

The application reads the Markdown file using Java NIO:

```java
String markdown = Files.readString(
        markdownPath,
        StandardCharsets.UTF_8
);
```

---

# Step 2 — Parse Markdown

Flexmark parser is configured:

```java
MutableDataSet options = new MutableDataSet();

options.set(
        Parser.EXTENSIONS,
        Arrays.asList(
                TablesExtension.create()
        )
);
```

The Markdown is parsed:

```java
Parser parser = Parser.builder(options).build();

var document = parser.parse(markdown);
```

---

# Step 3 — Markdown to HTML

Flexmark converts the parsed Markdown into HTML:

```java
HtmlRenderer renderer =
        HtmlRenderer.builder(options).build();

String htmlContent =
        renderer.render(document);
```

---

# Step 4 — Create XHTML Document

The generated HTML is placed inside an XHTML document:

```java
String html = """
        <!DOCTYPE html>
        <html xmlns="http://www.w3.org/1999/xhtml">

        <head>

            <meta charset="UTF-8" />

            <style>
                body {
                    font-family: Arial, Helvetica, sans-serif;
                    font-size: 14px;
                    line-height: 1.6;
                }

                table {
                    width: 100%;
                    border-collapse: collapse;
                }

                th,
                td {
                    border: 1px solid #ccc;
                    padding: 8px;
                }
            </style>

        </head>

        <body>

        """ + htmlContent + """

        </body>

        </html>
        """;
```

### Why XHTML?

OpenHTMLtoPDF uses an XML/XHTML parser.

Therefore HTML elements such as:

```html
<meta charset="UTF-8" />
```

must be properly closed.

Incorrect:

```html
<meta charset="UTF-8">
```

Correct:

```html
<meta charset="UTF-8" />
```

Similarly:

```html
<br />
<hr />
<img src="image.png" />
```

should be self-closed.

---

# Step 5 — Generate PDF

OpenHTMLtoPDF converts the XHTML document into PDF:

```java
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
```

---

# PDF Configuration

The generated document uses A4 paper:

```css
@page {
    size: A4;
    margin: 25mm 20mm 25mm 20mm;
}
```

The application also supports custom styling through CSS.

Example:

```css
body {
    font-family: Arial, Helvetica, sans-serif;
    font-size: 14px;
    line-height: 1.6;
}

h1 {
    font-size: 28px;
}

h2 {
    font-size: 22px;
}
```

---

# Tables

Markdown tables are supported using Flexmark's table extension.

Example:

```markdown
| Name | Role |
|------|------|
| John | Developer |
| Smith | Tester |
```

Output:

```text
+-------+-----------+
| Name  | Role      |
+-------+-----------+
| John  | Developer |
| Smith | Tester    |
+-------+-----------+
```

---

# Code Blocks

Markdown code blocks are supported:

````markdown
```java
public class HelloWorld {

    public static void main(String[] args) {
        System.out.println("Hello");
    }
}
```
````

They are converted to HTML `<pre>` and `<code>` elements and rendered into the PDF.

---

# Images

Images can be referenced from Markdown:

```markdown
![Java Logo](images/java.png)
```

The converter provides the Markdown file's parent directory as the base URI:

```java
builder.withHtmlContent(
        html,
        markdownPath.toAbsolutePath()
                .getParent()
                .toUri()
                .toString()
);
```

This allows relative resources to be resolved relative to the Markdown file.

Example:

```text
project/
│
├── input.md
│
├── images/
│   └── java.png
│
└── output.pdf
```

Markdown:

```markdown
![Java](images/java.png)
```

---

# Hyperlinks

Markdown hyperlinks are supported:

```markdown
[OpenAI](https://openai.com)
```

They are converted into HTML links:

```html
<a href="https://openai.com">OpenAI</a>
```

---

# Error Handling

If the Markdown file does not exist:

```text
Markdown file not found: input.md
```

The application validates the input file:

```java
if (!Files.exists(markdownPath)) {
    throw new IllegalArgumentException(
            "Markdown file not found: " + inputFile
    );
}
```

---

# Common Issues

## 1. `NoClassDefFoundError`

If you run:

```bash
java -cp target/classes com.p.md.to.pdf.MdToPdf
```

you may get:

```text
java.lang.NoClassDefFoundError:
com/vladsch/flexmark/util/data/DataHolder
```

This happens because:

```text
target/classes
```

contains your compiled classes but not Maven dependencies.

### Solution

Use Maven:

```powershell
mvn exec:java '-Dexec.args=input.md output.pdf'
```

---

## 2. `UnknownFormatConversionException`

If you use:

```java
""".formatted(htmlContent);
```

inside an HTML/CSS template containing:

```css
width: 100%;
```

Java may interpret `%` as a formatting character.

This can produce:

```text
java.util.UnknownFormatConversionException
```

### Solution

Avoid `.formatted()` for the complete CSS/HTML template:

```java
String html = """
        <html>
        <body>
        """ + htmlContent + """
        </body>
        </html>
        """;
```

---

## 3. `The element type "meta" must be terminated`

OpenHTMLtoPDF expects XHTML-compatible markup.

Incorrect:

```html
<meta charset="UTF-8">
```

Correct:

```html
<meta charset="UTF-8" />
```

Also use:

```html
<br />
<hr />
<img src="image.png" />
```

---

# Running Directly with Java

You can run the compiled application directly only when all dependency JARs are included in the classpath.

For normal development, Maven Exec Plugin is easier:

```powershell
mvn exec:java '-Dexec.args=input.md output.pdf'
```

---

# Complete Command Sequence

On Windows:

```powershell
git clone <repository-url>

cd md-to-pdf

mvn clean compile

mvn exec:java '-Dexec.args=input.md output.pdf'
```

Result:

```text
input.md
    |
    v
output.pdf
```

---

# Example

Input:

```text
E:\documents\input.md
```

Output:

```text
E:\documents\output.pdf
```

Command:

```powershell
mvn exec:java '-Dexec.args=E:\documents\input.md E:\documents\output.pdf'
```

---

# Advantages

Compared with a browser-based solution, this implementation:

* Does not require Chrome
* Does not require Puppeteer
* Runs directly on the JVM
* Can easily be integrated into Spring Boot
* Supports Maven dependency management
* Can be deployed on Linux/Windows servers
* Can be converted into a REST API

---

# Possible Enhancements

The application can be extended with:

* Table of contents
* Syntax highlighting
* Custom fonts
* Header and footer
* Page numbers
* Cover page
* Custom page sizes
* Landscape mode
* Custom CSS file
* Multiple Markdown files → single PDF
* Directory → PDF
* REST API using Spring Boot
* File upload API
* PDF download endpoint
* Mermaid diagram support
* PDF metadata
* Watermarks

---

# Spring Boot Integration

The converter can be easily exposed through a Spring Boot REST API:

```text
POST /api/pdf
```

Example flow:

```text
Client
  |
  | multipart/form-data
  v
Spring Boot
  |
  v
MdToPdf Service
  |
  +-- Flexmark
  |
  +-- OpenHTMLtoPDF
  |
  v
PDF
  |
  v
Client
```

---

# License

This project is intended for learning, development, and demonstration purposes.
