package org.tnmk.common.infrastructure.projectinfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tnmk.common.utils.json.JsonUtils;

@Configuration
public class ProjectInfoConfiguration {
    public static final Logger LOGGER = LoggerFactory.getLogger(ProjectInfoConfiguration.class);

    @Autowired
    private ProjectInfoService projectInfoService;

    @Bean(name = "projectInfoProperties")
    public ProjectInfoProperties projectInfoProperties() {
        LOGGER.debug("Load Project Info");
        ProjectInfoProperties projectInfoProperties = projectInfoService.loadProjectInfoProperties();
        LOGGER.debug("Loaded project info: \n" + JsonUtils.toStringMultiLine(projectInfoProperties));
        return projectInfoProperties;
    }
}
