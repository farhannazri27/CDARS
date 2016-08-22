package com.onsemi.cdars.controller;

import com.onsemi.cdars.dao.ParameterDetailsDAO;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.cdars.dao.ParameterMasterDAO;
import com.onsemi.cdars.model.ParameterDetails;
import com.onsemi.cdars.model.ParameterMaster;
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
@RequestMapping(value = "admin/parameterMaster")
@SessionAttributes({"userSession"})
public class ParameterMasterController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParameterMasterController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String parameterMaster(
            Model model
    ) {
        ParameterMasterDAO parameterMasterDAO = new ParameterMasterDAO();
        List<ParameterMaster> parameterMasterList = parameterMasterDAO.getParameterMasterList();
        model.addAttribute("parameterMasterList", parameterMasterList);
        return "parameterMaster/parameterMaster";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        ParameterMasterDAO parameterMasterDAO = new ParameterMasterDAO();
        String code = parameterMasterDAO.getNextMasterCode();
        model.addAttribute("mastercode", code);
        return "parameterMaster/add";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String remarks
    ) {
        ParameterMaster parameterMaster = new ParameterMaster();
        ParameterMasterDAO parameterMasterDAOss = new ParameterMasterDAO();
        String mastercode = parameterMasterDAOss.getNextMasterCode();
        parameterMaster.setCode(mastercode);
        parameterMaster.setName(name);
        parameterMaster.setRemarks(remarks);
        parameterMaster.setCreatedBy(userSession.getId());
        ParameterMasterDAO parameterMasterDAO = new ParameterMasterDAO();
        QueryResult queryResult = parameterMasterDAO.insertParameterMaster(parameterMaster);
        args = new String[1];
        args[0] = mastercode + " - " + name;
        if (queryResult.getGeneratedKey().equals("0")) {
            model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
            model.addAttribute("parameterMaster", parameterMaster);
            return "parameterMaster/add";
        } else {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
            return "redirect:/admin/parameterMaster/edit/" + queryResult.getGeneratedKey();
        }
    }

    @RequestMapping(value = "/edit/{parameterMasterId}", method = RequestMethod.GET)
    public String edit(
            Model model,
            @PathVariable("parameterMasterId") String parameterMasterId
    ) {
        ParameterMasterDAO parameterMasterDAO = new ParameterMasterDAO();
        ParameterMaster parameterMaster = parameterMasterDAO.getParameterMaster(parameterMasterId);
        model.addAttribute("parameterMaster", parameterMaster);
        return "parameterMaster/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String parameterMasterId,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String remarks
    ) {
        ParameterMaster parameterMaster = new ParameterMaster();
        parameterMaster.setId(parameterMasterId);
        parameterMaster.setCode(code);
        parameterMaster.setName(name);
        parameterMaster.setRemarks(remarks);
        parameterMaster.setModifiedBy(userSession.getId());
        ParameterMasterDAO parameterMasterDAO = new ParameterMasterDAO();
        QueryResult queryResult = parameterMasterDAO.updateParameterMaster(parameterMaster);
        args = new String[1];
        args[0] = code + " - " + name;
        LOGGER.info("xcxcxcxcxcxcxcxcxc " + queryResult.getResult());
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }
        return "redirect:/admin/parameterMaster/edit/" + parameterMasterId;
    }

    @RequestMapping(value = "/delete/{parameterMasterId}", method = RequestMethod.GET)
    public String delete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("parameterMasterId") String parameterMasterId
    ) {
        ParameterMasterDAO parameterMasterDAO = new ParameterMasterDAO();
        ParameterMaster parameterMaster = parameterMasterDAO.getParameterMaster(parameterMasterId);

        ParameterDetailsDAO detailsDAO = new ParameterDetailsDAO();
        int detailsCount = detailsDAO.getCountByMasterCode(parameterMaster.getCode());
        if (detailsCount == 0) {
            parameterMasterDAO = new ParameterMasterDAO();
            QueryResult queryResult = parameterMasterDAO.deleteParameterMaster(parameterMasterId);
            args = new String[1];
            args[0] = parameterMaster.getCode() + " - " + parameterMaster.getName();
            if (queryResult.getResult() == 1) {
                redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
            } else {
                redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
            }
        } else {
            args = new String[]{Integer.toString(detailsCount)};
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.parameter.delete.have_details.error", args, locale));
        }

        return "redirect:/admin/parameterMaster";
    }

    @RequestMapping(value = "/view/{parameterMasterId}", method = RequestMethod.GET)
    public String view(
            Model model,
            HttpServletRequest request,
            @PathVariable("parameterMasterId") String parameterMasterId
    ) throws UnsupportedEncodingException {
        String pdfUrl = URLEncoder.encode(request.getContextPath() + "/admin/parameterMaster/viewParameterMasterPdf/" + parameterMasterId, "UTF-8");
        String backUrl = servletContext.getContextPath() + "/parameterMaster";
        model.addAttribute("pdfUrl", pdfUrl);
        model.addAttribute("backUrl", backUrl);
        model.addAttribute("pageTitle", "general.label.parameterMaster");
        return "pdf/viewer";
    }

    @RequestMapping(value = "/viewParameterMasterPdf/{parameterMasterId}", method = RequestMethod.GET)
    public ModelAndView viewParameterMasterPdf(
            Model model,
            @PathVariable("parameterMasterId") String parameterMasterId
    ) {
        ParameterMasterDAO parameterMasterDAO = new ParameterMasterDAO();
        ParameterMaster parameterMaster = parameterMasterDAO.getParameterMaster(parameterMasterId);
        return new ModelAndView("parameterMasterPdf", "parameterMaster", parameterMaster);
    }

