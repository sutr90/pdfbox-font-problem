package cz.marbes.anonymizace.impl;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.util.Matrix;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PdfFontTest {

    @Test
    public void pdfFontTest() {
        try (PDDocument document = PDDocument.load(PdfDrawingTest.class.getResourceAsStream("protokol.pdf"))) {
            PDPage page = document.getPage(0);
            PDResources res = page.getResources();

            List<PDFont> fonts = new ArrayList<>();

            for (COSName fontName : res.getFontNames()) {
                PDFont font = res.getFont(fontName);
                System.out.println(font);
                fonts.add(font);
            }

            PDPage testPage = new PDPage();
            try (PDPageContentStream stream = new PDPageContentStream(document, testPage, PDPageContentStream.AppendMode.OVERWRITE, true)) {
                stream.beginText();
                int yPos = 200;
                for (int i = 0; i < fonts.size(); i++) {
                    stream.setFont(fonts.get(i), 12);
                    stream.setTextMatrix(Matrix.getTranslateInstance(20, yPos - 50 * i));
                    stream.showText("Protokol");

                }
                stream.endText();
            } catch (IOException e) {
                e.printStackTrace();
            }
            document.addPage(testPage);
        } catch (
            IOException e) {
            e.printStackTrace();
        }
    }
}
