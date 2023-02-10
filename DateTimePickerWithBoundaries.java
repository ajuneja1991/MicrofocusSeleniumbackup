package com.hp.opr.qa.tests.sys.ui.bvd;

import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.pageobjects.bvd.*;
import com.mf.opr.qa.framework.TestConfig;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.utils.TestUtils;
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
import java.util.List;
import java.util.Map;

public class DateTimePickerWithBoundaries {

    private LoginPage loginPage;
    private MainPage mainPage;
    private ManageDashboardsPage manageDashboardsPage;
    private EditDashboardPage editDashboardPage;
    public SystemSettingsPage systemSettingsPage;
    private WebDriver driver;
    private Map<String, String> result;
    private BvdWsClient client;
    private String BVD_USR = "";
    private String BVD_PWD = "";
    private String sDashboard="";
    private String sDashboardPath="";
    private String URL = "";
    private Config cfg;
    private String PROXY_HOST = "";
    private String BROWSER = "";
    private String sSourceDashboard = "TimeSeriesBaseDashboard";
    private String sDataChannel="";
    private String sDataChanneldefault="";
    private String sWidgetstartdate="shape193";
    private String sWidgetenddate="shape188";
    private String sWidget="group1";
    private int PROXY_PORT;
    String roleId;
    private String roleName = "roleDateTimePicker";
    private String desc = "For datetimepicker";
    private String category = "All";
    private String accessType = "full-control";
    private String Descparam="DateTimepicker";
    private String userName = "DateTimePickerTest";
    private String userLogin;
    private String sStartDate="1/1/2022, 12:00:00 AM";
    private String sStartDateWidget="shape193";
    private String sEndDateWidget="shape188";
    private String sEndDate="12/31/2022, 11:59:00 PM";
    private String userPwd = "Test@123";
    List<String> paramfieldvalues=Arrays.asList("Last Year");
    List<String> fieldparams=Arrays.asList("parameter_dateTimeparam (parameter_dateTime)");
    private List<String> admindropdownoptions = Arrays.asList("Dashboards", "Resources");
    private List<String> sDataFields=Arrays.asList("data1");
    private static String OS="";
    private List<String> dataPointDefault=Arrays.asList("svg path[d^='M 6']",
            "svg path[d^='M 10']",
            "svg path[d^='M 15']",
            "svg path[d^='M 19']",
            "svg path[d^='M 26']",
            "svg path[d^='M 28']");

    private List<String> dataPointLastYear=Arrays.asList("svg path[d^='M 4']",
            "svg path[d^='M 9']",
            "svg path[d^='M 14']",
            "svg path[d^='M 19']",
            "svg path[d^='M 26']",
            "svg path[d^='M 29']");

