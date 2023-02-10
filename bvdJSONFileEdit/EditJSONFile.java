package com.hp.opr.qa.framework.bvdJSONFileEdit;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
public class EditJSONFile {
    public static String editAPIKeyinJSON(String JSON, String property,String properyvalue) throws IOException {


        JsonObject jobject=null;
        try{
            File jsonFile = new File(JSON);
        String jsonString = FileUtils.readFileToString(jsonFile);
        JsonElement jelement = new JsonParser().parse(jsonString);
        jobject = jelement.getAsJsonObject();
        jobject.remove(property);
        jobject.addProperty(property,properyvalue);
        Gson gson = new Gson();
        String resultingJson = gson.toJson(jelement);
        FileUtils.writeStringToFile(jsonFile, resultingJson);
        System.out.println((int)JSON.trim().charAt(0));
        }catch(Exception e){
            e.printStackTrace();
        }return jobject.get(property).toString();
    }
}