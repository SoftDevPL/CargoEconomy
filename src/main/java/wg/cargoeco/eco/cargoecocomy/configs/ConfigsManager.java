package wg.cargoeco.eco.cargoecocomy.configs;

public class ConfigsManager {

    public EconomyConfiguration economyConfiguration;

    public ConfigsManager() {
        this.economyConfiguration = new EconomyConfiguration();
    }

    public void init() {
        this.economyConfiguration.init();
    }

}
