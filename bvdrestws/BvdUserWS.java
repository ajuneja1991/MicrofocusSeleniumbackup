package com.hp.opr.qa.framework.bvdrestws;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hp.opr.qa.framework.BvdRestClient;
import com.hp.opr.qa.framework.common.utils.Misc;
import com.hp.opr.qa.framework.common.utils.ResultStore;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.TestConfig;
import com.typesafe.config.Config;
import org.hornetq.utils.json.JSONArray;
import org.hornetq.utils.json.JSONException;
import org.hornetq.utils.json.JSONObject;
import org.testng.Reporter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Eva Sokolyanskaya on 30/06/2016.
 * WebServices for BVD System page
 */
public class BvdUserWS extends ABvdWS
{
  Map<String, String> result;
  private String BVD_USR="";
  private String BVD_PWD="";
  private String URL="";
  private Config cfg;
  private String api_version;
  private String apiversion_rest;
  
  public BvdUserWS(BvdRestClient client)
  {
    super(client);
    cfg = new TestConfig().getConfig();
    BVD_USR = cfg.getString("application.bvd.user.adminusr");
    BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
    URL=cfg.getString("application.bvd.url.external");
    api_version=cfg.getString("application.bvd.url.api_version");
    apiversion_rest="/rest/"+api_version+"/";
    baseURL = URL + apiversion_rest+"session/user";
  }

  public Map<String, String> getApiKey()  {
    String apiKey;
    String key = "apiKey";
    result = wsClient.get(baseURL);
    Result.rcEquals(result, "0");

    if (result.get("rc").equals("0"))
    {
      String jsonFile = result.get("out");
      JsonObject json = new JsonParser().parse(jsonFile).getAsJsonObject();
      apiKey = json.getAsJsonObject("data").getAsJsonObject("userDetails").get(key).
              getAsString();
      return ResultStore.success(apiKey);
    }
    else return result;
  }

  public Map<String, String> getJson()
  {
    return wsClient.get(baseURL);
  }

  public Map<String, String> createUser(String name, String login, String pwd, String email, Boolean superAdmin) {
    Map<String, String> result;

    try {

      String json = "{\"user_object\":{" +
              "\"name\":\"" + name + "\"," +
              "\"login\":\"" + login + "\"," +
              "\"email_address\":\"" + email + "\"," +
              "\"password\":\"" + pwd + "\"," +
              "\"ldap_user\":false," +
              "\"super_administrator\":" + superAdmin + "," +
              "\"inactive\":false," +
              "\"user_group\":[]," +
              "\"user_object_to_role\":[]}}";
      Reporter.log("json: " + json);
      JSONObject obj = new JSONObject(json);
      result = wsClient.post(URL + "/user?format=json", obj.toString());

      JSONObject out = new JSONObject(result.get("out"));
      String id = out.getJSONObject("user_object").get("id").toString();
      result = ResultStore.success(id);
    } catch (Exception e){
      result = ResultStore.failure(e.getMessage() + Misc.getStacktraceAsString(e));
    }
    return result;
  }

  public Map<String, String> createUserBVD(String name, String login, String pwd, Boolean superAdmin) {
    Map<String, String> result;

    try {

      String json = "{\"user_object\":{" +
              "\"name\":\"" + name + "\"," +
              "\"login\":\"" + login + "\"," +
              "\"password\":\"" + pwd + "\"," +
              "\"ldap_user\":false," +
              "\"super_administrator\":" + superAdmin + "," +
              "\"inactive\":false," +
              "\"user_group\":[]," +
              "\"user_object_to_role\":[]}}";
      Reporter.log("json: " + json);
      JSONObject obj = new JSONObject(json);
      result = wsClient.post(URL + "/user?format=json", obj.toString());

      JSONObject out = new JSONObject(result.get("out"));
      String id = out.getJSONObject("user_object").get("id").toString();
      result = ResultStore.success(id);
    } catch (Exception e){
      result = ResultStore.failure(e.getMessage() + Misc.getStacktraceAsString(e));
    }
    return result;
  }

