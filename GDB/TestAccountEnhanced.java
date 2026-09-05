class Account{

  private int accNo;
  private String name;
  private int age;
  private double balance;
  private String accType;
  private String status;
  private static int AGE=18;
  private static int MINS = 500.0;
  private static int MINC = 1000.0;
  private Integer pin = null;


  Account(int accNo, String name, int age, int iniBal, String accType){
    this.accNo=accNo;
    this.name=name;


    this.age =(age < AGE)?AGE:age;


    if(accType.equalsIgnoreCase("Savings") && iniBal<MINS) balance=500;
    else if(accType.equalsIgnoreCase("Current") && iniBal<MINC) balance=1000;
    else balance=iniBal;
       
    if(accType.equalsIgnoreCase("Savings") || accType.equalsIgnoreCase("Current")) this.accType=accType;
    else this.accType="Savings";


    status="Active";
    System.out.println("Account created!");
  }


  boolean deposit(double amount){
    if(amount>0 && status.equalsIgnoreCase("active")){
      balance+=amount;
      return true;
    }else{
      return false;
    }
  }


  public boolean withdraw(double amount, int pin){
    boolean verified=verifyPin(pin);


    if(verified && status.equalsIgnoreCase("active")){
      if(amount>0 && balance>=amount){
        if(accType.equalsIgnoreCase("Savings") && (balance-amount)>500){
          balance-=amount;
          return true;
        }else if(accType.equalsIgnoreCase("Current") && (balance-amount)>1000){
          balance-=amount;
          return true;
        }else return false;
      }else return false;
    } else {
      return false;
    }
  }  


  boolean closeAccount(){
    if (status.equalsIgnoreCase("active")){
      status="inactive";
      return true;
    }
    return false;
  }


  boolean reopenAccount(){
    if (status.equalsIgnoreCase("inactive")){
      status="Active";
      return true;
    }return false;
  }


  final public boolean setPin(int pin){
    if(pin>=1000 && pin<=9999){
      this.pin=pin;
      return true;
    }
    else return false;
  }


  boolean verifyPin(int pin){
    if(pin==this.pin) return true;
    else return false;
  }


  boolean hasPin(){
    if(pin!=null) return true;
    return false;
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






public class TestAccountEnhanced{
    static String display(Account a){
    return "Account #"+ a.getAccountNumber() + " | "+a.getName()+ " ("+a.getAge()+ " years old) |  "+ a.getAccountType()+" | Rs."+ a.getBalance()+ " | "+ a.getStatus()+" | PIN: "+ (a.hasPin()?"Yes":"No");
  }


  public static void main(String[] args){
    System.out.println("=".repeat(80));
    System.out.println("             ENHANCED ACCOUNT TEST (BOOLEAN RETURNS)");
    System.out.println("=".repeat(80));


    Account[] accounts=new Account[7];


    System.out.println("\n>>Test 1: Valid acc creation");
    Account a1=new Account(1001, "John Doe", 25, 1000, "Savings");
    accounts[0]= a1;
    System.out.println(display( a1));


    System.out.println("\n>> Test 2: Invalid age (under 18)");
    System.out.println("Creating account with age  16");
    Account a2=new Account(1002, "Young Kid ", 16, 500, "Savings");
    accounts[1]=a2;
    System.out.println("Age auto corrected to: "+ a2.getAge());
    System.out.println(display(a2));


    System.out.println("\n>>Test 3: Invalid Account Type");
    System.out.println("Creating account with type \"Invalid\" ");
    Account a3=new Account(1003, "Test User", 25, 500, "Invalid");
    accounts[2]=a3;
    System.out.println("Account type defaulted to: "+a3.getAccountType()+".");
    System.out.println(display(a3));


    System.out.println("\n> Test 4: Minimum Balance Enforcement on Creation");
    System.out.println("Creating Savings account with Rs. 300 ( below minimum)");
    Account a4=new Account(1004, "Bob Wilson", 25, 300, "Savings");
    accounts[3]=a4;
    System.out.println("Balance auto-corrected to minimum: Rs."+a4.getBalance());
    System.out.println(display(a4));


    System.out.println("\n> Test 5: Withdrawal with Minimum Balance");
    Account a5=new Account(1005, "Alice Brown", 30, 1000, "Current");
    a5.setPin(4321);
    accounts[4]=a5;
    System.out.println("Initial: "+display(a5));
    boolean success=a5.withdraw(200, 4321);
    System.out.println("Withdrawing Rs. 200.0 : "+(success?" SUCCESS":"FAILED"));
    System.out.println("New balance: Rs. "+a5.getBalance());
    System.out.println("After withdrawal:"+display(a5));
    success=a5.withdraw(900, 4321);
    System.out.println("Withdrawing Rs. 900.0 (would leave Rs. -100):  "+(success?"SUCCESS":"FAILED( Minimum balance violation)"));
    System.out.println("Current balance: Rs."+a5.getBalance());


    System.out.println("\n> Test 6: Account Status Management");
    Account a6=new Account(1006, "Charlie Green", 35, 2000, "Savings");
    accounts[5]=a6;
    System.out.println("Initial: "+display(a6));
    success=a6.closeAccount();
    System.out.println("Closing account:"+(success?" SUCCESS":" FAILED"));
    System.out.println("After close: "+display(a6));
    success=a6.deposit(500);
    System.out.println("Depositing Rs.500.0 to closed acc: "+(success?"SUCCESS":"FAILED(Account  inactive)"));
    success=a6.reopenAccount();
    System.out.println("Reopening account: "+ (success?"SUCCESS":"FAILED"));
    System.out.println("After reopen:"+display(a6)+" ");


    System.out.println("\n>> Test 7: PIN Protection");
    Account a7=new Account(1007, "Diana Prince", 28, 1500, "Savings");
    accounts[6]=a7;
    success=a7.setPin(1234);
    System.out.println("Setting PIN 1234:"+(success?" SUCCESS":" FAILED"));
    success=a7.withdraw(200, 1234);
    System.out.println("Withdrawing Rs. 200.0 with correct PIN (1234): "+(success?"SUCCESS ":"FAILED"));
    System.out.println("New balance: Rs. "+a7.getBalance());
    success=a7.withdraw(100, 9999);
    System.out.println("Withdrawing Rs. 100.0 with incorrect PIN (9999):"+(success?"SUCCESS":" FAILED ( Incorrect PIN)"));
    Account noPinAcc=new Account(9999, "Temp User", 25, 1000, "Savings");
    if(noPinAcc.hasPin()){
      success=noPinAcc.withdraw(100, 0);
      System.out.println("Withdrawing Rs. 100.0 with PIN not set: "+(success?"SUCCESS":"FAILED"));
    }else{
      System.out.println("Withdrawing Rs. 100.0 with PIN not set:  FAILED (PIN  not set)");
    }


    System.out.println("\n>> Test 8: All Accounts Summary");
    for(Account a: accounts){
      System.out.println(display(a));
    }


    System.out.println("\n"+"=".repeat(80));
    System.out.println("            ENHANCED TEST COMPLETED");
    System.out.println("=".repeat(80));
  }


 
}

