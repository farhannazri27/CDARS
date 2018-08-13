/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onsemi.cdars.config;

import com.onsemi.cdars.dao.WhUslReportDAO;
import com.onsemi.cdars.model.WhUslReport;
import java.time.Month;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.document.AbstractExcelView;

/**
 *
 * @author fg79cj
 */
public class ExcelBuilder extends AbstractExcelView {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelBuilder.class);

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
            HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // get data model which is passed by the Spring container
        Integer year = (Integer) model.get("year");
        Integer month = (Integer) model.get("month");
        String equipmentType = (String) model.get("equipmentType");
        String requestType = (String) model.get("requestType");

//surrogate of month and year for second loop
        Integer month2 = month;
        Integer year2 = year;

        Month monthHead = Month.of(month);
        String monthNameHeadFull = monthHead.name();

        String monthNameHead = monthNameHeadFull.substring(1, 3).toLowerCase();
        String monthNameHead2 = monthNameHeadFull.substring(0, 1);
        String todayDate = monthNameHead2 + monthNameHead + " " + year;

        if ("%%".equals(equipmentType)) {
            response.setHeader("Content-Disposition", "attachment; filename=\"SF and SBN Rel - Time Lapse Report Monthly Performance (" + todayDate + ").xls");
        } else {
            response.setHeader("Content-Disposition", "attachment; filename=\"SF and SBN Rel - Time Lapse Report Monthly Performance (" + todayDate + ") - " + equipmentType + ".xls");
        }

        // create a new Excel sheet
        HSSFSheet sheet = workbook.createSheet("SF and SBN Rel - Time Lapse Report Monthly Performance (" + todayDate + ").xlst");
        sheet.setDefaultColumnWidth(10);
        sheet.setDisplayGridlines(false);
//        sheet.setDefaultColumnWidth(30);

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBoldweight(HSSFFont.COLOR_NORMAL);
        font.setBold(true);
        font.setColor(HSSFColor.DARK_BLUE.index);
        style.setFont(font);
//        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);

        CellStyle styleBlueWithBorder = workbook.createCellStyle();
        Font fontBlue = workbook.createFont();
        fontBlue.setFontHeightInPoints((short) 10);
        fontBlue.setFontName(HSSFFont.FONT_ARIAL);
        fontBlue.setBoldweight(HSSFFont.COLOR_NORMAL);
        fontBlue.setBold(true);
        fontBlue.setColor(HSSFColor.DARK_BLUE.index);
        styleBlueWithBorder.setFont(fontBlue);
        styleBlueWithBorder.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        styleBlueWithBorder.setBorderRight(HSSFCellStyle.BORDER_THIN);
        styleBlueWithBorder.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styleBlueWithBorder.setBorderTop(HSSFCellStyle.BORDER_THIN);

        CellStyle styleWithBorder = workbook.createCellStyle();
        styleWithBorder.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        styleWithBorder.setBorderRight(HSSFCellStyle.BORDER_THIN);
        styleWithBorder.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styleWithBorder.setBorderTop(HSSFCellStyle.BORDER_THIN);

        CellStyle style2 = workbook.createCellStyle();
        Font font2 = workbook.createFont();
        font2.setFontHeightInPoints((short) 10);
        font2.setFontName(HSSFFont.FONT_ARIAL);
        font2.setBoldweight(HSSFFont.COLOR_NORMAL);
        font2.setBold(true);
        font2.setColor(HSSFColor.RED.index);
        style2.setFont(font2);

        CellStyle styleGreen = workbook.createCellStyle();
        Font fontGreen = workbook.createFont();
        fontGreen.setFontHeightInPoints((short) 10);
        fontGreen.setFontName(HSSFFont.FONT_ARIAL);
        fontGreen.setBoldweight(HSSFFont.COLOR_NORMAL);
        fontGreen.setBold(true);
        fontGreen.setColor(HSSFColor.GREEN.index);
        styleGreen.setFont(fontGreen);

        CellStyle styleRed = workbook.createCellStyle();
        Font fontRedRemark = workbook.createFont();
        fontRedRemark.setFontHeightInPoints((short) 9);
