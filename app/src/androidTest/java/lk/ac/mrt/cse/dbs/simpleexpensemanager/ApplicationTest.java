/*
 * Copyright 2015 Department of Computer Science and Engineering, University of Moratuwa.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *                  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package lk.ac.mrt.cse.dbs.simpleexpensemanager;

import static junit.framework.Assert.assertTrue;

import android.app.Application;
import android.content.Context;
import android.test.ApplicationTestCase;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.ExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.PersistentExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest {
    private static ExpenseManager expenseManager;

    @Before
    public void initial() throws ExpenseManagerException {
        Context context = ApplicationProvider.getApplicationContext();
        expenseManager = new PersistentExpenseManager(context);
    }
    @Test
    public void addAccount(){
        expenseManager.addAccount("331145","BOC","Ransika",5000.00);
        expenseManager.addAccount("1122","LOLC","Ravi",4500.00);
        List<String> accountList = expenseManager.getAccountNumbersList();
        assertTrue(accountList.contains("331145"));
        assertTrue(accountList.contains("1122"));
    }
    @Test
    public void transactionIncome() throws InvalidAccountException {;
        expenseManager.addAccount("1122","LOLC","Ravi",4500.00);

        double oldBalance = expenseManager.getAccountsDAO().getAccount("1122").getBalance();

        expenseManager.updateAccountBalance("1122",11,5,2022, ExpenseType.INCOME,"2500");

        double currentBalance = expenseManager.getAccountsDAO().getAccount("1122").getBalance();

        assertTrue(currentBalance-oldBalance==2500.00);
    }

    @Test
    public void transactionExpense() throws InvalidAccountException {;
        expenseManager.addAccount("1122","LOLC","Ravi",4500.00);

        double oldBalance = expenseManager.getAccountsDAO().getAccount("1122").getBalance();

        expenseManager.updateAccountBalance("1122",11,5,2022, ExpenseType.EXPENSE,"2500");

        double currentBalance = expenseManager.getAccountsDAO().getAccount("1122").getBalance();

        assertTrue(oldBalance-currentBalance==2500.00);
        

    }

}