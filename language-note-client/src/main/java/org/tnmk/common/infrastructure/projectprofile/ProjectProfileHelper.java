package org.tnmk.common.infrastructure.projectprofile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ProjectProfileHelper {
    public static final String NO_PROFILE = "none";
    public static final Logger LOGGER = LoggerFactory.getLogger(ProjectProfileHelper.class);
    @Autowired
    private Environment env;
    private String[] usingProfiles = null;

    public ProjectProfileHelper() {

    }

    public String[] getUsingProfiles() {
        if (usingProfiles == null) {
            usingProfiles = env.getActiveProfiles();
            if (usingProfiles.length == 0) {
                usingProfiles = env.getDefaultProfiles();
                if (usingProfiles.length == 0) {
                    LOGGER.warn("There's something really wrong with your Spring Profile configuration. There's no information in both activeProfiles and defaultProfiles.");
                    usingProfiles = new String[]{NO_PROFILE};
                }
            }
        }
        return usingProfiles;
    }

    public String getFirstUsingProfile() {
        String[] profiles = getUsingProfiles();
        return profiles[0];
    }
}
