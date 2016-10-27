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

public class WhShippingPdf extends AbstractITextPdfViewPotrait {

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
        table.setWidths(new float[]{1.2f, 4.0f});
        table.setSpacingBefore(20);

        Font fontHeader = fontOpenSans(9f, Font.BOLD);
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
        PdfPCell barcode = new PdfPCell(code128Image);
//            cell.setBorder(Rectangle.NO_BORDER);
        barcode.setPaddingLeft(8.0f);
        barcode.setPaddingTop(5.0f);

        whShipping = (WhShipping) model.get("whShipping");

        if ("Motherboard".equals(whShipping.getRequestEquipmentType())) {
            cellHeader.setPhrase(new Phrase("Motherboard ID", fontHeader));
            table.addCell(cellHeader);
            table.addCell(barcode);

            cellHeader.setPhrase(new Phrase("Quantity", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whShipping.getRequestQuantity(), fontContent));
            table.addCell(cellContent);
        } else if ("Stencil".equals(whShipping.getRequestEquipmentType())) {
            cellHeader.setPhrase(new Phrase("Stencil ID", fontHeader));
            table.addCell(cellHeader);
            table.addCell(barcode);

            cellHeader.setPhrase(new Phrase("Quantity", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whShipping.getRequestQuantity(), fontContent));
            table.addCell(cellContent);
        } else if ("Tray".equals(whShipping.getRequestEquipmentType())) {
            cellHeader.setPhrase(new Phrase("Tray ID", fontHeader));
            table.addCell(cellHeader);
            table.addCell(barcode);

            cellHeader.setPhrase(new Phrase("Quantity", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whShipping.getRequestQuantity(), fontContent));
            table.addCell(cellContent);

        } else if ("PCB".equals(whShipping.getRequestEquipmentType())) {
            cellHeader.setPhrase(new Phrase("PCB ID", fontHeader));
            table.addCell(cellHeader);
            table.addCell(barcode);

            cellHeader.setPhrase(new Phrase("Quantity Qual A", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whShipping.getPcbAQty(), fontContent));
            table.addCell(cellContent);

            cellHeader.setPhrase(new Phrase("Quantity Qual B", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whShipping.getPcbBQty(), fontContent));
            table.addCell(cellContent);

            cellHeader.setPhrase(new Phrase("Quantity Qual C", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whShipping.getPcbCQty(), fontContent));
            table.addCell(cellContent);

            cellHeader.setPhrase(new Phrase("Quantity CONTROL", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whShipping.getPcbCtrQty(), fontContent));
            table.addCell(cellContent);

            cellHeader.setPhrase(new Phrase("Total Quantity", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whShipping.getRequestQuantity(), fontContent));
            table.addCell(cellContent);

        } else {
            cellHeader.setPhrase(new Phrase("Equipment ID", fontHeader));
            table.addCell(cellHeader);
//            cellContent.setPhrase(new Phrase(whShipping.getRequestEquipmentId(), fontContent));
//            table.addCell(cellContent);
            table.addCell(barcode);
        }

//        cellHeader.setPhrase(new Phrase("Quantity", fontHeader));
//        table.addCell(cellHeader);
//        cellContent.setPhrase(new Phrase(whShipping.getRequestQuantity(), fontContent));
//        table.addCell(cellContent);
        cellHeader.setPhrase(new Phrase("Material Pass Number", fontHeader));
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

    }
}
