package com.gravitee.gravitee.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Philémon Globléhi <philemon.globlehi@gmail.com>
 */
@Service
public class GraviteeClient {

    private final OkhttpClientService okhttpClientService;
    private final Map<String, String> body = new HashMap<>();

    @Autowired
    public GraviteeClient(OkhttpClientService okhttpClientService) {
        this.okhttpClientService = okhttpClientService;
    }

    public String createApiRequest(String name, String version, String description, String contextPath, String endpoint) {
        body.put("name", name);
        body.put("version", version);
        body.put("description", description);
        body.put("contextPath", contextPath);
        body.put("endpoint", endpoint);

        return this.okhttpClientService.post(body, null);
    }

    public String createPlanRequest(String name, String description, String validation, String security, String apiId) {
        body.put("name",name);
        body.put("description",description);
        body.put("validation",validation);
        body.put("security",security);

        return this.okhttpClientService.post(body, apiId + "/plans");
    }

    public String publishPlanRequest(String apiId, String planId) {
        return this.okhttpClientService.post(null, apiId + "/plans/" + planId + "/_publish");
    }

    public String deployApiRequest(String apiId) {
        return this.okhttpClientService.post(null, apiId + "/deploy");
    }

    public String startApiRequest(String apiId) {
        return this.okhttpClientService.post(null, apiId + "?action=START");
    }

    public String publishApiOnApimPortalRequest(JSONObject data, String apiId) {
        data.put("lifecycle_state", "published");

        return this.okhttpClientService.put(data, apiId);
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
