package com.gravitee.gravitee.controller;

import com.gravitee.gravitee.model.ApiRequest;
import com.gravitee.gravitee.model.PlanRequest;
import com.gravitee.gravitee.service.GraviteeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Philemon Globlehi <philemon.globlehi@veone.net>
 */
@RestController
@RequestMapping(path = "/api/v1/rest/gravitee", name = "gravitee_")
public class GraviteeController {
    private final GraviteeClient graviteeClient;

    @Autowired
    public GraviteeController(GraviteeClient graviteeClient) {
        this.graviteeClient = graviteeClient;
    }

    @GetMapping(path = "/create/api", name = "create_api")
    public String createApiRequest(@RequestBody ApiRequest apiRequest) throws IOException {
        return this.graviteeClient.createApiRequest(
                apiRequest.getName(),
                apiRequest.getVersion(),
                apiRequest.getDescription(),
                apiRequest.getContextPath(),
                apiRequest.getEndpoint()
        );
    }

    @GetMapping(path = "/create/api/{apiId}/plan", name = "create_plan")
    public String createPlanRequest(@RequestBody PlanRequest planRequest, @PathVariable String apiId) throws IOException {
        return this.graviteeClient.createPlanRequest(
                planRequest.getName(),
                planRequest.getDescription(),
                planRequest.getValidation(),
                planRequest.getSecurity(),
                apiId

        );
    }

    @GetMapping(path = "/publish/plan/{planId}/api/{apiId}", name = "publish_plan")
    public String publishPlanRequest(@PathVariable String planId, @PathVariable String apiId) throws IOException {
        return this.graviteeClient.publishPlanRequest(
                apiId,
                planId
        );
    }

    @GetMapping(path = "/deploy/api/{apiId}", name = "deploy_api")
    public String deployApiRequest(@PathVariable String apiId) throws IOException {
        return this.graviteeClient.deployApiRequest(
                apiId
        );
    }

    @GetMapping(path = "/start/api/{apiId}", name = "start_api")
    public String startApiRequest(@PathVariable String apiId) throws IOException {
        return this.graviteeClient.startApiRequest(
                apiId
        );
    }

    @GetMapping(path = "/publish/api/{apiId}", name = "publish_api")
    public String publishApiOnApimPortalRequest(@PathVariable String apiId) throws IOException {
        Map<String, Object> data = new HashMap<>();
        return this.graviteeClient.publishApiOnApimPortalRequest(
                data,
                apiId
        );
    }


}
