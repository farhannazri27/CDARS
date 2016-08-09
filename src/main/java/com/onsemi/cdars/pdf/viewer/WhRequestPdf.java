package com.onsemi.cdars.pdf.viewer;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.onsemi.cdars.model.WhRequest;
import com.onsemi.cdars.pdf.AbstractITextPdfViewPotrait;

public class WhRequestPdf extends AbstractITextPdfViewPotrait {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc,
            PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        String title = "WAREHOUSE MANAGEMENT - HARDWARE REQUEST INFORMATION";

        Paragraph viewTitle = new Paragraph(title, fontOpenSans(10f, Font.BOLD));
        viewTitle.setAlignment(Element.ALIGN_CENTER);

        doc.add(viewTitle);

        Integer cellPadding = 7;

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[]{3.0f, 4.0f});
        table.setSpacingBefore(20);

        Font fontHeader = fontOpenSans(9f, Font.BOLD);
        fontHeader.setColor(BaseColor.WHITE);

        PdfPCell cellHeader = new PdfPCell();
        cellHeader.setBackgroundColor(BaseColor.DARK_GRAY);
        cellHeader.setPadding(cellPadding);

        Font fontContent = fontOpenSans();

        PdfPCell cellContent = new PdfPCell();
        cellContent.setPadding(cellPadding);

        WhRequest whRequest = (WhRequest) model.get("whRequest");

        if ("Motherboard".equals(whRequest.getEquipmentType())) {
            cellHeader.setPhrase(new Phrase("Motherboard ID", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whRequest.getEquipmentId(), fontContent));
            table.addCell(cellContent);
        } else if ("Stencil".equals(whRequest.getEquipmentType())) {
            cellHeader.setPhrase(new Phrase("Stencil ID", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whRequest.getEquipmentId(), fontContent));
            table.addCell(cellContent);
        } else if ("Tray".equals(whRequest.getEquipmentType())) {
            cellHeader.setPhrase(new Phrase("Tray Type", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whRequest.getEquipmentId(), fontContent));
            table.addCell(cellContent);

            cellHeader.setPhrase(new Phrase("Quantity", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whRequest.getQuantity(), fontContent));
            table.addCell(cellContent);

        } else if ("PCB".equals(whRequest.getEquipmentType())) {
            cellHeader.setPhrase(new Phrase("PCB Name", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whRequest.getEquipmentId(), fontContent));
            table.addCell(cellContent);

            cellHeader.setPhrase(new Phrase("Type", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whRequest.getType(), fontContent));
            table.addCell(cellContent);

            cellHeader.setPhrase(new Phrase("Quantity", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whRequest.getQuantity(), fontContent));
            table.addCell(cellContent);
        } else {
            cellHeader.setPhrase(new Phrase("Equipment ID", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whRequest.getEquipmentId(), fontContent));
            table.addCell(cellContent);
        }

        cellHeader.setPhrase(new Phrase("Requested By", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(whRequest.getRequestedBy(), fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Requested Date", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(whRequest.getRequestedDateView(), fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Approved By", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(whRequest.getFinalApprovedBy(), fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Approval Status", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(whRequest.getStatus(), fontContent));
        table.addCell(cellContent);

        doc.add(table);
    }
}
