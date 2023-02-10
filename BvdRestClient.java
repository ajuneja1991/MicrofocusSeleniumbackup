package com.hp.opr.qa.framework;

import com.mf.opr.qa.framework.RestClient;
import com.typesafe.config.Config;
import com.mf.opr.qa.framework.TestConfig;
import org.json.JSONObject;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

public class BvdRestClient extends RestClient {
    private static Config cfg = new TestConfig().getConfig();
    private static String BVD_USR = cfg.getString("application.bvd.user.adminusr");
    private static String BVD_PWD = cfg.getString("application.bvd.user.adminpwd");
    private static String URL=cfg.getString("application.bvd.url.external");
    private static String IDM_BASE_URL=cfg.getString("application.bvd.url.idmBaseUrl");
    private static String TENANT=cfg.getString("application.bvd.user.tenant");
    private String api_version=cfg.getString("application.bvd.url.api_version");
    private String apiversion_rest="/rest/"+api_version+"/";
    private String PROXY_HOST="";
    private String BROWSER="";
    private int PROXY_PORT;
    private Entity<?> content = Entity.entity("{}", MediaType.APPLICATION_JSON);
    public static String secureModToken = "";
    public static String authToken = "";

    public BvdRestClient() {
        super(BVD_USR, BVD_PWD);
    }

    public BvdRestClient(String username, String password) {
        super(username, password);
    }

    public BvdRestClient(String username, String password, String proxyHost, Integer proxyPort) {
        super(username, password, proxyHost, proxyPort);
    }

    @Override
    public Response execRequest(HttpVerbs httpVerb, String url, Entity<?> content, String contentType)
    {
        contentType = getHeaders() == null ? null : getHeaders().get("Content-Type");


        if(contentType == null || contentType.isEmpty())
        {
            contentType = isMediaTypeJson() ? MediaType.APPLICATION_JSON : MediaType.APPLICATION_XML;
        }
        Response response = null;
        Invocation.Builder b = restClient.target(url).
                request(contentType).
                header("x-auth-token", getXAuthToken(BVD_USR,BVD_PWD,TENANT));
        if (!headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) b.header(entry.getKey(), entry.getValue());
        }
        switch (httpVerb) {
            case POST:
            case PUT:
            case DELETE:
                b = b.header("X-Secure-Modify-Token", getSecureToken(content));
                break;
            default:
                break;
        }
        switch (httpVerb) {
            case POST:
                response = b.post(content);
                if (response.getCookies().containsKey("secureModifyToken")) {
                    BvdRestClient.secureModToken = response.getCookies().get("secureModifyToken").getValue();;
                }
                break;
            case GET:
                response = b.get();
                if (response.getCookies().containsKey("secureModifyToken")) {
                    BvdRestClient.secureModToken = response.getCookies().get("secureModifyToken").getValue();;
                }
                break;
            case PUT:
                response = b.put(content);
                if (response.getCookies().containsKey("secureModifyToken")) {
                    BvdRestClient.secureModToken = response.getCookies().get("secureModifyToken").getValue();;
                }
                break;
            case DELETE:
                response = b.delete();
                if (response.getCookies().containsKey("secureModifyToken")) {
                    BvdRestClient.secureModToken = response.getCookies().get("secureModifyToken").getValue();;
                }
                break;
            default:
                break;
        }
        return response;
    }

    protected String getSecureToken(Entity<?> content) {
        Response response = restClient.target(URL + apiversion_rest +"channel")
                .request()
                .header("x-auth-token", getXAuthToken(BVD_USR,BVD_PWD,TENANT))
                .get();
        String xsecureModifyToken = "";

        if (response.getCookies().containsKey("secureModifyToken")) {
            xsecureModifyToken = response.getCookies().get("secureModifyToken").getValue();
            BvdRestClient.secureModToken = xsecureModifyToken;
        } else {
            xsecureModifyToken = BvdRestClient.secureModToken;
        }
        response = restClient.target(URL + "/logout")
                .request()
                .header("x-auth-token", getXAuthToken(BVD_USR,BVD_PWD,TENANT))
                .post(this.content);
        response = restClient.target(URL + apiversion_rest+ "channel")
                .request()
                .header("x-auth-token", getXAuthToken(BVD_USR,BVD_PWD,TENANT))
                .get();
        if (response.getCookies().containsKey("secureModifyToken")) {
            xsecureModifyToken = response.getCookies().get("secureModifyToken").getValue();
            BvdRestClient.secureModToken = xsecureModifyToken;
            return xsecureModifyToken;
        } else {
            xsecureModifyToken = BvdRestClient.secureModToken;
            return xsecureModifyToken;
        }
    }

    protected Invocation.Builder authenticateRequest(Invocation.Builder b, HttpVerbs httpVerb) {
        Response response = restClient.target(URL + apiversion_rest + "channel")
                .request()
                .header("x-auth-token", getXAuthToken(BVD_USR,BVD_PWD,TENANT))
                .get();
        String xsecureModifyToken="";

        if (response.getCookies().containsKey("secureModifyToken")) {
            xsecureModifyToken = response.getCookies().get("secureModifyToken").getValue();
            BvdRestClient.secureModToken = xsecureModifyToken;
        } else {
            xsecureModifyToken = BvdRestClient.secureModToken;
        }

        if (response.getCookies().containsKey("secureModifyToken")) {
            xsecureModifyToken = response.getCookies().get("secureModifyToken").getValue();
            BvdRestClient.secureModToken = xsecureModifyToken;
        } else {
            xsecureModifyToken = BvdRestClient.secureModToken;
        }
        response = restClient.target(URL + "/logout")
                .request()
                .header("x-auth-token", getXAuthToken(BVD_USR,BVD_PWD,TENANT))
                .post(this.content);
        response = restClient.target(URL + apiversion_rest + "channel")
                .request()
                .header("x-auth-token", getXAuthToken(BVD_USR,BVD_PWD,TENANT))
                .get();
        return b
                .header("X-Secure-Modify-Token", xsecureModifyToken);
    }

    protected String getXAuthToken(String username, String password, String tenant) {
        String xAuthToken ="";
        Response response = null;

        JSONObject passwordCredentialsDetails = new JSONObject();
        passwordCredentialsDetails.put("username", username);
        passwordCredentialsDetails.put("password", password);

        JSONObject requestBody = new JSONObject();
        requestBody.put("passwordCredentials",passwordCredentialsDetails);
        requestBody.put("tenantName", tenant);

        response = restClient.target(IDM_BASE_URL + "idm-service/v3.0/tokens")
                .request().header("Content-Type", "application/json").post(Entity.json(requestBody.toString()));
        JSONObject jsonResponse = new JSONObject(response.readEntity(String.class));
        xAuthToken = (String) jsonResponse.getJSONObject("token").get("id");

        if(xAuthToken != null || !xAuthToken.isEmpty()) {
            BvdRestClient.authToken = xAuthToken;
            return xAuthToken;
        }
        else{
            xAuthToken = BvdRestClient.authToken;
            return xAuthToken;
        }
    }

}
