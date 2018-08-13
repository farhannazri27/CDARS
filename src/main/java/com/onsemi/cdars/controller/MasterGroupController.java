package com.onsemi.cdars.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.cdars.dao.MasterGroupDAO;
import com.onsemi.cdars.dao.UserGroupDAO;
import com.onsemi.cdars.model.MasterGroup;
import com.onsemi.cdars.model.UserGroup;
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
@RequestMapping(value = "admin/masterGroup")
@SessionAttributes({"userSession"})
public class MasterGroupController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MasterGroupController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String masterGroup(
            Model model
    ) {
        MasterGroupDAO masterGroupDAO = new MasterGroupDAO();
        List<MasterGroup> masterGroupList = masterGroupDAO.getMasterGroupList();
        model.addAttribute("masterGroupList", masterGroupList);
        return "masterGroup/masterGroup";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        return "masterGroup/add";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String group,
            @RequestParam(required = false) String hardware,
            @RequestParam(required = false) String remarks,
            @RequestParam(required = false) String createdBy
    ) {
        MasterGroup masterGroup = new MasterGroup();
        masterGroup.setGroupMaster(group);
        masterGroup.setHardware(hardware);
        masterGroup.setType("test");
        masterGroup.setRemarks(remarks);
        masterGroup.setCreatedBy(userSession.getFullname());
        masterGroup.setFlag("0");
        MasterGroupDAO masterGroupDAO = new MasterGroupDAO();
        QueryResult queryResult = masterGroupDAO.insertMasterGroup(masterGroup);
        args = new String[1];
        args[0] = group + " - " + hardware;
        if (queryResult.getGeneratedKey().equals("0")) {
            model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
            model.addAttribute("masterGroup", masterGroup);
            return "masterGroup/add";
        } else {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
            return "redirect:/admin/masterGroup/edit/" + queryResult.getGeneratedKey();
        }
    }

    @RequestMapping(value = "/edit/{masterGroupId}", method = RequestMethod.GET)
    public String edit(
            Model model,
            @PathVariable("masterGroupId") String masterGroupId
    ) {
        MasterGroupDAO masterGroupDAO = new MasterGroupDAO();
        MasterGroup masterGroup = masterGroupDAO.getMasterGroup(masterGroupId);
        model.addAttribute("masterGroup", masterGroup);
        return "masterGroup/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String group,
            @RequestParam(required = false) String hardware,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String remarks
    ) {
        MasterGroup masterGroup = new MasterGroup();
        masterGroup.setId(id);
        masterGroup.setGroupMaster(group);
        masterGroup.setHardware(hardware);
        masterGroup.setType(type);
        masterGroup.setRemarks(remarks);
        MasterGroupDAO masterGroupDAO = new MasterGroupDAO();
        QueryResult queryResult = masterGroupDAO.updateMasterGroupWithoutType(masterGroup);
        args = new String[1];
        args[0] = group + " - " + hardware;
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }
        return "redirect:/admin/masterGroup/edit/" + id;
    }

    @RequestMapping(value = "/delete/{masterGroupId}", method = RequestMethod.GET)
    public String delete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("masterGroupId") String masterGroupId
    ) {
        MasterGroupDAO masterGroupDAO = new MasterGroupDAO();
        MasterGroup masterGroup = masterGroupDAO.getMasterGroup(masterGroupId);
        masterGroupDAO = new MasterGroupDAO();
        QueryResult queryResult = masterGroupDAO.deleteMasterGroup(masterGroupId);
        args = new String[1];
        args[0] = masterGroup.getGroupMaster()+ " - " + masterGroup.getHardware();
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
        }
        return "redirect:/admin/masterGroup";
    }

    @RequestMapping(value = "/view/{masterGroupId}", method = RequestMethod.GET)
    public String view(
            Model model,
            HttpServletRequest request,
            @PathVariable("masterGroupId") String masterGroupId
    ) throws UnsupportedEncodingException {
        String pdfUrl = URLEncoder.encode(request.getContextPath() + "/masterGroup/viewMasterGroupPdf/" + masterGroupId, "UTF-8");
        String backUrl = servletContext.getContextPath() + "/masterGroup";
        model.addAttribute("pdfUrl", pdfUrl);
        model.addAttribute("backUrl", backUrl);
        model.addAttribute("pageTitle", "general.label.masterGroup");
        return "pdf/viewer";
    }

    @RequestMapping(value = "/viewMasterGroupPdf/{masterGroupId}", method = RequestMethod.GET)
    public ModelAndView viewMasterGroupPdf(
            Model model,
            @PathVariable("masterGroupId") String masterGroupId
    ) {
        MasterGroupDAO masterGroupDAO = new MasterGroupDAO();
        MasterGroup masterGroup = masterGroupDAO.getMasterGroup(masterGroupId);
        return new ModelAndView("masterGroupPdf", "masterGroup", masterGroup);
    }

    //add, edit delete child group
    @RequestMapping(value = "/addChildGroup/{masterGroupId}", method = RequestMethod.GET)
    public String editChildGroup(
            Model model,
            @PathVariable("masterGroupId") String masterGroupId
    ) {
        UserGroupDAO userGroupDAO = new UserGroupDAO();
        List<UserGroup> userGroupList = userGroupDAO.getGroupListbyMasterGroupId(masterGroupId);
        model.addAttribute("userGroupList", userGroupList);

        userGroupDAO = new UserGroupDAO();
        List<UserGroup> userGroupListAll = userGroupDAO.getGroupListforMaster();
        model.addAttribute("userGroupListAll", userGroupListAll);

        MasterGroupDAO masterGroupDAO = new MasterGroupDAO();
        MasterGroup masterGroupDetail = masterGroupDAO.getMasterGroup(masterGroupId);
        String masterGroup = masterGroupDetail.getGroupMaster();
        model.addAttribute("masterGroup", masterGroup);
        
        masterGroupDAO = new MasterGroupDAO();
        MasterGroup masterId2 = masterGroupDAO.getMasterGroup(masterGroupId);
        String masterId = masterGroupDetail.getId();
        model.addAttribute("masterId", masterId);

        masterGroupDAO = new MasterGroupDAO();
        MasterGroup masterGroupName = masterGroupDAO.getMasterGroup(masterGroupId);
        String masterHardware = masterGroupName.getHardware();
        model.addAttribute("masterHardware", masterHardware);
        return "masterGroup/addChild";
    }

    @RequestMapping(value = "/updateChildGroup", method = RequestMethod.POST)
    public String updateChildGroup(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String masterGroupId
    ) {
        UserGroup userGroup = new UserGroup();
        userGroup.setId(id);
        userGroup.setMasterGroupId(masterGroupId);
        userGroup.setModifiedBy(userSession.getId());
        UserGroupDAO userGroupDAO = new UserGroupDAO();
        QueryResult queryResult = userGroupDAO.updateMasterGroupId(userGroup);

        userGroupDAO = new UserGroupDAO();
        UserGroup userGroupDetail = userGroupDAO.getGroup(id);
        String groupName = userGroupDetail.getName();

        MasterGroupDAO masterGroupDAO = new MasterGroupDAO();
        MasterGroup masterGroupDetail = masterGroupDAO.getMasterGroup(masterGroupId);
        String masterGroupName = masterGroupDetail.getGroupMaster();

        args = new String[1];
        args[0] = masterGroupName + "'s Group - " + groupName;
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }
        return "redirect:/admin/masterGroup/addChildGroup/" + masterGroupId;
    }

    @RequestMapping(value = "/deleteChildGroup/{childGroupId}", method = RequestMethod.GET)
    public String deleteChildGroup(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @PathVariable("childGroupId") String childGroupId
    ) {
        UserGroupDAO userGroupDAO = new UserGroupDAO();
        UserGroup userGroup = userGroupDAO.getGroup(childGroupId);

        MasterGroupDAO masterGroupDAO = new MasterGroupDAO();
        MasterGroup masterGroup = masterGroupDAO.getMasterGroup(userGroup.getMasterGroupId());

        UserGroup userGroupUpdate = new UserGroup();
        userGroupUpdate.setId(childGroupId);
        userGroupUpdate.setMasterGroupId("0");
        userGroupUpdate.setModifiedBy(userSession.getId());
        userGroupDAO = new UserGroupDAO();
        QueryResult queryResult = userGroupDAO.updateMasterGroupId(userGroupUpdate);

        args = new String[1];
        args[0] = masterGroup.getGroupMaster()+ "'s Group - " + userGroup.getName();
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
        }
        return "redirect:/admin/masterGroup/addChildGroup/" + userGroup.getMasterGroupId();
    }
}
