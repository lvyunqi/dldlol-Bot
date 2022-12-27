package com.mryunqi.qimenbot.Controller;

import com.baidubce.http.ApiExplorerClient;
import com.baidubce.http.HttpMethodName;
import com.baidubce.model.ApiExplorerRequest;
import com.baidubce.model.ApiExplorerResponse;
import com.mryunqi.qimenbot.Util.BaiduApiAuth;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
public class RestAPI {

    public static ResponseEntity<String> comic(String type, String page, String size) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.apiopen.top/api/getImages?type={type}&page={page}&size={size}";
        Map<String, String> params = new HashMap<>();
        params.put("type", type);
        params.put("page", page);
        params.put("size", size);
        return restTemplate.getForEntity(url, String.class,params);
    }

    /**
     * UCloud API
     * @param imageUrl
     * @return pass-放行， forbid-封禁， check-人工审核
     */
    public static String PicCheck(String imageUrl) {
        String token = BaiduApiAuth.getAuth();
        String url = "https://aip.baidubce.com/rest/2.0/solution/v1/img_censor/v2/user_defined";
        ApiExplorerRequest request = new ApiExplorerRequest(HttpMethodName.POST, url);
        // 设置header参数
        request.addHeaderParameter("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        // 设置jsonBody参数
        String jsonBody = "imgUrl="+imageUrl;
        request.setJsonBody(jsonBody);
        // 设置query参数
        request.addQueryParameter("access_token", token);
        ApiExplorerClient client = new ApiExplorerClient();

        try {
            ApiExplorerResponse response = client.sendRequest(request);
            // 返回结果格式为Json字符串
            return response.getResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String GetPicTui (String link) throws IOException {
        try {
            String url = link;
            URL serverUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) serverUrl
                    .openConnection();
            conn.setRequestMethod("GET");
            conn.setInstanceFollowRedirects(false);

            conn.addRequestProperty("Accept-Charset", "UTF-8;");
            conn.addRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.8) Firefox/3.6.8");
            conn.addRequestProperty("Referer", url);
            conn.connect();
            String location = conn.getHeaderField("Location");
            return location;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
