package test;
import models.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.impl.Account;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {

    private Account account;

    @BeforeEach
    public void setup() {
        account = new Account();
    }

    @Test
    public void testDepositUpdatesBalanceAndAddsTransaction() {
        account.deposit(1000);
        account.deposit(500);

        assertEquals(2, getTransactionCount(account));
        assertEquals(1500, getCurrentBalance(account));
    }

    @Test
    public void testWithdrawUpdatesBalanceAndAddsTransaction() {
        account.deposit(1000);
        account.withdraw(300);

        assertEquals(2, getTransactionCount(account));
        assertEquals(700, getCurrentBalance(account));
    }

    @Test
    public void testPrintStatementShowsCorrectFormat() {

        Transaction t1 = new Transaction();
        t1.setAmount(1000);
        t1.setBalance(1000);
        t1.setDate(LocalDate.of(2012, 1, 10));

        Transaction t2 = new Transaction();
        t2.setAmount(2000);
        t2.setBalance(3000);
        t2.setDate(LocalDate.of(2012, 1, 13));

        Transaction t3 = new Transaction();
        t3.setAmount(-500);
        t3.setBalance(2500);
        t3.setDate(LocalDate.of(2012, 1, 14));

        getTransactions(account).add(t1);
        getTransactions(account).add(t2);
        getTransactions(account).add(t3);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        account.printStatement();

        String expected =
                "Date       || Amount     || Balance   \n" +
                        "14-01-2012 || -500       || 2500      \n" +
                        "13-01-2012 || 2000       || 3000      \n" +
                        "10-01-2012 || 1000       || 1000      \n";

        String output = outContent.toString().trim();

        assertTrue(output.contains("14-01-2012"));
        assertTrue(output.contains("1000"));
        assertTrue(output.contains("Balance"));
    }

    private int getTransactionCount(Account account) {
        return getTransactions(account).size();
    }

    private ArrayList<Transaction> getTransactions(Account account) {
        try {
            var field = Account.class.getDeclaredField("transactions");
            field.setAccessible(true);
            return (ArrayList<Transaction>) field.get(account);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private int getCurrentBalance(Account account) {
        try {
            var field = Account.class.getDeclaredField("currentBalance");
            field.setAccessible(true);
            return (int) field.get(account);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
