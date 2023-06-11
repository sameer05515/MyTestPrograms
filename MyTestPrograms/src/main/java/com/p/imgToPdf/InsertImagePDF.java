//package com.p.imgToPdf;
//
//import java.io.*;
//
//// importing itext library packages
//import com.itextpdf.io.image.*;
//import com.itextpdf.kernel.pdf.*;
//import com.itextpdf.layout.Document;
//import com.itextpdf.layout.element.Image;
//
//public class InsertImagePDF {
//  public static void main(String[] args)
//    throws IOException
//  {
//    String currDir = "C:\\Prem\\data\\others\\self\\comics\\Copy of 53_V27_Mummy_Ka_Kahar\\53 V27 Mummy Ka Kahar";
//
//    // Getting path of current working directory
//    // to create the pdf file in the same directory of
//    // the running java program
//    String pdfPath = currDir + "/InsertImage.pdf";
//
//    // Creating path for the new pdf file
//    PdfWriter writer = new PdfWriter(pdfPath);
//
//    // Creating PdfWriter object to write pdf file to
//    // the path
//    PdfDocument pdfDoc = new PdfDocument(writer);
//
//    // Creating a PdfDocument object
//    Document doc = new Document(pdfDoc);
//
//    // Creating a Document object
//    ImageData imageData = ImageDataFactory.create(
//      currDir + "/01.jpg");
//
//    // Creating imagedata from image on disk(from given
//    // path) using ImageData object
//    Image img = new Image(imageData);
//
//    // Creating Image object from the imagedata
//    doc.add(img);
//
//    // Adding Image to the empty document
//    doc.close();
//
//    // Closing the document
//    System.out.println(
//      "Image added successfully and PDF file created!");
//  }
//}
