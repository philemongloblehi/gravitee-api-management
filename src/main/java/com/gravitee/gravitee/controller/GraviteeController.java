package com.gravitee.gravitee.controller;

import com.gravitee.gravitee.model.ApiRequest;
import com.gravitee.gravitee.model.PlanRequest;
import com.gravitee.gravitee.service.GraviteeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Philemon Globlehi <philemon.globlehi@gmail.com>
 */
@RestController
@RequestMapping(path = "/api/v1/rest/gravitee", name = "gravitee_")
public class GraviteeController {
    private final GraviteeClient graviteeClient;

    @Autowired
    public GraviteeController(GraviteeClient graviteeClient) {
        this.graviteeClient = graviteeClient;
    }

    @PostMapping(path = "/create/api", name = "create_api")
    @ResponseStatus(HttpStatus.CREATED)
    public String createApiRequest(@RequestBody ApiRequest apiRequest) {
        return this.graviteeClient.createApiRequest(
                apiRequest.getName(),
                apiRequest.getVersion(),
                apiRequest.getDescription(),
                apiRequest.getContextPath(),
                apiRequest.getEndpoint()
        );
    }

    @PostMapping(path = "/api/{apiId}/create/plan", name = "create_plan")
    @ResponseStatus(HttpStatus.CREATED)
    public String createPlanRequest(@RequestBody PlanRequest planRequest, @PathVariable String apiId) {
        return this.graviteeClient.createPlanRequest(
                planRequest.getName(),
                planRequest.getDescription(),
                planRequest.getValidation(),
                planRequest.getSecurity(),
                apiId

        );
    }

    @GetMapping(path = "/publish/plan/{planId}/api/{apiId}", name = "publish_plan")
    @ResponseStatus(HttpStatus.OK)
    public String publishPlanRequest(@PathVariable String planId, @PathVariable String apiId) {
        return this.graviteeClient.publishPlanRequest(
                apiId,
                planId
        );
    }

    @GetMapping(path = "/deploy/api/{apiId}", name = "deploy_api")
    @ResponseStatus(HttpStatus.OK)
    public String deployApiRequest(@PathVariable String apiId) {
        return this.graviteeClient.deployApiRequest(
                apiId
        );
    }

    @GetMapping(path = "/start/api/{apiId}", name = "start_api")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String startApiRequest(@PathVariable String apiId) {
        return this.graviteeClient.startApiRequest(
                apiId
        );
    }

    @PutMapping(path = "/publish/api/{apiId}", name = "publish_api")
    @ResponseStatus(HttpStatus.OK)
    public String publishApiOnApimPortalRequest(@RequestBody String body, @PathVariable String apiId) {
        Map<String, String> data = this.fromStringtoMap(body);
        return this.graviteeClient.publishApiOnApimPortalRequest(
                data,
                apiId
        );
    }

    private Map<String, String> fromStringtoMap(String data) {
        Map<String, String> hashMap = new HashMap<>();
        String[] contents = data.split(",");
        for (String content : contents) {
            String[] contentData;
            contentData =content.split(":");
            String key = contentData[0].trim();
            String value = contentData[1].trim();
            hashMap.put(key, value);
        }

        return hashMap;
    }



}
