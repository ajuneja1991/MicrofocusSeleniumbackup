package com.hp.opr.qa.framework.bvdrestws;

import com.hp.opr.qa.framework.BvdRestClient;
import com.mf.opr.qa.framework.TestConfig;
import com.mf.opr.qa.framework.utils.JsonDoc;
import com.typesafe.config.Config;
import org.hornetq.utils.json.JSONException;
import org.testng.Reporter;

import javax.ws.rs.core.Form;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.List;
import java.util.Map;

public class BvdDataCollectorWS extends ABvdWS
{
  private String BVD_USR="";
  private String BVD_PWD="";
  private String URL="";
  private String api_version;
  private String apiversion_rest;
  private Config cfg;
  Map<String, String> result;
  String baseURL;

  public BvdDataCollectorWS(BvdRestClient client)
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

  public Map<String, String> createDataCollector(String name, String description, String resultFormat, String tags,
                                               String query)
  {
    String RestAPI = baseURL + apiversion_rest + "dataCollector?format=json";

    // resultFormat: default|groupwidget
    // tags: comma separated list e.g. "one", "two", "three"
    String json = "{\"dataCollector\":{\"active\":true,\"data\":{\"resultFormat\":\"" + resultFormat + "\"," +
      "\"tags\":[" + tags + "],\"dims\":[],\"queryText\":\"" + query + "\",\"description\":\"" + description + "\"," +
      "\"availableColumns\":[],\"sampleQueryResult\":{}},\"name\":\"" + name + "\"}}";

    Reporter.log(RestAPI + "\n" + json);
    result = wsClient.post(RestAPI, json);
    return result;
  }

  public Map<String, String> createDbConnection(String hostname, String port, String user, String password, String
    dbname, String forcetls)
  {
    String RestAPI = baseURL + apiversion_rest + "connection";

    Form form = new Form().param("connection", "{\"host\":\"" + hostname + "\",\"port\":" + port + "," +
      "\"username\":\"" + user + "\",\"password\":\"" + password + "\",\"database\":\"" + dbname+ "\"," +
      "\"forceTLS\":" + forcetls + "}\n");

    result = wsClient.put(RestAPI, form);
    return result;

  }

  public String getDbConnection()
  {
    String RestAPI = baseURL + apiversion_rest + "connection";

    result = wsClient.get(RestAPI);
    return result.get("out");

  }

  public Map<String, String> testDbConnection(String hostname, String port, String user, String password, String
    dbname, String forcetls)
  {
    String RestAPI = baseURL + apiversion_rest + "connection/test";

    Form form = new Form().param("connection", "{\"host\":\"" + hostname + "\",\"port\":" + port + "," +
      "\"username\":\"" + user + "\",\"password\":\"" + password + "\",\"database\":\"" + dbname+ "\"," +
      "\"forceTLS\":" + forcetls + "}\n");

    result = wsClient.post(RestAPI, form);
    return result;

  }

  public Map<String, String> testDbConnection(String hostname, String port, String user, String password, String
    dbname, String forcetls, String certName, String cert)
  {
    String RestAPI = baseURL + apiversion_rest + "connection/test";

    Form form = new Form().param("connection", "{\"host\":\"" + hostname + "\",\"port\":" + port + "," +
      "\"username\":\"" + user + "\",\"password\":\"" + password + "\",\"database\":\"" + dbname+ "\"," +
      "\"forceTLS\":" + forcetls + ",\"certName\":\"" + certName+ "\",\"certificate\":\"" + cert + "\"}\n")
      .param("certificatePath", "**dummySecretValue**1234**");

    result = wsClient.post(RestAPI, form);
    return result;

  }

  public Map<String, String> bvdDataCollectorCreateQuery(String json) throws JSONException,
    UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException
  {
    String RestAPI = baseURL + apiversion_rest + "dataCollector?format=json";
    Reporter.log(RestAPI + "\n" + json);
    result = wsClient.post(RestAPI, json);
    return result;
  }

  public Map<String, String> getQueriesJson()
  {
    return wsClient.get(baseURL + apiversion_rest + "dataCollector?format=json");
  }
  
  public Map<String, String> deleteAllExistQueries() throws UnrecoverableKeyException,
        NoSuchAlgorithmException, KeyStoreException, JSONException, KeyManagementException
  {
    String json = getQueriesJson().get("out");
    JsonDoc jsonDoc = new JsonDoc();
    jsonDoc.load(json);

    List<String> queryNames = jsonDoc.getTypedValue("$.data[*].name", List.class);

    for (String queryName: queryNames){
     /*if (queryName.contains("(")) {
        String [] query = queryName.split(" ");
        queryName = query[0];
      }*/
      result =  bvdDataCollectorDeleteQuery(queryName);
      System.out.println("Found query with name : " + queryName);
    }
    return result;
  }

  public Map<String, String> bvdDataCollectorDeleteQuery(String name) throws JSONException,
    UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException
  {
    System.out.println(name);
    String json = getQueriesJson().get("out");
    JsonDoc doc = new JsonDoc();
//String query=doc.load(json).getJsonNode(name);
    /*String queryId = doc.load(json).getJsonNode("$.data[?(@.name=~/" + name + "/)]._id").get(0)
      .asText();*/
    String queryId=doc.load(json).getJsonNode("$.data[*]._id").get(0).asText();

    String RestAPI = baseURL + apiversion_rest + "dataCollector/" + queryId + "?format=json";
    Reporter.log(RestAPI + "\n" + json);
    result = wsClient.delete(RestAPI);
    return result;
  }

  public Map<String, String> bvdDataCollectorUpdateQuery(String name, String jsonUpdate) throws
    JSONException,
    UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException
  {
    String json = getQueriesJson().get("out");
    JsonDoc doc = new JsonDoc();
    String queryId = doc.load(json).getJsonNode("$.data[?(@.name=~/" + name + "/)]._id").get(0)
      .asText();
    String RestAPI = baseURL + apiversion_rest + "dataCollector/" + queryId + "?format=json";
    Reporter.log(RestAPI + "\n" + json);
    result = wsClient.put(RestAPI, jsonUpdate);
    return result;
  }

}
