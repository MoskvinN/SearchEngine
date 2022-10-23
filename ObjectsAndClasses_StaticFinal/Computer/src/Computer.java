public class Computer {
    private Processor processor;
    private RAM ram;
    private InformationStorage informationStorage;
    private Screen screen;
    private Keyboard keyboard;
    private final String vendor;
    private final String name;

    public Computer(Processor processor, RAM ram, InformationStorage informationStorage, Screen screen, Keyboard keyboard, String vendor, String name) {
        this.processor = processor;
        this.ram = ram;
        this.informationStorage = informationStorage;
        this.screen = screen;
        this.keyboard = keyboard;
        this.vendor = vendor;
        this.name = name;
    }

    public double getTotalWeight(){
        return processor.getWeight() + ram.getWeight() + informationStorage.getWeight() + screen.getWeight() + keyboard.getWeight();
    }

    public Processor getProcessor() {
        return processor;
    }

    public RAM getRam() {
        return ram;
    }

    public InformationStorage getInformationStorage() {
        return informationStorage;
    }

    public Screen getScreen() {
        return screen;
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }

    public void setProcessor(Processor processor) {
        this.processor = processor;
    }

    public void setRam(RAM ram) {
        this.ram = ram;
    }

    public void setInformationStorage(InformationStorage informationStorage) {
        this.informationStorage = informationStorage;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public void setKeyboard(Keyboard keyboard) {
        this.keyboard = keyboard;
    }

    @Override
    public String toString() {
        return "Производитель: " + vendor + "\n" +
                "Название: " + name + "\n\n" +
                "Процессор: " + "\n" + processor + "\n" +
                "Оперативная память: " + "\n" + ram + "\n" +
                "Накопитель информации: " + "\n" + informationStorage + "\n" +
                "Монитор: " + "\n" + screen + "\n" +
                "Клавиатура: " + "\n" + keyboard;
    }
}
