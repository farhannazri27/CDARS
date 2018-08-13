/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onsemi.cdars.config;

import com.onsemi.cdars.dao.EmailConfigDAO;
import com.onsemi.cdars.dao.EmailRootcauseTimelapseDAO;
import com.onsemi.cdars.dao.EmailTimelapseDAO;
import com.onsemi.cdars.dao.WhRequestDAO;
import com.onsemi.cdars.dao.WhStatusLogDAO;
import com.onsemi.cdars.dao.WhUslReportDAO;
import com.onsemi.cdars.model.EmailConfig;
import com.onsemi.cdars.model.EmailRootcauseTimelapse;
import com.onsemi.cdars.model.EmailTimelapse;
import com.onsemi.cdars.model.WhStatusLog;
import com.onsemi.cdars.model.WhUslReport;
import com.onsemi.cdars.tools.EmailSender;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletContext;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 *
 * @author fg79cj
 */
@Configuration
@EnableScheduling
public class FtpConfigUSL24hrs {

    private static final Logger LOGGER = LoggerFactory.getLogger(FtpConfigUSL24hrs.class);
    String[] args = {};

    @Autowired
    ServletContext servletContext;

//    @Scheduled(cron = "0 0 */6 * * *") //every 6 hour
//    @Scheduled(cron = "0 16 17 * * ?") //every 8:00 AM - cron (sec min hr daysOfMth month daysOfWeek year(optional))
    public void cronRun() throws FileNotFoundException, IOException {
        LOGGER.info("Upper Spec Limit (USL Shipping) executed at everyday on 8:00 am. Current time is : " + new Date());

        String username = System.getProperty("user.name");
        if (!"fg79cj".equals(username)) {
            username = "imperial";
        }
        DateFormat dateFormat = new SimpleDateFormat("ddMMMyyyy");
        Date date = new Date();
        String todayDate = dateFormat.format(date);

        String reportName = "C:\\Users\\" + username + "\\Documents\\CDARS\\HIMS USL for Sending to SBN Factory Report (" + todayDate + ").xls";

        FileOutputStream fileOut = new FileOutputStream(reportName);
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("HIMS PROCESS EXCEED USL");
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBoldweight(HSSFFont.COLOR_NORMAL);
        font.setBold(true);
        font.setColor(HSSFColor.DARK_BLUE.index);
        style.setFont(font);
        sheet.createFreezePane(0, 1); // Freeze 1st Row

        HSSFRow rowhead = sheet.createRow((short) 0);
        rowhead.setRowStyle(style);

        HSSFCell cell1_0 = rowhead.createCell(0);
        cell1_0.setCellStyle(style);
        cell1_0.setCellValue("HARDWARE TYPE");

        HSSFCell cell1_1 = rowhead.createCell(1);
        cell1_1.setCellStyle(style);
        cell1_1.setCellValue("HARDWARE ID");

        HSSFCell cell1_2 = rowhead.createCell(2);
        cell1_2.setCellStyle(style);
        cell1_2.setCellValue("MATERIAL PASS NO");

        HSSFCell cell1_3 = rowhead.createCell(3);
        cell1_3.setCellStyle(style);
        cell1_3.setCellValue("DURATION");

        HSSFCell cell1_4 = rowhead.createCell(4);
        cell1_4.setCellStyle(style);
        cell1_4.setCellValue("CURRENT STATUS");

//            WhUSLDAO whUslDAO = new WhUSLDAO();
//            List<WhUSL> whUslList = whUslDAO.getWhUSLLog();
        String materialPassNo = "";
        String hardwareId = "";
        String hardwareType = "";
        String duration = "";
        String status = "";
        String text = "";
        boolean mbCardBibPart = false;
        boolean loadCard = false;
        boolean ate = false;
        boolean eqp = false;
        boolean pcbStencilTray = false;
        boolean bibPart = false;

        WhStatusLogDAO statusD = new WhStatusLogDAO();
//        List<WhStatusLog> whUslList = statusD.getTLReqToApproveAndApproveToMpCreatedList();
        List<WhStatusLog> whUslList = statusD.getTLReqToInventoryList();

        boolean checksize1 = false;
//        boolean checksize2 = false;
        for (int i = 0; i < whUslList.size(); i++) {
            checksize1 = true;
            hardwareType = whUslList.get(i).getEquipmentType();
            if ("Load Card".equals(whUslList.get(i).getEquipmentType())) {
                hardwareId = whUslList.get(i).getLoadCard();
            } else if ("Program Card".equals(whUslList.get(i).getEquipmentType())) {
                hardwareId = whUslList.get(i).getProgramCard();
            } else if ("Load Card & Program Card".equals(whUslList.get(i).getEquipmentType())) {
                hardwareId = whUslList.get(i).getLoadCard() + " & " + whUslList.get(i).getProgramCard();
            } else {
                hardwareId = whUslList.get(i).getEquipmentId();
            }
            materialPassNo = whUslList.get(i).getMpNo();
            String hourReqApp = whUslList.get(i).getRequestToApprove24();
            String hourReqAppIfNull = whUslList.get(i).getRequestToApproveTemp24();
            String hourAppMp = whUslList.get(i).getApproveToMPCreated24();
            String hourAppMpIfNull = whUslList.get(i).getApproveToMPCreatedTemp24();
            String hourMpTt = whUslList.get(i).getMpCreatedToTtScan24();
            String hourMpTtIfNull = whUslList.get(i).getMpCreatedToTtScanTemp24();
            String hourTtBs = whUslList.get(i).getTtScanToBsScan24();
            String hourTtBsIfNull = whUslList.get(i).getTtScanToBsScanTemp24();
            String hourBsShip = whUslList.get(i).getBsScanToShip24();
            String hourBsShipIfNull = whUslList.get(i).getBsScanToShipTemp24();
            String hourShipInv = whUslList.get(i).getShipToInventory24();
            String hourShipInvIfNull = whUslList.get(i).getShipToInventoryTemp24();

            boolean flag = false;

            if (hourReqAppIfNull != null) {
                if (Integer.parseInt(hourReqAppIfNull) >= 24 && hourReqApp == null) {
                    duration = whUslList.get(i).getRequestToApproveTemp();
                    status = "Pending Approval";
                    flag = true;
                    if ("Motherboard".equals(whUslList.get(i).getEquipmentType())) {
                        mbCardBibPart = true;
                    } else if ("Tray".equals(whUslList.get(i).getEquipmentType())) {
                        pcbStencilTray = true;
                    } else if ("PCB".equals(whUslList.get(i).getEquipmentType())) {
                        pcbStencilTray = true;
                    } else if ("Stencil".equals(whUslList.get(i).getEquipmentType())) {
                        pcbStencilTray = true;
                    } else if (whUslList.get(i).getEquipmentType().contains("EQP_")) {
                        eqp = true;
                    } else if (whUslList.get(i).getEquipmentType().contains("ATE_")) {
                        ate = true;
                    } else if (whUslList.get(i).getEquipmentType().contains("Card")) {
                        mbCardBibPart = true;
                    } else if (whUslList.get(i).getEquipmentType().contains("Bib Parts")) {
                        mbCardBibPart = true;
                    }
                }
            }

            if (hourAppMpIfNull != null) {
                if (Integer.parseInt(hourAppMpIfNull) >= 24 && hourAppMp == null && hourReqApp != null) {
                    duration = whUslList.get(i).getApproveToMPCreatedTemp();
                    status = "Pending Material Pass Number";
                    flag = true;

                    if ("Motherboard".equals(whUslList.get(i).getEquipmentType())) {
                        mbCardBibPart = true;
                    } else if ("Tray".equals(whUslList.get(i).getEquipmentType())) {
                        pcbStencilTray = true;
                    } else if ("PCB".equals(whUslList.get(i).getEquipmentType())) {
                        pcbStencilTray = true;
                    } else if ("Stencil".equals(whUslList.get(i).getEquipmentType())) {
                        pcbStencilTray = true;
                    } else if (whUslList.get(i).getEquipmentType().contains("EQP_")) {
                        eqp = true;
                    } else if (whUslList.get(i).getEquipmentType().contains("ATE_")) {
                        ate = true;
                    } else if (whUslList.get(i).getEquipmentType().contains("Card")) {
                        mbCardBibPart = true;
                    } else if (whUslList.get(i).getEquipmentType().contains("Bib Parts")) {
                        mbCardBibPart = true;
                    }
                }
            }

            if (hourMpTtIfNull != null) {
                if (Integer.parseInt(hourMpTtIfNull) >= 24 && hourMpTt == null) {
                    duration = whUslList.get(i).getMpCreatedToTtScanTemp();
                    status = "Pending Trip Ticket Scanning";
                    flag = true;

                    if ("Motherboard".equals(whUslList.get(i).getEquipmentType())) {
                        mbCardBibPart = true;
                    } else if ("Tray".equals(whUslList.get(i).getEquipmentType())) {
                        pcbStencilTray = true;
                    } else if ("PCB".equals(whUslList.get(i).getEquipmentType())) {
                        pcbStencilTray = true;
                    } else if ("Stencil".equals(whUslList.get(i).getEquipmentType())) {
                        pcbStencilTray = true;
                    } else if (whUslList.get(i).getEquipmentType().contains("EQP_")) {
                        eqp = true;
                    } else if (whUslList.get(i).getEquipmentType().contains("ATE_")) {
                        ate = true;
                    } else if (whUslList.get(i).getEquipmentType().contains("Card")) {
                        mbCardBibPart = true;
                    } else if (whUslList.get(i).getEquipmentType().contains("Bib Parts")) {
                        mbCardBibPart = true;
                    }
                }
            }

            if (hourTtBsIfNull != null) {
                if (Integer.parseInt(hourTtBsIfNull) >= 24 && hourTtBs == null && hourMpTt != null) {
                    duration = whUslList.get(i).getTtScanToBsScanTemp();
                    status = "Pending Barcode Sticker Scanning";
                    flag = true;

                    if ("Motherboard".equals(whUslList.get(i).getEquipmentType())) {
                        mbCardBibPart = true;
                    } else if ("Tray".equals(whUslList.get(i).getEquipmentType())) {
                        pcbStencilTray = true;
                    } else if ("PCB".equals(whUslList.get(i).getEquipmentType())) {
                        pcbStencilTray = true;
                    } else if ("Stencil".equals(whUslList.get(i).getEquipmentType())) {
                        pcbStencilTray = true;
                    } else if (whUslList.get(i).getEquipmentType().contains("EQP_")) {
                        eqp = true;
                    } else if (whUslList.get(i).getEquipmentType().contains("ATE_")) {
                        ate = true;
                    } else if (whUslList.get(i).getEquipmentType().contains("Card")) {
                        mbCardBibPart = true;
                    } else if (whUslList.get(i).getEquipmentType().contains("Bib Parts")) {
                        mbCardBibPart = true;
                    }
                }
            }

            if (hourBsShipIfNull != null) {
                if (Integer.parseInt(hourBsShipIfNull) >= 24 && hourBsShip == null && hourTtBs != null && hourMpTt != null) {
                    duration = whUslList.get(i).getBsScanToShipTemp();
                    status = "Pending Shipping Packing List";
                    flag = true;

                    if ("Motherboard".equals(whUslList.get(i).getEquipmentType())) {
                        mbCardBibPart = true;
                    } else if ("Tray".equals(whUslList.get(i).getEquipmentType())) {
                        pcbStencilTray = true;
                    } else if ("PCB".equals(whUslList.get(i).getEquipmentType())) {
                        pcbStencilTray = true;
                    } else if ("Stencil".equals(whUslList.get(i).getEquipmentType())) {
                        pcbStencilTray = true;
                    } else if (whUslList.get(i).getEquipmentType().contains("EQP_")) {
                        eqp = true;
                    } else if (whUslList.get(i).getEquipmentType().contains("ATE_")) {
                        ate = true;
                    } else if (whUslList.get(i).getEquipmentType().contains("Card")) {
                        mbCardBibPart = true;
                    } else if (whUslList.get(i).getEquipmentType().contains("Bib Parts")) {
                        mbCardBibPart = true;
                    }
                }
            }

            if (hourShipInvIfNull != null) {
                if (Integer.parseInt(hourShipInvIfNull) >= 24 && hourShipInv == null && hourBsShip != null && hourTtBs != null && hourMpTt != null) {
                    duration = whUslList.get(i).getShipToInventoryTemp();
                    status = "Pending Inventory in Seremban Factory";
                    flag = true;

                    if ("Motherboard".equals(whUslList.get(i).getEquipmentType())) {
                        mbCardBibPart = true;
                    } else if ("Tray".equals(whUslList.get(i).getEquipmentType())) {
                        pcbStencilTray = true;
                    } else if ("PCB".equals(whUslList.get(i).getEquipmentType())) {
                        pcbStencilTray = true;
                    } else if ("Stencil".equals(whUslList.get(i).getEquipmentType())) {
                        pcbStencilTray = true;
                    } else if (whUslList.get(i).getEquipmentType().contains("EQP_")) {
                        eqp = true;
                    } else if (whUslList.get(i).getEquipmentType().contains("ATE_")) {
                        ate = true;
                    } else if (whUslList.get(i).getEquipmentType().contains("Card")) {
                        mbCardBibPart = true;
                    } else if (whUslList.get(i).getEquipmentType().contains("Bib Parts")) {
                        mbCardBibPart = true;
                    }
                }
            }

            if (flag == true) {
                HSSFRow contents = sheet.createRow(sheet.getLastRowNum() + 1);
//                
                HSSFCell cell2_0 = contents.createCell(0);
                cell2_0.setCellValue(hardwareType);

                HSSFCell cell2_1 = contents.createCell(1);
                cell2_1.setCellValue(hardwareId);

                HSSFCell cell2_2 = contents.createCell(2);
                cell2_2.setCellValue(materialPassNo);

                HSSFCell cell2_3 = contents.createCell(3);
                cell2_3.setCellValue(duration);

                HSSFCell cell2_4 = contents.createCell(4);
                cell2_4.setCellValue(status);
            }
        }

//        WhStatusLogDAO statusD2 = new WhStatusLogDAO();
//        List<WhStatusLog> whUslList2 = statusD2.getTLMpCreatedToFinalInventoryDateList();
//
//        for (int i = 0; i < whUslList2.size(); i++) {
//            checksize2 = true;
//            hardwareType = whUslList2.get(i).getEquipmentType();
//            hardwareId = whUslList2.get(i).getEquipmentId();
//            materialPassNo = whUslList2.get(i).getMpNo();
//            String hourMpTt = whUslList2.get(i).getMpCreatedToTtScan24();
//            String hourMpTtIfNull = whUslList2.get(i).getMpCreatedToTtScanTemp24();
//            String hourTtBs = whUslList2.get(i).getTtScanToBsScan24();
//            String hourTtBsIfNull = whUslList2.get(i).getTtScanToBsScanTemp24();
//            String hourBsShip = whUslList2.get(i).getBsScanToShip24();
//            String hourBsShipIfNull = whUslList2.get(i).getBsScanToShipTemp24();
//            String hourShipInv = whUslList2.get(i).getShipToInventory24();
//            String hourShipInvIfNull = whUslList2.get(i).getShipToInventoryTemp24();
//
//            boolean flag = false;
//
//            if (hourMpTtIfNull != null) {
//                if (Integer.parseInt(hourMpTtIfNull) >= 24 && hourMpTt == null) {
//                    duration = whUslList2.get(i).getMpCreatedToTtScanTemp();
//                    status = "Pending Trip Ticket Scanning";
//                    flag = true;
//                }
//            }
//
//            if (hourTtBsIfNull != null) {
//                if (Integer.parseInt(hourTtBsIfNull) >= 24 && hourTtBs == null && hourMpTt != null) {
//                    duration = whUslList2.get(i).getTtScanToBsScanTemp();
//                    status = "Pending Barcode Sticker Scanning";
//                    flag = true;
//                }
//            }
//
//            if (hourBsShipIfNull != null) {
//                if (Integer.parseInt(hourBsShipIfNull) >= 24 && hourBsShip == null && hourTtBs != null && hourMpTt != null) {
//                    duration = whUslList2.get(i).getBsScanToShipTemp();
//                    status = "Pending Shipping Packing List";
//                    flag = true;
//                }
//            }
//
//            if (hourShipInvIfNull != null) {
//                if (Integer.parseInt(hourShipInvIfNull) >= 24 && hourShipInv == null && hourBsShip != null && hourTtBs != null && hourMpTt != null) {
//                    duration = whUslList2.get(i).getShipToInventoryTemp();
//                    status = "Pending Inventory in Seremban Factory";
//                    flag = true;
//                }
//            }
//
//            if (flag == true) {
//                HSSFRow contents = sheet.createRow(sheet.getLastRowNum() + 1);
////                
//                HSSFCell cell2_0 = contents.createCell(0);
//                cell2_0.setCellValue(hardwareType);
//
//                HSSFCell cell2_1 = contents.createCell(1);
//                cell2_1.setCellValue(hardwareId);
//
//                HSSFCell cell2_2 = contents.createCell(2);
//                cell2_2.setCellValue(materialPassNo);
//
//                HSSFCell cell2_3 = contents.createCell(3);
//                cell2_3.setCellValue(duration);
//
//                HSSFCell cell2_4 = contents.createCell(4);
//                cell2_4.setCellValue(status);
//            }
//
//        }
        if (checksize1 == true) {
            workbook.write(fileOut);
            workbook.close();

            //send email
            LOGGER.info("send email to person in charge");
            EmailSender emailSender = new EmailSender();
            com.onsemi.cdars.model.User user = new com.onsemi.cdars.model.User();
            user.setFullname("All");

            List<String> a = new ArrayList<String>();

            String emailApprover = "";
            String emaildistList1 = "";
            String emaildistList2 = "";
            String emaildistList3 = "";
            String emaildistList4 = "";
            String emailMbCardBibPart = "";
            String emailPcbTrayStencil = "";
            String emailAte = "";
            String emailEqp = "";

            emailApprover = "fg79cj@onsemi.com";
            a.add(emailApprover);

            EmailConfigDAO econfD = new EmailConfigDAO();
            int countDistList1 = econfD.getCountTask("Dist List 1");
            if (countDistList1 == 1) {
                econfD = new EmailConfigDAO();
                EmailConfig distList1 = econfD.getEmailConfigByTask("Dist List 1");
                emaildistList1 = distList1.getEmail();
                a.add(emaildistList1);
            }
            econfD = new EmailConfigDAO();
            int countDistList2 = econfD.getCountTask("Dist List 2");
            if (countDistList2 == 1) {
                econfD = new EmailConfigDAO();
                EmailConfig distList2 = econfD.getEmailConfigByTask("Dist List 2");
                emaildistList2 = distList2.getEmail();
                a.add(emaildistList2);
            }
            econfD = new EmailConfigDAO();
            int countDistList3 = econfD.getCountTask("Dist List 3");
            if (countDistList3 == 1) {
                econfD = new EmailConfigDAO();
                EmailConfig distList3 = econfD.getEmailConfigByTask("Dist List 3");
                emaildistList3 = distList3.getEmail();
                a.add(emaildistList3);
            }
            econfD = new EmailConfigDAO();
            int countDistList4 = econfD.getCountTask("Dist List 4");
            if (countDistList4 == 1) {
                econfD = new EmailConfigDAO();
                EmailConfig distList4 = econfD.getEmailConfigByTask("Dist List 4");
                emaildistList4 = distList4.getEmail();
                a.add(emaildistList4);
            }
            if (mbCardBibPart == true) {
                econfD = new EmailConfigDAO();
                int countMb = econfD.getCountTask("Motherboard");
                if (countMb == 1) {
                    econfD = new EmailConfigDAO();
                    EmailConfig distMb = econfD.getEmailConfigByTask("Motherboard");
                    emailMbCardBibPart = distMb.getEmail();
                    a.add(emailMbCardBibPart);
                }
            }
            if (pcbStencilTray == true) {
                econfD = new EmailConfigDAO();
                int countpcbStencilTray = econfD.getCountTask("Stencil");
                if (countpcbStencilTray == 1) {
                    econfD = new EmailConfigDAO();
                    EmailConfig distpcb = econfD.getEmailConfigByTask("Stencil");
                    emailPcbTrayStencil = distpcb.getEmail();
                    a.add(emailPcbTrayStencil);
                }
            }
            if (eqp == true) {
                econfD = new EmailConfigDAO();
                int countEqp = econfD.getCountTask("EQP");
                if (countEqp == 1) {
                    econfD = new EmailConfigDAO();
                    EmailConfig disteqp = econfD.getEmailConfigByTask("EQP");
                    emailEqp = disteqp.getEmail();
                    a.add(emailEqp);
                }
            }
            if (ate == true) {
                econfD = new EmailConfigDAO();
                int countAte = econfD.getCountTask("ATE");
                if (countAte == 1) {
                    econfD = new EmailConfigDAO();
                    EmailConfig distAte = econfD.getEmailConfigByTask("ATE");
                    emailAte = distAte.getEmail();
                    a.add(emailAte);
                }
            }

            String[] myArray = new String[a.size()];
            String[] emailTo = a.toArray(myArray);
            LOGGER.info("emailTo: " + Arrays.toString(emailTo));
            LOGGER.info("ate: " + ate);
            LOGGER.info("eqp: " + eqp);
            LOGGER.info("pcbStencilTray: " + pcbStencilTray);
            LOGGER.info("mbCardBibPart: " + mbCardBibPart);
//            String[] to = {"fg79cj@onsemi.com"};
            emailSender.htmlEmailWithAttachment(
                    servletContext,
                    user, //user name requestor
                    emailTo,
                    new File("C:\\Users\\" + username + "\\Documents\\CDARS\\HIMS USL for Sending to SBN Factory Report (" + todayDate + ").xls"),
                    "List of Hardware Exceed USL (24 hours) for Sending to SBN Factory", //subject
                    "Report for Hardware Process from HIMS(Hadware Sending to SBN Factory) that exceed Upper Specs Limit (24 hours) has been made. <br />"
                    + "Hence, attached is the report file for your view and perusal. <br /><br />"
                    + "<br /><br /> "
                    + "<style>table, th, td {border: 1px solid black;} </style>"
                    + "<table style=\"width:100%\">" //tbl
                    + "<tr>"
                    + "<th>HARDWARE TYPE</th> "
                    + "<th>HARDWARE ID</th> "
                    + "<th>MATERIAL PASS NO.</th>"
                    + "<th>DURATION</th>"
                    + "<th>CURRENT STATUS</th>"
                    + "</tr>"
                    + table()
                    + "</table>"
                    + "<br />Thank you." //msg
            );
        }

//        }
    }

//     @Scheduled(cron = "0 0 */6 * * *") //every 6 hour
//    @Scheduled(cron = "0 14 17 * * ?") //every 8:00 AM - cron (sec min hr daysOfMth month daysOfWeek year(optional))
    public void cronRun2() throws FileNotFoundException, IOException {
        LOGGER.info("Upper Spec Limit (USL Retrieval) executed at everyday on 8:00 am. Current time is : " + new Date());

        String username = System.getProperty("user.name");
        if (!"fg79cj".equals(username)) {
            username = "imperial";
        }
        DateFormat dateFormat = new SimpleDateFormat("ddMMMyyyy");
        Date date = new Date();
        String todayDate = dateFormat.format(date);

        String reportName = "C:\\Users\\" + username + "\\Documents\\CDARS\\HIMS USL for Retrieving from SBN Factory Report (" + todayDate + ").xls";

        FileOutputStream fileOut = new FileOutputStream(reportName);
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("HIMS PROCESS EXCEED USL");
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBoldweight(HSSFFont.COLOR_NORMAL);
        font.setBold(true);
        font.setColor(HSSFColor.DARK_BLUE.index);
        style.setFont(font);
        sheet.createFreezePane(0, 1); // Freeze 1st Row

        HSSFRow rowhead = sheet.createRow((short) 0);
        rowhead.setRowStyle(style);

        HSSFCell cell1_0 = rowhead.createCell(0);
        cell1_0.setCellStyle(style);
        cell1_0.setCellValue("HARDWARE TYPE");

        HSSFCell cell1_1 = rowhead.createCell(1);
        cell1_1.setCellStyle(style);
        cell1_1.setCellValue("HARDWARE ID");

        HSSFCell cell1_2 = rowhead.createCell(2);
        cell1_2.setCellStyle(style);
        cell1_2.setCellValue("MATERIAL PASS NO");

        HSSFCell cell1_3 = rowhead.createCell(3);
        cell1_3.setCellStyle(style);
        cell1_3.setCellValue("DURATION");

        HSSFCell cell1_4 = rowhead.createCell(4);
        cell1_4.setCellStyle(style);
        cell1_4.setCellValue("CURRENT STATUS");

        String materialPassNo = "";
        String hardwareId = "";
        String hardwareType = "";
        String duration = "";
        String status = "";
        String text = "";
        boolean mbCardBibPart = false;
        boolean ate = false;
        boolean eqp = false;
        boolean pcbStencilTray = false;

        WhStatusLogDAO statusD = new WhStatusLogDAO();
        List<WhStatusLog> whUslList = statusD.getTLRetrieveRequestToCloseList();

        boolean checksize1 = false;
        for (int i = 0; i < whUslList.size(); i++) {
            checksize1 = true;
            hardwareType = whUslList.get(i).getEquipmentType();
            if ("Load Card".equals(whUslList.get(i).getEquipmentType())) {
                hardwareId = whUslList.get(i).getLoadCard();
            } else if ("Program Card".equals(whUslList.get(i).getEquipmentType())) {
                hardwareId = whUslList.get(i).getProgramCard();
            } else if ("Load Card & Program Card".equals(whUslList.get(i).getEquipmentType())) {
                hardwareId = whUslList.get(i).getLoadCard() + " & " + whUslList.get(i).getProgramCard();
            } else {
                hardwareId = whUslList.get(i).getEquipmentId();
            }

            materialPassNo = whUslList.get(i).getMpNo();
            String hourReqVer = whUslList.get(i).getRequestToVerifiedDate24();
            String hourReqVerIfNull = whUslList.get(i).getRequestToVerifiedDateTemp24();
            String hourVerShip = whUslList.get(i).getVerifiedDatetoShipDate24();
            String hourVerShipIfNull = whUslList.get(i).getVerifiedDatetoShipDateTemp24();
            String hourShipBScan = whUslList.get(i).getShipDateToBsScan24();
            String hourShipBScanIfNull = whUslList.get(i).getShipDateToBsScanTemp24();
            String hourBScanTT = whUslList.get(i).getBsScanToTtScan24();
            String hourBScanTTIfNull = whUslList.get(i).getBsScanToTtScanTemp24();

            boolean flag = false;

            if (hourReqVerIfNull != null) {
                if (Integer.parseInt(hourReqVerIfNull) >= 24 && hourReqVer == null) {
                    duration = whUslList.get(i).getRequestToVerifiedDateTemp();
                    status = "Pending Box Barcode Verification at SBN Factory";
                    flag = true;
                    if ("Motherboard".equals(whUslList.get(i).getEquipmentType())) {
                        mbCardBibPart = true;
                    } else if ("Tray".equals(whUslList.get(i).getEquipmentType())) {
                        pcbStencilTray = true;
                    } else if ("PCB".equals(whUslList.get(i).getEquipmentType())) {
                        pcbStencilTray = true;
                    } else if ("Stencil".equals(whUslList.get(i).getEquipmentType())) {
                        pcbStencilTray = true;
                    } else if (whUslList.get(i).getEquipmentType().contains("EQP_")) {
                        eqp = true;
                    } else if (whUslList.get(i).getEquipmentType().contains("ATE_")) {
                        ate = true;
                    } else if (whUslList.get(i).getEquipmentType().contains("Card")) {
                        mbCardBibPart = true;
                    } else if (whUslList.get(i).getEquipmentType().contains("Bib Parts")) {
                        mbCardBibPart = true;
                    }
                }
            }

            if (hourVerShipIfNull != null) {
                if (Integer.parseInt(hourVerShipIfNull) >= 24 && hourVerShip == null && hourReqVer != null) {
                    duration = whUslList.get(i).getVerifiedDatetoShipDateTemp();
                    status = "Pending Shipping Packing List";
                    flag = true;
                    if ("Motherboard".equals(whUslList.get(i).getEquipmentType())) {
                        mbCardBibPart = true;
                    } else if ("Tray".equals(whUslList.get(i).getEquipmentType())) {
                        pcbStencilTray = true;
                    } else if ("PCB".equals(whUslList.get(i).getEquipmentType())) {
                        pcbStencilTray = true;
                    } else if ("Stencil".equals(whUslList.get(i).getEquipmentType())) {
                        pcbStencilTray = true;
                    } else if (whUslList.get(i).getEquipmentType().contains("EQP_")) {
                        eqp = true;
                    } else if (whUslList.get(i).getEquipmentType().contains("ATE_")) {
                        ate = true;
                    } else if (whUslList.get(i).getEquipmentType().contains("Card")) {
                        mbCardBibPart = true;
                    } else if (whUslList.get(i).getEquipmentType().contains("Bib Parts")) {
                        mbCardBibPart = true;
                    }
                }
            }

            if (hourShipBScanIfNull != null) {
                if (Integer.parseInt(hourShipBScanIfNull) >= 24 && hourShipBScan == null && hourVerShip != null) {
                    duration = whUslList.get(i).getShipDateToBsScanTemp();
                    status = "Pending Box Barcode Verification at Rel Lab";
                    flag = true;
                    if ("Motherboard".equals(whUslList.get(i).getEquipmentType())) {
                        mbCardBibPart = true;
                    } else if ("Tray".equals(whUslList.get(i).getEquipmentType())) {
                        pcbStencilTray = true;
                    } else if ("PCB".equals(whUslList.get(i).getEquipmentType())) {
                        pcbStencilTray = true;
                    } else if ("Stencil".equals(whUslList.get(i).getEquipmentType())) {
                        pcbStencilTray = true;
                    } else if (whUslList.get(i).getEquipmentType().contains("EQP_")) {
                        eqp = true;
                    } else if (whUslList.get(i).getEquipmentType().contains("ATE_")) {
                        ate = true;
                    } else if (whUslList.get(i).getEquipmentType().contains("Card")) {
                        mbCardBibPart = true;
                    } else if (whUslList.get(i).getEquipmentType().contains("Bib Parts")) {
                        mbCardBibPart = true;
                    }
                }
            }

            if (hourBScanTTIfNull != null) {
                if (Integer.parseInt(hourBScanTTIfNull) >= 24 && hourBScanTT == null && hourShipBScan != null) {
                    duration = whUslList.get(i).getBsScanToTtScanTemp();
                    status = "Pending Trip Ticket Verification at Rel Lab";
                    flag = true;
                    if ("Motherboard".equals(whUslList.get(i).getEquipmentType())) {
                        mbCardBibPart = true;
                    } else if ("Tray".equals(whUslList.get(i).getEquipmentType())) {
                        pcbStencilTray = true;
                    } else if ("PCB".equals(whUslList.get(i).getEquipmentType())) {
                        pcbStencilTray = true;
                    } else if ("Stencil".equals(whUslList.get(i).getEquipmentType())) {
                        pcbStencilTray = true;
                    } else if (whUslList.get(i).getEquipmentType().contains("EQP_")) {
                        eqp = true;
                    } else if (whUslList.get(i).getEquipmentType().contains("ATE_")) {
                        ate = true;
                    } else if (whUslList.get(i).getEquipmentType().contains("Card")) {
                        mbCardBibPart = true;
                    } else if (whUslList.get(i).getEquipmentType().contains("Bib Parts")) {
                        mbCardBibPart = true;
                    }
                }
            }

            if (flag == true) {
                HSSFRow contents = sheet.createRow(sheet.getLastRowNum() + 1);
//                
                HSSFCell cell2_0 = contents.createCell(0);
                cell2_0.setCellValue(hardwareType);

                HSSFCell cell2_1 = contents.createCell(1);
                cell2_1.setCellValue(hardwareId);

                HSSFCell cell2_2 = contents.createCell(2);
                cell2_2.setCellValue(materialPassNo);

                HSSFCell cell2_3 = contents.createCell(3);
                cell2_3.setCellValue(duration);

                HSSFCell cell2_4 = contents.createCell(4);
                cell2_4.setCellValue(status);
            }
        }

        if (checksize1 == true) {
            workbook.write(fileOut);
            workbook.close();

            //send email
            LOGGER.info("send email to person in charge");
            EmailSender emailSender = new EmailSender();
            com.onsemi.cdars.model.User user = new com.onsemi.cdars.model.User();
            user.setFullname("All");

            List<String> a = new ArrayList<String>();

            String emailApprover = "";
            String emaildistList1 = "";
            String emaildistList2 = "";
            String emaildistList3 = "";
            String emaildistList4 = "";
            String emailMbCardBibPart = "";
            String emailPcbTrayStencil = "";
            String emailAte = "";
            String emailEqp = "";

            emailApprover = "fg79cj@onsemi.com";
            a.add(emailApprover);

            EmailConfigDAO econfD = new EmailConfigDAO();
            int countDistList1 = econfD.getCountTask("Dist List 1");
            if (countDistList1 == 1) {
                econfD = new EmailConfigDAO();
                EmailConfig distList1 = econfD.getEmailConfigByTask("Dist List 1");
                emaildistList1 = distList1.getEmail();
                a.add(emaildistList1);
            }
            econfD = new EmailConfigDAO();
            int countDistList2 = econfD.getCountTask("Dist List 2");
            if (countDistList2 == 1) {
                econfD = new EmailConfigDAO();
                EmailConfig distList2 = econfD.getEmailConfigByTask("Dist List 2");
                emaildistList2 = distList2.getEmail();
                a.add(emaildistList2);
            }
            econfD = new EmailConfigDAO();
            int countDistList3 = econfD.getCountTask("Dist List 3");
            if (countDistList3 == 1) {
                econfD = new EmailConfigDAO();
                EmailConfig distList3 = econfD.getEmailConfigByTask("Dist List 3");
                emaildistList3 = distList3.getEmail();
                a.add(emaildistList3);
            }
            econfD = new EmailConfigDAO();
            int countDistList4 = econfD.getCountTask("Dist List 4");
            if (countDistList4 == 1) {
                econfD = new EmailConfigDAO();
                EmailConfig distList4 = econfD.getEmailConfigByTask("Dist List 4");
                emaildistList4 = distList4.getEmail();
                a.add(emaildistList4);
            }
            if (mbCardBibPart == true) {
                econfD = new EmailConfigDAO();
                int countMb = econfD.getCountTask("Motherboard");
                if (countMb == 1) {
                    econfD = new EmailConfigDAO();
                    EmailConfig distMb = econfD.getEmailConfigByTask("Motherboard");
                    emailMbCardBibPart = distMb.getEmail();
                    a.add(emailMbCardBibPart);
                }
            }
            if (pcbStencilTray == true) {
                econfD = new EmailConfigDAO();
                int countpcbStencilTray = econfD.getCountTask("Stencil");
                if (countpcbStencilTray == 1) {
                    econfD = new EmailConfigDAO();
                    EmailConfig distpcb = econfD.getEmailConfigByTask("Stencil");
                    emailPcbTrayStencil = distpcb.getEmail();
                    a.add(emailPcbTrayStencil);
                }
            }
            if (eqp == true) {
                econfD = new EmailConfigDAO();
                int countEqp = econfD.getCountTask("EQP");
                if (countEqp == 1) {
                    econfD = new EmailConfigDAO();
                    EmailConfig disteqp = econfD.getEmailConfigByTask("EQP");
                    emailEqp = disteqp.getEmail();
                    a.add(emailEqp);
                }
            }
            if (ate == true) {
                econfD = new EmailConfigDAO();
                int countAte = econfD.getCountTask("ATE");
                if (countAte == 1) {
                    econfD = new EmailConfigDAO();
                    EmailConfig distAte = econfD.getEmailConfigByTask("ATE");
                    emailAte = distAte.getEmail();
                    a.add(emailAte);
                }
            }
            String[] myArray = new String[a.size()];
            String[] emailTo = a.toArray(myArray);
            LOGGER.info("emailTo: " + Arrays.toString(emailTo));
            LOGGER.info("ate: " + ate);
            LOGGER.info("eqp: " + eqp);
            LOGGER.info("pcbStencilTray: " + pcbStencilTray);
            LOGGER.info("mbCardBibPart: " + mbCardBibPart);
            //                String[] to = {"hmsrelon@gmail.com", "hmsrelontest@gmail.com"};  //9/11/16
//            String[] to = {"fg79cj@onsemi.com"};
            emailSender.htmlEmailWithAttachment(
                    servletContext,
                    user, //user name requestor
                    emailTo,
                    new File("C:\\Users\\" + username + "\\Documents\\CDARS\\HIMS USL for Retrieving from SBN Factory Report (" + todayDate + ").xls"),
                    "List of Hardware Exceed USL (24 hours) for Retrieval from SBN Factory", //subject
                    "Report for Hardware Process from HIMS(Hadware Retrieval from SBN Factory) that exceed Upper Specs Limit (24 hours) has been made. <br />"
                    + "Hence, attached is the report file for your view and perusal. <br /><br />"
                    + "<br /><br /> "
                    + "<style>table, th, td {border: 1px solid black;} </style>"
                    + "<table style=\"width:100%\">" //tbl
                    + "<tr>"
                    + "<th>HARDWARE TYPE</th> "
                    + "<th>HARDWARE ID</th> "
                    + "<th>MATERIAL PASS NO.</th>"
                    + "<th>DURATION</th>"
                    + "<th>CURRENT STATUS</th>"
                    + "</tr>"
                    + table2()
                    + "</table>"
                    + "<br />Thank you." //msg
            );
        }

//        }
    }