  public Map<String, String> createUserWithGroup(String name, String login, String pwd, String
          email, Boolean superAdmin, String[] groupNames) {
    Map<String, String> result;

    try {
      String groupIDs = "[";
      Map<String, String> incomingResult = getGroups();
      int count = groupNames.length;
      String item;
      for (int i=0; i<count; i++) {
        item = "{\"id\":\"" + getGroupId(incomingResult,groupNames[i]).get("out") + "\"}";
        if (i == count-1) item = item.concat("]");
        else item = item.concat(",");
        groupIDs = groupIDs.concat(item);
      }

      String json = "{\"user_object\":{" +
              "\"name\":\"" + name + "\"," +
              "\"login\":\"" + login + "\"," +
              "\"email_address\":\"" + email + "\"," +
              "\"password\":\"" + pwd + "\"," +
              "\"ldap_user\":false," +
              "\"super_administrator\":" + superAdmin + "," +
              "\"inactive\":false," +
              "\"user_group\":" + groupIDs + "," +
              "\"user_object_to_role\":[]}}";
      Reporter.log("json: " + json);
      JSONObject obj = new JSONObject(json);
      result = wsClient.post(URL + "/user?format=json", obj.toString());

      JSONObject out = new JSONObject(result.get("out"));
      String id = out.getJSONObject("user_object").get("id").toString();
      result = ResultStore.success(id);
    } catch (Exception e){
      result = ResultStore.failure(e.getMessage() + Misc.getStacktraceAsString(e));
    }
    return result;
  }

  public Map<String, String> setSuperAdmin(String userId, Boolean superAdmin) {
    Map<String, String> result;

    try {

      String json = "{\"user_object\":{" +
              "\"id\":\" " + userId + "\"," +
              "\"super_administrator\":" + superAdmin + "}}";
      Reporter.log("json: " + json);
      JSONObject obj = new JSONObject(json);
      result = wsClient.put(URL + "/user/" + userId + "?format=json", obj.toString());
      Result.rcEquals(result, "0");

      result = ResultStore.success("User was set SuperAdmin= " + superAdmin + " successfully.");
    } catch (Exception e){
      result = ResultStore.failure("Failed to set SuperAdmin= " + superAdmin + ". " + e.getMessage()
              + Misc.getStacktraceAsString(e));
    }
    return result;
  }

  public Map<String, String> getUsers(){
    Map<String, String> result;
    //TODO: check if we need this string:
    wsClient.get(URL + apiversion_rest + "session/user");
    result = wsClient.get(URL + "/user?format=json");

    Result.rcEquals(result, "0");
    return result;
  }

  public Map<String, String> getUserId(String userName) {
    Map<String, String> result;

    try {
      String json = getUsers().get("out");
      Map<String, String> users = new HashMap<>();
      JSONObject user;
      String id;
      String name;

      JSONObject obj = new JSONObject(json).getJSONObject("user_object_list");
      JSONArray arr = obj.getJSONArray("user_object");
      int num = arr.length();

      for (int i=0; i<num; i++) {
        user = (JSONObject)arr.get(i);
        id = user.get("id").toString();
        name = user.get("name").toString();
        users.put(name, id);
      }

      result = ResultStore.success(users.get(userName));

    } catch (Exception e){
      result = ResultStore.failure("User not found. " + e.getMessage() + Misc.getStacktraceAsString
              (e));
    }
    return result;
  }

  public String[] getUserIds(String[] userNames) throws JSONException
  {
    String json = getUsers().get("out");
    Map<String, String> users = new HashMap<>();
    JSONObject user;
    String id;
    String name;

    JSONObject obj = new JSONObject(json).getJSONObject("user_object_list");
    JSONArray arr = obj.getJSONArray("user_object");
    int num = arr.length();

    for (int i=0; i<num; i++) {
      user = (JSONObject)arr.get(i);
      id = user.get("id").toString();

      name = user.get("name").toString().trim();
      users.put(name, id);
    }

//    System.out.println("Users map: " + users);

    List<String> ids = new ArrayList<>();
    for (String userName : userNames) {
      ids.add(users.get(userName));
    }

    String[] resultArray = new String[ids.size()];

    return ids.toArray(resultArray);
  }

  public Map<String, String> getUsersMap(String[] userNames)
  {
    String json = getUsers().get("out");
    Map<String, String> users = new HashMap<>();
    JSONObject user;
    String id;
    String name;
    try {

      JSONObject obj = new JSONObject(json).getJSONObject("user_object_list");
      JSONArray arr = obj.getJSONArray("user_object");
      int num = arr.length();

      for (int i=0; i<num; i++)
      {
        user = (JSONObject) arr.get(i);
        id = user.get("id").toString();

        name = user.get("name").toString().trim();
        users.put(name, id);
      }
    } catch (Exception e) {
      System.out.println(e.getStackTrace());
    }

    return users;
  }

