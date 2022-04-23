package com.gravitee.gravitee.service;

import com.gravitee.gravitee.exception.ResourceBadRequestException;
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

        String response = this.okhttpClientService.post(body, null);
        this.checkGraviteeResponse(response);

        return response;
    }

    public String createPlanRequest(String name, String description, String validation, String security, String apiId) {
        body.put("name",name);
        body.put("description",description);
        body.put("validation",validation);
        body.put("security",security);

        String response = this.okhttpClientService.post(body, apiId + "/plans");
        this.checkGraviteeResponse(response);

        return response;
    }

    public String publishPlanRequest(String apiId, String planId) {
        String response = this.okhttpClientService.post(null, apiId + "/plans/" + planId + "/_publish");
        this.checkGraviteeResponse(response);

        return response;
    }

    public String deployApiRequest(String apiId) {
        String response = this.okhttpClientService.post(null, apiId + "/deploy");
        this.checkGraviteeResponse(response);

        return response;
    }

    public String startApiRequest(String apiId) {
        return this.okhttpClientService.post(null, apiId + "?action=START");
    }

    public String publishApiOnApimPortalRequest(JSONObject data, String apiId) {
        data.put("lifecycle_state", "published");

        String response = this.okhttpClientService.put(data, apiId);
        this.checkGraviteeResponse(response);

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

    private int getErrorResponseStatusCode(String response) {
        JSONObject jsonObject = new JSONObject(response);
        return jsonObject.getInt("http_status");
    }

    private void checkGraviteeResponse(String response) {
        if (response != null) {
            if (this.isErrorResponse(response)) {
                int statusCode = this.getErrorResponseStatusCode(response);
                if (404 == statusCode) {
                    throw new ResourceBadRequestException(this.getErrorResponseMessage(response));
                }

                if (400 == statusCode) {
                    throw new ResourceBadRequestException(this.getErrorResponseMessage(response));
                }
            }
        }
    }
}
