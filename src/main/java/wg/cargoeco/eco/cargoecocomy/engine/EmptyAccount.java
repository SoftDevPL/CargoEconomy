package wg.cargoeco.eco.cargoecocomy.engine;

import java.util.UUID;

public class EmptyAccount extends Account {
    public EmptyAccount() {
        super(UUID.fromString("00000000-0000-0000-0000-000000000000"), 0);
    }
}
