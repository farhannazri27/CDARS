/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onsemi.cdars.controller;

import com.onsemi.cdars.dao.HardwareRequestDAO;
import com.onsemi.cdars.dao.WhRequestDAO;
import com.onsemi.cdars.model.HardwareRequest;
import com.onsemi.cdars.model.WhRequest;
import java.util.List;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 *
 * @author fg79cj
 */
@Controller
@RequestMapping(value = "/relLab/shipping")
@SessionAttributes({"userSession"})
public class ShippingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HardwareRequestController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String hardwareRequest(
            Model model
    ) {
        WhRequestDAO whRequestDAO = new WhRequestDAO();
        List<WhRequest> whRequestList = whRequestDAO.getWhRequestListForShipping();
        model.addAttribute("whRequestList", whRequestList);
        return "shipping/shipping";
    }

}
