package com.gravitee.gravitee.service;

import okhttp3.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * @author Philémon Globléhi <philemon.globlehi@gmail.com>
 */
@Service
public class OkhttpClientService {
    private final MediaType CONTENT_TYPE = MediaType.parse("application/json; charset=utf-8");

    @Value("${authentication.gravitee.token}")
    private String authenticationToken;

    @Value("${base.url.gravitee.api.management}")
    private String baseUrlApiManagement;

    private final OkHttpClient client = new OkHttpClient();
    private String url;

    public String post(Map<String, String> data, String endpoint) {
        url = this.buildUrl(endpoint);

        RequestBody body = RequestBody.create(CONTENT_TYPE, String.valueOf(new JSONObject(data)));
        Request request = new Request.Builder()
                .addHeader("Authorization", "Basic " + this.authenticationToken)
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return null != response ? response.body().string() : "";
        } catch (IOException ex) {
            return ex.getMessage();
        }
    }

    public String put(JSONObject data, String endpoint) {
        url = this.buildUrl(endpoint);

        RequestBody body = RequestBody.create(CONTENT_TYPE, data.toString());
        Request request = new Request.Builder()
                .addHeader("Authorization", "Basic " + this.authenticationToken)
                .url(url)
                .put(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return null != response ? response.body().string() : "";
        } catch (IOException ex) {
            return ex.getMessage();
        }
    }

    private String buildUrl(String endpoint) {
        return null == endpoint ? baseUrlApiManagement : baseUrlApiManagement +"/" + endpoint;
    }

}