    private String table() {
        String materialPassNo = "";
        String hardwareId = "";
        String hardwareType = "";
        String duration = "";
        String status = "";
        String text = "";

        WhStatusLogDAO statusD = new WhStatusLogDAO();
//        List<WhStatusLog> whUslList = statusD.getTLReqToApproveAndApproveToMpCreatedList();
        List<WhStatusLog> whUslList = statusD.getTLReqToInventoryList();

        for (int i = 0; i < whUslList.size(); i++) {
            hardwareType = whUslList.get(i).getEquipmentType();
            if ("Load Card".equals(whUslList.get(i).getEquipmentType())) {
                hardwareId = whUslList.get(i).getLoadCard();
            } else if ("Program Card".equals(whUslList.get(i).getEquipmentType())) {
                hardwareId = whUslList.get(i).getProgramCard();
            } else if ("Load Card & Program Card".equals(whUslList.get(i).getEquipmentType())) {
                hardwareId = whUslList.get(i).getLoadCard() + " & " + whUslList.get(i).getProgramCard();
            } else {
                hardwareId = whUslList.get(i).getEquipmentId();
            }
            materialPassNo = whUslList.get(i).getMpNo();
            String hourReqApp = whUslList.get(i).getRequestToApprove24();
            String hourReqAppIfNull = whUslList.get(i).getRequestToApproveTemp24();
            String hourAppMp = whUslList.get(i).getApproveToMPCreated24();
            String hourAppMpIfNull = whUslList.get(i).getApproveToMPCreatedTemp24();
            String hourMpTt = whUslList.get(i).getMpCreatedToTtScan24();
            String hourMpTtIfNull = whUslList.get(i).getMpCreatedToTtScanTemp24();
            String hourTtBs = whUslList.get(i).getTtScanToBsScan24();
            String hourTtBsIfNull = whUslList.get(i).getTtScanToBsScanTemp24();
            String hourBsShip = whUslList.get(i).getBsScanToShip24();
            String hourBsShipIfNull = whUslList.get(i).getBsScanToShipTemp24();
            String hourShipInv = whUslList.get(i).getShipToInventory24();
            String hourShipInvIfNull = whUslList.get(i).getShipToInventoryTemp24();

            boolean flag = false;

            if (hourReqAppIfNull != null) {
                if (Integer.parseInt(hourReqAppIfNull) >= 24 && hourReqApp == null) {
                    duration = whUslList.get(i).getRequestToApproveTemp();
                    status = "Pending Approval";
                    flag = true;
                }
            }

            if (hourAppMpIfNull != null) {
                if (Integer.parseInt(hourAppMpIfNull) >= 24 && hourAppMp == null && hourReqApp != null) {
                    duration = whUslList.get(i).getApproveToMPCreatedTemp();
                    status = "Pending Material Pass Number";
                    flag = true;
                }
            }

            if (hourMpTtIfNull != null) {
                if (Integer.parseInt(hourMpTtIfNull) >= 24 && hourMpTt == null) {
                    duration = whUslList.get(i).getMpCreatedToTtScanTemp();
                    status = "Pending Trip Ticket Scanning";
                    flag = true;
                }
            }

            if (hourTtBsIfNull != null) {
                if (Integer.parseInt(hourTtBsIfNull) >= 24 && hourTtBs == null && hourMpTt != null) {
                    duration = whUslList.get(i).getTtScanToBsScanTemp();
                    status = "Pending Barcode Sticker Scanning";
                    flag = true;
                }
            }

            if (hourBsShipIfNull != null) {
                if (Integer.parseInt(hourBsShipIfNull) >= 24 && hourBsShip == null && hourTtBs != null && hourMpTt != null) {
                    duration = whUslList.get(i).getBsScanToShipTemp();
                    status = "Pending Shipping Packing List";
                    flag = true;
                }
            }

            if (hourShipInvIfNull != null) {
                if (Integer.parseInt(hourShipInvIfNull) >= 24 && hourShipInv == null && hourBsShip != null && hourTtBs != null && hourMpTt != null) {
                    duration = whUslList.get(i).getShipToInventoryTemp();
                    status = "Pending Inventory in Seremban Factory";
                    flag = true;
                }
            }

            if (flag == true) {
                text = text + "<tr align = \"center\">";
                text = text + "<td>" + hardwareType + "</td>";
                text = text + "<td>" + hardwareId + "</td>";
                text = text + "<td>" + materialPassNo + "</td>";
                text = text + "<td>" + duration + "</td>";
                text = text + "<td>" + status + "</td>";
                text = text + "</tr>";
            }
        }

//        WhStatusLogDAO statusD2 = new WhStatusLogDAO();
//        List<WhStatusLog> whUslList2 = statusD2.getTLMpCreatedToFinalInventoryDateList();
//
//        for (int i = 0; i < whUslList2.size(); i++) {
//            hardwareType = whUslList2.get(i).getEquipmentType();
//            hardwareId = whUslList2.get(i).getEquipmentId();
//            materialPassNo = whUslList2.get(i).getMpNo();
//            String hourMpTt = whUslList2.get(i).getMpCreatedToTtScan24();
//            String hourMpTtIfNull = whUslList2.get(i).getMpCreatedToTtScanTemp24();
//            String hourTtBs = whUslList2.get(i).getTtScanToBsScan24();
//            String hourTtBsIfNull = whUslList2.get(i).getTtScanToBsScanTemp24();
//            String hourBsShip = whUslList2.get(i).getBsScanToShip24();
//            String hourBsShipIfNull = whUslList2.get(i).getBsScanToShipTemp24();
//            String hourShipInv = whUslList2.get(i).getShipToInventory24();
//            String hourShipInvIfNull = whUslList2.get(i).getShipToInventoryTemp24();
//
//            boolean flag = false;
//
//            if (hourMpTtIfNull != null) {
//                if (Integer.parseInt(hourMpTtIfNull) >= 24 && hourMpTt == null) {
//                    duration = whUslList2.get(i).getMpCreatedToTtScanTemp();
//                    status = "Pending Trip Ticket Scanning";
//                    flag = true;
//                }
//            }
//
//            if (hourTtBsIfNull != null) {
//                if (Integer.parseInt(hourTtBsIfNull) >= 24 && hourTtBs == null && hourMpTt != null) {
//                    duration = whUslList2.get(i).getTtScanToBsScanTemp();
//                    status = "Pending Barcode Sticker Scanning";
//                    flag = true;
//                }
//            }
//
//            if (hourBsShipIfNull != null) {
//                if (Integer.parseInt(hourBsShipIfNull) >= 24 && hourBsShip == null && hourTtBs != null && hourMpTt != null) {
//                    duration = whUslList2.get(i).getBsScanToShipTemp();
//                    status = "Pending Shipping Packing List";
//                    flag = true;
//                }
//            }
//
//            if (hourShipInvIfNull != null) {
//                if (Integer.parseInt(hourShipInvIfNull) >= 24 && hourShipInv == null && hourBsShip != null && hourTtBs != null && hourMpTt != null) {
//                    duration = whUslList2.get(i).getShipToInventoryTemp();
//                    status = "Pending Inventory in Seremban Factory";
//                    flag = true;
//                }
//            }
//
//            if (flag == true) {
//                text = text + "<tr align = \"center\">";
//                text = text + "<td>" + hardwareType + "</td>";
//                text = text + "<td>" + hardwareId + "</td>";
//                text = text + "<td>" + materialPassNo + "</td>";
//                text = text + "<td>" + duration + "</td>";
//                text = text + "<td>" + status + "</td>";
//                text = text + "</tr>";
//            }
//        }
        return text;
    }

