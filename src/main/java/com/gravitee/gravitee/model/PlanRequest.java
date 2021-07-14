package com.gravitee.gravitee.model;

import java.io.Serializable;

/**
 * @author Philemon Globlehi <philemon.globlehi@veone.net>
 */
public class PlanRequest implements Serializable {
    private String name;
    private String description;
    private String validation;
    private String security;

    public PlanRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }
}
