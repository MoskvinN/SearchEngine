public class StationDepth {

    private String stationName;
    private String depth;

    public StationDepth(String name, String depth) {
        this.stationName = name;
        this.depth = depth;
    }

    public String getStationName() {
        return stationName;
    }

    public String getDepth() {
        return depth;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StationDepth{");
        sb.append("name='").append(stationName).append('\'');
        sb.append(", depth='").append(depth).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
