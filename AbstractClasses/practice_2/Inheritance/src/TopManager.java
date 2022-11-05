public class TopManager implements Employee{

    private double salary;

    private double income;

    public TopManager(double salary, Company company) {
        this.salary = salary;
        this.income = company.getIncome();
    }

    @Override
    public double getMonthSalary() {
        if(income > 10_000_000){
            return salary + (1.5 * salary);
        }
        return salary;
    }


    @Override
    public String toString() {
        return getClass().getName() + " " + getMonthSalary();
    }

}
