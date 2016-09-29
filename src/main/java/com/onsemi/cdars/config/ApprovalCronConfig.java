/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onsemi.cdars.config;

import com.onsemi.cdars.dao.WhInventoryDAO;
import com.onsemi.cdars.dao.WhRequestDAO;
import com.onsemi.cdars.tools.EmailSender;
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
                user.setFullname("Approver");

                emailSender.htmlEmail(
                        servletContext,
                        //                    user name
                        user,
                        //                    to
                        "fg79cj@onsemi.com",
                        //                    subject
                        "Pending Approval Action for New Hardware Request",
                        //                    msg
                        "There are " + count + " hardware request still pending for approval action. Please go to this link "
                        + "<a href=\"http://fg79cj-l1:8080" + servletContext.getContextPath() + "/wh/whRequest\">CDARS</a>"
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

                emailSender.htmlEmail(
                        servletContext,
                        //                    user name
                        user,
                        //                    to
                        "fg79cj@onsemi.com",
                        //                    subject
                        "Material Pass Will Expire Within 30 Days",
                        //                    msg
                        "There are " + count + " material pass that will be expire within 30 days. Please go to this link "
                        + "<a href=\"http://fg79cj-l1:8080" + servletContext.getContextPath() + "/wh/whRequest\">CDARS</a>"
                        + " to request a retrieval from Sungai Gadut Warehouse. Thank you."
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
                        + "<a href=\"http://fg79cj-l1:8080" + servletContext.getContextPath() + "/wh/whRequest\">CDARS</a>"
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

}
