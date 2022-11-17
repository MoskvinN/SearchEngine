import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"name", "line", "date", "depth", "hasConnection"})
public class StationForJSON {
    private String name;
    private String line;
    private String date;
    private String depth;

    private boolean hasConnection;

    public void setName(String name) {
        this.name = name;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public void setHasConnection(boolean hasConnection) {
        this.hasConnection = hasConnection;
    }

    public String getName() {
        return name;
    }

    public String getLine() {
        return line;
    }

    public String getDate() {
        return date;
    }

    public String getDepth() {
        return depth;
    }

    public boolean isHasConnection() {
        return hasConnection;
    }
}
