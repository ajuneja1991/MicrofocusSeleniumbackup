package com.hp.opr.qa.tests.sys.ui.bvd;

import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.pageobjects.bvd.*;
import com.hp.opr.qa.framework.pageobjects.managementportal.MPortalLoginPage;
import com.hp.opr.qa.framework.pageobjects.managementportal.MPortalMainPage;
import com.hp.opr.qa.framework.pageobjects.managementportal.MPortalOpsBridgePage;
import com.hp.opr.qa.framework.pageobjects.bvd.bvdUserandRolePages.RoleEditor;
import com.hp.opr.qa.framework.pageobjects.bvd.bvdUserandRolePages.GroupList;
import com.hp.opr.qa.framework.pageobjects.bvd.bvdUserandRolePages.GroupEditor;
import com.hp.opr.qa.framework.pageobjects.bvd.bvdUserandRolePages.UsersList;
import com.hp.opr.qa.framework.pageobjects.usermgmt.RoleList;
import com.hp.opr.qa.framework.pageobjects.usermgmt.UsersGroupsAndRolesHomePage;
import com.hp.opr.qa.framework.pageobjects.usermgmt.UsersGroupsAndRolesSideNav;
import com.mf.opr.qa.framework.TestConfig;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.utils.TestUtils;
import com.typesafe.config.Config;
import org.hornetq.utils.json.JSONException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.*;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Map;

public class RBACTest {

    private LoginPage loginPage;
    private MainPage mainPage;
    private ManageDashboardsPage manageDashboardsPage;
    private EditDashboardPage editDashboardPage;
    private DataCollectorPage dataCollectorPage;
    private MPortalMainPage mportalmainpage;
    private MPortalOpsBridgePage mportalopsbridgepage;
    private MPortalLoginPage mportalloginpage;
    private UsersGroupsAndRolesHomePage usersGroupsAndRolesHomePage;
    private UsersGroupsAndRolesSideNav usersGroupsAndRolesSideNav;
    private UserEditorBVD userEditor;
    private GroupEditor groupEditor;
    private RoleEditor roleEditor;
    private UsersList userList;
    private GroupList groupList;
    private RoleList roleList;
    private WebDriver driver;
    private Map<String, String> result;
    private BvdWsClient client;
    private Config cfg;
    private String PROXY_HOST="";
    private String BROWSER="";
    private int PROXY_PORT;
    private String sSourceDashboard ;
    private String sDashboard ;
    private String sDashboardPath;
    private String BVD_USR="";
    private String BVD_PWD="";
    private String URL="";
    private String mportalurl;
    private String userName = "RBACTest";
    private String userPwd = "Test@123";
    private String roleName = "roleRBAC";
    private String desc = "roleRBAC";
    private String category = "All";
    private String accessType = "full-control";
    private String userLogin="test";
    private String mportaluser="userRBAC";
    private String mportalgroup="groupRBAC";
    private String mportalpassword="Installed,1";
    private String validateAdminValue="admin";
    private String validateAdminValuegroup="Administrators";
    private String validateAdminValuegroup1="bvd_admin_group";
    private String validateRBACValue="RBACTest";
    private String validateRBACValuegroup="roleRBACgroup";
    private String validateRBACValuegroup1="undefined";
    private String assrole="superAdmin";
    private String grouprole="DATABASE";
    private String sGroupDescription = "This group is an RBAC group";
    private String sRoleDescription="This role is an RBAC role";
    String datafieldadmin="loginuser";
    String datafieldgroup="groupname";
    String datafieldgroup1="?column?";
    int queryresultsize=0;
    String userId;
    String roleId;
    String queryType="DataQuery";
    String rbacusererror="The referenced parameter query with variable name 'sys.users' is missing. Please create a parameter query with this name.";
    String rbacgrouperror="The referenced parameter query with variable name 'sys.group' is missing. Please create a parameter query with this name.";
    private String sDataChannel ;
    private String sDataChannelgroup;
    private String tags = "RBACTest";
    private String sWidget = "shape2";
    private String sWidgetgroup="shape3";
    private String sWidgetgroup1="shape4";

    private void initPages() {
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        mainPage = PageFactory.initElements(driver, MainPage.class);
        manageDashboardsPage = PageFactory.initElements(driver, ManageDashboardsPage.class);
        editDashboardPage = PageFactory.initElements(driver, EditDashboardPage.class);
        dataCollectorPage=PageFactory.initElements(driver, DataCollectorPage.class);
        mportalmainpage=PageFactory.initElements(driver, MPortalMainPage.class);
        mportalopsbridgepage=PageFactory.initElements(driver, MPortalOpsBridgePage.class);
        mportalloginpage=PageFactory.initElements(driver, MPortalLoginPage.class);
        usersGroupsAndRolesHomePage = PageFactory.initElements(driver, UsersGroupsAndRolesHomePage.class);
        roleEditor = PageFactory.initElements(driver, RoleEditor.class);
        userList = PageFactory.initElements(driver, UsersList.class);
        groupList = PageFactory.initElements(driver, GroupList.class);
        usersGroupsAndRolesSideNav = PageFactory.initElements(driver, UsersGroupsAndRolesSideNav.class);
        roleList = PageFactory.initElements(driver, RoleList.class);
        groupEditor = PageFactory.initElements(driver, GroupEditor.class);
        userEditor = PageFactory.initElements(driver, UserEditorBVD.class);
    }

