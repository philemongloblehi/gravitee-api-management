package com.gravitee.gravitee.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Philémon Globléhi <philemon.globlehi@veone.net>
 */
@Service
public class GraviteeClient {

    private final OkhttpClientService okhttpClientService;
    private final Map<String, String> body = new HashMap<>();

    @Autowired
    public GraviteeClient(OkhttpClientService okhttpClientService) {
        this.okhttpClientService = okhttpClientService;
    }

    public String createApiRequest(String name, String version, String description, String contextPath, String endpoint) throws IOException {
        body.put("name", name);
        body.put("version", version);
        body.put("description", description);
        body.put("contextPath", contextPath);
        body.put("endpoint", endpoint);

        String response = this.okhttpClientService.post(body, null);
        if (this.isErrorResponse(response)) {
            return this.getErrorResponseMessage(response);
        }

        return response;
    }

    public String createPlanRequest(String name, String description, String validation, String security, String apiId) throws IOException {
        body.put("name",name);
        body.put("description",description);
        body.put("validation",validation);
        body.put("security",security);

        return this.okhttpClientService.post(body, apiId + "/plans");
    }

    public String publishPlanRequest(String apiId, String planId) throws IOException {
        String response = this.okhttpClientService.post(null, apiId + "/plans/" + planId + "/_publish");
        if (this.isErrorResponse(response)) {
            return this.getErrorResponseMessage(response);
        }

        return response;
    }

    public String deployApiRequest(String apiId) throws IOException {
        String response = this.okhttpClientService.post(null, apiId + "/deploy");
        if (this.isErrorResponse(response)) {
            return this.getErrorResponseMessage(response);
        }

        return response;
    }

    public String startApiRequest(String apiId) throws IOException {
        String response = this.okhttpClientService.post(null, apiId + "?action=START");
        if (this.isErrorResponse(response)) {
            return this.getErrorResponseMessage(response);
        }

        return response;
    }

    public String publishApiOnApimPortalRequest(Map<String, Object> data, String apiId) throws IOException {
        data.put("lifecyle_state", "published");

        String response = this.okhttpClientService.put(data, apiId);
        if (this.isErrorResponse(response)) {
            return this.getErrorResponseMessage(response);
        }

        return response;
    }

    private boolean isErrorResponse(String response) {
        JSONObject jsonObject = new JSONObject(response);
        return jsonObject.has("message") && jsonObject.has("http_status");
    }

    private String getErrorResponseMessage(String response) {
        JSONObject jsonObject = new JSONObject(response);
        return jsonObject.getString("message");
    }
}
