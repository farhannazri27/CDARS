package com.onsemi.cdars.pdf;

import com.itextpdf.text.BaseColor;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletContext;
import org.apache.commons.io.IOUtils;

public abstract class AbstractITextPdfViewPotrait extends AbstractView {

    public AbstractITextPdfViewPotrait() {
        setContentType("application/pdf");
    }

    @Override
    protected boolean generatesDownloadContent() {
        return true;
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // IE workaround: write into byte array first.
        ByteArrayOutputStream baos = createTemporaryOutputStream();

        // Apply preferences and build metadata.
        Document document = newDocument();
        PdfWriter writer = newWriter(document, baos);
        TableHeader event = new TableHeader();
        writer.setPageEvent(event);
        prepareWriter(model, writer, request);
        buildPdfMetadata(model, document, request);

        // Build PDF document.
        document.open();
        // Add Logo
        ServletContext context = getServletContext();
        //String file = "/resources/img/cmts_all.png";
        String file = "/resources/img/cdars_logo.png";
        InputStream is = context.getResourceAsStream(file);
        byte[] bytes = IOUtils.toByteArray(is);
        Image image = Image.getInstance(bytes);
        event.setHeaderLogo(image);
        //Add Text
        event.setHeader("CDARS");
        event.setFooter("Copyright © 2016, ONSEMI");
        // Build PDF
        buildPdfDocument(model, document, writer, request, response);
        document.close();

        // Flush to HTTP response.
        writeToResponse(response, baos);
    }

    protected Document newDocument() {
        //return new Document(PageSize.LETTER.rotate());
        return new Document(PageSize.A4, 36, 36, 80, 36);
        //return new Document(PageSize.A4);
    }

    protected PdfWriter newWriter(Document document, OutputStream os) throws DocumentException {
        return PdfWriter.getInstance(document, os);
    }

    protected void prepareWriter(Map<String, Object> model, PdfWriter writer, HttpServletRequest request)
            throws DocumentException {
        writer.setViewerPreferences(getViewerPreferences());
    }

    protected int getViewerPreferences() {
        return PdfWriter.ALLOW_PRINTING | PdfWriter.PageLayoutSinglePage;
    }

    protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {
    }

