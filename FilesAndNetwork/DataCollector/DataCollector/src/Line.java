public class Line {
    private String name;
    private String number;

    public Line(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Line{");
        sb.append("name='").append(name).append('\'');
        sb.append(", number='").append(number).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
