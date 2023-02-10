package com.hp.opr.qa.framework.bvdrestws;

import com.hp.opr.qa.framework.BvdRestClient;
import com.mf.opr.qa.framework.TestConfig;
import com.typesafe.config.Config;

import java.util.Map;

/**
 * Created by KaWa on 7/25/2016.
 */
public class BvdTenantWS extends ABvdWS
{
    Map<String, String> result;
    String baseURL;
    private String BVD_USR="";
    private String BVD_PWD="";
    private String URL="";
    private String api_version;
    private String apiversion_rest;
    private Config cfg;

    public BvdTenantWS(BvdRestClient client)
    {
        super(client);
        cfg = new TestConfig().getConfig();
        BVD_USR = cfg.getString("application.bvd.user.adminusr");
        BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
        URL=cfg.getString("application.bvd.url.external");
        api_version=cfg.getString("application.bvd.url.api_version");
        apiversion_rest="/rest/"+api_version+"/";
        baseURL = URL + apiversion_rest + "tenant";
    }

    public Map<String, String> getNewApiKey ()
    {
        String url = baseURL + "/apikey";
        result = wsClient.post(url);
        return result;
    }

}
