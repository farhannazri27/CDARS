package com.onsemi.cdars.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.cdars.model.JSONResponse;
import com.onsemi.cdars.model.UserSession;
import com.onsemi.cdars.tools.SPTSWebService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping(value = "/spts_bk")
@SessionAttributes({"userSession"})
public class SPTSController_bk {

    private static final Logger LOGGER = LoggerFactory.getLogger(SPTSController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public @ResponseBody
    String main(
            Model model
    ) {
        return "SPTS Controller";
    }

    @RequestMapping(value = "/getitemall", method = RequestMethod.GET)
    public String getItemAll(
            Model model,
            HttpServletRequest request
    ) throws IOException {
        JSONArray getItemAll = SPTSWebService.getItemAll();
        List<LinkedHashMap<String, String>> itemList = new ArrayList();
        for (int i = 0; i < getItemAll.length(); i++) {
            JSONObject jsonObject = getItemAll.getJSONObject(i);
            LinkedHashMap<String, String> item;
            ObjectMapper mapper = new ObjectMapper();
            item = mapper.readValue(jsonObject.toString(), new TypeReference<LinkedHashMap<String, String>>() {
            });
            itemList.add(item);
        }
        model.addAttribute("itemList", itemList);
        return "spts/getitemall";
    }
    
    @RequestMapping(value = "/getitembyparam", method = RequestMethod.GET)
    public String getItemByParam(
            Model model,
            HttpServletRequest request
    ) throws IOException {
        JSONObject params = new JSONObject();
        params.put("itemName", "SO8FL 15032A Stencil");
        params.put("itemType", "Stencil");
        params.put("itemStatus", "0");
        JSONArray getItemByParam = SPTSWebService.getItemByParam(params);
        List<LinkedHashMap<String, String>> itemList = new ArrayList();
        for (int i = 0; i < getItemByParam.length(); i++) {
            JSONObject jsonObject = getItemByParam.getJSONObject(i);
            LinkedHashMap<String, String> item;
            ObjectMapper mapper = new ObjectMapper();
            item = mapper.readValue(jsonObject.toString(), new TypeReference<LinkedHashMap<String, String>>() {
            });
            itemList.add(item);
        }
        model.addAttribute("itemList", itemList);
        return "spts/getitemall";
    }

    @RequestMapping(value = "/json/getitemall", method = RequestMethod.GET)
    public @ResponseBody
    JSONResponse jsonGetItemAll(
            @ModelAttribute UserSession userSession,
            HttpServletRequest request
    ) throws IOException {
        JSONResponse response = new JSONResponse();
        JSONArray getItemAll = SPTSWebService.getItemAll();
        List<LinkedHashMap<String, String>> itemList = new ArrayList();
        for (int i = 0; i < getItemAll.length(); i++) {
            JSONObject jsonObject = getItemAll.getJSONObject(i);
            LinkedHashMap<String, String> item;
            ObjectMapper mapper = new ObjectMapper();
            item = mapper.readValue(jsonObject.toString(), new TypeReference<LinkedHashMap<String, String>>() {
            });
            itemList.add(item);
        }
        response.setResult(itemList);
        response.setStatus(Boolean.TRUE);
        response.setStatusMessage("SUCCESS");
        return response;
    }
    
    @RequestMapping(value = "/json/getitembyparam", method = RequestMethod.GET)
    public @ResponseBody
    JSONResponse jsonGetItemByParam(
            @ModelAttribute UserSession userSession,
            HttpServletRequest request
    ) throws IOException {
        JSONResponse response = new JSONResponse();
        JSONObject params = new JSONObject();
        params.put("itemName", "SO8FL 15032A Stencil");
        params.put("itemType", "Stencil");
        params.put("itemStatus", "0");
        JSONArray getItemByParam = SPTSWebService.getItemByParam(params);
        List<LinkedHashMap<String, String>> itemList = new ArrayList();
        for (int i = 0; i < getItemByParam.length(); i++) {
            JSONObject jsonObject = getItemByParam.getJSONObject(i);
            LinkedHashMap<String, String> item;
            ObjectMapper mapper = new ObjectMapper();
            item = mapper.readValue(jsonObject.toString(), new TypeReference<LinkedHashMap<String, String>>() {
            });
            itemList.add(item);
        }
        response.setResult(itemList);
        response.setStatus(Boolean.TRUE);
        response.setStatusMessage("SUCCESS");
        return response;
    }
}