    @BeforeClass
    public void setup() {
        cfg = new TestConfig().getConfig();
        BROWSER = cfg.getString("application.bvd.browser.name");
        BVD_USR = cfg.getString("application.bvd.user.adminusr");
        BVD_PWD = cfg.getString("application.bvd.user.adminpwd");
        URL = cfg.getString("application.bvd.url.external");
        driver = UiTestWebDriver.getWebDriver();
        OS = System.getProperty("os.name").toLowerCase();
        driver.get(URL);
        sDataChanneldefault="DateTimePickerdefault";
        sDataChannel="DateTimePicker";
        sDashboard = sSourceDashboard + "_DateTimePicker_" + BROWSER;
        sDashboardPath = "C:\\tmp\\" + sDashboard + ".svg";
        initPages();
        if(OS.contains("win")){
            sDashboardPath = new String("C:\\tmp\\" + sDashboard + ".svg");
        } else {
            sDashboardPath = new String("/tmp/" + sDashboard + ".svg");
        }
        try {
            client = new BvdWsClient(BVD_USR, BVD_PWD, PROXY_HOST, PROXY_PORT);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initPages() {
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        mainPage = PageFactory.initElements(driver, MainPage.class);
        manageDashboardsPage = PageFactory.initElements(driver, ManageDashboardsPage.class);
        editDashboardPage = PageFactory.initElements(driver, EditDashboardPage.class);
        systemSettingsPage = PageFactory.initElements(driver, SystemSettingsPage.class);
    }

    private String DateTimepickerDatabaseQuery() {
        return "{\"dataCollector\":{\"active\":true,\"data\":{\"resultFormat\":\"unchanged\"," +
                "\"tags\":[],\"dims\":[],\"queryText\":\"select * from datetimepicker " +
                "where ${timestamp > ${parameter_dateTime:start}} and ${timestamp < ${parameter_dateTime:end}}\",\"availableColumns\":" +
                "[\"ID\",\"TIMESTAMP\",\"DATA1\",\"DATA2\"],\"sampleQueryResult\":{\"id\":1,\"timestamp\":\"2019-01-05T10:00:00.000Z\",\"data1\":" +
                "10,\"data2\":90}},\"name\":\"DateTimePicker\"}}";
    }

    private String parameterdateTime() {
        return "{\"dataCollector\":{\"active\":true,\"data\":{\"resultFormat\":\"unchanged\"," +
                "\"tags\":[],\"dims\":[],\"paramQueryType\":\"date\",\"predefinedValue\":\"Last Year\",\""+
                "displayName\":\"parameter_dateTimeparam\",\"labelValueList\":[],\"queryText\":\"\"" +
                ",\"selectedDate\":\"specificDate\",\"selectedoption\":\"customvalue\",\"variableName\":\"parameter_dateTime\",\"value\":\"YEAR\",\"availableColumns\":" +
                "[],\"selectedColumn\":\"\",\"selectedColumnValue\":\"\"},\"type\":\"param\",\"name\":\"parameter_dateTimeparam (parameter_dateTime)\"}}";
    }


    private String DateTimepickerDatabaseQuerydefault() {
        return "{\"dataCollector\":{\"active\":true,\"data\":{\"resultFormat\":\"unchanged\"," +
                "\"tags\":[],\"dims\":[],\"queryText\":\"select * from datetimepicker " +
                "where ${timestamp > ${parameter_dateTimedefault:start}} and ${timestamp < ${parameter_dateTimedefault:end}}\",\"availableColumns\":" +
                "[\"ID\",\"TIMESTAMP\",\"DATA1\",\"DATA2\"],\"sampleQueryResult\":{\"id\":1,\"timestamp\":\"2019-01-05T10:00:00.000Z\",\"data1\":" +
                "10,\"data2\":90}},\"name\":\"DateTimePickerdefault\"}}";
    }
    private String parameterdateTimeDefault() {
        return "{\"dataCollector\":{\"active\":true,\"data\":{\"resultFormat\":\"unchanged\"," +
                "\"tags\":[],\"dims\":[],\"paramQueryType\":\"date\",\"displayName\":\"parameter_dateTimeparamdefault\",\"labelValueList\":[],\"queryText\":\"\"" +
                ",\"selectedDate\":\"specificDate\",\"selectedoption\":\"novalue\",\"variableName\":\"parameter_dateTimedefault\",\"value\":\"\",\"availableColumns\":" +
                "[],\"selectedColumn\":\"\",\"selectedColumnValue\":\"\"},\"type\":\"param\",\"name\":\"parameter_dateTimeparamdefault (parameter_dateTimedefault)\"}}";
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

    public void copyTestFiles() {
        result = TestUtils.downloadResource(this.getClass(), sSourceDashboard + ".svg", sDashboardPath);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 40)
    public void createDateTimepickerDatabaseQuery() throws UnrecoverableKeyException,
            NoSuchAlgorithmException, KeyStoreException, JSONException, KeyManagementException {
        result = client.collectorWS.bvdDataCollectorCreateQuery(DateTimepickerDatabaseQuery());
        Result.rcEquals(result, "0");
    }

    @Test(priority = 45)
    public void createDateTimepickerDatabaseQueryDefault() throws UnrecoverableKeyException,
            NoSuchAlgorithmException, KeyStoreException, JSONException, KeyManagementException {
        result = client.collectorWS.bvdDataCollectorCreateQuery(DateTimepickerDatabaseQuerydefault());
        Result.rcEquals(result, "0");
    }

    @Test(priority = 50)
    public void createDateTimepickerDateparamquery() throws UnrecoverableKeyException,
            NoSuchAlgorithmException, KeyStoreException, JSONException, KeyManagementException {
        result = client.collectorWS.bvdDataCollectorCreateQuery(parameterdateTime());
        Result.rcEquals(result, "0");
    }

    @Test(priority = 55)
    public void createDateTimepickerDateparamquerydefault() throws UnrecoverableKeyException,
            NoSuchAlgorithmException, KeyStoreException, JSONException, KeyManagementException {
        result = client.collectorWS.bvdDataCollectorCreateQuery(parameterdateTimeDefault());
        Result.rcEquals(result, "0");
    }

    @Test(priority = 57)
    public void openSystemSettings() {
        result = mainPage.openSystemSettings();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 58)
    public void setSystemTimeZoneInUI() {
        result = systemSettingsPage.setSystemTimeZoneUI("UTC");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 68)
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

        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 100)
    public void selectWidgetstartdate() {
        result = editDashboardPage.selectWidget(sWidgetstartdate);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 110)
    public void setDataChannelstartdate() {
        result = editDashboardPage.setDataChannel(sDataChannel);
        Result.rcEquals(result, "0");

        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");

        result = editDashboardPage.setDataFieldMultiSelectTypeText(sDataFields);
        Result.rcEquals(result, "0");

        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 120)
    public void selectWidgetenddate() {
        result = editDashboardPage.selectWidget(sWidgetenddate);
        Result.rcEquals(result, "0");

    }

    @Test(priority = 130)
    public void setDataChannelenddate() {
        result = editDashboardPage.setDataChannel(sDataChannel);
        Result.rcEquals(result, "0");

        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");

        result = editDashboardPage.setDataFieldMultiSelectTypeText(sDataFields);
        Result.rcEquals(result, "0");

        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 140)
    public void selectWidgetdatetime() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 160)
    public void setDataField() {
        result = editDashboardPage.setDataFieldMultiSelectTypeText(sDataFields);
        Result.rcEquals(result, "0");

    }

    @Test(priority = 170)
    public void setTimeSpan() {
        result = editDashboardPage.setTimeSpanInMinutes("0");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 180)
    public void saveUploadDashboard() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority=190)
    public void logoutcheck()
    {
        result = mainPage.logout();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 200)
    public void testLoginnonadmin()
    {
        driver.get(URL);
        result = loginPage.login(userName, userPwd);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 210)
    public void login_checkfornonadmin()
    {
        result = loginPage.idm_LoggedIn_Checkfornonadmin();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 220)
    public void viewDashboard() {

        boolean value = mainPage.checkIfDashboardExistsInMainPage(sDashboard);
        if(!value){
            result = mainPage.viewDashboard(sDashboard);
            Result.rcEquals(result, "0");
        }

    }

