package com.onsemi.cdars.controller;

import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.cdars.dao.WhMpListDAO;
import com.onsemi.cdars.dao.WhShippingDAO;
import com.onsemi.cdars.model.WhMpList;
import com.onsemi.cdars.model.UserSession;
import com.onsemi.cdars.model.WhShipping;
import com.onsemi.cdars.tools.EmailSender;
import com.onsemi.cdars.tools.QueryResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/wh/whShipping/whMpList")
@SessionAttributes({"userSession"})
public class WhMpListController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WhMpListController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    //Delimiters which has to be in the CSV file
    private static final String COMMA_DELIMITER = ",";
    private static final String LINE_SEPARATOR = "\n";
    private static final String HEADER = "id,hardware_type,hardware_id,quantity,material pass number,material pass expiry date,requested_by,"
            + "requestor_email,requested_date,remarks,shipping_date";

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String whMpList(
            Model model
    ) {
        WhMpListDAO whMpListDAO = new WhMpListDAO();
        List<WhMpList> whMpListList = whMpListDAO.getWhMpListListDateDisplay();
        model.addAttribute("whMpListList", whMpListList);
        return "whMpList/whMpList";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        return "whMpList/add";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(
            Model model,
            HttpServletRequest request,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String mpNo
    ) {
        //check ad material pass ke tidak dkt shipping.
        WhShippingDAO whShipD = new WhShippingDAO();
        int count = whShipD.getCountMpNo(mpNo);
        if (count != 0) {
            //check da ade ke mp_no dkt whmplist
            WhMpListDAO whMpListDAO = new WhMpListDAO();
            int countMpNo = whMpListDAO.getCountMpNo(mpNo);
            if (countMpNo == 0) {
                WhShippingDAO whshipD = new WhShippingDAO();

                WhShipping whship = whshipD.getWhShippingMergeWithRequestByMpNo(mpNo);

                WhMpList whMpList = new WhMpList();
                whMpList.setWhShipId(whship.getId());
                whMpList.setMpNo(mpNo);
                whMpList.setMpExpiryDate(whship.getMpExpiryDate());
                whMpList.setHardwareId(whship.getRequestEquipmentId());
                whMpList.setHardwareType(whship.getRequestEquipmentType());
                whMpList.setQuantity(whship.getRequestQuantity());
                whMpList.setRequestedBy(whship.getRequestRequestedBy());
                whMpList.setRequestedDate(whship.getRequestRequestedDate());
                whMpList.setCreatedBy(userSession.getFullname());
                whMpListDAO = new WhMpListDAO();
                QueryResult queryResult = whMpListDAO.insertWhMpList(whMpList);
                args = new String[1];
                args[0] = mpNo;
                if (queryResult.getGeneratedKey().equals("0")) {
                    model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
                    model.addAttribute("whMpList", whMpList);
                    return "whMpList/add";
                } else {
                    String username = System.getProperty("user.name");
                    File file = new File("C:\\Users\\" + username + "\\Documents\\CDARS\\cdars_shipping.csv");

                    if (file.exists()) {
                        //create csv file
                        LOGGER.info("tiada header");
                        FileWriter fileWriter = null;
                        try {
                            fileWriter = new FileWriter("C:\\Users\\" + username + "\\Documents\\CDARS\\cdars_shipping.csv", true);
                            //New Line after the header
                            fileWriter.append(LINE_SEPARATOR);

//                            fileWriter.append(queryResult.getGeneratedKey());
                            fileWriter.append(whship.getRequestId());
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(whship.getRequestEquipmentType());
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(whship.getRequestEquipmentId());
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(whship.getRequestQuantity());
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(mpNo);
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(whship.getMpExpiryDate());
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(whship.getRequestRequestedBy());
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(whship.getRequestRequestorEmail());
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(whship.getRequestRequestedDate());
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(whship.getRemarks());
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(whship.getCreatedDate());
                            fileWriter.append(COMMA_DELIMITER);
                            System.out.println("append to CSV file Succeed!!!");
                        } catch (Exception ee) {
                            ee.printStackTrace();
                        } finally {
                            try {
                                fileWriter.close();
                            } catch (IOException ie) {
                                System.out.println("Error occured while closing the fileWriter");
                                ie.printStackTrace();
                            }
                        }
                    } else {
                        FileWriter fileWriter = null;
                        try {
                            fileWriter = new FileWriter("C:\\Users\\" + username + "\\Documents\\CDARS\\cdars_shipping.csv");
                            LOGGER.info("no file yet");
                            //Adding the header
                            fileWriter.append(HEADER);

                            //New Line after the header
                            fileWriter.append(LINE_SEPARATOR);

                            fileWriter.append(whship.getRequestId());
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(whship.getRequestEquipmentType());
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(whship.getRequestEquipmentId());
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(whship.getRequestQuantity());
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(mpNo);
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(whship.getMpExpiryDate());
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(whship.getRequestRequestedBy());
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(whship.getRequestRequestorEmail());
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(whship.getRequestRequestedDate());
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(whship.getRemarks());
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(whship.getCreatedDate());
                            fileWriter.append(COMMA_DELIMITER);
                            System.out.println("Write new to CSV file Succeed!!!");
                        } catch (Exception ee) {
                            ee.printStackTrace();
                        } finally {
                            try {
                                fileWriter.close();
                            } catch (IOException ie) {
                                System.out.println("Error occured while closing the fileWriter");
                                ie.printStackTrace();
                            }
                        }
                    }

//                    hold until testing 7/9/16
                    EmailSender emailSender = new EmailSender();
                    com.onsemi.cdars.model.User user = new com.onsemi.cdars.model.User();
                    user.setFullname(userSession.getFullname());
                    String[] to = {"farhannazri27@yahoo.com", "hmsrelon@gmail.com"};
                    emailSender.htmlEmailWithAttachment(
                            servletContext,
                            //                    user name
                            user,
                            //                    to
                            to,
                            //                         "farhannazri27@yahoo.com",
                            // attachment file
                            new File("C:\\Users\\" + username + "\\Documents\\CDARS\\cdars_shipping.csv"),
                            //                    subject
                            "New Hardware Shipping from CDARS",
                            //                    msg
                            "New hardware will be ship to storage factory. "
                    //                                    + "Please go to this link "
                    //                            + "<a href=\"" + request.getScheme() + "://fg79cj-l1:" + request.getServerPort() + request.getContextPath() + "/wh/whRequest/approval/" + queryResult.getGeneratedKey() + "\">CDARS</a>"
                    //                            + " to check the shipping list."
                    );

                    //update status at shipping list to "Ship"
                    WhShippingDAO shipD = new WhShippingDAO();
                    WhShipping ship = shipD.getWhShippingMergeWithRequestByMpNo(mpNo);

                    WhShipping shipUpdate = new WhShipping();
                    shipUpdate.setId(ship.getId());
                    shipUpdate.setRequestId(ship.getRequestId());
                    shipUpdate.setStatus("Ship");
                    WhShippingDAO shipDD = new WhShippingDAO();
                    QueryResult u = shipDD.updateWhShippingStatus(shipUpdate);
                    if (u.getResult() == 1) {
                        LOGGER.info("Status Ship updated");
                    } else {
                        LOGGER.info("Status Ship updated failed");
                    }

                    redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
//			return "redirect:/whMpList/edit/" + queryResult.getGeneratedKey();
//                    return "redirect:/wh/whMpList/add"; 
                    return "whMpList/add";
                }
            } else {
                String messageError = "Material Pass Number " + mpNo + " already added to the list!";
                model.addAttribute("error", messageError);
                return "whMpList/add";
            }

        } else {
            String messageError = "Material Pass Number " + mpNo + " Not Exist!";
            model.addAttribute("error", messageError);
            return "whMpList/add";
        }

    }

    @RequestMapping(value = "/deleteAll", method = RequestMethod.GET)
    public String deleteAll(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs
    ) {
        WhMpListDAO whMpListDAO = new WhMpListDAO();
        QueryResult queryResult = whMpListDAO.deleteAllWhMpList();
        args = new String[1];
        args[0] = "All data has been ";
        String error = "Unable to delete, please try again!";
        if (queryResult.getResult() > 0) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", error);
        }
        return "redirect:/wh/whShipping/whMpList";
    }
}
