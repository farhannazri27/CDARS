package com.onsemi.cdars.tools;

import static com.google.common.io.CharStreams.copy;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

public class SPTSWebService_bk {

    // private static final String SPTS_WEB_SERVICE_URL = "http://sptstest.jorfei.com/SPTSServices/SPTSServices.asmx";
    private static final String SPTS_WEB_SERVICE_URL = "http://mysed-rel-spv2/SPTSServices/SPTSServices.asmx";
    private static final String SPTS_ACTION_GETITEMALL = "http://tempuri.org/GetItemAll";
    private static final String SPTS_ACTION_GETITEMBYPKID = "http://tempuri.org/GetItemByPKID";
    private static final String SPTS_ACTION_GETITEMBYPARAM = "http://tempuri.org/GetItemByParam";
    private static final String SPTS_ACTION_INSERTITEM = "http://tempuri.org/InsertItem";
    private static final String SPTS_ACTION_UPDATEITEM = "http://tempuri.org/UpdateItem";
    private static final String SPTS_ACTION_DELETEITEM = "http://tempuri.org/DeleteItem";
    private static final String SPTS_ACTION_INSERTTRANSACTION = "http://tempuri.org/InsertTransaction";
    private static final String SPTS_ACTION_INSERTSFITEM = "http://tempuri.org/InsertSFItem";
    private static final String SPTS_ACTION_UPDATESFITEMLOCATION = "http://tempuri.org/UpdateSFItemLocation";
    private static final String SPTS_ACTION_DELETESFITEM = "http://tempuri.org/DeleteSFItem";
    private static final String SPTS_ACTION_INSERTACTIVITYLOG = "http://tempuri.org/InsertActivityLog";
    private static final String SPTS_ACTION_GETSFITEMBYPARAM = "http://tempuri.org/GetSFItemByParam";

