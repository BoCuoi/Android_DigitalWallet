package nguyenlexuantung.digitalwallet.model;

public class TransactionType {
    private String type_name;
    private int type_id;

    public TransactionType() {
    }

    public TransactionType(int type_id, String type_name) {
        this.type_id = type_id;
        this.type_name = type_name;
    }


    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    @Override
    public String toString() {
        return type_name;
    }
}
