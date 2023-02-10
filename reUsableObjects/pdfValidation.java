package com.hp.opr.qa.framework.reUsableObjects;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.io.RandomAccessFile;
import java.io.File;


public class pdfValidation {
    public static String readPDFContent(String filePath) throws Exception {

        PDFTextStripper pdfStripper = null;
        COSDocument document = null;
        PDDocument pdDoc = null;
        String parsedText = null;
        boolean found1 = true,found2 = true;
        File fileToParse = new File(filePath);

        try{
            PDFParser parser = new PDFParser(new RandomAccessFile(fileToParse,"r"));
            parser.parse();
            document = parser.getDocument();
            pdfStripper = new PDFTextStripper();
            pdfStripper.setStartPage(1);
            pdfStripper.setEndPage(2);
            pdDoc = new PDDocument(document);
            int no = pdDoc.getNumberOfPages();
            System.out.println("no. of  pages="+no);
            parsedText = pdfStripper.getText(pdDoc);
            System.out.println("Text = ");
            System.out.println(parsedText);

        }



        finally {

            if (document != null) {
                document.close();
            }

            pdDoc.close();
            fileToParse.delete();
        }
        return parsedText;
    }
}