    public static JSONArray getItemAll() throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getItemAll(), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_GETITEMALL);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("GetItemAllResponse");
            JSONObject getAllItemResult = getAllItemResponse.getJSONObject("GetItemAllResult");
            JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
            JSONObject itemDS = resultContent.getJSONObject("ItemDS");
            JSONArray jsonArray = itemDS.optJSONArray("ITEMS");
            if (jsonArray == null) {
                JSONObject jo = itemDS.getJSONObject("ITEMS");
                JSONArray ja = new JSONArray();
                ja.put(jo);
                items = ja;
            } else {
                items = jsonArray;
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }

    public static JSONObject getItemByPKID(String pkID) throws IOException {
        JSONObject item = new JSONObject();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getItemByPKID(pkID), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_GETITEMBYPKID);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getItemByPKIDResponse = soapBody.getJSONObject("GetItemByPKIDResponse");
            JSONObject getItemByPKIDResult = getItemByPKIDResponse.getJSONObject("GetItemByPKIDResult");
            JSONObject resultContent = getItemByPKIDResult.getJSONObject("diffgr:diffgram");
            JSONObject itemDS = resultContent.getJSONObject("ItemDS");
            item = itemDS.getJSONObject("ITEMS");
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return item;
    }

    public static JSONArray getItemByParam(JSONObject params) throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getItemByParam(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_GETITEMBYPARAM);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("GetItemByParamResponse");
            JSONObject getAllItemResult = getAllItemResponse.getJSONObject("GetItemByParamResult");
            JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
            JSONObject itemDS = resultContent.getJSONObject("ItemDS");
            JSONArray jsonArray = itemDS.optJSONArray("ITEMS");
            if (jsonArray == null) {
                JSONObject jo = itemDS.getJSONObject("ITEMS");
                JSONArray ja = new JSONArray();
                ja.put(jo);
                items = ja;
            } else {
                items = jsonArray;
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }

    public static Integer insertItem(JSONObject params) throws IOException {
        Integer pkID = 0;
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.insertItem(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_INSERTITEM);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("InsertItemResponse");
            pkID = getAllItemResponse.getInt("InsertItemResult");
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return pkID;
    }

    public static Boolean updateItem(JSONObject params) throws IOException {
        Boolean updateResult = false;
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.updateItem(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_UPDATEITEM);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("UpdateItemResponse");
            updateResult = getAllItemResponse.getBoolean("UpdateItemResult");
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return updateResult;
    }

    public static Boolean deleteItem(JSONObject params) throws IOException {
        Boolean updateResult = false;
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.deleteItem(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_DELETEITEM);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("DeleteItemResponse");
            updateResult = getAllItemResponse.getBoolean("DeleteItemResult");
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return updateResult;
    }

    private static void errorResponse(int result, String errorResponse) {
        if (result == 500) {
            System.out.println("SPTSWebService Status: " + result);
            System.out.println("SPTSWebService Response: " + errorResponse);
            JSONObject jsonObject = XML.toJSONObject(errorResponse);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject soapFault = soapBody.getJSONObject("soap:Fault");
            String faultCode = soapFault.getString("faultcode");
            String faultString = soapFault.getString("faultstring");
            System.out.println("faultCode: " + faultCode);
            System.out.println("faultString: " + faultString);
            JSONObject faultDetail = soapFault.getJSONObject("detail");
            if (!faultDetail.toString().equals("")) {
                String faultDetailMessage = faultDetail.getString("message");
                String faultDetailDescription = faultDetail.getString("description");
                System.out.println("faultDetailMessage: " + faultDetailMessage);
                System.out.println("faultDetailDescription: " + faultDetailDescription);
            }
        } else if (result == 400) {
            System.out.println("SPTSWebService Status: " + result);
            System.out.println("SPTSWebService Response: " + errorResponse);
        }
    }
    
    public static Integer insertTransaction(JSONObject params) throws IOException {
        Integer pkID = 0;
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.insertTransaction(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_INSERTTRANSACTION);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("InsertTransactionResponse");
            pkID = getAllItemResponse.getInt("InsertTransactionResult");
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return pkID;
    }
    
    public static JSONArray getSFItemByParam(JSONObject params) throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getSFItemByParam(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_GETSFITEMBYPARAM);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("GetSFItemByParamResponse");
            JSONObject getAllItemResult = getAllItemResponse.getJSONObject("GetSFItemByParamResult");
            JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
            JSONObject itemDS = resultContent.getJSONObject("ItemDS");
            JSONArray jsonArray = itemDS.optJSONArray("ITEMS");
            if (jsonArray == null) {
                JSONObject jo = itemDS.getJSONObject("ITEMS");
                JSONArray ja = new JSONArray();
                ja.put(jo);
                items = ja;
            } else {
                items = jsonArray;
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }
    
    public static Integer insertSFItem(JSONObject params) throws IOException {
        Integer pkID = 0;
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.insertSFItem(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_INSERTSFITEM);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("InsertSFItemResponse");
            pkID = getAllItemResponse.getInt("InsertSFItemResult");
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return pkID;
    }
    
     public static Boolean updateSFItemLocation(JSONObject params) throws IOException {
        Boolean updateResult = false;
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.updateSFItemLocation(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_UPDATESFITEMLOCATION);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("UpdateSFItemLocationResponse");
            updateResult = getAllItemResponse.getBoolean("UpdateSFItemLocationResult");
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return updateResult;
    }
     
      public static Boolean DeleteSFItem(JSONObject params) throws IOException {
        Boolean updateResult = false;
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.deleteSFItem(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_DELETESFITEM);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("DeleteSFItemResponse");
            updateResult = getAllItemResponse.getBoolean("DeleteSFItemResult");
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return updateResult;
    }
      
      public static Integer insertActivityLog(JSONObject params) throws IOException {
        Integer pkID = 0;
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.insertActivityLog(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_INSERTACTIVITYLOG);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("InsertActivityLogResponse");
            pkID = getAllItemResponse.getInt("InsertActivityLogResult");
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return pkID;
    }
}
