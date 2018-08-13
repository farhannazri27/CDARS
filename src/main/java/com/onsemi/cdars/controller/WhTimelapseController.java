package com.onsemi.cdars.controller;

import com.onsemi.cdars.dao.EmailRootcauseTimelapseDAO;
import com.onsemi.cdars.dao.EmailTimelapseDAO;
import com.onsemi.cdars.dao.LDAPUserDAO;
import com.onsemi.cdars.dao.ParameterDetailsDAO;
import java.util.List;
import java.util.Locale;
import com.onsemi.cdars.dao.WhTimelapseDAO;
import com.onsemi.cdars.dao.WhUslReportDAO;
import com.onsemi.cdars.model.EmailRootcauseTimelapse;
import com.onsemi.cdars.model.EmailTimelapse;
import com.onsemi.cdars.model.LDAPUser;
import com.onsemi.cdars.model.ParameterDetails;
import com.onsemi.cdars.model.WhTimelapse;
import com.onsemi.cdars.model.UserSession;
import com.onsemi.cdars.model.WhUslReport;
import com.onsemi.cdars.tools.QueryResult;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/whTimelapse")
@SessionAttributes({"userSession"})
public class WhTimelapseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WhTimelapseController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public String whTimelapseSend(
            Model model
    ) {

        Calendar now = Calendar.getInstance();
        Integer year = now.get(Calendar.YEAR);
        Integer stop = 0;

        Integer monthTemp = now.get(Calendar.MONTH) + 1; //month start from 0 - 11

        List<WhUslReport> surrogate = new ArrayList<WhUslReport>();
        WhUslReport whStatusLog;

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
            List<WhUslReport> whTimelapseList = whUslReport.GetListOfFailedShipItemByYearAndByMonthLeftJoinTimelapseTable(monthTemp.toString(), year.toString());

            for (int i = 0; i < whTimelapseList.size(); i++) {

                String failSteps = "";
                String flag = "0";
                if (Integer.parseInt(whTimelapseList.get(i).getReqToApp()) > 24) {
                    failSteps = "Requested to Approved";
                    flag = "1";
                }

                if (Integer.parseInt(whTimelapseList.get(i).getMpCreateToTtScan()) > 24) {
                    if ("1".equals(flag)) {
                        failSteps = failSteps + "; Material Pass Inserted to Trip ticket Scanned";
                        flag = "1";
                    } else {
                        failSteps = "Material Pass Inserted to Trip ticket Scanned";
                        flag = "1";
                    }
                }

                if (Integer.parseInt(whTimelapseList.get(i).getTtScanToBcScan()) > 24) {
                    if ("1".equals(flag)) {
                        failSteps = failSteps + "; Trip ticket Scanned to Barcode Sticker Scanned";
                        flag = "1";
                    } else {
                        failSteps = "Trip ticket Scanned to Barcode Sticker Scanned";
                        flag = "1";
                    }
                }

                if (Integer.parseInt(whTimelapseList.get(i).getBcScanToShip()) > 24) {
                    if ("1".equals(flag)) {
                        failSteps = failSteps + "; Barcode Sticker Scanned to Shipped";
                        flag = "1";
                    } else {
                        failSteps = "Barcode Sticker Scanned to Shipped";
                        flag = "1";
                    }
                }

                if (Integer.parseInt(whTimelapseList.get(i).getShipToInv()) > 24) {
                    if ("1".equals(flag)) {
                        failSteps = failSteps + "; Shipped to Inventory";
                        flag = "1";
                    } else {
                        failSteps = "Shipped to Inventory";
                        flag = "1";
                    }
                }

//                whTimelapseList.get(i).setCa(failSteps);
                whStatusLog = new WhUslReport();
                whStatusLog.setRequestId(whTimelapseList.get(i).getRequestId());
                whStatusLog.setEqptType(whTimelapseList.get(i).getEqptType());
                whStatusLog.setEqptId(whTimelapseList.get(i).getEqptId());
                whStatusLog.setMpNo(whTimelapseList.get(i).getMpNo());
                whStatusLog.setRequestDate(whTimelapseList.get(i).getRequestDate());
                whStatusLog.setLoadCard(whTimelapseList.get(i).getLoadCard());
                whStatusLog.setProgramCard(whTimelapseList.get(i).getProgramCard());
                whStatusLog.setTotalHourTakeShip(whTimelapseList.get(i).getTotalHourTakeShip());
                whStatusLog.setCa(failSteps);
                surrogate.add(whStatusLog);
            }
            monthTemp -= 1;
        }

        model.addAttribute("surrogate", surrogate);
        return "timeLapse/timeLapseSend";
    }

    @RequestMapping(value = "/countShip", method = RequestMethod.GET)
    public String countShip(
            Model model
    ) {

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

        LOGGER.info("countShip......" + countShip);
        LOGGER.info("countRetrieve......" + countRetrieve);

        model.addAttribute("countShip", countShip);
        return "timeLapse/timeLapseSend";
    }

    @RequestMapping(value = "/send2", method = RequestMethod.GET)
    public String whTimelapseSend2(
            Model model
    ) {

        List<WhUslReport> surrogate = new ArrayList<WhUslReport>();
        WhUslReport whStatusLog;

        WhUslReportDAO whUslReport = new WhUslReportDAO();
        List<WhUslReport> whTimelapseList = whUslReport.GetListOfFailedShipItemInnerJoinTimelapseTable();

        for (int i = 0; i < whTimelapseList.size(); i++) {

            String failSteps = "";
            String flag = "0";
            if (Integer.parseInt(whTimelapseList.get(i).getReqToApp()) > 24) {
                failSteps = "Requested to Approved";
                flag = "1";
            }

            if (Integer.parseInt(whTimelapseList.get(i).getMpCreateToTtScan()) > 24) {
                if ("1".equals(flag)) {
                    failSteps = failSteps + "; Material Pass Inserted to Trip ticket Scanned";
                    flag = "1";
                } else {
                    failSteps = "Material Pass Inserted to Trip ticket Scanned";
                    flag = "1";
                }
            }

            if (Integer.parseInt(whTimelapseList.get(i).getTtScanToBcScan()) > 24) {
                if ("1".equals(flag)) {
                    failSteps = failSteps + "; Trip ticket Scanned to Barcode Sticker Scanned";
                    flag = "1";
                } else {
                    failSteps = "Trip ticket Scanned to Barcode Sticker Scanned";
                    flag = "1";
                }
            }

            if (Integer.parseInt(whTimelapseList.get(i).getBcScanToShip()) > 24) {
                if ("1".equals(flag)) {
                    failSteps = failSteps + "; Barcode Sticker Scanned to Shipped";
                    flag = "1";
                } else {
                    failSteps = "Barcode Sticker Scanned to Shipped";
                    flag = "1";
                }
            }

            if (Integer.parseInt(whTimelapseList.get(i).getShipToInv()) > 24) {
                if ("1".equals(flag)) {
                    failSteps = failSteps + "; Shipped to Inventory";
                    flag = "1";
                } else {
                    failSteps = "Shipped to Inventory";
                    flag = "1";
                }
            }

//                whTimelapseList.get(i).setCa(failSteps);
            whStatusLog = new WhUslReport();
            whStatusLog.setRequestId(whTimelapseList.get(i).getRequestId());
            whStatusLog.setEqptType(whTimelapseList.get(i).getEqptType());
            whStatusLog.setEqptId(whTimelapseList.get(i).getEqptId());
            whStatusLog.setMpNo(whTimelapseList.get(i).getMpNo());
            whStatusLog.setRequestDate(whTimelapseList.get(i).getRequestDate());
            whStatusLog.setLoadCard(whTimelapseList.get(i).getLoadCard());
            whStatusLog.setProgramCard(whTimelapseList.get(i).getProgramCard());
            whStatusLog.setTotalHourTakeShip(whTimelapseList.get(i).getTotalHourTakeShip());
            whStatusLog.setCa(failSteps);
            whStatusLog.setCategory(whTimelapseList.get(i).getCategory());
            surrogate.add(whStatusLog);
        }

        model.addAttribute("surrogate", surrogate);
        return "timeLapse/timeLapseSend2";
    }

    @RequestMapping(value = "/retrieve", method = RequestMethod.GET)
    public String whTimelapseRetrieve(
            Model model
    ) {

        Calendar now = Calendar.getInstance();
        Integer year = now.get(Calendar.YEAR);
        Integer stop = 0;

        Integer monthTemp = now.get(Calendar.MONTH) + 1; //month start from 0 - 11

        List<WhUslReport> surrogate = new ArrayList<WhUslReport>();
        WhUslReport whStatusLog;

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
            List<WhUslReport> whTimelapseList = whUslReport.GetListOfFailedRetrieveItemByYearAndByMonthLeftJoinTimelapseTable(monthTemp.toString(), year.toString());

            for (int i = 0; i < whTimelapseList.size(); i++) {

                String failSteps = "";
                String flag = "0";
                if (Integer.parseInt(whTimelapseList.get(i).getReqToVer()) > 24) {
                    failSteps = "Requested to SF Verified";
                    flag = "1";
                }

                if (Integer.parseInt(whTimelapseList.get(i).getVerToShip()) > 24) {
                    if ("1".equals(flag)) {
                        failSteps = failSteps + "; SF Verified to Shipped";
                        flag = "1";
                    } else {
                        failSteps = "SF Verified to Shipped";
                        flag = "1";
                    }
                }

                if (Integer.parseInt(whTimelapseList.get(i).getShipToBcScan()) > 24) {
                    if ("1".equals(flag)) {
                        failSteps = failSteps + "; Shipped to Barcode Sticker Scanned";
                        flag = "1";
                    } else {
                        failSteps = "Shipped to Barcode Sticker Scanned";
                        flag = "1";
                    }
                }

                if (Integer.parseInt(whTimelapseList.get(i).getBcScanToTtScan()) > 24) {
                    if ("1".equals(flag)) {
                        failSteps = failSteps + "; Barcode Sticker Scanned to Trip ticket Scanned";
                        flag = "1";
                    } else {
                        failSteps = "Barcode Sticker Scanned to Trip ticket Scanned";
                        flag = "1";
                    }
                }

//                whTimelapseList.get(i).setCa(failSteps);
                whStatusLog = new WhUslReport();
                whStatusLog.setRequestId(whTimelapseList.get(i).getRequestId());
                whStatusLog.setEqptType(whTimelapseList.get(i).getEqptType());
                whStatusLog.setEqptId(whTimelapseList.get(i).getEqptId());
                whStatusLog.setMpNo(whTimelapseList.get(i).getMpNo());
                whStatusLog.setRequestDate(whTimelapseList.get(i).getRequestDate());
                whStatusLog.setLoadCard(whTimelapseList.get(i).getLoadCard());
                whStatusLog.setProgramCard(whTimelapseList.get(i).getProgramCard());
                whStatusLog.setTotalHourTakeShip(whTimelapseList.get(i).getTotalHourTakeRetrieve());
                whStatusLog.setCa(failSteps);
                surrogate.add(whStatusLog);
            }
            monthTemp -= 1;
        }

        model.addAttribute("surrogate", surrogate);
        return "timeLapse/timeLapseRetrieve";
    }

    @RequestMapping(value = "/retrieve2", method = RequestMethod.GET)
    public String whTimelapseRetrieve2(
            Model model
    ) {

        List<WhUslReport> surrogate = new ArrayList<WhUslReport>();
        WhUslReport whStatusLog;

        WhUslReportDAO whUslReport = new WhUslReportDAO();
        List<WhUslReport> whTimelapseList = whUslReport.GetListOfFailedRetrieveItemInnerJoinTimelapseTable();

        for (int i = 0; i < whTimelapseList.size(); i++) {

            String failSteps = "";
            String flag = "0";
            if (Integer.parseInt(whTimelapseList.get(i).getReqToVer()) > 24) {
                failSteps = "Requested to SF Verified";
                flag = "1";
            }

            if (Integer.parseInt(whTimelapseList.get(i).getVerToShip()) > 24) {
                if ("1".equals(flag)) {
                    failSteps = failSteps + "; SF Verified to Shipped";
                    flag = "1";
                } else {
                    failSteps = "SF Verified to Shipped";
                    flag = "1";
                }
            }

            if (Integer.parseInt(whTimelapseList.get(i).getShipToBcScan()) > 24) {
                if ("1".equals(flag)) {
                    failSteps = failSteps + "; Shipped to Barcode Sticker Scanned";
                    flag = "1";
                } else {
                    failSteps = "Shipped to Barcode Sticker Scanned";
                    flag = "1";
                }
            }

            if (Integer.parseInt(whTimelapseList.get(i).getBcScanToTtScan()) > 24) {
                if ("1".equals(flag)) {
                    failSteps = failSteps + "; Barcode Sticker Scanned to Trip ticket Scanned";
                    flag = "1";
                } else {
                    failSteps = "Barcode Sticker Scanned to Trip ticket Scanned";
                    flag = "1";
                }
            }

//                whTimelapseList.get(i).setCa(failSteps);
            whStatusLog = new WhUslReport();
            whStatusLog.setRequestId(whTimelapseList.get(i).getRequestId());
            whStatusLog.setEqptType(whTimelapseList.get(i).getEqptType());
            whStatusLog.setEqptId(whTimelapseList.get(i).getEqptId());
            whStatusLog.setMpNo(whTimelapseList.get(i).getMpNo());
            whStatusLog.setRequestDate(whTimelapseList.get(i).getRequestDate());
            whStatusLog.setLoadCard(whTimelapseList.get(i).getLoadCard());
            whStatusLog.setProgramCard(whTimelapseList.get(i).getProgramCard());
            whStatusLog.setTotalHourTakeShip(whTimelapseList.get(i).getTotalHourTakeRetrieve());
            whStatusLog.setCategory(whTimelapseList.get(i).getCategory());
            whStatusLog.setCa(failSteps);
            surrogate.add(whStatusLog);
        }

        model.addAttribute("surrogate", surrogate);
        return "timeLapse/timeLapseRetrieve2";
    }

    @RequestMapping(value = "/manage1/{reqId}", method = RequestMethod.GET)
    public String manageSend(
            Model model,
            @PathVariable("reqId") String reqId
    ) {
        WhUslReportDAO whUslReport = new WhUslReportDAO();
        WhUslReport whTimelapse = whUslReport.GetListOfFailedShipItemByYearAndByMonthLeftJoinTimelapseTableWithReqId(reqId);
        WhUslReport whStatusLog;
        String failSteps = "";
        String flag = "0";
        if (Integer.parseInt(whTimelapse.getReqToApp()) > 24) {
            failSteps = "Requested to Approved";
            flag = "1";
        }

        if (Integer.parseInt(whTimelapse.getMpCreateToTtScan()) > 24) {
            if ("1".equals(flag)) {
                failSteps = failSteps + "; Material Pass Inserted to Trip ticket Scanned";
                flag = "1";
            } else {
                failSteps = "Material Pass Inserted to Trip ticket Scanned";
                flag = "1";
            }
        }

        if (Integer.parseInt(whTimelapse.getTtScanToBcScan()) > 24) {
            if ("1".equals(flag)) {
                failSteps = failSteps + "; Trip ticket Scanned to Barcode Sticker Scanned";
                flag = "1";
            } else {
                failSteps = "Trip ticket Scanned to Barcode Sticker Scanned";
                flag = "1";
            }
        }

        if (Integer.parseInt(whTimelapse.getBcScanToShip()) > 24) {
            if ("1".equals(flag)) {
                failSteps = failSteps + "; Barcode Sticker Scanned to Shipped";
                flag = "1";
            } else {
                failSteps = "Barcode Sticker Scanned to Shipped";
                flag = "1";
            }
        }

        if (Integer.parseInt(whTimelapse.getShipToInv()) > 24) {
            if ("1".equals(flag)) {
                failSteps = failSteps + "; Shipped to Inventory";
                flag = "1";
            } else {
                failSteps = "Shipped to Inventory";
                flag = "1";
            }
        }

        String requestType = "send";

        whStatusLog = new WhUslReport();
        whStatusLog.setRequestId(whTimelapse.getRequestId());
        whStatusLog.setEqptType(whTimelapse.getEqptType());
        whStatusLog.setEqptId(whTimelapse.getEqptId());
        whStatusLog.setMpNo(whTimelapse.getMpNo());
        whStatusLog.setRequestDate(whTimelapse.getRequestDate());
        whStatusLog.setLoadCard(whTimelapse.getLoadCard());
        whStatusLog.setProgramCard(whTimelapse.getProgramCard());
        whStatusLog.setTotalHourTakeShip(whTimelapse.getTotalHourTakeShip());
        whStatusLog.setCa(failSteps);
        model.addAttribute("whStatusLog", whStatusLog);
        model.addAttribute("requestType", requestType);
        ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> category = sDAO.getGroupParameterDetailList("", "021");
        model.addAttribute("category", category);
        return "timeLapse/edit";
    }

    @RequestMapping(value = "/manageSend2/{reqId}", method = RequestMethod.GET)
    public String manageSend2(
            Model model,
            @PathVariable("reqId") String reqId
    ) {
        WhUslReportDAO whUslReport = new WhUslReportDAO();
        WhUslReport whTimelapse = whUslReport.GetListOfFailedShipItemByInnerJoinTimelapseTableWithReqId(reqId);
        WhUslReport whStatusLog;
        String failSteps = "";
        String flag = "0";
        if (Integer.parseInt(whTimelapse.getReqToApp()) > 24) {
            failSteps = "Requested to Approved";
            flag = "1";
        }

        if (Integer.parseInt(whTimelapse.getMpCreateToTtScan()) > 24) {
            if ("1".equals(flag)) {
                failSteps = failSteps + "; Material Pass Inserted to Trip ticket Scanned";
                flag = "1";
            } else {
                failSteps = "Material Pass Inserted to Trip ticket Scanned";
                flag = "1";
            }
        }

        if (Integer.parseInt(whTimelapse.getTtScanToBcScan()) > 24) {
            if ("1".equals(flag)) {
                failSteps = failSteps + "; Trip ticket Scanned to Barcode Sticker Scanned";
                flag = "1";
            } else {
                failSteps = "Trip ticket Scanned to Barcode Sticker Scanned";
                flag = "1";
            }
        }

        if (Integer.parseInt(whTimelapse.getBcScanToShip()) > 24) {
            if ("1".equals(flag)) {
                failSteps = failSteps + "; Barcode Sticker Scanned to Shipped";
                flag = "1";
            } else {
                failSteps = "Barcode Sticker Scanned to Shipped";
                flag = "1";
            }
        }

        if (Integer.parseInt(whTimelapse.getShipToInv()) > 24) {
            if ("1".equals(flag)) {
                failSteps = failSteps + "; Shipped to Inventory";
                flag = "1";
            } else {
                failSteps = "Shipped to Inventory";
                flag = "1";
            }
        }

        String requestType = "send";

        whStatusLog = new WhUslReport();
        whStatusLog.setRequestId(whTimelapse.getRequestId());
        whStatusLog.setEqptType(whTimelapse.getEqptType());
        whStatusLog.setEqptId(whTimelapse.getEqptId());
        whStatusLog.setMpNo(whTimelapse.getMpNo());
        whStatusLog.setRequestDate(whTimelapse.getRequestDate());
        whStatusLog.setLoadCard(whTimelapse.getLoadCard());
        whStatusLog.setProgramCard(whTimelapse.getProgramCard());
        whStatusLog.setTotalHourTakeShip(whTimelapse.getTotalHourTakeShip());
        whStatusLog.setCa(whTimelapse.getCa());
        whStatusLog.setRootCause(whTimelapse.getRootCause());
        whStatusLog.setCategory(whTimelapse.getCategory());
        whStatusLog.setTiId(whTimelapse.getTiId());
        whStatusLog.setStatusUsl(failSteps);
        model.addAttribute("whStatusLog", whStatusLog);
        model.addAttribute("requestType", requestType);
        ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> category = sDAO.getGroupParameterDetailList(whTimelapse.getCategory(), "021");
        model.addAttribute("category", category);
        return "timeLapse/edit2";
    }

    @RequestMapping(value = "/manage2/{reqId}", method = RequestMethod.GET)
    public String manageRetrieve(
            Model model,
            @PathVariable("reqId") String reqId
    ) {
        WhUslReportDAO whUslReport = new WhUslReportDAO();
        WhUslReport whTimelapse = whUslReport.GetListOfFailedRetrieveItemWithTimelapseTableByReqId(reqId);
        WhUslReport whStatusLog;
        String failSteps = "";
        String flag = "0";
        if (Integer.parseInt(whTimelapse.getReqToVer()) > 24) {
            failSteps = "Requested to SF Verified";
            flag = "1";
        }

        if (Integer.parseInt(whTimelapse.getVerToShip()) > 24) {
            if ("1".equals(flag)) {
                failSteps = failSteps + "; SF Verified to Shipped";
                flag = "1";
            } else {
                failSteps = "SF Verified to Shipped";
                flag = "1";
            }
        }

        if (Integer.parseInt(whTimelapse.getShipToBcScan()) > 24) {
            if ("1".equals(flag)) {
                failSteps = failSteps + "; Shipped to Barcode Sticker Scanned";
                flag = "1";
            } else {
                failSteps = "Shipped to Barcode Sticker Scanned";
                flag = "1";
            }
        }

        if (Integer.parseInt(whTimelapse.getBcScanToTtScan()) > 24) {
            if ("1".equals(flag)) {
                failSteps = failSteps + "; Barcode Sticker Scanned to Trip ticket Scanned";
                flag = "1";
            } else {
                failSteps = "Barcode Sticker Scanned to Trip ticket Scanned";
                flag = "1";
            }
        }

        String requestType = "retrieve";

        whStatusLog = new WhUslReport();
        whStatusLog.setRequestId(whTimelapse.getRequestId());
        whStatusLog.setEqptType(whTimelapse.getEqptType());
        whStatusLog.setEqptId(whTimelapse.getEqptId());
        whStatusLog.setMpNo(whTimelapse.getMpNo());
        whStatusLog.setRequestDate(whTimelapse.getRequestDate());
        whStatusLog.setLoadCard(whTimelapse.getLoadCard());
        whStatusLog.setProgramCard(whTimelapse.getProgramCard());
        whStatusLog.setTotalHourTakeShip(whTimelapse.getTotalHourTakeRetrieve());
        whStatusLog.setCa(failSteps);
        model.addAttribute("whStatusLog", whStatusLog);
        model.addAttribute("requestType", requestType);
        ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> category = sDAO.getGroupParameterDetailList("", "021");
        model.addAttribute("category", category);
        return "timeLapse/edit";
    }

    @RequestMapping(value = "/manageRetrieve2/{reqId}", method = RequestMethod.GET)
    public String manageRetrieve2(
            Model model,
            @PathVariable("reqId") String reqId
    ) {
        WhUslReportDAO whUslReport = new WhUslReportDAO();
        WhUslReport whTimelapse = whUslReport.GetListOfFailedRetrieveItemByInnerJoinTimelapseTableWithReqId(reqId);
        WhUslReport whStatusLog;
        String failSteps = "";
        String flag = "0";
        if (Integer.parseInt(whTimelapse.getReqToVer()) > 24) {
            failSteps = "Requested to SF Verified";
            flag = "1";
        }

        if (Integer.parseInt(whTimelapse.getVerToShip()) > 24) {
            if ("1".equals(flag)) {
                failSteps = failSteps + "; SF Verified to Shipped";
                flag = "1";
            } else {
                failSteps = "SF Verified to Shipped";
                flag = "1";
            }
        }

        if (Integer.parseInt(whTimelapse.getShipToBcScan()) > 24) {
            if ("1".equals(flag)) {
                failSteps = failSteps + "; Shipped to Barcode Sticker Scanned";
                flag = "1";
            } else {
                failSteps = "Shipped to Barcode Sticker Scanned";
                flag = "1";
            }
        }

        if (Integer.parseInt(whTimelapse.getBcScanToTtScan()) > 24) {
            if ("1".equals(flag)) {
                failSteps = failSteps + "; Barcode Sticker Scanned to Trip ticket Scanned";
                flag = "1";
            } else {
                failSteps = "Barcode Sticker Scanned to Trip ticket Scanned";
                flag = "1";
            }
        }

        String requestType = "retrieve";

        whStatusLog = new WhUslReport();
        whStatusLog.setRequestId(whTimelapse.getRequestId());
        whStatusLog.setEqptType(whTimelapse.getEqptType());
        whStatusLog.setEqptId(whTimelapse.getEqptId());
        whStatusLog.setMpNo(whTimelapse.getMpNo());
        whStatusLog.setRequestDate(whTimelapse.getRequestDate());
        whStatusLog.setLoadCard(whTimelapse.getLoadCard());
        whStatusLog.setProgramCard(whTimelapse.getProgramCard());
        whStatusLog.setTotalHourTakeShip(whTimelapse.getTotalHourTakeRetrieve());
        whStatusLog.setStatusUsl(failSteps);
        whStatusLog.setTiId(whTimelapse.getTiId());
        whStatusLog.setCa(whTimelapse.getCa());
        whStatusLog.setRootCause(whTimelapse.getRootCause());
        whStatusLog.setCategory(whTimelapse.getCategory());
        model.addAttribute("whStatusLog", whStatusLog);
        model.addAttribute("requestType", requestType);
        ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> category = sDAO.getGroupParameterDetailList(whTimelapse.getCategory(), "021");
        model.addAttribute("category", category);
        return "timeLapse/edit2";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateTimelapse(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String ca,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String eqptId,
            @RequestParam(required = false) String eqptType,
            @RequestParam(required = false) String requestType,
            @RequestParam(required = false) String rootCause
    ) {
        WhTimelapse whTimelapse = new WhTimelapse();
        whTimelapse.setRequestId(id);
        whTimelapse.setCa(ca);
        whTimelapse.setRootCause(rootCause);
        whTimelapse.setCreatedBy(userSession.getFullname());
        whTimelapse.setCategory(category);
        WhTimelapseDAO whTimelapseDAO = new WhTimelapseDAO();
        QueryResult queryResult = whTimelapseDAO.insertWhTimelapse(whTimelapse);
        args = new String[1];
        args[0] = eqptType + " - " + eqptId;
        if (!"0".equals(queryResult.getGeneratedKey())) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }
        if ("send".equals(requestType)) {
            return "redirect:/whTimelapse/send";
        } else {
            return "redirect:/whTimelapse/retrieve";
        }

    }

    @RequestMapping(value = "/update2", method = RequestMethod.POST)
    public String updateTimelapse2(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String tiId,
            @RequestParam(required = false) String ca,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String eqptId,
            @RequestParam(required = false) String eqptType,
            @RequestParam(required = false) String requestType,
            @RequestParam(required = false) String rootCause
    ) {
        WhTimelapse whTimelapse = new WhTimelapse();
        whTimelapse.setRequestId(id);
        whTimelapse.setId(tiId);
        whTimelapse.setCa(ca);
        whTimelapse.setRootCause(rootCause);
        whTimelapse.setCreatedBy(userSession.getFullname());
        whTimelapse.setCategory(category);
        WhTimelapseDAO whTimelapseDAO = new WhTimelapseDAO();
        QueryResult queryResult = whTimelapseDAO.updateWhTimelapse(whTimelapse);
        args = new String[1];
        args[0] = eqptType + " - " + eqptId;
        if (queryResult.getResult() > 0) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }
        if ("send".equals(requestType)) {
            return "redirect:/whTimelapse/send2";
        } else {
            return "redirect:/whTimelapse/retrieve2";
        }

    }

    @RequestMapping(value = "/emailTimelapse", method = RequestMethod.GET)
    public String emailTimelapse(
            Model model, @ModelAttribute UserSession userSession
    ) {
        EmailTimelapseDAO emailTimelapseDAO = new EmailTimelapseDAO();
        List<EmailTimelapse> emailTimelapseList = emailTimelapseDAO.getEmailTimelapseList();
        model.addAttribute("emailTimelapseList", emailTimelapseList);
        String groupId = userSession.getGroup();
        model.addAttribute("groupId", groupId);
        return "emailTimelapse/emailTimelapse";
    }
    

    @RequestMapping(value = "/emailTimelapse/add", method = RequestMethod.GET)
    public String add(Model model) {
        LDAPUserDAO ldapDao = new LDAPUserDAO();
        List<LDAPUser> user = ldapDao.list();
        model.addAttribute("user", user);
        ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> emailTo = sDAO.getGroupParameterDetailList("", "020");
        model.addAttribute("emailTo", emailTo);
        return "emailTimelapse/add";
    }

    @RequestMapping(value = "/emailTimelapse/save", method = RequestMethod.POST)
    public String save(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String sendCc,
            @RequestParam(required = false) String userOncid,
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String flag,
            @RequestParam(required = false) String remarks
    ) {

        LDAPUserDAO ldap = new LDAPUserDAO();
        LDAPUser lu = ldap.getByOncid(userOncid);

        EmailTimelapse emailTimelapse = new EmailTimelapse();
        emailTimelapse.setSendCc(sendCc);
        emailTimelapse.setUserOncid(userOncid);
        emailTimelapse.setUserName(lu.getFirstname() + " " + lu.getLastname());
        emailTimelapse.setEmail(email);
        emailTimelapse.setFlag("0");
        emailTimelapse.setRemarks(remarks);

        EmailTimelapseDAO countD = new EmailTimelapseDAO();
        Integer count = countD.getCountName(userOncid);
        if (count > 0) {
            model.addAttribute("error", "User has been assigned before. Please select another user.");
            model.addAttribute("emailTimelapse", emailTimelapse);
            return "emailTimelapse/add";
        } else {
            EmailTimelapseDAO emailTimelapseDAO = new EmailTimelapseDAO();
            QueryResult queryResult = emailTimelapseDAO.insertEmailTimelapse(emailTimelapse);
            args = new String[1];
            args[0] = lu.getFirstname() + " " + lu.getLastname() + " - " + sendCc;
            if (queryResult.getGeneratedKey().equals("0")) {
                model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
                model.addAttribute("emailTimelapse", emailTimelapse);
                return "emailTimelapse/add";
            } else {
                redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
                return "redirect:/whTimelapse/emailTimelapse";
            }
        }

    }

    @RequestMapping(value = "/emailTimelapse/edit/{emailTimelapseId}", method = RequestMethod.GET)
    public String edit(
            Model model,
            @PathVariable("emailTimelapseId") String emailTimelapseId
    ) {
        EmailTimelapseDAO emailTimelapseDAO = new EmailTimelapseDAO();
        EmailTimelapse emailTimelapse = emailTimelapseDAO.getEmailTimelapse(emailTimelapseId);
        ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> emailTo = sDAO.getGroupParameterDetailList(emailTimelapse.getSendCc(), "020");
        model.addAttribute("emailTo", emailTo);
        model.addAttribute("emailTimelapse", emailTimelapse);
        return "emailTimelapse/edit";
    }

    @RequestMapping(value = "/emailTimelapse/update", method = RequestMethod.POST)
    public String update(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String sendCc,
            @RequestParam(required = false) String userOncid,
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String flag,
            @RequestParam(required = false) String remarks
    ) {
        EmailTimelapse emailTimelapse = new EmailTimelapse();
        emailTimelapse.setId(id);
        emailTimelapse.setSendCc(sendCc);
        emailTimelapse.setUserOncid(userOncid);
        emailTimelapse.setUserName(userName);
        emailTimelapse.setEmail(email);
        emailTimelapse.setFlag("0");
        emailTimelapse.setRemarks(remarks);

        EmailTimelapseDAO emailTimelapseDAO = new EmailTimelapseDAO();
        QueryResult queryResult = emailTimelapseDAO.updateEmailTimelapse(emailTimelapse);
        args = new String[1];
        args[0] = sendCc + " - " + userOncid;
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }
        return "redirect:/whTimelapse/emailTimelapse";
    }

    @RequestMapping(value = "/emailTimelapse/delete/{emailTimelapseId}", method = RequestMethod.GET)
    public String delete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("emailTimelapseId") String emailTimelapseId
    ) {
        EmailTimelapseDAO emailTimelapseDAO = new EmailTimelapseDAO();
        EmailTimelapse emailTimelapse = emailTimelapseDAO.getEmailTimelapse(emailTimelapseId);
        emailTimelapseDAO = new EmailTimelapseDAO();
        QueryResult queryResult = emailTimelapseDAO.deleteEmailTimelapse(emailTimelapseId);
        args = new String[1];
        args[0] = emailTimelapse.getSendCc() + " - " + emailTimelapse.getUserOncid();
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
        }
        return "redirect:/whTimelapse/emailTimelapse";
    }
    
    @RequestMapping(value = "/emailTimelapseRcCa", method = RequestMethod.GET)
    public String emailTimelapseRcCa(
            Model model, @ModelAttribute UserSession userSession
    ) {
        EmailRootcauseTimelapseDAO emailTimelapseDAO = new EmailRootcauseTimelapseDAO();
        List<EmailRootcauseTimelapse> emailTimelapseList = emailTimelapseDAO.getEmailTimelapseList();
        model.addAttribute("emailTimelapseList", emailTimelapseList);
        String groupId = userSession.getGroup();
        model.addAttribute("groupId", groupId);
        return "emailRootcauseTimelapse/emailRootcauseTimelapse";
    }
    
    @RequestMapping(value = "/emailTimelapseRcCa/add", method = RequestMethod.GET)
    public String addRcCa(Model model) {
        LDAPUserDAO ldapDao = new LDAPUserDAO();
        List<LDAPUser> user = ldapDao.list();
        model.addAttribute("user", user);
        ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> emailTo = sDAO.getGroupParameterDetailList("", "020");
        model.addAttribute("emailTo", emailTo);
        return "emailRootcauseTimelapse/add";
    }

    @RequestMapping(value = "/emailTimelapseRcCa/save", method = RequestMethod.POST)
    public String saveRcCa(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String sendCc,
            @RequestParam(required = false) String userOncid,
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String flag,
            @RequestParam(required = false) String remarks
    ) {

        LDAPUserDAO ldap = new LDAPUserDAO();
        LDAPUser lu = ldap.getByOncid(userOncid);

        EmailRootcauseTimelapse emailTimelapse = new EmailRootcauseTimelapse();
        emailTimelapse.setSendCc(sendCc);
        emailTimelapse.setUserOncid(userOncid);
        emailTimelapse.setUserName(lu.getFirstname() + " " + lu.getLastname());
        emailTimelapse.setEmail(email);
        emailTimelapse.setFlag("0");
        emailTimelapse.setRemarks(remarks);

        EmailRootcauseTimelapseDAO countD = new EmailRootcauseTimelapseDAO();
        Integer count = countD.getCountName(userOncid);
        if (count > 0) {
            model.addAttribute("error", "User has been assigned before. Please select another user.");
            model.addAttribute("emailTimelapse", emailTimelapse);
            return "emailRootcauseTimelapse/add";
        } else {
            EmailRootcauseTimelapseDAO emailTimelapseDAO = new EmailRootcauseTimelapseDAO();
            QueryResult queryResult = emailTimelapseDAO.insertEmailTimelapse(emailTimelapse);
            args = new String[1];
            args[0] = lu.getFirstname() + " " + lu.getLastname() + " - " + sendCc;
            if (queryResult.getGeneratedKey().equals("0")) {
                model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
                model.addAttribute("emailTimelapse", emailTimelapse);
                return "emailRootcauseTimelapse/add";
            } else {
                redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
                return "redirect:/whTimelapse/emailTimelapseRcCa";
            }
        }

    }

    @RequestMapping(value = "/emailTimelapseRcCa/edit/{emailTimelapseId}", method = RequestMethod.GET)
    public String editRcCa(
            Model model,
            @PathVariable("emailTimelapseId") String emailTimelapseId
    ) {
        EmailRootcauseTimelapseDAO emailTimelapseDAO = new EmailRootcauseTimelapseDAO();
        EmailRootcauseTimelapse emailTimelapse = emailTimelapseDAO.getEmailTimelapse(emailTimelapseId);
        ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> emailTo = sDAO.getGroupParameterDetailList(emailTimelapse.getSendCc(), "020");
        model.addAttribute("emailTo", emailTo);
        model.addAttribute("emailTimelapse", emailTimelapse);
        return "emailRootcauseTimelapse/edit";
    }

    @RequestMapping(value = "/emailTimelapseRcCa/update", method = RequestMethod.POST)
    public String updateRcCa(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String sendCc,
            @RequestParam(required = false) String userOncid,
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String flag,
            @RequestParam(required = false) String remarks
    ) {
        EmailRootcauseTimelapse emailTimelapse = new EmailRootcauseTimelapse();
        emailTimelapse.setId(id);
        emailTimelapse.setSendCc(sendCc);
        emailTimelapse.setUserOncid(userOncid);
        emailTimelapse.setUserName(userName);
        emailTimelapse.setEmail(email);
        emailTimelapse.setFlag("0");
        emailTimelapse.setRemarks(remarks);

        EmailRootcauseTimelapseDAO emailTimelapseDAO = new EmailRootcauseTimelapseDAO();
        QueryResult queryResult = emailTimelapseDAO.updateEmailTimelapse(emailTimelapse);
        args = new String[1];
        args[0] = sendCc + " - " + userOncid;
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }
        return "redirect:/whTimelapse/emailTimelapseRcCa";
    }

    @RequestMapping(value = "/emailTimelapseRcCa/delete/{emailTimelapseId}", method = RequestMethod.GET)
    public String deleteRcCa(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("emailTimelapseId") String emailTimelapseId
    ) {
        EmailRootcauseTimelapseDAO emailTimelapseDAO = new EmailRootcauseTimelapseDAO();
        EmailRootcauseTimelapse emailTimelapse = emailTimelapseDAO.getEmailTimelapse(emailTimelapseId);
        emailTimelapseDAO = new EmailRootcauseTimelapseDAO();
        QueryResult queryResult = emailTimelapseDAO.deleteEmailTimelapse(emailTimelapseId);
        args = new String[1];
        args[0] = emailTimelapse.getSendCc() + " - " + emailTimelapse.getUserOncid();
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
        }
        return "redirect:/whTimelapse/emailTimelapseRcCa";
    }

    @RequestMapping(value = "/timeLapse", method = RequestMethod.GET)
    public String timeLapse(
            Model model, @ModelAttribute UserSession userSession
    ) {
        ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> requestTypeList = sDAO.getGroupParameterDetailList("", "006");
        model.addAttribute("requestTypeList", requestTypeList);
        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> hardwareTypeList = sDAO.getGroupParameterDetailList("", "002");
        model.addAttribute("hardwareTypeList", hardwareTypeList);
        LDAPUserDAO ldapDao = new LDAPUserDAO();
        List<LDAPUser> user = ldapDao.list();
        model.addAttribute("user", user);
        return "timeLapse/timeLapse";
    }

    @RequestMapping(value = "/timeLapse/downloadExcel", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView downloadExcel(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String dateStart,
            @RequestParam(required = false) String[] sentTo,
            @RequestParam(required = false) String requestType,
            @RequestParam(required = false) String equipmentType
    ) throws ParseException {

        String year = dateStart.substring(3, 7);
        String month = dateStart.substring(0, 2);

        model.addAttribute("year", Integer.parseInt(year));
        model.addAttribute("month", Integer.parseInt(month));
        model.addAttribute("requestType", requestType);

        // return a view which will be resolved by an excel view resolver
        return new ModelAndView("excelView", "equipmentType", equipmentType);
    }
}