    @BeforeClass
    public void setup() {
        cfg = new TestConfig().getConfig();
        BROWSER=cfg.getString("application.bvd.browser.name");
        BVD_USR = cfg.getString("application.bvd.user.adminusr");
        BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
        URL=cfg.getString("application.bvd.url.external");
        mportalurl=cfg.getString("application.bvd.url.mportalurl");
        sSourceDashboard = "RBACTest";
        sDashboard = sSourceDashboard + "_" + BROWSER;
        sDashboardPath = "C:\\tmp\\" + sDashboard + ".svg";
        driver = UiTestWebDriver.getWebDriver();
        sDataChannel = tags.replace(",", " ") + " " + BROWSER;
        sDataChannelgroup=tags.replace(",", " ") + " " + BROWSER+" group";
        driver.get(mportalurl);
        initPages();
        try {
            client = new BvdWsClient(BVD_USR, BVD_PWD, PROXY_HOST, PROXY_PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(priority=50)
    public void logintoBVDAdmin(){

        driver.get(URL);
        TestUtils.sleepAndLog(5000);
        result = loginPage.login(BVD_USR, BVD_PWD);
        Result.rcEquals(result, "0");

        result = loginPage.idm_LoggedIn_Check();
        Result.rcEquals(result, "0");

    }

    @Test(priority = 55)
    public void createRole() {
        result = client.userWS.createRole(roleName, desc, category,accessType);
        Result.rcEquals(result, "0");
        roleId = result.get("out");
    }

    @Test(priority = 60)
    public void copyTestFiles() {
        result = TestUtils.downloadResource(this.getClass(), sSourceDashboard + ".svg", sDashboardPath);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 130)
    public void createDataincorrectuser()  {

        result=mainPage.openDataCollectors();
        Result.rcEquals(result,"0");

        result = dataCollectorPage.clickNewQuery("DataQuery");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.typeTextIntoQueryName(sSourceDashboard+" "+BROWSER,queryType);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.typeTextIntoQueryDescription(sSourceDashboard+" "+BROWSER+" Description",queryType);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.switchFormat("Default");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.typeTextIntoQuery("select ${${sys.users}} as loginuser");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickExecuteQuery();
        Result.rcEquals(result, "0");

        result = dataCollectorPage.checkQueryResult("LOGINUSER","true");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickSaveQuery();
        Result.rcEquals(result, "0");

    }


    @Test(priority = 140)
    public void validateincorrectuser()  {

        result = dataCollectorPage.readerror(rbacusererror);
        Result.rcEquals(result, "0");

    }


    @Test(priority = 150)
    public void createDatacorrectuser()
    {

        Actions actions = new Actions(driver);
        try {
            Robot robot = new Robot();

            robot.mouseMove(400,350);
        } catch (AWTException e) {
            e.printStackTrace();
        }

        actions.click().build().perform();

        TestUtils.sleep(2);

        result = dataCollectorPage.clickQueryInList(sSourceDashboard+" "+BROWSER);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickEditQuery(queryType);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.switchFormat("Default");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.typeTextIntoQuery("select ${${sys.user}} as loginuser");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickExecuteQuery();
        Result.rcEquals(result, "0");

        result = dataCollectorPage.checkQueryResult("LOGINUSER","admin");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickSaveQuery();
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickQueryInList(sSourceDashboard+" "+BROWSER);
        Result.rcEquals(result, "0");

    }


    @Test(priority = 160)
    public void createincorrectDataforgroup()  {

        result = dataCollectorPage.clickNewQuery("DataQuery");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.typeTextIntoQueryName(sSourceDashboard+" "+BROWSER+" group",queryType);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.typeTextIntoQueryDescription(sSourceDashboard+" "+BROWSER+" group"+" Description",queryType);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.switchFormat("Default");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.typeTextIntoQuery("select ${${sys.group}} as groupname");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickExecuteQuery();
        Result.rcEquals(result, "0");

        result = dataCollectorPage.checkQueryResult("GROUPNAME","true");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickSaveQuery();
        Result.rcEquals(result, "0");

    }

    @Test(priority = 170)
    public void validateincorrectgroup()  {

        result = dataCollectorPage.readerror(rbacgrouperror);
        Result.rcEquals(result, "0");

    }

    @Test(priority = 180)
    public void createDatacorrectgroup()
    {

        Actions actions = new Actions(driver);
        try {
            Robot robot = new Robot();

            robot.mouseMove(400,350);
        } catch (AWTException e) {
            e.printStackTrace();
        }

        actions.click().build().perform();

        TestUtils.sleep(2);

        result = dataCollectorPage.clickQueryInList(sSourceDashboard+" "+BROWSER+" group");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickEditQuery(queryType);
        Result.rcEquals(result, "0");

        result = dataCollectorPage.switchFormat("Default");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.typeTextIntoQuery("select ${${sys.groups}} as groupname");
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickExecuteQuery();
        Result.rcEquals(result, "0");

        result = dataCollectorPage.checkQueryResultforRBAC();
        Result.rcEquals(result, "0");

        result = dataCollectorPage.clickSaveQuery();
        Result.rcEquals(result, "0");

        TestUtils.sleep(2);

        result = dataCollectorPage.clickQueryInList(sSourceDashboard+" "+BROWSER+" group");
        Result.rcEquals(result, "0");

    }


    @Test(priority = 190)
    public void openManageDashboards() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 200)
    public void uploadDashboard() {
        result = manageDashboardsPage.uploadDashboard(sDashboardPath);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 210)
    public void selectWidget() {
        result = editDashboardPage.selectWidget(sWidget);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 220)
    public void setDataChannel() {
        result = editDashboardPage.setDataChannel(sDataChannel);
        Result.rcEquals(result, "0");
    }

    @Test(priority =230)
    public void setdatafield() {
        result = editDashboardPage.setDataField(datafieldadmin);
        Result.rcEquals(result, "0");

    }

    @Test(priority = 240)
    public void selectWidgetgroup() {
        result = editDashboardPage.selectWidget(sWidgetgroup);
        Result.rcEquals(result, "0");
        queryresultsize=DataCollectorPage.queryResultsElementsSize;

    }

    @Test(priority = 250)
    public void setDataChannelgroup() {
        result = editDashboardPage.setDataChannel(sDataChannelgroup);
        Result.rcEquals(result, "0");
    }

    @Test(priority =260)
    public void apply() {
        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");

    }

    @Test(priority =270)
    public void setdatafieldgroup() {
        result = editDashboardPage.setDataField(datafieldgroup);
        Result.rcEquals(result, "0");

    }

    @Test(priority = 271)
    public void selectwidgetgroup1() {
        result = editDashboardPage.selectWidget(sWidgetgroup1);
        Result.rcEquals(result, "0");

    }

    @Test(priority = 272)
    public void setDataChannelWidgetgroup1() {
        result = editDashboardPage.setDataChannel(sDataChannelgroup);
        Result.rcEquals(result, "0");
    }


    @Test(priority =275)
    public void setdatafieldgroup1() {

        if(queryresultsize>2) {

            result = editDashboardPage.setDataField(datafieldgroup1);
            Result.rcEquals(result, "0");
        }
        else{

            result = editDashboardPage.setDataField(datafieldgroup);
            Result.rcEquals(result, "0");
        }

    }

    @Test(priority =280)
    public void savedatafield() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");

    }

    @Test(priority = 290)
    public void viewDashboardforadmin() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 300)
    public void validateDashboardforadmin() {
        TestUtils.sleep(2);
        if(queryresultsize>2) {

            result = mainPage.validateadminvalueRBAC(validateAdminValue,validateAdminValuegroup,validateAdminValuegroup1);
            Result.rcEquals(result, "0");
        }
        else{

            result = mainPage.validateadminvalueRBAC(validateAdminValue,validateAdminValuegroup,validateAdminValuegroup);
            Result.rcEquals(result, "0");
        }


    }


    @Test(priority=310)
    public void logoutforadmin()
    {
        result = mainPage.logout();
        Result.rcEquals(result, "0");
    }

    @Test(priority=320)
    public void loginforRBACuser() {

        result = loginPage.login(userName, userPwd);
        Result.rcEquals(result, "0");

        result = loginPage.idm_LoggedIn_Check();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 330)
    public void viewDashboardforRBACUser() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 340)
    public void validateDashboardforRBACUser() {
        TestUtils.sleep(2);
        if(queryresultsize>2) {

            result = mainPage.validateadminvalueRBAC(validateRBACValue,validateRBACValuegroup,validateRBACValuegroup1);
            Result.rcEquals(result, "0");
        }
        else{

            result = mainPage.validateadminvalueRBAC(validateRBACValue,validateRBACValuegroup,validateRBACValuegroup);
            Result.rcEquals(result, "0");
        }
    }

    @Test(priority = 350)
    public void logOutFromUser() {
        result = mainPage.logout();
    }

    @Test(priority = 630)
    public void deleteUserAndRole()
    {
        result = client.userWS.deleteRole(roleId);
        Result.rcEquals(result,"0");
    }

    @Test(priority = 640)
    public void deleteDashboard() {
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
