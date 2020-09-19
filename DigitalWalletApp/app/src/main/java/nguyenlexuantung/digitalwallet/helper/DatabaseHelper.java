package nguyenlexuantung.digitalwallet.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import nguyenlexuantung.digitalwallet.model.TransactionType;
import nguyenlexuantung.digitalwallet.model.TransactionTypeStatistics;
import nguyenlexuantung.digitalwallet.model.Transaction_;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "DigitalWallet.sql";
    public static final int VERSION = 3;

    private static final String TABLE_NAME = "Transaction_";


    // Context context;
    private static final String CREATE_TRANSACTION_TYPE = "CREATE TABLE IF NOT EXISTS TransactionType(TypeID INTEGER PRIMARY KEY, TypeName nvarchar(50))";
    private static final String CREATE_TRANSACTION = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + "(" +
            "TransactionID INTEGER PRIMARY KEY, " +
            "TransactionName nvarchar(50)," +
            "TransactionMoney INTEGER, " +
            "TransactionDate nvarchar(50), " +
            "TypeID INTEGER NOT NULL CONSTRAINT TypeID REFERENCES TransactionType(TypeID) ON DELETE CASCADE)";

    private static final String QUERY_GROUP_BY = "SELECT SUM(TransactionMoney) AS Money, TypeName FROM TransactionType a JOIN Transaction_ b ON a.TypeID = b.TypeID GROUP BY TypeName";
    private static final String ALTER_DATABASE = "ALTER TABLE Transacion_ ADD COLUMN test TEXT";

//    private static final String CREATE_DEFAULT_TYPE_1 = "INSERT INTO TransactionType(TypeID,TypeName) VALUES( 1 , 'Food');";
//    private static final String CREATE_DEFAULT_TYPE_2 = "INSERT INTO TransactionType(TypeID,TypeName) VALUES( 2 , 'Entertainment');";



    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TRANSACTION_TYPE);
        db.execSQL(CREATE_TRANSACTION);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 3) {
            db.execSQL(ALTER_DATABASE);
        }
    }


    public ArrayList<TransactionType> getAllTransactionType() {
        ArrayList<TransactionType> listType = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String sql = "SELECT * FROM TransactionType";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                TransactionType transactionType = new TransactionType(cursor.getInt(0), cursor.getString(1));
                listType.add(transactionType);
            } while (cursor.moveToNext());
        }
        return listType;
    }

    public void addTransactionType(TransactionType transactionType) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put("TypeID", transactionType.getType_id());
        values.put("TypeName", transactionType.getType_name());
        sqLiteDatabase.insert("TransactionType", null, values);
        sqLiteDatabase.close();
    }

    public boolean deleteTransactionType(int TypeID) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        long result = sqLiteDatabase.delete("TransactionType", "TypeID=?", new String[]{String.valueOf(TypeID)});
        if (result == -1) {
            sqLiteDatabase.close();
            return false;
        } else {
            sqLiteDatabase.close();
            return true;
        }
//        sqLiteDatabase.execSQL("DELETE FROM Transaction_ WHERE TransactionID= '" + TransactionID + "'");
    }

    public boolean updateTransactionType(TransactionType transactionType) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TypeName", transactionType.getType_name());
        long result = sqLiteDatabase.update("TransactionType", values, "TypeID=?", new String[]{String.valueOf(transactionType.getType_id())});
        if (result == -1) {
            sqLiteDatabase.close();
            return false;
        } else {
            sqLiteDatabase.close();
            return true;
        }
    }



    public List<Transaction_> getAllTransaction() {
        List<Transaction_> listTransaction = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String sql = "SELECT * FROM Transaction_";

        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                Transaction_ transaction_ = new Transaction_(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getInt(4)
                );
                listTransaction.add(transaction_);
            } while (cursor.moveToNext());
        }
        return listTransaction;
    }


    public ArrayList<Transaction_> getAllTransactionForSearch() {
        ArrayList<Transaction_> listTransaction = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String sql = "SELECT * FROM Transaction_";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                Transaction_ transaction_ = new Transaction_(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getInt(4)
                );
                listTransaction.add(transaction_);
            } while (cursor.moveToNext());
        }
        return listTransaction;
    }

    public boolean addTransaction(Transaction_ transaction_) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put("TransactionID", transaction_.getId_transaction());
        values.put("TransactionName", transaction_.getName_transaction());
        values.put("TransactionMoney", transaction_.getMoney_transaction());
        values.put("TransactionDate", transaction_.getDate_transaction());
        values.put("TypeID", transaction_.getType_id());

        long result = sqLiteDatabase.insert("Transaction_", null, values);

        if (result == -1) {
            sqLiteDatabase.close();
            return false;
        } else {
            sqLiteDatabase.close();
            return true;
        }
    }

    public boolean updateTransaction(Transaction_ transaction_) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TransactionName", transaction_.getName_transaction());
        values.put("TransactionMoney", transaction_.getMoney_transaction());
        values.put("TransactionDate", transaction_.getDate_transaction());
        values.put("TypeID", transaction_.getType_id());
        long result = sqLiteDatabase.update("Transaction_", values, "TransactionID=?", new String[]{String.valueOf(transaction_.getId_transaction())});
        if (result == -1) {
            sqLiteDatabase.close();
            return false;
        } else {
            sqLiteDatabase.close();
            return true;
        }
    }

    public boolean deleteTransaction(int TransactionID) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        long result = sqLiteDatabase.delete("Transaction_", "TransactionID=?", new String[]{String.valueOf(TransactionID)});
        if (result == -1) {
            sqLiteDatabase.close();
            return false;
        } else {
            sqLiteDatabase.close();
            return true;
        }
//        sqLiteDatabase.execSQL("DELETE FROM Transaction_ WHERE TransactionID= '" + TransactionID + "'");
//        sqLiteDatabase.close();
    }

    public ArrayList<TransactionTypeStatistics> getAllTransactionTypeStatistics() {
        ArrayList<TransactionTypeStatistics> transactionTypeStatisticsArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(QUERY_GROUP_BY, null);
        if (cursor.moveToFirst()) {
            do {
                TransactionTypeStatistics transactionTypeStatistics = new TransactionTypeStatistics(cursor.getInt(0), cursor.getString(1));
                transactionTypeStatisticsArrayList.add(transactionTypeStatistics);
            } while (cursor.moveToNext());
        }
        return transactionTypeStatisticsArrayList;
    }
}
