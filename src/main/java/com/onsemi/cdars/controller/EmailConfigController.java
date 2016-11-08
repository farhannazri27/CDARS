package com.onsemi.cdars.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.cdars.dao.EmailConfigDAO;
import com.onsemi.cdars.dao.LDAPUserDAO;
import com.onsemi.cdars.dao.ParameterDetailsDAO;
import com.onsemi.cdars.model.EmailConfig;
import com.onsemi.cdars.model.LDAPUser;
import com.onsemi.cdars.model.ParameterDetails;
import com.onsemi.cdars.model.UserSession;
import com.onsemi.cdars.tools.QueryResult;
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
@RequestMapping(value = "/admin/emailConfig")
@SessionAttributes({"userSession"})
public class EmailConfigController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailConfigController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String emailConfig(
            Model model, @ModelAttribute UserSession userSession
    ) {
        EmailConfigDAO emailConfigDAO = new EmailConfigDAO();
        List<EmailConfig> emailConfigList = emailConfigDAO.getEmailConfigList();
        String groupId = userSession.getGroup();
        model.addAttribute("emailConfigList", emailConfigList);
        model.addAttribute("groupId", groupId);
        return "emailConfig/emailConfig";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {

        ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> emailTo = sDAO.getGroupParameterDetailList("", "014");

        LDAPUserDAO ldapDao = new LDAPUserDAO();
        List<LDAPUser> user = ldapDao.list();

        model.addAttribute("emailTo", emailTo);
        model.addAttribute("user", user);
        return "emailConfig/add";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String taskPdetailsCode,
            @RequestParam(required = false) String userOncid,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String remarks,
            @RequestParam(required = false) String flag
    ) {
        EmailConfig emailConfig = new EmailConfig();
        emailConfig.setTaskPdetailsCode(taskPdetailsCode);
        emailConfig.setUserOncid(userOncid);

        LDAPUserDAO ldap = new LDAPUserDAO();
        LDAPUser lu = ldap.getByOncid(userOncid);
//        emailConfig.setEmail(lu.getEmail());
        emailConfig.setEmail(email);
        emailConfig.setUserName(lu.getFirstname() + " " + lu.getLastname());
        emailConfig.setFlag("0");
        emailConfig.setRemarks(remarks);
        EmailConfigDAO emailConfigDAO = new EmailConfigDAO();
        if (emailConfigDAO.getCountTask(taskPdetailsCode) != 0) {
            model.addAttribute("error", taskPdetailsCode + " already been assigned.");
            model.addAttribute("emailConfig", emailConfig);
            ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
            List<ParameterDetails> emailTo = sDAO.getGroupParameterDetailList("", "014");

            LDAPUserDAO ldapDao = new LDAPUserDAO();
            List<LDAPUser> user = ldapDao.list();
            model.addAttribute("emailTo", emailTo);
            model.addAttribute("user", user);
            return "emailConfig/add";
        } else {
            emailConfigDAO = new EmailConfigDAO();
            QueryResult queryResult = emailConfigDAO.insertEmailConfig(emailConfig);
            args = new String[1];
            args[0] = taskPdetailsCode + " - " + lu.getFirstname() + " " + lu.getLastname();
            if (queryResult.getGeneratedKey().equals("0")) {
                model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
                model.addAttribute("emailConfig", emailConfig);
                ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
                List<ParameterDetails> emailTo = sDAO.getGroupParameterDetailList("", "014");

                LDAPUserDAO ldapDao = new LDAPUserDAO();
                List<LDAPUser> user = ldapDao.list();
                model.addAttribute("emailTo", emailTo);
                model.addAttribute("user", user);
                return "emailConfig/add";
            } else {
                redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
                return "redirect:/admin/emailConfig/edit/" + queryResult.getGeneratedKey();
            }
        }

    }

    @RequestMapping(value = "/edit/{emailConfigId}", method = RequestMethod.GET)
    public String edit(
            Model model,
            @PathVariable("emailConfigId") String emailConfigId
    ) {
        EmailConfigDAO emailConfigDAO = new EmailConfigDAO();
        EmailConfig emailConfig = emailConfigDAO.getEmailConfig(emailConfigId);
        LDAPUserDAO ldap = new LDAPUserDAO();
        List<LDAPUser> user = ldap.list(emailConfig.getUserOncid());

        ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> emailTo = sDAO.getGroupParameterDetailList(emailConfig.getTaskPdetailsCode(), "014");

        model.addAttribute("emailConfig", emailConfig);
        model.addAttribute("user", user);
        model.addAttribute("emailTo", emailTo);
        return "emailConfig/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String emailConfigId,
            @RequestParam(required = false) String taskPdetailsCode,
            @RequestParam(required = false) String userOncid,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String remarks,
            @RequestParam(required = false) String flag
    ) {
        EmailConfig emailConfig = new EmailConfig();
        emailConfig.setId(emailConfigId);
        emailConfig.setTaskPdetailsCode(taskPdetailsCode);
        emailConfig.setUserOncid(userOncid);

        LDAPUserDAO ldap = new LDAPUserDAO();
        LDAPUser lu = ldap.getByOncid(userOncid);
//        emailConfig.setEmail(lu.getEmail());
        emailConfig.setEmail(email);
        emailConfig.setUserName(lu.getFirstname() + " " + lu.getLastname());
        emailConfig.setFlag("0");
        emailConfig.setRemarks(remarks);
        EmailConfigDAO emailConfigDAO = new EmailConfigDAO();

        QueryResult queryResult = emailConfigDAO.updateEmailConfig(emailConfig);
        args = new String[1];
        args[0] = taskPdetailsCode + " - " + lu.getFirstname() + " " + lu.getLastname();
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }
        return "redirect:/admin/emailConfig/edit/" + emailConfigId;
    }

    @RequestMapping(value = "/delete/{emailConfigId}", method = RequestMethod.GET)
    public String delete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("emailConfigId") String emailConfigId
    ) {
        EmailConfigDAO emailConfigDAO = new EmailConfigDAO();
        EmailConfig emailConfig = emailConfigDAO.getEmailConfig(emailConfigId);
        emailConfigDAO = new EmailConfigDAO();
        QueryResult queryResult = emailConfigDAO.deleteEmailConfig(emailConfigId);
        args = new String[1];
        args[0] = emailConfig.getTaskPdetailsCode() + " - " + emailConfig.getUserOncid();
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
        }
        return "redirect:/admin/emailConfig";
    }

    @RequestMapping(value = "/view/{emailConfigId}", method = RequestMethod.GET)
    public String view(
            Model model,
            HttpServletRequest request,
            @PathVariable("emailConfigId") String emailConfigId
    ) throws UnsupportedEncodingException {
        String pdfUrl = URLEncoder.encode(request.getContextPath() + "/admin/emailConfig/viewEmailConfigPdf/" + emailConfigId, "UTF-8");
        String backUrl = servletContext.getContextPath() + "/admin/emailConfig";
        model.addAttribute("pdfUrl", pdfUrl);
        model.addAttribute("backUrl", backUrl);
        model.addAttribute("pageTitle", "general.label.emailConfig");
        return "pdf/viewer";
    }

    @RequestMapping(value = "/viewEmailConfigPdf/{emailConfigId}", method = RequestMethod.GET)
    public ModelAndView viewEmailConfigPdf(
            Model model,
            @PathVariable("emailConfigId") String emailConfigId
    ) {
        EmailConfigDAO emailConfigDAO = new EmailConfigDAO();
        EmailConfig emailConfig = emailConfigDAO.getEmailConfig(emailConfigId);
        return new ModelAndView("emailConfigPdf", "emailConfig", emailConfig);
    }
}
