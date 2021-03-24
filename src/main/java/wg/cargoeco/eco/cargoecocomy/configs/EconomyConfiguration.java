package wg.cargoeco.eco.cargoecocomy.configs;

import wg.cargoeco.eco.cargoecocomy.configs.resourcesConfigGenerator.ConfigAccessor;

public class EconomyConfiguration extends ConfigAccessor {

    public String path = "Configuration.";
    private String currencySymbol;
    private String pluralSymbol;
    private String singularSymbol;


    public void init() {
        super.init("CargoConfiguration");
        this.currencySymbol = getStringPath(path + "currencySymbol");
        this.singularSymbol = getStringPath(path + "singularSymbol");
        this.pluralSymbol = getStringPath(path + "pluralSymbol");
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
