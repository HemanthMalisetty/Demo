package utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import utils.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PDF {

    public static void main (String args[]) throws IOException {


        String inputImageFullName = "log/diffs/url VG Demo/url VG Demo/1200x1040/Mac OS X 10.14/Chrome/00000251829776547219/00000251829776546950/step 1 https~www.timeanddate.com~countdown~newyear-Diff.png";
        double scaleFactor = .250;

        try {
            // https://pdfbox.apache.org/docs/2.0.8/javadocs/overview-summary.html

            //Creating PDF document object
            PDDocument document = new PDDocument();

            //Creating a blank page
            PDPage page = new PDPage();
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            float pageHeight = page.getMediaBox().getHeight();
            float pageWidth = page.getMediaBox().getWidth();

            //Figure out where to put the image (upper left)

            //Drawing the image in the PDF document with a specified size
            File inputFile = new File(inputImageFullName);
            BufferedImage inputImage = ImageIO.read(inputFile);
            int imgWidth = inputImage.getWidth(null);
            int imgHeight = inputImage.getHeight(null);

            //Figure out the scale factor based on width to fit 2 images side by side
            scaleFactor = ((pageWidth / 2.5) / imgWidth);


            long scaledWidth = Math.round( (double) imgWidth * scaleFactor);
            long scaledHeight = Math.round( (double) imgHeight * scaleFactor);
            PDImageXObject pdImage = PDImageXObject.createFromFile(inputImageFullName,document);

            contentStream.drawImage(pdImage, 50, pageHeight - (scaledHeight + 45), scaledWidth, scaledHeight);
            contentStream.drawImage(pdImage, 55 + scaledWidth, pageHeight - (scaledHeight + 45), scaledWidth, scaledHeight);

            //Begin the Content stream
            contentStream.beginText();

            //Setting the font to the Content stream
            contentStream.setFont(PDType1Font.HELVETICA, 12);

            //Setting the position for the line
            contentStream.newLineAtOffset(40, pageHeight - 30);

            String text = "Applitools test name and step names here";

            //Adding text in the form of string
            contentStream.showText(text);

            //Ending the content stream
            contentStream.endText();

            System.out.println("Content added");

            //Closing the content stream
            contentStream.close();

            //Adding the blank page to the document
            document.addPage(page);

            //Saving the document
            document.save("./log/my_doc.pdf");

            System.out.println("PDF created");

            //Closing the document
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
