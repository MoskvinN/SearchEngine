import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Company {

    private ArrayList<Employee> staff = new ArrayList<>();

    private static double income;

    public static void setIncome(double income) {
        Company.income = income;
    }

    public List<Employee> getTopSalaryStaff(int count){
        if(count >= 0 && count < staff.size()){
            List<Employee> topStaff = new ArrayList<>();
            Collections.sort(staff, new Comparator<>() {
                @Override
                public int compare(Employee employee, Employee t1) {
                    return Double.compare(employee.getMonthSalary(), t1.getMonthSalary());
                }
            });
            Collections.reverse(staff);
            for (int i = 0; i < count; i++) {
                topStaff.add(staff.get(i));
            }
            return topStaff;
        }
        return new ArrayList<>();
    }

    public List<Employee> getLowestSalaryStaff(int count){
        if(count >= 0 && count < staff.size()){
            List<Employee> lowStaff = new ArrayList<>();
            Collections.sort(staff, new Comparator<>() {
                @Override
                public int compare(Employee employee, Employee t1) {
                    return Double.compare(employee.getMonthSalary(), t1.getMonthSalary());
                }
            });
            for (int i = 0; i < count; i++) {
                lowStaff.add(staff.get(i));
            }
            return lowStaff;
        }
        return new ArrayList<>();
    }

    public void hire(Employee employee){
        staff.add(employee);
    }

    public void hireAll(ArrayList<Employee> employees){
        staff.addAll(employees);
    }

    public void fire(Employee employee){
        staff.remove(employee);
    }

    public static double getIncome() {
        return income;
    }
}
