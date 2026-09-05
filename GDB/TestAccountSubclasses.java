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
    if (!accountType.equalsIgnoreCase("Savings") && !accountType.equalsIgnoreCase("Current")
        && !accountType.equalsIgnoreCase("FIXED_DEPOSIT") && !accountType.equalsIgnoreCase("SALARY")) {
          throw new IllegalArgumentException("Account type should be Savings, Current, Fixed_Deposit or Salary.");
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
      return this.pin!= null && this.pin== pin;
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

class SavingsAccount extends Account{
  private double minBalance = 1000.0;
  private double interestRate = 4.0;

  public SavingsAccount(int accountNumber, String name, int age, double initialBalance){
    super(accountNumber, name, age, initialBalance, "SAVINGS");
  }

  public void applyInterest(){
    double bal=getBalance();
    double interest =bal*interestRate/100;
    try{
      deposit(interest);
    }catch(Exception e){
      System.out.println(e.getMessage());
    }
  }
  public double getMinBalance(){return minBalance; }
  public double getInterestRate(){return interestRate; }
}

class CurrentAccount extends Account{
  private double overdraftLimit = 25000.0;

  public CurrentAccount(int accountNumber, String name, int age, double initialBalance){
    super(accountNumber, name, age, initialBalance, "CURRENT");
  }
  public double getOverdraftLimit(){return overdraftLimit; }
  public void setOverdraftLimit(double overdraftLimit){this.overdraftLimit = overdraftLimit; }
}

class FixedDepositAccount extends Account{
  private int tenureMonths = 12;
  private double interestRate = 6.5;

  public FixedDepositAccount(int accountNumber, String name, int age, double initialBalance){
    super(accountNumber, name, age, initialBalance, "FIXED_DEPOSIT");
  }
  public double calculateMaturityAmount(){
    double rate = interestRate/100;
    int n = 4;
    double t = tenureMonths/12.0;
    return getBalance()*Math.pow(1+rate/n,n*t);
  }

  public int getTenureMonths(){return tenureMonths; }
  public double getInterestRate(){return interestRate; }
}

class SalaryAccount extends Account{
  private String employerName;
  private int inactiveMonths;

  public SalaryAccount(int accountNumber, String name, int age, double initialBalance, String employerName){
    super(accountNumber, name, age, initialBalance, "SALARY");
    this.employerName = employerName;
    this.inactiveMonths = 0;
  }
  public String getEmployerName(){return employerName; }
  public int getInactiveMonths(){return inactiveMonths; }
}

public class TestAccountSubclasses{
  public static void main(String[] args){
    System.out.println("===Activity 7: Account Subclasses Test===");

    SavingsAccount sa = new SavingsAccount(2001, "Ravi", 25, 10000);
    System.out.println("Savings Account Created: Balance Rs "+ sa.getBalance()+ " | Min Balance: Rs "+ sa.getMinBalance());

    CurrentAccount ca = new CurrentAccount(2002, "Priya", 30, 5000);
    System.out.println("Current Account Created: Overdraft Limit Rs "+ ca.getOverdraftLimit());

    FixedDepositAccount fd = new FixedDepositAccount(2003, "Amit", 28, 50000);
    System.out.println("Fixed Deposit Created: Tenure "+ fd.getTenureMonths()+ " months | Interest: "+ fd.getInterestRate()+ "%");

    SalaryAccount sal = new SalaryAccount(2004, "Neha", 26, 20000, "Infosys");
    System.out.println("Salary Account Created: Employer "+ sal.getEmployerName());

    System.out.println("All subclasses instantiated successfully!");
  }
}