    @Test(priority = 225)
    public void validateStartandEndDate() {
        TestUtils.sleep(4);
        result = mainPage.getTextOfTextValueWidget(sStartDateWidget);
        Result.outEquals(result, sStartDate);

        result = mainPage.getTextOfTextValueWidget(sEndDateWidget);
        Result.outEquals(result, sEndDate);

    }


    @Test(priority = 230)
    public void validateDefaultYearValue() {
        result = mainPage.barCount(sWidget,9);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 240)
    public void clickOnLeftBarButton() {
        result = mainPage.clickOnLeftButton();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 245)
    public void checkApplyResetbuttonStatus() {
        result = mainPage.applyandResetbtnStatuswhenViewDashboard1stTime();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 250)
    public void clickOnCalendarforLastOneHour() {
        result = mainPage.selectdateTimepickercalendar();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 260)
    public void clickOnLastOneHourLink() {
        result = mainPage.selectrelativeTimefromDatePicker("1 Hour","Last 1 hour");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 270)
    public void clickOnCalendartoValidateLastOnehour() {
        result = mainPage.selectdateTimepickercalendar();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 280)
    public void ValidateLastOneHour() {
        result = mainPage.validaterelativeTime("1 Hour",-1, "UTC");
        Result.rcEquals(result, "0");
    }


    @Test(priority = 290)
    public void clickOnCalendarforLastTwoHours() {
        result = mainPage.selectdateTimepickercalendar();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 300)
    public void clickOnLastTwoHoursLink() {
        result = mainPage.selectrelativeTimefromDatePicker("2 Hours","Last 2 Hours");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 310)
    public void clickOnCalendartoValidateLastTwoHours() {
        result = mainPage.selectdateTimepickercalendar();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 320)
    public void ValidateLastTwoHours() {
        result = mainPage.validaterelativeTime("2 Hours",-2, "UTC");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 330)
    public void clickOnCalendarforLastThreeHours() {
        result = mainPage.selectdateTimepickercalendar();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 340)
    public void clickOnLastThreeHoursLink() {
        result = mainPage.selectrelativeTimefromDatePicker("3 Hours","Last 3 Hours");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 350)
    public void clickOnCalendartoValidateLastThreeHours() {
        result = mainPage.selectdateTimepickercalendar();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 360)
    public void ValidateLastThreeHours() {
        result = mainPage.validaterelativeTime("3 Hours",-3, "UTC");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 370)
    public void clickOnCalendarforLastSixHours() {
        result = mainPage.selectdateTimepickercalendar();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 380)
    public void clickOnLastSixHoursLink() {
        result = mainPage.selectrelativeTimefromDatePicker("6 Hours","Last 6 Hours");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 390)
    public void clickOnCalendartoValidateLastSixHours() {
        result = mainPage.selectdateTimepickercalendar();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 400)
    public void ValidateLastSixHours() {
        result = mainPage.validaterelativeTime("6 Hours",-6, "UTC");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 410)
    public void clickOnCalendarforLastTwelveHours() {
        result = mainPage.selectdateTimepickercalendar();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 420)
    public void clickOnLastTwelveHoursLink() {
        result = mainPage.selectrelativeTimefromDatePicker("12 Hours","Last 12 Hours");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 430)
    public void clickOnCalendartoValidateLastTwelveHours() {
        result = mainPage.selectdateTimepickercalendar();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 440)
    public void ValidateLastTwelveHours() {
        result = mainPage.validaterelativeTime("12 Hours",-12, "UTC");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 515)
    public void clickOnCalendarforLastMonth() {
        result = mainPage.selectdateTimepickercalendar();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 520)
    public void clickOnLastMonthLink() {
        result = mainPage.selectrelativeTimefromDatePickerNew("MONTH","Previous Month");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 530)
    public void clickOnCalendartoValidateLastMonth() {
        result = mainPage.selectdateTimepickercalendar();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 540)
    public void ValidateLastMonth() {
        result = mainPage.validaterelativeTimeDWMY("MONTH", -1);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 545)
    public void clickOnCalendarforLastYear() {
        result = mainPage.selectdateTimepickercalendar();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 550)
    public void clickOnLastYearLink() {
        result = mainPage.selectrelativeTimefromDatePickerNew("YEAR","Previous Year");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 560)
    public void clickOnCalendartoValidateLastYear() {
        result = mainPage.selectdateTimepickercalendar();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 570)
    public void ValidateLastYear() {
        result = mainPage.validaterelativeTimeDWMY("YEAR", -1);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 580)
    public void checkapplybutton() {
        result = mainPage.validateApplyButtondisabled();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 590)
    public void ValidateLastYearData() {
        result = mainPage.barCount(sWidget,9);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 610)
    public void clickOnCalendarforLastMonthNegScenario() {
        result = mainPage.selectdateTimepickercalendar();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 620)
    public void clickOnLastMonthLinkNegScenario() {
        result = mainPage.selectrelativeTimefromDatePickerNew("MONTH","Previous Month");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 630)
    public void validateApplybuttonEnabled() {
        result = mainPage.leftBarParameterApplyValue();
        Result.rcEquals(result, "0");
        TestUtils.sleep(4);
    }

