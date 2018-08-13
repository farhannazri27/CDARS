package com.onsemi.cdars.tools;

import static com.google.common.io.CharStreams.copy;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

public class SPTSWebService {

//    private static final String SPTS_WEB_SERVICE_URL = "http://sptstest.jorfei.com/SPTSServices/SPTSServices.asmx";
    private static final String SPTS_WEB_SERVICE_URL = "http://mysed-rel-app04/SPTSServices/SPTSServices.asmx";
//    private static final String SPTS_WEB_SERVICE_URL = "http://mysed-rel-app04/SPTSServicesSF/SPTSServices.asmx";
//    private static final String SPTS_WEB_SERVICE_URL = "http://MYSED-REL-SPV2.ad.onsemi.com/SPTSServicesSF/SPTSServices.asmx";
    private static final String SPTS_ACTION_GETITEMALL = "http://tempuri.org/GetItemAll";
    private static final String SPTS_ACTION_GETITEMBYPKID = "http://tempuri.org/GetItemByPKID";
    private static final String SPTS_ACTION_GETITEMBYPARAM = "http://tempuri.org/GetItemByParam";
    private static final String SPTS_ACTION_GETITEMBYPARAM2 = "http://tempuri.org/GetItemByParam2";
    private static final String SPTS_ACTION_GETITEMWITHSFBYPARAM = "http://tempuri.org/GetItemWithSFByParam";
    private static final String SPTS_ACTION_INSERTITEM = "http://tempuri.org/InsertItem";
    private static final String SPTS_ACTION_UPDATEITEM = "http://tempuri.org/UpdateItem";
    private static final String SPTS_ACTION_DELETEITEM = "http://tempuri.org/DeleteItem";
    //EXTRA
    private static final String SPTS_ACTION_GETRACKALL = "http://tempuri.org/GetRackAll";
    private static final String SPTS_ACTION_GETITEMTYPEALL = "http://tempuri.org/GetItemTypeAll";
    private static final String SPTS_ACTION_GETCARDTYPEALL = "http://tempuri.org/GetCardTypeAll";

    private static final String SPTS_ACTION_INSERTTRANSACTION = "http://tempuri.org/InsertTransaction";
    private static final String SPTS_ACTION_INSERTSFITEM = "http://tempuri.org/InsertSFItem";
    private static final String SPTS_ACTION_UPDATESFITEMLOCATION = "http://tempuri.org/UpdateSFItemLocation";
    private static final String SPTS_ACTION_DELETESFITEM = "http://tempuri.org/DeleteSFItem";
    private static final String SPTS_ACTION_INSERTACTIVITYLOG = "http://tempuri.org/InsertActivityLog";
    private static final String SPTS_ACTION_GETSFITEMBYPARAM = "http://tempuri.org/GetSFItemByParam";
    private static final String SPTS_ACTION_GETTRANSACTIONBYPARAM = "http://tempuri.org/GetTransactionByParam";
    private static final String SPTS_ACTION_DELETETRANSACTION = "http://tempuri.org/DeleteTransaction";

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
            try {
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
            } catch (Exception e) {
                //Ignore
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }

    public static JSONArray getItemByParam2(JSONObject params) throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getItemByParam2(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_GETITEMBYPARAM2);
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
            JSONObject getAllItemResponse = soapBody.getJSONObject("GetItemByParam2Response");
            try {
                JSONObject getAllItemResult = getAllItemResponse.getJSONObject("GetItemByParam2Result");
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
            } catch (Exception e) {
                //Ignore
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }

    public static JSONArray getItemWithSfByParam(JSONObject params) throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getItemWithSfByParam(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_GETITEMWITHSFBYPARAM);
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
            JSONObject getAllItemResponse = soapBody.getJSONObject("GetItemWithSFByParamResponse");
            try {
                JSONObject getAllItemResult = getAllItemResponse.getJSONObject("GetItemWithSFByParamResult");
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
            } catch (Exception e) {
                //Ignore
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }

    public static SPTSResponse insertItem(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
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
            sr.setStatus(Boolean.TRUE);
            sr.setResponseCode(result);
            sr.setResponseId(pkID);
            sr.setErrorCode("");
            sr.setErrorMessage("");
            sr.setErrorDetail("");
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(pkID);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }

