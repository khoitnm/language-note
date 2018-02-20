package org.tnmk.ln.app.aggregation.topic.model;

import org.hibernate.validator.constraints.NotBlank;

public class MemorizeExpressionInWebPageRequest {
    private String locale;
    @NotBlank
    private String expression;
    @NotBlank
    private String pageTitle;
    @NotBlank
    private String pageUrl;

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
