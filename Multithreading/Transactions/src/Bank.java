import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Bank {

    private Map<String, Account> accounts;
    private final Random random = new Random();

    public Bank(Map<String, Account> accounts) {
        this.accounts = accounts;
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }

    public synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount)
        throws InterruptedException {
        Thread.sleep(1000);
        return random.nextBoolean();
    }

    /**
     * TODO: реализовать метод. Метод переводит деньги между счетами. Если сумма транзакции > 50000,
     * то после совершения транзакции, она отправляется на проверку Службе Безопасности – вызывается
     * метод isFraud. Если возвращается true, то делается блокировка счетов (как – на ваше
     * усмотрение)
     */

    public void transfer(String fromAccountNum, String toAccountNum, long amount) {
        Account fromAccount = getAccount(fromAccountNum);
        Account toAccount = getAccount(toAccountNum);
        if(fromAccountNum.compareTo(toAccountNum) < 0){
            synchronized (fromAccount) {
                synchronized (toAccount) {
                    doTransfer(fromAccount, toAccount, amount);
                }
            }
        }else {
            synchronized (toAccount) {
                synchronized (fromAccount) {
                    doTransfer(fromAccount, toAccount, amount);
                }
            }
        }
    }

    private void doTransfer(Account fromAccount, Account toAccount, long amount) {
        if (!fromAccount.isBlocked() && !toAccount.isBlocked()) {
            if(fromAccount.getMoney() >= amount){
                fromAccount.setMoney(fromAccount.getMoney() - amount);
                toAccount.setMoney(toAccount.getMoney() + amount);
                if (amount > 50000) {
                    try {
                        if (isFraud(fromAccount.getAccNumber(), toAccount.getAccNumber(), amount)) {
                            fromAccount.setBlocked();
                            toAccount.setBlocked();
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }else {
                System.out.println("На счету отправителя недостаточно средств!!!");
            }
        }
    }
    /**
     * TODO: реализовать метод. Возвращает остаток на счёте.
     */
    public long getBalance(String accountNum) {
        for (String s : accounts.keySet()) {
            if(accounts.get(s).getAccNumber().equals(accountNum)){
                return accounts.get(s).getMoney();
            }
        }
        return 0;
    }

    public long getSumAllAccounts() {
        long sum = 0;
        for (Account account : accounts.values()) {
            sum += account.getMoney();
        }
        return sum;
    }

    public Account getAccount(String accountNum){
        for (Account account : accounts.values()) {
            if(account.getAccNumber().equals(accountNum)){
                return account;
            }
        }
        return null;
    }
}