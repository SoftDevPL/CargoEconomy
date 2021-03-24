package wg.cargoeco.eco.cargoecocomy.database;

public class SQLManager {

    public Database database;

    public SQLManager(Database database) {
        this.database = database;
    }

    public void init() {
        this.database.init();
        this.database.connect();
    }

}
