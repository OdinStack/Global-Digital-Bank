class Account{
  static final double MIN_BALANCE_SAVINGS = 500.0;
  private static final double MIN_BALANCE_CURRENT = 1000.0;
  private static final int MIN_AGE = 18;
  private static final int MIN_PIN = 1000;
  private static final int MAX_PIN = 9999;

  private int accountNumber;
  private String name;
  private int age;
  private double balance;
  private String accountType;
  private String status;
  private Integer pin;


  public Account(int accountNumber, String name, int age, double initialBalance, String accountType)throws IllegalArgumentException {
    this.accountNumber = accountNumber;
    this.name = name;
    this.age = age;
    this.balance = initialBalance;
    this.accountType = accountType;
    this.status = "Active";
    this.pin = null;

 // TODO: Validate age (must be >= 18)
    if(age< MIN_AGE) {
      throw new IllegalArgumentException("Age should at least be " + MIN_AGE);
    }
 // TODO: Validate account type (must be "Savings" or "Current")
    if (!accountType.equalsIgnoreCase("Savings") && !accountType.equalsIgnoreCase("Current")) {
          throw new IllegalArgumentException("Account type should be Savings or Current.");
        }
 // TODO: Validate minimum balance based on account type
    double minBalance;
    minBalance= accountType.equalsIgnoreCase("Savings")? MIN_BALANCE_SAVINGS: MIN_BALANCE_CURRENT;
    if (initialBalance < minBalance) {
          throw new IllegalArgumentException(
              "This account needs a min balance of Rs."+ minBalance);
    }   
 }
 public void setName(String name) { this.name = name; }

 // ===== Business Methods =====
  public void deposit(double amount)
    throws InvalidAmountException, InactiveAccountException {
 // TODO: Check if account is active
    validateActive();        
 // TODO: Check if amount is positive
    if (amount <= 0) {
            throw new InvalidAmountException("Amount should be positive.");
        }
 // TODO: Add amount to balance
    balance += amount;
 }

  public void withdraw(double amount, int pin)
    throws InvalidAmountException,InsufficientBalanceException,
                   MinimumBalanceViolationException,
                   InactiveAccountException,
                   InvalidPinException {
    // TODO: Check if account is active
    // TODO: Check if PIN is set
    // TODO: Verify PIN
    // TODO: Check if amount is positive
    // TODO: Check if sufficient balance
    // TODO: Check minimum balance after withdrawal
    // TODO: Deduct amount from balance
        validateActive();
        if (!hasPin()) {
            throw new InvalidPinException("No PIN hfor this account.");
        }
        if (!verifyPin(pin)) {
            throw new InvalidPinException("Incorrect PIN.");
        }
        if (amount<= 0) {
            throw new InvalidAmountException("Amount should be positive.");
        }
        if (amount> balance) {
            throw new InsufficientBalanceException(
                "Insufficient balance. Available: Rs."+ balance);
        }
 
        double remaining= balance- amount;
        double minBalance = getMinimumBalance();
        if(remaining< minBalance) {
            throw new MinimumBalanceViolationException("Withdrawal amt would leave amount less than minimum balance");
        }
        balance -= amount;
    }

  //Account Status Management
  public void closeAccount() throws IllegalStateException {
        if (status.equalsIgnoreCase("Inactive")) {
            throw new IllegalStateException("Account already closed.");
        }
        status = "Inactive";
    }
 
  public void reopenAccount() throws IllegalStateException {
      if (status.equalsIgnoreCase("Active")) {
          throw new IllegalStateException("Account already active.");
      }
      status = "Active";
  }
 
  //PIN Management
  public void setPin(int pin) throws IllegalArgumentException {
      if (pin< MIN_PIN || pin> MAX_PIN) {
          throw new IllegalArgumentException("PIN should be a 4-digit number.");
      }
      this.pin = pin;
  }

  public boolean verifyPin(int pin) {
      return this.pin!= null && this.pin== pin; //if  both satisfy, either null or wrong returns false
  }

  public boolean hasPin() {
      return pin!= null;
  }

  // ===== Helper Methods =====
  private double getMinimumBalance() {
      return accountType.equalsIgnoreCase("Savings")? MIN_BALANCE_SAVINGS: MIN_BALANCE_CURRENT;
  }

  private void validateActive() throws InactiveAccountException {
      if (!status.equalsIgnoreCase("Active")) {
          throw new InactiveAccountException("This operation cannot be performed on an inactive account.");
      }
  }

  // ===== Getters =====
  public int getAccountNumber(){return accountNumber; }
  public String getName(){return name; }
  public int getAge(){return age; }
  public double getBalance(){return balance; }
  public String getAccountType(){return accountType; }
  public String getStatus(){return status; }

  
}


public class TestAccountExceptions {
    static String display(Account a) {
        return "Account #" + a.getAccountNumber() + " | " + a.getName() +
               " (" + a.getAge() + " yrs) | " + a.getAccountType() +
               " | Rs. " + a.getBalance() + " | " + a.getStatus() +
               " | PIN: " + (a.hasPin() ? "Yes" : "No");
    }

