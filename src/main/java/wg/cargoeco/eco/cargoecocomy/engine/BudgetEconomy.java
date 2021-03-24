package wg.cargoeco.eco.cargoecocomy.engine;

import com.sun.istack.internal.NotNull;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.OfflinePlayer;
import wg.cargoeco.eco.cargoecocomy.CargoEconomy;
import wg.cargoeco.eco.cargoecocomy.database.Database;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class BudgetEconomy implements Economy {
    public final static int FRACTIONAL_DIGIT = 2;
    public final static String CURRENCY_SYMBOL = "$";
    public final static String CURRENCY_PLURAL = "dolars";
    public final static String CURRENCY_SINGULAR = "dolar";
    private final List<Account> accounts = new ArrayList<>();
    private final List<Bank> banks = new ArrayList<>();
    private final Database database;

    public BudgetEconomy(){
        database = CargoEconomy.getInstance().getSqlManager().getDatabase();
    }


    @NotNull
    private Optional<Account> getAccount(UUID playerUUID) {
        return accounts.stream().filter(account -> account.getOwnerUUID().equals(playerUUID)).findAny();
    }

    @NotNull
    private Optional<Bank> getBankByName(String bankName) {
        return banks.stream().filter(bank -> bank.getBankName().equals(bankName)).findAny();
    }

    private void addAccountToDatabase(Account account) {
        //todo
    }

    private void updateAccountInDatabase(Account account) {
        //todo
    }

    private void addBankToDatabase(Bank bank) {
        //todo
    }

    private void updateBankMoneyInDatabase(Bank bank) {
        //todo
    }

    private void removeBankFromDatabase(Bank bank){
        //todo
    }

    private EconomyResponse noAccountResponse() {
        return new EconomyResponse(0, 0,
                EconomyResponse.ResponseType.FAILURE, "Player does not have account");
    }

    private EconomyResponse playerNotFoundResponse() {
        return new EconomyResponse(0, 0,
                EconomyResponse.ResponseType.FAILURE, "Player not found");
    }

    private EconomyResponse bankNotExistsResponse() {
        return new EconomyResponse(0, 0,
                EconomyResponse.ResponseType.FAILURE, "Bank does not exists");
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
        OfflinePlayer foundedPlayer = CargoEconomy.getOfflinePlayer(playerName);
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
        OfflinePlayer foundPlayer = CargoEconomy.getOfflinePlayer(playerName);
        if (foundPlayer == null) return 0;
        return getBalance(foundPlayer);
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
        OfflinePlayer foundPlayer = CargoEconomy.getOfflinePlayer(playerName);
        if (foundPlayer == null) return playerNotFoundResponse();
        return withdrawPlayer(foundPlayer, amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
        Account playerAccount = this.getAccount(player.getUniqueId()).orElse(null);

        //no account
        if (playerAccount == null)
            return noAccountResponse();

        //no money
        if (playerAccount.getMoney() < amount) return new EconomyResponse(0, playerAccount.getMoney(),
                EconomyResponse.ResponseType.FAILURE, "Player does not have enough money");

        //ok
        playerAccount.setMoney(playerAccount.getMoney() - amount);
        this.updateAccountInDatabase(playerAccount);
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
        OfflinePlayer foundedPlayer = CargoEconomy.getOfflinePlayer(playerName);
        if (foundedPlayer == null) return playerNotFoundResponse();
        return depositPlayer(foundedPlayer, amount);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
        Account playerAccount = this.getAccount(player.getUniqueId()).orElse(null);

        //no account
        if (playerAccount == null)
            return noAccountResponse();

        //ok
        playerAccount.addMoney(amount);
        this.updateAccountInDatabase(playerAccount);
        return new EconomyResponse(amount, playerAccount.getMoney(), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
        return depositPlayer(playerName, amount);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) {
        return depositPlayer(player, amount);
    }

    @Override
    public EconomyResponse createBank(String name, String player) {
        OfflinePlayer foundPlayer = CargoEconomy.getOfflinePlayer(player);
        if (foundPlayer == null) return playerNotFoundResponse();

        return createBank(name, foundPlayer);
    }

    @Override
    public EconomyResponse createBank(String name, OfflinePlayer player) {
        if (this.banks.stream().anyMatch(queried -> queried.getBankName().equals(name))) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE,
                    "Bank with name " + name + " already exists");
        }
        Bank bank = new Bank(player.getUniqueId(), name);
        this.banks.add(bank);
        this.addBankToDatabase(bank);
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse deleteBank(String name) {
        AtomicReference<Double> amount = new AtomicReference<>((double) 0);
        if (this.banks.removeIf(bank -> {
            if (bank.getBankName().equals(name)) {
                amount.set(bank.getMoney());
                this.removeBankFromDatabase(bank);
                return true;
            }
            return false;
        })) {
            return new EconomyResponse(amount.get(), 0, EconomyResponse.ResponseType.SUCCESS, "");
        }
        return bankNotExistsResponse();
    }

    @Override
    public EconomyResponse bankBalance(String name) {
        Bank bank = this.getBankByName(name).orElse(null);
        if (bank == null) return bankNotExistsResponse();
        return new EconomyResponse(0, bank.getMoney(), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse bankHas(String name, double amount) {
        Bank bank = this.getBankByName(name).orElse(null);

        //no bank
        if (bank == null) return bankNotExistsResponse();

        //no money
        if (bank.getMoney() < amount)
            return new EconomyResponse(0, bank.getMoney(), EconomyResponse.ResponseType.FAILURE,
                    "Bank has not enough money");

        //ok
        return new EconomyResponse(0, bank.getMoney(), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse bankWithdraw(String name, double amount) {
        Bank bank = this.getBankByName(name).orElse(null);
        if (bank == null) return bankNotExistsResponse();

        //no money
        if (bank.getMoney() < amount)
            return new EconomyResponse(0, bank.getMoney(), EconomyResponse.ResponseType.FAILURE,
                    "Bank has not enough money");

        //ok
        bank.addMoney(-amount);
        this.updateBankMoneyInDatabase(bank);
        return new EconomyResponse(amount, bank.getMoney(), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse bankDeposit(String name, double amount) {
        Bank bank = this.getBankByName(name).orElse(null);
        if (bank == null) return bankNotExistsResponse();

        bank.addMoney(amount);
        this.updateBankMoneyInDatabase(bank);
        return new EconomyResponse(amount, bank.getMoney(), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse isBankOwner(String name, String playerName) {
        OfflinePlayer foundPlayer = CargoEconomy.getOfflinePlayer(playerName);
        if (foundPlayer == null) return playerNotFoundResponse();

        return isBankOwner(name, foundPlayer);
    }

    @Override
    public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
        Bank bank = this.getBankByName(name).orElse(null);
        if (bank == null) return bankNotExistsResponse();

        return new EconomyResponse(0, bank.getMoney(), bank.getOwnerUUID().equals(player.getUniqueId()) ?
                EconomyResponse.ResponseType.SUCCESS : EconomyResponse.ResponseType.FAILURE, "");
    }

    @Override
    public EconomyResponse isBankMember(String name, String playerName) {
        OfflinePlayer foundPlayer = CargoEconomy.getOfflinePlayer(playerName);
        if (foundPlayer == null) return playerNotFoundResponse();

        return isBankMember(name, foundPlayer);
    }

    @Override
    public EconomyResponse isBankMember(String name, OfflinePlayer player) {
        Bank bank = this.getBankByName(name).orElse(null);
        if (bank == null) return bankNotExistsResponse();

        return new EconomyResponse(0, bank.getMoney(), bank.getMembersUUIDs().contains(player.getUniqueId()) ?
                EconomyResponse.ResponseType.SUCCESS : EconomyResponse.ResponseType.FAILURE, "");
    }

    @Override
    public List<String> getBanks() {
        return this.banks.stream().map(Bank::getBankName).collect(Collectors.toList());
    }

    @Override
    public boolean createPlayerAccount(String playerName) {
        OfflinePlayer foundPlayer = CargoEconomy.getOfflinePlayer(playerName);
        if (foundPlayer == null) return false;

        return createPlayerAccount(foundPlayer);
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player) {
        if(this.hasAccount(player)) return true;

        Account account = new Account(player.getUniqueId(), 0);
        this.accounts.add(account);
        this.addAccountToDatabase(account);
        return true;
    }

    @Override
    public boolean createPlayerAccount(String playerName, String worldName) {
        return createPlayerAccount(playerName);
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player, String worldName) {
        return createPlayerAccount(player);
    }
}
