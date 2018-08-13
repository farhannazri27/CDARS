/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onsemi.cdars.config;

import com.onsemi.cdars.dao.EmailConfigDAO;
import com.onsemi.cdars.dao.WhInventoryDAO;
import com.onsemi.cdars.dao.WhMpListDAO;
import com.onsemi.cdars.dao.WhRequestDAO;
import com.onsemi.cdars.dao.WhStatusLogDAO;
import com.onsemi.cdars.model.EmailConfig;
import com.onsemi.cdars.model.WhInventory;
import com.onsemi.cdars.model.WhMpList;
import com.onsemi.cdars.model.WhStatusLog;
import com.onsemi.cdars.tools.EmailSender;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
public class ApprovalCronConfig {

    @Autowired
    ServletContext servletContext;

    private static final Logger LOGGER = LoggerFactory.getLogger(ApprovalCronConfig.class);

//    @Scheduled(fixedRate = 60000)
//    hold for now
//    @Scheduled(cron = "0 0 8 * * *") //every 8.00am
    public void EmailForPendingApprovalDisposition() {

        //utk inventory
        LOGGER.info("cron detected -[Approval Reminder]!!!");

        try {

            WhRequestDAO requestD = new WhRequestDAO();
            int count = requestD.getCountNowDateMoreDateRequested3Days();
            if (count > 0) {

                EmailSender emailSender = new EmailSender();
                com.onsemi.cdars.model.User user = new com.onsemi.cdars.model.User();
                user.setFullname("All");

                List<String> a = new ArrayList<String>();

                String emailApprover = "";
//                String emaildistList1 = "";
//                String emaildistList2 = "";
//                String emaildistList3 = "";
//                String emaildistList4 = "";
                String emailApprover1 = "";
                String motherboard = "";
                String pcb = "";

                emailApprover = "fg79cj@onsemi.com";
                a.add(emailApprover);

                EmailConfigDAO econfD = new EmailConfigDAO();
                int countapprover1 = econfD.getCountTask("Approver 1");
                if (countapprover1 == 1) {
                    econfD = new EmailConfigDAO();
                    EmailConfig Approver1 = econfD.getEmailConfigByTask("Approver 1");
                    emailApprover1 = Approver1.getEmail();
                    a.add(emailApprover1);
                }
                econfD = new EmailConfigDAO();
                int countmb = econfD.getCountTask("Motherboard");
                if (countmb == 1) {
                    econfD = new EmailConfigDAO();
                    EmailConfig mb = econfD.getEmailConfigByTask("Motherboard");
                    motherboard = mb.getEmail();
                    a.add(motherboard);
                }

                econfD = new EmailConfigDAO();
                int countpcb = econfD.getCountTask("PCB");
                if (countpcb == 1) {
                    econfD = new EmailConfigDAO();
                    EmailConfig pcb1 = econfD.getEmailConfigByTask("PCB");
                    pcb = pcb1.getEmail();
                    a.add(pcb);
                }

                String[] myArray = new String[a.size()];
                String[] emailTo = a.toArray(myArray);

                emailSender.htmlEmailManyTo(
                        servletContext,
                        //                    user name
                        user,
                        //                    to
                        //                        "fg79cj@onsemi.com",
                        emailTo,
                        //                    subject
                        "Pending Approval Action for New Hardware Request",
                        //                    msg
                        "There are " + count + " hardware request still pending for approval action. Please go to this link "
                        //local development
                        //                        + "<a href=\"http://fg79cj-l1:8080" + servletContext.getContextPath() + "/wh/whRequest\">HIMS</a>"
                        //                        + " to do the approval action. Thank you."
                        //server production
                        + "<a href=\"http://mysed-rel-app03:8080" + servletContext.getContextPath() + "/wh/whRequest\">HIMS</a>"
                        + " to do the approval action. Thank you."
                );

                LOGGER.info("total pending hardware for approval disposition " + count);

            } else {
                LOGGER.info("No pending hardware for approval disposition.");
            }

        } catch (Exception ee) {
            ee.printStackTrace();
        }

    }

