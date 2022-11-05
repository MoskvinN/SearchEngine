public class Manager implements Employee{
    private double salary;

    private double earnedMoney = 115000 + (int)(Math.random() * (25001));

    public Manager(double salary) {
        this.salary = salary;
    }

    @Override
    public double getMonthSalary() {
        return salary + (0.05 * earnedMoney);
    }

    @Override
    public String toString() {
        return getClass().getName() + " " + getMonthSalary();
    }
}