//Parameter Details
    @RequestMapping(value = "/detailList/{parameterMasterCode}", method = RequestMethod.GET)
    public String parameterDetails(
            Model model,
            @PathVariable("parameterMasterCode") String parameterMasterCode
    ) {
        ParameterDetailsDAO parameterDetailsDAO = new ParameterDetailsDAO();
        List<ParameterDetails> parameterDetailsList = parameterDetailsDAO.getParameterDetailsListByMasterCode(parameterMasterCode);
        model.addAttribute("parameterDetailsList", parameterDetailsList);
        return "parameterDetails/parameterDetails";
    }

    @RequestMapping(value = "/addDetail/{parameterMasterCode}", method = RequestMethod.GET)
    public String addParameterDetail(
            Model model,
            @PathVariable("parameterMasterCode") String parameterMasterCode
    ) {
        ParameterMasterDAO parameterMasterDAO = new ParameterMasterDAO();
        String codeName = parameterMasterDAO.getMasterCodeandName(parameterMasterCode);
        
        parameterMasterDAO = new ParameterMasterDAO();
//        ParameterMasterDAO par = new ParameterMasterDAO();
        String masterCode = parameterMasterDAO.getMasterCode(parameterMasterCode);
        
        ParameterDetailsDAO p = new ParameterDetailsDAO();
        String detailsCode = p.getNextDetailCode(parameterMasterCode);
        
        p = new ParameterDetailsDAO();
//        ParameterDetailsDAO parameterDetailsDAO = new ParameterDetailsDAO();
        List<ParameterDetails> parameterDetailsList = p.getParameterDetailsListByMasterCode(parameterMasterCode);
        model.addAttribute("parameterDetailsList", parameterDetailsList);
        model.addAttribute("codeName", codeName);
        model.addAttribute("masterCode", masterCode);
        model.addAttribute("detailsCode", detailsCode);
        return "parameterDetails/add";
    }

    @RequestMapping(value = "/saveDetail", method = RequestMethod.POST)
    public String save(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String masterName,
            @RequestParam(required = false) String masterCode,
            @RequestParam(required = false) String detailsCode,
            @RequestParam(required = false) String detailsName,
            @RequestParam(required = false) String remarks
    ) {
        ParameterDetails parameterDetails = new ParameterDetails();
        parameterDetails.setMasterCode(masterCode);
        parameterDetails.setDetailCode(detailsCode);
        parameterDetails.setName(detailsName);
        parameterDetails.setRemarks(remarks);
        parameterDetails.setCreatedBy(userSession.getId());
        ParameterDetailsDAO parameterDetailsDAO = new ParameterDetailsDAO();
        QueryResult queryResult = parameterDetailsDAO.insertParameterDetails(parameterDetails);
        args = new String[1];
        args[0] = detailsCode + " - " + detailsName;
        if (queryResult.getGeneratedKey().equals("0")) {
            model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
            model.addAttribute("parameterDetails", parameterDetails);
            return "parameterDetails/add";
        } else {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
            return "redirect:/admin/parameterMaster/addDetail/" + masterCode;
        }
    }

    @RequestMapping(value = "/editDetails/{parameterDetailsId}", method = RequestMethod.GET)
    public String editDetails(
            Model model,
            @PathVariable("parameterDetailsId") String parameterDetailsId
    ) {
        ParameterDetailsDAO parameterDetailsDAO = new ParameterDetailsDAO();
        ParameterDetails parameterDetails = parameterDetailsDAO.getParameterDetails(parameterDetailsId);
        model.addAttribute("parameterDetails", parameterDetails);
        return "parameterDetails/edit";
    }

    @RequestMapping(value = "/updateDetails", method = RequestMethod.POST)
    public String updateDetail(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String masterCode,
            @RequestParam(required = false) String detailCode,
            @RequestParam(required = false) String detailsName,
            @RequestParam(required = false) String remarks
    ) {
        ParameterDetails parameterDetails = new ParameterDetails();
        parameterDetails.setId(id);
        parameterDetails.setMasterCode(masterCode);
        parameterDetails.setDetailCode(detailCode);
        parameterDetails.setName(detailsName);
        parameterDetails.setRemarks(remarks);
        parameterDetails.setModifiedBy(userSession.getId());
        ParameterDetailsDAO parameterDetailsDAO = new ParameterDetailsDAO();
        QueryResult queryResult = parameterDetailsDAO.updateParameterDetails(parameterDetails);
        args = new String[1];
        args[0] = detailsName + " - " + detailCode;
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }
//        return "redirect:/admin/parameterMaster/editDetails/" + id;
        return "redirect:/admin/parameterMaster/addDetail/" + masterCode;
    }

    @RequestMapping(value = "/deleteDetail/{parameterDetailsId}", method = RequestMethod.GET)
    public String deleteDetails(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("parameterDetailsId") String parameterDetailsId
    ) {
        ParameterDetailsDAO parameterDetailsDAO = new ParameterDetailsDAO();
        ParameterDetails parameterDetails = parameterDetailsDAO.getParameterDetails(parameterDetailsId);
        String masterCode = parameterDetails.getMasterCode();
        parameterDetailsDAO = new ParameterDetailsDAO();
        QueryResult queryResult = parameterDetailsDAO.deleteParameterDetails(parameterDetailsId);
        args = new String[1];
        args[0] = parameterDetails.getName() + " - " + parameterDetails.getDetailCode();
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
        }
//        return "redirect:/admin/parameterMaster";
        return "redirect:/admin/parameterMaster/addDetail/" + masterCode;
    }
}
