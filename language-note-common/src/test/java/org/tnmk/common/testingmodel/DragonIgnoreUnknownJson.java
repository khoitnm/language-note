package org.tnmk.common.testingmodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DragonIgnoreUnknownJson extends Creature {
}
