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
import com.onsemi.cdars.model.HardwareRequest;
import com.onsemi.cdars.pdf.AbstractITextPdfViewPotrait;

public class HardwareRequestPdf extends AbstractITextPdfViewPotrait {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc,
            PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        String title = "HARDWARE REQUEST INFORMATION";

        Paragraph viewTitle = new Paragraph(title, fontOpenSans(10f, Font.BOLD));
        viewTitle.setAlignment(Element.ALIGN_CENTER);

        doc.add(viewTitle);
        
        Integer cellPadding = 5;

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
        
        HardwareRequest hardwareRequest = (HardwareRequest) model.get("hardwareRequest");
        
        cellHeader.setPhrase(new Phrase("RMS", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(hardwareRequest.getRms(), fontContent));
        table.addCell(cellContent);
        
        cellHeader.setPhrase(new Phrase("Event", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(hardwareRequest.getEventCode(), fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Process Step", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(hardwareRequest.getProcess(), fontContent));
        table.addCell(cellContent);
        
        cellHeader.setPhrase(new Phrase("Forecast 1st Readout Start", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(hardwareRequest.getForecastDateView(), fontContent));
        table.addCell(cellContent);
        
        cellHeader.setPhrase(new Phrase("Status", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(hardwareRequest.getStatus(), fontContent));
        table.addCell(cellContent);

        doc.add(table);
    }
}
