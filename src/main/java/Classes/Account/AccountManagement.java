package Classes.Account;

public class AccountManagement {
    private Account accounts[];
    private int currentIndex;

    public AccountManagement() {
        accounts = new Account[10];
        accounts[0] = new Account("admin", "admin");
        currentIndex = 0;
    }

    public void add(Object obj) {
        if (currentIndex < accounts.length) {
            accounts[currentIndex++] = (Account) obj;
        } else {
            System.out.println("Account List is full. Cannot add more.");
        }
    }

    public boolean authenticate(String username, String password) {
        boolean flag = false;
        for (Account account : accounts) {
            if (account != null) {
                if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    public boolean authorize(String username, String password) {
        return (username.equals("admin") && password.equals("admin"));
    }

    public boolean isVerifiedUsername(String newAccountUsername) {
        boolean flag = true;
        for (Account account : accounts) {
            if (account != null) {
                if (account.getUsername().equals(newAccountUsername)) {
                    flag = false;
                }
            }
        }
        return flag;
    }
}