//        fontRedRemark.setColor(HSSFColor.RED.index);
        styleRed.setFont(fontRedRemark);

        CellStyle styleBlueandFillGrey = workbook.createCellStyle();
        Font fontBlueNGray = workbook.createFont();
        fontBlueNGray.setFontHeightInPoints((short) 10);
        fontBlueNGray.setFontName(HSSFFont.FONT_ARIAL);
        fontBlueNGray.setBoldweight(HSSFFont.COLOR_NORMAL);
        fontBlueNGray.setBold(true);
        fontBlueNGray.setColor(HSSFColor.BLACK.index);
        styleBlueandFillGrey.setFont(fontBlueNGray);
        styleBlueandFillGrey.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        styleBlueandFillGrey.setFillPattern(CellStyle.SOLID_FOREGROUND);
        styleBlueandFillGrey.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        styleBlueandFillGrey.setBorderRight(HSSFCellStyle.BORDER_THIN);
        styleBlueandFillGrey.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styleBlueandFillGrey.setBorderTop(HSSFCellStyle.BORDER_THIN);

        //create dynamic rownum
        Short rowNum = 0;

        HSSFRow rowtitle = sheet.createRow((short) rowNum);
//        rowtitle.setRowStyle(style);

        HSSFCell cellt_0 = rowtitle.createCell(0);
        cellt_0.setCellStyle(style);
        if ("%%".equals(equipmentType)) {
            cellt_0.setCellValue("SF and SBN Rel - Time Lapse Report Monthly Performance (" + todayDate + ")");
        } else {
            cellt_0.setCellValue("SF and SBN Rel - Time Lapse Report Monthly Performance (" + todayDate + ") - " + equipmentType + "");
        }

        //add 1 row to current rowNum
        rowNum = (short) (rowNum + 2);

        HSSFRow rowhead = sheet.createRow((short) rowNum);
//        rowhead.setRowStyle(style);

        HSSFCell cell1_0 = rowhead.createCell(0);
        cell1_0.setCellStyle(styleBlueWithBorder);
        cell1_0.setCellValue("Time Lapse Category");

        //add 1 row to current rowNum
        rowNum = (short) (rowNum + 1);

        HSSFRow rowforShiptoSF = sheet.createRow((short) rowNum);

        if ("Ship".equals(requestType) || "all".equals(requestType)) {
            //insert to first row for ship to SF
            HSSFCell celltitleShpToSf = rowforShiptoSF.createCell(0);
            celltitleShpToSf.setCellStyle(styleWithBorder);
            celltitleShpToSf.setCellValue("Rel Lab to Storage Factory");
        }

        if ("Ship".equals(requestType) || "all".equals(requestType)) {
//            //add 1 row to current rowNum
            rowNum = (short) (rowNum + 1);
        } else {
            rowNum = (short) (rowNum);
        }
        //insert to 2nd row for ship to RL
        HSSFRow rowforShiptoRL = sheet.createRow((short) rowNum);

        if ("Retrieve".equals(requestType) || "all".equals(requestType)) {

            HSSFCell celltitleShpToRL = rowforShiptoRL.createCell(0);
            celltitleShpToRL.setCellStyle(styleWithBorder);
            celltitleShpToRL.setCellValue("Storage Factory to Rel Lab");
        }

        HSSFFont fontRed = workbook.createFont();
        fontRed.setColor(HSSFColor.RED.index);

        Integer cellCol = 1;

        //loop for total activity
        for (int x = 1; x <= 12; x++) {
            if (month < 1) {
//                year = year - 1;
                year -= 1;
                month = 12;
            }

            Month monthHead1 = Month.of(month);
            String monthNameHeadFull1 = monthHead1.name();
            String monthName = monthNameHeadFull1.substring(0, 3);
            String year1 = year.toString().substring(2, 4);

            HSSFCell cell1_month = rowhead.createCell(cellCol);
            cell1_month.setCellStyle(style);
            cell1_month.setCellStyle(styleBlueWithBorder);
            cell1_month.setCellValue(monthName + "-" + year1);

            if ("Ship".equals(requestType) || "all".equals(requestType)) {
                WhUslReportDAO count = new WhUslReportDAO();
                Integer totalItemShip = count.getCountTotalShipByYearAndByMonthAndByEqptType(month.toString(), year.toString(), equipmentType);

                count = new WhUslReportDAO();
                Integer totalFailShip = count.getCountShipFailByYearAndByMonthAndByEqptType(month.toString(), year.toString(), equipmentType);

                HSSFRichTextString ship = new HSSFRichTextString(totalFailShip + "/" + totalItemShip);
                ship.applyFont(0, Integer.toString(totalFailShip).length(), fontRed);

                HSSFCell cellContentDec16 = rowforShiptoSF.createCell(cellCol);
                cellContentDec16.setCellStyle(styleWithBorder);
                cellContentDec16.setCellValue(ship);
            }

            if ("Retrieve".equals(requestType) || "all".equals(requestType)) {

                WhUslReportDAO count = new WhUslReportDAO();
                Integer totalRetrieveItem = count.getCountTotalRetrieveByYearAndByMonthAndByEqptType(month.toString(), year.toString(), equipmentType);

                count = new WhUslReportDAO();
                Integer totalRetrieveFail = count.getCountRetrieveFailByYearAndByMonthAndByEqptType(month.toString(), year.toString(), equipmentType);

                HSSFRichTextString retrieve = new HSSFRichTextString(totalRetrieveFail + "/" + totalRetrieveItem);
                retrieve.applyFont(0, Integer.toString(totalRetrieveFail).length(), fontRed);

                HSSFCell cellContentRetrieveDec16 = rowforShiptoRL.createCell(cellCol);
                cellContentRetrieveDec16.setCellStyle(styleWithBorder);
                cellContentRetrieveDec16.setCellValue(retrieve);

            }

            month -= 1;
            cellCol += 1;
        }

        //add 3 row to current rowNum
        rowNum = (short) (rowNum + 3);

        //remark
        HSSFRow rowforRemarks = sheet.createRow((short) rowNum);

        HSSFCell celltitleRemarks = rowforRemarks.createCell(0);
