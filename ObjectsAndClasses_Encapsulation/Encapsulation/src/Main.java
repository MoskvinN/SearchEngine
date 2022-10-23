import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Cargo cargo = new Cargo(new Dimensions(20, 30, 35), 120, "Baker Street, 221B", false, "24a54345", false);
        System.out.println(cargo);
        System.out.println(cargo.setDimensions(new Dimensions(30, 40, 50)));
        System.out.println(cargo.setWeight(250));
        System.out.println(cargo.setDeliveryAddress("Tisovaya str., 4"));

        Elevator elevator = new Elevator(-3, 26);
        while (true) {
            System.out.print("Введите номер этажа: ");
            int floor = new Scanner(System.in).nextInt();
            elevator.move(floor);
        }
    }
}
