package np.com.naxa.iset.utils.maputils;

import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

public class MapCommonUtils {

    public static void zoomToMapBoundary(final MapView mapView,final GeoPoint centerPoint){
//        BoundingBox boundingBox = new BoundingBox(27.737334, 85.534334, 27.647866, 85.386996);
        BoundingBox boundingBox = new BoundingBox(27.767334, 85.554334, 27.617866, 85.366996);
        mapView.zoomToBoundingBox(boundingBox, true);
        mapView.setScrollableAreaLimitDouble(boundingBox);
        mapView.getController().animateTo(centerPoint);
        mapView.invalidate();
    }
}
