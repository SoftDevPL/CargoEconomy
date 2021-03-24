package wg.cargoeco.eco.cargoecocomy.configs;

import lombok.Getter;
import wg.cargoeco.eco.cargoecocomy.configs.resourcesConfigGenerator.ConfigGenerator;

public class ConfigsManager {

    @Getter
    private EconomyConfiguration economyConfiguration;

    @Getter
    private ConfigGenerator configGenerator;

    public ConfigsManager() {
        this.configGenerator = new ConfigGenerator();
        this.economyConfiguration = new EconomyConfiguration();
    }

    public void init() {
        this.configGenerator.init();
        this.economyConfiguration.init();
    }

}
