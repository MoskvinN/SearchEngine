public class InformationStorage {
    private final InformationStorageType type;
    private final int volume;
    private final double weight;

    public InformationStorage(InformationStorageType type, int volume, double weight) {
        this.type = type;
        this.volume = volume;
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "тип: " + type + "\n" +
                "объем: " + volume + "\n" +
                "вес: " + weight + "\n";
    }
}
