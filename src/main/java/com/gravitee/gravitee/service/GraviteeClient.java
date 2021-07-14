package com.gravitee.gravitee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
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

        return this.okhttpClientService.post(body, null);
    }

    public String createPlanRequest(String name, String description, String validation, ArrayList<String> characteristics, Map<String, ArrayList<String>> paths, String security, String apiId) throws IOException {
        body.put("name",name);
        body.put("description",description);
        body.put("validation",validation);
        body.put("characteristics",characteristics.toString());
        body.put("paths",paths.toString());
        body.put("security",security);

        return this.okhttpClientService.post(body, apiId + "/plans");
    }

    public String publishPlanRequest(String apiId, String planId) throws IOException {
        return this.okhttpClientService.post(null, apiId + "/plans/" + planId + "/_publish");
    }

    public String deployApiRequest(String apiId) throws IOException {
        return this.okhttpClientService.post(null, apiId + "/deploy");
    }

    public String startApiRequest(String apiId) throws IOException {
        return this.okhttpClientService.post(null, apiId + "?action=START");
    }

    public String publishApiOnApimPortalRequest(Map<String, Object> data, String apiId) throws IOException {
        data.put("lifecyle_state", "published");

        return this.okhttpClientService.put(data, apiId);
    }
}
