package ru.nuyaxov.pzviewha.payload;

/**
 * DTO object for getting data about Sandbox and Server settings from PZ Server via API
 *
 */
public class ConfigInfo {

    private String configName; // Setting name of PZ server
    private String configValue; // Value of Setting of PZ server
    private String configDescription; // Description of setting

    /**
     * Get name of setting
     *
     * @return name of setting {@link String}
     * */
    public String getConfigName() {
        return configName;
    }

    /**
     * Set name of setting
     * @param configName  name of setting
     * */
    public void setConfigName(String configName) {
        this.configName = configName;
    }

    /**
     * Get value of setting
     *
     * @return value of setting {@link String}
     * */
    public String getConfigValue() {
        return configValue;
    }

    /**
     * Set value of setting
     * @param configValue  value of setting
     * */
    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    /**
     * Get description of setting
     *
     * @return description of setting {@link String}
     * */
    public String getConfigDescription() {
        return configDescription;
    }

    /**
     * Set description of setting
     * @param configDescription  description of setting
     * */
    public void setConfigDescription(String configDescription) {
        this.configDescription = configDescription;
    }
}

