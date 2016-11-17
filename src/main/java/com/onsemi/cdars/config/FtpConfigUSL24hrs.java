/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onsemi.cdars.config;

import com.onsemi.cdars.dao.WhStatusLogDAO;
import com.onsemi.cdars.model.WhStatusLog;
import com.onsemi.cdars.tools.EmailSender;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletContext;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
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

//    @Scheduled(cron = "0 49 9 * * ?") //every 8:00 AM - cron (sec min hr daysOfMth month daysOfWeek year(optional))
    public void cronRun() throws FileNotFoundException, IOException {
        LOGGER.info("Upper Spec Limit (USL) executed at everyday on 8:00 am. Current time is : " + new Date());

        String username = System.getProperty("user.name");
        if (!"fg79cj".equals(username)) {
            username = "imperial";
        }
        DateFormat dateFormat = new SimpleDateFormat("ddMMMyyyy");
        Date date = new Date();
        String todayDate = dateFormat.format(date);

        String reportName = "C:\\Users\\" + username + "\\Documents\\CDARS\\HIMS Upper Specs Limit Report (" + todayDate + ").xls";
            
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
            
            WhStatusLogDAO statusD = new WhStatusLogDAO();
            List<WhStatusLog> whUslList = statusD.getTLReqToApproveAndApproveToMpCreatedList();

            for (int i = 0; i < whUslList.size(); i++) {
                hardwareType = whUslList.get(i).getEquipmentType();
                hardwareId = whUslList.get(i).getEquipmentId();
                materialPassNo = whUslList.get(i).getMpNo();
                String hourReqApp = whUslList.get(i).getRequestToApprove24();
                String hourReqAppIfNull = whUslList.get(i).getRequestToApproveTemp24();
                String hourAppMp = whUslList.get(i).getApproveToMPCreated24();
                String hourAppMpIfNull = whUslList.get(i).getApproveToMPCreatedTemp24();

                boolean flag = false;

                if (hourReqAppIfNull!= null) {
                    if (Integer.parseInt(hourReqAppIfNull) >= 24 && hourReqApp == null) {
                        duration = whUslList.get(i).getRequestToApproveTemp();
                        status = "Pending Approval";
                        flag = true;
                    }
                }
                
                if (hourAppMpIfNull!= null) {
                    if (Integer.parseInt(hourAppMpIfNull) >= 24 && hourAppMp == null && hourReqApp != null) {
                        duration = whUslList.get(i).getApproveToMPCreatedTemp();
                        status = "Pending Material Pass Number";
                        flag = true;
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
            
            WhStatusLogDAO statusD2 = new WhStatusLogDAO();
            List<WhStatusLog> whUslList2 = statusD2.getTLMpCreatedToFinalInventoryDateList();

            for (int i = 0; i < whUslList2.size(); i++) {
                hardwareType = whUslList2.get(i).getEquipmentType();
                hardwareId = whUslList2.get(i).getEquipmentId();
                materialPassNo = whUslList2.get(i).getMpNo();
                String hourMpTt = whUslList2.get(i).getMpCreatedToTtScan24();
                String hourMpTtIfNull = whUslList2.get(i).getMpCreatedToTtScanTemp24();
                String hourTtBs = whUslList2.get(i).getTtScanToBsScan24();
                String hourTtBsIfNull = whUslList2.get(i).getTtScanToBsScanTemp24();
                String hourBsShip = whUslList2.get(i).getBsScanToShip24();
                String hourBsShipIfNull = whUslList2.get(i).getBsScanToShipTemp24();
                String hourShipInv = whUslList2.get(i).getShipToInventory24();
                String hourShipInvIfNull = whUslList2.get(i).getShipToInventoryTemp24();

                boolean flag = false;

                if (hourMpTtIfNull!= null) {
                    if (Integer.parseInt(hourMpTtIfNull) >= 24 && hourMpTt == null) {
                        duration = whUslList2.get(i).getMpCreatedToTtScanTemp();
                        status = "Pending Trip Ticket Scanning";
                        flag = true;
                    }
                }
                
                if (hourTtBsIfNull!= null) {
                    if (Integer.parseInt(hourTtBsIfNull) >= 24 && hourTtBs == null && hourMpTt != null) {
                        duration = whUslList2.get(i).getTtScanToBsScanTemp();
                        status = "Pending Barcode Sticker Scanning";
                        flag = true;
                    }
                }
                
                if (hourBsShipIfNull!= null) {
                    if (Integer.parseInt(hourBsShipIfNull) >= 24 && hourBsShip == null && hourTtBs != null && hourMpTt != null) {
                        duration = whUslList2.get(i).getBsScanToShipTemp();
                        status = "Pending Shipping Packing List";
                        flag = true;
                    }
                }
                
                if (hourShipInvIfNull!= null) {
                    if (Integer.parseInt(hourShipInvIfNull) >= 24 && hourShipInv == null && hourBsShip != null && hourTtBs != null && hourMpTt != null) {
                        duration = whUslList2.get(i).getShipToInventoryTemp();
                        status = "Pending Inventory in Seremban Factory";
                        flag = true;
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
       
            
            workbook.write(fileOut);
            workbook.close();
            
            //send email
            LOGGER.info("send email to person in charge");
            EmailSender emailSender = new EmailSender();
            com.onsemi.cdars.model.User user = new com.onsemi.cdars.model.User();
            user.setFullname("All");
//                String[] to = {"hmsrelon@gmail.com", "hmsrelontest@gmail.com"};  //9/11/16
            String[] to = {"fg79cj@onsemi.com"};
            emailSender.htmlEmailWithAttachment(
                    servletContext,
                    user, //user name requestor
                    to,
                    new File("C:\\Users\\" + username + "\\Documents\\CDARS\\HIMS Upper Specs Limit Report (" + todayDate + ").xls"),
                    "List of Hardware Exceed USL (24 hours)", //subject
                    "Report for Hardware Process from HIMS that exceed Upper Specs Limit (24 hours) has been made. <br />"
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
        List<WhStatusLog> whUslList = statusD.getTLReqToApproveAndApproveToMpCreatedList();

        for (int i = 0; i < whUslList.size(); i++) {
            hardwareType = whUslList.get(i).getEquipmentType();
            hardwareId = whUslList.get(i).getEquipmentId();
            materialPassNo = whUslList.get(i).getMpNo();
            String hourReqApp = whUslList.get(i).getRequestToApprove24();
            String hourReqAppIfNull = whUslList.get(i).getRequestToApproveTemp24();
            String hourAppMp = whUslList.get(i).getApproveToMPCreated24();
            String hourAppMpIfNull = whUslList.get(i).getApproveToMPCreatedTemp24();

            boolean flag = false;

            if (hourReqAppIfNull!= null) {
                if (Integer.parseInt(hourReqAppIfNull) >= 24 && hourReqApp == null) {
                    duration = whUslList.get(i).getRequestToApproveTemp();
                    status = "Pending Approval";
                    flag = true;
                }
            }

            if (hourAppMpIfNull!= null) {
                if (Integer.parseInt(hourAppMpIfNull) >= 24 && hourAppMp == null && hourReqApp != null) {
                    duration = whUslList.get(i).getApproveToMPCreatedTemp();
                    status = "Pending Material Pass Number";
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
            
        WhStatusLogDAO statusD2 = new WhStatusLogDAO();
        List<WhStatusLog> whUslList2 = statusD2.getTLMpCreatedToFinalInventoryDateList();
        
        for (int i = 0; i < whUslList2.size(); i++) {
            hardwareType = whUslList2.get(i).getEquipmentType();
            hardwareId = whUslList2.get(i).getEquipmentId();
            materialPassNo = whUslList2.get(i).getMpNo();
            String hourMpTt = whUslList2.get(i).getMpCreatedToTtScan24();
            String hourMpTtIfNull = whUslList2.get(i).getMpCreatedToTtScanTemp24();
            String hourTtBs = whUslList2.get(i).getTtScanToBsScan24();
            String hourTtBsIfNull = whUslList2.get(i).getTtScanToBsScanTemp24();
            String hourBsShip = whUslList2.get(i).getBsScanToShip24();
            String hourBsShipIfNull = whUslList2.get(i).getBsScanToShipTemp24();
            String hourShipInv = whUslList2.get(i).getShipToInventory24();
            String hourShipInvIfNull = whUslList2.get(i).getShipToInventoryTemp24();

            boolean flag = false;

            if (hourMpTtIfNull!= null) {
                if (Integer.parseInt(hourMpTtIfNull) >= 24 && hourMpTt == null) {
                    duration = whUslList2.get(i).getMpCreatedToTtScanTemp();
                    status = "Pending Trip Ticket Scanning";
                    flag = true;
                }
            }

            if (hourTtBsIfNull!= null) {
                if (Integer.parseInt(hourTtBsIfNull) >= 24 && hourTtBs == null && hourMpTt != null) {
                    duration = whUslList2.get(i).getTtScanToBsScanTemp();
                    status = "Pending Barcode Sticker Scanning";
                    flag = true;
                }
            }

            if (hourBsShipIfNull!= null) {
                if (Integer.parseInt(hourBsShipIfNull) >= 24 && hourBsShip == null && hourTtBs != null && hourMpTt != null) {
                    duration = whUslList2.get(i).getBsScanToShipTemp();
                    status = "Pending Shipping Packing List";
                    flag = true;
                }
            }

            if (hourShipInvIfNull!= null) {
                if (Integer.parseInt(hourShipInvIfNull) >= 24 && hourShipInv == null && hourBsShip != null && hourTtBs != null && hourMpTt != null) {
                    duration = whUslList2.get(i).getShipToInventoryTemp();
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
        return text;
    }
}
