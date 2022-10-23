public class Dimensions {
    private final int width;
    private final int height;
    private final int length;

    public Dimensions(int width, int height, int length) {
        this.width = width;
        this.height = height;
        this.length = length;
    }

    public int volumeCalculating(){
        return width * height * length;
    }

    @Override
    public String toString() {
        return "\n" + "ширина: " + width + "\n" +
                "высота: " + height + "\n" +
                "длинна: " + length;
    }
}
