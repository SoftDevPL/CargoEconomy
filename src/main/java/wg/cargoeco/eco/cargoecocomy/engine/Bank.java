package wg.cargoeco.eco.cargoecocomy.engine;

import com.sun.istack.internal.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class Bank {
    @NotNull
    private UUID ownerUUID;
    private String bankName = "";
    private List<UUID> membersUUIDs = new ArrayList<>();
    private double money;

    public Bank(@NotNull UUID ownerUUID, String bankName, double initialMoney) {
        this.ownerUUID = ownerUUID;
        this.money = initialMoney;
        this.bankName = bankName;
    }

    public Bank(@NotNull UUID ownerUUID, String bankName) {
        this(ownerUUID, bankName, 0);
    }

    public void addMoney(double amount) {
        this.money += amount;
    }
}
