package pl.woelke.garbageapi.utility;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class PdfParser {

    private static final Logger LOGGER = Logger.getLogger(PdfParser.class.getName());
    private String result;

    public PdfParser() {
    }

    public String ExtractTextByArea(File fileName, int x,int y,int width,int height) {

            LOGGER.info("ExtractTextByArea()");

            try (PDDocument document = PDDocument.load(fileName)) {

                if (!document.isEncrypted()) {

                    PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                    stripper.setSortByPosition(true);
                    stripper.setShouldSeparateByBeads(true);
                    Rectangle rect = new Rectangle(x,y,width,height);
                    stripper.addRegion("class1", rect);
                    PDPage firstPage = document.getPage(0);
                    stripper.extractRegions( firstPage );
                    // System.out.println("Text in the area:" + rect);
                    result = stripper.getTextForRegion("class1");

                }
            } catch (IOException e){
                System.err.println("Exception while trying to read pdf document - " + e);
            }
            LOGGER.info("ExtractedTextByArea:"+ result);
            return result;
        }
    }

