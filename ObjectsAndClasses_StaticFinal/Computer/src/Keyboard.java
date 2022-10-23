public class Keyboard {
    private final KeyboardType type;
    private final IlluminationType illuminationType;
    private final double weight;

    public Keyboard(KeyboardType type, IlluminationType illuminationType, double weight) {
        this.type = type;
        this.illuminationType = illuminationType;
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "тип: " + type + "\n" +
                "тип подсветки: " + illuminationType + "\n" +
                "вес: " + weight + "\n";
    }
}
