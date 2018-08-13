package com.onsemi.cdars.controller;

import com.onsemi.cdars.dao.LDAPUserDAO;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.cdars.dao.MenuDAO;
import com.onsemi.cdars.dao.UserDAO;
import com.onsemi.cdars.dao.UserGroupAccessDAO;
import com.onsemi.cdars.dao.UserGroupDAO;
import com.onsemi.cdars.model.JSONResponse;
import com.onsemi.cdars.model.LDAPUser;
import com.onsemi.cdars.model.Menu;
import com.onsemi.cdars.tools.QueryResult;
import com.onsemi.cdars.model.User;
import com.onsemi.cdars.model.UserGroup;
import com.onsemi.cdars.model.UserGroupAccess;
import com.onsemi.cdars.model.UserSession;
import com.onsemi.cdars.tools.SystemUtil;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/admin")
@SessionAttributes({"userSession"})
@PropertySource("classpath:ldap.properties")
public class AdminController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private Environment env;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String home(Model model) {
        return "redirect:/admin/user";
    }

    @RequestMapping(value = "/user", method = {RequestMethod.GET, RequestMethod.POST})
    public String user(
            Model model,
            @RequestParam(required = false) String selectedGroup
    ) {
        selectedGroup = SystemUtil.nullToEmptyString(selectedGroup);
        LDAPUserDAO ldapUserDAO = new LDAPUserDAO();
        List<LDAPUser> ldapUserList = ldapUserDAO.listByGroupId(selectedGroup);
        UserGroupDAO userGroupDAO = new UserGroupDAO();
        List<UserGroup> userGroupList = userGroupDAO.getGroupList(selectedGroup);
        model.addAttribute("userList", ldapUserList);
        model.addAttribute("userGroupList", userGroupList);
        model.addAttribute("selectedGroup", selectedGroup);
        return "admin/ldap_user";
    }

    @RequestMapping(value = "/user/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String userAdd(
            Model model,
            @RequestParam(required = false) String loginId
    ) {
        LOGGER.info("Login Id: " + loginId);
        List<LDAPUser> ldapUserList = new ArrayList<LDAPUser>();

        if (loginId != null) {
            //Start Retrieve LDAP Users
            Hashtable h = new Hashtable();
            h.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            h.put(Context.PROVIDER_URL, env.getProperty("ldap.url"));

            DirContext ctx = null;
            NamingEnumeration results = null;

            try {
                ctx = new InitialDirContext(h);
                SearchControls controls = new SearchControls();
                controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
                String[] attrIDs = {"givenname", "sn", "title", "cn", "mail", "oncid"};
                controls.setReturningAttributes(attrIDs);
                //Local
//                results = ctx.search("ou=Users", "(cn=" + loginId + ")", controls);
                //Onsemi
//                results = ctx.search("ou=Seremban,ou=ONSemi", "(cn=" + loginId + ")", controls);
                results = ctx.search("ou=ONSemi", "(cn=" + loginId + ")", controls);

                while (results.hasMore()) {
                    SearchResult searchResult = (SearchResult) results.next();
                    Attributes attributes = searchResult.getAttributes();

                    LDAPUser user = new LDAPUser();

                    Enumeration e = attributes.getIDs();
                    while (e.hasMoreElements()) {
                        String key = (String) e.nextElement();
                        if (key.equalsIgnoreCase("givenName")) {
                            user.setFirstname(attributes.get(key).get().toString());
                        }
                        if (key.equalsIgnoreCase("sn")) {
                            user.setLastname(attributes.get(key).get().toString());
                        }
                        if (key.equalsIgnoreCase("title")) {
                            user.setTitle(attributes.get(key).get().toString());
                        }
                        if (key.equalsIgnoreCase("cn")) {
                            user.setLoginId(attributes.get(key).get().toString());
                        }
                        if (key.equalsIgnoreCase("mail")) {
                            user.setEmail(attributes.get(key).get().toString());
                        }
                        if (key.equalsIgnoreCase("oncid")) {
                            user.setOncid(attributes.get(key).get().toString());
                        }
                    }

                    ldapUserList.add(user);
                }
            } catch (NamingException e) {
                LOGGER.error(e.getMessage());
            } finally {
                if (results != null) {
                    try {
                        results.close();
                    } catch (Exception e) {
                        LOGGER.error(e.getMessage());
                    }
                }
                if (ctx != null) {
                    try {
                        ctx.close();
                    } catch (Exception e) {
                        LOGGER.error(e.getMessage());
                    }
                }
            }
            //End Retrieve LDAP Users
        }

        UserGroupDAO userGroupDAO = new UserGroupDAO();
        List<UserGroup> userGroupList = userGroupDAO.getGroupList("");
        model.addAttribute("userGroupList", userGroupList);
        model.addAttribute("userList", ldapUserList);
        return "admin/ldap_user_add";
    }

    @RequestMapping(value = "/user/loginid/{loginId}", method = RequestMethod.GET)
    public @ResponseBody
    JSONResponse userLoginId(
            @ModelAttribute UserSession userSession,
            HttpServletRequest request,
            @PathVariable("loginId") String loginId
    ) {
        JSONResponse response = new JSONResponse();
        LDAPUserDAO ldapUserDAO = new LDAPUserDAO();
        LDAPUser ldapUser = ldapUserDAO.getByLoginId(loginId);
        if (ldapUser.getFirstname() == null) {
            response.setStatus(Boolean.FALSE);
            response.setStatusMessage("User not registered!");
            response.setResult(ldapUser);
        } else {
            response.setStatus(Boolean.TRUE);
            response.setStatusMessage("User already registered!");
            response.setResult(ldapUser);
        }
        return response;
    }

    @RequestMapping(value = "/user/ldap/save", method = RequestMethod.POST)
    public @ResponseBody
    JSONResponse userLDAPSave(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String loginId,
            @RequestParam(required = false) String firstname,
            @RequestParam(required = false) String lastname,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String oncid,
            @RequestParam(required = false) String groupId
    ) {

        JSONResponse response = new JSONResponse();
        LDAPUser ldapUser = new LDAPUser();
        ldapUser.setLoginId(loginId);
        ldapUser.setOncid(oncid);
        ldapUser.setFirstname(firstname);
        ldapUser.setLastname(lastname);
        ldapUser.setEmail(email);
        ldapUser.setTitle(title);
        ldapUser.setGroupId(groupId);
        ldapUser.setCreatedBy(userSession.getId());
        LDAPUserDAO ldapUserDAO = new LDAPUserDAO();
        QueryResult queryResult = ldapUserDAO.insert(ldapUser);
        if (queryResult.getResult() <= 0) {
            response.setStatus(Boolean.FALSE);
            response.setStatusMessage(queryResult.getErrorMessage());
            response.setResult(ldapUser);
        } else {
            response.setStatus(Boolean.TRUE);
            response.setStatusMessage("User added!");
            response.setResult(ldapUser);
        }
        return response;
    }

    @RequestMapping(value = "/user/sync/{loginId}", method = RequestMethod.GET)
    public @ResponseBody
    JSONResponse userSync(
            @ModelAttribute UserSession userSession,
            HttpServletRequest request,
            @PathVariable("loginId") String loginId
    ) {
        //Start Retrieve LDAP Users
        Hashtable h = new Hashtable();
        h.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        h.put(Context.PROVIDER_URL, env.getProperty("ldap.url"));

        DirContext ctx = null;
        NamingEnumeration results = null;
        LDAPUser ldapUser = new LDAPUser();

        try {
            ctx = new InitialDirContext(h);
            SearchControls controls = new SearchControls();
            controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String[] attrIDs = {"givenname", "sn", "title", "cn", "mail", "oncid"};
            controls.setReturningAttributes(attrIDs);
            //Local
//            results = ctx.search("ou=Users", "(cn=" + loginId + ")", controls);
            //Onsemi
//            results = ctx.search("ou=Seremban,ou=ONSemi", "(cn=" + loginId + ")", controls);
            results = ctx.search("ou=ONSemi", "(cn=" + loginId + ")", controls);

            while (results.hasMore()) {
                SearchResult searchResult = (SearchResult) results.next();
                Attributes attributes = searchResult.getAttributes();

                Enumeration e = attributes.getIDs();
                while (e.hasMoreElements()) {
                    String key = (String) e.nextElement();
                    if (key.equalsIgnoreCase("givenName")) {
                        ldapUser.setFirstname(attributes.get(key).get().toString());
                    }
                    if (key.equalsIgnoreCase("sn")) {
                        ldapUser.setLastname(attributes.get(key).get().toString());
                    }
                    if (key.equalsIgnoreCase("title")) {
                        ldapUser.setTitle(attributes.get(key).get().toString());
                    }
                    if (key.equalsIgnoreCase("cn")) {
                        ldapUser.setLoginId(attributes.get(key).get().toString());
                    }
                    if (key.equalsIgnoreCase("mail")) {
                        ldapUser.setEmail(attributes.get(key).get().toString());
                    }
                    if (key.equalsIgnoreCase("oncid")) {
                        ldapUser.setOncid(attributes.get(key).get().toString());
                    }
                }
            }
        } catch (NamingException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (results != null) {
                try {
                    results.close();
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                }
            }
            if (ctx != null) {
                try {
                    ctx.close();
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        //End Retrieve LDAP Users

        JSONResponse response = new JSONResponse();
        if (ldapUser.getFirstname() == null) {
            response.setStatus(Boolean.FALSE);
            response.setStatusMessage("Unable to retrieve user info from LDAP for " + loginId + "!");
            response.setResult(ldapUser);
        } else {
            LDAPUserDAO ldapUserDAO = new LDAPUserDAO();
            LDAPUser dbLdapUser = ldapUserDAO.getByLoginId(loginId);
            if (dbLdapUser.getFirstname() == null) {
                response.setStatus(Boolean.FALSE);
                response.setStatusMessage("Unable to retrieve user info from Database for " + loginId + "!");
                response.setResult(ldapUser);
            } else {
                LDAPUser updateUser = new LDAPUser();
                updateUser.setId(dbLdapUser.getId());
                updateUser.setOncid(ldapUser.getOncid());
                updateUser.setFirstname(ldapUser.getFirstname());
                updateUser.setLastname(ldapUser.getLastname());
                updateUser.setEmail(ldapUser.getEmail());
                updateUser.setTitle(ldapUser.getTitle());
                updateUser.setGroupId(dbLdapUser.getGroupId());
                updateUser.setModifiedBy(userSession.getId());
                QueryResult queryResult = ldapUserDAO.update(updateUser);
                if (queryResult.getResult() <= 0) {
                    response.setStatus(Boolean.FALSE);
                    response.setStatusMessage(queryResult.getErrorMessage());
                    response.setResult(ldapUser);
                } else {
                    response.setStatus(Boolean.TRUE);
                    response.setStatusMessage("User updated!");
                    response.setResult(updateUser);
                }
            }
        }
        return response;
    }

    @RequestMapping(value = "/user/save", method = {RequestMethod.GET, RequestMethod.POST})
    public String groupSave(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String loginId,
            @RequestParam(required = false) String fullname,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) String groupId
    ) {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByLoginId(loginId);
        if (user == null) {
            user = new User();
            user.setLoginId(loginId);
            user.setFullname(fullname);
            user.setEmail(email);
            user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
            user.setGroupId(groupId);
            user.setCreatedBy(userSession.getId());
            userDAO = new UserDAO();
            QueryResult queryResult = userDAO.insertUser(user);
            if (queryResult.getGeneratedKey().equals("0")) {
                model.addAttribute("error", messageSource.getMessage("admin.label.user.save.error", args, locale));
                model.addAttribute("loginId", loginId);
                model.addAttribute("fullname", fullname);
                model.addAttribute("email", email);
                UserGroupDAO userGroupDAO = new UserGroupDAO();
                List<UserGroup> userGroupList = userGroupDAO.getGroupList(groupId);
                model.addAttribute("userGroupList", userGroupList);
                return "admin/user_add";
            } else {
                redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.user.save.success", args, locale));
                return "redirect:/admin/user/edit/" + queryResult.getGeneratedKey();
            }
        } else {
            args = new String[1];
            args[0] = loginId;
            model.addAttribute("error", messageSource.getMessage("general.label.exist.success", args, locale));
            model.addAttribute("loginId", loginId);
            model.addAttribute("fullname", fullname);
            model.addAttribute("email", email);
            UserGroupDAO userGroupDAO = new UserGroupDAO();
            List<UserGroup> userGroupList = userGroupDAO.getGroupList(groupId);
            model.addAttribute("userGroupList", userGroupList);
            return "admin/user_add";
        }
    }

    @RequestMapping(value = "/user/edit/{userId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String userEdit(
            Model model,
            @PathVariable("userId") String userId
    ) {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUser(userId);
        UserGroupDAO userGroupDAO = new UserGroupDAO();
        List<UserGroup> userGroupList = userGroupDAO.getGroupList(user.getGroupId());
        model.addAttribute("user", user);
        model.addAttribute("userGroupList", userGroupList);
        return "admin/user_edit";
    }

    @RequestMapping(value = "/user/update", method = {RequestMethod.GET, RequestMethod.POST})
    public String userUpdate(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String fullname,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String groupId,
            @RequestParam(required = false) String isActive
    ) {
        User user = new User();
        user.setId(userId);
        user.setFullname(fullname);
        user.setEmail(email);
        user.setIsActive(isActive);
        user.setModifiedBy(userSession.getId());
        user.setGroupId(groupId);

        UserDAO userDAO = new UserDAO();
        QueryResult queryResult = userDAO.updateUser(user);
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.user.update.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.user.update.error", args, locale));
        }
        return "redirect:/admin/user/edit/" + userId;
    }

    @RequestMapping(value = "/user/password", method = {RequestMethod.GET, RequestMethod.POST})
    public String userPassword(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String currentPassword,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) String confirmPassword
    ) {
        UserDAO userDAO = new UserDAO();
        User currentUser = userDAO.getUser(userId);
        if (BCrypt.checkpw(currentPassword, currentUser.getPassword())) {
            User user = new User();
            user.setId(userId);
            user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
            user.setModifiedBy(userSession.getId());
            userDAO = new UserDAO();
            QueryResult queryResult = userDAO.updatePassword(user);
            if (queryResult.getResult() == 1) {
                redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.user.password.success", args, locale));
            } else {
                redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.user.password.error", args, locale));
            }
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.user.current_password.error", args, locale));
        }

        return "redirect:/admin/user/edit/" + userId;
    }

    @RequestMapping(value = "/user/delete/{userId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String userDelete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("userId") String userId
    ) {
        LDAPUserDAO ldapUserDAO = new LDAPUserDAO();
        QueryResult queryResult = ldapUserDAO.delete(userId);
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.user.delete.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.user.delete.error", args, locale));
        }
        return "redirect:/admin/user";
    }

    @RequestMapping(value = "/group", method = RequestMethod.GET)
    public String group(
            Model model
    ) {
        UserGroupDAO userGroupDAO = new UserGroupDAO();
        List<UserGroup> userGroupList = userGroupDAO.getGroupList("");
        model.addAttribute("userGroupList", userGroupList);
        return "admin/group";
    }

    @RequestMapping(value = "/group/menu/{groupId}", method = RequestMethod.GET)
    public String groupMenu(
            Model model,
            @PathVariable("groupId") String groupId
    ) {
        UserGroupAccessDAO userGroupAccessDAO = new UserGroupAccessDAO();
        List<UserGroupAccess> userGroupAccessList = userGroupAccessDAO.getUserGroupAccess(groupId);
        model.addAttribute("userGroupAccessList", userGroupAccessList);
        model.addAttribute("groupId", groupId);
        return "admin/group_menu";
    }

    @RequestMapping(value = "/group/menu/save", method = {RequestMethod.POST})
    public String groupMenuSave(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @RequestParam(required = false) String groupId,
            @RequestParam(required = false) String[] groupAccess
    ) {
        groupAccess = SystemUtil.nullToEmptyString(groupAccess);
        UserGroupAccessDAO removeUserGroupAccessDAO = new UserGroupAccessDAO();
        //should use batch insert for performance
        QueryResult addQueryResult = new QueryResult();
        addQueryResult.setResult(0);
        for (String access : groupAccess) {
            UserGroupAccessDAO addUserGroupAccessDAO = new UserGroupAccessDAO();
            addQueryResult = addUserGroupAccessDAO.addAccess(groupId, access);
        }
        QueryResult remQueryResult = removeUserGroupAccessDAO.removeAccess(groupId, groupAccess);
        int result = addQueryResult.getResult() + remQueryResult.getResult();
        if (result != 0) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.group.access.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.group.access.error", args, locale));
        }
        return "redirect:/admin/group/menu/" + groupId;
    }

    @RequestMapping(value = "/group/add", method = RequestMethod.GET)
    public String groupAdd(Model model) {
        return "admin/group_add";
    }

    @RequestMapping(value = "/group/edit/{groupId}", method = RequestMethod.GET)
    public String group_edit(
            Model model,
            @PathVariable("groupId") String groupId
    ) {
        UserGroupDAO userGroupDAO = new UserGroupDAO();
        UserGroup userGroup = userGroupDAO.getGroup(groupId);
        model.addAttribute("userGroup", userGroup);
        return "admin/group_edit";
    }

    @RequestMapping(value = "/group/save", method = {RequestMethod.GET, RequestMethod.POST})
    public String groupSave(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String groupCode,
            @RequestParam(required = false) String groupName
    ) {
        UserGroup userGroup = new UserGroup();
        userGroup.setCode(groupCode);
        userGroup.setName(groupName);
        userGroup.setCreatedBy(userSession.getId());
        UserGroupDAO userGroupDAO = new UserGroupDAO();
        QueryResult queryResult = userGroupDAO.insertGroup(userGroup);
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.group.save.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.group.save.error", args, locale));
        }
        return "redirect:/admin/group/edit/" + queryResult.getGeneratedKey();
    }

    @RequestMapping(value = "/group/update", method = {RequestMethod.GET, RequestMethod.POST})
    public String groupUpdate(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String groupId,
            @RequestParam(required = false) String groupCode,
            @RequestParam(required = false) String groupName
    ) {
        UserGroup userGroup = new UserGroup();
        userGroup.setId(groupId);
        userGroup.setCode(groupCode);
        userGroup.setName(groupName);
        userGroup.setModifiedBy(userSession.getId());
        UserGroupDAO userGroupDAO = new UserGroupDAO();
        QueryResult queryResult = userGroupDAO.updateGroup(userGroup);
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.group.update.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.group.update.error", args, locale));
        }
        return "redirect:/admin/group/edit/" + groupId;
    }

    @RequestMapping(value = "/group/delete/{groupId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String groupDelete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("groupId") String groupId
    ) {
        UserDAO userDAO = new UserDAO();
        int userCount = userDAO.getCountByGroupId(groupId);
        if (userCount == 0) {
            UserGroupDAO userGroupDAO = new UserGroupDAO();
            QueryResult queryResult = userGroupDAO.deleteGroup(groupId);
            UserGroupAccessDAO removeUserGroupAccessDAO = new UserGroupAccessDAO();
            QueryResult remQueryResult = removeUserGroupAccessDAO.removeAccessByGroupId(groupId);
            int result = queryResult.getResult() + remQueryResult.getResult();
            if (result != 0) {
                redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.group.delete.success", args, locale));
            } else {
                redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.group.delete.error", args, locale));
            }
        } else {
            args = new String[]{Integer.toString(userCount)};
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.group.delete.have_user.error", args, locale));
        }
        return "redirect:/admin/group";
    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public String menu(
            Model model
    ) {
        MenuDAO menuDAO = new MenuDAO();
        List<Menu> parentMenuList = menuDAO.getMenuList("0");
        String tbody = "<tbody>";
        String menuOption = "";
        for (int i = 0; i < parentMenuList.size(); i++) {
            Menu parentMenu = parentMenuList.get(i);
            tbody += "<tr><td>&nbsp;</td><td><i class='fa " + parentMenu.getIcon() + "'></i>&nbsp;" + parentMenu.getName() + "</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>";
            menuOption += "<option value='" + parentMenu.getId() + "'>" + parentMenu.getName() + "</option>";
            List<Menu> childMenuList = menuDAO.getMenuList(parentMenu.getCode());
            if (!childMenuList.isEmpty()) {
                for (int j = 0; j < childMenuList.size(); j++) {
                    Menu childMenu = childMenuList.get(j);
                    tbody += "<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td><i class='fa fa-minus'></i>&nbsp;" + childMenu.getName() + "</td><td>&nbsp;</td></tr>";
                    menuOption += "<option value='" + childMenu.getId() + "'>&nbsp;&nbsp;&nbsp;&nbsp;" + childMenu.getName() + "</option>";
                }
            }
        }
        tbody += "</tbody>";
        model.addAttribute("tbody", tbody);
        model.addAttribute("menuOption", menuOption);
        return "admin/menu";
    }

}