  public Map<String, String> getUserMail(String userLogin) {
    Map<String, String> result;

    try {
      String json = getUsers().get("out");
      JSONObject obj = new JSONObject(json).getJSONObject("user_object_list");
      JSONArray arr = obj.getJSONArray("user_object");
      JSONObject user;
      String mail;
      String login;
      Map<String, String> users = new HashMap<>();

      int num = arr.length();

      for (int i=0; i<num; i++) {
        user = (JSONObject)arr.get(i);
        mail = user.get("email_address").toString();
        login = user.get("login").toString();
        users.put(login, mail);
      }

      result = ResultStore.success(users.get(userLogin));

    } catch (Exception e){
      result = ResultStore.failure(e.getMessage() + Misc.getStacktraceAsString(e));
    }
    return result;
  }

  public Map<String, String> deleteUserById(String userId) {
    Map<String, String> result;

    try {
      String json = "{\"user_object_list\":{\"user_object\":[{" +
              "\"id\":\"" + userId + "\"}]}}";
      Reporter.log("json: " + json);
      JSONObject obj = new JSONObject(json);
      result = wsClient.post(URL + "/user?_method=DELETE&format=json&multiMode=true", obj.toString());
    } catch (Exception e){
      result = ResultStore.failure(e.getMessage() + Misc.getStacktraceAsString(e));
    }
    return result;
  }

  public Map<String, String> deleteUsersByNames(String[] userNames) {
    Map<String, String> result;

    try {
      String[] userIds = getUserIds(userNames);
      int num = userIds.length;
      String toJson = "";

      for (int i=0; i<num; i++) {
        toJson = toJson.concat("{\"id\":\"" + userIds[i] + "\"}");
        if (i<num-1) toJson = toJson.concat(",");
      }

      String json = "{\"user_object_list\":{\"user_object\":[" + toJson + "]}}";
      Reporter.log("json: " + json);
      JSONObject obj = new JSONObject(json);
      result = wsClient.post(URL + "/user?_method=DELETE&format=json&multiMode=true", obj.toString());
    } catch (Exception e){
      result = ResultStore.failure(e.getMessage() + Misc.getStacktraceAsString(e));
    }
    return result;
  }

  public Map<String, String> deleteUserByName(String userName) {
    Map<String, String> result;

    try {
      result = deleteUserById((getUserId(userName)).get("out"));

    } catch (Exception e){
      result = ResultStore.failure(e.getMessage() + Misc.getStacktraceAsString(e));
    }
    return result;
  }

  public Map<String, String> getRoleId(String roleName) {
    Map<String, String> result;

    try {
//      wsClient.get(URL + "/rest/session/user");
      result = wsClient.get(URL + apiversion_rest+"role?format=json");
      String json = result.get("out");
      Map<String, String> roles = new HashMap<>();
      JSONObject role;
      String id;
      String name;

      JSONObject obj = new JSONObject(json).getJSONObject("role_list");
      JSONArray arr = obj.getJSONArray("role");
      int num = arr.length();

      for (int i=0; i<num; i++) {
        role = (JSONObject)arr.get(i);
        id = role.get("id").toString();
        name = role.get("name").toString();
        roles.put(name, id);
      }

      result = ResultStore.success(roles.get(roleName));

    } catch (Exception e){
      result = ResultStore.failure("Role not found. " + e.getMessage() + Misc.getStacktraceAsString
              (e));
    }
    return result;
  }

  public Map<String, String> createRole(String roleName, String description, String categoryName,
                                        String accessType) {
    Map<String, String> result;

    String assignment;
    switch (categoryName) {
      case "All": assignment = "";
        break;
      case "assigned": assignment = "<>assigned";
        break;
      case "not-assigned": assignment = "<>not-assigned";
        break;
      default: assignment = "<>assigned<>" + categoryName;
    }

    try {
      String json = "{\"role\":{" +
              "\"name\":\"" + roleName + "\"," +
              "\"description\":\"" + description + "\"," +
              "\"user_object_to_role\":[]," +
              "\"user_group_to_role\":[]," +
              "\"permission\":[{\"operation_key\":\"" + accessType +"\"," +
              "\"resource_key\":\"omi-event" + assignment + "\"}]}}";
      Reporter.log("json: " + json);
      JSONObject obj = new JSONObject(json);
      result = wsClient.post(URL + apiversion_rest+ "role?format=json", obj.toString());
      Result.rcEquals(result, "0");

      JSONObject out = new JSONObject(result.get("out"));
      String id = out.getJSONObject("role").get("id").toString();
      result = ResultStore.success(id);

//      System.out.println("role ID: " + id);
    } catch (Exception e){
      result = ResultStore.failure(e.getMessage() + Misc.getStacktraceAsString(e));
    }
    return result;
  }

