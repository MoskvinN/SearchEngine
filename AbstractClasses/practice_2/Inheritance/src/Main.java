import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Company company = new Company();
        Company.setIncome(11_000_000.0);
        ArrayList<Employee> employees = new ArrayList<>();
        for (int i = 0; i < 180; i++) {
            employees.add(new Operator(25000 + (int)(Math.random() * (25001))));
        }
        for (int i = 0; i < 80; i++) {
            employees.add(new Manager(50000 + (int)(Math.random() * (25001))));
        }
        for (int i = 0; i < 10; i++) {
            employees.add(new TopManager(115000 + (int)(Math.random() * (25001))));
        }
        company.hireAll(employees);
        System.out.println(company.getTopSalaryStaff(10));
        System.out.println(company.getLowestSalaryStaff(30));
        for (int i = 0; i < employees.size() / 2; i++) {
            company.fire(employees.get((int) (Math.random() * (261))));
        }
        System.out.println(company.getTopSalaryStaff(10));
        System.out.println(company.getLowestSalaryStaff(30));
    }
}
