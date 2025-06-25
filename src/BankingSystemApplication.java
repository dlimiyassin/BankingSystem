import services.facade.AccountService;
import services.impl.Account;

public class BankingSystemApplication {
    public static void main(String[] args){
        AccountService accountService = new Account();
        accountService.deposit(1000);
        accountService.deposit(2000);
        accountService.withdraw(500);
        accountService.printStatement();
    }
}
