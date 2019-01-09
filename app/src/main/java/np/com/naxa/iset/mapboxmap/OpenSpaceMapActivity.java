package np.com.naxa.iset.mapboxmap;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import np.com.naxa.iset.R;
import np.com.naxa.iset.event.MapDataLayerListCheckEvent;
import np.com.naxa.iset.event.MyCircleContactAddEvent;
import np.com.naxa.iset.home.model.MapDataCategory;
import np.com.naxa.iset.mapboxmap.mapboxutils.DrawGeoJsonOnMap;
import np.com.naxa.iset.mapboxmap.mapboxutils.DrawRouteOnMap;
import np.com.naxa.iset.mapboxmap.mapboxutils.MapboxBaseStyleUtils;
import np.com.naxa.iset.mapboxmap.openspace.MapCategoryListAdapter;
import np.com.naxa.iset.mapboxmap.openspace.MapCategoryModel;
import np.com.naxa.iset.utils.DialogFactory;
import np.com.naxa.iset.utils.SharedPreferenceUtils;
import np.com.naxa.iset.utils.sectionmultiitemUtils.DataServer;
import np.com.naxa.iset.utils.sectionmultiitemUtils.SectionMultipleItem;

import static np.com.naxa.iset.utils.SharedPreferenceUtils.KEY_MUNICIPAL_BOARDER;
import static np.com.naxa.iset.utils.SharedPreferenceUtils.KEY_WARD;
import static np.com.naxa.iset.utils.SharedPreferenceUtils.MAP_OVERLAY_LAYER;

// classes needed to add a marker
// classes to calculate a route

public class OpenSpaceMapActivity extends AppCompatActivity implements OnMapReadyCallback, PermissionsListener, MapboxMap.OnMapClickListener,
        LocationListener {

    private static final String TAG = "DemoActivity";
    @BindView(R.id.toolbar_general)
    Toolbar toolbarGeneral;
    @BindView(R.id.point)
    Button btnPoint;
    @BindView(R.id.multipolygon)
    Button btnMultipolygon;
    @BindView(R.id.multiLineString)
    Button btnMultiLineString;
    @BindView(R.id.navigation)
    Button navigation;
    @BindView(R.id.floating_search_map_category)
    FloatingSearchView floatingSearchMapCategory;
    @BindView(R.id.recyclerViewMapCategory)
    RecyclerView recyclerViewMapCategory;

    @BindView(R.id.iv_sliding_layout_indicator)
    ImageView ivSlidingLayoutIndicator;
    @BindView(R.id.btnMapLayerData)
    Button btnMapLayerData;
    @BindView(R.id.btnMapLayerSwitch)
    Button btnMapLayerSwitch;

    private SlidingUpPanelLayout mLayout;
    private PermissionsManager permissionsManager;
    private MapView mapView;
    private MapboxMap mapboxMap;
    int count = 0;


    String filename = "";

    // variables for adding a marker
    private Marker destinationMarker;
    private LatLng originCoord;
    private LatLng destinationCoord;

    private Location originLocation;

    // variables for calculating and drawing a route
    private Point originPosition;
    private Point destinationPosition;
    private DirectionsRoute currentRoute;
    private NavigationMapRoute navigationMapRoute;
    SharedPreferenceUtils sharedPreferenceUtils;

    // 1. create entityList which item data extend SectionMultiEntity
    private List<SectionMultipleItem> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "pk.eyJ1Ijoic2FtaXJkYW5nYWwiLCJhIjoiY2pwZHNjaXNpMDJrNjNxbWFlaDZobnZ1MyJ9.ASQwLRwoQeTp3PkVqHh2hw");
        setContentView(R.layout.activity_open_space_map);
        ButterKnife.bind(this);

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        sharedPreferenceUtils = new SharedPreferenceUtils(OpenSpaceMapActivity.this);
        setupToolBar();
