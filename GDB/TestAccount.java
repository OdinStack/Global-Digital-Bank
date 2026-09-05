class Account{

  private int accNo;
  private String name;
  private int age;
  private double balance;
  private String accType;
  private String status;

  Account(int accNo, String name, int age, int iniBal, String accType){
    this.accNo=accNo;
    this.name=name;
    this.age=age;
    balance=iniBal;
    this.accType=accType;
    status="Active";
    System.out.println("Account created!");
  }

  boolean deposit(double amount){
    if(amount>0){
      balance+=amount;
      return true;
    }else{
      return false;
    }
  }

  boolean withdraw(double amount){
    if(amount>0 && balance>=amount){
      balance-=amount;
      return true;
    }else{
      return false;
    }
  }

  int getAccountNumber(){
    return accNo;
  }
  String getName(){
    return name;
  }
  int getAge(){
    return age;
  }
  double getBalance(){
    return balance;
  }
  String getAccountType(){
    return accType;
  }
  String getStatus(){
    return status;
  }
  void setName(String name){
    this.name=name;
  }
  void setAge(int age){
    this.age=age;
  }
}



public class TestAccount{
  public static void main(String[] args){
    System.out.println("=".repeat(50));
    System.out.println("  GLOBAL DIGITAL BANK -ACCOUNT TEST");
    System.out.println("=".repeat(50));


    System.out.println(">1. Creating Account");
    Account a1=new Account(1001, "John Doe", 25, 1000, "Savings");
    System.out.println("Account #" + a1.getAccountNumber() +" | "+ a1.getName()+ " ("+a1.getAge()+" yrs) | "+ a1.getAccountType()+ " | ₹"+a1.getBalance()+ " | "+a1.getStatus());
    System.out.println("\n> 2. Deposit Money");
    boolean success=a1.deposit(500);
    System.out.println("Depositing ₹500.0: "+ (success?" SUCCESS": "FAILED ( Invalid amt)"));
    System.out.println("New balance: ₹"+ a1.getBalance());
    success=a1.deposit(-100);


    System.out.println("Depositing ₹-100.0: "+ (success?"SUCCESS": "FAILED ( Invalid amount)"));
    System.out.println("\n> 3. Withdraw Money");
    success=a1.withdraw(200);
    System.out.println("Withdrawing ₹200.0: "+(success?"SUCCESS":" FAILED ( Insufficient balance)"));
    System.out.println("New balance: ₹"+a1.getBalance());
    success=a1.withdraw(2000);
    System.out.println("Withdrawing ₹2000.0: "+ (success? "SUCCESS": " FAILED ( Insufficient balance)"));
    System.out.println("Current balance: ₹"+a1.getBalance());


    System.out.println("\n>4. Creating Another Account");
    Account  a2= new Account(1002, "Jane Smith", 30, 2000, "Current");
    System.out.println("Account #"+a2.getAccountNumber()+ " | "+a2.getName()+ " ("+a2.getAge()+ "  yrs) | "+ a2.getAccountType()+ " | ₹"+ a2.getBalance()+ " | "+ a2.getStatus());
    System.out.println("\n>>5. All Accounts");
    System.out.println("Account #"+ a1.getAccountNumber()+" | "+ a1.getName()+" ("+ a1.getAge()+" yrs) | "+ a1.getAccountType()+" | ₹"+ a1.getBalance()+" | "+ a1.getStatus());
    System.out.println("Account # "+ a2.getAccountNumber()+" | "+ a2.getName()+" ("+ a2.getAge()+" yrs) | "+ a2.getAccountType()+" | ₹"+ a2.getBalance()+" | "+ a2.getStatus()+"\n");


    System.out.println("=".repeat(50));
    System.out.println("  TEST COMPLETED");
    System.out.println("=".repeat(50));
  }
}

