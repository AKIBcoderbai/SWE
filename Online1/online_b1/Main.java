// Two independent "clients" of the Logger, each in its own class.
// Neither one creates a Logger itself -- both just ask Logger.getInstance() for it.
class DepositService {
    void deposit(String account, double amount) {
        Logger log = Logger.getInstance();
        log.log("Deposited $" + amount + " into account " + account);
    }
}

class WithdrawService {
    void withdraw(String account, double amount) {
        Logger log = Logger.getInstance();
        log.log("Withdrew $" + amount + " from account " + account);
    }
}

public class Main{
    public static void main(String[] args) {
        // client 1 access
        Logger logger1 = Logger.getInstance();
        System.out.println("logger1 secret code: " + logger1.getSecretCode());

        // client 2 access
        Logger logger2 = Logger.getInstance();
        System.out.println("logger2 secret code: " + logger2.getSecretCode());

        // real proof of Singleton: reference identity, not just matching field values
        System.out.println("logger1 == logger2 ? " + (logger1 == logger2));

        // now prove it through two totally separate classes, not just two local variables
        DepositService deposits = new DepositService();
        WithdrawService withdrawals = new WithdrawService();

        deposits.deposit("ACC-101", 500.0);
        withdrawals.withdraw("ACC-101", 200.0);

        // getInstance() again from main -- should be the SAME object that logged both transactions above
        Logger logger3 = Logger.getInstance();
        System.out.println("logger3 == logger1 ? " + (logger3 == logger1));
        logger3.printAllLogs();   // will show BOTH the deposit and the withdrawal
    }
}


