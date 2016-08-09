package com.onsemi.cdars.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.cdars.dao.IonicLimitDAO;
import com.onsemi.cdars.model.IonicLimit;
import com.onsemi.cdars.model.UserSession;
import com.onsemi.cdars.tools.QueryResult;
import com.onsemi.cdars.tools.SystemUtil;
import java.text.DecimalFormat;
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
@RequestMapping(value = "admin/ionicLimit")
@SessionAttributes({"userSession"})
public class IonicLimitController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IonicLimitController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String ionicLimit(
            Model model
    ) {
        IonicLimitDAO ionicLimitDAO = new IonicLimitDAO();
        List<IonicLimit> ionicLimitList = ionicLimitDAO.getIonicLimitList();
        model.addAttribute("ionicLimitList", ionicLimitList);
        return "ionicLimit/ionicLimit";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        return "ionicLimit/add";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String value
    ) {
        IonicLimit ionicLimit = new IonicLimit();
        ionicLimit.setName(name);
        ionicLimit.setValue(value);
        ionicLimit.setCreatedBy(userSession.getId());
        IonicLimitDAO ionicLimitDAO = new IonicLimitDAO();
        QueryResult queryResult = ionicLimitDAO.insertIonicLimit(ionicLimit);
        args = new String[1];
        args[0] = name + " - " + value;
        if (queryResult.getGeneratedKey().equals("0")) {
            model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
            model.addAttribute("ionicLimit", ionicLimit);
            return "ionicLimit/add";
        } else {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
            return "redirect:/admin/ionicLimit/edit/" + queryResult.getGeneratedKey();
        }
    }

    @RequestMapping(value = "/edit/{ionicLimitId}", method = RequestMethod.GET)
    public String edit(
            Model model,
            @PathVariable("ionicLimitId") String ionicLimitId
    ) {
        IonicLimitDAO ionicLimitDAO = new IonicLimitDAO();
        IonicLimit ionicLimit = ionicLimitDAO.getIonicLimit(ionicLimitId);
        model.addAttribute("ionicLimit", ionicLimit);
        return "ionicLimit/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String ionicLimitId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String value
    ) {
        IonicLimit ionicLimit = new IonicLimit();
        ionicLimit.setId(ionicLimitId);
        ionicLimit.setName(name);
        ionicLimit.setValue(value);
        ionicLimit.setModifiedBy(userSession.getId());
        IonicLimitDAO ionicLimitDAO = new IonicLimitDAO();
        QueryResult queryResult = ionicLimitDAO.updateIonicLimit(ionicLimit);
        args = new String[1];
        args[0] = name + " - " + value;
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }
        return "redirect:/admin/ionicLimit/edit/" + ionicLimitId;
    }

    @RequestMapping(value = "/delete/{ionicLimitId}", method = RequestMethod.GET)
    public String delete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("ionicLimitId") String ionicLimitId
    ) {
        IonicLimitDAO ionicLimitDAO = new IonicLimitDAO();
        IonicLimit ionicLimit = ionicLimitDAO.getIonicLimit(ionicLimitId);
        ionicLimitDAO = new IonicLimitDAO();
        QueryResult queryResult = ionicLimitDAO.deleteIonicLimit(ionicLimitId);
        args = new String[1];
        args[0] = ionicLimit.getName() + " - " + ionicLimit.getValue();
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
        }
        return "redirect:/admin/ionicLimit";
    }

    @RequestMapping(value = "/view/{ionicLimitId}", method = RequestMethod.GET)
    public String view(
            Model model,
            HttpServletRequest request,
            @PathVariable("ionicLimitId") String ionicLimitId
    ) throws UnsupportedEncodingException {
        String pdfUrl = URLEncoder.encode(request.getContextPath() + "/admin/ionicLimit/viewIonicLimitPdf/" + ionicLimitId, "UTF-8");
        model.addAttribute("pdfUrl", pdfUrl);
        model.addAttribute("pageTitle", "general.label.ionicLimit");
        return "pdf/viewer";
    }

    @RequestMapping(value = "/viewIonicLimitPdf/{ionicLimitId}", method = RequestMethod.GET)
    public ModelAndView viewIonicLimitPdf(
            Model model,
            @PathVariable("ionicLimitId") String ionicLimitId
    ) {
        IonicLimitDAO ionicLimitDAO = new IonicLimitDAO();
        IonicLimit ionicLimit = ionicLimitDAO.getIonicLimit(ionicLimitId);
        return new ModelAndView("ionicLimitPdf", "ionicLimit", ionicLimit);
    }
}
