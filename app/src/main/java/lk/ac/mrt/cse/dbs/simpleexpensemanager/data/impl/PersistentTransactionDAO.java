package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.Database_help;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class PersistentTransactionDAO extends Database_help implements TransactionDAO {
    public PersistentTransactionDAO(@Nullable Context context) {
        super(context);
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        Transaction transaction = new Transaction(date, accountNo, expenseType, amount);
        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(TRANSACTION_DATE, String.valueOf(date));
        values.put(ACCOUNT_NO, accountNo);
        values.put(AMOUNT, amount);
        values.put(EXPENSE_TYPE, String.valueOf(expenseType));

        database.insert(TRANSACTIONS, null, values);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public List<Transaction> getAllTransactionLogs() {
        SQLiteDatabase database = this.getReadableDatabase();
        List<Transaction> transactions = new ArrayList<>();

        String query_get_transactions = "SELECT * FROM " + TRANSACTIONS + ";";
        Cursor cur = database.rawQuery(query_get_transactions, null);

        if (cur.moveToFirst()) {
            do {
                String date = cur.getString(1);
                String account_no = cur.getString(2);
                Double amount = cur.getDouble(3);
                String type = cur.getString(4);
                ExpenseType expenseType = ExpenseType.valueOf(type);

                SimpleDateFormat date_format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                Date cur_date = null;

                try {
                    cur_date = date_format.parse(date);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Transaction transaction = new Transaction(cur_date, account_no, expenseType, amount);
                transactions.add(transaction);
            } while (cur.moveToNext());
        }
        cur.close();
        database.close();

        return transactions;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        SQLiteDatabase database = this.getReadableDatabase();
        List<Transaction> transactions = new ArrayList<>();
        String query_get_paginated = "SELECT * FROM " + TRANSACTIONS + " LIMIT " + limit + ";";
        Cursor cur = database.rawQuery(query_get_paginated, null);

        if (cur.moveToFirst()) {
            do {
                String date = cur.getString(1);
                String account_no = cur.getString(2);
                Double amount = cur.getDouble(3);
                String type = cur.getString(4);
                ExpenseType expenseType = ExpenseType.valueOf(type);

                SimpleDateFormat date_format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                Date cur_date = null;
                try {
                    cur_date = date_format.parse(date);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Transaction transaction = new Transaction(cur_date, account_no, expenseType, amount);
                transactions.add(transaction);
            } while (cur.moveToNext());
        }
        cur.close();
        database.close();

        return transactions;
    }
}
