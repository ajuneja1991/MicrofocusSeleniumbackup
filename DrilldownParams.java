package com.hp.opr.qa.tests.sys.ui.bvd;

import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.common.utils.ResultStore;
import com.hp.opr.qa.framework.pageobjects.bvd.EditDashboardPage;
import com.hp.opr.qa.framework.pageobjects.bvd.LoginPage;
import com.hp.opr.qa.framework.pageobjects.bvd.MainPage;
import com.hp.opr.qa.framework.pageobjects.bvd.ManageDashboardsPage;
import com.hp.opr.qa.framework.utils.UiTestUtils;
import com.mf.opr.qa.framework.TestConfig;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.utils.TestUtils;
import com.typesafe.config.Config;
import org.hornetq.utils.json.JSONException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DrilldownParams {

    private LoginPage loginPage;
    private MainPage mainPage;
    private ManageDashboardsPage manageDashboardsPage;
    private EditDashboardPage editDashboardPage;
    private WebDriver driver;
    private Map<String, String> result;
    private BvdWsClient client;
    private String shyperlinkWidget = "group8";
    private String sWidget = "group109";
    private String sWidgetanyshapetextwidgetDashboard2 = "group8";
    private String sTextWidgetshapefordate="shape206";
    private String sTextValueWidget="shape219";
    String sLinkToDashboard="DrilldownParams1_chrome";
    private String sText = "20";
    private String id = "background";
    private String hyperlinkurl="";
    private String HyperLinkforURLwidget="g#group109";
    DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("M/d/yyyy");
    LocalDateTime now = LocalDateTime.now();
    private String date = dtf1.format(now);
    DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("a");
    private String AmPm = dtf2.format(now);
    private List<String> sTextFieldsFirst = Arrays.asList("admin",date,AmPm);
    private String BVD_USR = "";
    private String BVD_PWD = "";
    private List<String> textElemsInWidgetwoTitlesolditem = new ArrayList<>();
    private List<String> textElemsInWidgetwotitleid = new ArrayList<>();
    public String hyperlinkurlafterbase="";
    List<String> paramfieldvaluesafterlinktourl=Arrays.asList("Week","9");
    List<String> paramfieldvaluesafterlinktodashboard=Arrays.asList("Day","5");
    List<String> fieldparams=Arrays.asList("TimeTestparam","idparam");
    private String URL = "";
    private Config cfg;
    private String PROXY_HOST = "";
    private String BROWSER = "";
    private String username="DrilldownparamsTest";
    private String sSourceDashboard = "DrilldownParams";
    private String sSourceDashboard1 = "DrilldownParams1";
    private String HyperLinkforDashboard="g#group11";
    private String sDashboard1;
    private String sDashboardPath1;
    private String sDashboard;
    private String sDashboardPath;
    private int PROXY_PORT;
    private String roleName = "roleDrilldownparams";
    private String desc = "roleDrilldownparams";
    private String category = "All";
    private String accessType = "full-control";
    private String pwd="Test@123";
    String roleId;
    
    private String parameter_dateTimeparam_start_tourl="2017-01-28 08:00";
    String timetestparam="Day";
    String paramfromdateinUI="parameter_dateTimeparam :start";
    String paramtodateinUI="parameter_dateTimeparam :end";
    private List<String> dataPointafterapplyingParam= Arrays.asList("svg path[d^='M 47'][stroke='none']",
            "svg path[d^='M 114'][stroke='none']");

    private List<String> dataPointafterapplyingParamtoanotherdashboard= Arrays.asList("svg path[d^='M 48'][stroke='none']",
            "svg path[d^='M 154'][stroke='none']");

    private List<String> dataPointafterapplyingParaminlinkedurldashboard= Arrays.asList("svg path[d^='M 113'][stroke='none']");
    private String hyperlinkurlwithoutbaseurlfordashboard;
    private String hyperlinkurlwithoutbaseurl;


    private void initPages() {
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        mainPage = PageFactory.initElements(driver, MainPage.class);
        manageDashboardsPage = PageFactory.initElements(driver, ManageDashboardsPage.class);
        editDashboardPage = PageFactory.initElements(driver, EditDashboardPage.class);
    }

    private String DrilldownDatabaseQuery() {
        return "{\"dataCollector\":{\"active\":true,\"data\":{\"resultFormat\":\"groupwidget\"," +
                "\"tags\":[],\"dims\":[],\"queryText\":\"SELECT * FROM public" +
                ".bvd_lwr_demo where ${ID = ${id}} and ${sold_item = ${solditem}} limit 12\",\"availableColumns\":" +
                "[\"ID\",\"LOCATION\",\"TIMESTAMP\",\"sold_item\"],\"sampleQueryResult\":{\"ID\":1,\"LOCATION\":\"Boeblingen\",\"TIMESTAMP\":" +
                "\"2017-01-01T09:00:00.000Z\",\"sold_item\":9}},\"name\":\"DrilldownDatabaseQuery\"}}";
    }


    private String DrilldownDatedataquery() {
        return "{\"dataCollector\":{\"active\":true,\"data\":{\"resultFormat\":\"unchanged\"," +
                "\"tags\":[],\"dims\":[],\"queryText\":\"SELECT * FROM" +
                " datetimeparam_table where ${timestamp > ${parameter_dateTime:start}} and ${timestamp < ${parameter_dateTime:end}}\"," +
                "\"availableColumns\":[\"ID\",\"TIMESTAMP\",\"DATA1\",\"DATA2\"],\"sampleQueryResult\":{\"ID\":29,\"TIMESTAMP\":\" 2017-01-29T09:00:00.000Z\"," +
                "\"Data1\":1,\"Data2\":100}},\"name\":\"DrilldownDatedataquery\"}}";
    }


    private String Drilldowndownvaluedataquery() {
        return "{\"dataCollector\":{\"active\":true,\"data\":{\"resultFormat\":\"unchanged\"," +
                "\"tags\":[],\"dims\":[],\"queryText\":\"SELECT * FROM " +
                "NonqueryWithDateTable where ${createdon <(current_date - cast(${TimeTest} as INTEGER))}" +
                "order by createdon desc\",\"availableColumns\":[\"DATA1\",\"DATA2\",\"ID\",\"CREATEDON\",\"CREATED\"]," +
                "\"sampleQueryResult\":{\"DATA1\":20,\"DATA2\":30,\"ID\":1," +
                "\"CREATEDON\":\"1950-01-01T00:00:00.000Z\",\"CREATED\":\"2000-12-31T23:00:00.000Z\"}},\"name\":\"Drilldowndownvaluedataquery\"}}";
    }


    private String idparamid() {
        return "{\"dataCollector\":{\"active\":true,\"data\":{\"resultFormat\":\"unchanged\"," +
                "\"tags\":[],\"dims\":[],\"paramQueryType\":\"sql\",\"displayName\":\"idparam\",\"labelValueList\":[],\"queryText\":\"SELECT * FROM " +
                "bvd_lwr_demo limit 12\",\"selectedDate\":\"specificDate\",\"selectedoption\":\"novalue\",\"variableName\":\"id\",\"value\":\"\",\"availableColumns\":" +
                "[\"ID\",\"LOCATION\",\"TIMESTAMP\",\"sold_item\"],\"sampleQueryResult\":{\"ID\":1,\"LOCATION\":\"Boeblingen\",\"TIMESTAMP\":" +
                "\"2017-01-01T09:00:00.000Z\",\"sold_item\":9},\"selectedColumn\":{\"icon\":\"\",\"id\":\"ID\",\"label\":\"ID\"},"+
                "\"selectedColumnValue\":{\"icon\":\"\",\"id\":\"ID\",\"label\":\"ID\"}},\"type\":\"param\",\"name\":\"idparam (id)\"}}";
    }

    private String solditemparamsolditem() {
        return "{\"dataCollector\":{\"active\":true,\"data\":{\"resultFormat\":\"unchanged\"," +
                "\"tags\":[],\"dims\":[],\"paramQueryType\":\"sql\",\"displayName\":\"solditemparam\",\"labelValueList\":[],\"queryText\":\"SELECT * FROM " +
                "bvd_lwr_demo limit 12\",\"selectedDate\":\"specificDate\",\"selectedoption\":\"novalue\",\"variableName\":\"solditem\",\"value\":\"\",\"availableColumns\":" +
                "[\"ID\",\"LOCATION\",\"TIMESTAMP\",\"sold_item\"],\"sampleQueryResult\":{\"ID\":1,\"LOCATION\":\"Boeblingen\",\"TIMESTAMP\":" +
                "\"2017-01-01T09:00:00.000Z\",\"sold_item\":9},\"selectedColumn\":{\"icon\":\"\",\"id\":\"sold_item\",\"label\":\"sold_item\"},"+
                "\"selectedColumnValue\":{\"icon\":\"\",\"id\":\"sold_item\",\"label\":\"sold_item\"}},\"type\":\"param\",\"name\":\"solditemparam (solditem)\"}}";
    }

    private String parameterdateTime() {
        return "{\"dataCollector\":{\"active\":true,\"data\":{\"resultFormat\":\"unchanged\"," +
                "\"tags\":[],\"dims\":[],\"paramQueryType\":\"date\",\"displayName\":\"parameter_dateTimeparam\",\"labelValueList\":[],\"queryText\":\"\"" +
                ",\"selectedDate\":\"specificDate\",\"selectedoption\":\"novalue\",\"variableName\":\"parameter_dateTime\",\"value\":\"\",\"availableColumns\":" +
                "[],\"selectedColumn\":\"\",\"selectedColumnValue\":\"\"},\"type\":\"param\",\"name\":\"parameter_dateTimeparam (parameter_dateTime)\"}}";
    }

    private String TimeTestparam() {
        return "{\"dataCollector\":{\"active\":true,\"data\":{\"resultFormat\":\"unchanged\"," +
                "\"tags\":[],\"dims\":[],\"paramQueryType\":\"valueList\",\"displayName\":\"TimeTestparam\",\"labelValueList\":[{\"label\":\"Day\",\"value\":\"1\"},"+
                "{\"label\":\"Week\",\"value\":\"7\"},{\"label\":\"Month\",\"value\":\"30\"},{\"label\":\"Year\",\"value\":\"365\"}],\"queryText\":\"\"" +
                ",\"selectedDate\":\"specificDate\",\"selectedoption\":\"customvalue\",\"variableName\":\"TimeTest\",\"value\":\"9125\",\"availableColumns\":" +
                "[],\"sampleQueryResult\":{\"label\":\"Day\",\"Value\":\"1\"},\"selectedColumn\":\"\","+
                "\"selectedColumnValue\":\"\"},\"type\":\"param\",\"name\":\"TimeTestparam (TIMETEST)\"}}";
    }




    @BeforeClass
    public void setup() {
        cfg = new TestConfig().getConfig();
        BROWSER = cfg.getString("application.bvd.browser.name");
        BVD_USR = cfg.getString("application.bvd.user.adminusr");
        BVD_PWD = cfg.getString("application.bvd.user.adminpwd");
        URL = cfg.getString("application.bvd.url.external");
        sDashboard = sSourceDashboard + "_" + BROWSER;
        sDashboardPath = "C:\\tmp\\" + sDashboard + ".svg";
        sDashboard1 = sSourceDashboard1 + "_" + BROWSER;
        sDashboardPath1 = "C:\\tmp\\" + sDashboard1 + ".svg";
        hyperlinkurl=URL+"/#/show/DrilldownParams1_chrome?params=parameter_dateTime:start%3D%202017-01-28%2008:00%20%23%23%20parameter"+
                "_dateTimeparam%20:start%20%23%23%20parameter_dateTimeparam%20:start%20%23%23%20parameter_dateTimeparam%20:start%20%23%23%202017"+
                "-01-28%2008:00;parameter_dateTime:end%3D%202017-01-30%2008:00%20%23%23%20parameter_dateTimeparam%20:end%20%23%23%20parameter_"+
                "dateTimeparam%20:end%20%23%23%20parameter_dateTimeparam%20:end%20%23%23%202017-01-30%2008:00;TIMETEST%3D%207%20%23%23%20"+
                "TimeTestparam%20(TIMETEST)%20%23%23%20undefined%20%23%23%20undefined%20%23%23%20Week";
        hyperlinkurlwithoutbaseurl="/show/DrilldownParams1_chrome?params=TIMETEST%3D%207%20%23%23%20TIMETEST%20%23%23%20undefined%20%23%23%20undefined"+
                "%20%23%23%20Week;id%3D%209%20%23%23%20id%20%23%23%20ID%20%23%23%20ID%20%23%23%209";

        hyperlinkurlwithoutbaseurlfordashboard="TIMETEST%3D%201%20%23%23%20TIMETEST%20%23%23%20undefined%20%23%23%20undefined%20%23%23%20Day;id"+
                 "%3D%205%20%23%23%20id%20%23%23%20ID%20%23%23%20ID%20%23%23%205";
        driver = UiTestWebDriver.getWebDriver();
        driver.get(URL);
        initPages();
        try {
            client = new BvdWsClient(BVD_USR, BVD_PWD, PROXY_HOST, PROXY_PORT);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test(priority = 10)
    public void createRole() {
        result = client.userWS.createRole(roleName, desc, category, accessType);
        Result.rcEquals(result, "0");
        roleId = result.get("out");
    }

    @Test(priority = 20)
    public void idmlogin() {
        result = loginPage.login(BVD_USR, BVD_PWD);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 30)
    public void login_check() {
        result = loginPage.idm_LoggedIn_Check();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 35)
    public void createDrilldownDatabaseQuery() throws UnrecoverableKeyException,
            NoSuchAlgorithmException, KeyStoreException, JSONException, KeyManagementException {
        result = client.collectorWS.bvdDataCollectorCreateQuery(DrilldownDatabaseQuery());
        Result.rcEquals(result, "0");
    }

    @Test(priority = 40)
    public void createDrilldownDatedataquery() throws UnrecoverableKeyException,
            NoSuchAlgorithmException, KeyStoreException, JSONException, KeyManagementException {
        result = client.collectorWS.bvdDataCollectorCreateQuery(DrilldownDatedataquery());
        Result.rcEquals(result, "0");
    }

    @Test(priority = 50)
    public void createDrilldowndownvaluedataquery() throws UnrecoverableKeyException,
            NoSuchAlgorithmException, KeyStoreException, JSONException, KeyManagementException {
        result = client.collectorWS.bvdDataCollectorCreateQuery((Drilldowndownvaluedataquery()));
        Result.rcEquals(result, "0");
    }

    @Test(priority = 60)
    public void createDrilldownidparamid() throws UnrecoverableKeyException,
            NoSuchAlgorithmException, KeyStoreException, JSONException, KeyManagementException {
        result = client.collectorWS.bvdDataCollectorCreateQuery(idparamid());
        Result.rcEquals(result, "0");
    }

    @Test(priority = 70)
    public void createDrilldownsolditemparamsolditem() throws UnrecoverableKeyException,
            NoSuchAlgorithmException, KeyStoreException, JSONException, KeyManagementException {
        result = client.collectorWS.bvdDataCollectorCreateQuery(solditemparamsolditem());
        Result.rcEquals(result, "0");
    }

    @Test(priority = 80)
    public void createDrilldowndparameterdateTime() throws UnrecoverableKeyException,
            NoSuchAlgorithmException, KeyStoreException, JSONException, KeyManagementException {
        result = client.collectorWS.bvdDataCollectorCreateQuery(parameterdateTime());
        Result.rcEquals(result, "0");
    }

    @Test(priority = 85)
    public void createDrilldownTimeTestparam() throws UnrecoverableKeyException,
            NoSuchAlgorithmException, KeyStoreException, JSONException, KeyManagementException {
        result = client.collectorWS.bvdDataCollectorCreateQuery(TimeTestparam());
        Result.rcEquals(result, "0");
    }

    @Test(priority = 90)
    public void copyTestFiles() {
        result = TestUtils.downloadResource(this.getClass(), sSourceDashboard + ".svg", sDashboardPath);
        Result.rcEquals(result, "0");

        result = TestUtils.downloadResource(this.getClass(), sSourceDashboard1 + ".svg", sDashboardPath1);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 100)
    public void uploadDashboard() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");

        result = manageDashboardsPage.uploadDashboard(sDashboardPath);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 110)
    public void saveDashboard() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 120)
    public void uploadDashboard1() {
        result = manageDashboardsPage.uploadDashboard(sDashboardPath1);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 125)
    public void modifyMutiSeriesBarDashboard2() {

        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");

        result = editDashboardPage.setHyperlinkToAnotherDashboard(sDashboard);
        Result.rcEquals(result, "0");

    }

    @Test(priority = 127)
    public void modifyanyshapewidget() {
        result = editDashboardPage.selectWidget(sWidgetanyshapetextwidgetDashboard2);
        Result.rcEquals(result, "0");

        result = editDashboardPage.setHyperlinkToUrl(URL + "/#/show/Welcome?params=none");
        Result.rcEquals(result, "0");

    }

    @Test(priority = 130)
    public void saveDashboard1() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 140)
    public void viewDashboard1() {
        result = mainPage.viewDashboard(sDashboard1);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 150)
    public void openSlideout() {
        result = mainPage.clickOnLeftButton();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 160)
    public void setTestParam() {
        result = mainPage.ParameterValueForTextWidgetnew("TimeTestparam","Week");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 170)
    public void setIdparam(){
        result = mainPage.ParameterValueForTextWidgetnew("idparam","9");
        Result.rcEquals(result, "0");
    }


    @Test(priority = 180)
    public void applytoDrilldownparams1(){
        result = mainPage.leftBarParameterApplyValue();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 190)
    public void geturlforusingasHyperlink()

    {
        TestUtils.sleep(5);
        String urlforHyperlink = driver.getCurrentUrl();
        hyperlinkurlafterbase=urlforHyperlink.substring(urlforHyperlink.indexOf("#")+1);
    }

    @Test(priority = 200)
    public void openmanageDashboard()
    {
        result=mainPage.openManageDashboards();
        Result.rcEquals(result,"0");
    }


    @Test(priority = 210)
    public void editDashboard() {
        result =manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result,"0");
    }

    @Test(priority = 220)
    public void selectWidgetforanotherdashboard() {
        result = editDashboardPage.selectWidget(shyperlinkWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 230)
    public void setHyperlinkToAnotherDashboard() {
        result = editDashboardPage.setHyperlinkToAnotherDashboard_passparams(sLinkToDashboard,true);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 240)
    public void selectWidgetforurl() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 250)
    public void setHyperlinkToAnotherDashboardthroughurl() {
        result = editDashboardPage.setHyperlinkToUrl("/#"+hyperlinkurlafterbase);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 251)
    public void saveDashboardafteraddingHyperlinkgroup109() {
        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 252)
    public void selectWidgetforsecondurl() {
        result = editDashboardPage.selectWidget(sTextValueWidget);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 255)
    public void setHyperlinktourlsecond() {
        result = editDashboardPage.setHyperlinkToUrl("#/show/DrilldownParams1_chrome?params=id=#{ID}");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 260)
    public void saveDashboardafteraddingHyperlink() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 270)
    public void viewDashboard()
    {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 280)
    public void checkHyperlinkToUrl() {
        result = mainPage.clickOnHyperLink(HyperLinkforURLwidget);
        Result.rcEquals(result, "0");

    }

    @Test(priority = 290)
    public void geturlofthelinkedurlpage() {
        String url = driver.getCurrentUrl();
        String str=url.substring(url.indexOf("#")+1);
        Assert.assertEquals(str,hyperlinkurlwithoutbaseurl);
    }

    @Test(priority = 300)
    public void clickOnLeftBarButtontovalidateparamsafterredirecting() {
        TestUtils.sleep(3);
        result = mainPage.clickOnLeftButton();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 320)
    public void readtheparamsvalueafterredirectingthroughurl() {
        result = mainPage.readslideoutfieldValues(paramfieldvaluesafterlinktourl,fieldparams);
        Result.rcEquals(result, "0");
    }



    @Test(priority = 340)
    public void verifyingTextofDateforLinkedDashboardbyUrl() {
        result = mainPage.verifyingTextForDate(id,sTextFieldsFirst,sTextWidgetshapefordate);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 360)
    public void validateGroupWidgetValues()
    {

        textElemsInWidgetwotitleid.add("9");
        textElemsInWidgetwoTitlesolditem.add("2");

        Assert.assertEquals(mainPage.getGroupWidgetCount(), 1);

        result = mainPage.compareTextElementsInTextWidgetWithOutTitle(textElemsInWidgetwotitleid,"shape219");
        Result.rcEquals(result, "0");

        result = mainPage.compareTextElementsInTextWidgetWithOutTitle(textElemsInWidgetwoTitlesolditem,"shape220");
        Result.rcEquals(result, "0");

        textElemsInWidgetwotitleid.remove(0);
        textElemsInWidgetwoTitlesolditem.remove(0);
    }

    @Test(priority = 370)
    public void validatedashboardname() {
        TestUtils.sleepAndLog(2000);
        // string compare
        if (driver.getTitle().contains(sLinkToDashboard)) {
            result = ResultStore.success("Required Dashboard "+sLinkToDashboard+" is displayed");
        } else {
            result = ResultStore.failure("Required Dashboard "+sLinkToDashboard+" is not displayed and instead " + result.get("out") + " is shown");
        }
        Result.rcEquals(result, "0");

    }


    @Test(priority = 380)
    public void closeslideout() {

        result = mainPage.closeSlideout();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 390)
    public void backforHyperlinkurl2()
    {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }



    @Test(priority = 400)
    public void checkSHyperlinkurl2() {
        result = mainPage.clickOnHyperLink(HyperLinkforURLwidget);
        Result.rcEquals(result, "0");

    }

    @Test(priority = 410)
    public void geturloftheHyperlinkurl2page() {
        String url = driver.getCurrentUrl();
        String str=url.substring(url.indexOf("#")+1);
        Assert.assertEquals(str,hyperlinkurlwithoutbaseurl);
    }

    @Test(priority = 420)
    public void clickOnLeftBarButtontovalidateparamsafterredirectingHyperlinkurl2() {
        TestUtils.sleep(3);
        result = mainPage.clickOnLeftButton();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 430)
    public void readtheparamsvalueafterredirectingthroughHyperlinkurl2() {
        result = mainPage.readslideoutfieldValues(paramfieldvaluesafterlinktourl,fieldparams);
        Result.rcEquals(result, "0");
    }



    @Test(priority = 440)
    public void verifyingTextofDateforLinkedDashboardbyHyperlinkurl2() {
        result = mainPage.verifyingTextForDate(id,sTextFieldsFirst,sTextWidgetshapefordate);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 460)
    public void validateGroupWidgetValuesHyperlinkurl2()
    {

        textElemsInWidgetwotitleid.add("9");
        textElemsInWidgetwoTitlesolditem.add("2");

        Assert.assertEquals(mainPage.getGroupWidgetCount(), 1);

        result = mainPage.compareTextElementsInTextWidgetWithOutTitle(textElemsInWidgetwotitleid,"shape219");
        Result.rcEquals(result, "0");

        result = mainPage.compareTextElementsInTextWidgetWithOutTitle(textElemsInWidgetwoTitlesolditem,"shape220");
        Result.rcEquals(result, "0");

        textElemsInWidgetwotitleid.remove(0);
        textElemsInWidgetwoTitlesolditem.remove(0);
    }

    @Test(priority = 470)
    public void validatedashboardnameHyperlinkurl2() {
        TestUtils.sleepAndLog(2000);
        if (driver.getTitle().contains(sLinkToDashboard)) {
            result = ResultStore.success("Required Dashboard "+sLinkToDashboard+" is displayed");
        } else {
            result = ResultStore.failure("Required Dashboard "+sLinkToDashboard+" is not displayed and instead " + result.get("out") + " is shown");
        }
        Result.rcEquals(result, "0");

    }


    @Test(priority = 480)
    public void closeslideoutHyperlinkurl2() {

        result = mainPage.closeSlideout();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 490)
    public void back()
    {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 500)
    public void clickOnLeftBarButton() {
        result = mainPage.clickOnLeftButton();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 520)
    public void clickOnLeftBarParameterValue() {
        result = mainPage.ParameterValueForTextWidgetnew("TimeTestparam","Day");
        Result.rcEquals(result, "0");

        result = mainPage.ParameterValueForTextWidgetnew("idparam","5");
        Result.rcEquals(result, "0");

    }

    @Test(priority = 530)
    public void clickOnLeftBarParameterApplyValue() {
        result = mainPage.leftBarParameterApplyValue();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 540)
    public void verifyingTextAfterApplyingDatachannel() {
        TestUtils.sleep(4);
        result = mainPage.verifyingTextForDate(id,sTextFieldsFirst,sTextWidgetshapefordate);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 550)
    public void validateGroupWidgetValuesforLinkedDashboard()
    {

        textElemsInWidgetwotitleid.add("5");
        textElemsInWidgetwoTitlesolditem.add("7");

        Assert.assertEquals(mainPage.getGroupWidgetCount(), 1);

        result = mainPage.compareTextElementsInTextWidgetWithOutTitle(textElemsInWidgetwotitleid,"shape219");
        Result.rcEquals(result, "0");

        result = mainPage.compareTextElementsInTextWidgetWithOutTitle(textElemsInWidgetwoTitlesolditem,"shape220");
        Result.rcEquals(result, "0");

        textElemsInWidgetwotitleid.remove(0);
        textElemsInWidgetwoTitlesolditem.remove(0);
    }


    @Test(priority = 570)
    public void checkHyperlinkToAnotherDashboard() {
        result=mainPage.clickOnHyperLink("g#group8 text");
        Result.rcEquals(result, "0");

    }

    @Test(priority = 580)
    public void validatedashboardnameafetrHyperlinkToDashboard() {
        TestUtils.sleepAndLog(2000);
        // string compare
        if (driver.getTitle().contains(sLinkToDashboard)) {
            result = ResultStore.success("Required Dashboard "+sLinkToDashboard+" is displayed");
        } else {
            result = ResultStore.failure("Required Dashboard "+sLinkToDashboard+" is not displayed and instead " + result.get("out") + " is shown");
        }
        Result.rcEquals(result, "0");

    }

    @Test(priority = 590)
    public void geturlofthelinkeddashboardpage() {
        String url = driver.getCurrentUrl();
        String str=url.substring(url.indexOf("=")+1);
        Assert.assertEquals(str,hyperlinkurlwithoutbaseurlfordashboard);
    }

    @Test(priority = 600)
    public void clickOnLeftBarButtontovalidateparamsafterredirectingtodashboard() {
        result = mainPage.clickOnLeftButton();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 610)
    public void readtheparamsvalueafterredirectingthroughdashboard() {
        result = mainPage.readslideoutfieldValues(paramfieldvaluesafterlinktodashboard,fieldparams);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 620)
    public void verifyingTextofDateforLinkedDashboard() {
        result = mainPage.verifyingTextForDate(id,sTextFieldsFirst,sTextWidgetshapefordate);
        Result.rcEquals(result, "0");
    }



    @Test(priority = 635)
    public void closeSlideOutDashboard2() {
        result = mainPage.closeSlideout();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 640)
    public void clickonHyperLinkdashboard() {
        result = mainPage.clickOnHyperLink(HyperLinkforURLwidget);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 650)
    public void validateGroupWidgetforLinkedDashboard() {
        TestUtils.sleep(5);
        Assert.assertEquals(mainPage.getGroupWidgetCount(), 4);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 660)
    public void validateURLforParamsOffOption() {

        String url = driver.getCurrentUrl();
        Assert.assertEquals(url,URL+"/#/show/DrilldownParams_chrome?params=none");
    }

    @Test(priority = 670)
    public void navigatetoDashboard1()
    {
        result = mainPage.viewDashboard(sDashboard1);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 700)
    public void dashboard2linkedtoPramsnone() {
        TestUtils.sleep(5);
        result = mainPage.clickOnHyperLink("g#group8 text");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 710)
    public void validateurlforpramsnone() {
        TestUtils.sleep(4);
        String url = driver.getCurrentUrl();
        Assert.assertEquals(url,URL+"/#/show/Welcome?params=none");
    }

    @Test(priority = 720)
    public void validatedashboardnameafetrHyperlinkToDashboardparamsnone() {
        TestUtils.sleepAndLog(2000);
        // string compare
        if (driver.getTitle().contains("Welcome")) {
            result = ResultStore.success("Required Dashboard Welcome is displayed");
        } else {
            result = ResultStore.failure("Required Dashboard Welcome is not displayed and instead " + result.get("out") + " is shown");
        }
        Result.rcEquals(result, "0");

    }

    @Test(priority = 730)
    public void viewDashboardforGroupWidget()
    {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 740)
    public void clickOnLeftBarButtonforGroupWidget() {
        result = mainPage.clickOnLeftButton();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 750)
    public void clickOnLeftBarParameterValueforGroupWidget() {

        result = mainPage.ParameterValueForTextWidgetnew("idparam","4");
        Result.rcEquals(result, "0");

    }

    @Test(priority = 760)
    public void clickOnLeftBarParameterApplyValueforGroupWidget() {
        result = mainPage.leftBarParameterApplyValue();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 770)
    public void validateDataDisplay() {

        TestUtils.sleep(4);
        textElemsInWidgetwotitleid.add("4");
        textElemsInWidgetwoTitlesolditem.add("9");

        result = mainPage.compareTextElementsInTextWidgetWithOutTitle(textElemsInWidgetwotitleid,"shape219");
        Result.rcEquals(result, "0");

        result = mainPage.compareTextElementsInTextWidgetWithOutTitle(textElemsInWidgetwoTitlesolditem,"shape220");
        Result.rcEquals(result, "0");

    }

    @Test(priority = 780)
    public void navigatetoURL() {
      result=mainPage.clickOnHyperLink("g[id$='-shape219'] text");
      Result.rcEquals(result, "0");

    }

    @Test(priority = 790)
    public void navigatetoURLValidate() {
        TestUtils.sleepAndLog(4000);
        // string compare
        if (driver.getTitle().contains("DrilldownParams1_chrome")) {
            result = ResultStore.success("Required Dashboard DrilldownParams1_chrome is displayed");
        } else {
            result = ResultStore.failure("Required Dashboard DrilldownParams1_chrome is not displayed and instead " + result.get("out") + " is shown");
        }
        Result.rcEquals(result, "0");
    }


    @Test(priority = 800)
    public void validateDashboard() {
        TestUtils.sleepAndLog(2000);
        result = mainPage.compareTextElementsInTextWidgetWithOutTitle(textElemsInWidgetwotitleid,"shape219");
        Result.rcEquals(result, "0");

        result = mainPage.compareTextElementsInTextWidgetWithOutTitle(textElemsInWidgetwoTitlesolditem,"shape220");
        Result.rcEquals(result, "0");

        Assert.assertEquals(mainPage.getGroupWidgetCount(), 1);
    }

    @Test(priority = 810)
    public void clickLeftSlideOutvalidateApplyBtnOnClickRESET() {
        result = mainPage.clickOnLeftButton();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 820)
    public void clickRESETvalidateApplyBtnOnClickRESET() {
        result = mainPage.leftBarParameterRESETbutton();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 830)
    public void validateApplyBtnvalidateApplyBtnOnClickRESET() {
        TestUtils.sleep(3);
        result = mainPage.barCount(sWidget,4);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 840)
    public void validateReApply() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");

        TestUtils.sleep(4);
        result = mainPage.clickOnLeftButton();
        Result.rcEquals(result, "0");

        result = mainPage.ParameterValueForTextWidgetnew("idparam","2");
        Result.rcEquals(result, "0");

        result = mainPage.leftBarParameterApplyValue();
        Result.rcEquals(result, "0");

        result=mainPage.clickOnHyperLink("g[id$='-shape219'] text");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 850)
    public void navigatetoURLValidateNewURL() {
        TestUtils.sleepAndLog(4000);
        // string compare
        if (driver.getTitle().contains("DrilldownParams1_chrome")) {
            result = ResultStore.success("Required Dashboard DrilldownParams1_chrome is displayed");
        } else {
            result = ResultStore.failure("Required Dashboard DrilldownParams1_chrome is not displayed and instead " + result.get("out") + " is shown");
        }
        Result.rcEquals(result, "0");
    }

    @Test(priority = 860)
    public void clickLeftSlideOutvalidateApplyBtn() {
        result = mainPage.clickOnLeftButton();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 870)
    public void selectParamValueandValidateApplyEnabled() {
        result = mainPage.ParameterValueForTextWidgetnew("idparam","5");
        Result.rcEquals(result, "0");

        result = mainPage.leftBarParameterApplyValue();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 880)
    public void validateDataDisplayafterSelectParamValue() {

        TestUtils.sleep(4);
        textElemsInWidgetwotitleid.remove(0);
        textElemsInWidgetwoTitlesolditem.remove(0);
        textElemsInWidgetwotitleid.add("5");
        textElemsInWidgetwoTitlesolditem.add("7");

        result = mainPage.compareTextElementsInTextWidgetWithOutTitle(textElemsInWidgetwotitleid,"shape219");
        Result.rcEquals(result, "0");

        result = mainPage.compareTextElementsInTextWidgetWithOutTitle(textElemsInWidgetwoTitlesolditem,"shape220");
        Result.rcEquals(result, "0");

    }



    @Test(priority = 1000)
    public void deleteDashboard() {
        result = client.dashboardWS.deleteDashboard(sDashboard);
        Result.rcEquals(result, "0");
        result = client.dashboardWS.deleteDashboard(sDashboard1);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 1010)
    public void deleteRole()
    {
        result = client.userWS.deleteRole(roleId);
        Result.rcEquals(result,"0");
    }


    @AfterClass
    public void cleanup() throws UnrecoverableKeyException, NoSuchAlgorithmException,
            KeyStoreException, JSONException, KeyManagementException
    {
        client.collectorWS.deleteAllExistQueries();
        driver.quit();
    }

}
