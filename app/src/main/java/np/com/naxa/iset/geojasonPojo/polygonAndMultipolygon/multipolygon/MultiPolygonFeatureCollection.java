
package np.com.naxa.iset.geojasonPojo.polygonAndMultipolygon.multipolygon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MultiPolygonFeatureCollection {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("features")
    @Expose
    private List<MultiPolygonFeature> features = null;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<MultiPolygonFeature> getFeatures() {
        return features;
    }

    public void setFeatures(List<MultiPolygonFeature> features) {
        this.features = features;
    }
}
