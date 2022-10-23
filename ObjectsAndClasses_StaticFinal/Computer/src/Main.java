public class Main {
    public static void main(String[] args) {
        Computer computer = new Computer(
                new Processor(3.7, 8, "AMD", 35),
                new RAM(RAMType.DDR3, 16, 70),
                new InformationStorage(InformationStorageType.HDD, 1000, 160),
                new Screen(24, ScreenType.IPS, 3150),
                new Keyboard(KeyboardType.MEMBRANE, IlluminationType.NONE, 1000),
                "ASUS",
                "ROG Strix"
        );
        System.out.println(computer);
        System.out.println("Общий вес комплектующих первого компьютера: " + computer.getTotalWeight());

        Computer computer1 = new Computer(
                new Processor(3.7, 12, "AMD", 40),
                new RAM(RAMType.DDR4, 32, 70),
                new InformationStorage(InformationStorageType.SSD, 1000, 160),
                new Screen(27, ScreenType.IPS, 3500),
                new Keyboard(KeyboardType.MECHANICAL, IlluminationType.RGB, 1500),
                "MSI",
                "MAG"
        );

        System.out.println(computer1);
        System.out.println("Общий вес комплектующих второго компьютера: " + computer1.getTotalWeight());
    }
}