    private String table2() {
        String materialPassNo = "";
        String hardwareId = "";
        String hardwareType = "";
        String duration = "";
        String status = "";
        String text = "";

        WhStatusLogDAO statusD = new WhStatusLogDAO();
        List<WhStatusLog> whUslList = statusD.getTLRetrieveRequestToCloseList();

        for (int i = 0; i < whUslList.size(); i++) {
            hardwareType = whUslList.get(i).getEquipmentType();
            if ("Load Card".equals(whUslList.get(i).getEquipmentType())) {
                hardwareId = whUslList.get(i).getLoadCard();
            } else if ("Program Card".equals(whUslList.get(i).getEquipmentType())) {
                hardwareId = whUslList.get(i).getProgramCard();
            } else if ("Load Card & Program Card".equals(whUslList.get(i).getEquipmentType())) {
                hardwareId = whUslList.get(i).getLoadCard() + " & " + whUslList.get(i).getProgramCard();
            } else {
                hardwareId = whUslList.get(i).getEquipmentId();
            }
            materialPassNo = whUslList.get(i).getMpNo();
            String hourReqVer = whUslList.get(i).getRequestToVerifiedDate24();
            String hourReqVerIfNull = whUslList.get(i).getRequestToVerifiedDateTemp24();
            String hourVerShip = whUslList.get(i).getVerifiedDatetoShipDate24();
            String hourVerShipIfNull = whUslList.get(i).getVerifiedDatetoShipDateTemp24();
            String hourShipBScan = whUslList.get(i).getShipDateToBsScan24();
            String hourShipBScanIfNull = whUslList.get(i).getShipDateToBsScanTemp24();
            String hourBScanTT = whUslList.get(i).getBsScanToTtScan24();
            String hourBScanTTIfNull = whUslList.get(i).getBsScanToTtScanTemp24();

            boolean flag = false;

            if (hourReqVerIfNull != null) {
                if (Integer.parseInt(hourReqVerIfNull) >= 24 && hourReqVer == null) {
                    duration = whUslList.get(i).getRequestToVerifiedDateTemp();
                    status = "Pending Box Barcode Verification at SBN Factory";
                    flag = true;
                }
            }

            if (hourVerShipIfNull != null) {
                if (Integer.parseInt(hourVerShipIfNull) >= 24 && hourVerShip == null && hourReqVer != null) {
                    duration = whUslList.get(i).getVerifiedDatetoShipDateTemp();
                    status = "Pending Shipping Packing List";
                    flag = true;
                }
            }

            if (hourShipBScanIfNull != null) {
                if (Integer.parseInt(hourShipBScanIfNull) >= 24 && hourShipBScan == null && hourVerShip != null) {
                    duration = whUslList.get(i).getShipDateToBsScanTemp();
                    status = "Pending Box Barcode Verification at Rel Lab";
                    flag = true;
                }
            }

            if (hourBScanTTIfNull != null) {
                if (Integer.parseInt(hourBScanTTIfNull) >= 24 && hourBScanTT == null && hourShipBScan != null) {
                    duration = whUslList.get(i).getBsScanToTtScanTemp();
                    status = "Pending Trip Ticket Verification at Rel Lab";
                    flag = true;
                }
            }

            if (flag == true) {
                text = text + "<tr align = \"center\">";
                text = text + "<td>" + hardwareType + "</td>";
                text = text + "<td>" + hardwareId + "</td>";
                text = text + "<td>" + materialPassNo + "</td>";
                text = text + "<td>" + duration + "</td>";
                text = text + "<td>" + status + "</td>";
                text = text + "</tr>";
            }
        }
        return text;
    }

//    @Scheduled(cron = "0 38 07 * * ?") //every 1st day of month at 9:00 AM - cron (sec min hr daysOfMth month daysOfWeek year(optional))
    @Scheduled(cron = "0 0 09 01 * ?") //every 1st day of month at 9:00 AM - cron (sec min hr daysOfMth month daysOfWeek year(optional))
    public void cronForMonthlyReport() throws FileNotFoundException, IOException {
        LOGGER.info("Upper Spec Limit (USL Shipping) executed at everyday on 8:00 am. Current time is : " + new Date());

        String username = System.getProperty("user.name");
        if (!"fg79cj".equals(username)) {
            username = "imperial";
        }
//        DateFormat dateFormat = new SimpleDateFormat("MMM yyyy");
//        Date date = new Date();
//        String todayDate = dateFormat.format(date);

        Calendar now = Calendar.getInstance();
        Integer year = now.get(Calendar.YEAR);

        //dont plus one bcoz wanna get previous month 
//        Integer monthTemp = now.get(Calendar.MONTH) + 1; //month start from 0 - 11
        Integer monthTemp = now.get(Calendar.MONTH);
        if (monthTemp < 1) {
            year = year - 1;
            monthTemp = 12;
        }
        Month monthHead = Month.of(monthTemp);
        String monthNameHeadFull = monthHead.name();

        String monthNameHead = monthNameHeadFull.substring(1, 3).toLowerCase();
        String monthNameHead2 = monthNameHeadFull.substring(0, 1);
        String todayDate = monthNameHead2 + monthNameHead + " " + year;

        String reportName = "C:\\Users\\" + username + "\\Documents\\CDARS\\SF and SBN Rel - Time Lapse Report Monthly Performance (" + todayDate + ").xls";

        FileOutputStream fileOut = new FileOutputStream(reportName);
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("SF and SBN Rel - Time Lapse Report Monthly Performance (" + todayDate + ")");
        sheet.setDefaultColumnWidth(10);
        sheet.setDisplayGridlines(false);

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

//        sheet.createFreezePane(0, 1); // Freeze 1st Row
        //create dynamic rownum
        Short rowNum = 0;

        HSSFRow rowtitle = sheet.createRow((short) rowNum);
//        rowtitle.setRowStyle(style);

        HSSFCell cellt_0 = rowtitle.createCell(0);
        cellt_0.setCellStyle(style);
        cellt_0.setCellValue("SF and SBN Rel - Time Lapse Report Monthly Performance (" + todayDate + ")");

        //add 1 row to current rowNum
        rowNum = (short) (rowNum + 2);

        HSSFRow rowhead = sheet.createRow((short) rowNum);
//        rowhead.setRowStyle(style);

        HSSFCell cell1_0 = rowhead.createCell(0);
        cell1_0.setCellStyle(styleBlueWithBorder);
        cell1_0.setCellValue("Time Lapse Category");

//        Calendar now = Calendar.getInstance();
//        Integer year = now.get(Calendar.YEAR);
//        Integer monthTemp = now.get(Calendar.MONTH) + 1;
        //add 1 row to current rowNum
        rowNum = (short) (rowNum + 1);

        //insert to first row for ship to SF
        HSSFRow rowforShiptoSF = sheet.createRow((short) rowNum);

        HSSFCell celltitleShpToSf = rowforShiptoSF.createCell(0);
        celltitleShpToSf.setCellStyle(styleWithBorder);
        celltitleShpToSf.setCellValue("Rel Lab to Storage Factory");

        //add 1 row to current rowNum
        rowNum = (short) (rowNum + 1);

        //insert to 2nd row for ship to RL
        HSSFRow rowforShiptoRL = sheet.createRow((short) rowNum);

        HSSFCell celltitleShpToRL = rowforShiptoRL.createCell(0);
        celltitleShpToRL.setCellStyle(styleWithBorder);
        celltitleShpToRL.setCellValue("Storage Factory to Rel Lab");

        HSSFFont fontRed = workbook.createFont();
        fontRed.setColor(HSSFColor.RED.index);

        Integer cellCol = 1;

        //current month minus 1 coz wanna start from previous month
//        monthTemp = monthTemp - 1;
        //loop for total activity
        for (int x = 1; x <= 12; x++) {
            if (monthTemp < 1) {
//                year = year - 1;
                year -= 1;
                monthTemp = 12;
            }

            Month month = Month.of(monthTemp);
            String monthNameFull = month.name();

            String monthName = monthNameFull.substring(0, 3);
            String year1 = year.toString().substring(2, 4);

            //total Ship to SF
            WhUslReportDAO count = new WhUslReportDAO();
            Integer totalItemShip = count.getCountTotalShipByYearAndByMonth(monthTemp.toString(), year.toString());

            count = new WhUslReportDAO();
            Integer totalFailShip = count.getCountShipFailByYearAndByMonth(monthTemp.toString(), year.toString());

            count = new WhUslReportDAO();
            Integer totalRetrieveItem = count.getCountTotalRetrieveByYearAndByMonth(monthTemp.toString(), year.toString());

            count = new WhUslReportDAO();
            Integer totalRetrieveFail = count.getCountRetrieveFailByYearAndByMonth(monthTemp.toString(), year.toString());

            HSSFCell cell1_month = rowhead.createCell(cellCol);
            cell1_month.setCellStyle(style);
            cell1_month.setCellStyle(styleBlueWithBorder);
            cell1_month.setCellValue(monthName + "-" + year1);

            HSSFRichTextString ship = new HSSFRichTextString(totalFailShip + "/" + totalItemShip);
            ship.applyFont(0, Integer.toString(totalFailShip).length(), fontRed);

            HSSFCell cellContentDec16 = rowforShiptoSF.createCell(cellCol);
            cellContentDec16.setCellStyle(styleWithBorder);
            cellContentDec16.setCellValue(ship);

            HSSFRichTextString retrieve = new HSSFRichTextString(totalRetrieveFail + "/" + totalRetrieveItem);
            retrieve.applyFont(0, Integer.toString(totalRetrieveFail).length(), fontRed);

            HSSFCell cellContentRetrieveDec16 = rowforShiptoRL.createCell(cellCol);
            cellContentRetrieveDec16.setCellStyle(styleWithBorder);
            cellContentRetrieveDec16.setCellValue(retrieve);

//            monthTemp = monthTemp - 1;
//            cellCol = cellCol + 1;
            monthTemp -= 1;
            cellCol += 1;
        }

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

        Calendar now2 = Calendar.getInstance();
        Integer year2 = now2.get(Calendar.YEAR);
//        Integer monthTemp2 = now2.get(Calendar.MONTH) + 1; //dont add 1 becoz wanna start with previous month
        Integer monthTemp2 = now2.get(Calendar.MONTH);

        Integer cellColumn = 0;
        Integer stop = 0; //to stop on december

        //loop for all item details in month
//        for (int x = 12; x > 0; x--) {
        for (int x = 12; x > 0 && stop == 0; x--) { //stop loop when reach december

            if (monthTemp2 < 1) {
//                year2 = year2 - 1;
                year2 -= 1;
                monthTemp2 = 12;
                stop = 1;
            }

            if (monthTemp2 == 12) {
                stop = 1;
            }

            //insert activity failed
            String failSteps = "";
            String flag = "0";

            //add 2 row to current rowNum
            rowNum = (short) (rowNum + 2);

            Month month = Month.of(monthTemp2);
            String monthName = month.name();
            String yearSub = year2.toString().substring(2, 4);

            //Item fail header Dec 17
            HSSFRow rowforItemFailDec17 = sheet.createRow((short) rowNum);
            HSSFCell cell1Dec17 = rowforItemFailDec17.createCell(0);
            cell1Dec17.setCellStyle(style2);
            cell1Dec17.setCellValue(monthName + " " + yearSub);

            //add 1 row to current rowNum
            rowNum = (short) (rowNum + 1);

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

            WhUslReportDAO countShipFail = new WhUslReportDAO();
            Integer totalFailShip = countShipFail.getCountShipFailByYearAndByMonth(monthTemp2.toString(), year2.toString());
//                Integer totalFailShip = countShipFail.getCountShipFailCurrentYearWithCustomMonth(Integer.toString(x));

            if (totalFailShip > 0) {

                WhUslReportDAO fail = new WhUslReportDAO();
//                List<WhUslReport> failShipDec17 = fail.GetListOfFailedShipItemByYearAndByMonth(monthTemp2.toString(), year2.toString());
                List<WhUslReport> failShipDec17 = fail.GetListOfFailedShipItemByYearAndByMonthLeftJoinTimelapseTableForExcel(monthTemp2.toString(), year2.toString());
                for (int i = 0; i < failShipDec17.size(); i++) {
                    //add 1 row to current rowNum
                    rowNum = (short) (rowNum + 1);
                    //insert to failed item details for dec 17
                    HSSFRow rowforShiptoSFFail = sheet.createRow((short) rowNum);

                    HSSFCell celltitleShpToSFFailType = rowforShiptoSFFail.createCell(0);
                    celltitleShpToSFFailType.setCellStyle(styleWithBorder);
                    celltitleShpToSFFailType.setCellValue(failShipDec17.get(i).getEqptType());

                    HSSFCell celltitleShpToSFFailId = rowforShiptoSFFail.createCell(1);
                    celltitleShpToSFFailId.setCellStyle(styleWithBorder);
                    if ("Load Card".equals(failShipDec17.get(i).getEqptType())) {
                        celltitleShpToSFFailId.setCellValue(failShipDec17.get(i).getLoadCard());
                    } else if ("Program Card".equals(failShipDec17.get(i).getEqptType())) {
                        celltitleShpToSFFailId.setCellValue(failShipDec17.get(i).getProgramCard());
                    } else if ("Load Card & Program Card".equals(failShipDec17.get(i).getEqptType())) {
                        celltitleShpToSFFailId.setCellValue(failShipDec17.get(i).getLoadCard() + " & " + failShipDec17.get(i).getProgramCard());
                    } else {
                        celltitleShpToSFFailId.setCellValue(failShipDec17.get(i).getEqptId());
                    }

                    HSSFCell celltitleShpToSFFailMP = rowforShiptoSFFail.createCell(4);
                    celltitleShpToSFFailMP.setCellStyle(styleWithBorder);
                    celltitleShpToSFFailMP.setCellValue(failShipDec17.get(i).getMpNo().toLowerCase());

                    HSSFCell celltitleShpToSFFailDur = rowforShiptoSFFail.createCell(6);
                    celltitleShpToSFFailDur.setCellStyle(styleWithBorder);
                    celltitleShpToSFFailDur.setCellValue(failShipDec17.get(i).getTotalHourTakeShip());

                    failSteps = "";
                    flag = "0";

                    if (Integer.parseInt(failShipDec17.get(i).getReqToApp()) > 24) {
                        failSteps = "Requested to Approved";
                        flag = "1";
                    }

                    if (Integer.parseInt(failShipDec17.get(i).getMpCreateToTtScan()) > 24) {
                        if ("1".equals(flag)) {
                            failSteps = failSteps + "; Material Pass Inserted to Trip ticket Scanned";
                            flag = "1";
                        } else {
                            failSteps = "Material Pass Inserted to Trip ticket Scanned";
                            flag = "1";
                        }
                    }

                    if (Integer.parseInt(failShipDec17.get(i).getTtScanToBcScan()) > 24) {
                        if ("1".equals(flag)) {
                            failSteps = failSteps + "; Trip ticket Scanned to Barcode Sticker Scanned";
                            flag = "1";
                        } else {
                            failSteps = "Trip ticket Scanned to Barcode Sticker Scanned";
                            flag = "1";
                        }
                    }

                    if (Integer.parseInt(failShipDec17.get(i).getBcScanToShip()) > 24) {
                        if ("1".equals(flag)) {
                            failSteps = failSteps + "; Barcode Sticker Scanned to Shipped";
                            flag = "1";
                        } else {
                            failSteps = "Barcode Sticker Scanned to Shipped";
                            flag = "1";
                        }
                    }

                    if (Integer.parseInt(failShipDec17.get(i).getShipToInv()) > 24) {
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
                    celltitleFailShipRc.setCellValue(failShipDec17.get(i).getRootCause());

                    HSSFCell celltitleFailShipCa = rowforShiptoSFFail.createCell(14);
                    celltitleFailShipCa.setCellStyle(styleWithBorder);
                    celltitleFailShipCa.setCellValue(failShipDec17.get(i).getCa());

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

            //add 2 row to current rowNum
            rowNum = (short) (rowNum + 2);

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

            WhUslReportDAO countRetrieveFail = new WhUslReportDAO();
            Integer totalRetrieveFail = countRetrieveFail.getCountRetrieveFailByYearAndByMonth(monthTemp2.toString(), year2.toString());

            if (totalRetrieveFail > 0) {

                WhUslReportDAO fail = new WhUslReportDAO();
                List<WhUslReport> failShipDec17 = fail.GetListOfFailedRetrieveItemByYearAndByMonthLeftJoinTimelapseTableForExcel(monthTemp2.toString(), year2.toString());
                for (int i = 0; i < failShipDec17.size(); i++) {
                    //add 1 row to current rowNum
                    rowNum = (short) (rowNum + 1);
                    //insert to failed item details for dec 17
                    HSSFRow rowforShiptoSFFail = sheet.createRow((short) rowNum);

                    HSSFCell celltitleShpToSFFailType = rowforShiptoSFFail.createCell(0);
                    celltitleShpToSFFailType.setCellStyle(styleWithBorder);
                    celltitleShpToSFFailType.setCellValue(failShipDec17.get(i).getEqptType());

                    HSSFCell celltitleShpToSFFailId = rowforShiptoSFFail.createCell(1);
                    celltitleShpToSFFailId.setCellStyle(styleWithBorder);
                    if ("Load Card".equals(failShipDec17.get(i).getEqptType())) {
                        celltitleShpToSFFailId.setCellValue(failShipDec17.get(i).getLoadCard());
                    } else if ("Program Card".equals(failShipDec17.get(i).getEqptType())) {
                        celltitleShpToSFFailId.setCellValue(failShipDec17.get(i).getProgramCard());
                    } else if ("Load Card & Program Card".equals(failShipDec17.get(i).getEqptType())) {
                        celltitleShpToSFFailId.setCellValue(failShipDec17.get(i).getLoadCard() + " & " + failShipDec17.get(i).getProgramCard());
                    } else {
                        celltitleShpToSFFailId.setCellValue(failShipDec17.get(i).getEqptId());
                    }
                    HSSFCell celltitleShpToSFFailMP = rowforShiptoSFFail.createCell(4);
                    celltitleShpToSFFailMP.setCellStyle(styleWithBorder);
                    celltitleShpToSFFailMP.setCellValue(failShipDec17.get(i).getMpNo().toLowerCase());

                    HSSFCell celltitleShpToSFFailDur = rowforShiptoSFFail.createCell(6);
                    celltitleShpToSFFailDur.setCellStyle(styleWithBorder);
                    celltitleShpToSFFailDur.setCellValue(failShipDec17.get(i).getTotalHourTakeRetrieve());

                    flag = "0";
                    failSteps = "";

                    if (Integer.parseInt(failShipDec17.get(i).getReqToVer()) > 24) {
                        failSteps = "Requested to SF Verified";
                        flag = "1";
                    }

                    if (Integer.parseInt(failShipDec17.get(i).getVerToShip()) > 24) {
                        if ("1".equals(flag)) {
                            failSteps = failSteps + "; SF Verified to Shipped";
                            flag = "1";
                        } else {
                            failSteps = "SF Verified to Shipped";
                            flag = "1";
                        }
                    }

                    if (Integer.parseInt(failShipDec17.get(i).getShipToBcScan()) > 24) {
                        if ("1".equals(flag)) {
                            failSteps = failSteps + "; Shipped to Barcode Sticker Scanned";
                            flag = "1";
                        } else {
                            failSteps = "Shipped to Barcode Sticker Scanned";
                            flag = "1";
                        }
                    }

                    if (Integer.parseInt(failShipDec17.get(i).getBcScanToTtScan()) > 24) {
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
                    celltitleFailStepFailRc.setCellValue(failShipDec17.get(i).getRootCause());

                    HSSFCell celltitleFailStepFailCa = rowforShiptoSFFail.createCell(14);
                    celltitleFailStepFailCa.setCellStyle(styleWithBorder);
                    celltitleFailStepFailCa.setCellValue(failShipDec17.get(i).getCa());

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

            //add 2 row to current rowNum
//            rowNum = (short) (rowNum + 2);
            flag = "0";
            failSteps = "";

//            monthTemp2 = monthTemp2 - 1;
//            cellColumn = cellColumn + 1;
            monthTemp2 -= 1;
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

        workbook.write(fileOut);
        workbook.close();

        //send email
        LOGGER.info("send email to person in charge");
        EmailSender emailSender = new EmailSender();
        com.onsemi.cdars.model.User user = new com.onsemi.cdars.model.User();
        user.setFullname("All");

        EmailTimelapseDAO tlD = new EmailTimelapseDAO();
        Integer countCc = tlD.getCountCc();

        if (countCc > 0) { //use htmlEmailWithAttachmentWithCc
            List<String> Cc = new ArrayList<String>();
            tlD = new EmailTimelapseDAO();
            List<EmailTimelapse> CcList = tlD.getEmailTimelapseListForCc();
            for (EmailTimelapse b : CcList) {
                Cc.add(b.getEmail());
            }
            String[] myArrayCc = new String[Cc.size()];
            String[] emailCc = Cc.toArray(myArrayCc);

            List<String> To = new ArrayList<String>();
            tlD = new EmailTimelapseDAO();
            List<EmailTimelapse> toList = tlD.getEmailTimelapseListForDistList();
            for (EmailTimelapse to : toList) {
                To.add(to.getEmail());
            }
            String[] myArrayTo = new String[To.size()];
            String[] emailTo = To.toArray(myArrayTo);

            emailSender.htmlEmailWithAttachmentWithCc(
                    servletContext,
                    user, //user name requestor
                    emailTo,
                    emailCc,
                    new File("C:\\Users\\" + username + "\\Documents\\CDARS\\SF and SBN Rel - Time Lapse Report Monthly Performance (" + todayDate + ").xls"),
                    "SF and SBN Rel - Time Lapse Report Monthly Performance (" + todayDate + ")", //subject
                    "Attached is the HIMS time-lapse report for " + todayDate + " . <br />"
                    + "<br />"
                    + "<br />Thank you." //msg
            );

        } else { //use htmlEmailWithAttachment 

            List<String> To = new ArrayList<String>();
            tlD = new EmailTimelapseDAO();
            List<EmailTimelapse> toList = tlD.getEmailTimelapseListForDistList();
            for (EmailTimelapse to : toList) {
                To.add(to.getEmail());
            }
            String[] myArrayTo = new String[To.size()];
            String[] emailTo = To.toArray(myArrayTo);

            emailSender.htmlEmailWithAttachment(
                    servletContext,
                    user, //user name requestor
                    emailTo,
                    new File("C:\\Users\\" + username + "\\Documents\\CDARS\\SF and SBN Rel - Time Lapse Report Monthly Performance (" + todayDate + ").xls"),
                    "SF and SBN Rel - Time Lapse Report Monthly Performance (" + todayDate + ")", //subject
                    "Attached is the HIMS time-lapse report for " + todayDate + " . <br />"
                    + "<br />"
                    + "<br />Thank you." //msg
            );
        }
    }

    @Scheduled(cron = "0 15 08 * * *") //every 8.15am
    public void EmailForPendingRCAndCA() {

        //utk inventory
//        LOGGER.info("cron detected -[Approval Reminder]!!!");
        try {

            Calendar now = Calendar.getInstance();
            Integer year = now.get(Calendar.YEAR);
            Integer stop = 0;

            Integer monthTemp = now.get(Calendar.MONTH) + 1; //month start from 0 - 11

            Integer countShip = 0;
            Integer countRetrieve = 0;

            for (int x = 12; x > 0 && stop == 0; x--) { //stop loop when reach december

                if (monthTemp < 1) {
                    year -= 1;
                    monthTemp = 12;
                    stop = 1;
                }

                if (monthTemp == 12) {
                    stop = 1;
                }

                WhUslReportDAO whUslReport = new WhUslReportDAO();
                Integer count = whUslReport.GetCountOfFailedShipItemByYearAndByMonthLeftJoinTimelapseTable(monthTemp.toString(), year.toString());
                countShip += count;

                whUslReport = new WhUslReportDAO();
                Integer countRet = whUslReport.GetCountOfFailedRetrieveItemByYearAndByMonthLeftJoinTimelapseTable(monthTemp.toString(), year.toString());
                countRetrieve += countRet;

                monthTemp -= 1;
            }

            if (countShip > 0 || countRetrieve > 0) {

                EmailSender emailSender = new EmailSender();
                com.onsemi.cdars.model.User user = new com.onsemi.cdars.model.User();
                user.setFullname("All");

                EmailRootcauseTimelapseDAO tlD = new EmailRootcauseTimelapseDAO();
                Integer countCc = tlD.getCountCc();

                if (countCc > 0) { //with Cc
                    List<String> Cc = new ArrayList<String>();
                    tlD = new EmailRootcauseTimelapseDAO();
                    List<EmailRootcauseTimelapse> CcList = tlD.getEmailTimelapseListForCc();
                    for (EmailRootcauseTimelapse b : CcList) {
                        Cc.add(b.getEmail());
                    }
                    String[] myArrayCc = new String[Cc.size()];
                    String[] emailCc = Cc.toArray(myArrayCc);

                    List<String> To = new ArrayList<String>();
                    tlD = new EmailRootcauseTimelapseDAO();
                    List<EmailRootcauseTimelapse> toList = tlD.getEmailTimelapseListForDistList();
                    for (EmailRootcauseTimelapse to : toList) {
                        To.add(to.getEmail());
                    }
                    String[] myArrayTo = new String[To.size()];
                    String[] emailTo = To.toArray(myArrayTo);

                    emailSender.htmlEmailManyToWithCc(
                            servletContext,
                            user, //user name requestor
                            emailTo,
                            emailCc,
                            "HIMS Time Lapse - Pending Root Cause / CA", //subject
                            "Rel Lab to SBN Factory - <b>" + countShip + "</b> items pending Root Cause and Correlative Action  <br />"
                            + "SBN Factory to Rel Lab - <b>" + countRetrieve + "</b> items pending Root Cause and Correlative Action  <br /><br />"
                            + "For <b>Rel Lab to SBN Factory</b> items, Please click <a href=\"http://mysed-rel-app03:8080" + servletContext.getContextPath() + "/whTimelapse/send\">HERE</a> <br />"
                            + "For <b>SBN Factory to Rel Lab</b> items, Please click <a href=\"http://mysed-rel-app03:8080" + servletContext.getContextPath() + "/whTimelapse/retrieve\">HERE</a> <br />"
                            + "<br />"
                            + "<br />Thank you." //msg
                    );
                } else { //without Cc 

                    List<String> To = new ArrayList<String>();
                    tlD = new EmailRootcauseTimelapseDAO();
                    List<EmailRootcauseTimelapse> toList = tlD.getEmailTimelapseListForDistList();
                    for (EmailRootcauseTimelapse to : toList) {
                        To.add(to.getEmail());
                    }
                    String[] myArrayTo = new String[To.size()];
                    String[] emailTo = To.toArray(myArrayTo);

                    emailSender.htmlEmailManyTo(
                            servletContext,
                            user, //user name requestor
                            emailTo,
                            "HIMS Time Lapse - Pending Root Cause / CA", //subject
                            "Rel Lab to SBN Factory - <b>" + countShip + "</b> items pending Root Cause and Correlative Action  <br />"
                            + "SBN Factory to Rel Lab - <b>" + countRetrieve + "</b> items pending Root Cause and Correlative Action  <br /><br />"
                            + "For <b>Rel Lab to SBN Factory</b> items, Please click <a href=\"http://mysed-rel-app03:8080" + servletContext.getContextPath() + "/whTimelapse/send\">HERE</a> <br />"
                            + "For <b>SBN Factory to Rel Lab</b> items, Please click <a href=\"http://mysed-rel-app03:8080" + servletContext.getContextPath() + "/whTimelapse/retrieve\">HERE</a> <br />"
                            + "<br />"
                            + "<br />Thank you." //msg

                    );
                }

            }

        } catch (Exception ee) {
            ee.printStackTrace();
        }

    }
}
