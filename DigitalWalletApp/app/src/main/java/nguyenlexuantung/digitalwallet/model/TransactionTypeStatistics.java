package nguyenlexuantung.digitalwallet.model;

public class TransactionTypeStatistics {
    private int money;
    private String type_name;

    public TransactionTypeStatistics() {
    }

    public TransactionTypeStatistics(int money, String type_name) {
        this.money = money;
        this.type_name = type_name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

}
