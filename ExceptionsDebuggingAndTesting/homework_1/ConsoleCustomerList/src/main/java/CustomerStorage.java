import java.util.HashMap;
import java.util.Map;

public class CustomerStorage {
    private final Map<String, Customer> storage;

    public CustomerStorage() {
        storage = new HashMap<>();
    }

    public void addCustomer(String data) {
        final int INDEX_NAME = 0;
        final int INDEX_SURNAME = 1;
        final int INDEX_EMAIL = 2;
        final int INDEX_PHONE = 3;

        String[] components = data.split("\\s+");
        if(components.length != 4){
            throw new WrongFormatException("Wrong format. Correct format: " +
                    "add Василий Петров vasily.petrov@gmail.com +79215637722");
        } else if (!(components[INDEX_PHONE].matches("^((\\+7|7|8)+(\\d){10})$"))) {
            throw new WrongPhoneNumberException("Wrong phone number. Correct format: +79999999999");
        } else if (!(components[INDEX_EMAIL].matches("^(\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*\\.\\w{2,4})$"))) {
            throw new WrongEmailException("Wrong email. Correct format: vasily.petrov@gmail.com");
        }
        String name = components[INDEX_NAME] + " " + components[INDEX_SURNAME];
        storage.put(name, new Customer(name, components[INDEX_PHONE], components[INDEX_EMAIL]));
    }

    public void listCustomers() {
        storage.values().forEach(System.out::println);
    }

    public void removeCustomer(String name) {
        storage.remove(name);
    }

    public Customer getCustomer(String name) {
        return storage.get(name);
    }

    public int getCount() {
        return storage.size();
    }
}