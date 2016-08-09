package com.onsemi.cdars.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.cdars.dao.ParameterDetailsDAO;
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
@RequestMapping(value = "/parameterDetails")
@SessionAttributes({"userSession"})
public class ParameterDetailsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParameterDetailsController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String parameterDetails(
            Model model
    ) {
        ParameterDetailsDAO parameterDetailsDAO = new ParameterDetailsDAO();
        List<ParameterDetails> parameterDetailsList = parameterDetailsDAO.getParameterDetailsList();
        model.addAttribute("parameterDetailsList", parameterDetailsList);
        return "parameterDetails/parameterDetails";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        return "parameterDetails/add";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String masterCode,
            @RequestParam(required = false) String detailCode,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String remarks,
            @RequestParam(required = false) String createdBy,
            @RequestParam(required = false) String createdDate,
            @RequestParam(required = false) String modifiedBy,
            @RequestParam(required = false) String modifiedDate
    ) {
        ParameterDetails parameterDetails = new ParameterDetails();
        parameterDetails.setMasterCode(masterCode);
        parameterDetails.setDetailCode(detailCode);
        parameterDetails.setName(name);
        parameterDetails.setRemarks(remarks);
        parameterDetails.setCreatedBy(createdBy);
        parameterDetails.setCreatedDate(createdDate);
        parameterDetails.setModifiedBy(modifiedBy);
        parameterDetails.setModifiedDate(modifiedDate);
        ParameterDetailsDAO parameterDetailsDAO = new ParameterDetailsDAO();
        QueryResult queryResult = parameterDetailsDAO.insertParameterDetails(parameterDetails);
        args = new String[1];
        args[0] = masterCode + " - " + detailCode;
        if (queryResult.getGeneratedKey().equals("0")) {
            model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
            model.addAttribute("parameterDetails", parameterDetails);
            return "parameterDetails/add";
        } else {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
            return "redirect:/parameterDetails/edit/" + queryResult.getGeneratedKey();
        }
    }

    @RequestMapping(value = "/edit/{parameterDetailsId}", method = RequestMethod.GET)
    public String edit(
            Model model,
            @PathVariable("parameterDetailsId") String parameterDetailsId
    ) {
        ParameterDetailsDAO parameterDetailsDAO = new ParameterDetailsDAO();
        ParameterDetails parameterDetails = parameterDetailsDAO.getParameterDetails(parameterDetailsId);
        model.addAttribute("parameterDetails", parameterDetails);
        return "parameterDetails/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String masterCode,
            @RequestParam(required = false) String detailCode,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String remarks,
            @RequestParam(required = false) String createdBy,
            @RequestParam(required = false) String createdDate,
            @RequestParam(required = false) String modifiedBy,
            @RequestParam(required = false) String modifiedDate
    ) {
        ParameterDetails parameterDetails = new ParameterDetails();
        parameterDetails.setId(id);
        parameterDetails.setMasterCode(masterCode);
        parameterDetails.setDetailCode(detailCode);
        parameterDetails.setName(name);
        parameterDetails.setRemarks(remarks);
        parameterDetails.setCreatedBy(createdBy);
        parameterDetails.setCreatedDate(createdDate);
        parameterDetails.setModifiedBy(modifiedBy);
        parameterDetails.setModifiedDate(modifiedDate);
        ParameterDetailsDAO parameterDetailsDAO = new ParameterDetailsDAO();
        QueryResult queryResult = parameterDetailsDAO.updateParameterDetails(parameterDetails);
        args = new String[1];
        args[0] = masterCode + " - " + detailCode;
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }
        return "redirect:/parameterDetails/edit/" + id;
    }

    @RequestMapping(value = "/delete/{parameterDetailsId}", method = RequestMethod.GET)
    public String delete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("parameterDetailsId") String parameterDetailsId
    ) {
        ParameterDetailsDAO parameterDetailsDAO = new ParameterDetailsDAO();
        ParameterDetails parameterDetails = parameterDetailsDAO.getParameterDetails(parameterDetailsId);
        parameterDetailsDAO = new ParameterDetailsDAO();
        QueryResult queryResult = parameterDetailsDAO.deleteParameterDetails(parameterDetailsId);
        args = new String[1];
        args[0] = parameterDetails.getMasterCode() + " - " + parameterDetails.getDetailCode();
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
        }
        return "redirect:/parameterMaster";
    }

    @RequestMapping(value = "/view/{parameterDetailsId}", method = RequestMethod.GET)
    public String view(
            Model model,
            HttpServletRequest request,
            @PathVariable("parameterDetailsId") String parameterDetailsId
    ) throws UnsupportedEncodingException {
        String pdfUrl = URLEncoder.encode(request.getContextPath() + "/parameterDetails/viewParameterDetailsPdf/" + parameterDetailsId, "UTF-8");
        model.addAttribute("pdfUrl", pdfUrl);
        model.addAttribute("pageTitle", "general.label.parameterDetails");
        return "pdf/viewer";
    }

    @RequestMapping(value = "/viewParameterDetailsPdf/{parameterDetailsId}", method = RequestMethod.GET)
    public ModelAndView viewParameterDetailsPdf(
            Model model,
            @PathVariable("parameterDetailsId") String parameterDetailsId
    ) {
        ParameterDetailsDAO parameterDetailsDAO = new ParameterDetailsDAO();
        ParameterDetails parameterDetails = parameterDetailsDAO.getParameterDetails(parameterDetailsId);
        return new ModelAndView("parameterDetailsPdf", "parameterDetails", parameterDetails);
    }
}