    @Scheduled(cron = "0 2 8 * * *") //every 8.00am
    public void EmailForMPExpiryDate() {

        //utk inventory
        LOGGER.info("cron detected -[Approval Reminder]!!!");

        try {

            WhInventoryDAO inventoryD = new WhInventoryDAO();
            int count = inventoryD.getCountMpExpiryDate();
            if (count > 0) {

                //create excel file
                String username = System.getProperty("user.name");
                if (!"fg79cj".equals(username)) {
                    username = "imperial";
                }
                DateFormat dateFormat = new SimpleDateFormat("ddMMMyyyy");
                Date date = new Date();
                String todayDate = dateFormat.format(date);

                String reportName = "C:\\Users\\" + username + "\\Documents\\CDARS\\MP_expiry\\[HIMS] Material Pass Expire Within 30 Days (" + todayDate + ").xls";

                FileOutputStream fileOut = new FileOutputStream(reportName);
                HSSFWorkbook workbook = new HSSFWorkbook();
                HSSFSheet sheet = workbook.createSheet("HIMS MP EXPIRE 30 DAYS");
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
                cell1_2.setCellValue("QUANTITY");

                HSSFCell cell1_3 = rowhead.createCell(3);
                cell1_3.setCellStyle(style);
                cell1_3.setCellValue("MATERIAL PASS NO");

                HSSFCell cell1_4 = rowhead.createCell(4);
                cell1_4.setCellStyle(style);
                cell1_4.setCellValue("MATERIAL PASS EXPIRY DATE");

                String materialPassNo = "";
                String hardwareId = "";
                String hardwareType = "";
                String materialPassExpiryDate = "";
                String quantity = "";
                String text = "";

                WhInventoryDAO invD = new WhInventoryDAO();
                List< WhInventory> whMpListList = invD.getWhInventoryListMpExpire30Days();

                boolean checksize1 = false;
                for (int i = 0; i < whMpListList.size(); i++) {
                    checksize1 = true;
                    hardwareType = whMpListList.get(i).getEquipmentType();
                    hardwareId = whMpListList.get(i).getEquipmentId();
                    materialPassNo = whMpListList.get(i).getMpNo();
                    materialPassExpiryDate = whMpListList.get(i).getViewMpExpiryDate();
                    quantity = whMpListList.get(i).getQuantity();

                    HSSFRow contents = sheet.createRow(sheet.getLastRowNum() + 1);
//                
                    HSSFCell cell2_0 = contents.createCell(0);
                    cell2_0.setCellValue(hardwareType);

                    HSSFCell cell2_1 = contents.createCell(1);
                    cell2_1.setCellValue(hardwareId);

                    HSSFCell cell2_2 = contents.createCell(2);
                    cell2_2.setCellValue(quantity);

                    HSSFCell cell2_3 = contents.createCell(3);
                    cell2_3.setCellValue(materialPassNo);

                    HSSFCell cell2_4 = contents.createCell(4);
                    cell2_4.setCellValue(materialPassExpiryDate);
                }

                if (checksize1 == true) {
                    workbook.write(fileOut);
                    workbook.close();
                }

                EmailSender emailSender = new EmailSender();
                com.onsemi.cdars.model.User user = new com.onsemi.cdars.model.User();
                user.setFullname("All");

                List<String> a = new ArrayList<String>();

                String emailApprover = "";
                String emaildistList1 = "";
                String emaildistList2 = "";
                String emaildistList3 = "";
                String emaildistList4 = "";
                String emailApprover1 = "";
                String motherboard = "";
                String pcb = "";
                 String ate = "";
                  String eqpt = "";

                emailApprover = "fg79cj@onsemi.com";
                a.add(emailApprover);

                EmailConfigDAO econfD = new EmailConfigDAO();
                int countapprover1 = econfD.getCountTask("Approver 1");
                if (countapprover1 == 1) {
                    econfD = new EmailConfigDAO();
                    EmailConfig Approver1 = econfD.getEmailConfigByTask("Approver 1");
                    emailApprover1 = Approver1.getEmail();
                    a.add(emailApprover1);
                }
                econfD = new EmailConfigDAO();
                int countmb = econfD.getCountTask("Motherboard");
                if (countmb == 1) {
                    econfD = new EmailConfigDAO();
                    EmailConfig mb = econfD.getEmailConfigByTask("Motherboard");
                    motherboard = mb.getEmail();
                    a.add(motherboard);
                }

                econfD = new EmailConfigDAO();
                int countpcb = econfD.getCountTask("PCB");
                if (countpcb == 1) {
                    econfD = new EmailConfigDAO();
                    EmailConfig pcb1 = econfD.getEmailConfigByTask("PCB");
                    pcb = pcb1.getEmail();
                    a.add(pcb);
                }

                econfD = new EmailConfigDAO();
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
                econfD = new EmailConfigDAO();
                int countAte = econfD.getCountTask("ATE");
                if (countAte == 1) {
                    econfD = new EmailConfigDAO();
                    EmailConfig ateEmail = econfD.getEmailConfigByTask("ATE");
                    ate = ateEmail.getEmail();
                    a.add(ate);
                }
                econfD = new EmailConfigDAO();
                int countEqpt = econfD.getCountTask("EQP");
                if (countEqpt == 1) {
                    econfD = new EmailConfigDAO();
                    EmailConfig eqptEmail = econfD.getEmailConfigByTask("EQP");
                    eqpt = eqptEmail.getEmail();
                    a.add(eqpt);
                }
                String[] myArray = new String[a.size()];
                String[] emailTo = a.toArray(myArray);

                emailSender.htmlEmailWithAttachment(
                        servletContext,
                        //                    user name
                        user,
                        //                    to
                        //                       
                        emailTo,
                        new File("C:\\Users\\" + username + "\\Documents\\CDARS\\MP_expiry\\[HIMS] Material Pass Expire Within 30 Days (" + todayDate + ").xls"),
                        //                    subject
                        "Material Pass Will Expire Within 30 Days",
                        //                    msg
                        "There are " + count + " material pass that will be expire within 30 days. Please go to this link "
                        //local development
                        //                        + "<a href=\"http://fg79cj-l1:8080" + servletContext.getContextPath() + "/wh/whRequest\">HIMS</a>"
                        //                        + " to request a retrieval from Sungai Gadut Warehouse. Thank you."
                        //server production
                        + "<a href=\"http://mysed-rel-app03:8080" + servletContext.getContextPath() + "/wh/whRequest\">HIMS</a>"
                        + " to request a retrieval from Seremban Factory. "
                        + "<br /><br /> "
                        + "<br />Thank you." //msg
                );

                LOGGER.info("total material pass to expire: " + count);

            } else {
                LOGGER.info("No material pass is expire.");
            }

        } catch (Exception ee) {
            ee.printStackTrace();
        }

    }

