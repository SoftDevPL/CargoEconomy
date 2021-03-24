package wg.cargoeco.eco.cargoecocomy.engine;

import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Account {
    @NotNull
    private UUID ownerUUID;
    private double money;
}
