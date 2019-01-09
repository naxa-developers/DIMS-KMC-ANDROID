package np.com.naxa.iset.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.franmontiel.localechanger.LocaleChanger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import np.com.naxa.iset.DatabaseDataSPClass;
import np.com.naxa.iset.R;
import np.com.naxa.iset.database.entity.CommonPlacesAttrb;
import np.com.naxa.iset.database.entity.GeoJsonCategoryEntity;
import np.com.naxa.iset.database.entity.GeoJsonListEntity;
import np.com.naxa.iset.home.HomeActivity;
import np.com.naxa.iset.home.MapDataRepository;
import np.com.naxa.iset.network.model.GeoJsonCategoryDetails;
import np.com.naxa.iset.network.retrofit.NetworkApiClient;
import np.com.naxa.iset.network.retrofit.NetworkApiInterface;
import np.com.naxa.iset.newhomepage.SectionGridHomeActivity;
import np.com.naxa.iset.utils.CreateAppMainFolderUtils;
import np.com.naxa.iset.utils.DialogFactory;
import np.com.naxa.iset.utils.SharedPreferenceUtils;
import np.com.naxa.iset.utils.imageutils.ImageSaveTask;
import np.com.naxa.iset.viewmodel.CommonPlacesAttribViewModel;
import np.com.naxa.iset.viewmodel.EducationalInstitutesViewModel;
import np.com.naxa.iset.viewmodel.GeoJsonCategoryViewModel;
import np.com.naxa.iset.viewmodel.GeoJsonListViewModel;
import np.com.naxa.iset.viewmodel.HospitalFacilitiesVewModel;
import np.com.naxa.iset.viewmodel.OpenSpaceViewModel;
import okhttp3.ResponseBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static np.com.naxa.iset.utils.CreateAppMainFolderUtils.appmainFolderName;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";
    private MapDataRepository repository;
    private CommonPlacesAttribViewModel commonPlacesAttribViewModel;
    private HospitalFacilitiesVewModel hospitalFacilitiesVewModel;
    private EducationalInstitutesViewModel educationalInstitutesViewModel;
    private OpenSpaceViewModel openSpaceViewModel;
    private DatabaseDataSPClass sharedpref = new DatabaseDataSPClass(this);

    private final int RESULT_STORAGE_PERMISSION = 50;

    private NetworkApiInterface apiInterface;
    GeoJsonCategoryViewModel geoJsonCategoryViewModel;
    GeoJsonListViewModel geoJsonListViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash);
        repository = new MapDataRepository();

        apiInterface = NetworkApiClient.getAPIClient().create(NetworkApiInterface.class);
        geoJsonCategoryViewModel = ViewModelProviders.of(this).get(GeoJsonCategoryViewModel.class);
        geoJsonListViewModel = ViewModelProviders.of(this).get(GeoJsonListViewModel.class);


        commonPlacesAttribViewModel = ViewModelProviders.of(this).get(CommonPlacesAttribViewModel.class);
        hospitalFacilitiesVewModel = ViewModelProviders.of(this).get(HospitalFacilitiesVewModel.class);
        educationalInstitutesViewModel = ViewModelProviders.of(this).get(EducationalInstitutesViewModel.class);
        openSpaceViewModel = ViewModelProviders.of(this).get(OpenSpaceViewModel.class);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            LocaleChanger.setLocale(new Locale("ne", "NP"));

//            ActivityRecreationHelper.recreate(ReportActivityOld.this, true);
        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                handleStoragePermission();
            }
        }, 2000);

    }


    @AfterPermissionGranted(RESULT_STORAGE_PERMISSION)
    private void handleStoragePermission() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

//            create your own Directory
            CreateAppMainFolderUtils createAppMainFolderUtils = new CreateAppMainFolderUtils(SplashActivity.this,
                    appmainFolderName);
            createAppMainFolderUtils.createMainFolder();