//        setupBottomSlidingPanel();
//        setupListRecycler();
        mData = DataServer.getMapDatacategoryList(OpenSpaceMapActivity.this);


    }

    private void setupToolBar() {
        setSupportActionBar(toolbarGeneral);
        getSupportActionBar().setTitle("Open Spaces");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setupBottomSlidingPanel() {
        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.i(TAG, "onPanelSlide, offset " + slideOffset);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                Log.i(TAG, "onPanelStateChanged " + newState);

                switch (newState.toString()) {

                    case "COLLAPSED":
                        ivSlidingLayoutIndicator.setBackground(getResources().getDrawable(R.drawable.ic_keyboard_arrow_up_white_24dp));
                        break;

                    case "DRAGGING":
                        ivSlidingLayoutIndicator.setBackground(getResources().getDrawable(R.drawable.ic_sliding_neutral_white_24dp));
                        break;

                    case "EXPANDED":
                        ivSlidingLayoutIndicator.setBackground(getResources().getDrawable(R.drawable.ic_keyboard_arrow_down_white_24dp));
                        break;
                }

            }
        });
        mLayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });

    }

    private void setupListRecycler() {
        MapCategoryListAdapter mapCategoryListAdapter = new MapCategoryListAdapter(R.layout.openspace_map_category_list_item_row_layout, null);
        recyclerViewMapCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewMapCategory.setAdapter(mapCategoryListAdapter);

        loadDataToList();
    }

    private void loadDataToList() {

        ArrayList<MapCategoryModel> mapCategoryModelArrayList = new ArrayList<MapCategoryModel>();

        mapCategoryModelArrayList.add(new MapCategoryModel("Hospital", getResources().getDrawable(R.drawable.ic_hospital)));
        mapCategoryModelArrayList.add(new MapCategoryModel("College", getResources().getDrawable(R.drawable.ic_college)));
        mapCategoryModelArrayList.add(new MapCategoryModel("Gas Station", getResources().getDrawable(R.drawable.ic_gas_station)));
        mapCategoryModelArrayList.add(new MapCategoryModel("Bus Station", getResources().getDrawable(R.drawable.ic_bus_station)));
        mapCategoryModelArrayList.add(new MapCategoryModel("Restaurant", getResources().getDrawable(R.drawable.ic_restaurant)));
        mapCategoryModelArrayList.add(new MapCategoryModel("Airport", getResources().getDrawable(R.drawable.ic_airport)));
        ((MapCategoryListAdapter) recyclerViewMapCategory.getAdapter()).replaceData(mapCategoryModelArrayList);


    }

    private Dialog setupMapOptionsDialog() {
        // launch new intent instead of loading fragment

        int MAP_OVERLAY_ID = sharedPreferenceUtils.getIntValue(MAP_OVERLAY_LAYER, -1);

        return DialogFactory.createBaseLayerDialog(OpenSpaceMapActivity.this, new DialogFactory.CustomBaseLayerDialogListner() {
            @Override
            public void onStreetClick() {
                mapView.setStyleUrl(getResources().getString(R.string.mapbox_style_mapbox_streets));
                if (MAP_OVERLAY_ID == KEY_MUNICIPAL_BOARDER) {
                    onMetropolitanClick();
                } else if (MAP_OVERLAY_ID == KEY_WARD) {
                    onWardClick();
                }

            }

            @Override
            public void onSatelliteClick() {
                mapView.setStyleUrl(getResources().getString(R.string.mapbox_style_satellite));
                if (MAP_OVERLAY_ID == KEY_MUNICIPAL_BOARDER) {
                    onMetropolitanClick();
                } else if (MAP_OVERLAY_ID == KEY_WARD) {
                    onWardClick();
                }

            }

            @Override
            public void onOpenStreetClick() {
                if (MAP_OVERLAY_ID == KEY_MUNICIPAL_BOARDER) {
                    onMetropolitanClick();
                } else if (MAP_OVERLAY_ID == KEY_WARD) {
                    onWardClick();
                }

            }

            @Override
            public void onMetropolitanClick() {
                filename = "kathmandu_boundary.json";
                count++;
                point = false;
                drawGeoJsonOnMap.readAndDrawGeoSonFileOnMap(filename, point , "");
            }

            @Override
            public void onWardClick() {
                filename = "kathmandu_wards.json";
                count++;
                point = false;
                drawGeoJsonOnMap.readAndDrawGeoSonFileOnMap(filename, point, "");

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mLayout != null &&
                (mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }

    DrawGeoJsonOnMap drawGeoJsonOnMap;
    DrawRouteOnMap drawRouteOnMap;
    MapboxBaseStyleUtils mapboxBaseStyleUtils;

    @Override
    public void onMapReady(MapboxMap mapboxMap) {

        this.mapboxMap = mapboxMap;
        setMapCameraPosition();

//        mapboxMap.setStyleUrl("mapbox://styles/samirdangal/cjqc6swc681go2rpjwnpdtm7y");
//        mapView.setStyleUrl("mapbox://styles/samirdangal/cjqc6swc681go2rpjwnpdtm7y");
//        mapView.setStyleUrl("https://api.mapbox.com/styles/v1/samirdangal/cjqc6swc681go2rpjwnpdtm7y.html?fresh=true&title=true&access_token=pk.eyJ1Ijoic2FtaXJkYW5nYWwiLCJhIjoiY2pwZHNjaXNpMDJrNjNxbWFlaDZobnZ1MyJ9.ASQwLRwoQeTp3PkVqHh2hw#16.5/48.766736/2.329968/0");
//        mapboxMap.setStyleUrl("https://api.mapbox.com/styles/v1/samirdangal/cjqc6swc681go2rpjwnpdtm7y.html?fresh=true&title=true&access_token=pk.eyJ1Ijoic2FtaXJkYW5nYWwiLCJhIjoiY2pwZHNjaXNpMDJrNjNxbWFlaDZobnZ1MyJ9.ASQwLRwoQeTp3PkVqHh2hw#16.5/48.766736/2.329968/0");


        mapboxMap.addOnMapClickListener(this);


        drawGeoJsonOnMap = new DrawGeoJsonOnMap(OpenSpaceMapActivity.this, mapboxMap, mapView);
        drawRouteOnMap = new DrawRouteOnMap(OpenSpaceMapActivity.this, mapboxMap, mapView);
        mapboxBaseStyleUtils = new MapboxBaseStyleUtils(OpenSpaceMapActivity.this, mapboxMap, mapView);
        mapboxBaseStyleUtils.changeBaseColor();

        if (sharedPreferenceUtils.getIntValue(MAP_OVERLAY_LAYER, -1) == -1 ||
                sharedPreferenceUtils.getIntValue(MAP_OVERLAY_LAYER, -1) == KEY_MUNICIPAL_BOARDER) {
            drawGeoJsonOnMap.readAndDrawGeoSonFileOnMap("kathmandu_boundary.json", true, "");
            count = 2;
        } else {
            drawGeoJsonOnMap.readAndDrawGeoSonFileOnMap("kathmandu_wards.json", true, "");
            count = 2;
        }
        setupMapOptionsDialog();
        DialogFactory.createMapDataLayerDialog(OpenSpaceMapActivity.this, mData, drawGeoJsonOnMap);

        mapView.invalidate();
        }

    public void setMapCameraPosition() {

        CameraPosition position = new CameraPosition.Builder()
                .target(new LatLng(27.657531140175244, 85.46161651611328)) // Sets the new camera position
                .zoom(11) // Sets the zoom
                .bearing(0) // Rotate the camera
                .tilt(30) // Set the camera tilt
                .build(); // Creates a CameraPosition from the builder

        mapboxMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(position), 7000);

        enableLocationComponent();
    }


    @Override
    public void onMapClick(@NonNull LatLng latLngPoint) {

        if(point){
            return;
        }

        if (destinationMarker != null) {
            mapboxMap.removeMarker(destinationMarker);
        }
        destinationCoord = latLngPoint;
//        destinationMarker = mapboxMap.addMarker(new MarkerOptions()
//                .position(destinationCoord)
//        );


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }

        try {

            originCoord = new LatLng(originLocation.getLatitude(), originLocation.getLongitude());
            originLocation = mapboxMap.getLocationComponent().getLocationEngine().getLastLocation();

            destinationPosition = Point.fromLngLat(destinationCoord.getLongitude(), destinationCoord.getLatitude());
            originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());

            if (originPosition == null) {
                return;
            }
            if (destinationPosition == null) {
                return;
            }
//            drawRouteOnMap.getRoute(originPosition, destinationPosition);
//            navigation.setVisibility(View.VISIBLE);
        } catch (NullPointerException e) {
            Toast.makeText(this, "Searching current location \nMake sure your GPS provider is enabled.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }


    @SuppressWarnings({"MissingPermission"})
    private void enableLocationComponent() {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {

            LocationComponentOptions options = LocationComponentOptions.builder(this)
                    .trackingGesturesManagement(true)
                    .accuracyColor(ContextCompat.getColor(this, R.color.colorAccent))
                    .build();

            // Get an instance of the component
            LocationComponent locationComponent = mapboxMap.getLocationComponent();

            // Activate with options
            locationComponent.activateLocationComponent(this, options);

            // Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);


            // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);
            locationComponent.setRenderMode(RenderMode.COMPASS);


//            originLocation = locationComponent.getLocationEngine().getLastLocation();

            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);


        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, "You need to provide location permission to show your current location", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            enableLocationComponent();
        } else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        mapView.onStart();


    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }


    private void animateCameraPosition(Location location) {
        CameraPosition position = new CameraPosition.Builder()
//                .target(new LatLng(27.7033, 85.4324)) // Sets the new camera position
                .target(new LatLng(location)) // Sets the new camera position
                .zoom(11.5) // Sets the zoom
                .bearing(0) // Rotate the camera
                .tilt(30) // Set the camera tilt
                .build(); // Creates a CameraPosition from the builder

        Log.d("MapBox", "animateCameraPosition: ");


        mapboxMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(position), 3000);
    }


    ArrayList<LatLng> points = null;
    boolean point = false;

    @OnClick({R.id.point, R.id.multipolygon, R.id.multiLineString, R.id.navigation, R.id.btnMapLayerData, R.id.btnMapLayerSwitch})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.point:
                filename = "financial_institution.geojson";
                count++;
                point = true;
                drawGeoJsonOnMap.readAndDrawGeoSonFileOnMap(filename, point, "ic_marker_openspace");
                break;

            case R.id.multipolygon:
                filename = "wards.geojson";
                count++;
                point = false;
                drawGeoJsonOnMap.readAndDrawGeoSonFileOnMap(filename, point, "");
                break;

            case R.id.multiLineString:
                filename = "road_network.geojson";
                count++;
                point = false;
                drawGeoJsonOnMap.readAndDrawGeoSonFileOnMap(filename, point, "");
                break;

            case R.id.navigation:
                drawRouteOnMap.enableNavigationUiLauncher(OpenSpaceMapActivity.this);
                break;

            case R.id.btnMapLayerData:
                if(mapboxMap == null){
                    return;
                }
                DialogFactory.createMapDataLayerDialog(OpenSpaceMapActivity.this, mData, drawGeoJsonOnMap).show();
                break;

            case R.id.btnMapLayerSwitch:
                setupMapOptionsDialog().show();
                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRVItemClick(MapDataLayerListCheckEvent.MapDataLayerListCheckedEvent itemClick) {
        String name = itemClick.getMultiItemSectionModel().getData_value();
//        if(itemClick.getChecked()){
            drawGeoJsonOnMap.readAndDrawGeoSonFileOnMap(itemClick.getMultiItemSectionModel().getData_value(), itemClick.getChecked(), itemClick.getMultiItemSectionModel().getImage());
//        }
        Toast.makeText(this, "add to your circle button clicked "+name, Toast.LENGTH_SHORT).show();

    }


    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected boolean gps_enabled, network_enabled;

    @Override
    public void onLocationChanged(Location location) {
        originLocation = location;
//        animateCameraPosition(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Please enable your GPS Provider", Toast.LENGTH_LONG).show();

    }

}