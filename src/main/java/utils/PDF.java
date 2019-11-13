package utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

public class PDF {

    // public static void main (String args[]) throws IOException { }


    public static void makePDF(List<BufferedImage> leftimages, List<BufferedImage> rightimages,
                               String stepNames[], String batchName, String testName, String PDFName, String timeStamp) throws IOException {

        try {
            // https://pdfbox.apache.org/docs/2.0.8/javadocs/overview-summary.html

            PDDocument document = new PDDocument();

            PDPage page;
            PDPageContentStream contentStream;
            Image inputImage;
            PDImageXObject pdImage;

            float pageHeight;
            float pageWidth;

            for(int i=0;i<leftimages.size();i++) {
                page = new PDPage();
                contentStream = new PDPageContentStream(document, page);
                pageHeight = page.getMediaBox().getHeight();
                pageWidth = page.getMediaBox().getWidth();
                if(null!=leftimages.get(i)) {
                    int imgWidth = leftimages.get(i).getWidth(null);
                    int imgHeight = leftimages.get(i).getHeight(null);
                    double scaleFactor = ((pageWidth / 2.5) / imgWidth);
                    if (scaleFactor > 1) scaleFactor = 1;
                    long scaledWidth = Math.round((double) imgWidth * scaleFactor);
                    long scaledHeight = Math.round((double) imgHeight * scaleFactor);
                    pdImage = JPEGFactory.createFromImage(document, leftimages.get(i));
                    contentStream.drawImage(pdImage, 50, pageHeight - (scaledHeight + 75), scaledWidth, scaledHeight);
                }

                if(null!=rightimages.get(i)) {
                    int imgWidth = rightimages.get(i).getWidth(null);
                    int imgHeight = rightimages.get(i).getHeight(null);
                    double scaleFactor = ((pageWidth / 2.5) / imgWidth);
                    if (scaleFactor > 1) scaleFactor = 1;
                    long scaledWidth = Math.round((double) imgWidth * scaleFactor);
                    long scaledHeight = Math.round((double) imgHeight * scaleFactor);
                    pdImage = JPEGFactory.createFromImage(document, rightimages.get(i));
                    contentStream.drawImage(pdImage, 55 + scaledWidth, pageHeight - (scaledHeight + 75), scaledWidth, scaledHeight);
                }

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 11);
                contentStream.newLineAtOffset(40, pageHeight - 35);
                String text = batchName + " | " + testName + " | Step " + (i+1) + "/" + String.valueOf(leftimages.size()) + " " + stepNames[i];
                contentStream.showText(text);
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 10);
                contentStream.newLineAtOffset(40, pageHeight - 50);
                contentStream.showText(timeStamp);
                contentStream.endText();

                contentStream.close();

                document.addPage(page);

            }
            document.save(PDFName);
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
