package com.onsemi.cdars.pdf.viewer;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.onsemi.cdars.model.WhShipping;
import com.onsemi.cdars.pdf.AbstractITextPdfViewPotrait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WhShippingPdf extends AbstractITextPdfViewPotrait {

    private static final Logger LOGGER = LoggerFactory.getLogger(WhShippingPdf.class);

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc,
            PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        WhShipping whShipping = (WhShipping) model.get("whShipping");

        if ("Motherboard".equals(whShipping.getRequestEquipmentType())) {
            String title = "MOTHERBOARD TRIP TICKET";
            Paragraph viewTitle = new Paragraph(title, fontOpenSans(10f, Font.BOLD));
            viewTitle.setAlignment(Element.ALIGN_CENTER);

            doc.add(viewTitle);
        } else if ("Stencil".equals(whShipping.getRequestEquipmentType())) {
            String title = "STENCIL TRIP TICKET";
            Paragraph viewTitle = new Paragraph(title, fontOpenSans(10f, Font.BOLD));
            viewTitle.setAlignment(Element.ALIGN_CENTER);

            doc.add(viewTitle);
        } else if ("Tray".equals(whShipping.getRequestEquipmentType())) {
            String title = "TRAY TRIP TICKET";
            Paragraph viewTitle = new Paragraph(title, fontOpenSans(10f, Font.BOLD));
            viewTitle.setAlignment(Element.ALIGN_CENTER);

            doc.add(viewTitle);
        } else if ("PCB".equals(whShipping.getRequestEquipmentType())) {
            String title = "PCB TRIP TICKET";
            Paragraph viewTitle = new Paragraph(title, fontOpenSans(10f, Font.BOLD));
            viewTitle.setAlignment(Element.ALIGN_CENTER);

            doc.add(viewTitle);
        } else {
            String title = "HARDWARE TRIP TICKET";
            Paragraph viewTitle = new Paragraph(title, fontOpenSans(10f, Font.BOLD));
            viewTitle.setAlignment(Element.ALIGN_CENTER);

            doc.add(viewTitle);
        }

        doc.add(Chunk.NEWLINE);

        //        doc.add(code128.createImageWithBarcode(barcodewriter.getDirectContent(), BaseColor.BLACK, BaseColor.YELLOW));
        Integer cellPadding = 7;

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[]{0.8f, 4.0f});
        table.setSpacingBefore(20);

        Font fontHeader = fontOpenSans(7f, Font.BOLD);
        fontHeader.setColor(BaseColor.WHITE);

        PdfPCell cellHeader = new PdfPCell();
        cellHeader.setBackgroundColor(BaseColor.DARK_GRAY);
        cellHeader.setPadding(cellPadding);
//        cellHeader.setBorder(Rectangle.NO_BORDER);

        Font fontContent = fontOpenSans();

        PdfPCell cellContent = new PdfPCell();
        cellContent.setPadding(cellPadding);
//        cellContent.setBorder(Rectangle.NO_BORDER);

        PdfContentByte cb = writer.getDirectContent();
        Barcode128 code128 = new Barcode128();
        code128.setGenerateChecksum(true);
        code128.setCode(whShipping.getRequestEquipmentId());
        code128.setSize(cellPadding);
        Image code128Image = code128.createImageWithBarcode(cb, null, null);
        float dd = code128Image.getScaledWidth();
        PdfPCell barcode = new PdfPCell();
        if (dd > 410.20001) {
            barcode = new PdfPCell(code128Image, true);
            barcode.setPaddingLeft(7.0f);
            barcode.setPaddingTop(3.5f);
            barcode.setPaddingRight(7.0f);
        } else {
            barcode = new PdfPCell(code128Image);
            barcode.setPaddingLeft(7.0f);
            barcode.setPaddingTop(3.5f);
            barcode.setPaddingRight(7.0f);
        }
