package com.onsemi.cdars.controller;

import com.onsemi.cdars.dao.WhShippingDAO;
import java.util.Locale;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.onsemi.cdars.model.UserSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes("userSession")
public class HomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(
            Model model,
            HttpServletRequest request,
            @RequestParam(required = false) String selectedProgram
    ) {
        HttpSession currentSession = request.getSession();
        UserSession userSession = (UserSession) currentSession.getAttribute("userSession");
        WhShippingDAO shipD = new WhShippingDAO();
        int countMpShip = shipD.getCountNoMpNumFlag0();
        shipD = new WhShippingDAO();
        int countBsShip = shipD.getCountBSFlag0();
        shipD = new WhShippingDAO();
        int countTtShip = shipD.getCountTtFlag0();
        model.addAttribute("countMpShip", countMpShip);
        model.addAttribute("countBsShip", countBsShip);
        model.addAttribute("countTtShip", countTtShip);
        if (userSession != null) {
            //Anything for Dashboard
            return "home/index";
        } else {
            return "home/index";
        }
    }

//    @RequestMapping(value = "/error", method = RequestMethod.GET)
//    public String loginError(RedirectAttributes redirectAttrs, Locale locale) {
//        redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.login.error", args, locale));
//        return "redirect:/";
//    }
    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String loginError(
            HttpServletRequest request,
            RedirectAttributes redirectAttrs,
            Locale locale) {
        LOGGER.info("Login LDAP Error!");
        LOGGER.info(request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION").toString());
        redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.login.error", args, locale));
        return "redirect:/";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(RedirectAttributes redirectAttrs, Locale locale) {
        redirectAttrs.addFlashAttribute("logout", messageSource.getMessage("general.label.logout", args, locale));
        return "redirect:/";
    }

    @RequestMapping(value = "/register", method = {RequestMethod.GET, RequestMethod.POST})
    public String register(Model model, HttpServletRequest request) {
        return "home/register";
    }
}
