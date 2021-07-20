package com.gravitee.gravitee.model;

import java.io.Serializable;

/**
 * @author Philémon Globléhi <philemon.globlehi@gmail.com>
 */
public class ApiRequest implements Serializable {
    private String name;
    private String version;
    private String description;
    private String contextPath;
    private String endpoint;

    public ApiRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

}
