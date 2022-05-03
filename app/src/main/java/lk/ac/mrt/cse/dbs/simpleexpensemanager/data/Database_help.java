package lk.ac.mrt.cse.dbs.simpleexpensemanager.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class Database_help extends SQLiteOpenHelper {
    public static final String ACCOUNTS = "Account_Table";
    public static final String TRANSACTIONS = "Transaction_Table";
    public static final String ACCOUNT_NAME = "Account_Holder_Name";
    public static final String BANK = "Bank_Name";
    public static final String BALANCE = "Balance";
    public static final String TRANSACTION_ID = "ID";
    public static final String ACCOUNT_NO = "Account_ID";
    public static final String TRANSACTION_DATE = "Date";
    public static final String TRANSACTION_ACCOUNT = "transaction_account_ID";
    public static final String AMOUNT = "Amount";
    public static final String EXPENSE_TYPE = "Expense_Type";

    public Database_help(@Nullable Context context) {
        super(context, "190097J.db", null, 1);
    }

    //when there is no database. when first run the code this method will be called and create the database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query_accounts = "CREATE TABLE " + ACCOUNTS + "(" + ACCOUNT_NO + " TEXT PRIMARY KEY," + BANK + " TEXT," + ACCOUNT_NAME + " TEXT," + BALANCE + " REAL )";
        String query_transactions = "CREATE TABLE " + TRANSACTIONS + "(" + TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TRANSACTION_DATE + " DATE, " + TRANSACTION_ACCOUNT + " REFERENCES ACCOUNT_TABLE(Account_ID) , " + AMOUNT + " REAL, " + EXPENSE_TYPE + " TEXT )";

        sqLiteDatabase.execSQL(query_accounts);
        sqLiteDatabase.execSQL(query_transactions);
    }

    //After creating ,used for the upgrade and modify database
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