    protected abstract void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
            HttpServletRequest request, HttpServletResponse response) throws Exception;

    public Font fontOpenSans() throws DocumentException, IOException {
        return fontOpenSans(9f, Font.NORMAL);
    }

    public Font fontOpenSans(Float size, Integer style) throws DocumentException, IOException {
        ServletContext context = getServletContext();
        String regular = "OpenSans-Regular.ttf";
        String bold = "OpenSans-Bold.ttf";
        String boldItalic = "OpenSans-BoldItalic.ttf";
        String file = "/resources/fonts/" + regular;
        if (style == Font.BOLD) {
            file = "/resources/fonts/" + bold;
        } else if (style == Font.BOLDITALIC) {
            file = "/resources/fonts/" + boldItalic;
        }
        InputStream is = context.getResourceAsStream(file);
        byte[] ttfAfm = IOUtils.toByteArray(is);
        BaseFont base = BaseFont.createFont(regular, BaseFont.WINANSI, BaseFont.EMBEDDED, BaseFont.NOT_CACHED, ttfAfm, null);
        if (style == Font.BOLD) {
            base = BaseFont.createFont(bold, BaseFont.WINANSI, BaseFont.EMBEDDED, BaseFont.NOT_CACHED, ttfAfm, null);
            style = Font.NORMAL;
        } else if (style == Font.BOLDITALIC) {
            base = BaseFont.createFont(boldItalic, BaseFont.WINANSI, BaseFont.EMBEDDED, BaseFont.NOT_CACHED, ttfAfm, null);
            style = Font.NORMAL;
        }
        Font font = new Font(base, size, style);
        return font;
    }

    public PdfPTable tableNoData() throws IOException, DocumentException {
        Font fontNoData = fontOpenSans(9f, Font.ITALIC);
        Integer cellPadding = 5;
        
        PdfPTable tableNoData = new PdfPTable(1);
        tableNoData.setWidthPercentage(100.0f);
        tableNoData.setWidths(new float[]{1.0f});
        tableNoData.setSpacingBefore(0);

        PdfPCell cellNoData = new PdfPCell();
        cellNoData.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellNoData.setPadding(cellPadding);
        cellNoData.setPhrase(new Phrase("No data available!", fontNoData));
        tableNoData.addCell(cellNoData);
        
        return tableNoData;
    }

    /**
     * Inner class to add a table as header.
     */
    class TableHeader extends PdfPageEventHelper {

        /**
         * The header text.
         */
        String header;
        String footer;
        Image headerLogo;
        /**
         * The template with the total number of pages.
         */
        PdfTemplate total;

        /**
         * Allows us to change the content of the header.
         *
         * @param header The new header String
         */
        public void setHeader(String header) {
            this.header = header;
        }

        public void setHeaderLogo(Image headerLogo) {
            this.headerLogo = headerLogo;
        }

        public void setFooter(String footer) {
            this.footer = footer;
        }

        /**
         * Creates the PdfTemplate that will hold the total number of pages.
         *
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onOpenDocument(
         * com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
         */
        @Override
        public void onOpenDocument(PdfWriter writer, Document document) {
            total = writer.getDirectContent().createTemplate(30, 16);
        }

        /**
         * Adds a header to every page
         *
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(
         * com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
         */
        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            try {
                BaseColor borderColor = new BaseColor(249, 78, 5);
                Float borderWidth = 1.8f;
                // Table Header
                PdfPTable tableHeader = new PdfPTable(4);
                tableHeader.setWidths(new float[]{6.7f, 6.0f, 5.8f, 1.0f});
                tableHeader.setTotalWidth(527);
                tableHeader.setLockedWidth(true);
                // Set Logo
                PdfPCell cellLogo = new PdfPCell(headerLogo);
                cellLogo.setBorder(Rectangle.BOTTOM);
                cellLogo.setBorderColor(borderColor);
                cellLogo.setBorderWidth(borderWidth);
                cellLogo.setFixedHeight(35);
                cellLogo.setPaddingBottom(5);
                tableHeader.addCell(cellLogo);
                // Set Text Header
                PdfPCell cellTitle = new PdfPCell();
                cellTitle.setPhrase(new Phrase(header, fontOpenSans(12f, Font.BOLD)));
                cellTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellTitle.setBorder(Rectangle.BOTTOM);
                cellTitle.setBorderColor(borderColor);
                cellTitle.setBorderWidth(borderWidth);
                cellTitle.setFixedHeight(37);
                cellTitle.setPaddingBottom(5);
                cellTitle.setPaddingTop(0);
                tableHeader.addCell(cellTitle);
                // Set Page Of
                PdfPCell cellPageOf = new PdfPCell();
                cellPageOf.setPhrase(new Phrase(String.format("Page %d of", writer.getPageNumber()), fontOpenSans(8f, Font.NORMAL)));
                cellPageOf.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cellPageOf.setBorder(Rectangle.BOTTOM);
                cellPageOf.setBorderColor(borderColor);
                cellPageOf.setBorderWidth(borderWidth);
                cellPageOf.setFixedHeight(37);
                cellPageOf.setPaddingBottom(4.95f);
                cellPageOf.setPaddingTop(22.0f);
                cellPageOf.setPaddingRight(3.0f);
                tableHeader.addCell(cellPageOf);
                // Set Page Number
                PdfPCell cellPageNo = new PdfPCell(Image.getInstance(total));
                cellPageNo.setBorder(Rectangle.BOTTOM);
                cellPageNo.setBorderColor(borderColor);
                cellPageNo.setBorderWidth(borderWidth);
                cellPageNo.setFixedHeight(37);
                cellPageNo.setPaddingBottom(5.3f);
                cellPageNo.setPaddingTop(22.0f);
                tableHeader.addCell(cellPageNo);
                // Write Table Header
                tableHeader.writeSelectedRows(0, -1, 34, 823, writer.getDirectContent());

                // Table Footer
                PdfPTable tableFooter = new PdfPTable(1);
                tableFooter.setWidths(new float[]{100.0f});
                tableFooter.setTotalWidth(527);
                tableFooter.setLockedWidth(true);
                // Set Footer Title
                PdfPCell cellFooter = new PdfPCell();
                cellFooter.setPhrase(new Phrase(footer, fontOpenSans(6f, Font.NORMAL)));
                cellFooter.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellFooter.setBorder(Rectangle.TOP);
                cellFooter.setBorderColor(borderColor);
                cellFooter.setBorderWidth(borderWidth);
                cellFooter.setPaddingTop(5);
                tableFooter.addCell(cellFooter);
                // Write Table Footer
                tableFooter.writeSelectedRows(0, -1, 34, 30, writer.getDirectContent());
            } catch (DocumentException de) {
                throw new ExceptionConverter(de);
            } catch (IOException ex) {
                throw new ExceptionConverter(ex);
            }
        }

        /**
         * Fills out the total number of pages before the document is closed.
         *
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onCloseDocument(
         * com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
         */
        @Override
        public void onCloseDocument(PdfWriter writer, Document document) {
            ColumnText.showTextAligned(total, Element.ALIGN_LEFT,
                    new Phrase(String.valueOf(writer.getPageNumber() - 1)),
                    2, 2, 0);
        }
    }
}
