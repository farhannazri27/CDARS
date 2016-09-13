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
import com.onsemi.cdars.model.WhInventory;
import com.onsemi.cdars.pdf.AbstractITextPdfViewPotrait;

public class WhInventoryPdf extends AbstractITextPdfViewPotrait {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc,
            PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        String title = "INVENTORY INFORMATION";

        Paragraph viewTitle = new Paragraph(title, fontOpenSans(10f, Font.BOLD));
        viewTitle.setAlignment(Element.ALIGN_CENTER);

        doc.add(viewTitle);

        Integer cellPadding = 5;

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[]{2.0f, 4.0f});
        table.setSpacingBefore(20);

        Font fontHeader = fontOpenSans(9f, Font.BOLD);
        fontHeader.setColor(BaseColor.WHITE);

        PdfPCell cellHeader = new PdfPCell();
        cellHeader.setBackgroundColor(BaseColor.DARK_GRAY);
        cellHeader.setPadding(cellPadding);

        Font fontContent = fontOpenSans();

        PdfPCell cellContent = new PdfPCell();
        cellContent.setPadding(cellPadding);

        WhInventory whInventory = (WhInventory) model.get("whInventory");

        cellHeader.setPhrase(new Phrase("Hardware Type", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(whInventory.getEquipmentType(), fontContent));
        table.addCell(cellContent);

        if ("Motherboard".equals(whInventory.getEquipmentType())) {
            cellHeader.setPhrase(new Phrase("Motherboard ID", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whInventory.getEquipmentId(), fontContent));
            table.addCell(cellContent);
        } else if ("Stencil".equals(whInventory.getEquipmentType())) {
            cellHeader.setPhrase(new Phrase("Stencil ID", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whInventory.getEquipmentId(), fontContent));
            table.addCell(cellContent);
        } else if ("Tray".equals(whInventory.getEquipmentType())) {
            cellHeader.setPhrase(new Phrase("Tray Type", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whInventory.getEquipmentId(), fontContent));
            table.addCell(cellContent);

        } else if ("PCB".equals(whInventory.getEquipmentType())) {
            cellHeader.setPhrase(new Phrase("PCB Name", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whInventory.getEquipmentId(), fontContent));
            table.addCell(cellContent);

        } else {
            cellHeader.setPhrase(new Phrase("Equipment ID", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whInventory.getEquipmentId(), fontContent));
            table.addCell(cellContent);
        }
        
        cellHeader.setPhrase(new Phrase("Quantity", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(whInventory.getQuantity(), fontContent));
        table.addCell(cellContent);
        
        cellHeader.setPhrase(new Phrase("Material Pass No.", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(whInventory.getMpNo(), fontContent));
        table.addCell(cellContent);
        
        cellHeader.setPhrase(new Phrase("Material Pass Expiry Date", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(whInventory.getViewMpExpiryDate(), fontContent));
        table.addCell(cellContent);
        
        cellHeader.setPhrase(new Phrase("Location", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(whInventory.getInventoryLocation(), fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Inventory Date", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(whInventory.getViewInventoryDate(), fontContent));
        table.addCell(cellContent);
        
        cellHeader.setPhrase(new Phrase("Inventory By", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(whInventory.getInventoryBy(), fontContent));
        table.addCell(cellContent);

        doc.add(table);

    }
}
