package com.hp.opr.qa.framework.bvdrestws;

import com.hp.opr.qa.framework.BvdRestClient;

/**
 * Created by Eva Sokolyanskaya on 30/06/2016.
 */
public class BvdWsClient {
    private BvdRestClient client;
    public BvdUserWS userWS;
    public BvdReceiverWS receiverWS;
    public BvdDataCollectorWS collectorWS;
    public BvdTenantWS tenantWS;
    public BvdTemplateWS templateWS;
    public BvdDashboardWS dashboardWS;
    public BvdChannelWS channel;
    public BvdSystemWS systemWS;

    public BvdWsClient(String username, String password, String proxyHost, int proxyPort) {
        client = new BvdRestClient(username, password, proxyHost, proxyPort);
        client.setMediaTypeJson();
        initializeBvdResources();
    }
    
    public BvdWsClient(String username, String password) {
        client = new BvdRestClient(username, password);
        client.setMediaTypeJson();
        initializeBvdResources();
    }

    public void initializeBvdResources() {

        userWS = new BvdUserWS(client);
        receiverWS = new BvdReceiverWS(client);
        tenantWS = new BvdTenantWS(client);
        templateWS = new BvdTemplateWS(client);
        dashboardWS = new BvdDashboardWS(client);
        channel = new BvdChannelWS(client);
        collectorWS = new BvdDataCollectorWS(client);
        systemWS = new BvdSystemWS(client);
    }
    
    public BvdRestClient getClient(){
        return client;
    }

}
