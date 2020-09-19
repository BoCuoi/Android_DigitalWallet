package nguyenlexuantung.digitalwallet.model;

import androidx.annotation.Nullable;

import java.util.Date;

public class Transaction_ {
    private int id_transaction;
    private String name_transaction;
    private int money_transaction;
    private String date_transaction;
    private int type_id;


    public Transaction_() {
    }

    public Transaction_(int id_transaction, String name_transaction, int money_transaction, String date_transaction, int type_id) {
        this.id_transaction = id_transaction;
        this.name_transaction = name_transaction;
        this.money_transaction = money_transaction;
        this.date_transaction = date_transaction;
        this.type_id = type_id;
    }

    public int getId_transaction() {
        return id_transaction;
    }

    public void setId_transaction(int id_transaction) {
        this.id_transaction = id_transaction;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getName_transaction() {
        return name_transaction;
    }

    public void setName_transaction(String name_transaction) {
        this.name_transaction = name_transaction;
    }

    public int getMoney_transaction() {
        return money_transaction;
    }

    public void setMoney_transaction(int money_transaction) {
        this.money_transaction = money_transaction;
    }

    public String getDate_transaction() {
        return date_transaction;
    }

    public void setDate_transaction(String date_transaction) {
        this.date_transaction = date_transaction;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != getClass()) return false;

        Transaction_ transaction = (Transaction_) obj;

        return id_transaction == transaction.id_transaction;
    }

}
