package utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.IOException;

public class PDF {

    public static void main (String args[]) throws IOException {
        //Creating PDF document object
        PDDocument document = new PDDocument();

        try {
            //Creating a blank page
            PDPage page = new PDPage();
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            //Creating PDImageXObject object
            PDImageXObject pdImage = PDImageXObject.createFromFile("log/diffs/url VG Demo/url VG Demo/1200x1040/Mac OS X 10.14/Chrome/00000251829776547219/00000251829776546950/step 1 https~www.timeanddate.com~countdown~newyear-Diff.png",document);

            //Drawing the image in the PDF document
            contentStream.drawImage(pdImage, 70, 250);

            //Begin the Content stream
            contentStream.beginText();

            //Setting the font to the Content stream
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);

            //Setting the position for the line
            contentStream.newLineAtOffset(25, 800);

            String text = "This is the sample document and we are adding content to it.";

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
