package com.hp.opr.qa.tests.sys.ui.bvd;


import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.common.utils.ResultStore;
import com.hp.opr.qa.framework.pageobjects.bvd.EditDashboardPage;
import com.hp.opr.qa.framework.pageobjects.bvd.LoginPage;
import com.hp.opr.qa.framework.pageobjects.bvd.MainPage;
import com.hp.opr.qa.framework.pageobjects.bvd.ManageDashboardsPage;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.TestConfig;
import com.mf.opr.qa.framework.utils.TestUtils;
import com.hp.opr.qa.framework.utils.UiTestUtils;
import com.typesafe.config.Config;
import org.hornetq.utils.json.JSONException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DonutChart {
    private LoginPage loginPage;
    private MainPage mainPage;
    private ManageDashboardsPage manageDashboardsPage;
    private EditDashboardPage editDashboardPage;
    private WebDriver driver;
    private Map<String, String> result;
    private String sWidget = "group19";
    private String sWidgetnew = "group802";
    private String sDataChannel ;
    private List<String> lDataField = Collections.singletonList("numberOfCritical");
    private List<String> lDataFields = Arrays.asList("numberOfCritical", "numberOfMajor",
          "numberOfMinor", "numberOfNormal", "numberOfUnknown", "cpuLoad", "ramUsage");
    private List<String> lChartNumbersDefault = Arrays.asList("20", "80");
    private List<String> lChartNumberMaxValue = Arrays.asList("20th", "0th");
    private List<String> lChartNumberFormatted = Arrays.asList("20th", "80th");
    private List<String> sDonutFields_title = Arrays.asList("numberOfCritical: 20", "numberOfMajor: 15","numberOfMinor: 10","numberOfNormal: 5",
            "numberOfUnknown: 0","cpuLoad: 77","ramUsage: 89");
    //TODO: clean old values and steps

    List<String> lSlicesDefaultChrome = Collections.singletonList("M-1.7320189602291773e-13," +
          "-49.60699462890625A49.60699462890625,49.60699462890625 0 0,1 47.179044234017915," +
          "-15.329439039864251L28.33953981459861,-9.208097685295094A29.7979626801088," +
          "29.7979626801088 0 0,0 -1.0403903063314399e-13,-29.7979626801088Z, " +
          "M47.179044234017915,-15.329439039864251A49.60699462890625,49.60699462890625 0 1,1 " +
          "-0.00018221666585574968,-49.606994628571584L-0.00010945402859982127," +
          "-29.797962679907773A29.7979626801088,29.7979626801088 0 1,0 28.33953981459861," +
          "-9.208097685295094Z");
    List<String> lSlicesDefaultIE = Collections.singletonList("M 2.16773e-015 -35.4016 A 35.4016 35.4016 0 0 1 33.669 -10.9397 L 11.7841 -3.8289 A 12.3906 12.3906 0 0 0 7.58704e-016 -12.3906 Z, M 33.669 -10.9397 A 35.4016 35.4016 0 1 1 -6.50318e-015 -35.4016 L -2.27611e-015 -12.3906 A 12.3906 12.3906 0 1 0 11.7841 -3.8289 Z");
    List<String> lSlicesDefaultFirefox = Collections.singletonList("M2.167725835710746e-15,-35.401649475097656A35.401649475097656,35.401649475097656 0 0,1 33.66896942088853,-10.939711316710111L11.784139297310984,-3.828898960848539A12.39057731628418,12.39057731628418 0 0,0 7.587040424987611e-16,-12.39057731628418Z, M33.66896942088853,-10.939711316710111A35.401649475097656,35.401649475097656 0 1,1 -6.503177507132238e-15,-35.401649475097656L-2.276112127496283e-15,-12.39057731628418A12.39057731628418,12.39057731628418 0 1,0 11.784139297310984,-3.828898960848539Z");
    List<String> lSlicesMaxValueChrome = Collections.singletonList("M-1.7320189602291773e-13," +
          "-49.60699462890625A49.60699462890625,49.60699462890625 0 1,1 -0.00018221666585574968," +
          "-49.606994628571584L-0.00010945402859982127,-29.797962679907773A29.7979626801088," +
          "29.7979626801088 0 1,0 -1.0403903063314399e-13,-29.7979626801088Z, " +
          "M-0.00018221666585574968,-49.606994628571584A49.60699462890625,49.60699462890625 0 0,1" +
          " -0.00018221666585574968,-49.606994628571584L-0.00010945402859982127," +
          "-29.797962679907773A29.7979626801088,29.7979626801088 0 0,0 -0.00010945402859982127," +
          "-29.797962679907773Z");
    List<String> lSlicesMaxValueIE = Collections.singletonList("M 0 35.4016 A 35.4016 35.4016 0 1 1 0 -35.4016 A 35.4016 35.4016 0 1 1 0 35.4016 M 0 12.3906 A 12.3906 12.3906 0 1 0 0 -12.3906 A 12.3906 12.3906 0 1 0 0 12.3906 Z, M -6.50318e-015 -35.4016 A 35.4016 35.4016 0 0 1 -6.50318e-015 -35.4016 L -2.27611e-015 -12.3906 A 12.3906 12.3906 0 0 0 -2.27611e-015 -12.3906 Z");
    List<String> lSlicesMaxValueFirefox = Collections.singletonList("M0,35.401649475097656A35.401649475097656,35.401649475097656 0 1,1 0,-35.401649475097656A35.401649475097656,35.401649475097656 0 1,1 0,35.401649475097656M0,12.39057731628418A12.39057731628418,12.39057731628418 0 1,0 0,-12.39057731628418A12.39057731628418,12.39057731628418 0 1,0 0,12.39057731628418Z, M-6.503177507132238e-15,-35.401649475097656A35.401649475097656,35.401649475097656 0 0,1 -6.503177507132238e-15,-35.401649475097656L-2.276112127496283e-15,-12.39057731628418A12.39057731628418,12.39057731628418 0 0,0 -2.276112127496283e-15,-12.39057731628418Z");
    List<String> lSlicesDataFieldsChrome = Collections.singletonList("M-1.7320189602291773e-13," +
          "-49.60699462890625A49.60699462890625,49.60699462890625 0 0,1 27.25947482741444," +
          "-41.446048644543815L16.374239553575833,-24.895840193238495A29.7979626801088," +
          "29.7979626801088 0 0,0 -1.0403903063314399e-13,-29.7979626801088Z, M27.25947482741444," +
          "-41.446048644543815A49.60699462890625,49.60699462890625 0 0,1 42.2213246977682," +
          "-26.042535530895645L25.361533530913523,-15.64324764380804A29.7979626801088," +
          "29.7979626801088 0 0,0 16.374239553575833,-24.895840193238495Z, M42.2213246977682," +
          "-26.042535530895645A49.60699462890625,49.60699462890625 0 0,1 47.916667451391355," +
          "-12.839271648543997L28.782615821675805,-7.712302272796008A29.7979626801088," +
          "29.7979626801088 0 0,0 25.361533530913523,-15.64324764380804Z, M47.916667451391355," +
          "-12.839271648543997A49.60699462890625,49.60699462890625 0 0,1 49.27156498093639," +
          "-5.759062462041577L29.596476575038775,-3.4593574877913156A29.7979626801088," +
          "29.7979626801088 0 0,0 28.782615821675805,-7.712302272796008Z, M49.27156498093639," +
          "-5.759062462041577A49.60699462890625,49.60699462890625 0 0,1 49.27156498093639," +
          "-5.759062462041577L29.596476575038775,-3.4593574877913156A29.7979626801088," +
          "29.7979626801088 0 0,0 29.596476575038775,-3.4593574877913156Z, M49.27156498093639," +
          "-5.759062462041577A49.60699462890625,49.60699462890625 0 0,1 -26.042419215107614," +
          "42.22139644228892L-15.643177775162766,25.361576626460163A29.7979626801088," +
          "29.7979626801088 0 0,0 29.596476575038775,-3.4593574877913156Z, M-26.042419215107614," +
          "42.22139644228892A49.60699462890625,49.60699462890625 0 0,1 -0.00018221666585574968," +
          "-49.606994628571584L-0.00010945402859982127,-29.797962679907773A29.7979626801088," +
          "29.7979626801088 0 0,0 -15.643177775162766,25.361576626460163Z");
    List<String> lSlicesDataFieldsIE = Collections.singletonList("M 2.16773e-015 -35.4016 A 35.4016 35.4016 0 0 1 20.8086 28.6405 L 7.283 10.0242 A 12.3906 12.3906 0 0 0 7.58704e-016 -12.3906 Z, M 20.8086 28.6405 A 35.4016 35.4016 0 0 1 -33.669 10.9397 L -11.7841 3.8289 A 12.3906 12.3906 0 0 0 7.283 10.0242 Z, M -33.669 10.9397 A 35.4016 35.4016 0 0 1 -20.8086 -28.6405 L -7.283 -10.0242 A 12.3906 12.3906 0 0 0 -11.7841 3.8289 Z, M -20.8086 -28.6405 A 35.4016 35.4016 0 0 1 -6.50318e-015 -35.4016 L -2.27611e-015 -12.3906 A 12.3906 12.3906 0 0 0 -7.283 -10.0242 Z, M -6.50318e-015 -35.4016 A 35.4016 35.4016 0 0 1 -6.50318e-015 -35.4016 L -2.27611e-015 -12.3906 A 12.3906 12.3906 0 0 0 -2.27611e-015 -12.3906 Z");
    List<String> lSlicesDataFieldsFirefox = Collections.singletonList("M2.167725835710746e-15,-35.401649475097656A35.401649475097656,35.401649475097656 0 0,1 20.808567468289976,28.64053605425894L7.2829986139014915,10.024187618990629A12.39057731628418,12.39057731628418 0 0,0 7.587040424987611e-16,-12.39057731628418Z, M20.808567468289976,28.64053605425894A35.401649475097656,35.401649475097656 0 0,1 -33.66896942088853,10.939711316710115L-11.784139297310984,3.8288989608485404A12.39057731628418,12.39057731628418 0 0,0 7.2829986139014915,10.024187618990629Z, M-33.66896942088853,10.939711316710115A35.401649475097656,35.401649475097656 0 0,1 -20.80856746828998,-28.640536054258938L-7.282998613901492,-10.024187618990629A12.39057731628418,12.39057731628418 0 0,0 -11.784139297310984,3.8288989608485404Z, M-20.80856746828998,-28.640536054258938A35.401649475097656,35.401649475097656 0 0,1 -6.503177507132238e-15,-35.401649475097656L-2.276112127496283e-15,-12.39057731628418A12.39057731628418,12.39057731628418 0 0,0 -7.282998613901492,-10.024187618990629Z, M-6.503177507132238e-15,-35.401649475097656A35.401649475097656,35.401649475097656 0 0,1 -6.503177507132238e-15,-35.401649475097656L-2.276112127496283e-15,-12.39057731628418A12.39057731628418,12.39057731628418 0 0,0 -2.276112127496283e-15,-12.39057731628418Z");
    List<String> lChartColorDefault = Arrays.asList("#00BC74, #37BAE8");
    List<String> lChartColor = Arrays.asList("#FF0000", "#FF9933");
    List<String> lChartColors = Arrays.asList("#FF0000, #FF9933, #C8C800, #33CC33, #B2B2B2, #7030A0, #0078B9");
    List<String> lChartNumbers = Arrays.asList("20th", "15th", "10th", "5th", "0th", "77th", "89th");
    String sVisibility = "hidden";
    String sChartColors = "#FF0000;#FF9933;#C8C800;#33CC33;#B2B2B2";
    String sNumberFormat = "'0o'";
    String sLinkToDashboard;
    String sLinkToUrl = "https://www.google.de";
    String sVisibilityRule = "numberOfCritical!=20";
    String sSourceDashboard = "BaseDashboard";
    String sSourceDashboardsample ="sample_dashboard_test";
    private String sDashboard ;
    String sDashboardnew;
    String sDashboardPathnew;
    String sDashboardPath ;
    private String BVD_USR="";
    private String BVD_PWD="";
    private String URL="";
    private Config cfg;
    private String PROXY_HOST="";
    private String BROWSER="";
    private int PROXY_PORT;
    String key;
    String dims = "browser";
    String tags = "DonutChart";
    private String userName = "Testerdonutchart";
    private String userLogin;
    private String userPwd = "Test@123";
    private String roleName = "testdonutchart";
    private String desc = "For tester";
    private String category = "All";
    private String accessType = "full-control";
    String roleId;
    private List<String> admindropdownoptions = Arrays.asList("Dashboards", "Resources");
    BvdWsClient client;
    String json;

    private void initPages() {
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        mainPage = PageFactory.initElements(driver, MainPage.class);
        manageDashboardsPage = PageFactory.initElements(driver, ManageDashboardsPage.class);
        editDashboardPage = PageFactory.initElements(driver, EditDashboardPage.class);
    }

    @BeforeClass
    public void setup() {
        cfg = new TestConfig().getConfig();
        BROWSER=cfg.getString("application.bvd.browser.name");
        BVD_USR = cfg.getString("application.bvd.user.adminusr");
        BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
        URL=cfg.getString("application.bvd.url.external");
        sDataChannel = "DonutChart " + BROWSER;
        sDashboard = sSourceDashboard + "_BVDDonutChart_" + BROWSER;
        sDashboardnew=sSourceDashboardsample+"_BVDDonutChart_" + BROWSER;
        sDashboardPath = "C:\\tmp\\" + sDashboard + ".svg";
        sDashboardPathnew = "C:\\tmp\\" + sDashboardnew + ".svg";
        driver = UiTestWebDriver.getWebDriver();
        driver.get(URL);
        initPages();
        try {
            client = new BvdWsClient(BVD_USR, BVD_PWD, PROXY_HOST, PROXY_PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 1)
    public void createRole() {
        result = client.userWS.createRole(roleName, desc, category,accessType);
        Result.rcEquals(result, "0");
        roleId = result.get("out");
    }
    @Test(priority = 5)
    public void idmlogin() {
        result = loginPage.login(userName, userPwd);
        Result.rcEquals(result, "0");
    }
    @Test(priority = 10)
    public void login_check()
    {
        result = loginPage.idm_LoggedIn_Check();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 15)
    public void checknonadminOptionsdropdown() {
        result = mainPage.adminoptionsfornonadmin(admindropdownoptions);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 20)
    public void copyTestFiles() {
        result = TestUtils.downloadResource(this.getClass(), sSourceDashboard + ".svg", sDashboardPath);
        Result.rcEquals(result, "0");

        result = TestUtils.downloadResource(this.getClass(), sSourceDashboardsample + ".svg",  sDashboardPathnew);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 30)
    public void getDashboardNames() {
        result = mainPage.getDashboardNames();
        Result.rcEquals(result, "0");

        sLinkToDashboard = "Welcome";
    }

    @Test(priority = 40)
    public void getApiKeyWS() {
        result = client.userWS.getApiKey();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 50)
    public void sendData() throws JSONException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        key = result.get("out");
        json = "{\"browser\":\"" + BROWSER + "\"," +
                "\"omiSystem\":\"tcpd123.deu.hp.com\"," +
                "\"mdb\":\"OMi Health Status\"," +
                "\"label\":\"Overall\"," +
                "\"type\":\"PIE\"," +
                "\"filterName\":\"OMi Health Status Overall\"," +
                "\"viewFilterName\":\"OMi Deployment with HP Operations Agents\"," +
                "\"filterStatus\":\"FILTER_OK\"," +
                "\"mostCritical\":3," +
                "\"numberOfCritical\":20," +
                "\"numberOfMajor\":15," +
                "\"numberOfMinor\":10," +
                "\"numberOfNormal\":5," +
                "\"numberOfUnknown\":0," +
                "\"cpuLoad\":77," +
                "\"ramUsage\":89," +
                "\"statusColor\":\"#C8C800\"}";

        result = client.receiverWS.bvdSendData(key, dims, tags, json);
        Result.rcEquals(result, "0");

    }

    @Test(priority = 60)
    public void openManageDashboards() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 70)
    public void uploadDashboard() {
        result = manageDashboardsPage.uploadDashboard(sDashboardPath);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 80)
    public void selectWidget() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 90)
    public void setDataChannel() {
        result = editDashboardPage.setDataChannel(sDataChannel);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 95)
    public void apply() {
        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 100)
    public void setDataField() {
        result = editDashboardPage.setDataFieldMultiSelect(lDataField);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 110)
    public void saveUploadDashboard() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 120)
    public void viewDashboard() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 130)
    public void checkChartNumbers() {
        result = mainPage.getChartNumbers(sWidget);
        Result.outEquals(result, lChartNumbersDefault.toString());
    }

    @Test(priority = 140)
    public void checkSlices() {
        result = mainPage.getSlicesOfDonutChartWidget(sWidget);
        Result.rcEquals(result, "0");

        if (BROWSER.equals("ie")) {
            Result.assertEquals(result.get("out"), lSlicesDefaultIE.toString());
        } else if (BROWSER.equals("chrome")) {
            Result.assertEquals(result.get("out"), lSlicesDefaultChrome.toString());
        } else {
            Result.assertEquals(result.get("out"), lSlicesDefaultFirefox.toString());
        }
    }

    @Test(priority = 150)
    public void checkDefaultChartColors() {
        result = mainPage.getColorsOfDonutChartWidget(sWidget);
        Result.outEquals(result, lChartColorDefault.toString());
    }

    @Test(priority = 160)
    public void openManageDashboardsToSetNumberFormat() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 170)
    public void openEditDashboardToSetNumberFormat() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 180)
    public void selectWidgetToSetNumberFormat() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 190)
    public void setNumberFormat() {
        result = editDashboardPage.setNumberFormat(sNumberFormat);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 200)
    public void saveDashboardNumberFormat() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 210)
    public void viewDashboardNumberFormat() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 220)
    public void checkNumberFormatOfWidget() {
        result = mainPage.getChartNumbers(sWidget);
        Result.outEquals(result, lChartNumberFormatted.toString());
    }

    @Test(priority = 230)
    public void openManageDashboardsToSetChartColors() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 240)
    public void openEditDashboardToSetChartColors() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 250)
    public void selectWidgetToSetChartColors() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 260)
    public void setChartColors() {
        result = editDashboardPage.setChartColors(sChartColors);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 270)
    public void saveDashboardChartColors() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 280)
    public void viewDashboardChartColors() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 290)
    public void checkChartColors() {
        result = mainPage.getColorsOfDonutChartWidget(sWidget);
        Result.outEquals(result, lChartColor.toString());
    }

    @Test(priority = 300)
    public void openManageDashboardsToSetMaxValue() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 310)
    public void openEditDashboardToSetMaxValue() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 320)
    public void selectWidgetToSetMaxValue() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 330)
    public void setMaxValue() {
        result = editDashboardPage.setMaxValue("10");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 340)
    public void saveDashboardMaxValue() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 350)
    public void MaxValue() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 360)
    public void checkChartNumbersMaxValue() {
        result = mainPage.getChartNumbers(sWidget);
        Result.outEquals(result, lChartNumberMaxValue.toString());
    }

    @Test(priority = 370)
    public void checkSlicesMaxValue() {
        result = mainPage.getSlicesOfDonutChartWidget(sWidget);
        Result.rcEquals(result, "0");

        if (BROWSER.equals("ie")) {
            Result.assertEquals(result.get("out"), lSlicesMaxValueIE.toString());
        } else if (BROWSER.equals("chrome")) {
            Result.assertEquals(result.get("out"), lSlicesMaxValueChrome.toString());
        } else {
            Result.assertEquals(result.get("out"), lSlicesMaxValueFirefox.toString());
        }
    }

    @Test(priority = 380)
    public void openManageDashboardsToSetDataFields() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 390)
    public void openEditDashboardToSetDataFields() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 400)
    public void selectWidgetToSetDataFields() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 410)
    public void setDataFields() {
        if (BROWSER.equalsIgnoreCase("chrome")) {

            result = editDashboardPage.SetDataFieldIgnoreExisting(lDataFields);
            Result.rcEquals(result, "0");
        }else{
            result= editDashboardPage.clearDataFieldselectCalprop();
            Result.rcEquals(result, "0");

            result = editDashboardPage.setDataFieldMultiSelectgecko(lDataFields);
            Result.rcEquals(result, "0");
        }
    }

    @Test(priority = 420)
    public void saveDashboardDataFields() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 430)
    public void viewDashboardDataFields() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 440)
    public void checkChartColorsDataFields() {
        result = mainPage.getColorsOfDonutChartWidget(sWidget);
        Result.outEquals(result, lChartColors.toString());
    }

    @Test(priority = 450)
    public void checkChartNumbersDataFields() {
        result = mainPage.getChartNumbers(sWidget);
        Result.outEquals(result, lChartNumbers.toString());
    }

    @Test(priority = 460)
    public void checkSlicesDataFields() {
        result = mainPage.getSlicesOfDonutChartWidget(sWidget);
        Result.rcEquals(result, "0");

        if (BROWSER.equals("ie")) {
            Result.assertEquals(result.get("out"), lSlicesDataFieldsIE.toString());
        } else if (BROWSER.equals("chrome")) {
            Result.assertEquals(result.get("out"), lSlicesDataFieldsChrome.toString());
        } else {
            Result.assertEquals(result.get("out"), lSlicesDataFieldsFirefox.toString());
        }
    }


    @Test(priority = 550)
    public void openManageDashboardsToChangeDonutSize() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 560)
    public void openEditDashboardToChangeDonutSize() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 570)
    public void selectWidgetToChangeDonutSize() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 780)
    public void openManageDashboardsToSetHyperlinkToAnotherDashboard() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 790)
    public void openEditDashboardToSetHyperlinkToAnotherDashboard() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 800)
    public void selectWidgetToSetHyperlinkToAnotherDashboard() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 810)
    public void setHyperlinkToAnotherDashboard() {
        UiTestUtils.waitDocumentReadyState(driver, 15);
        result = editDashboardPage.setHyperlinkToAnotherDashboard(sLinkToDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 820)
    public void saveDashboardHyperlinkToAnotherDashboard() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 830)
    public void viewDashboardHyperlinkToAnotherDashboard() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 840, enabled = false)
    public void checkHyperlinkToAnotherDashboard() {
        result = mainPage.getHyperlink(sWidget);
        Result.outContains(result, sLinkToDashboard);

        result = mainPage.clickWidget(sWidget);
        Result.rcEquals(result, "0");

        result = mainPage.getDashboard();
        Result.outEquals(result, sLinkToDashboard);
    }

    @Test(priority = 850)
    public void openManageDashboardsToSetHyperlinkToUrl() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 860)
    public void openEditDashboardToSetHyperlinkToUrl() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 870)
    public void selectWidgetToSetHyperlinkToUrl() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 880)
    public void setHyperlinkToUrl() {
        result = editDashboardPage.setHyperlinkToUrl(sLinkToUrl);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 890)
    public void saveDashboardHyperlinkToUrl() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 900)
    public void viewDashboardHyperlinkToUrl() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 910, enabled = false)
    public void checkHyperlinkToUrl() {
        result = mainPage.getHyperlink(sWidget);
        Result.outEquals(result, sLinkToUrl);

        result = mainPage.clickWidget(sWidget);
        Result.outEquals(result, sLinkToUrl);
    }

    @Test(priority = 920, enabled = false)
    public void back() {
        driver.navigate().back();
        TestUtils.sleepAndLog(2000);
        if (driver.getCurrentUrl().contains(URL)) {
            result = ResultStore.success("Backward navigation was successful");
        } else {
            result = ResultStore.failure("Backward navigation was not working, instead " + result.get("out") + " is shown");
        }
        Result.rcEquals(result, "0");
    }

    @Test(priority = 930)
    public void openManageDashboardsToSetVisibilityRule() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 940)
    public void openEditDashboardToSetVisibilityRule() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 950)
    public void selectWidgetToSetVisibilityRule() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 960)
    public void setVisibilityRule() {
        result = editDashboardPage.setVisibilityRule(sVisibilityRule);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 970)
    public void saveDashboardVisibilityRule() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 980)
    public void viewDashboardVisibilityRule() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 990)
    public void checkVisibilityOfWidget() {
        result = mainPage.getVisibilityOfWidget(sWidget);
        Result.outEquals(result, sVisibility);
    }

    @Test(priority = 1000)
    public void openManageDashboards_title() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 1010)
    public void uploadDashboard_title() {
        result = manageDashboardsPage.uploadDashboard(sDashboardPathnew);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 1020)
    public void selectWidget_title() {
        result = editDashboardPage.selectWidget(sWidgetnew);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 1030)
    public void setDataChannel_title() {
        result = editDashboardPage.setDataChannel(sDataChannel);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 1040)
    public void apply_title() {
        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 1050)
    public void setDataField_title() {
        result = editDashboardPage.setDataFieldMultiSelect(lDataFields);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 1060)
    public void saveUploadDashboard_title() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 1070)
    public void viewDashboard_title() {
        result = mainPage.viewDashboard(sDashboardnew);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 1080)
    public void checkChartDataFields_title() {
        result = mainPage.getDonutFieldValues_title(sDonutFields_title);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 1090)
    public void testLogout() {
        result = mainPage.logout();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 1100)
    public void deleteRole()
    {
        result = client.userWS.deleteRole(roleId);
        Result.rcEquals(result,"0");
    }


    @Test(priority = 1110)
    public void deleteDashboard() {
        result = client.dashboardWS.deleteDashboard(sDashboard);
        Result.rcEquals(result, "0");
        result = client.dashboardWS.deleteDashboard(sDashboardnew);
        Result.rcEquals(result, "0");
    }

    @AfterClass
    public void cleanup() {
        driver.quit();
    }
}