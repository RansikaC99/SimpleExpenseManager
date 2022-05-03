package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.Database_help;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

public class PersistentAccountDAO extends Database_help implements AccountDAO {
    public PersistentAccountDAO(@Nullable Context context) {
        super(context);
    }

    @Override
    public List<String> getAccountNumbersList() {
        SQLiteDatabase database = this.getReadableDatabase();
        List<String> account_list = new ArrayList<>();

        String query_get_accounts = "SELECT " + ACCOUNT_NO + " FROM " + ACCOUNTS + ";";
        Cursor cur = database.rawQuery(query_get_accounts, null);

        if (cur.moveToFirst()) {
            do {
                String account_no = cur.getString(0);
                account_list.add(account_no);
            } while (cur.moveToNext());
        }
        cur.close();

        return account_list;
    }

    @Override
    public List<Account> getAccountsList() {
        SQLiteDatabase database = this.getReadableDatabase();
        List<Account> account_list = new ArrayList<>();

        String query_get_account_list = "SELECT * FROM " + ACCOUNTS + ";";
        Cursor cur = database.rawQuery(query_get_account_list, null);


        if (cur.moveToFirst()) {
            do {
                String account_no = cur.getString(0);
                String bank = cur.getString(1);
                String account_name = cur.getString(2);
                Double balance = cur.getDouble(3);
                account_list.add(new Account(account_no, bank, account_name, balance));
            } while (cur.moveToNext());
        }
        cur.close();

        return account_list;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        SQLiteDatabase database = this.getReadableDatabase();
        Account account;

        String query_get_account = "SELECT * FROM " + ACCOUNTS + " WHERE " + ACCOUNT_NO + " =\"" + accountNo + "\";";
        Cursor cur = database.rawQuery(query_get_account, null);

        if (cur.moveToFirst()) {
            String account_no = cur.getString(0);
            String bank = cur.getString(1);
            String account_name = cur.getString(2);
            Double balance = cur.getDouble(3);
            account = new Account(account_no, bank, account_name, balance);
        } else {
            account = null;
        }
        cur.close();

        return account;
    }

    @Override
    public void addAccount(Account account) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ACCOUNT_NO, account.getAccountNo());
        values.put(BANK, account.getBankName());
        values.put(ACCOUNT_NAME, account.getAccountHolderName());
        values.put(BALANCE, account.getBalance());

        long insert = database.insert(ACCOUNTS, null, values);
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ACCOUNT_NO, accountNo);

        long delete = database.delete(ACCOUNTS, ACCOUNT_NO + "=?", new String[]{accountNo});
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        SQLiteDatabase database = this.getWritableDatabase();

        Account account = this.getAccount(accountNo);
        double balance = account.getBalance();

        if (expenseType == expenseType.INCOME){
            balance = balance + amount;
        } else {
            balance = balance - amount;
        }

        String query_update = "UPDATE " + ACCOUNTS + " SET " + BALANCE + " = " + balance + " WHERE " + ACCOUNT_NO + " = \"" + accountNo +"\";";
        database.execSQL(query_update);
        database.close();
    }
}
