package com.hp.opr.qa.tests.sys.ui.bvd;
import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdJSONFileEdit.EditJSONFile;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.common.utils.Misc;
import com.hp.opr.qa.framework.common.utils.ResultStore;
import com.hp.opr.qa.framework.pageobjects.bvd.EditDashboardPage;
import com.hp.opr.qa.framework.pageobjects.bvd.MainPage;
import com.hp.opr.qa.framework.pageobjects.bvd.ManageDashboardsPage;
import com.mf.opr.qa.framework.TestConfig;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.utils.TestUtils;
import com.typesafe.config.Config;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import com.hp.opr.qa.framework.pageobjects.bvd.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class LocalizationUsingCLI
{
    private LoginPage loginPage;
    private MainPage mainPage;
    private WebDriver driver;
    private String URL = "";
    private String BVD_USR="";
    private String BVD_PWD="";
    private Map<String, String> result;
    private Config cfg;
    private BvdWsClient client;
    private String PROXY_HOST="";
    private int PROXY_PORT;
    private String zipName = "bvd-cli.zip";
    private String windowsTool = "bvd-cli-win.exe";
    private String linuxTool = "bvd-cli-linux";
    private String macOsTool = "bvd-cli-macos";

    private String windowsToolbvdcli = "bvd-cli\\bvd-cli-win.exe";
    private String linuxToolbvdcli = "bvd-cli\\bvd-cli-linux";
    private String macOsToolcli = "bvd-cli\\bvd-cli-macos";
    private String bvdclifolder="bvd-cli";
    private String tempPath = "C:\\tmp\\";
    private String srcDashboard = "Localization";
    private String transDashboard = "translated";
    private String[] dataAfterTranslation = {"abc,d","ef/g","Value"};
    private String srcDashboardPath ;
    private String sDashboard;
    private String BROWSER = "";
    private String json_file = "localization_catalog";
    private ManageDashboardsPage manageDashboardsPage;
    private EditDashboardPage editDashboardPage;
    private ArrayList<String> allFiles = new ArrayList<>();
    private List<String> toolFiles = Arrays.asList(zipName, windowsTool, linuxTool, macOsTool,windowsToolbvdcli,linuxToolbvdcli,macOsToolcli,bvdclifolder);
    private List<String> generatedFiles = Arrays.asList("Localization_chrome.svg", "translated.svg", "localization_catalog.json");
    WebDriverWait wait;
    private String userName = "Tester_Localization_Using_CLI";
    private String userPwd = "Test@123";
    private String roleName = "test_Localization_Using_CLI";
    private String desc = "test_Localization_Using_CLI";
    String roleId;
    private String category = "All";
    private String accessType = "full-control";

    private void initPages()
    {
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        mainPage = PageFactory.initElements(driver, MainPage.class);
        manageDashboardsPage = PageFactory.initElements(driver, ManageDashboardsPage.class);
        editDashboardPage = PageFactory.initElements(driver, EditDashboardPage.class);
    }

    @BeforeClass
    public void setup()  {
        cfg = new TestConfig().getConfig();
        BROWSER = cfg.getString("application.bvd.browser.name");
        sDashboard = srcDashboard + "_" + BROWSER ;
        srcDashboardPath = tempPath + sDashboard + ".svg";
        cfg = new TestConfig().getConfig();
        BVD_USR = cfg.getString("application.bvd.user.adminusr");
        BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
        URL=cfg.getString("application.bvd.url.external");
        driver = UiTestWebDriver.getWebDriver();
        wait=new WebDriverWait(driver, 30);
        driver.get(URL);
        initPages();
        allFiles.addAll(toolFiles);
        allFiles.addAll(generatedFiles);

        try
        {
            client = new BvdWsClient(BVD_USR, BVD_PWD, PROXY_HOST, PROXY_PORT);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Test (priority = 0)
    public void initialClean()
    {
        try
        {
            for (String targetFile : allFiles)
            {
                java.io.File fileToDelete;
                fileToDelete = new java.io.File(tempPath + targetFile);
                if(fileToDelete.exists()){
                    fileToDelete.delete();
                }

            }
            result = ResultStore.success("File(s) successfully deleted");
            Result.rcEquals(result, "0");
        } catch (Exception exc)
        {
            result = ResultStore.failure("Failed to delete file  error: " + Misc.getStacktraceAsString(exc));
        }

        Result.rcEquals(result, "0");
    }

    @Test (priority = 1)
    public void deletebvdcliFolder()
    {
        try
        {
            for (String targetFile : allFiles)
            {
                java.io.File fileToDelete;
                fileToDelete = new java.io.File(tempPath + targetFile);
                fileToDelete.delete();
            }
            result = ResultStore.success("File(s) successfully deleted");
            Result.rcEquals(result, "0");
        } catch (Exception exc)
        {
            result = ResultStore.failure("Failed to delete file  error: " + Misc.getStacktraceAsString(exc));
        }

        Result.rcEquals(result, "0");
    }


    @Test(priority = 3)
    public void createRole() {
        result = client.userWS.createRole(roleName, desc, category,accessType);
        Result.rcEquals(result, "0");
        roleId = result.get("out");
    }
    @Test(priority = 5)
    public void testLoginNonadmin() {
        result = loginPage.login(userName, userPwd);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 10)
    public void login_checkfornonadmin()
    {
        result = loginPage.idm_LoggedIn_Checkfornonadmin();
        Result.rcEquals(result, "0");
    }


    @Test(priority = 20)
    public void navigateToResources()
    {
        result = mainPage.openResources();
        Result.rcEquals(result, "0");
        result = mainPage.pageScrollView();
        Result.rcEquals(result, "0");
    }
    @Test(priority = 30)
    public void downloadBVDCli()
    {
        result = mainPage.downloadResource(zipName);
        Result.rcEquals(result, "0");

        java.io.File downloadedZip;
        downloadedZip = new java.io.File(tempPath + zipName);

        for(int i=1; i<150; i++){
            if (downloadedZip.exists()){
                System.out.println("BREAK");
                break;
            }
            else {
                System.out.println("SLEEP # " + i);
                TestUtils.sleepAndLog(4000);
            }
        }

    }
    @Test(priority = 40)
    public void unzipTool() throws IOException
    {
        Runtime.getRuntime().exec("\"c:\\Program Files\\7-Zip\\7z.exe\" x " + tempPath + zipName + " " +
                "-oc:\\tmp\\");
        TestUtils.sleepAndLog(4000);
    }

    @Test(priority = 50)
    public void copyFile()
    {
        result = TestUtils.downloadResource(this.getClass(), srcDashboard + ".svg",
                srcDashboardPath );
        Result.rcEquals(result, "0");
        TestUtils.sleepAndLog(4000);
    }

    @Test (priority = 60)
    public void generateJSONUsingCLI() {


        String[] command = {windowsTool, "--generate", "--file", srcDashboard + ".svg", "--messagecatalogue", json_file};
        try {
            TestUtils.sleep(2);
            Runtime.getRuntime().exec("cmd.exe /c cd \"C:\\tmp\" & start cmd.exe /k \"bvd-cli-win.exe --generate --file Localization_chrome.svg --messagecatalogue localization_catalog\"" );
            //Runtime.getRuntime().exec(command);

            java.io.File jsonFile;
            jsonFile = new java.io.File(tempPath+json_file+".json");
            for(int i=1; i<150; i++) {
                if (jsonFile.exists()){
                    System.out.println("BREAK");
                    break;
                }
                else{
                    System.out.println("SLEEP # " + i);
                    TestUtils.sleepAndLog(4000);
                }
            }

        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }

    @Test(priority = 70)
    public void EditJSON() throws IOException {
        EditJSONFile.editAPIKeyinJSON(tempPath+json_file+".json","key1","abc,d");
        EditJSONFile.editAPIKeyinJSON(tempPath+json_file+".json","key2","ef/g");
        TestUtils.sleep(2);

    }

    @Test(priority = 80)
    public void createTranslatedSVG() throws IOException
    {
        TestUtils.sleep(2);
        Runtime.getRuntime().exec("cmd.exe /c cd \"C:\\tmp\" & start cmd.exe /k \"bvd-cli-win.exe --translate --file Localization_chrome.svg --messagecatalogue localization_catalog.json --translatedDashboard translated");
        TestUtils.sleep(10);

    }

    @Test(priority = 90)
    public void uploadNewDashboard()
    {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");

        result = manageDashboardsPage.uploadDashboard(tempPath + transDashboard + ".svg");
        Result.rcEquals(result, "0");
        TestUtils.sleepAndLog(1000);
        result = editDashboardPage.apply();
        Result.rcEquals(result, "0");
    }

    @Test (priority = 100)
    public void viewDashboard() {
        result = mainPage.viewDashboard(transDashboard);
        Result.rcEquals(result, "0");

    }

    @Test(priority = 110)
    public void validateTranslatedDashboard()
    {
        result = mainPage.validateDashboard(dataAfterTranslation);
        Result.rcEquals(result,"0");
    }

    @Test(priority = 120)
    public void deleteDashboard() {
        result = client.dashboardWS.deleteDashboard(transDashboard);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 130)
    public void logoutFromMainPage()
    {
        result = mainPage.logout();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 135)
    public void deleteRole()
    {
        result = client.userWS.deleteRole(roleId);
        Result.rcEquals(result,"0");
    }


    @Test(priority = 140)
    public void terminateGeneratorProcess () throws IOException{
        Runtime.getRuntime().exec("taskkill /IM cmd.exe");
    }

    @AfterClass
    public void cleanup(){
        driver.quit();
    }

}
