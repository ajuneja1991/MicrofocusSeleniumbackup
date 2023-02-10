package com.hp.opr.qa.framework.bvdrestws;

import com.hp.opr.qa.framework.BvdRestClient;
import com.hp.opr.qa.framework.common.utils.Misc;
import com.hp.opr.qa.framework.common.utils.ResultStore;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.TestConfig;
import com.typesafe.config.Config;

import java.util.Map;

/**
 * Created by Eva Sokolyanskaya on 27/09/2016.
 * Support web services for Templates
 */
public class BvdTemplateWS extends ABvdWS
{
  private String BVD_USR="";
  private String BVD_PWD="";
  private String URL="";
  private Config cfg;
  private String api_version;
  private String apiversion_rest;

  public BvdTemplateWS(BvdRestClient client)
  {
    super(client);
    cfg = new TestConfig().getConfig();
    BVD_USR = cfg.getString("application.bvd.user.adminusr");
    BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
    api_version=cfg.getString("application.bvd.url.api_version");
    apiversion_rest="/rest/"+api_version+"/";
    URL=cfg.getString("application.bvd.url.external");
  }
  
  public Map<String, String> deleteTemplate(String templateName) {
    Map<String, String> result;
        
    try {
      
      result = wsClient.delete(URL + apiversion_rest + "dashboard/" + templateName);
      
      result = ResultStore.success(templateName + " successfully deleted.");
      Result.rcEquals(result, "0");
      
    } catch (Exception e){
      result = ResultStore.failure(e.getMessage() + Misc.getStacktraceAsString(e));
    }
    return result;
  }
}