  public Map<String, String> createRoleAssignUser(String name, String description, String categoryName,
                                                  String accessType, String assignedUserId) {
    Map<String, String> result;

    String assignment;
    switch (categoryName) {
      case "All": assignment = "";
        break;
      case "assigned": assignment = "<>assigned";
        break;
      case "not-assigned": assignment = "<>not-assigned";
        break;
      default: assignment = "<>assigned<>" + categoryName;
    }

    try {
      String json = "{\"role\":{" +
              "\"name\":\"" + name + "\"," +
              "\"description\":\"" + description + "\"," +
              "\"user_object_to_role\":[{" +
              "\"user_object\":{\"id\":\"" + assignedUserId + "\"}," +
              "\"tenant\":[]}],\"user_group_to_role\":[]," +
              "\"permission\":[{\"operation_key\":\"" + accessType +"\"," +
              "\"resource_key\":\"omi-event" + assignment + "\"}]}}";
      Reporter.log("json: " + json);
      JSONObject obj = new JSONObject(json);
      result = wsClient.post(URL + apiversion_rest+ "role?format=json", obj.toString());
      Result.rcEquals(result, "0");

      JSONObject out = new JSONObject(result.get("out"));
      String id = out.getJSONObject("role").get("id").toString();
      result = ResultStore.success(id);

//      System.out.println("role ID: " + id);
    } catch (Exception e){
      result = ResultStore.failure(e.getMessage() + Misc.getStacktraceAsString(e));
    }
    return result;
  }

  public Map<String, String> deleteRole(String roleId) {
    Map<String, String> result;

    try {
      String json = "{\"role_list\":{\"role\":[{" +
              "\"id\":\"" + roleId + "\"}]}}";
      Reporter.log("json: " + json);
      JSONObject obj = new JSONObject(json);
      result = wsClient.post(URL + apiversion_rest + "role?_method=DELETE&format=json&multiMode=true", obj.toString());
    } catch (Exception e){
      result = ResultStore.failure(e.getMessage() + Misc.getStacktraceAsString(e));
    }
    return result;
  }

  public Map<String, String> getCategoriesNames(String categorytype){
    Map<String, String> result;

    try {
      result = wsClient.get(URL + apiversion_rest + "categories?scope="+categorytype);
      Result.rcEquals(result, "0");
      String json = result.get("out");
      String toResult = "";

      JSONObject obj = new JSONObject(json);
      JSONArray arr = obj.getJSONArray("data");
      int num = arr.length();

      for (int i=0; i<num; i++)
      {
        toResult = toResult.concat(arr.get(i).toString()).concat(" ");
      }
      result = ResultStore.success(toResult);

    } catch (Exception e){
      result = ResultStore.failure("Categories not found. " + e.getMessage() + Misc
              .getStacktraceAsString
                      (e));
    }
    return result;
  }

  public Map<String, String> deleteCategories(String[] categories) {
    Map<String, String> result;
    int num = categories.length;
    String catString = "";
    for (int i=0; i<num; i++) {
      catString = catString.concat("{\"category\":\"" + categories[i] + "\"}");
      if (i<num-1) catString = catString.concat(",");
    }

    try {
      String json = "{\"event_category_list\":{\"event_category\":[" + catString + "]}}";
      Reporter.log("json: " + json);
      JSONObject obj = new JSONObject(json);
      result = wsClient.post(URL + apiversion_rest+ "eventCategory?_method=DELETE&format=json&multiMode=true", obj.toString());
    } catch (Exception e){
      result = ResultStore.failure(e.getMessage() + Misc.getStacktraceAsString(e));
    }
    return result;
  }

  public Map<String, String> deleteCategory(String categoryName) {
    Map<String, String> result;
    String[] categories = {categoryName};

    try {
      result = deleteCategories(categories);
    } catch (Exception e){
      result = ResultStore.failure(e.getMessage() + Misc.getStacktraceAsString(e));
    }
    return result;
  }

  public Map<String, String> createGroup(String groupName, String description) {
    Map<String, String> result;

    try {
      String json = "{\"user_group\":{" +
              "\"name\":\"" + groupName + "\"," +
              "\"description\":\"" + description + "\"," +
              "\"event_assignment\":true," +
              "\"ldap_auto_assignment\":false," +
              "\"child_user_group\":[]," +
              "\"parent_user_group\":[]," +
              "\"user_object\":[]," +
              "\"user_group_to_role\":[]," +
              "\"ldap_group\":[]}}";
      Reporter.log("json: " + json);
      JSONObject obj = new JSONObject(json);
      result = wsClient.post(URL + "/group?format=json", obj.toString());

      JSONObject out = new JSONObject(result.get("out"));
      String id = out.getJSONObject("user_group").get("id").toString();
      result = ResultStore.success(id);

      System.out.println("group ID: " + id);
    } catch (Exception e){
      result = ResultStore.failure(e.getMessage() + Misc.getStacktraceAsString(e));
    }
    return result;
  }

