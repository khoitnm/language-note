package org.tnmk.ln.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.tnmk.common.infrastructure.projectprofile.ProjectProfileUtil;

/**
 * We will need this class when changing this project package from 'jar' to 'war'
 */
public class ServletInitializer extends SpringBootServletInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServletInitializer.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.profiles(ProjectProfileUtil.getDefaultProfile()).sources(WebApplication.class);
    }
}
