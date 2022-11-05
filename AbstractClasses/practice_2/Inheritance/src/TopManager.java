public class TopManager implements Employee{

    private double salary;

    public TopManager(double salary) {
        this.salary = salary;
    }

    @Override
    public double getMonthSalary() {
        if(Company.getIncome() > 10_000_000){
            return salary + (1.5 * salary);
        }
        return salary;
    }

    @Override
    public String toString() {
        return getClass().getName() + " " + getMonthSalary();
    }
}
