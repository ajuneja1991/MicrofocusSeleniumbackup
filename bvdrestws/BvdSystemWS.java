package com.hp.opr.qa.framework.bvdrestws;

import com.hp.opr.qa.framework.BvdRestClient;
import com.mf.opr.qa.framework.TestConfig;
import com.typesafe.config.Config;
import org.testng.Reporter;

import java.util.Map;

public class BvdSystemWS extends ABvdWS
{
  
  Map<String, String> result;
  String baseURL;
  private String BVD_USR="";
  private String BVD_PWD="";
  private String URL="";
  private Config cfg;
  private String api_version;
  private String apiversion_rest;

  public BvdSystemWS(BvdRestClient client)
  {
    super(client);
    cfg = new TestConfig().getConfig();
    BVD_USR = cfg.getString("application.bvd.user.adminusr");
    BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
    URL=cfg.getString("application.bvd.url.external");
    api_version=cfg.getString("application.bvd.url.api_version");
    apiversion_rest="/rest/"+api_version+"/";
    baseURL = URL;
  }
  
  
  public Map<String, String> purgeChannels()
  {
    String RestAPI = baseURL + apiversion_rest + "channel/purge";
    String json = "{\"error\":false,\"message\":\"dashboard.systemsettings.aging.purging" +
          ".triggered\"}";
    
    Reporter.log(RestAPI + "\n" + json);
    result = wsClient.post(RestAPI, json);
    return result;
  }
  
}
