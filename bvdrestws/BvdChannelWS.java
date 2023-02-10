package com.hp.opr.qa.framework.bvdrestws;

import com.hp.opr.qa.framework.BvdRestClient;
import com.hp.opr.qa.framework.common.utils.Misc;
import com.hp.opr.qa.framework.common.utils.ResultStore;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.TestConfig;
import com.typesafe.config.Config;
import org.hornetq.utils.json.JSONArray;
import org.hornetq.utils.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 3/20/2017.
 */
public class BvdChannelWS extends ABvdWS
{
  private String BVD_USR="";
  private String BVD_PWD="";
  private String URL="";
  private String api_version;
  private String apiversion_rest;
  private Config cfg;
  public BvdChannelWS(BvdRestClient client)
  {
    super(client);
    cfg = new TestConfig().getConfig();
    BVD_USR = cfg.getString("application.bvd.user.adminusr");
    BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
    URL=cfg.getString("application.bvd.url.external");
    api_version=cfg.getString("application.bvd.url.api_version");
    apiversion_rest="/rest/"+api_version+"/";
    baseURL = URL + apiversion_rest + "channel/";
  }

  public Map<String, String> getChannelStats(String channelName) {
    Map<String, String> result;
    channelName = channelName.replaceAll(" ", "%20");
    channelName = channelName.replaceAll("<>", "%3C%3");

    try {
      result = wsClient.get(baseURL + channelName + "/stats");
      Result.rcEquals(result, "0");

    } catch (Exception e){
      result = ResultStore.failure(e.getMessage() + Misc.getStacktraceAsString(e));
    }
    return result;
  }

  public Map<String, String> getChannels() {
    Map<String, String> result;


    try {
      result = wsClient.get(baseURL);
      Result.rcEquals(result, "0");

    } catch (Exception e){
      result = ResultStore.failure(e.getMessage() + Misc.getStacktraceAsString(e));
    }
    return result;
  }

  public Map<String, String> getDataField(String channelName, String dataField) {
    Map<String, String> result;
    channelName = channelName.replaceAll(" ", "%20");
    channelName = channelName.replaceAll("<>", "%3C%3E");

    try {
      result = wsClient.get(baseURL + channelName + "/state");
      Result.rcEquals(result, "0");

      JSONObject dataI;
      String json = result.get("out");
      String toResult = "";

      JSONArray data = new JSONObject(json).getJSONArray("data");
      int num = data.length();

      for (int i=0; i<num; i++)
      {
        dataI = (JSONObject)data.get(i);
        toResult = dataI.getJSONObject("data").get(dataField).toString().trim();
      }
      result = ResultStore.success(toResult);

    } catch (Exception e){
      result = ResultStore.failure("Categories not found. " + e.getMessage() + Misc
        .getStacktraceAsString
          (e));
    }
    return result;
  }
  
  public List<String>
  getRecords(String channelName, int recordsCount)
  {
    Map<String, String> result;
    channelName = channelName.replaceAll(" ", "%20");
    channelName = channelName.replaceAll("<>", "%3C%3E");
    List<String> records = new ArrayList<>();
    
    try {
      result = wsClient.get(baseURL + channelName + "/state/?count=" + recordsCount);
      Result.rcEquals(result, "0");
      
      String json = result.get("out");

      JSONArray data = new JSONObject(json).getJSONArray("data");
      int num = data.length();
       
      for (int i=0; i<num; i++) records.add(data.getString(i));
        
    } catch (Exception e){
      Result.fail(Misc.getStacktraceAsString(e));
    }
    return records;
  }
  
}

