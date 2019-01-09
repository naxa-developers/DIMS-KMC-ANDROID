package np.com.naxa.iset.utils.maputils;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.FolderOverlay;

import java.util.ArrayList;
import java.util.List;

import np.com.naxa.iset.database.entity.CommonPlacesAttrb;

public class MapGeoJsonToObject {
    private static final String TAG = "MapGeoJsonToObject";
    GeoPoint centerPoint = new GeoPoint(27.657531140175244, 85.46161651611328);


    public List<CommonPlacesAttrb> getCommonPlacesListObj(Context context, String geoJson, String fileName, MapView mapView,
                                                          MapMarkerOverlayUtils mapMarkerOverlayUtils, FolderOverlay myOverLay,
                                                          int marker_image, String summaryName) {
        List<CommonPlacesAttrb> commonPlacesAttrbList = new ArrayList<CommonPlacesAttrb>();
        FolderOverlay folderOverlay = new FolderOverlay();

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(geoJson);
            JSONArray jsonarray = new JSONArray(jsonObject.getString("features"));

            Log.d(TAG, "getCommonPlacesListObj: filename " + fileName);

            String remarks = "";
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject properties = new JSONObject(jsonarray.getJSONObject(i).getString("properties"));
                JSONObject geometry = new JSONObject(jsonarray.getJSONObject(i).getString("geometry"));
                JSONArray coordinates = geometry.getJSONArray("coordinates");

                String name = properties.has(summaryName) ? properties.getString(summaryName)
                        : properties.has("name") ? properties.getString("name")
                        : properties.has("Name") ? properties.getString("Name")
                        : properties.has("Name of Bank Providing ATM Service") ? properties.getString("Name of Bank Providing ATM Service")
                        : "null";
                String address = properties.has("address") ? properties.getString("address")
                        : properties.has("Address") ? properties.getString("Address")
                        : " ";

                String type = geometry.getString("type");
                double longitude;
                double latitude;
                if (type.equals("Point")) {
                    longitude = Double.parseDouble(coordinates.get(0).toString());
                    latitude = Double.parseDouble(coordinates.get(1).toString());
                } else if (type.equals("MultiPolygon")) {
                    JSONArray coordinates1 = coordinates.getJSONArray(0);
                    JSONArray coordinates2 = coordinates1.getJSONArray(0);
                    JSONArray coordinates3 = coordinates2.getJSONArray(0);

                    longitude = Double.parseDouble(coordinates3.get(0).toString());
                    latitude = Double.parseDouble(coordinates3.get(1).toString());
                } else {
// for multiLineString
                    JSONArray coordinates1 = coordinates.getJSONArray(0);
                    JSONArray coordinates2 = coordinates1.getJSONArray(0);

                    longitude = Double.parseDouble(coordinates2.get(0).toString());
                    latitude = Double.parseDouble(coordinates2.get(1).toString());
                }

//                if(properties.has("remarks") || properties.has("Remarks")) {
//                    remarks = properties.has("remarks") ? properties.getString("remarks") : properties.getString("Remarks");
//                }
                CommonPlacesAttrb commonPlacesAttrb = new CommonPlacesAttrb(name, address, fileName, latitude, longitude, remarks, properties.toString());

                folderOverlay.add(mapMarkerOverlayUtils.overlayFromCommonPlaceAttrib(context,
                        commonPlacesAttrb, mapView, marker_image, properties.toString()));
            }

            if (mapView.getOverlays().size() >= 2) {
                mapView.getOverlays().set(1, folderOverlay);
            } else {
                mapView.getOverlays().add(folderOverlay);
            }
            MapCommonUtils.zoomToMapBoundary(mapView, centerPoint);
            mapView.invalidate();
//            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return commonPlacesAttrbList;
    }


    public void getWardDetailsListObj(Context context, String geoJson, String fileName, MapView mapView,
                                      MapMarkerOverlayUtils mapMarkerOverlayUtils, FolderOverlay myOverLay,
                                      int marker_image) {
        List<WardDetailsModel> wardDetailsModelList = new ArrayList<WardDetailsModel>();

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(geoJson);
            JSONArray jsonarray = new JSONArray(jsonObject.getString("features"));

            Log.d(TAG, "getCommonPlacesListObj: filename " + fileName);
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject properties = new JSONObject(jsonarray.getJSONObject(i).getString("properties"));
                String name = properties.getString("GaPa_NaPa") + " " + properties.getString("Type_GN");
                String ward = properties.getString("NEW_WARD_N");
                String area = properties.getString("Area_SQKM");
                String district = properties.getString("DISTRICT");
                double latitude = Double.parseDouble(properties.getString("Cent_Y"));
                double longitude = Double.parseDouble(properties.getString("Cent_X"));
                WardDetailsModel wardDetailsModel = new WardDetailsModel(name, ward, area, district, latitude, longitude);

                mapView.getOverlays().add(mapMarkerOverlayUtils.overlayFromWardDetailsModel(context,
                        wardDetailsModel, mapView, marker_image));
                mapView.getOverlays().add(myOverLay);
                mapView.invalidate();

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
