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
import com.onsemi.cdars.model.WhRetrieval;
import com.onsemi.cdars.pdf.AbstractITextPdfViewPotrait;

public class WhRetrievalPdf extends AbstractITextPdfViewPotrait {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc,
            PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        String title = "RETRIEVAL INFORMATION";

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

        WhRetrieval whRetrieval = (WhRetrieval) model.get("whRetrieval");

        cellHeader.setPhrase(new Phrase("Hardware Type", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(whRetrieval.getHardwareType(), fontContent));
        table.addCell(cellContent);

        if ("Motherboard".equals(whRetrieval.getHardwareType())) {
            cellHeader.setPhrase(new Phrase("Motherboard ID", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whRetrieval.getHardwareId(), fontContent));
            table.addCell(cellContent);
        } else if ("Stencil".equals(whRetrieval.getHardwareType())) {
            cellHeader.setPhrase(new Phrase("Stencil ID", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whRetrieval.getHardwareId(), fontContent));
            table.addCell(cellContent);
        } else if ("Tray".equals(whRetrieval.getHardwareType())) {
            cellHeader.setPhrase(new Phrase("Tray Type", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whRetrieval.getHardwareId(), fontContent));
            table.addCell(cellContent);

        } else if ("PCB".equals(whRetrieval.getHardwareType())) {
            cellHeader.setPhrase(new Phrase("PCB Name", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whRetrieval.getHardwareId(), fontContent));
            table.addCell(cellContent);

        } else if ("Load Card".equals(whRetrieval.getHardwareType())) {
            cellHeader.setPhrase(new Phrase("Load Card ID", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whRetrieval.getLoadCard(), fontContent));
            table.addCell(cellContent);


        } else if ("Program Card".equals(whRetrieval.getHardwareType())) {
            cellHeader.setPhrase(new Phrase("Program Card ID", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whRetrieval.getProgramCard(), fontContent));
            table.addCell(cellContent);


        } else if ("Load Card & Program Card".equals(whRetrieval.getHardwareType())) {
            cellHeader.setPhrase(new Phrase("Load Card ID", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whRetrieval.getLoadCard(), fontContent));
            table.addCell(cellContent);

            cellHeader.setPhrase(new Phrase("Load Card Quantity", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whRetrieval.getLoadCardQty(), fontContent));
            table.addCell(cellContent);

            cellHeader.setPhrase(new Phrase("Program Card ID", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whRetrieval.getProgramCard(), fontContent));
            table.addCell(cellContent);

            cellHeader.setPhrase(new Phrase("Program Card Quantity", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whRetrieval.getProgramCardQty(), fontContent));
            table.addCell(cellContent);

        } else {
            cellHeader.setPhrase(new Phrase("Equipment ID", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whRetrieval.getHardwareId(), fontContent));
            table.addCell(cellContent);
        }

        cellHeader.setPhrase(new Phrase("Quantity", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(whRetrieval.getHardwareQty(), fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Reason for Retrieval", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(whRetrieval.getRetrievalReason(), fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Material Pass No.", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(whRetrieval.getMpNo(), fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Material Pass Expiry Date", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(whRetrieval.getViewMpExpiryDate(), fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Rack", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(whRetrieval.getRack(), fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Shelf", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(whRetrieval.getShelf(), fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Requested By", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(whRetrieval.getRequestedBy(), fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Requested Date", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(whRetrieval.getRequestedDate(), fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Remarks", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(whRetrieval.getRemarks(), fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Status", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(whRetrieval.getStatus(), fontContent));
        table.addCell(cellContent);

        doc.add(table);

    }
}