//        PdfPCell barcode = new PdfPCell(code128Image,true);
//            cell.setBorder(Rectangle.NO_BORDER);
//        barcode.setPaddingLeft(8.0f);
//        barcode.setPaddingTop(3.0f);
//        barcode.setPaddingRight(7.0f);

//        LOGGER.info(" code128Image.getAbsoluteX(); " + dd);
        whShipping = (WhShipping) model.get("whShipping");

        if ("Motherboard".equals(whShipping.getRequestEquipmentType())) {
            cellHeader.setPhrase(new Phrase("Motherboard ID", fontHeader));
            table.addCell(cellHeader);
            table.addCell(barcode);

            cellHeader.setPhrase(new Phrase("Quantity", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whShipping.getRequestQuantity() + " Motherboard", fontContent));
            table.addCell(cellContent);
        } else if ("Stencil".equals(whShipping.getRequestEquipmentType())) {
            cellHeader.setPhrase(new Phrase("Stencil ID", fontHeader));
            table.addCell(cellHeader);
            table.addCell(barcode);

            cellHeader.setPhrase(new Phrase("Quantity", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whShipping.getRequestQuantity() + " Stencil", fontContent));
            table.addCell(cellContent);
        } else if ("Tray".equals(whShipping.getRequestEquipmentType())) {
            cellHeader.setPhrase(new Phrase("Tray ID", fontHeader));
            table.addCell(cellHeader);
            table.addCell(barcode);

            cellHeader.setPhrase(new Phrase("Quantity", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whShipping.getRequestQuantity() + " Tray(s)", fontContent));
            table.addCell(cellContent);

        } else if ("PCB".equals(whShipping.getRequestEquipmentType())) {
            cellHeader.setPhrase(new Phrase("PCB ID", fontHeader));
            table.addCell(cellHeader);
            table.addCell(barcode);

            cellHeader.setPhrase(new Phrase("Quantity Qual A", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whShipping.getPcbAQty() + " PCB Panel(s)", fontContent));
            table.addCell(cellContent);

            cellHeader.setPhrase(new Phrase("Quantity Qual B", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whShipping.getPcbBQty() + " PCB Panel(s)", fontContent));
            table.addCell(cellContent);

            cellHeader.setPhrase(new Phrase("Quantity Qual C", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whShipping.getPcbCQty() + " PCB Panel(s)", fontContent));
            table.addCell(cellContent);

            cellHeader.setPhrase(new Phrase("Quantity CONTROL", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whShipping.getPcbCtrQty() + " PCB Panel(s)", fontContent));
            table.addCell(cellContent);

            cellHeader.setPhrase(new Phrase("Total Quantity", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whShipping.getRequestQuantity() + " PCB Panel(s)", fontContent));
            table.addCell(cellContent);

        } else {
            cellHeader.setPhrase(new Phrase("Equipment ID", fontHeader));
            table.addCell(cellHeader);
//            cellContent.setPhrase(new Phrase(whShipping.getRequestEquipmentId(), fontContent));
//            table.addCell(cellContent);
            table.addCell(barcode);
        }

        cellHeader.setPhrase(new Phrase("Material Pass No.", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(whShipping.getMpNo(), fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Requested By", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(whShipping.getRequestRequestedBy(), fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Requested Date", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(whShipping.getRequestViewRequestedDate(), fontContent));
        table.addCell(cellContent);

        doc.add(table);

//        doc.add(Chunk.NEWLINE);
//
//        PdfPTable table1 = new PdfPTable(1);
//        table1.setWidthPercentage(100.0f);
//        
//
//        PdfContentByte cb1 = writer.getDirectContent();
//        Barcode128 code128test = new Barcode128();
//        code128test.setGenerateChecksum(true);
//        code128test.setCode(whShipping.getRequestEquipmentId());
//        Image code128Imagetest = code128test.createImageWithBarcode(cb1, null, null);
//        PdfPCell barcode1 = new PdfPCell(code128Imagetest);
//        table1.addCell(barcode1).setBorder(Rectangle.NO_BORDER);
//        doc.add(table1);
        
    }
}
