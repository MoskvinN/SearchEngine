public class Cargo {
    private final Dimensions dimensions;
    private final int weight;
    private final String deliveryAddress;
    private final boolean isFlip;
    private final String registrationNumber;
    private final boolean isFragile;

    public Cargo(Dimensions dimensions, int weight, String deliveryAddress, boolean isFlip, String registrationNumber, boolean isFragile) {
        this.dimensions = dimensions;
        this.weight = weight;
        this.deliveryAddress = deliveryAddress;
        this.isFlip = isFlip;
        this.registrationNumber = registrationNumber;
        this.isFragile = isFragile;
    }

    public Cargo setDeliveryAddress(String deliveryAddress){
        return new Cargo(dimensions, weight, deliveryAddress, isFlip, registrationNumber, isFragile);
    }

    public Cargo setDimensions(Dimensions dimensions){
        return new Cargo(dimensions, weight, deliveryAddress, isFlip, registrationNumber, isFragile);
    }

    public Cargo setWeight(int weight){
        return new Cargo(dimensions, weight, deliveryAddress, isFlip, registrationNumber, isFragile);
    }

    @Override
    public String toString() {
        return "габариты: " + dimensions + "\n" +
                "вес: " + weight + "\n" +
                "адресс: " + deliveryAddress + "\n" +
                "можно ли переворачивать: " + isFlip + "\n" +
                "регистрационный номер: " + registrationNumber + "\n" +
                "хрупкий груз: " + isFragile + "\n";
    }
}
