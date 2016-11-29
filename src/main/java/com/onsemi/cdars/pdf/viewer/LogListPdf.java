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
import com.onsemi.cdars.model.WhStatusLog;
import com.onsemi.cdars.pdf.AbstractITextPdfViewPotrait;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogListPdf extends AbstractITextPdfViewPotrait {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogListPdf.class);

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc,
            PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        WhRequest whRequest = (WhRequest) model.get("whRequest");

        if ("Motherboard".equals(whRequest.getEquipmentType())) {
            String title = "MOTHERBOARD LOG INFORMATION";
            Paragraph viewTitle = new Paragraph(title, fontOpenSans(10f, Font.BOLD));
            viewTitle.setAlignment(Element.ALIGN_CENTER);

            doc.add(viewTitle);
        } else if ("Stencil".equals(whRequest.getEquipmentType())) {
            String title = "STENCIL LOG INFORMATION";
            Paragraph viewTitle = new Paragraph(title, fontOpenSans(10f, Font.BOLD));
            viewTitle.setAlignment(Element.ALIGN_CENTER);

            doc.add(viewTitle);
        } else if ("Tray".equals(whRequest.getEquipmentType())) {
            String title = "TRAY LOG INFORMATION";
            Paragraph viewTitle = new Paragraph(title, fontOpenSans(10f, Font.BOLD));
            viewTitle.setAlignment(Element.ALIGN_CENTER);

            doc.add(viewTitle);
        } else if ("PCB".equals(whRequest.getEquipmentType())) {
            String title = "PCB LOG INFORMATION";
            Paragraph viewTitle = new Paragraph(title, fontOpenSans(10f, Font.BOLD));
            viewTitle.setAlignment(Element.ALIGN_CENTER);

            doc.add(viewTitle);
        } else {
            String title = "HARDWARE LOG INFORMATION";
            Paragraph viewTitle = new Paragraph(title, fontOpenSans(10f, Font.BOLD));
            viewTitle.setAlignment(Element.ALIGN_CENTER);

            doc.add(viewTitle);
        }

        Integer cellPadding = 7;

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[]{2.0f, 4.0f});
        table.setSpacingBefore(20);

        PdfPTable table2 = new PdfPTable(4);
        table2.setWidthPercentage(100.0f);
        table2.setWidths(new float[]{4.0f, 2.0f, 3.0f, 3.0f});
        table2.setSpacingBefore(20);

        PdfPTable table3 = new PdfPTable(3);
        table3.setWidthPercentage(100.0f);
        table3.setWidths(new float[]{2.5f, 2.5f, 2.5f});
        table3.setSpacingBefore(15);

        PdfPTable table4 = new PdfPTable(3);
        table4.setWidthPercentage(100.0f);
        table4.setWidths(new float[]{2.5f, 2.5f, 2.5f});
        table4.setSpacingBefore(15);

        PdfPTable table5 = new PdfPTable(3);
        table5.setWidthPercentage(100.0f);
        table5.setWidths(new float[]{2.5f, 2.5f, 2.5f});
        table5.setSpacingBefore(15);

        PdfPTable table6 = new PdfPTable(4);
        table6.setWidthPercentage(100.0f);
        table6.setWidths(new float[]{2.0f, 2.0f, 2.0f, 2.0f});
        table6.setSpacingBefore(15);

        PdfPTable table7 = new PdfPTable(3);
        table7.setWidthPercentage(100.0f);
        table7.setWidths(new float[]{2.5f, 2.5f, 2.5f});
        table7.setSpacingBefore(15);

        PdfPTable table8 = new PdfPTable(3);
        table8.setWidthPercentage(100.0f);
        table8.setWidths(new float[]{2.5f, 2.5f, 2.5f});
        table8.setSpacingBefore(15);

        Font fontHeader = fontOpenSans(9f, Font.BOLD);
        fontHeader.setColor(BaseColor.WHITE);

        Font fontHeader3 = fontOpenSans(7f, Font.BOLD);
        fontHeader3.setColor(BaseColor.WHITE);
        PdfPCell cellHeader3 = new PdfPCell();
        cellHeader3.setBackgroundColor(BaseColor.GRAY);
        cellHeader3.setPadding(cellPadding);

        Font fontContent3 = fontOpenSans(6.5f, Font.NORMAL);
        PdfPCell cellContent3 = new PdfPCell();
        cellContent3.setPadding(cellPadding);

        PdfPCell cellHeader = new PdfPCell();
        cellHeader.setBackgroundColor(BaseColor.DARK_GRAY);
        cellHeader.setPadding(cellPadding);

        Font fontContent = fontOpenSans();

        PdfPCell cellContent = new PdfPCell();
        cellContent.setPadding(cellPadding);

        whRequest = (WhRequest) model.get("whRequest");

        cellHeader.setPhrase(new Phrase("Material Pass Number", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(whRequest.getMpNo(), fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Material Pass Expiry Date", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(whRequest.getMpExpiryDateView(), fontContent));
        table.addCell(cellContent);

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
        } else if ("PCB".equals(whRequest.getEquipmentType())) {
            cellHeader.setPhrase(new Phrase("PCB ID", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whRequest.getEquipmentId(), fontContent));
            table.addCell(cellContent);
            cellHeader.setPhrase(new Phrase("PCB ID Qual A", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whRequest.getPcbA(), fontContent));
            table.addCell(cellContent);
            cellHeader.setPhrase(new Phrase("Quantity Qual A", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whRequest.getPcbAQty(), fontContent));
            table.addCell(cellContent);
            cellHeader.setPhrase(new Phrase("PCB ID Qual B", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whRequest.getPcbB(), fontContent));
            table.addCell(cellContent);
            cellHeader.setPhrase(new Phrase("Quantity Qual B", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whRequest.getPcbBQty(), fontContent));
            table.addCell(cellContent);
            cellHeader.setPhrase(new Phrase("PCB ID Qual C", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whRequest.getPcbC(), fontContent));
            table.addCell(cellContent);
            cellHeader.setPhrase(new Phrase("Quantity Qual C", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whRequest.getPcbCQty(), fontContent));
            table.addCell(cellContent);
            cellHeader.setPhrase(new Phrase("PCB ID Control", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whRequest.getPcbCtr(), fontContent));
            table.addCell(cellContent);
            cellHeader.setPhrase(new Phrase("Quantity Control", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whRequest.getPcbCtrQty(), fontContent));
            table.addCell(cellContent);
        } else {
            cellHeader.setPhrase(new Phrase("Equipment ID", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whRequest.getEquipmentId(), fontContent));
            table.addCell(cellContent);
        }

        cellHeader.setPhrase(new Phrase("Total Quantity", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(whRequest.getQuantity(), fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Requested By", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(whRequest.getRequestedBy(), fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Requested Date", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(whRequest.getRequestedDateView(), fontContent));
        table.addCell(cellContent);
        if ("Ship".equals(whRequest.getRequestType())) {
            cellHeader.setPhrase(new Phrase("Approved By", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whRequest.getFinalApprovedBy(), fontContent));
            table.addCell(cellContent);
        } else {
            cellHeader.setPhrase(new Phrase("Reason for Retrieval", fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase(whRequest.getRetrievalReason(), fontContent));
            table.addCell(cellContent);
        }
        cellHeader.setPhrase(new Phrase("Status", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(whRequest.getStatus(), fontContent));
        table.addCell(cellContent);

        doc.add(table);

        /* START TABLE TIMELAPSE */
        String title2 = "\n\n\n";
        Paragraph viewTitle2 = new Paragraph(title2, fontOpenSans(8f, Font.BOLD));
        viewTitle2.setAlignment(Element.ALIGN_LEFT);
        doc.add(viewTitle2);

        // Timelapse
        WhStatusLog whStaShipReToMp = (WhStatusLog) model.get("firstShipReqToMpCreatedTL");
        WhStatusLog whStaMpToInv = (WhStatusLog) model.get("firstShipMpCreatedToInventoryTL");
        WhStatusLog countReIdShip = (WhStatusLog) model.get("countReIdShip");
        WhStatusLog whretrievalTL = (WhStatusLog) model.get("retrievalTL");
        WhStatusLog countReIdRetrieval = (WhStatusLog) model.get("countReIdRetrieval");
        WhStatusLog countShipRequestIdAtRetrieval = (WhStatusLog) model.get("countShipRequestIdAtRetrieval");
        WhStatusLog whRetrieveShp1 = (WhStatusLog) model.get("retrievalShipReqToMpCreatedTL");
        WhStatusLog whRetreiveShp2 = (WhStatusLog) model.get("retrievalShipMpCreatedToInventoryTL");

        if ("Ship".equals(whRequest.getRequestType())) {
            String title3 = "SHIPPING TIMELAPSE";
            Paragraph viewTitle3 = new Paragraph(title3, fontOpenSans(8f, Font.BOLD));
            viewTitle3.setAlignment(Element.ALIGN_LEFT);
            doc.add(viewTitle3);
            cellHeader3.setPhrase(new Phrase("Request Time - Approval Time", fontHeader3));
            table3.addCell(cellHeader3);
            cellHeader3.setPhrase(new Phrase("Approval Time - Material Pass Created Time", fontHeader3));
            table3.addCell(cellHeader3);
            cellHeader3.setPhrase(new Phrase("Material Pass Created Time - Trip Ticket Scan Time", fontHeader3));
            table3.addCell(cellHeader3);
            if (whStaShipReToMp.getRequestToApprove() != null || !"".equals(whStaShipReToMp.getRequestToApprove())) {
                cellContent.setPhrase(new Phrase(whStaShipReToMp.getRequestToApprove(), fontContent3));
                table3.addCell(cellContent);
            } else {
                cellContent.setPhrase(new Phrase("-", fontContent3));
                table3.addCell(cellContent);
            }

            if (whStaShipReToMp.getApproveToMPCreated() != null) {
                cellContent.setPhrase(new Phrase(whStaShipReToMp.getApproveToMPCreated(), fontContent3));
                table3.addCell(cellContent);
            } else {
                cellContent.setPhrase(new Phrase("-", fontContent3));
                table3.addCell(cellContent);
            }

            if (!"0".equals(countReIdShip.getCount())) {
                if (whStaMpToInv.getMpCreatedToTtScan() != null) {
                    cellContent.setPhrase(new Phrase(whStaMpToInv.getMpCreatedToTtScan(), fontContent3));
                    table3.addCell(cellContent);
                } else {
                    cellContent.setPhrase(new Phrase("-", fontContent3));
                    table3.addCell(cellContent);
                }
            } else {
                cellContent.setPhrase(new Phrase("-", fontContent3));
                table3.addCell(cellContent);
            }

            doc.add(table3);

            String title4 = "\n\n";
            Paragraph viewTitle4 = new Paragraph(title4, fontOpenSans(8f, Font.BOLD));
            viewTitle4.setAlignment(Element.ALIGN_LEFT);
            doc.add(viewTitle4);

            cellHeader3.setPhrase(new Phrase("Trip Ticket Scan Time - Barcode Scan Time", fontHeader3));
            table5.addCell(cellHeader3);
            cellHeader3.setPhrase(new Phrase("Barcode Scan Time - Shipping Time", fontHeader3));
            table5.addCell(cellHeader3);
            cellHeader3.setPhrase(new Phrase("Shipping Time - Inventory Time", fontHeader3));
            table5.addCell(cellHeader3);
            if (!"0".equals(countReIdShip.getCount())) {
                if (whStaMpToInv.getTtScanToBsScan() != null) {
                    cellContent.setPhrase(new Phrase(whStaMpToInv.getTtScanToBsScan(), fontContent3));
                    table5.addCell(cellContent);
                } else {
                    cellContent.setPhrase(new Phrase("-", fontContent3));
                    table5.addCell(cellContent);
                }
            } else {
                cellContent.setPhrase(new Phrase("-", fontContent3));
                table5.addCell(cellContent);
            }

            if (!"0".equals(countReIdShip.getCount())) {
                if (whStaMpToInv.getBsScanToShip() != null) {
                    cellContent.setPhrase(new Phrase(whStaMpToInv.getBsScanToShip(), fontContent3));
                    table5.addCell(cellContent);
                } else {
                    cellContent.setPhrase(new Phrase("-", fontContent3));
                    table5.addCell(cellContent);
                }
            } else {
                cellContent.setPhrase(new Phrase("-", fontContent3));
                table5.addCell(cellContent);
            }

            if (!"0".equals(countReIdShip.getCount())) {
                if (whStaMpToInv.getShipToInventory() != null) {
                    cellContent.setPhrase(new Phrase(whStaMpToInv.getShipToInventory(), fontContent3));
                    table5.addCell(cellContent);
                } else {
                    cellContent.setPhrase(new Phrase("-", fontContent3));
                    table5.addCell(cellContent);
                }
            } else {
                cellContent.setPhrase(new Phrase("-", fontContent3));
                table5.addCell(cellContent);
            }

            doc.add(table5);
        } else if ("Retrieve".equals(whRequest.getRequestType())) {

            //shipping timelapse for retrieval request
            String title3 = "SHIPPING TIMELAPSE";
            Paragraph viewTitle3 = new Paragraph(title3, fontOpenSans(8f, Font.BOLD));
            viewTitle3.setAlignment(Element.ALIGN_LEFT);
            doc.add(viewTitle3);
            cellHeader3.setPhrase(new Phrase("Request Time - Approval Time", fontHeader3));
            table3.addCell(cellHeader3);
            cellHeader3.setPhrase(new Phrase("Approval Time - Material Pass Created Time", fontHeader3));
            table3.addCell(cellHeader3);
            cellHeader3.setPhrase(new Phrase("Material Pass Created Time - Trip Ticket Scan Time", fontHeader3));
            table3.addCell(cellHeader3);
            if (!"0".equals(countShipRequestIdAtRetrieval.getCount())) {
                if (whRetrieveShp1.getRequestToApprove() != null || !"".equals(whRetrieveShp1.getRequestToApprove())) {
                    cellContent.setPhrase(new Phrase(whRetrieveShp1.getRequestToApprove(), fontContent3));
                    table3.addCell(cellContent);
                } else {
                    cellContent.setPhrase(new Phrase("-", fontContent3));
                    table3.addCell(cellContent);
                }
            } else {
                cellContent.setPhrase(new Phrase("-", fontContent3));
                table3.addCell(cellContent);
            }
            if (!"0".equals(countShipRequestIdAtRetrieval.getCount())) {
                if (whRetrieveShp1.getApproveToMPCreated() != null) {
                    cellContent.setPhrase(new Phrase(whRetrieveShp1.getApproveToMPCreated(), fontContent3));
                    table3.addCell(cellContent);
                } else {
                    cellContent.setPhrase(new Phrase("-", fontContent3));
                    table3.addCell(cellContent);
                }
            } else {
                cellContent.setPhrase(new Phrase("-", fontContent3));
                table3.addCell(cellContent);
            }

            if (!"0".equals(countShipRequestIdAtRetrieval.getCount())) {
                if (whRetreiveShp2.getMpCreatedToTtScan() != null) {
                    cellContent.setPhrase(new Phrase(whRetreiveShp2.getMpCreatedToTtScan(), fontContent3));
                    table3.addCell(cellContent);
                } else {
                    cellContent.setPhrase(new Phrase("-", fontContent3));
                    table3.addCell(cellContent);
                }
            } else {
                cellContent.setPhrase(new Phrase("-", fontContent3));
                table3.addCell(cellContent);
            }

            doc.add(table3);

            String title4 = "\n\n";
            Paragraph viewTitle4 = new Paragraph(title4, fontOpenSans(8f, Font.BOLD));
            viewTitle4.setAlignment(Element.ALIGN_LEFT);
            doc.add(viewTitle4);

            cellHeader3.setPhrase(new Phrase("Trip Ticket Scan Time - Barcode Scan Time", fontHeader3));
            table5.addCell(cellHeader3);
            cellHeader3.setPhrase(new Phrase("Barcode Scan Time - Shipping Time", fontHeader3));
            table5.addCell(cellHeader3);
            cellHeader3.setPhrase(new Phrase("Shipping Time - Inventory Time", fontHeader3));
            table5.addCell(cellHeader3);
            if (!"0".equals(countShipRequestIdAtRetrieval.getCount())) {
                if (whRetreiveShp2.getTtScanToBsScan() != null) {
                    cellContent.setPhrase(new Phrase(whRetreiveShp2.getTtScanToBsScan(), fontContent3));
                    table5.addCell(cellContent);
                } else {
                    cellContent.setPhrase(new Phrase("-", fontContent3));
                    table5.addCell(cellContent);
                }
            } else {
                cellContent.setPhrase(new Phrase("-", fontContent3));
                table5.addCell(cellContent);
            }

            if (!"0".equals(countShipRequestIdAtRetrieval.getCount())) {
                if (whRetreiveShp2.getBsScanToShip() != null) {
                    cellContent.setPhrase(new Phrase(whRetreiveShp2.getBsScanToShip(), fontContent3));
                    table5.addCell(cellContent);
                } else {
                    cellContent.setPhrase(new Phrase("-", fontContent3));
                    table5.addCell(cellContent);
                }
            } else {
                cellContent.setPhrase(new Phrase("-", fontContent3));
                table5.addCell(cellContent);
            }

            if (!"0".equals(countShipRequestIdAtRetrieval.getCount())) {
                if (whRetreiveShp2.getShipToInventory() != null) {
                    cellContent.setPhrase(new Phrase(whRetreiveShp2.getShipToInventory(), fontContent3));
                    table5.addCell(cellContent);
                } else {
                    cellContent.setPhrase(new Phrase("-", fontContent3));
                    table5.addCell(cellContent);
                }
            } else {
                cellContent.setPhrase(new Phrase("-", fontContent3));
                table5.addCell(cellContent);
            }

            doc.add(table5);

            String title5 = "\n\nRETRIEVAL TIMELAPSE";
            Paragraph viewTitle5 = new Paragraph(title5, fontOpenSans(8f, Font.BOLD));
            viewTitle5.setAlignment(Element.ALIGN_LEFT);
            doc.add(viewTitle5);
            cellHeader3.setPhrase(new Phrase("Request Time - Barcode Verification Time", fontHeader3));
            table6.addCell(cellHeader3);
            cellHeader3.setPhrase(new Phrase("Barcode Verification (SF) Time - Shipment Time", fontHeader3));
            table6.addCell(cellHeader3);
            cellHeader3.setPhrase(new Phrase("Shipment Time - Barcode Verification (RL) Time", fontHeader3));
            table6.addCell(cellHeader3);
            cellHeader3.setPhrase(new Phrase("Barcode Verification (RL) Time - Trip Ticket Verification (RL) Time", fontHeader3));
            table6.addCell(cellHeader3);

            if (whretrievalTL.getRequestToVerifiedDate() != null || !"".equals(whretrievalTL.getRequestToVerifiedDate())) {
                cellContent.setPhrase(new Phrase(whretrievalTL.getRequestToVerifiedDate(), fontContent3));
                table6.addCell(cellContent);
            } else {
                cellContent.setPhrase(new Phrase("-", fontContent3));
                table6.addCell(cellContent);
            }
            if (whretrievalTL.getVerifiedDatetoShipDate() != null || !"".equals(whretrievalTL.getVerifiedDatetoShipDate())) {
                cellContent.setPhrase(new Phrase(whretrievalTL.getVerifiedDatetoShipDate(), fontContent3));
                table6.addCell(cellContent);
            } else {
                cellContent.setPhrase(new Phrase("-", fontContent3));
                table6.addCell(cellContent);
            }
            if (whretrievalTL.getShipDateToBsScan() != null || !"".equals(whretrievalTL.getShipDateToBsScan())) {
                cellContent.setPhrase(new Phrase(whretrievalTL.getShipDateToBsScan(), fontContent3));
                table6.addCell(cellContent);
            } else {
                cellContent.setPhrase(new Phrase("-", fontContent3));
                table6.addCell(cellContent);
            }
            if (whretrievalTL.getBsScanToTtScan() != null || !"".equals(whretrievalTL.getBsScanToTtScan())) {
                cellContent.setPhrase(new Phrase(whretrievalTL.getBsScanToTtScan(), fontContent3));
                table6.addCell(cellContent);
            } else {
                cellContent.setPhrase(new Phrase("-", fontContent3));
                table6.addCell(cellContent);
            }
            doc.add(table6);
        }

        String title5 = "\n\n";
        Paragraph viewTitle5 = new Paragraph(title5, fontOpenSans(8f, Font.BOLD));
        viewTitle5.setAlignment(Element.ALIGN_LEFT);
        doc.add(viewTitle5);
        String title6 = "Log";
        Paragraph viewTitle6 = new Paragraph(title6, fontOpenSans(8f, Font.BOLD));
        viewTitle6.setAlignment(Element.ALIGN_LEFT);
        doc.add(viewTitle6);

        //Log
        List<WhStatusLog> test = (List<WhStatusLog>) model.get("Log");

        int i = 0;
        while (i < test.size()) {
            String moduleName = test.get(i).getModule();
            if (moduleName.equals("cdars_wh_request")) {
                moduleName = "Hardware Request";
            } else if (moduleName.equals("cdars_wh_shipping") || moduleName.equals("cdars_wh_mp_list")) {
                moduleName = "Hardware Shipment";
            } else if (moduleName.equals("cdars_wh_retrieval")) {
                moduleName = "Hardware Retrieval";
            } else if (moduleName.equals("cdars_wh_inventory")) {
                moduleName = "Hardware Inventory";
            }
            if (i == 0) {
                //Header
                cellHeader.setPhrase(new Phrase("Status", fontHeader));
                table2.addCell(cellHeader);
                cellHeader.setPhrase(new Phrase("Timestamp", fontHeader));
                table2.addCell(cellHeader);
                cellHeader.setPhrase(new Phrase("Module Stage", fontHeader));
                table2.addCell(cellHeader);
                cellHeader.setPhrase(new Phrase("Created by", fontHeader));
                table2.addCell(cellHeader);
            }
            cellContent.setPhrase(new Phrase(test.get(i).getStatus(), fontContent));
            table2.addCell(cellContent);
            cellContent.setPhrase(new Phrase(test.get(i).getStatusDate(), fontContent));
            table2.addCell(cellContent);
            cellContent.setPhrase(new Phrase(moduleName, fontContent));
            table2.addCell(cellContent);
            cellContent.setPhrase(new Phrase(test.get(i).getCreatedBy(), fontContent));
            table2.addCell(cellContent);

            i++;
        }
        List<WhStatusLog> testShip = (List<WhStatusLog>) model.get("ShipLog");
        int ii = 0;
        while (ii < testShip.size()) {

            String moduleNames = testShip.get(ii).getModule();
            if (moduleNames.equals("cdars_wh_request")) {
                moduleNames = "Hardware Request";
            } else if (moduleNames.equals("cdars_wh_shipping") || moduleNames.equals("cdars_wh_mp_list")) {
                moduleNames = "Hardware Shipment";
            } else if (moduleNames.equals("cdars_wh_retrieval")) {
                moduleNames = "Hardware Retrieval";
            } else if (moduleNames.equals("cdars_wh_inventory")) {
                moduleNames = "Hardware Inventory";
            }
            cellContent.setPhrase(new Phrase(testShip.get(ii).getStatus(), fontContent));
            table2.addCell(cellContent);
            cellContent.setPhrase(new Phrase(testShip.get(ii).getStatusDate(), fontContent));
            table2.addCell(cellContent);
            cellContent.setPhrase(new Phrase(moduleNames, fontContent));
            table2.addCell(cellContent);
            cellContent.setPhrase(new Phrase(testShip.get(ii).getCreatedBy(), fontContent));
            table2.addCell(cellContent);
            ii++;
        }

        doc.add(table2);
    }
}
