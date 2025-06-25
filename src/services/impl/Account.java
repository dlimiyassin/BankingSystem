package services.impl;

import models.Transaction;
import services.facade.AccountService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Account implements AccountService {

    private final ArrayList<Transaction> transactions = new ArrayList<>();
    private int currentBalance = 0;

    @Override
    public void deposit(int amount) {

        if (amount <= 0) throw new IllegalArgumentException("The amount must be positive in Deposit Operation");

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        currentBalance += amount;
        transaction.setBalance(currentBalance);
        transaction.setDate(LocalDate.now());  // i did set the date here because you instruct to avoid change the public interface
        transactions.add(transaction);
    }

    @Override
    public void withdraw(int amount) {

        if (amount <= 0) throw new IllegalArgumentException("The amount must be positive in withdraw Operation");
        if (amount > currentBalance) throw new IllegalStateException("the desired amount not found, your current balance is : " + currentBalance);  // i'm not sure if i should tell them their current amount

        Transaction transaction = new Transaction();
        transaction.setAmount(-amount);
        currentBalance -= amount;
        transaction.setBalance(currentBalance);
        transaction.setDate(LocalDate.now());
        transactions.add(transaction);
    }

    @Override
    public void printStatement() {
        StringBuilder statement = new StringBuilder();
        if (transactions.isEmpty()){
            statement.append("There are no transactions as of now.");
        }else {
            List<Transaction> reversed = new ArrayList<>(transactions); // i used this to avoid changing the internal list in order to avoid reverse the list twice
            Collections.reverse(reversed);
            statement.append(String.format("%-10s || %-10s || %-10s","Date","Amount","Balance")).append("\n");
            reversed.forEach(transaction -> {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                statement.append(String.format("%-10s || %-10d || %-10d",transaction.getDate().format(formatter),transaction.getAmount(),transaction.getBalance())).append("\n");
            });
        }
        System.out.println(statement);
    }

}
