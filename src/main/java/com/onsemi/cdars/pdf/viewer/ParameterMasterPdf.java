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
import com.onsemi.cdars.model.ParameterMaster;
import com.onsemi.cdars.pdf.AbstractITextPdfViewPotrait;

public class ParameterMasterPdf extends AbstractITextPdfViewPotrait {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc,
            PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        String title = "Parameter Information";

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
        
        ParameterMaster parameterMaster = (ParameterMaster) model.get("parameterMaster");
        
        cellHeader.setPhrase(new Phrase("Code", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(parameterMaster.getCode(), fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Name", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(parameterMaster.getName(), fontContent));
        table.addCell(cellContent);
        
        cellHeader.setPhrase(new Phrase("Remarks", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(parameterMaster.getRemarks(), fontContent));
        table.addCell(cellContent);

        doc.add(table);
    }
}
