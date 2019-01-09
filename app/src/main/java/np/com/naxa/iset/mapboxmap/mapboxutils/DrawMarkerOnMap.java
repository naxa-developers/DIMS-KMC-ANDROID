package np.com.naxa.iset.mapboxmap.mapboxutils;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import np.com.naxa.iset.R;
import np.com.naxa.iset.mapboxmap.OpenSpaceMapActivity;
import np.com.naxa.iset.utils.imageutils.LoadImageUtils;

public class DrawMarkerOnMap implements MapboxMap.OnInfoWindowClickListener,
        MapboxMap.OnMarkerClickListener {
    private static final String TAG = "DrawMarkerOnMap";

    OpenSpaceMapActivity context;
    MapboxMap mapboxMap;
    MapView mapView;

    String imageName;
    ArrayList<LatLng> points = null;
    StringBuilder geoJsonString;


    public DrawMarkerOnMap(OpenSpaceMapActivity context, MapboxMap mapboxMap, MapView mapView) {
        this.context = context;
        this.mapboxMap = mapboxMap;
        this.mapView = mapView;
    }


    public void AddMarkerOnMap(String geoJsonFileName ,StringBuilder stringBuilder, String imageName){

        Log.d(TAG, "AddMarkerOnMap: "+stringBuilder.toString());

        final int[] count = {0};

//      Icon icon = IconFactory.getInstance(context).fromResource(R.drawable.ic_marker_hospital);

        Icon icon ;

//        if(LoadImageUtils.getImageIconFroDrawable(context, imageName) == null){
//            icon = IconFactory.getInstance(context).fromResource(R.drawable.mapbox_marker_icon_default);
//        }else {
//            icon = LoadImageUtils.getImageIconFroDrawable(context, imageName);
//        }

        if((LoadImageUtils.getImageBitmapFromDrawable(context, imageName)) == null){
            icon = IconFactory.getInstance(context).fromResource(R.drawable.mapbox_marker_icon_default);
        }else {
            icon = IconFactory.getInstance(context).fromBitmap(LoadImageUtils.getImageBitmapFromDrawable(context, imageName));
        }

//      BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.map_marker_blue);

      if(stringBuilder == null){
          return;
      }

        String geoJson = stringBuilder.toString();


        FeatureCollection featureCollection = FeatureCollection.fromJson(geoJson);
        List<Feature> featureList  = featureCollection.features();

        Observable.just(featureList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapIterable(new Function<List<Feature>, Iterable<Feature>>() {
                    @Override
                    public Iterable<Feature> apply(List<Feature> features) throws Exception {
                        return features;
                    }
                })
                .map(new Function<Feature, Feature>() {
                    @Override
                    public Feature apply(Feature feature) throws Exception {
                        return feature;
                    }
                })
                .subscribe(new DisposableObserver<Feature>() {
                    @Override
                    public void onNext(Feature feature) {

                        count[0]++;

                        String title = feature.getStringProperty("name");

//                        String snippest = feature.getProperty("properties").getAsString();
                        String snippest = feature.toString();
                        Log.d(TAG, "onNext: JSON Object "+snippest);
                        Log.d(TAG, "onNext: JSON Object Geometry "+feature.geometry().toJson());


                        LatLng location = new LatLng(0.0, 0.0);

                        try {
                            JSONObject jsonObject = new JSONObject(feature.geometry().toJson());
                            Log.d(TAG, "onNext: JSON Object Co-ordinates "+jsonObject.getJSONArray("coordinates").getString(0));
                            String Lon = jsonObject.getJSONArray("coordinates").getString(0) ;
                            String Lat = jsonObject.getJSONArray("coordinates").getString(1) ;
                            location = new LatLng(Double.parseDouble(Lat), Double.parseDouble(Lon));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                        MarkerOptions marker = new MarkerOptions().position(location)
                                .title(title).snippet(snippest).icon(icon);

                        // Add the custom icon marker to the map
//                        Marker marker = mapboxMap.addMarker(new MarkerOptions()
//                                .position(new LatLng(location))
//                                .title(title)
//                                .snippet(snippest)
//                                .icon(icon));
//                        marker.setTitle(geoJsonFileName+count[0]);

                        mapboxMap.addMarker(marker);

                        mapboxMap.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(@NonNull Marker marker) {
                                Toast.makeText(context, "Marker tapped: " + marker.getTitle(), Toast.LENGTH_LONG).show();
                                onInfoWindowClick(marker);
                                return true;
                            }
                        });


//                        marker.setMapboxMap(mapboxMap);


                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {


                    }
                });

    }


    public void RemoveMarkerOnMap(String geoJsonFileName, StringBuilder stringBuilder, String imageName){

        Icon icon = IconFactory.getInstance(context).fromResource(R.drawable.ic_marker_hospital);
        final int[] count = {0};

        Log.d(TAG, "RemoveMarkerOnMap: "+stringBuilder.toString());

        if(stringBuilder == null){
            return;
        }

        String geoJson = stringBuilder.toString();


        FeatureCollection featureCollection = FeatureCollection.fromJson(geoJson);
        List<Feature> featureList  = featureCollection.features();

        Observable.just(featureList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapIterable(new Function<List<Feature>, Iterable<Feature>>() {
                    @Override
                    public Iterable<Feature> apply(List<Feature> features) throws Exception {
                        return features;
                    }
                })
                .map(new Function<Feature, Feature>() {
                    @Override
                    public Feature apply(Feature feature) throws Exception {
                        return feature;
                    }
                })
                .subscribe(new DisposableObserver<Feature>() {
                    @Override
                    public void onNext(Feature feature) {

                        count[0]++;

                        String title = feature.getStringProperty("name");

//                        String snippest = feature.getProperty("properties").getAsString();
                        String snippest = feature.toString();
                        Log.d(TAG, "onNext: JSON Object RemoveMarkerOnMap "+snippest);
                        Log.d(TAG, "onNext: JSON Object Geometry RemoveMarkerOnMap "+feature.geometry().toJson());


                        LatLng location = new LatLng(0.0, 0.0);

                        try {
                            JSONObject jsonObject = new JSONObject(feature.geometry().toJson());
                            Log.d(TAG, "onNext: JSON Object Co-ordinates "+jsonObject.getJSONArray("coordinates").getString(0));
                            String Lon = jsonObject.getJSONArray("coordinates").getString(0) ;
                            String Lat = jsonObject.getJSONArray("coordinates").getString(1) ;
                            location = new LatLng(Double.parseDouble(Lat), Double.parseDouble(Lon));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        mapboxMap.removeMarker(new Marker(new MarkerOptions()
                                .position(new LatLng(location))
                                .title(title)
                                ));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {

        String snippest = marker.getSnippet();
        Log.d(TAG, "onMarkerClick: "+snippest);

        return false;
    }

    @Override
    public boolean onInfoWindowClick(@NonNull Marker marker) {
        String snippest = marker.getSnippet();
        Log.d(TAG, "onMarkerClick: "+snippest);
        return false;
    }
}