    @Test(priority = 640)
    public void clickOnLeftBarButtonGMT() {
        result = mainPage.clickOnLeftButton();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 650)
    public void checkRESETbutton() {
        result = mainPage.leftBarParameterRESETbutton();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 660)
    public void validateRESETFunctionality() {
        result = mainPage.validateapplyandRestAfterResestClicked("Previous Year");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 670)
    public void validateDefaultData() {
        result = mainPage.barCount(sWidget,9);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 690)
    public void clickOnEditDashboardButton() {
        result = mainPage.clickQuickEditbutton();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 700)
    public void modifyDataChannelforstartdate() {
        result = editDashboardPage.selectWidget(sWidgetstartdate);
        Result.rcEquals(result, "0");

        result = editDashboardPage.modifyDataChannelSetNewChannel(sDataChanneldefault);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 710)
    public void modifyDataChannelforenddate() {
        result = editDashboardPage.selectWidget(sWidgetenddate);
        Result.rcEquals(result, "0");

        result = editDashboardPage.modifyDataChannelSetNewChannel(sDataChanneldefault);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 720)
    public void modifyDataChannelforMultiBarWidgetdefault() {

        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");

        result = editDashboardPage.modifyDataChannelSetNewChannel(sDataChanneldefault);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 730)
    public void applyforDefault() {
        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 740)
    public void setDataFieldforMultiBarDefault() {

        result = editDashboardPage.setDataFieldMultiSelectTypeText(sDataFields);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 750)
    public void saveUploadDashboardDefault() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 755)
    public void viewDashboardDefault() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 760)
    public void clickOnLeftBarButtonDefault() {
        result = mainPage.clickOnLeftButton();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 770)
    public void checkApplybuttondisabledDefault() {
        result = mainPage.validateApplyButtondisabled();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 780)
    public void clickOnCalendarforLastYearDefaultNone() {
        result = mainPage.selectdateTimepickercalendar();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 790)
    public void clickOnLastYearforDefault() {
        result = mainPage.selectrelativeTimefromDatePickerNew("YEAR","Previous Year");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 800)
    public void clickOnCalendarValidateLastYearforDefault() {
        result = mainPage.selectdateTimepickercalendar();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 810)
    public void ValidateLastYearDefault() {
        result = mainPage.validaterelativeTimeDWMY("YEAR", -1);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 820)
    public void clickApplyLastYearAfterDefault() {
        result = mainPage.leftBarParameterApplyValue();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 830)
    public void ValidateLastYearDataAfterDefault() {
        result = mainPage.barCount(sWidget,9);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 2000)
    public void deleteRole()
    {
        result = client.userWS.deleteRole(roleId);
        Result.rcEquals(result,"0");
    }

    @Test(priority = 2010)
    public void deleteDashboard()
    {
        result = client.dashboardWS.deleteDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }


    @AfterClass
    public void cleanup() throws UnrecoverableKeyException, NoSuchAlgorithmException,
            KeyStoreException, JSONException, KeyManagementException
    {
        client.collectorWS.deleteAllExistQueries();
        driver.quit();
    }

}

