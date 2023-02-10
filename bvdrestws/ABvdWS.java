package com.hp.opr.qa.framework.bvdrestws;

import com.hp.opr.qa.framework.BvdRestClient;


/**
 * Created by Eva Sokolyanskaya on 30/06/2016.
 * Parent class for BVD web-services
 */
abstract class ABvdWS
{
  BvdRestClient wsClient;
  String baseURL;

  public ABvdWS(BvdRestClient client)
  {
    wsClient = client;
  }

}
