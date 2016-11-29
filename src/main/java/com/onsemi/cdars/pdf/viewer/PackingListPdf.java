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
import com.onsemi.cdars.controller.WhRequestController;
import com.onsemi.cdars.model.WhMpList;
import com.onsemi.cdars.pdf.AbstractITextPdfViewPotrait;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PackingListPdf extends AbstractITextPdfViewPotrait {
private static final Logger LOGGER = LoggerFactory.getLogger(PackingListPdf.class);
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc,
            PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        Date date = new Date();
        String todayDate = dateFormat.format(date);
        Paragraph viewTitle2 = new Paragraph("Generated Date : " + todayDate, fontOpenSans(6f, Font.NORMAL));
        viewTitle2.setAlignment(Element.ALIGN_RIGHT);
        doc.add(viewTitle2);
        
        String title = "\nHIMS RL Shipping List (Rel Lab to SBN Factory)";
        Paragraph viewTitle = new Paragraph(title, fontOpenSans(10f, Font.BOLD));
        viewTitle.setAlignment(Element.ALIGN_CENTER);
        doc.add(viewTitle);

        Integer cellPadding = 5;
        
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[]{0.5f, 1.5f, 2.0f, 1.5f, 3.5f, 0.5f});
        table.setSpacingBefore(20);
        
        Font fontHeader = fontOpenSans(7f, Font.BOLD);
        fontHeader.setColor(BaseColor.WHITE);
        PdfPCell cellHeader = new PdfPCell();
        cellHeader.setBackgroundColor(BaseColor.GRAY);
        cellHeader.setPadding(cellPadding);

        Font fontContent = fontOpenSans(7f, Font.NORMAL);
        PdfPCell cellContent = new PdfPCell();
        cellContent.setPadding(cellPadding);
        
        List<WhMpList> packingList = (List<WhMpList>) model.get("packingList");
        
        int i = 0;
        while(i<packingList.size()) {
            if(i==0) {
                //Header
                cellHeader.setPhrase(new Phrase("No", fontHeader));
                table.addCell(cellHeader);
                cellHeader.setPhrase(new Phrase("Material Pass No", fontHeader));
                table.addCell(cellHeader);
                cellHeader.setPhrase(new Phrase("MP Expiry Date", fontHeader));
                table.addCell(cellHeader);
                cellHeader.setPhrase(new Phrase("Hardware Type", fontHeader));
                table.addCell(cellHeader);
                cellHeader.setPhrase(new Phrase("Hardware ID", fontHeader));
                table.addCell(cellHeader);
                cellHeader.setPhrase(new Phrase("Qty", fontHeader));
                table.addCell(cellHeader);
            }
            cellContent.setPhrase(new Phrase(i+1 + "", fontContent));
            table.addCell(cellContent);
            cellContent.setPhrase(new Phrase(packingList.get(i).getMpNo(), fontContent));
            table.addCell(cellContent);
            cellContent.setPhrase(new Phrase(packingList.get(i).getViewMpExpiryDate(), fontContent));
            table.addCell(cellContent);
            cellContent.setPhrase(new Phrase(packingList.get(i).getHardwareType(), fontContent));
            table.addCell(cellContent);
            cellContent.setPhrase(new Phrase(packingList.get(i).getHardwareId(), fontContent));
            table.addCell(cellContent);
            cellContent.setPhrase(new Phrase(packingList.get(i).getQuantity(), fontContent));
            table.addCell(cellContent);
            i++;
        }        
        doc.add(table);     
    }
}
