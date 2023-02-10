package com.hp.opr.qa.framework.bvdrestws;

import com.hp.opr.qa.framework.BvdRestClient;
import com.mf.opr.qa.framework.TestConfig;
import com.typesafe.config.Config;
import org.hornetq.utils.json.JSONArray;
import org.hornetq.utils.json.JSONException;
import org.hornetq.utils.json.JSONObject;
import org.testng.Reporter;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Map;

/**
 * Created by KaWa on 5/24/2015.
 */
public class BvdReceiverWS extends ABvdWS
{
    Map<String, String> result;
    String baseURL;
    private String BVD_USR="";
    private String BVD_PWD="";
    private String baseReceiverURL="";
    private Config cfg;

    public BvdReceiverWS(BvdRestClient client)
    {
        super(client);
        cfg = new TestConfig().getConfig();
        BVD_USR = cfg.getString("application.bvd.user.adminusr");
        BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
        baseReceiverURL=cfg.getString("application.bvd.url.externalReceiverUrl");
        baseURL = baseReceiverURL;
    }

    public Map<String, String> bvdSendData (String key, String dims, String tags, String json) throws JSONException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException
    {
        if (tags != null && !tags.isEmpty()) tags = "/tags/" + tags; else tags="";
        String RestAPI = baseURL + "/api/submit/" + key + "/dims/" + dims + tags;
        Reporter.log(RestAPI + "\n" + json);
        JSONObject obj = new JSONObject(json);
        result = wsClient.post(RestAPI, obj.toString());
        return result;
    }
  
  public Map<String, String> bvdSendDataTagsOnly (String key, String tags, String json) throws
        JSONException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException
  {
    if (tags != null && !tags.isEmpty()) tags = "/tags/" + tags; else tags="";
    String RestAPI = baseURL + "/api/submit/" + key + tags;
    Reporter.log(RestAPI + "\n" + json);
    JSONObject obj = new JSONObject(json);
    result = wsClient.post(RestAPI, obj.toString());
    return result;
  }
    
    public Map<String, String> bvdSendData (String key, String dims, String json) throws JSONException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException
    {
        result = bvdSendData(key, dims, "", json);
        return result;
    }


    public Map<String, String> bvdSendDataArray (String key, String dims, String tags, String json) throws JSONException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException
    {
        String RestAPI = baseURL + "/api/submit/" + key + "/dims/" + dims + "/tags/" + tags;
        Reporter.log(RestAPI + "\n" + json);
        JSONArray obj = new JSONArray(json);
        result = wsClient.post(RestAPI, obj.toString());
        return result;
    }
  
  public Map<String, String> bvdSendDataArrayQueryParametersString(String key, String dims, String
        tags, String json, String url) throws JSONException, UnrecoverableKeyException,
        NoSuchAlgorithmException, KeyStoreException, KeyManagementException
  {
    Reporter.log(url + "\n" + json);
    JSONArray obj = new JSONArray(json);
    result = wsClient.post(url, obj.toString());
    return result;
  }
  
  public Map<String, String> bvdSendDataArrayQuery(String key, String dims, String
        tags, String json) throws JSONException, UnrecoverableKeyException,
        NoSuchAlgorithmException, KeyStoreException, KeyManagementException{
      if(tags == ""){
        
        return bvdSendDataArrayQueryParametersString(key, dims, tags, json, baseURL +
              "/api/submit/" + key + "?dims=" + dims);
      }
      else {
        
        return bvdSendDataArrayQueryParametersString(key, dims, tags, json, baseURL +
              "/api/submit/" + key
              + "?dims=" + dims + "&tags=" + tags);
      }
  }
  
}
