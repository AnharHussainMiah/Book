package com.anhar;

import com.itextpdf.kernel.pdf.event.PdfDocumentEvent;
import com.itextpdf.kernel.pdf.event.AbstractPdfDocumentEvent;
import com.itextpdf.kernel.pdf.event.AbstractPdfDocumentEventHandler;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;

public class PageNumberHandler extends AbstractPdfDocumentEventHandler {

    private final PdfFont font;

    public PageNumberHandler() throws Exception {
        this.font = PdfFontFactory.createFont();
    }

    @Override
    public void onAcceptedEvent(AbstractPdfDocumentEvent event) {

        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfDocument pdf = docEvent.getDocument();

        int pageNumber = pdf.getPageNumber(docEvent.getPage());

        // Skip title page
        if (pageNumber == 1) {
            return;
        }

        float x = docEvent.getPage().getPageSize().getWidth() / 2;
        float y = 20;

        PdfCanvas pdfCanvas = new PdfCanvas(docEvent.getPage());

        Canvas canvas = new Canvas(
                pdfCanvas,
                docEvent.getPage().getPageSize());

        canvas.showTextAligned(
                new Paragraph(String.valueOf(pageNumber - 1))
                        .setFont(font)
                        .setFontSize(10),
                x,
                y,
                TextAlignment.CENTER);

        canvas.close();
    }
}