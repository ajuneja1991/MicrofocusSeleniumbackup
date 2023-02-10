package com.hp.opr.qa.tests.sys.ui.bvd;

import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.pageobjects.bvd.EditDashboardPage;
import com.hp.opr.qa.framework.pageobjects.bvd.LoginPage;
import com.hp.opr.qa.framework.pageobjects.bvd.MainPage;
import com.hp.opr.qa.framework.pageobjects.bvd.ManageDashboardsPage;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.TestConfig;
import com.mf.opr.qa.framework.utils.TestUtils;
import com.typesafe.config.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DashboardCRUDMenuCategory {
    private LoginPage loginPage;
    private MainPage mainPage;
    private ManageDashboardsPage manageDashboardsPage;
    private EditDashboardPage editDashboardPage;
    private WebDriver driver;
    private Map<String, String> result;
    private String BVD_USR="";
    private String BVD_PWD="";
    private String URL="";
    private Config cfg;
    private String PROXY_HOST="";
    private String BROWSER="";
    private int PROXY_PORT;
    private String userName = "TesterforCRUDCategory";
    private String userLogin;
    private String userPwd = "Test@123";
    private String roleName = "testforcrudcategory";
    private String desc = "For tester";
    private String category = "All";
    private String accessType = "full-control";
    private String userNameNotassignedFull = "TesterforLogin";
    private String userLoginNotassignedFull;
    private String userPwdNotassignedFull = "Test@123";
    private String roleNameNotassignedFull = "testforlogin";
    private String descNotassignedFull = "For tester";
    private String categoryNotassignedFull = "not-assigned";
    private String accessTypeNotassignedFull = "full-control";
    String roleId;
    String roleIdNotAssignedFull;
    private List<String> admindropdownoptions = Arrays.asList("Dashboards", "Resources");
    private String sSourceDashboard = "sample_dashboard_test";
    private String sDashboard = "";
    private String sDashboardnew= "";
    private String sMenuCategory="";
    private String sDashboardPath="";
    private List<String> dashboardsUnderCateg=Arrays.asList("");
    private String sDashboardPathnew="";

    private BvdWsClient client;


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
        sDashboard=sSourceDashboard + "_Dashboard_MenuCRUD_Category_" + BROWSER;
        sDashboardnew=sSourceDashboard + "_new_Dashboard_MenuCRUD_Category_" + BROWSER;
        sMenuCategory = "MenuCRUD_Category_" + BROWSER;
        sDashboardPath = "C:\\tmp\\" + sDashboard + ".svg";
        sDashboardPathnew ="C:\\tmp\\" + sDashboardnew + ".svg";
        dashboardsUnderCateg = Arrays.asList(sDashboard, sDashboardnew);
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
    public void createRoleToUser() {
        result = client.userWS.createRole(roleName, desc, category,accessType);
        Result.rcEquals(result, "0");
        roleId = result.get("out");

        result = client.userWS.createRole(roleNameNotassignedFull, descNotassignedFull, categoryNotassignedFull,accessTypeNotassignedFull);
        Result.rcEquals(result, "0");
        roleIdNotAssignedFull = result.get("out");

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
    @Test(priority = 17)
    public void copyTestFiles() {
        result = TestUtils.downloadResource(this.getClass(), sSourceDashboard + ".svg",  sDashboardPath);
        Result.rcEquals(result, "0");

        result = TestUtils.downloadResource(this.getClass(), sSourceDashboard + ".svg",  sDashboardPathnew);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 20)
    public void openManageDashboards() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 30)
    public void uploadDashboard() {
        result = manageDashboardsPage.uploadDashboard(sDashboardPath);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 40)
    public void saveUploadedDashboard() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 45)
    public void uploadandSaveNewDashboard() {
        result = manageDashboardsPage.uploadDashboard(sDashboardPathnew);
        Result.rcEquals(result, "0");

        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 50)
    public void viewDashboard() {
        result = mainPage.viewDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 60)
    public void openManageDashboardsToSetCategory() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 70)
    public void openEditToSetCategory() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 80)
    public void assignNewCategory() {
        result = editDashboardPage.assignNewMenuCategory(sMenuCategory);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 90)
    public void saveDashboardCategory() {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 95)
    public void viewDashboardWithMenu() {
        result = mainPage.viewDashboardwithMenu(sMenuCategory,sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 98)
    public void openManageDashboardsforReplace() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 100)
    public void openEditDashboardToReplace() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 110)
    public void replaceDashboard() {
        result = editDashboardPage.replaceDashboard(sDashboardPath);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 120)
    public void applyReplacedDashboard() {
        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 130)
    public void replaceDashboardVerifyCategory() {
        result = editDashboardPage.getCategory();
        Result.outContainsNot(result, sMenuCategory);
    }

    @Test(priority = 140)
    public void cancelReplacedDashboard() {
        result = editDashboardPage.cancel();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 150)
    public void viewReplacedDashboard() {
        result = mainPage.viewDashboardwithMenu(sMenuCategory,sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 160)
    public void openManageDashboardsToReplace() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 170)
    public void replaceDashboardCategory() {
        result = manageDashboardsPage.replaceDashboard(sDashboardPath);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 180)
    public void applyReplacedDashboardAgain() {
        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 200)
    public void cancelReplacedDashboardCategory() {
        result = editDashboardPage.cancel();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 210)
    public void viewReplacedDashboardCategory() {
        result = mainPage.viewDashboardwithMenu(sMenuCategory,sDashboard);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 220)
    public void ManageDashboardForNewDashboard() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 230)
    public void editDashboardForNewDashboard() {
        result = manageDashboardsPage.openEditDashboard(sDashboardnew);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 240)
    public void assignMenuCategorytoNewDashboard() {
        result = editDashboardPage.assignMenuCategory(sMenuCategory);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 250)
    public void verifyDashboardsUnderCateg() {
        result = mainPage.checkDashboardsUnderMenuCategory(sMenuCategory,dashboardsUnderCateg);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 260)
    public void searchDashboard() {

        result = mainPage.searchDashboard(sMenuCategory);
        Result.rcEquals(result, "0");
    }



    @Test(priority = 270)
    public void openManageDashboardforDelete() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 280)
    public void editDashboardforDelete() {
        result = manageDashboardsPage.openEditDashboard(sDashboardnew);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 280)
    public void trydeleteCategoryerror() {
        result = editDashboardPage.removeMenuCategory(sMenuCategory);
        Result.rcEquals(result, "0");

        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");

        TestUtils.sleep(4);
        result = editDashboardPage.deleteMenuCategory(sMenuCategory);
        Result.rcEquals(result, "0");

        result = editDashboardPage.deleteCategoryerror(sMenuCategory);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 290)
    public void logOut() {
        result = mainPage.logout();
        Result.rcEquals(result, "0");

    }

    @Test(priority = 300)
    public void logInForNotAssignedCateg() {
        result = loginPage.login(userNameNotassignedFull, userPwdNotassignedFull);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 310)
    public void login_checkNotAssignedFull()
    {
        result = loginPage.idm_LoggedIn_Check();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 320)
    public void openManageDashboardsToDeleteCategory() {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 330)
    public void openEditDashboardsToDeleteCategory() {
        result = manageDashboardsPage.openEditDashboard(sDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 340)
    public void editDashboardDeleteCategory() {
        result = client.dashboardWS.getAssignedCategories(sDashboard);
        Result.rcEquals(result, "0");

        result = editDashboardPage.removeMenuCategory(sMenuCategory);
        Result.rcEquals(result, "0");

        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");

        TestUtils.sleep(4);
        result = editDashboardPage.deleteMenuCategory(sMenuCategory);
        Result.rcEquals(result, "0");

    }


    @Test(priority = 350)
    public void logOutForNotAssigned() {
        result = mainPage.logout();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 630)
    public void editDashboardCheckCategoryDeleted() {
        result = client.userWS.getCategoriesNames("menu");
        Result.outContainsNot(result, sMenuCategory);
    }

    @Test(priority = 640)
    public void DeleteDashboard() {
            result = client.dashboardWS.deleteDashboard(sDashboard);
            Result.rcEquals(result, "0");

        result = client.dashboardWS.deleteDashboard(sDashboardnew);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 650)
    public void cleanUpDeleteCategory() {
        result = client.userWS.getCategoriesNames("menu");
        Result.rcEquals(result, "0");
        if (result.get("out").contains(sMenuCategory))
        {
            result = client.userWS.deleteCategory(sMenuCategory);
            Result.rcEquals(result, "0");
        }
    }

    @Test(priority = 670)
    public void deleteRole()
    {
        result = client.userWS.deleteRole(roleId);
        Result.rcEquals(result,"0");

        result = client.userWS.deleteRole(roleIdNotAssignedFull);
        Result.rcEquals(result,"0");

    }



    @AfterClass
    public void cleanup() {
        driver.quit();
    }
}
