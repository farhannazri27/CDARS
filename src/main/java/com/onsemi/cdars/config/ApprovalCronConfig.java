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
import com.onsemi.cdars.model.EmailConfig;
import com.onsemi.cdars.model.WhInventory;
import com.onsemi.cdars.model.WhMpList;
import com.onsemi.cdars.tools.EmailSender;
import java.util.List;
import javax.servlet.ServletContext;
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
//    @Scheduled(cron = "0 5 * * * *") //every hour after 5 minute every day
    @Scheduled(cron = "0 0 8 * * *") //every 8.00am
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

                EmailConfigDAO econfD = new EmailConfigDAO();
                EmailConfig approver = econfD.getEmailConfigByTask("Approver 1");

                econfD = new EmailConfigDAO();
                EmailConfig Motherboard = econfD.getEmailConfigByTask("Motherboard");

                econfD = new EmailConfigDAO();
                EmailConfig PCB = econfD.getEmailConfigByTask("PCB");

                String[] to = {approver.getEmail(), Motherboard.getEmail(), PCB.getEmail()};

                emailSender.htmlEmailManyTo(
                        servletContext,
                        //                    user name
                        user,
                        //                    to
                        //                        "fg79cj@onsemi.com",
                        to,
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

    @Scheduled(cron = "0 0 8 * * *") //every 8.00am
    public void EmailForMPExpiryDate() {

        //utk inventory
        LOGGER.info("cron detected -[Approval Reminder]!!!");

        try {

            WhInventoryDAO inventoryD = new WhInventoryDAO();
            int count = inventoryD.getCountMpExpiryDate();
            if (count > 0) {

                EmailSender emailSender = new EmailSender();
                com.onsemi.cdars.model.User user = new com.onsemi.cdars.model.User();
                user.setFullname("All");

                EmailConfigDAO econfD = new EmailConfigDAO();
                EmailConfig approver = econfD.getEmailConfigByTask("Approver 1");

                econfD = new EmailConfigDAO();
                EmailConfig Motherboard = econfD.getEmailConfigByTask("Motherboard");

                econfD = new EmailConfigDAO();
                EmailConfig PCB = econfD.getEmailConfigByTask("PCB");

                String[] to = {approver.getEmail(), Motherboard.getEmail(), PCB.getEmail()};

                emailSender.htmlEmailManyTo(
                        servletContext,
                        //                    user name
                        user,
                        //                    to
                        //                       
                        to,
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
                        + "<style>table, th, td {border: 1px solid black;} </style>"
                        + "<table style=\"width:100%\">" //tbl
                        + "<tr>"
                        + "<th>MATERIAL PASS NO</th> "
                        + "<th>MATERIAL PASS EXPIRY DATE</th> "
                        + "<th>HARDWARE TYPE</th>"
                        + "<th>HARDWARE ID</th>"
                        + "<th>QUANTITY</th>"
                        + "</tr>"
                        + table()
                        + "</table>"
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

                EmailSender emailSender = new EmailSender();
                com.onsemi.cdars.model.User user = new com.onsemi.cdars.model.User();
                user.setFullname("All");

                emailSender.htmlEmail(
                        servletContext,
                        //                    user name
                        user,
                        //                    to
                        "fg79cj@onsemi.com",
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
