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

/**
 *
 * @author fg79cj
 */
public class SptsClass {

    //status has 2 type = status(movement) and itemstatus
    //check status(movement) = 1 : good
    public List<LinkedHashMap<String, String>> getSptsItemByParam(
            String itemType,
            String itemStatus,
            String status
    ) throws IOException {
        JSONObject params = new JSONObject();
//        params.put("itemName", "SO8FL 15032A Stencil");
        params.put("itemType", itemType);
        params.put("itemStatus", itemStatus);
        params.put("status", status);
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
        return itemList;
    }

    public List<LinkedHashMap<String, String>> getSptsItemByParamForPcb(
            String itemType,
            String itemStatus,
            String status,
            String itemID
    ) throws IOException {
        JSONObject params = new JSONObject();
//        params.put("itemName", "SO8FL 15032A Stencil");
        params.put("itemType", itemType);
        params.put("itemStatus", itemStatus);
        params.put("status", status);
        params.put("itemID", itemID);
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
        return itemList;
    }

}
