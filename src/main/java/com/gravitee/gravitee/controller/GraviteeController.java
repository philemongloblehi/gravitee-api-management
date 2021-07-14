package com.gravitee.gravitee.controller;

import com.gravitee.gravitee.service.GraviteeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/rest/gravitee", name = "gravitee_")
public class GraviteeController {
    private final GraviteeClient graviteeClient;

    @Autowired
    public GraviteeController(GraviteeClient graviteeClient) {
        this.graviteeClient = graviteeClient;
    }

    @GetMapping(path = "/create/api", name = "create_api")
    public String createApiRequest() throws IOException {
        return this.graviteeClient.createApiRequest(
                "My first API",
                "1",
                "Gravitee.io Echo API Proxy",
                "/test02",
                "https://api.gravitee.io/echo"
        );
    }

    @GetMapping(path = "/create/request", name = "create_plan")
    public String createPlanRequest() throws IOException {
        ArrayList<String> characteristics = new ArrayList<>();
        characteristics.add("");
        Map<String, ArrayList<String>> paths = new HashMap<>();
        ArrayList<String> content = new ArrayList<>();
        content.add("null");

        return this.graviteeClient.createPlanRequest(
                "My Plan",
                "Unlimited access plan",
                "auto",
                "api_key",
                "f5f73c01-4d91-406d-b73c-014d91206d90"

        );
    }

    @GetMapping(path = "/publish/plan", name = "publish_plan")
    public String publishPlanRequest() throws IOException {
        return this.graviteeClient.publishPlanRequest(
                "f5f73c01-4d91-406d-b73c-014d91206d90",
                "1ce0b909-a1f4-4368-a0b9-09a1f45368f2"
        );
    }

    @GetMapping(path = "/deploy/api", name = "deploy_api")
    public String deployApiRequest() throws IOException {
        return this.graviteeClient.deployApiRequest(
                "f5f73c01-4d91-406d-b73c-014d91206d90"
        );
    }

    @GetMapping(path = "/start/api", name = "start_api")
    public String startApiRequest() throws IOException {
        return this.graviteeClient.startApiRequest(
                "f5f73c01-4d91-406d-b73c-014d91206d90"
        );
    }

    @GetMapping(path = "/publish/api", name = "publish_api")
    public String publishApiOnApimPortalRequest() {
        return null;
    }


}
