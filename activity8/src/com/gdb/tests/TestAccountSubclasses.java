package com.gdb.tests;

import com.gdb.domain.*;
import com.gdb.exceptions.*;

public class TestAccountSubclasses {
    public static void main(String[] args) {
        System.out.println("=== Activity 8: Polymorphism Test ===");

        // NOTE: The domain classes in src/com/gdb/domain are provided complete (the
        // Activity 7 subclasses plus
        // their overridden withdraw() methods). Declare each account with the parent
        // type Account so that the
        // overridden withdraw() is chosen at runtime (dynamic method dispatch).

        // TODO: Step 1 - Test SavingsAccount minimum balance breach
        // Create a SavingsAccount (balance 10000.0, minBalance 1000.0), withdraw 9500.0
        // with the correct PIN,
        // catch MinimumBalanceViolationException and print [PASS]; print [FAIL] for any
        // other outcome.

        Account savings = new SavingsAccount("SA101", "Raiyan", 25, 10000.0, "ACTIVE", "1234", 1000.0, 4.0);
        try {
            savings.withdraw(9500.0, "1234");
            System.out.println("[Savings] Withdraw 9500 (breaches min balance 1000): FAIL");
        } catch (MinimumBalanceViolationException e) {
            System.out.println(
                    "[Savings] Withdraw 9500 (breaches min balance 1000): Caught MinimumBalanceViolationException [PASS]");
        } catch (Exception e) {
            System.out.println("[Savings] Withdraw 9500 (breaches min balance 1000): FAIL (" + e.getMessage() + ")");
        }

        // TODO: Step 2 - Test CurrentAccount valid withdrawal utilizing overdraft
        // facility
        // Create a CurrentAccount (balance 5000.0, overdraftLimit 25000.0), withdraw
        // 10000.0 with the correct PIN,
        // verify it succeeds (balance goes to -5000.0) and print [PASS].
        Account current = new CurrentAccount("CA201", "Bob", 30, 5000.0, "ACTIVE", "5678", 25000.0);
        try {
            current.withdraw(10000.0, "5678");
            if (current.getBalance() == -5000.0) {
                System.out.println("[Current] Withdraw with Overdraft (Balance goes to -5000): SUCCESS [PASS]");
            } else {
                System.out
                        .println("[Current] Withdraw with Overdraft (Balance goes to -5000): FAIL (Unexpected balance: "
                                + current.getBalance() + ")");
            }
        } catch (Exception e) {
            System.out.println(
                    "[Current] Withdraw with Overdraft (Balance goes to -5000): FAIL (" + e.getMessage() + ")");
        }

        // TODO: Step 3 - Test CurrentAccount exceeding overdraft limit
        // On the same account, withdraw 30000.0, catch InsufficientBalanceException and
        // print [PASS].
        try {
            current.withdraw(30000.0, "5678");
            System.out.println("[Current] Withdraw exceeding Overdraft (exceeds -25000): FAIL");
        } catch (InsufficientBalanceException e) {
            System.out.println(
                    "[Current] Withdraw exceeding Overdraft (exceeds -25000): Caught InsufficientBalanceException [PASS]");
        } catch (Exception e) {
            System.out
                    .println("[Current] Withdraw exceeding Overdraft (exceeds -25000): FAIL (" + e.getMessage() + ")");
        }

        // TODO: Step 4 - Test FixedDepositAccount premature withdrawal block
        // Create a FixedDepositAccount, attempt any withdrawal, catch AccountException
        // and print [PASS].
        Account fd = new FixedDepositAccount("FD301", "Charlie", 40, 50000.0, "ACTIVE", "9999", 2, 5.0);
        try {
            fd.withdraw(10000.0, "9999");
            System.out.println("[FixedDeposit] Withdraw attempt: FAIL");
        } catch (AccountException e) {
            System.out.println("[FixedDeposit] Withdraw attempt: Caught AccountException [PASS]");
        } catch (Exception e) {
            System.out.println("[FixedDeposit] Withdraw attempt: FAIL (" + e.getMessage() + ")");
        }

        System.out.println("All polymorphic behaviors verified!");

    }
}
