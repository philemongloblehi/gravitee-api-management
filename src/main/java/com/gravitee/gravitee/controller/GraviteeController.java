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
                "/myfirstapiTest",
                "https://api.gravitee.io/echo"
        );
    }

    public String createPlanRequest() throws IOException {
        ArrayList<String> characteristics = new ArrayList<>();
        Map<String, String> paths = new HashMap<>();
        paths.put("/", "");
        return this.graviteeClient.createPlanRequest(
                "My Plan",
                "Unlimited access plan",
                "auto",
                characteristics,
                paths,
                "api_key",
                "apiId"

        );
    }

    public String publishPlanRequest() {
        return null;
    }

    public String deployApiRequest() {
        return null;
    }

    public String startApiRequest() {
        return null;
    }

    public String publishApiOnApimPortalRequest() {
        return null;
    }


}
