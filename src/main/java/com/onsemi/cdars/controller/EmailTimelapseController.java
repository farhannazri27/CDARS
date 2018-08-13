package com.onsemi.cdars.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.cdars.dao.EmailTimelapseDAO;
import com.onsemi.cdars.dao.LDAPUserDAO;
import com.onsemi.cdars.dao.ParameterDetailsDAO;
import com.onsemi.cdars.model.EmailTimelapse;
import com.onsemi.cdars.model.LDAPUser;
import com.onsemi.cdars.model.ParameterDetails;
import com.onsemi.cdars.model.UserSession;
import com.onsemi.cdars.tools.QueryResult;
import java.text.ParseException;
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
@RequestMapping(value = "/whTimelapse2")
@SessionAttributes({"userSession"})
public class EmailTimelapseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailTimelapseController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

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
                return "redirect:/whTimelapse2/emailTimelapse";
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
        return "redirect:/whTimelapse2/emailTimelapse";
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
        return "redirect:/whTimelapse2/emailTimelapse";
    }

    @RequestMapping(value = "/emailTimelapse/view/{emailTimelapseId}", method = RequestMethod.GET)
    public String view(
            Model model,
            HttpServletRequest request,
            @PathVariable("emailTimelapseId") String emailTimelapseId
    ) throws UnsupportedEncodingException {
        String pdfUrl = URLEncoder.encode(request.getContextPath() + "/whTimelapse2/emailTimelapse/viewEmailTimelapsePdf/" + emailTimelapseId, "UTF-8");
        String backUrl = servletContext.getContextPath() + "/whTimelapse2/emailTimelapse";
        model.addAttribute("pdfUrl", pdfUrl);
        model.addAttribute("backUrl", backUrl);
        model.addAttribute("pageTitle", "general.label.emailTimelapse");
        return "pdf/viewer";
    }

    @RequestMapping(value = "/emailTimelapse/viewEmailTimelapsePdf/{emailTimelapseId}", method = RequestMethod.GET)
    public ModelAndView viewEmailTimelapsePdf(
            Model model,
            @PathVariable("emailTimelapseId") String emailTimelapseId
    ) {
        EmailTimelapseDAO emailTimelapseDAO = new EmailTimelapseDAO();
        EmailTimelapse emailTimelapse = emailTimelapseDAO.getEmailTimelapse(emailTimelapseId);
        return new ModelAndView("emailTimelapsePdf", "emailTimelapse", emailTimelapse);
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

//    @RequestMapping(value = "/timeLapse/downloadExcel", method = RequestMethod.POST)
//    public String test(
//            Model model,
//            Locale locale,
//            RedirectAttributes redirectAttrs,
//            @ModelAttribute UserSession userSession,
//            @RequestParam(required = false) String dateStart,
//            @RequestParam(required = false) String[] sentTo,
//            @RequestParam(required = false) String requestType,
//            @RequestParam(required = false) String equipmentType
//    ) {
//        LOGGER.info("sent....." + Arrays.toString(sentTo));
//        LOGGER.info("dateStart....." + dateStart);
//
//        //send email
////        LOGGER.info("send email to person in charge");
////        EmailSender emailSender = new EmailSender();
////        com.onsemi.cdars.model.User user = new com.onsemi.cdars.model.User();
////        user.setFullname("All");
////
////        List<String> a = new ArrayList<String>();
////        String[] myArray = new String[a.size()];
////        String[] emailTo = a.toArray(myArray);
////        String[] to = {"fg79cj@onsemi.com"};
////        emailSender.htmlEmailManyTo(
////                servletContext,
////                user, //user name requestor
////                sentTo,
////                "Test", //subject
////                "<br />Test Purpose only. Thank you." //msg
////        );
//        return "redirect:/admin/timeLapse";
//
//    }
}
