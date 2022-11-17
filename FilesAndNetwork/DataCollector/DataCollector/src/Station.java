public class Station {
    private String name;
    private String lineNumber;

    private String hasConnection;
    public Station(String name, String lineNumber, String hasConnection) {
        this.name = name;
        this.lineNumber = lineNumber;
        this.hasConnection = hasConnection;
    }

    public String getName() {
        return name;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public String getHasConnection() {
        return hasConnection;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Station{");
        sb.append("name='").append(name).append('\'');
        sb.append(", lineNumber='").append(lineNumber).append('\'');
        sb.append(", hasConnection=").append(hasConnection);
        sb.append('}');
        return sb.toString();
    }
}