//            if(createAppMainFolderUtils.isFolderExist("media")) {
//                String[] image = {"https://tinyurl.com/ybu9syvj", Environment.getExternalStorageDirectory() + "/"+appmainFolderName+"/"+"media/"+"downloadTestImage.jpg"};
//                ImageSaveTask imageSaveTask = new ImageSaveTask(SplashActivity.this);
//                imageSaveTask.execute(image);
//            }


            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            SharedPreferenceUtils sharedPreference = new SharedPreferenceUtils(this);

            Date date = Calendar.getInstance(new Locale("en", "US")).getTime();
//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
//            String formattedDate = df.format(date);
            String formattedDate = date.toString();


            if (formattedDate.equals(sharedPreference.getStringValue("time", ""))) {
                SectionGridHomeActivity.start(SplashActivity.this);
            } else {
                SharedPreferenceUtils.getInstance(this).setValue("time", formattedDate);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    //we are connected to a network
//                    fetchGeoJsonCategoryList();
                    SectionGridHomeActivity.start(SplashActivity.this);

                } else {
                    // redirect to homepage27.1.1
//                    SectionGridHomeActivity.start(SplashActivity.this);
                    startActivity(new Intent(SplashActivity.this, SectionGridHomeActivity.class));
                }
            }


        } else {
            EasyPermissions.requestPermissions(this, "Provide storage permission to save data.",
                    RESULT_STORAGE_PERMISSION, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }



    ProgressDialog progressDialog;
    int[] totalCount = new int[1];
    int[] progress = new int[1];
    String[] geoJsonDisplayName = new String[1];

    private void fetchGeoJsonCategoryList() {
        progressDialog = DialogFactory.createProgressBarDialog(SplashActivity.this, "", "");
        progressDialog.show();


        final String[] geoJsonName = new String[1];
        final String[] summaryName = new String[1];
        final String[] geoJsonBaseType = new String[1];

        apiInterface
                .getGeoJsonCategoryDetails()
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<GeoJsonCategoryDetails, ObservableSource<List<GeoJsonCategoryEntity>>>() {
                    @Override
                    public ObservableSource<List<GeoJsonCategoryEntity>> apply(GeoJsonCategoryDetails geoJsonCategoryDetails) throws Exception {
                        totalCount[0] = geoJsonCategoryDetails.getData().size();
                        progressDialog.setMax(totalCount[0]);

                        return Observable.just(geoJsonCategoryDetails.getData());
                    }
                })
                .flatMapIterable(new Function<List<GeoJsonCategoryEntity>, Iterable<GeoJsonCategoryEntity>>() {
                    @Override
                    public Iterable<GeoJsonCategoryEntity> apply(List<GeoJsonCategoryEntity> geoJsonCategoryEntities) throws Exception {
                        return geoJsonCategoryEntities;
                    }
                })
                .flatMap(new Function<GeoJsonCategoryEntity, Observable<ResponseBody>>() {
                    @Override
                    public Observable<ResponseBody> apply(GeoJsonCategoryEntity geoJsonCategoryEntity) throws Exception {

                        geoJsonCategoryViewModel.insert(geoJsonCategoryEntity);
                        geoJsonDisplayName[0] = geoJsonCategoryEntity.getCategoryName();
                        geoJsonName[0] = geoJsonCategoryEntity.getCategoryTable();
                        geoJsonBaseType[0] = geoJsonCategoryEntity.getCategoryType();
                        summaryName[0] = geoJsonCategoryEntity.getSummaryName();

                        return apiInterface.getGeoJsonDetails(geoJsonCategoryEntity.getCategoryTable());
                    }

                })
                .subscribe(new DisposableObserver<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody s) {


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progress[0]++;

                                String alertMsg = getString(R.string.fetching_file, geoJsonDisplayName[0], String.valueOf(progress[0]), String.valueOf(totalCount[0]));
                                progressDialog.setMax(totalCount[0]);
                                progressDialog.setMessage(alertMsg);
                                progressDialog.setProgress(progress[0]);
                            }
                        });

                        BufferedReader reader = new BufferedReader(new InputStreamReader(s.byteStream()));
                        StringBuilder sb = new StringBuilder();
                        String line = null;
                        try {

                            while ((line = reader.readLine()) != null) {
                                sb.append(line).append("\n");
                            }
                            reader.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        String geoJsonToString = sb.toString();
                        Log.d(TAG, "onNext: GeoJson " + sb.toString());

                        if (!TextUtils.isEmpty(geoJsonToString)) {
                            geoJsonListViewModel.insert(new GeoJsonListEntity(geoJsonName[0], geoJsonToString));

//                            json parse and store to database
                            JSONObject jsonObject = null;
                            try {
                                int latlongDiffCounter = 0;

                                jsonObject = new JSONObject(geoJsonToString);

                                JSONArray jsonarray = new JSONArray(jsonObject.getString("features"));
                                Log.d(TAG, "onNext: " + "save data to database --> " + geoJsonName[0] + " , " + geoJsonBaseType[0]);

                                for (int i = 0; i < jsonarray.length(); i++) {
                                    JSONObject properties = new JSONObject(jsonarray.getJSONObject(i).getString("properties"));
                                    JSONObject geometry = new JSONObject(jsonarray.getJSONObject(i).getString("geometry"));
                                    JSONArray coordinates = geometry.getJSONArray("coordinates");

                                    String name = properties.has(summaryName[0]) ? properties.getString(summaryName[0])
                                            : properties.has("name") ? properties.getString("name")
                                            : properties.has("Name") ? properties.getString("Name")
                                            : properties.has("Name of Bank Providing ATM Service") ? properties.getString("Name of Bank Providing ATM Service")
                                            : "null";

                                    String address = properties.has("address") ? properties.getString("address") : properties.has("Address") ? properties.getString("Address") : " ";

                                    String type = geometry.getString("type");


                                    double longitude;
                                    double latitude;
                                    double latlongMakeDiff = Double.parseDouble("0.0000000000" + latlongDiffCounter + i);
                                    if (type.equals("Point")) {
                                        longitude = Double.parseDouble(coordinates.get(0).toString()) + latlongMakeDiff;
                                        latitude = Double.parseDouble(coordinates.get(1).toString()) + latlongMakeDiff;
                                    } else if (type.equals("MultiPolygon")) {
                                        JSONArray coordinates1 = coordinates.getJSONArray(0);
                                        JSONArray coordinates2 = coordinates1.getJSONArray(0);
                                        JSONArray coordinates3 = coordinates2.getJSONArray(0);

                                        longitude = Double.parseDouble(coordinates3.get(0).toString()) + latlongMakeDiff;
                                        latitude = Double.parseDouble(coordinates3.get(1).toString()) + latlongMakeDiff;
                                    } else {
// for multiLineString
                                        JSONArray coordinates1 = coordinates.getJSONArray(0);
                                        JSONArray coordinates2 = coordinates1.getJSONArray(0);

                                        longitude = Double.parseDouble(coordinates2.get(0).toString()) + latlongMakeDiff;
                                        latitude = Double.parseDouble(coordinates2.get(1).toString()) + latlongMakeDiff;
                                    }


                                    latlongDiffCounter++;
                                    if (latlongDiffCounter > 9) {
                                        latlongDiffCounter = 0;
                                    }


                                    CommonPlacesAttrb commonPlacesAttrb = new CommonPlacesAttrb(name, address, geoJsonName[0], latitude, longitude, summaryName[0], properties.toString());
                                    long id = commonPlacesAttribViewModel.insert(commonPlacesAttrb);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        finish();
                        Log.d(TAG, "onError: " + e.getMessage());

                    }

                    @Override
                    public void onComplete() {
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        SectionGridHomeActivity.start(SplashActivity.this);
                    }
                });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        newBase = LocaleChanger.configureBaseContext(newBase);
        super.attachBaseContext(newBase);
    }
}