  public Map<String, String> createGroupAssignUserAndRole(String groupName, String description,
                                                          String userId, String roleId) {
    Map<String, String> result;

    try {
      String json = "{\"user_group\":{" +
              "\"name\":\"" + groupName + "\"," +
              "\"description\":\"" + description + "\"," +
              "\"event_assignment\":true," +
              "\"ldap_auto_assignment\":false," +
              "\"child_user_group\":[]," +
              "\"parent_user_group\":[]," +
              "\"user_object\":[{\"id\":\"" + userId + "\"}]," +
              "\"user_group_to_role\":[{\"role\":{\"id\":\"" + roleId + "\"}}]," +
              "\"ldap_group\":[]}}";
      Reporter.log("json: " + json);
      JSONObject obj = new JSONObject(json);
      result = wsClient.post(URL + "/group?format=json", obj.toString());

      JSONObject out = new JSONObject(result.get("out"));
      String id = out.getJSONObject("user_group").get("id").toString();
      result = ResultStore.success(id);

      System.out.println("group ID: " + id);
    } catch (Exception e){
      result = ResultStore.failure(e.getMessage() + " " + Misc.getStacktraceAsString(e));
    }
    return result;
  }

  public Map<String, String> createGroupAssignRole(String groupName, String description,
                                                   String roleId) {
    Map<String, String> result;

    try {
      String json = "{\"user_group\":{" +
              "\"name\":\"" + groupName + "\"," +
              "\"description\":\"" + description + "\"," +
              "\"event_assignment\":true," +
              "\"ldap_auto_assignment\":false," +
              "\"child_user_group\":[]," +
              "\"parent_user_group\":[]," +
              "\"user_object\":[]," +
              "\"user_group_to_role\":[{\"role\":{\"id\":\"" + roleId + "\"}}]," +
              "\"ldap_group\":[]}}";
      Reporter.log("json: " + json);
      JSONObject obj = new JSONObject(json);
      result = wsClient.post(URL + "/group?format=json", obj.toString());

      JSONObject out = new JSONObject(result.get("out"));
      String id = out.getJSONObject("user_group").get("id").toString();
      result = ResultStore.success(id);

      System.out.println("group ID: " + id);
    } catch (Exception e){
      result = ResultStore.failure(e.getMessage() + " " + Misc.getStacktraceAsString(e));
    }
    return result;
  }

  public Map<String, String> getGroups()  {
    Map<String, String> result;
    wsClient.get(URL + apiversion_rest + "session/user");
    result = wsClient.get(URL + "/group?format=json");

//    Result.rcEquals(result, "0");
//
    return result;
  }

  public Map<String, String> getGroupId(Map<String, String> incomingResult, String groupName) {
    Map<String, String> result;

    try {
      String json = incomingResult.get("out");
      Map<String, String> groups = new HashMap<>();
      JSONObject group;
      String id;
      String name;

      JSONObject obj = new JSONObject(json).getJSONObject("user_group_list");
      JSONArray arr = obj.getJSONArray("user_group");
      int num = arr.length();

      for (int i=0; i<num; i++) {
        group = (JSONObject)arr.get(i);
        id = group.get("id").toString();
        name = group.get("name").toString();
        groups.put(name, id);
      }

      result = ResultStore.success(groups.get(groupName));

    } catch (Exception e){
      result = ResultStore.failure("Group not found. " + e.getMessage() + Misc.getStacktraceAsString
              (e));
    }
    return result;
  }

  public Map<String, String> deleteGroupById(String groupId) {
    Map<String, String> result;

    try {
      String json = "{\"user_group_list\":{\"user_group\":[{" +
              "\"id\":\"" + groupId + "\"}]}}";
      Reporter.log("json: " + json);
      JSONObject obj = new JSONObject(json);
      result = wsClient.post(URL + "/group?_method=DELETE&format=json&multiMode=true", obj.toString());
    } catch (Exception e){
      result = ResultStore.failure(e.getMessage() + Misc.getStacktraceAsString(e));
    }
    return result;
  }

  public Map<String, String> deleteGroupByName(String groupName) {
    Map<String, String> result;

    try {
      result = deleteGroupById(getGroupId(getGroups(), groupName).get("out"));
    } catch (Exception e){
      result = ResultStore.failure(e.getMessage() + Misc.getStacktraceAsString(e));
    }
    return result;
  }



}
