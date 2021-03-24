package wg.cargoeco.eco.cargoecocomy.configs;

import wg.cargoeco.eco.cargoecocomy.configs.resourcesConfigGenerator.ConfigAccessor;

public class EconomyConfiguration extends ConfigAccessor {

    public String path = "Configurations.";
    private String currencySymbol;
    private String pluralSymbol;
    private String singularSymbol;


    public void init() {
        super.init("CargoConfiguration");
        this.currencySymbol = getStringPath(path + "currencySymbol");
        this.singularSymbol = getStringPath(path + "currencySymbol");
        this.pluralSymbol = getStringPath(path + "currencySymbol");
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public String getPluralSymbol() {
        return pluralSymbol;
    }

    public String getSingularSymbol() {
        return singularSymbol;
    }

}
