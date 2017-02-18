package tnmk.common.infrastructure.projectinfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;
import tnmk.common.infrastructure.projectprofile.ProjectProfileHelper;

import java.io.IOException;
import java.util.Properties;

@Service
public class ProjectInfoService {
    public static final Logger LOGGER = LoggerFactory.getLogger(ProjectInfoService.class);

    private final ProjectProfileHelper profileHelper;

    @Autowired
    public ProjectInfoService(ProjectProfileHelper profileHelper) {this.profileHelper = profileHelper;}

    public ProjectInfoProperties loadProjectInfoProperties() {
        String propertiesFile = "/build.properties";
        ProjectInfoProperties projectInfoProperties = new ProjectInfoProperties();
        projectInfoProperties.setProfiles(profileHelper.getUsingProfiles());
        try {
            Resource resource = new ClassPathResource(propertiesFile);
            Properties props = PropertiesLoaderUtils.loadProperties(resource);
            projectInfoProperties.setGitBuildNumber(props.getProperty("git.build.number"));
            projectInfoProperties.setProjectVersion(props.getProperty("project.version"));
            projectInfoProperties.setGitRevision(props.getProperty("git.revision"));
            projectInfoProperties.setGitTag(props.getProperty("git.tag"));
            projectInfoProperties.setProjectBuildTime(props.getProperty("project.build.time"));
        } catch (IOException e) {
            LOGGER.warn("Cannot load properties from {}. But don't worry, the program can start normally. You only need to recheck why the file is not generated automatically (configured by Maven plugin).", propertiesFile);
        }
        return projectInfoProperties;
    }
}
