package com.hp.opr.qa.tests.sys.ui.bvd;

import com.hp.opr.qa.framework.UiTestWebDriver;
import com.hp.opr.qa.framework.bvdrestws.BvdWsClient;
import com.hp.opr.qa.framework.common.utils.Misc;
import com.hp.opr.qa.framework.common.utils.ResultStore;
import com.hp.opr.qa.framework.pageobjects.bvd.EditDashboardPage;
import com.hp.opr.qa.framework.pageobjects.bvd.LoginPage;
import com.hp.opr.qa.framework.pageobjects.bvd.MainPage;
import com.hp.opr.qa.framework.pageobjects.bvd.ManageDashboardsPage;
import com.hp.opr.qa.framework.reUsableObjects.pdfValidation;
import com.mf.opr.qa.framework.TestConfig;
import com.mf.opr.qa.framework.runner.Result;
import com.mf.opr.qa.framework.utils.TestUtils;
import com.typesafe.config.Config;
import org.apache.commons.io.FileUtils;
import org.hornetq.utils.json.JSONException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Web2PDF {
    private LoginPage loginPage;
    private MainPage mainPage;
    private ManageDashboardsPage manageDashboardsPage;
    private EditDashboardPage editDashboardPage;
    private WebDriver driver;
    private Map<String, String> result;
    private BvdWsClient client;
    private List<String> defaultWidgetDataFields = Arrays.asList("Data1", "Data2", "Data3", "Data4");
    public List<String> defaultWidgetnumbers = Arrays.asList("10", "20", "30", "40");
    private List<String> SinglePageData = Arrays.asList("1","2","3","4","9","9","9","9");
    private String singlePDFGenerate="singlePdfGenerate.bat";
    private String singlePDFGeneratespecifiedPath="singlePdfGenerateSpecifiedPath.bat";
    private String multiPagePDFGenerate="multiPagePdfGenerate.bat";
    private String multiPagePDFGenerateSpecifiedPath="multiPagePdfGenerateSpecifiedPath.bat";
    private String ExportDashboard="ExportDashboard.bat";
    private String inputText="input.txt";
    private List<String> textElemsInWidget = new ArrayList<>();
    private List<String> textElemsInWidgetwoTitle = new ArrayList<>();
    private List<String> textElemsInWidgetwoTitlesolditem = new ArrayList<>();
    private List<String> textElemsInWidgetwotitlelocation = new ArrayList<>();
    private String autoDefaultWidgetQuery = "AutoDefaultWidgetQuery";
    private String autoGroupWidgetQuery = "AutoGroupWidgetQuery";
    String sSourceDashboard = "autoDataCollector";
    String sDashboard ;
    String sDashboardPath;
    private String BVD_USR="";
    private String BVD_PWD="";
    private String URL="";
    private Config cfg;
    private String PROXY_HOST="";
    private String BROWSER="";
    private int PROXY_PORT;
    private String dashboard_URL = "";
    private String zipLink = "/webtopdf/getcli";
    private String tempPath = "C:\\tmp\\";
    private String zipName = "pdf-print.zip";
    private String windowsTool = "windows\\pdf-print.exe";
    private String linuxTool = "linux\\pdf-print";
    private String macOsTool = "macos\\pdf-print";
    private String defaultOutputFile = "autoDataCollector_WebToPDF_chrome";
    private String defaultFileStartsWith = "autoDataCollector_WebToPDF_chrome-";
    String PDF_ParticularPath= "web2pdf.pdf";
    String pdfName = "web2pdf";
    String PDF_Multipage = "ips-cli\\multipage_web2pdf.pdf";
    String fileNameForSingleContent;
    private ArrayList<String> allFiles = new ArrayList<>();
    private List<String> toolFiles = Arrays.asList(zipName, windowsTool, linuxTool, macOsTool);
    private List<String> generatedFiles = Arrays.asList(defaultOutputFile,PDF_Multipage,PDF_ParticularPath );

    private String roleName = "testScaling";
    private String desc = "For tester";
    private String category = "All";
    private String accessType = "full-control";
    private String userName = "TestScaling";
    private String userPwd = "Test@123";

    String roleId;
    private String BVD_PORT="";
    private String BVD_HOSTNAME="";

    private String sDataChannel = "WebToPDF";

    private void initPages()
    {
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        mainPage = PageFactory.initElements(driver, MainPage.class);
        manageDashboardsPage = PageFactory.initElements(driver, ManageDashboardsPage.class);
        editDashboardPage = PageFactory.initElements(driver, EditDashboardPage.class);
    }

    private String defaultWidgetJson()
    {
        return "{\"dataCollector\":{\"active\":true,\"data\":{\"resultFormat\":\"unchanged\"," +
                "\"tags\":[\"DefaultWidgetQuery\"],\"dims\":[],\"queryText\":\"SELECT * FROM public" +
                ".bvd_automation2 where id = 1\",\"availableColumns\":[\"Data1\",\"Data2\",\"Data3\"," +
                "\"Data4\",\"id\"],\"sampleQueryResult\":{\"Data1\":10,\"Data2\":20,\"Data3\":30," +
                "\"Data4\":40,\"id\":1}},\"name\":\"AutoDefaultWidgetQuery\"}}";
    }


    private String WebToPDFDatabaseQuery() {
        return "{\"dataCollector\":{\"active\":true,\"data\":{\"resultFormat\":\"groupwidget\"," +
                "\"tags\":[],\"dims\":[],\"queryText\":\"SELECT * FROM bvd_lwr_demo " +
                "limit 60\",\"availableColumns\":" +
                "[\"ID\",\"LOCATION\",\"TIMESTAMP\",\"sold_item\"],\"sampleQueryResult\":{\"ID\":1,\"LOCATION\":\"Boeblingen\",\"TIMESTAMP\":" +
                "\"2017-01-01T09:00:00.000Z\",\"sold_item\":9}},\"name\":\"WebToPDF\"}}";
    }


    private void executeCommand(String[] commands) throws IOException
    {

        Runtime rt = Runtime.getRuntime();
        Process proc = rt.exec(commands);

        TestUtils.sleep(10);
        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(proc.getErrorStream()));

        Reporter.log("Here is output for "+Arrays.toString(commands)+":\n");
        String s = null;
        while ((s = stdInput.readLine()) != null) {
            Reporter.log(s+"\n");
        }

        Reporter.log("Here is errors for "+Arrays.toString(commands)+":\n");
        while ((s = stdError.readLine()) != null) {
            Reporter.log(s+"\n");


        }
    }

    @BeforeClass
    public void setup()
    {
        cfg = new TestConfig().getConfig();
        BROWSER=cfg.getString("application.bvd.browser.name");
        BVD_USR = cfg.getString("application.bvd.user.adminusr");
        BVD_PWD=cfg.getString("application.bvd.user.adminpwd");
        BVD_PORT=cfg.getString("application.bvd.network.webserver_port");
        BVD_HOSTNAME=cfg.getString("environment.suite.hostname.master1");
        URL=cfg.getString("application.bvd.url.external");

        sDashboard = sSourceDashboard + "_WebToPDF_" + BROWSER;
        sDashboardPath = "C:\\tmp\\" + sDashboard + ".svg";


        driver = UiTestWebDriver.getWebDriver();
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

    public void singlePageVerification(String pdfContent)
    {
        boolean found1 = true,found2 = true;
        for(String s:defaultWidgetnumbers)
        {
            if(!pdfContent.contains(s))
            {
                found1 = false;
                break;
            }
        }
        Assert.assertTrue(found1);
        for(String s:SinglePageData)
        {
            if(!pdfContent.contains(s))
            {
                found2 = false;
                break;
            }
        }
        Assert.assertTrue(found2);
    }


    public void multiPageVerification(String pdfContent)
    {
        boolean found1 = true,found2 = true;
        for(String s:defaultWidgetnumbers)
        {
            if(!pdfContent.contains(s))
            {
                found1 = false;
                break;
            }
        }
        Assert.assertTrue(found1);
        for(String s:SinglePageData)
        {
            if(!pdfContent.contains(s))
            {
                found2 = false;
                break;
            }
        }
        Assert.assertTrue(found2);
    }

    public boolean checkFileExists(String folderPath, String fileName)
    {

        File folder = new File(folderPath);
        String files;
        File[] listOfFiles = folder.listFiles();
        boolean found=false;
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                files = listOfFiles[i].getName();
                System.out.println(files);
                if (files.startsWith(fileName)) {
                    System.out.println("found =================");
                    found = true;
                    break;
                } else {
                    System.out.println("SLEEP # " + i);
                }
            }
        }return found;

    }

    public boolean checkFileExistsWithWait(String filePath)
    {
        boolean found=false;
        java.io.File fileToCheck;
        fileToCheck = new java.io.File(filePath);

        for(int i=1; i<80; i++){
            if (fileToCheck.exists()){
                found=true;
                System.out.println("BREAK");
                break;
            }
            else {
                System.out.println("SLEEP # " + i);
                TestUtils.sleepAndLog(2000);
            }
        }
        return found;
    }


    public void deleteFile(String folderPath, String initialFileName){
        File folder = new File(folderPath);
        String files;
        File[] listOfFiles = folder.listFiles();
        String fullFileName="";
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                files = listOfFiles[i].getName();
                System.out.println(files);
                if (files.startsWith(initialFileName)) {
                    listOfFiles[i].delete();
                    fullFileName=files;
                    System.out.println("Deleted FileName is ================="+ fullFileName);
                } else {
                    System.out.println("SLEEP # " + i);
                }
            }
        }
    }

    public String getFileNameString(String folderPath, String initialFileName){
        File folder = new File(folderPath);
        String files;
        File[] listOfFiles = folder.listFiles();
        String fullFileName="";
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                files = listOfFiles[i].getName();
                System.out.println(files);
                if (files.startsWith(initialFileName)) {
                    fullFileName=files;
                    System.out.println("Full FileName is ================="+ fullFileName);
                    break;
                } else {
                    System.out.println("SLEEP # " + i);
                    TestUtils.sleepAndLog(500);
                }
            }
        } return fullFileName;
    }

    @Test(priority = 0)
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
    public void login_check() throws UnrecoverableKeyException, NoSuchAlgorithmException,
            KeyStoreException, JSONException, KeyManagementException
    {
        result = loginPage.idm_LoggedIn_Check();
        Result.rcEquals(result, "0");
        client.collectorWS.deleteAllExistQueries();
    }

    @Test(priority = 20)
    public void initialClean()
    {
        allFiles.add("pdf-print.exe");
        try
        {
            for (String targetFile : allFiles)
            {
                java.io.File fileToDelete;
                fileToDelete = new java.io.File(tempPath + targetFile);
                if(fileToDelete.exists()){
                    if(fileToDelete.delete())
                    {
                        Assert.assertTrue(true,targetFile + "deleted successfully");

                    }
                    else
                    {
                        Assert.assertTrue(false,targetFile + "not deleted successfully");
                    }
                }

            }
            result = ResultStore.success("File(s) successfully deleted");
            Result.rcEquals(result, "0");
        } catch (Exception exc)
        {
            result = ResultStore.failure("Failed to delete file  error: " + Misc.getStacktraceAsString(exc));
        }
        allFiles.remove("pdf-print.exe");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 25)
    public void deleteDefaultFile() throws Exception {

        try{
            deleteFile("C:\\tmp", defaultFileStartsWith);
        }
        catch (Exception e){
            e.getStackTrace();
        }
        Runtime.getRuntime().exec("taskkill /F /IM cmd.exe") ;

    }


    @Test(priority = 30)
    public void createInitialQueryDefault() throws UnrecoverableKeyException,
            NoSuchAlgorithmException, KeyStoreException, JSONException, KeyManagementException
    {
        result = client.collectorWS.bvdDataCollectorCreateQuery(defaultWidgetJson());
        Result.rcEquals(result, "0");
    }


    @Test(priority = 45)
    public void createInitialQueryWeb() throws UnrecoverableKeyException,
            NoSuchAlgorithmException, KeyStoreException, JSONException, KeyManagementException
    {
        result = client.collectorWS.bvdDataCollectorCreateQuery(WebToPDFDatabaseQuery());
        Result.rcEquals(result, "0");
    }

    @Test(priority = 50)
    public void copyTestFiles()
    {
        result = TestUtils.downloadResource(this.getClass(), sSourceDashboard + ".svg", sDashboardPath);
        Result.rcEquals(result, "0");

        result = TestUtils.downloadResource(this.getClass(), singlePDFGenerate,
                tempPath+singlePDFGenerate);

        result = TestUtils.downloadResource(this.getClass(), singlePDFGeneratespecifiedPath,
                tempPath+singlePDFGeneratespecifiedPath);

        result = TestUtils.downloadResource(this.getClass(), multiPagePDFGenerate,
                tempPath+multiPagePDFGenerate);

        result = TestUtils.downloadResource(this.getClass(), multiPagePDFGenerateSpecifiedPath,
                tempPath+multiPagePDFGenerateSpecifiedPath);

        result = TestUtils.downloadResource(this.getClass(), inputText,
                tempPath+inputText);


    }

    @Test(priority = 60)
    public void uploadDashboard()
    {
        result = mainPage.openManageDashboards();
        Result.rcEquals(result, "0");

        result = manageDashboardsPage.uploadDashboard(sDashboardPath);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 70)
    public void selectWidget()
    {
        result = editDashboardPage.selectWidget("group1");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 80)
    public void setDataChannel()
    {
        result = editDashboardPage.setDataChannel(autoDefaultWidgetQuery);
        Result.rcEquals(result, "0");
    }


    @Test(priority = 90)
    public void setDataFields()
    {
        result = editDashboardPage.setDataFieldMultiSelect(defaultWidgetDataFields);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 100)
    public void selectGroupWidget()
    {
        result = editDashboardPage.selectWidget("group30");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 110)
    public void setDataChannelGroupWidget()
    {
        result = editDashboardPage.setDataChannel(sDataChannel);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 120)
    public void selectTextWidgetID()
    {
        result = editDashboardPage.selectWidget("shape27");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 130)
    public void setDataFieldsTextInGroup()
    {
        result = editDashboardPage.setDataField("ID");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 140)
    public void selectTextWidgetSoldItem()
    {
        result = editDashboardPage.selectWidget("shape28");
        Result.rcEquals(result, "0");
    }

    @Test(priority = 150)
    public void setDataFieldsTextInGroup2()
    {
        result = editDashboardPage.setDataField("sold_item");
        Result.rcEquals(result, "0");
    }


    @Test(priority = 160)
    public void saveDashboard()
    {
        result = editDashboardPage.save();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 170)
    public void openDashboard()
    {
        result = mainPage.viewDashboard(sDashboard);
        dashboard_URL = driver.getCurrentUrl();
        System.out.println(dashboard_URL);
        Result.rcEquals(result, "0");
    }

    @Test(priority = 180)
    public void checkChartNumber()
    {
        result = mainPage.getChartNumbers("group1");
        Result.outEquals(result, defaultWidgetnumbers.toString());

    }


    @Test(priority = 210)
    public void navigateToResources()
    {
        result = mainPage.openResources();
        Result.rcEquals(result, "0");
        result = mainPage.pageScrollView();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 220)
    public void downloadIpsCli()
    {
        result = mainPage.downloadResourceUsingHref(zipLink);
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

    @Test(priority = 230)
    public void unzipTool() throws IOException
    {
        Runtime.getRuntime().exec("\"c:\\Program Files\\7-Zip\\7z.exe\" x " + tempPath + zipName + " " +
                "-oc:\\tmp\\");
        TestUtils.sleepAndLog(4000);
    }



    @Test(priority = 250)
    public void checkFilesExist() throws IOException
    {
        try
        {
            for (String targetFile : toolFiles)
            {
                java.io.File fileToCheck;
                fileToCheck = new java.io.File(tempPath + targetFile);
                if(!fileToCheck.exists()){
                    result = ResultStore.failure("One of files doesn't exist: " + targetFile);
                    Result.rcEquals(result, "0");
                }
            }
            result = ResultStore.success("File(s) exist");

        } catch (Exception exc)
        {
            result = ResultStore.failure("Failed to delete file  error: " + Misc
                    .getStacktraceAsString(exc));
        }

        Result.rcEquals(result, "0");

    }


    @Test(priority = 255)
    public void copypdfprintTotmpFolder() throws IOException
    {
        try
        {
                File srcFile = new File(tempPath + "\\windows\\pdf-print.exe");
                File destFile = new File(tempPath + "\\pdf-print.exe");
                FileUtils.copyFile(srcFile, destFile);
                if(!destFile.exists()){
                    result = ResultStore.failure("pdf-print file cannot be copied" );
                    Result.rcEquals(result, "0");
                }

            result = ResultStore.success("pdf-print File is transfered to tmp folder");

        } catch (Exception exc)
        {
            result = ResultStore.failure("Failed to delete file  error: " + Misc
                    .getStacktraceAsString(exc));
        }

        Result.rcEquals(result, "0");

    }

    @Test(priority = 257)
    public void deleteFileAtTmpFolder() throws Exception {

        try{
            deleteFile("C:\\tmp", defaultFileStartsWith);
        }
        catch (Exception e){
            e.getStackTrace();
        }
        Runtime.getRuntime().exec("taskkill /F /IM cmd.exe") ;

    }

    @Test(priority = 260)
    public void generateSinglePgPDFDefault() throws IOException, InterruptedException
    {
        //current path single page
        try
        {
            TestUtils.sleepAndLog(2000);
            Process p = Runtime.getRuntime().exec("cmd.exe /c cd \"C:\\tmp\" & start cmd.exe /K \"singlePdfGenerate.bat");
            TestUtils.sleepAndLog(50000);
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
        Assert.assertTrue(checkFileExists("C:\\tmp\\", defaultFileStartsWith));
        Runtime.getRuntime().exec("taskkill /F /IM cmd.exe") ;


    }

    @Test(priority = 265)
    public void getFileName() throws IOException, InterruptedException
    {
        try{
            fileNameForSingleContent = getFileNameString("C:\\tmp", defaultFileStartsWith);
            }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test(priority = 270)
    public void verifySinglePgPDFContentDefault() throws Exception {
        String filePath = tempPath+fileNameForSingleContent;
        String pdfContent = pdfValidation.readPDFContent(filePath);
        singlePageVerification(pdfContent);

    }


    @Test(priority = 280)
    public void generateSinglePgPDFAtPath() throws IOException, InterruptedException
    {
        //particular path single page
        boolean found=false;
        try
        {
            TestUtils.sleepAndLog(2000);
            Process p = Runtime.getRuntime().exec("cmd.exe /c cd \"C:\\tmp\" & start cmd.exe /K \"singlePdfGenerateSpecifiedPath.bat");
            TestUtils.sleepAndLog(4000);

        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
        Assert.assertTrue(checkFileExists("C:\\tmp\\ips-cli", PDF_ParticularPath));
        Runtime.getRuntime().exec("taskkill /F /IM cmd.exe") ;
    }

    @Test(priority = 290)
    public void verifySinglePgPDFAtPath() throws Exception {

        Web2PDF obj = new Web2PDF();
        String pdfContent = pdfValidation.readPDFContent("C:\\tmp\\ips-cli\\"+PDF_ParticularPath);
        singlePageVerification(pdfContent);

    }

    @Test(priority = 295)
    public void deleteFileAtPath() throws Exception {

        try{
            deleteFile("C:\\tmp\\", defaultFileStartsWith);
        }
        catch (Exception e){
            e.getStackTrace();
        }
        Runtime.getRuntime().exec("taskkill /F /IM cmd.exe") ;

    }


    @Test(priority = 300)
    public void generateMultiPagePDFAtDefault() throws IOException, InterruptedException
    {
        //multi page default location
        boolean found=false;
        try
        {
            TestUtils.sleepAndLog(2000);
            Process p = Runtime.getRuntime().exec("cmd.exe /c cd \"C:\\tmp\" & start cmd.exe /K \"multiPagePdfGenerate.bat");
            TestUtils.sleepAndLog(4000);

        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }

        Assert.assertTrue(checkFileExists(tempPath, defaultOutputFile));
        TestUtils.sleepAndLog(1000);
        Runtime.getRuntime().exec("taskkill /f /im cmd.exe") ;
        TestUtils.sleepAndLog(4000);
    }


    @Test(priority = 300)
    public void generateMultiPagePDFAtPath() throws IOException, InterruptedException
    {
        //particular path multi page
        boolean found=false;
        try
        {
            TestUtils.sleepAndLog(2000);
            Process p = Runtime.getRuntime().exec("cmd.exe /c cd \"C:\\tmp\" & start cmd.exe /K \"multiPagePdfGenerateSpecifiedPath.bat");
            TestUtils.sleepAndLog(4000);

        }

        catch(IOException ex)
        {
            ex.printStackTrace();
        }
        Assert.assertTrue(checkFileExistsWithWait("C:\\tmp\\ips-cli\\multipage_web2pdf.pdf"));
        TestUtils.sleepAndLog(1000);
        Runtime.getRuntime().exec("taskkill /f /im cmd.exe") ;

    }

    @Test(priority = 510)
    public void logoutFromMainPage()
    {
        result = mainPage.logout();
        Result.rcEquals(result, "0");
    }

    @Test(priority = 515)
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
        client.dashboardWS.deleteDashboard(sDashboard);
        driver.quit();
    }

}