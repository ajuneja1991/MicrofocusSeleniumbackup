package com.hp.opr.qa.framework.bvdrestws;

import com.hp.opr.qa.framework.BvdRestClient;
import com.hp.opr.qa.framework.common.utils.Misc;
import com.hp.opr.qa.framework.common.utils.ResultStore;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.TestConfig;
import com.typesafe.config.Config;
import org.hornetq.utils.json.JSONArray;
import org.hornetq.utils.json.JSONObject;

import java.util.Map;

/**
 * Created by Eva Sokolyanskaya on 30/09/2016.
 * Support web services for Dashboards
 */
public class BvdDashboardWS extends ABvdWS
{
  private String BVD_USR="";
  private String BVD_PWD="";
  private String URL="";
  private Config cfg;
  private String api_version;
  private String apiversion_rest;

  public BvdDashboardWS(BvdRestClient client)
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

  public Map<String, String> deleteDashboard(String dashboardName) {
    Map<String, String> result;

    try {

      wsClient.delete(URL + apiversion_rest + "dashboard/" + dashboardName);

      result = ResultStore.success(dashboardName + " successfully deleted.");

    } catch (Exception e){
      result = ResultStore.failure(e.getMessage() + Misc.getStacktraceAsString(e));
    }
    return result;
  }

  public Map<String, String> getDashboardData(String dashboardName) {
    Map<String, String> result;

    try {
      result = wsClient.get(URL + apiversion_rest + "dashboard/" + dashboardName);
      Result.rcEquals(result, "0");

    } catch (Exception e){
      result = ResultStore.failure(e.getMessage() + Misc.getStacktraceAsString(e));
    }
    return result;
  }

  public Map<String, String> getAllDashboards() {
    Map<String, String> result;

    try {
      result = wsClient.get(URL + apiversion_rest + "dashboard/?type=templates,dashboards");
      Result.rcEquals(result, "0");

    } catch (Exception e){
      result = ResultStore.failure(e.getMessage() + Misc.getStacktraceAsString(e));
    }
    return result;
  }


  public Map<String, String> updateDashboardData(String dashboardName, String data) {
    Map<String, String> result;

    try {
      result = wsClient.put(URL + apiversion_rest + "dashboard/" + dashboardName, data);
      Result.rcEquals(result, "0");

    } catch (Exception e){
      result = ResultStore.failure(e.getMessage() + Misc.getStacktraceAsString(e));
    }
    return result;
  }

  public Map<String, String> getAssignedCategories(String dashboardName) {
    Map<String, String> result;

    try {
      result = getDashboardData(dashboardName);
      Result.rcEquals(result, "0");

      JSONObject cat;
      String json = result.get("out");
      String toResult = "";

      JSONObject obj = new JSONObject(json).getJSONObject("data");
      JSONArray arr = obj.getJSONArray("category");
      int num = arr.length();

      for (int i=0; i<num; i++)
      {
        cat = (JSONObject)arr.get(i);
        toResult = toResult.concat(cat.get("name").toString()).concat(" ");
      }
      result = ResultStore.success(toResult);

    } catch (Exception e){
      result = ResultStore.failure("Categories not found. " + e.getMessage() + Misc
              .getStacktraceAsString
                      (e));
    }
    return result;
  }
}
