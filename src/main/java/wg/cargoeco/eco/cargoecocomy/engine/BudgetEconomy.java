package wg.cargoeco.eco.cargoecocomy.engine;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import wg.cargoeco.eco.cargoecocomy.CargoEconomy;

import java.math.BigDecimal;
import java.util.*;

public class BudgetEconomy implements Economy {
    public final static int FRACTIONAL_DIGIT = 2;
    public final static String CURRENCY_SYMBOL = "$";
    public final static String CURRENCY_PLURAL = "dolars";
    public final static String CURRENCY_SINGULAR = "dolar";
    private final List<Account> accounts = new ArrayList<>();


    @Nullable
    private OfflinePlayer getOfflinePlayer(String name) {
        return Arrays.stream(Bukkit.getOfflinePlayers()).filter(player ->
                player.getName().equalsIgnoreCase(name)).findAny().orElse(null);
    }

    @NotNull
    private Optional<Account> getAccount(UUID playerUUID) {
        return accounts.stream().filter(account -> account.getOwnerUUID().equals(playerUUID)).findAny();
    }

    @Override
    public boolean isEnabled() {
        return CargoEconomy.getInstance() != null;
    }

    @Override
    public String getName() {
        return CargoEconomy.getInstance().getName();
    }

    @Override
    public boolean hasBankSupport() {
        return true;
    }

    @Override
    public int fractionalDigits() {
        return FRACTIONAL_DIGIT;
    }

    @Override
    public String format(double amount) {
        return BigDecimal.valueOf(Math.round(amount * 100) / 100).toPlainString() + CURRENCY_SYMBOL;
    }

    @Override
    public String currencyNamePlural() {
        return CURRENCY_PLURAL;
    }

    @Override
    public String currencyNameSingular() {
        return CURRENCY_SINGULAR;
    }

    @Override
    public boolean hasAccount(String playerName) {
        OfflinePlayer foundedPlayer = getOfflinePlayer(playerName);
        if (foundedPlayer == null) return false;
        return hasAccount(foundedPlayer);
    }

    @Override
    public boolean hasAccount(OfflinePlayer player) {
        return accounts.stream()
                .anyMatch(account -> account.getOwnerUUID().equals(player.getUniqueId()));
    }

    @Override
    public boolean hasAccount(String playerName, String worldName) {
        return hasAccount(playerName);
    }

    @Override
    public boolean hasAccount(OfflinePlayer player, String worldName) {
        return hasAccount(player);
    }

    @Override
    public double getBalance(String playerName) {
        OfflinePlayer foundedPlayer = getOfflinePlayer(playerName);
        if (foundedPlayer == null) return 0;
        return getBalance(foundedPlayer);
    }

    @Override
    public double getBalance(OfflinePlayer player) {
        return getAccount(player.getUniqueId()).orElse(new EmptyAccount()).getMoney();
    }

    @Override
    public double getBalance(String playerName, String world) {
        return getBalance(playerName);
    }

    @Override
    public double getBalance(OfflinePlayer player, String world) {
        return getBalance(player);
    }

    @Override
    public boolean has(String playerName, double amount) {
        return getBalance(playerName) >= amount;
    }

    @Override
    public boolean has(OfflinePlayer player, double amount) {
        return getBalance(player) >= amount;
    }

    @Override
    public boolean has(String playerName, String worldName, double amount) {
        return has(playerName, amount);
    }

    @Override
    public boolean has(OfflinePlayer player, String worldName, double amount) {
        return has(player, amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, double amount) {
        OfflinePlayer foundedPlayer = this.getOfflinePlayer(playerName);
        if (foundedPlayer == null) return new EconomyResponse(0, 0,
                EconomyResponse.ResponseType.FAILURE, "Player not found");
        return withdrawPlayer(foundedPlayer, amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
        Account playerAccount = this.getAccount(player.getUniqueId()).orElse(null);

        //no account
        if (playerAccount == null)
            return new EconomyResponse(0, 0,
                    EconomyResponse.ResponseType.FAILURE, "Player does not have account");

        //no money
        if (playerAccount.getMoney() < amount) return new EconomyResponse(0, playerAccount.getMoney(),
                EconomyResponse.ResponseType.FAILURE, "Player does not have enough money");

        //ok
        playerAccount.setMoney(playerAccount.getMoney() - amount);
        return new EconomyResponse(amount, playerAccount.getMoney(), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
        return withdrawPlayer(playerName, amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount) {
        return withdrawPlayer(player, amount);
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, double amount) {
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) {
        return null;
    }

    @Override
    public EconomyResponse createBank(String name, String player) {
        return null;
    }

    @Override
    public EconomyResponse createBank(String name, OfflinePlayer player) {
        return null;
    }

    @Override
    public EconomyResponse deleteBank(String name) {
        return null;
    }

    @Override
    public EconomyResponse bankBalance(String name) {
        return null;
    }

    @Override
    public EconomyResponse bankHas(String name, double amount) {
        return null;
    }

    @Override
    public EconomyResponse bankWithdraw(String name, double amount) {
        return null;
    }

    @Override
    public EconomyResponse bankDeposit(String name, double amount) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String name, String playerName) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String name, String playerName) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String name, OfflinePlayer player) {
        return null;
    }

    @Override
    public List<String> getBanks() {
        return null;
    }

    @Override
    public boolean createPlayerAccount(String playerName) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(String playerName, String worldName) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player, String worldName) {
        return false;
    }
}