    public static void main(String[] args) {
        Account[] accounts = new Account[6];
        int count = 0;

        System.out.println("=".repeat(60));
        System.out.println(" ACCOUNT TEST WITH EXCEPTIONS");
        System.out.println("=".repeat(60));

        //test 1
        System.out.println(">>Test 1: Valid Account Creation");
        try{
            Account a1= new Account(1001, "John Doe", 25, 1000, "Savings");
            accounts[count++]= a1;
            System.out.println("SUCCESS: "+ display(a1));
        }catch(IllegalArgumentException e) {
            System.out.println("EXCEPTION: "+ e.getMessage());}

        //test 2
        System.out.println("\n>>Test 2:Invalid Age(under 18)");
        try{
Account a2=new Account(1002, "Young Kid", 16, 500, "Savings");
accounts[count++]= a2;
        }catch(IllegalArgumentException e) {
            System.out.println("EXCEPTION: "+e.getMessage());
        }

        //test 3
        System.out.println("\n>>> Test 3: Invalid Account Type");
        try{
Account a3=new Account(1003, "Test User", 25, 500, "Invalid");
accounts[count++]= a3;}catch(IllegalArgumentException e) {
            System.out.println("EXCEPTION: "+e.getMessage());}

        //test 4
        System.out.println("\n>>> Test 4: Minimum Balance on Creation");
        System.out.println("Creating Savings account with Rs.300");
        try{
Account a4=new Account(1004, "Bob Wilson", 25, 300, "Savings");
accounts[count++] =a4;        }catch(IllegalArgumentException e){
            System.out.println("EXCEPTION: "+e.getMessage());}

        //test 5
        System.out.println("\n>> Test 5:Valid Deposit and Withdrawal");
        Account a5= null;
        try{
            a5=new Account(1005, "Alice Brown", 30, 1000, "Current");
            accounts[count++]= a5;
            System.out.println("Account: "+display(a5));
            a5.setPin(1234);
            System.out.println("Setting PIN 1234: Success");
            a5.deposit(500);
            System.out.println("Depositing Rs.500.0: Success");
            System.out.println("Balance after deposit: Rs."+a5.getBalance());
            a5.withdraw(200,1234);
            System.out.println("Withdrawing Rs.200.0: SUCCESS");
            System.out.println("Balance after withdrawal: Rs. "+a5.getBalance());
            System.out.println(display(a5));

        }catch(AccountException  | IllegalArgumentException e) {
            System.out.println("EXCEPTION: "+ e.getMessage());}

        //test 6
        System.out.println("\n>Test 6: Invalid Deposit (Negative amt)");
        System.out.println("Attempting to deposit Rs.-100.0");
        try{
            a5.deposit(-100);
        }catch(AccountException e) {
            System.out.println("EXCEPTION:"+ e.getMessage());}

        System.out.println("\n>> Test 7: Insufficient Balance");
        try{
            Account a6 = new Account(1006, "Charlie Green", 35, 500, "Savings");
            accounts[count++]= a6;
            a6.setPin(1111);
            System.out.println("Account: " + display(a6));
            System.out.println("Attempting to withdraw Rs.1000.0");
            a6.withdraw(1000, 1111);
        }catch(AccountException | IllegalArgumentException e) {
            System.out.println("EXCEPTION: "+e.getMessage());}

        System.out.println("\n>>> Test 8: Minimum Balance Violation");
        try {
            Account a7 = new Account(1007, "Diana Prince", 28, 1000, "Savings");
            accounts[count++]= a7;
            a7.setPin(2222);
            System.out.println("Account: " + display(a7));
            System.out.println("Attempting to withdraw Rs. 600.0");
            a7.withdraw(600, 2222);
        } catch (AccountException | IllegalArgumentException e) {
            System.out.println("EXCEPTION: " + e.getMessage());}

        System.out.println("\n>>> Test 9: Inactive Account Operations");
        try{
            Account a8 = new Account(1008, "Eve Wilson", 32, 2000, "Current");
            accounts[count++]= a8;
            System.out.println("Account: " + display(a8));

            a8.closeAccount();
            System.out.println("Closing account: SUCCESS");

            System.out.println("Attempting to deposit Rs.100.0 on closed account");
            try {
                a8.deposit(100);
            } catch (AccountException e) {
                System.out.println("EXCEPTION: " + e.getMessage());}
            a8.reopenAccount();
            System.out.println("Reopening account: SUCCESS");

            a8.deposit(100);
            System.out.println("Depositing Rs. 100.0 after reopen: SUCCESS");
            System.out.println("Balance after deposit: Rs." + a8.getBalance());
        }catch(AccountException| IllegalArgumentException |IllegalStateException e){
            System.out.println("EXCEPTION: "+e.getMessage());}

        //tset 10
        System.out.println("\n>Test 10: PIN Verification");
        try{
            Account a9 = new Account(1009, "Frank Miller", 40, 1500, "Savings");
            accounts[count++] = a9;
            System.out.println("Account: " + display(a9));

            a9.setPin(1234);
            System.out.println("Setting PIN 1234: SUCCESS");

            a9.withdraw(200, 1234);
            System.out.println("Withdrawing Rs.200.0 with correct PIN: SUCCESS");
            System.out.println("Balance: Rs. " + a9.getBalance());

            System.out.println("Attempting to withdraw Rs. 100.0 with incorrect PIN (9999)");
            try{
                a9.withdraw(100, 9999);
            }catch(AccountException e) {
                System.out.println("EXCEPTION: " + e.getMessage());
            }

            System.out.println("Attempting to withdraw Rs.100.0 without PIN set");
            try{
                new Account(1010, "No Pin User", 26, 1000, "Savings").withdraw(100, 0);
            }catch(AccountException e) {
                System.out.println("EXCEPTION: "+e.getMessage());
            }
        }catch(AccountException | IllegalArgumentException e) {
            System.out.println("EXCEPTION: "+ e.getMessage());}

        System.out.println("\n>>> Test 11: All Accounts Summary");
        for (int i = 0; i < count; i++) {
            System.out.println(display(accounts[i]));}
            
        System.out.println("\n");
        System.out.println("\n" + "=".repeat(60));
        System.out.println(" TEST COMPLETED");
        System.out.println("=".repeat(60));
    }
}