//        celltitleRemarks.setCellStyle(style);
        celltitleRemarks.setCellStyle(styleRed);
        celltitleRemarks.setCellValue("Remarks *");

        HSSFCell celltitleRemarksContent = rowforRemarks.createCell(1);
        celltitleRemarksContent.setCellStyle(styleRed);
        celltitleRemarksContent.setCellValue("- Number of items fail from total activity USL / number of items");

        //add 1 row to current rowNum
        rowNum = (short) (rowNum + 1);

        //remark
        HSSFRow rowforRemarks2 = sheet.createRow((short) rowNum);

        HSSFCell celltitleRemarksContent2 = rowforRemarks2.createCell(1);
        celltitleRemarksContent2.setCellStyle(styleRed);
        celltitleRemarksContent2.setCellValue("- Total USL for Rel Lab to Storage Factory = 120 hours");

        //add 1 row to current rowNum
        rowNum = (short) (rowNum + 1);

        //remark
        HSSFRow rowforRemarks3 = sheet.createRow((short) rowNum);

        HSSFCell celltitleRemarksContent3 = rowforRemarks3.createCell(1);
        celltitleRemarksContent3.setCellStyle(styleRed);
        celltitleRemarksContent3.setCellValue("- Total USL for Storage Factory to  Rel Lab = 96 hours");

        //add 2 row to current rowNum
        rowNum = (short) (rowNum + 2);

        //Title
        HSSFRow rowfortitle = sheet.createRow((short) rowNum);

        HSSFCell celltitle = rowfortitle.createCell(0);
        celltitle.setCellStyle(style);
        celltitle.setCellValue("List of Failed Items");

        Integer cellColumn = 0;
        Integer stop = 0;

        //loop for all item details in month
        for (int x = 12; x > 0 && stop == 0; x--) {

            if (month2 < 1) {
//                year2 = year2 - 1;
                year2 -= 1;
                month2 = 12;
                stop = 1;
            }

            if (month2 == 12) {
                stop = 1;
            }

            //insert activity failed
            String failSteps = "";
            String flag = "0";

            //add 2 row to current rowNum
            rowNum = (short) (rowNum + 2);

            Month monthc = Month.of(month2);
            String monthName = monthc.name();

            String yearSub = year2.toString().substring(2, 4);

            //Item fail header Dec 17
            HSSFRow rowforItemFailDec17 = sheet.createRow((short) rowNum);
            HSSFCell cell1Dec17 = rowforItemFailDec17.createCell(0);
            cell1Dec17.setCellStyle(style2);
            cell1Dec17.setCellValue(monthName + " " + yearSub);

            //add 1 row to current rowNum
            rowNum = (short) (rowNum + 1);

            if ("Ship".equals(requestType) || "all".equals(requestType)) {

                HSSFRow rowforItemFailDec17Ship = sheet.createRow((short) rowNum);
                HSSFCell cell1Dec17Fail = rowforItemFailDec17Ship.createCell(0);
                cell1Dec17Fail.setCellStyle(styleGreen);
                cell1Dec17Fail.setCellValue("Rel Lab to Storage Factory");

                //add 1 row to current rowNum
                rowNum = (short) (rowNum + 1);

                //Item fail details Dec 17
                HSSFRow rowforItemFailHeaderDec17 = sheet.createRow((short) rowNum);

                HSSFCell cell1ItemtypeDec17 = rowforItemFailHeaderDec17.createCell(0);
                cell1ItemtypeDec17.setCellStyle(styleBlueandFillGrey);
                cell1ItemtypeDec17.setCellValue("Item Type");

                HSSFCell cell1ItemIdDec17 = rowforItemFailHeaderDec17.createCell(1);
                cell1ItemIdDec17.setCellStyle(styleBlueandFillGrey);
                cell1ItemIdDec17.setCellValue("Item ID");

                HSSFCell cell1mpNoDec17 = rowforItemFailHeaderDec17.createCell(4);
                cell1mpNoDec17.setCellStyle(styleBlueandFillGrey);
                cell1mpNoDec17.setCellValue("Material Pass No");

                HSSFCell cell1DurationDec17 = rowforItemFailHeaderDec17.createCell(6);
                cell1DurationDec17.setCellStyle(styleBlueandFillGrey);
                cell1DurationDec17.setCellValue("Duration (hrs)");

                HSSFCell cell1FailedDec17 = rowforItemFailHeaderDec17.createCell(7);
                cell1FailedDec17.setCellStyle(styleBlueandFillGrey);
                cell1FailedDec17.setCellValue("Process Steps Over USL");

                HSSFCell cell1Rc = rowforItemFailHeaderDec17.createCell(10);
                cell1Rc.setCellStyle(styleBlueandFillGrey);
                cell1Rc.setCellValue("Root Cause");

                HSSFCell cell1Ca = rowforItemFailHeaderDec17.createCell(14);
                cell1Ca.setCellStyle(styleBlueandFillGrey);
                cell1Ca.setCellValue("Correlative Action");

                HSSFCell cell2mpNoDec17 = rowforItemFailHeaderDec17.createCell(2);
                cell2mpNoDec17.setCellStyle(styleBlueandFillGrey);

                HSSFCell cell4mpNoDec17 = rowforItemFailHeaderDec17.createCell(3);
                cell4mpNoDec17.setCellStyle(styleBlueandFillGrey);

                HSSFCell cell7mpNoDec17 = rowforItemFailHeaderDec17.createCell(5);
                cell7mpNoDec17.setCellStyle(styleBlueandFillGrey);

                HSSFCell cell8mpNoDec17 = rowforItemFailHeaderDec17.createCell(8);
                cell8mpNoDec17.setCellStyle(styleBlueandFillGrey);

                HSSFCell cell9mpNoDec17 = rowforItemFailHeaderDec17.createCell(9);
                cell9mpNoDec17.setCellStyle(styleBlueandFillGrey);

                HSSFCell cell11mpNoDec17 = rowforItemFailHeaderDec17.createCell(11);
                cell11mpNoDec17.setCellStyle(styleBlueandFillGrey);

                HSSFCell cell12mpNoDec17 = rowforItemFailHeaderDec17.createCell(12);
                cell12mpNoDec17.setCellStyle(styleBlueandFillGrey);

                HSSFCell cell13mpNoDec17 = rowforItemFailHeaderDec17.createCell(13);
                cell13mpNoDec17.setCellStyle(styleBlueandFillGrey);

                HSSFCell cell14mpNoDec17 = rowforItemFailHeaderDec17.createCell(15);
                cell14mpNoDec17.setCellStyle(styleBlueandFillGrey);

                HSSFCell cell15mpNoDec17 = rowforItemFailHeaderDec17.createCell(16);
                cell15mpNoDec17.setCellStyle(styleBlueandFillGrey);

                HSSFCell cell117mpNoDec17 = rowforItemFailHeaderDec17.createCell(17);
                cell117mpNoDec17.setCellStyle(styleBlueandFillGrey);

                WhUslReportDAO count = new WhUslReportDAO();
                Integer totalFailShip = count.getCountShipFailByYearAndByMonthAndByEqptType(month2.toString(), year2.toString(), equipmentType);

                if (totalFailShip > 0) {

                    WhUslReportDAO fail = new WhUslReportDAO();
//                    List<WhUslReport> failShipDetails = fail.GetListOfFailedShipItemByYearAndByMonthAndByEqptType(month2.toString(), year2.toString(), equipmentType);
                    List<WhUslReport> failShipDetails = fail.GetListOfFailedShipItemByYearAndByMonthAndByEqptTypeWithTimelapseTable(month2.toString(), year2.toString(), equipmentType);

                    for (int i = 0; i < failShipDetails.size(); i++) {
                        //add 1 row to current rowNum
                        rowNum = (short) (rowNum + 1);
                        //insert to failed item details for dec 17
                        HSSFRow rowforShiptoSFFail = sheet.createRow((short) rowNum);

                        HSSFCell celltitleShpToSFFailType = rowforShiptoSFFail.createCell(0);
                        celltitleShpToSFFailType.setCellStyle(styleWithBorder);
                        celltitleShpToSFFailType.setCellValue(failShipDetails.get(i).getEqptType());

                        HSSFCell celltitleShpToSFFailId = rowforShiptoSFFail.createCell(1);
                        celltitleShpToSFFailId.setCellStyle(styleWithBorder);
                        if ("Load Card".equals(failShipDetails.get(i).getEqptType())) {
                            celltitleShpToSFFailId.setCellValue(failShipDetails.get(i).getLoadCard());
                        } else if ("Program Card".equals(failShipDetails.get(i).getEqptType())) {
                            celltitleShpToSFFailId.setCellValue(failShipDetails.get(i).getProgramCard());
                        } else if ("Load Card & Program Card".equals(failShipDetails.get(i).getEqptType())) {
                            celltitleShpToSFFailId.setCellValue(failShipDetails.get(i).getLoadCard() + " & " + failShipDetails.get(i).getProgramCard());
                        } else {
                            celltitleShpToSFFailId.setCellValue(failShipDetails.get(i).getEqptId());
                        }

                        HSSFCell celltitleShpToSFFailMP = rowforShiptoSFFail.createCell(4);
                        celltitleShpToSFFailMP.setCellStyle(styleWithBorder);
                        celltitleShpToSFFailMP.setCellValue(failShipDetails.get(i).getMpNo().toLowerCase());

                        HSSFCell celltitleShpToSFFailDur = rowforShiptoSFFail.createCell(6);
                        celltitleShpToSFFailDur.setCellStyle(styleWithBorder);
                        celltitleShpToSFFailDur.setCellValue(failShipDetails.get(i).getTotalHourTakeShip());

                        failSteps = "";
                        flag = "0";

                        if (Integer.parseInt(failShipDetails.get(i).getReqToApp()) > 24) {
                            failSteps = "Requested to Approved";
                            flag = "1";
                        }

                        if (Integer.parseInt(failShipDetails.get(i).getMpCreateToTtScan()) > 24) {
                            if ("1".equals(flag)) {
                                failSteps = failSteps + "; Material Pass Inserted to Trip ticket Scanned";
                                flag = "1";
                            } else {
                                failSteps = "Material Pass Inserted to Trip ticket Scanned";
                                flag = "1";
                            }
                        }

                        if (Integer.parseInt(failShipDetails.get(i).getTtScanToBcScan()) > 24) {
                            if ("1".equals(flag)) {
                                failSteps = failSteps + "; Trip ticket Scanned to Barcode Sticker Scanned";
                                flag = "1";
                            } else {
                                failSteps = "Trip ticket Scanned to Barcode Sticker Scanned";
                                flag = "1";
                            }
                        }

                        if (Integer.parseInt(failShipDetails.get(i).getBcScanToShip()) > 24) {
                            if ("1".equals(flag)) {
                                failSteps = failSteps + "; Barcode Sticker Scanned to Shipped";
                                flag = "1";
                            } else {
                                failSteps = "Barcode Sticker Scanned to Shipped";
                                flag = "1";
                            }
                        }

                        if (Integer.parseInt(failShipDetails.get(i).getShipToInv()) > 24) {
                            if ("1".equals(flag)) {
                                failSteps = failSteps + "; Shipped to Inventory";
                                flag = "1";
                            } else {
                                failSteps = "Shipped to Inventory";
                                flag = "1";
                            }
                        }
                        HSSFCell celltitleFailStepFailDur = rowforShiptoSFFail.createCell(7);
                        celltitleFailStepFailDur.setCellStyle(styleWithBorder);
                        celltitleFailStepFailDur.setCellValue(failSteps);

                        HSSFCell celltitleFailShipRc = rowforShiptoSFFail.createCell(10);
                        celltitleFailShipRc.setCellStyle(styleWithBorder);
                        celltitleFailShipRc.setCellValue(failShipDetails.get(i).getRootCause());

                        HSSFCell celltitleFailShipCa = rowforShiptoSFFail.createCell(14);
                        celltitleFailShipCa.setCellStyle(styleWithBorder);
                        celltitleFailShipCa.setCellValue(failShipDetails.get(i).getCa());

                        HSSFCell cell2 = rowforShiptoSFFail.createCell(2);
                        cell2.setCellStyle(styleWithBorder);

                        HSSFCell cell4 = rowforShiptoSFFail.createCell(3);
                        cell4.setCellStyle(styleWithBorder);

                        HSSFCell cell7 = rowforShiptoSFFail.createCell(5);
                        cell7.setCellStyle(styleWithBorder);

                        HSSFCell cell8 = rowforShiptoSFFail.createCell(8);
                        cell8.setCellStyle(styleWithBorder);

                        HSSFCell cell9 = rowforShiptoSFFail.createCell(9);
                        cell9.setCellStyle(styleWithBorder);

                        HSSFCell cell11 = rowforShiptoSFFail.createCell(11);
                        cell11.setCellStyle(styleWithBorder);

                        HSSFCell cell12 = rowforShiptoSFFail.createCell(12);
                        cell12.setCellStyle(styleWithBorder);

                        HSSFCell cell13 = rowforShiptoSFFail.createCell(13);
                        cell13.setCellStyle(styleWithBorder);

                        HSSFCell cell14 = rowforShiptoSFFail.createCell(15);
                        cell14.setCellStyle(styleWithBorder);

                        HSSFCell cell15 = rowforShiptoSFFail.createCell(16);
                        cell15.setCellStyle(styleWithBorder);

                        HSSFCell cell17 = rowforShiptoSFFail.createCell(17);
                        cell17.setCellStyle(styleWithBorder);

                    }
                } else {
                    rowNum = (short) (rowNum + 1);
                    //insert to failed item details for dec 17
                    HSSFRow rowforShiptoSFFail = sheet.createRow((short) rowNum);
                    HSSFCell celltitleShpToSFFailType = rowforShiptoSFFail.createCell(0);
                    celltitleShpToSFFailType.setCellValue("N/A");

                }

            }

            if ("Retrieve".equals(requestType) || "all".equals(requestType)) {

                if ("all".equals(requestType)) {
                    //add 2 row to current rowNum
                    rowNum = (short) (rowNum + 2);
                }

                HSSFRow rowforItemFailDec17Retrieve = sheet.createRow((short) rowNum);
                HSSFCell cell1Dec17FailRet = rowforItemFailDec17Retrieve.createCell(0);
                cell1Dec17FailRet.setCellStyle(styleGreen);
                cell1Dec17FailRet.setCellValue("Storage Factory to Rel Lab");

                //add 1 row to current rowNum
                rowNum = (short) (rowNum + 1);

                //Item fail details Dec 17
                HSSFRow rowforItemFailHeaderDec17Retrieve = sheet.createRow((short) rowNum);

                HSSFCell cell1ItemtypeDec17Retrieve = rowforItemFailHeaderDec17Retrieve.createCell(0);
                cell1ItemtypeDec17Retrieve.setCellStyle(styleBlueandFillGrey);
                cell1ItemtypeDec17Retrieve.setCellValue("Item Type");

                HSSFCell cell1ItemIdDec17Retrieve = rowforItemFailHeaderDec17Retrieve.createCell(1);
                cell1ItemIdDec17Retrieve.setCellStyle(styleBlueandFillGrey);
                cell1ItemIdDec17Retrieve.setCellValue("Item ID");

                HSSFCell cell1mpNoDec17Retrieve = rowforItemFailHeaderDec17Retrieve.createCell(4);
                cell1mpNoDec17Retrieve.setCellStyle(styleBlueandFillGrey);
                cell1mpNoDec17Retrieve.setCellValue("Material Pass No");

                HSSFCell cell1DurationDec17Retrieve = rowforItemFailHeaderDec17Retrieve.createCell(6);
                cell1DurationDec17Retrieve.setCellStyle(styleBlueandFillGrey);
                cell1DurationDec17Retrieve.setCellValue("Duration (hrs)");

                HSSFCell cell1StepDec17Retrieve = rowforItemFailHeaderDec17Retrieve.createCell(7);
                cell1StepDec17Retrieve.setCellStyle(styleBlueandFillGrey);
                cell1StepDec17Retrieve.setCellValue("Process Steps Over USL");

                HSSFCell cell1RcDec17Retrieve = rowforItemFailHeaderDec17Retrieve.createCell(10);
                cell1RcDec17Retrieve.setCellStyle(styleBlueandFillGrey);
                cell1RcDec17Retrieve.setCellValue("Root Cause");

                HSSFCell cell1CaDec17Retrieve = rowforItemFailHeaderDec17Retrieve.createCell(14);
                cell1CaDec17Retrieve.setCellStyle(styleBlueandFillGrey);
                cell1CaDec17Retrieve.setCellValue("Correlative Action");

                HSSFCell cell22 = rowforItemFailHeaderDec17Retrieve.createCell(2);
                cell22.setCellStyle(styleBlueandFillGrey);

                HSSFCell cell44 = rowforItemFailHeaderDec17Retrieve.createCell(3);
                cell44.setCellStyle(styleBlueandFillGrey);

                HSSFCell cell77 = rowforItemFailHeaderDec17Retrieve.createCell(5);
                cell77.setCellStyle(styleBlueandFillGrey);

                HSSFCell cell88 = rowforItemFailHeaderDec17Retrieve.createCell(8);
                cell88.setCellStyle(styleBlueandFillGrey);

                HSSFCell cell99 = rowforItemFailHeaderDec17Retrieve.createCell(9);
                cell99.setCellStyle(styleBlueandFillGrey);

                HSSFCell cell11 = rowforItemFailHeaderDec17Retrieve.createCell(11);
                cell11.setCellStyle(styleBlueandFillGrey);

                HSSFCell cell12 = rowforItemFailHeaderDec17Retrieve.createCell(12);
                cell12.setCellStyle(styleBlueandFillGrey);

                HSSFCell cell13 = rowforItemFailHeaderDec17Retrieve.createCell(13);
                cell13.setCellStyle(styleBlueandFillGrey);

                HSSFCell cell14 = rowforItemFailHeaderDec17Retrieve.createCell(15);
                cell14.setCellStyle(styleBlueandFillGrey);

                HSSFCell cell15 = rowforItemFailHeaderDec17Retrieve.createCell(16);
                cell15.setCellStyle(styleBlueandFillGrey);

                HSSFCell cell17 = rowforItemFailHeaderDec17Retrieve.createCell(17);
                cell17.setCellStyle(styleBlueandFillGrey);

                WhUslReportDAO count2 = new WhUslReportDAO();
                Integer totalRetrieveFail = count2.getCountRetrieveFailByYearAndByMonthAndByEqptType(month2.toString(), year2.toString(), equipmentType);

                if (totalRetrieveFail > 0) {

                    WhUslReportDAO fail = new WhUslReportDAO();
//                    List<WhUslReport> failRetriveDetails = fail.GetListOfFailedRetrieveItemByYearAndByMonthAndByEqptType(month2.toString(), year2.toString(), equipmentType);
                    List<WhUslReport> failRetriveDetails = fail.GetListOfFailedRetrieveItemByYearAndByMonthAndByEqptTypeWithTimelapseTable(month2.toString(), year2.toString(), equipmentType);

                    for (int i = 0; i < failRetriveDetails.size(); i++) {
                        //add 1 row to current rowNum
                        rowNum = (short) (rowNum + 1);
                        //insert to failed item details for dec 17
                        HSSFRow rowforShiptoSFFail = sheet.createRow((short) rowNum);

                        HSSFCell celltitleShpToSFFailType = rowforShiptoSFFail.createCell(0);
                        celltitleShpToSFFailType.setCellStyle(styleWithBorder);
                        celltitleShpToSFFailType.setCellValue(failRetriveDetails.get(i).getEqptType());

                        HSSFCell celltitleShpToSFFailId = rowforShiptoSFFail.createCell(1);
                        celltitleShpToSFFailId.setCellStyle(styleWithBorder);
                        if ("Load Card".equals(failRetriveDetails.get(i).getEqptType())) {
                            celltitleShpToSFFailId.setCellValue(failRetriveDetails.get(i).getLoadCard());
                        } else if ("Program Card".equals(failRetriveDetails.get(i).getEqptType())) {
                            celltitleShpToSFFailId.setCellValue(failRetriveDetails.get(i).getProgramCard());
                        } else if ("Load Card & Program Card".equals(failRetriveDetails.get(i).getEqptType())) {
                            celltitleShpToSFFailId.setCellValue(failRetriveDetails.get(i).getLoadCard() + " & " + failRetriveDetails.get(i).getProgramCard());
                        } else {
                            celltitleShpToSFFailId.setCellValue(failRetriveDetails.get(i).getEqptId());
                        }
                        HSSFCell celltitleShpToSFFailMP = rowforShiptoSFFail.createCell(4);
                        celltitleShpToSFFailMP.setCellStyle(styleWithBorder);
                        celltitleShpToSFFailMP.setCellValue(failRetriveDetails.get(i).getMpNo().toLowerCase());

                        HSSFCell celltitleShpToSFFailDur = rowforShiptoSFFail.createCell(6);
                        celltitleShpToSFFailDur.setCellStyle(styleWithBorder);
                        celltitleShpToSFFailDur.setCellValue(failRetriveDetails.get(i).getTotalHourTakeRetrieve());

                        flag = "0";
                        failSteps = "";

                        if (Integer.parseInt(failRetriveDetails.get(i).getReqToVer()) > 24) {
                            failSteps = "Requested to SF Verified";
                            flag = "1";
                        }

                        if (Integer.parseInt(failRetriveDetails.get(i).getVerToShip()) > 24) {
                            if ("1".equals(flag)) {
                                failSteps = failSteps + "; SF Verified to Shipped";
                                flag = "1";
                            } else {
                                failSteps = "SF Verified to Shipped";
                                flag = "1";
                            }
                        }

                        if (Integer.parseInt(failRetriveDetails.get(i).getShipToBcScan()) > 24) {
                            if ("1".equals(flag)) {
                                failSteps = failSteps + "; Shipped to Barcode Sticker Scanned";
                                flag = "1";
                            } else {
                                failSteps = "Shipped to Barcode Sticker Scanned";
                                flag = "1";
                            }
                        }

                        if (Integer.parseInt(failRetriveDetails.get(i).getBcScanToTtScan()) > 24) {
                            if ("1".equals(flag)) {
                                failSteps = failSteps + "; Barcode Sticker Scanned to Trip ticket Scanned";
                                flag = "1";
                            } else {
                                failSteps = "Barcode Sticker Scanned to Trip ticket Scanned";
                                flag = "1";
                            }
                        }

                        HSSFCell celltitleFailStepFailDur = rowforShiptoSFFail.createCell(7);
                        celltitleFailStepFailDur.setCellStyle(styleWithBorder);
                        celltitleFailStepFailDur.setCellValue(failSteps);

                        HSSFCell celltitleFailStepFailRc = rowforShiptoSFFail.createCell(10);
                        celltitleFailStepFailRc.setCellStyle(styleWithBorder);
                        celltitleFailStepFailRc.setCellValue(failRetriveDetails.get(i).getRootCause());

                        HSSFCell celltitleFailStepFailCa = rowforShiptoSFFail.createCell(14);
                        celltitleFailStepFailCa.setCellStyle(styleWithBorder);
                        celltitleFailStepFailCa.setCellValue(failRetriveDetails.get(i).getCa());

                        HSSFCell cell2 = rowforShiptoSFFail.createCell(2);
                        cell2.setCellStyle(styleWithBorder);

                        HSSFCell cell4 = rowforShiptoSFFail.createCell(3);
                        cell4.setCellStyle(styleWithBorder);

                        HSSFCell cell7 = rowforShiptoSFFail.createCell(5);
                        cell7.setCellStyle(styleWithBorder);

                        HSSFCell cell8 = rowforShiptoSFFail.createCell(8);
                        cell8.setCellStyle(styleWithBorder);

                        HSSFCell cell9 = rowforShiptoSFFail.createCell(9);
                        cell9.setCellStyle(styleWithBorder);

                        HSSFCell cell11R = rowforShiptoSFFail.createCell(11);
                        cell11R.setCellStyle(styleWithBorder);

                        HSSFCell cell12R = rowforShiptoSFFail.createCell(12);
                        cell12R.setCellStyle(styleWithBorder);

                        HSSFCell cell13R = rowforShiptoSFFail.createCell(13);
                        cell13R.setCellStyle(styleWithBorder);

                        HSSFCell cell14R = rowforShiptoSFFail.createCell(15);
                        cell14R.setCellStyle(styleWithBorder);

                        HSSFCell cell5R = rowforShiptoSFFail.createCell(16);
                        cell5R.setCellStyle(styleWithBorder);

                        HSSFCell cell17R = rowforShiptoSFFail.createCell(17);
                        cell17R.setCellStyle(styleWithBorder);
                    }
                } else {
                    rowNum = (short) (rowNum + 1);
                    //insert to failed item details for dec 17
                    HSSFRow rowforShiptoSFFail = sheet.createRow((short) rowNum);
                    HSSFCell celltitleShpToSFFailType = rowforShiptoSFFail.createCell(0);
                    celltitleShpToSFFailType.setCellValue("N/A");

                }

            }

            //add 2 row to current rowNum
//            rowNum = (short) (rowNum + 2);
            flag = "0";
            failSteps = "";

//            monthTemp2 = monthTemp2 - 1;
//            cellColumn = cellColumn + 1;
            month2 -= 1;
            cellColumn += 1;

        }
        //end of loop for all items details in month

        //auto resize column
        sheet.autoSizeColumn(0);
//        for (int columnIndex = 2; columnIndex < 15; columnIndex++) {
//            sheet.autoSizeColumn(columnIndex);
//        }

        //merger cell for remark content
        sheet.addMergedRegion(new CellRangeAddress(5, 5, 1, 6)); //rowstr, rowend, colstr, colend
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 1, 6)); //rowstr, rowend, colstr, colend
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 1, 5)); //rowstr, rowend, colstr, colend

        for (int columnIndex = 15; columnIndex < (rowNum + 1); columnIndex++) {
            sheet.addMergedRegion(new CellRangeAddress(columnIndex, columnIndex, 1, 3)); //rowstr, rowend, colstr, colend
            sheet.addMergedRegion(new CellRangeAddress(columnIndex, columnIndex, 4, 5));
            sheet.addMergedRegion(new CellRangeAddress(columnIndex, columnIndex, 7, 9));
            sheet.addMergedRegion(new CellRangeAddress(columnIndex, columnIndex, 10, 13));
            sheet.addMergedRegion(new CellRangeAddress(columnIndex, columnIndex, 14, 17));
        }
    }

}
