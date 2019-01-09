package np.com.naxa.iset.home.model;

public class MapDataCategory {

    //    public int image;
    public String name;

    public String image;
    public int marker_image;
    private String filename;
    private String summaryName;
    private String type;

    public static final String ROAD = "ROAD";
    public static final String RIVER = "RIVER";
    public static final String BOUNDARY = "BOUNDARY";
    public static final String POINT = "POINT";

    public MapDataCategory(String image, String name, String filename, String type, int marker_image, String summaryName) {
        this.image = image;
        this.marker_image = marker_image;
        this.name = name;
        this.filename = filename;
        this.type = type;
        this.summaryName = summaryName;
    }

    public String getFileName() {
        return filename;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getSummaryName() {
        return summaryName;
    }


    public String getType() {
        return type;
    }

    public int getMarker_image() {
        return marker_image;
    }
}