    public static SPTSResponse updateItem(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
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
            Boolean updateResult = getAllItemResponse.getBoolean("UpdateItemResult");
            if (updateResult) {
                sr.setStatus(Boolean.TRUE);
                sr.setResponseCode(result);
                sr.setResponseId(0);
                sr.setErrorCode("");
                sr.setErrorMessage("");
                sr.setErrorDetail("");
            } else {
                sr.setStatus(Boolean.FALSE);
                sr.setResponseCode(result);
                sr.setResponseId(0);
                sr.setErrorCode("200");
                sr.setErrorMessage("Update failed!");
                sr.setErrorDetail("");
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(0);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }

    public static SPTSResponse deleteItem(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
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
            Boolean deleteResult = getAllItemResponse.getBoolean("DeleteItemResult");
            if (deleteResult) {
                sr.setStatus(Boolean.TRUE);
                sr.setResponseCode(result);
                sr.setResponseId(0);
                sr.setErrorCode("");
                sr.setErrorMessage("");
                sr.setErrorDetail("");
            } else {
                sr.setStatus(Boolean.FALSE);
                sr.setResponseCode(result);
                sr.setResponseId(0);
                sr.setErrorCode("200");
                sr.setErrorMessage("Update failed!");
                sr.setErrorDetail("");
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(0);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }

    private static HashMap errorResponse(int result, String errorResponse) {
        HashMap error = new HashMap();
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
            error.put("errorCode", faultCode);
            error.put("errorMessage", faultString);
            try {
                JSONObject faultDetail = soapFault.getJSONObject("detail");
                if (!faultDetail.toString().equals("")) {
                    String faultDetailMessage = faultDetail.getString("message");
                    String faultDetailDescription = faultDetail.getString("description");
                    System.out.println("faultDetailMessage: " + faultDetailMessage);
                    System.out.println("faultDetailDescription: " + faultDetailDescription);
                    String errorDetail = faultDetailMessage;
                    if (!faultDetailDescription.equals("") && !faultDetailDescription.equals(faultDetailMessage)) {
                        errorDetail = faultDetailMessage + " - " + faultDetailDescription;
                    }
                    error.put("errorDetail", errorDetail);
                }
            } catch (Exception e) {
                error.put("errorDetail", "");
            }
        } else if (result == 400) {
            System.out.println("SPTSWebService Status: " + result);
            System.out.println("SPTSWebService Response: " + errorResponse);
            error.put("errorCode", Integer.toString(result));
            error.put("errorMessage", errorResponse);
            error.put("errorDetail", "");
        }
        return error;
    }

    //EXTRA
    public static JSONArray getRackAll() throws IOException {
        JSONArray racks = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getRackAll(), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_GETRACKALL);
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
            JSONObject getAllItemResponse = soapBody.getJSONObject("GetRackAllResponse");
            JSONObject getAllItemResult = getAllItemResponse.getJSONObject("GetRackAllResult");
            JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
            //System.out.println(resultContent.toString());
            JSONObject itemDS = resultContent.getJSONObject("RackDS");
            JSONArray jsonArray = itemDS.optJSONArray("RACKS");
            if (jsonArray == null) {
                JSONObject jo = itemDS.getJSONObject("RACKS");
                JSONArray ja = new JSONArray();
                ja.put(jo);
                racks = ja;
            } else {
                racks = jsonArray;
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return racks;
    }

    public static JSONArray getItemTypeAll() throws IOException {
        JSONArray racks = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getRackAll(), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_GETITEMTYPEALL);
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
            JSONObject getAllItemResponse = soapBody.getJSONObject("GetItemTypeAllResponse");
            JSONObject getAllItemResult = getAllItemResponse.getJSONObject("GetItemTypeAllResult");
            JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
            //System.out.println(resultContent.toString());
            JSONObject itemDS = resultContent.getJSONObject("ItemDS");
            JSONArray jsonArray = itemDS.optJSONArray("ITEMS");
            if (jsonArray == null) {
                JSONObject jo = itemDS.getJSONObject("ITEMS");
                JSONArray ja = new JSONArray();
                ja.put(jo);
                racks = ja;
            } else {
                racks = jsonArray;
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return racks;
    }

    public static JSONArray getCardTypeAll() throws IOException {
        JSONArray racks = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getRackAll(), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_GETCARDTYPEALL);
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
            JSONObject getAllItemResponse = soapBody.getJSONObject("GetCardTypeAllResponse");
            JSONObject getAllItemResult = getAllItemResponse.getJSONObject("GetCardTypeAllResult");
            JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
            //System.out.println(resultContent.toString());
            JSONObject itemDS = resultContent.getJSONObject("ItemDS");
            JSONArray jsonArray = itemDS.optJSONArray("ITEMS");
            if (jsonArray == null) {
                JSONObject jo = itemDS.getJSONObject("ITEMS");
                JSONArray ja = new JSONArray();
                ja.put(jo);
                racks = ja;
            } else {
                racks = jsonArray;
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return racks;
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
            try {
                JSONObject getAllItemResult = getAllItemResponse.getJSONObject("GetSFItemByParamResult");
                JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
//                 System.out.println(resultContent.toString());
                JSONObject itemDS = resultContent.getJSONObject("SFItemDS");
                JSONArray jsonArray = itemDS.optJSONArray("SFITEMS");
                if (jsonArray == null) {
                    JSONObject jo = itemDS.getJSONObject("SFITEMS");
                    JSONArray ja = new JSONArray();
                    ja.put(jo);
                    items = ja;
                } else {
                    items = jsonArray;
                }
            } catch (Exception e) {
                //Ignore
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }

    public static SPTSResponse insertTransaction(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
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
            sr.setStatus(Boolean.TRUE);
            sr.setResponseCode(result);
            sr.setResponseId(pkID);
            sr.setErrorCode("");
            sr.setErrorMessage("");
            sr.setErrorDetail("");
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(pkID);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }

    public static SPTSResponse insertSFItem(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
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
            sr.setStatus(Boolean.TRUE);
            sr.setResponseCode(result);
            sr.setResponseId(pkID);
            sr.setErrorCode("");
            sr.setErrorMessage("");
            sr.setErrorDetail("");
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(pkID);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }

    public static SPTSResponse updateSFItemLocation(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
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
            Boolean updateResult = getAllItemResponse.getBoolean("UpdateSFItemLocationResult");
            if (updateResult) {
                sr.setStatus(Boolean.TRUE);
                sr.setResponseCode(result);
                sr.setResponseId(0);
                sr.setErrorCode("");
                sr.setErrorMessage("");
                sr.setErrorDetail("");
            } else {
                sr.setStatus(Boolean.FALSE);
                sr.setResponseCode(result);
                sr.setResponseId(0);
                sr.setErrorCode("200");
                sr.setErrorMessage("Update failed!");
                sr.setErrorDetail("");
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(0);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }

    public static SPTSResponse DeleteSFItem(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
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
            Boolean deleteResult = getAllItemResponse.getBoolean("DeleteSFItemResult");
            if (deleteResult) {
                sr.setStatus(Boolean.TRUE);
                sr.setResponseCode(result);
                sr.setResponseId(0);
                sr.setErrorCode("");
                sr.setErrorMessage("");
                sr.setErrorDetail("");
            } else {
                sr.setStatus(Boolean.FALSE);
                sr.setResponseCode(result);
                sr.setResponseId(0);
                sr.setErrorCode("200");
                sr.setErrorMessage("Update failed!");
                sr.setErrorDetail("");
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(0);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }

    public static SPTSResponse insertActivityLog(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
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
            sr.setStatus(Boolean.TRUE);
            sr.setResponseCode(result);
            sr.setResponseId(pkID);
            sr.setErrorCode("");
            sr.setErrorMessage("");
            sr.setErrorDetail("");
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(pkID);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }

    public static JSONArray getTransactionByParam(JSONObject params) throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getTransactionByParam(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_GETTRANSACTIONBYPARAM);
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
            JSONObject getAllItemResponse = soapBody.getJSONObject("GetTransactionByParamResponse");
            try {
                JSONObject getAllItemResult = getAllItemResponse.getJSONObject("GetTransactionByParamResult");
                JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
//                System.out.println(resultContent.toString());
                JSONObject itemDS = resultContent.getJSONObject("TransactionDS");
                JSONArray jsonArray = itemDS.optJSONArray("TRANSACTIONS");
                if (jsonArray == null) {
                    JSONObject jo = itemDS.getJSONObject("TRANSACTIONS");
                    JSONArray ja = new JSONArray();
                    ja.put(jo);
                    items = ja;
                } else {
                    items = jsonArray;
                }
            } catch (Exception e) {
                //Ignore
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }

    public static SPTSResponse DeleteTransaction(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.deleteTransaction(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_DELETETRANSACTION);
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
            JSONObject getAllItemResponse = soapBody.getJSONObject("DeleteTransactionResponse");
            Boolean deleteResult = getAllItemResponse.getBoolean("DeleteTransactionResult");
            if (deleteResult) {
                sr.setStatus(Boolean.TRUE);
                sr.setResponseCode(result);
                sr.setResponseId(0);
                sr.setErrorCode("");
                sr.setErrorMessage("");
                sr.setErrorDetail("");
            } else {
                sr.setStatus(Boolean.FALSE);
                sr.setResponseCode(result);
                sr.setResponseId(0);
                sr.setErrorCode("200");
                sr.setErrorMessage("Update failed!");
                sr.setErrorDetail("");
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(0);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }
}
