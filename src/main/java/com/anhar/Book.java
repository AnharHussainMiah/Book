package com.anhar;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Collectors;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.event.PdfDocumentEvent;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.AreaBreakType;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;

public class Book {
        private static float mmToPt(float mm) {
                return mm * 72f / 25.4f;
        }

        public static void main(String[] args) throws Exception {
                String[] book = loadBookData(Path.of("."));

                String title = book[0];
                String bookData = book[1];

                GeneratePDF(title, bookData);
        }

        private static void GeneratePDF(String Title, String BookData) throws Exception {
                PageSize bFormat = new PageSize(
                                mmToPt(129),
                                mmToPt(198));

                PdfDocument pdf = new PdfDocument(new PdfWriter("book.pdf"));
                Document document = new Document(pdf, bFormat);

                pdf.addEventHandler(
                                PdfDocumentEvent.END_PAGE,
                                new PageNumberHandler());

                document.setMargins(
                                50, // top
                                45, // right
                                45, // bottom
                                45 // left
                );

                Paragraph title = new Paragraph(Title.toUpperCase())
                                .setFontSize(28)
                                .setTextAlignment(TextAlignment.CENTER);

                float pageHeight = bFormat.getHeight();

                document.showTextAligned(
                                title,
                                bFormat.getWidth() / 2,
                                pageHeight / 2,
                                pdf.getNumberOfPages() + 1,
                                TextAlignment.CENTER,
                                VerticalAlignment.MIDDLE,
                                0);

                document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

                document.add(new Paragraph(BookData)
                                .setFontSize(11)
                                .setMultipliedLeading(1.4f)
                                .setTextAlignment(TextAlignment.JUSTIFIED_ALL));

                document.close();
        }

        public static String[] loadBookData(Path directory) throws IOException {

                Path titleFile = directory.resolve("000.txt");

                if (!Files.exists(titleFile)) {
                        throw new IllegalStateException("Missing title file: 000.txt");
                }

                String title = Files.readString(titleFile).trim();

                String bookData = Files.list(directory)
                                .filter(Files::isRegularFile)
                                .filter(p -> p.getFileName().toString().matches("\\d{3}\\.txt"))
                                .filter(p -> !p.getFileName().toString().equals("000.txt"))
                                .sorted(Comparator.comparing(
                                                p -> p.getFileName().toString()))
                                .map(path -> {
                                        try {
                                                return Files.readString(path);
                                        } catch (IOException e) {
                                                throw new RuntimeException(e);
                                        }
                                })
                                .collect(Collectors.joining(
                                                System.lineSeparator() + System.lineSeparator()));

                bookData = bookData.replace("\r\n", " ").replace("\n", " ").replace(".", " ").replace(" ", ".")
                                .replaceAll(
                                                "[\\s.]+",
                                                ".");

                return new String[] {
                                title,
                                bookData
                };
        }
}
