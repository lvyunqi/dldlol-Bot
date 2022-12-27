package com.mryunqi.qimenbot.Controller;

public class API_Auth {
    public static boolean ApiKeyAuth(String apiKey,String LocalApiKey){
        return (apiKey).equals(LocalApiKey);
    }
}
