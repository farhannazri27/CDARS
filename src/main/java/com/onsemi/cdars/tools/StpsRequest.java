/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onsemi.cdars.tools;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author fg79cj
 */
public class StpsRequest {
    
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
    
}