    @Scheduled(cron = "0 0 8 * * *") //every 8.00am
    public void EmailForExpiredMp() {

        //utk inventory
        LOGGER.info("cron detected -[Approval Reminder for expired Mp]!!!");

        try {

            WhInventoryDAO inventoryD = new WhInventoryDAO();
            int count = inventoryD.getCountExpiredMp();
            if (count > 0) {

                //create excel file
                String username = System.getProperty("user.name");
                if (!"fg79cj".equals(username)) {
                    username = "imperial";
                }
                DateFormat dateFormat = new SimpleDateFormat("ddMMMyyyy");
                Date date = new Date();
                String todayDate = dateFormat.format(date);

                String reportName = "C:\\Users\\" + username + "\\Documents\\CDARS\\MP_expiry\\[HIMS] Material Pass Has Expired(" + todayDate + ").xls";

                FileOutputStream fileOut = new FileOutputStream(reportName);
                HSSFWorkbook workbook = new HSSFWorkbook();
                HSSFSheet sheet = workbook.createSheet("HIMS MP EXPIRED");
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
                cell1_2.setCellValue("QUANTITY");

                HSSFCell cell1_3 = rowhead.createCell(3);
                cell1_3.setCellStyle(style);
                cell1_3.setCellValue("MATERIAL PASS NO");

                HSSFCell cell1_4 = rowhead.createCell(4);
                cell1_4.setCellStyle(style);
                cell1_4.setCellValue("MATERIAL PASS EXPIRY DATE");

                String materialPassNo = "";
                String hardwareId = "";
                String hardwareType = "";
                String materialPassExpiryDate = "";
                String quantity = "";
                String text = "";

                WhInventoryDAO invD = new WhInventoryDAO();
                List< WhInventory> whMpListList = invD.getWhInventoryListMpHasExpired();

                boolean checksize1 = false;
                for (int i = 0; i < whMpListList.size(); i++) {
                    checksize1 = true;
                    hardwareType = whMpListList.get(i).getEquipmentType();
                    hardwareId = whMpListList.get(i).getEquipmentId();
                    materialPassNo = whMpListList.get(i).getMpNo();
                    materialPassExpiryDate = whMpListList.get(i).getViewMpExpiryDate();
                    quantity = whMpListList.get(i).getQuantity();

                    HSSFRow contents = sheet.createRow(sheet.getLastRowNum() + 1);
//                
                    HSSFCell cell2_0 = contents.createCell(0);
                    cell2_0.setCellValue(hardwareType);

                    HSSFCell cell2_1 = contents.createCell(1);
                    cell2_1.setCellValue(hardwareId);

                    HSSFCell cell2_2 = contents.createCell(2);
                    cell2_2.setCellValue(quantity);

                    HSSFCell cell2_3 = contents.createCell(3);
                    cell2_3.setCellValue(materialPassNo);

                    HSSFCell cell2_4 = contents.createCell(4);
                    cell2_4.setCellValue(materialPassExpiryDate);
                }

                if (checksize1 == true) {
                    workbook.write(fileOut);
                    workbook.close();
                }

                EmailSender emailSender = new EmailSender();
                com.onsemi.cdars.model.User user = new com.onsemi.cdars.model.User();
                user.setFullname("All");

                List<String> a = new ArrayList<String>();

                String emailApprover = "";
                String emaildistList1 = "";
                String emaildistList2 = "";
                String emaildistList3 = "";
                String emaildistList4 = "";
                String emailApprover1 = "";
                String motherboard = "";
                String pcb = "";
                String ate = "";
                String eqpt = "";

                emailApprover = "fg79cj@onsemi.com";
                a.add(emailApprover);

                EmailConfigDAO econfD = new EmailConfigDAO();
                int countapprover1 = econfD.getCountTask("Approver 1");
                if (countapprover1 == 1) {
                    econfD = new EmailConfigDAO();
                    EmailConfig Approver1 = econfD.getEmailConfigByTask("Approver 1");
                    emailApprover1 = Approver1.getEmail();
                    a.add(emailApprover1);
                }
                econfD = new EmailConfigDAO();
                int countmb = econfD.getCountTask("Motherboard");
                if (countmb == 1) {
                    econfD = new EmailConfigDAO();
                    EmailConfig mb = econfD.getEmailConfigByTask("Motherboard");
                    motherboard = mb.getEmail();
                    a.add(motherboard);
                }

                econfD = new EmailConfigDAO();
                int countpcb = econfD.getCountTask("PCB");
                if (countpcb == 1) {
                    econfD = new EmailConfigDAO();
                    EmailConfig pcb1 = econfD.getEmailConfigByTask("PCB");
                    pcb = pcb1.getEmail();
                    a.add(pcb);
                }

                econfD = new EmailConfigDAO();
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

                econfD = new EmailConfigDAO();
                int countAte = econfD.getCountTask("ATE");
                if (countAte == 1) {
                    econfD = new EmailConfigDAO();
                    EmailConfig ateEmail = econfD.getEmailConfigByTask("ATE");
                    ate = ateEmail.getEmail();
                    a.add(ate);
                }
                econfD = new EmailConfigDAO();
                int countEqpt = econfD.getCountTask("EQP");
                if (countEqpt == 1) {
                    econfD = new EmailConfigDAO();
                    EmailConfig eqptEmail = econfD.getEmailConfigByTask("EQP");
                    eqpt = eqptEmail.getEmail();
                    a.add(eqpt);
                }
                String[] myArray = new String[a.size()];
                String[] emailTo = a.toArray(myArray);

                emailSender.htmlEmailWithAttachment(
                        servletContext,
                        //                    user name
                        user,
                        //                    to
                        emailTo,
                        new File("C:\\Users\\" + username + "\\Documents\\CDARS\\MP_expiry\\[HIMS] Material Pass Has Expired(" + todayDate + ").xls"),
                        //                    subject
                        "Material Pass Has Expired",
                        //                    msg
                        "There are " + count + " material pass has expired. Please go to this link "
                        //local development
                        //                        + "<a href=\"http://fg79cj-l1:8080" + servletContext.getContextPath() + "/wh/whRequest\">HIMS</a>"
                        //                        + " to request a retrieval from Sungai Gadut Warehouse. Thank you."
                        //server production
                        + "<a href=\"http://mysed-rel-app03:8080" + servletContext.getContextPath() + "/wh/whRequest\">HIMS</a>"
                        + " to request a retrieval from Sungai Gadut Warehouse. Thank you."
                );

                LOGGER.info("total material pass expired: " + count);

            } else {
                LOGGER.info("No material pass has expired.");
            }

        } catch (Exception ee) {
            ee.printStackTrace();
        }

    }

    private String table() {
        WhInventoryDAO invD = new WhInventoryDAO();
        List< WhInventory> whMpListList = invD.getWhInventoryListMpExpire30Days();
        String materialPassNo = "";
        String materialPassExp = "";
        String hardwareType = "";
        String hardwareId = "";
        String quantity = "";
        String text = "";

        for (int i = 0; i < whMpListList.size(); i++) {
            materialPassNo = whMpListList.get(i).getMpNo();
            materialPassExp = whMpListList.get(i).getViewMpExpiryDate();
            hardwareType = whMpListList.get(i).getEquipmentType();
            hardwareId = whMpListList.get(i).getEquipmentId();
            quantity = whMpListList.get(i).getQuantity();
            text = text + "<tr align = \"center\">";
            text = text + "<td>" + materialPassNo + "</td>";
            text = text + "<td>" + materialPassExp + "</td>";
            text = text + "<td>" + hardwareType + "</td>";
            text = text + "<td>" + hardwareId + "</td>";
            text = text + "<td>" + quantity + "</td>";
            text = text + "</tr>";
        }
        return text;
    }

}
