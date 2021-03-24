package wg.cargoeco.eco.cargoecocomy.configs;

import lombok.Getter;

public class ConfigsManager {

    @Getter
    private EconomyConfiguration economyConfiguration;

    public ConfigsManager() {
        this.economyConfiguration = new EconomyConfiguration();
    }

    public void init() {
        this.economyConfiguration.init();
    }

}
