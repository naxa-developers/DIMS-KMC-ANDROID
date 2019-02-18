package np.com.naxa.iset.report;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import np.com.naxa.iset.R;
import np.com.naxa.iset.database.entity.ReportDetailsEntity;
import np.com.naxa.iset.event.ReportSavedFormListItemEvent;
import np.com.naxa.iset.gps.GeoPointActivity;
import np.com.naxa.iset.mycircle.registeruser.NormalResponse;
import np.com.naxa.iset.mycircle.registeruser.UserModel;
import np.com.naxa.iset.network.UrlClass;
import np.com.naxa.iset.network.retrofit.NetworkApiClient;
import np.com.naxa.iset.network.retrofit.NetworkApiInterface;
import np.com.naxa.iset.newhomepage.SectionGridHomeActivity;
import np.com.naxa.iset.utils.CalendarUtils;
import np.com.naxa.iset.utils.CreateAppMainFolderUtils;
import np.com.naxa.iset.utils.DialogFactory;
import np.com.naxa.iset.utils.JsonGsonConverterUtils;
import np.com.naxa.iset.utils.SharedPreferenceUtils;
import np.com.naxa.iset.utils.ToastUtils;
import np.com.naxa.iset.utils.imageutils.LoadImageUtils;
import np.com.naxa.iset.database.viewmodel.ReportDetailsViewModel;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class ReportActivity extends AppCompatActivity {
    private static final String TAG = "ReportActivity";
    @BindView(R.id.toolbar_general)
    Toolbar toolbarGeneral;
    @BindView(R.id.appbar_general)
    AppBarLayout appbarGeneral;
    @BindView(R.id.spn_ward_no)
    Spinner spnWardNo;
    @BindView(R.id.spn_hazard_type)
    Spinner spnHazardType;
    @BindView(R.id.btn_photo)
    Button btnPhoto;
    @BindView(R.id.btn_gps_location)
    Button btnGPSLocation;
    @BindView(R.id.et_message)
    EditText etMessage;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.et_occurance_date)
    EditText etOccuranceDate;
    @BindView(R.id.et_occurance_time)
    EditText etOccuranceTime;
    @BindView(R.id.et_vdc_name)
    EditText etVdcName;
    @BindView(R.id.et_name_of_the_place)
    EditText etNameOfThePlace;
    @BindView(R.id.spn_risk_level)
    Spinner spnRiskLevel;
    @BindView(R.id.spn_disaster_status)
    Spinner spnDisasterStatus;
    @BindView(R.id.et_reporter_name)
    EditText etReporterName;
    @BindView(R.id.et_reporter_address)
    EditText etReporterAddress;
    @BindView(R.id.et_reporter_contact)
    EditText etReporterContact;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.ivImagePreview)
    ImageView ivImagePreview;
    @BindView(R.id.et_name_of_the_ward_staff)
    EditText etNameOfTheWardStaff;
    @BindView(R.id.et_designation)
    EditText etDesignation;
    @BindView(R.id.et_total_no_of_death)
    EditText etTotalNoOfDeath;
    @BindView(R.id.et_total_no_of_injured)
    EditText etTotalNoOfInjured;
    @BindView(R.id.et_affected_people)
    EditText etAffectedPeople;
    @BindView(R.id.spn_damage_of_the_infrastructure)
    Spinner spnDamageOfTheInfrastructure;
    @BindView(R.id.et_animals_affected)
    EditText etAnimalsAffected;
    @BindView(R.id.et_estimated_loss)
    EditText etEstimatedLoss;
    @BindView(R.id.layout_staff_form_field)
    LinearLayout layoutStaffFormField;

    private String imageFilePath = null, imageNameToBeSaved = "";
    private File imageFileToBeUploaded;
    private Boolean hasNewImage = false;

    SharedPreferenceUtils sharedPreferenceUtils;
    CreateAppMainFolderUtils createAppMainFolderUtils;

    private boolean isFromSavedForm = false;
    private boolean isFromUnverifiedForm = false;

    public static final int GEOPOINT_RESULT_CODE = 1994;
    public static final String LOCATION_RESULT = "LOCATION_RESULT";
    private final int RESULT_LOCATION_PERMISSION = 100;
    double myLat = 0.0;
    double myLong = 0.0;
    String latitude, longitude;
    ReportDetailsEntity reportDetailsEntity;

    ReportDetailsViewModel reportDetailsViewModel;
    NetworkApiInterface apiInterface;

    ArrayAdapter<String> wardAdapter, hazardAdapter, riskLevelAdapter, disasterStatusAdapter, infrastructureDamageAdapter;

    String edited = "0";

    private static final int KEY_SAVE = 111;
    private static final int KEY_SEND = 121;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);

        createAppMainFolderUtils = new CreateAppMainFolderUtils(ReportActivity.this, CreateAppMainFolderUtils.appmainFolderName);
        reportDetailsViewModel = ViewModelProviders.of(this).get(ReportDetailsViewModel.class);
        apiInterface = NetworkApiClient.getAPIClient().create(NetworkApiInterface.class);


        sharedPreferenceUtils = new SharedPreferenceUtils(ReportActivity.this);
        setupToolBar();
        setUpSpinner();

        initDefaultField();
    }

    private void setupToolBar() {
        setSupportActionBar(toolbarGeneral);
        getSupportActionBar().setTitle("Report");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setUpSpinner() {
        wardAdapter = new ArrayAdapter<String>(ReportActivity.this,
                R.layout.item_spinner, getResources().getStringArray(R.array.ward_no));
        wardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnWardNo.setAdapter(wardAdapter);

        hazardAdapter = new ArrayAdapter<String>(ReportActivity.this,
                R.layout.item_spinner, getResources().getStringArray(R.array.hazard_type));
        hazardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnHazardType.setAdapter(hazardAdapter);

        riskLevelAdapter = new ArrayAdapter<String>(ReportActivity.this,
                R.layout.item_spinner, getResources().getStringArray(R.array.risk_level));
        riskLevelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnRiskLevel.setAdapter(riskLevelAdapter);

        disasterStatusAdapter = new ArrayAdapter<String>(ReportActivity.this,
                R.layout.item_spinner, getResources().getStringArray(R.array.disaster_status));
        disasterStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDisasterStatus.setAdapter(disasterStatusAdapter);

        infrastructureDamageAdapter = new ArrayAdapter<String>(ReportActivity.this,
                R.layout.item_spinner, getResources().getStringArray(R.array.infrastructure_damage));
        infrastructureDamageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDamageOfTheInfrastructure.setAdapter(infrastructureDamageAdapter);


    }

    private void initDefaultField() {
        etVdcName.setText("Kathmandu Metropolitan City");
        if (sharedPreferenceUtils.getBoolanValue(SharedPreferenceUtils.USER_ALREADY_LOGGED_IN, false)) {

            UserModel userModel = JsonGsonConverterUtils.getUserFromJson(sharedPreferenceUtils.getStringValue(SharedPreferenceUtils.USER_DETAILS, null));

            etReporterName.setText(userModel.getFullName());
            etReporterAddress.setText(userModel.getAddress());
            etReporterContact.setText(userModel.getMobileNo());
        }
    }

    @OnClick({R.id.et_occurance_date, R.id.et_occurance_time, R.id.btn_photo, R.id.btn_gps_location, R.id.btn_save, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_occurance_date:
                CalendarUtils.datePickerDialog(ReportActivity.this, etOccuranceDate).show();
                break;

            case R.id.et_occurance_time:
                CalendarUtils.timePickerDialog(ReportActivity.this, etOccuranceTime).show();
                break;

            case R.id.btn_photo:
                EasyImage.openChooserWithDocuments(ReportActivity.this, "Take Image", 0);
                break;

            case R.id.btn_gps_location:
                handleLocationPermission();
                break;

            case R.id.btn_save:
                if (hasLocationAndImage()) {
                    if (validateSpinner()) {
                        convertDataToJson(KEY_SAVE);
                        saveDataToDatabase();
                    }
                }

                break;

            case R.id.btn_submit:
//                if (hasLocationAndImage()) {
//                    if (validateSpinner()) {
//                        convertDataToJson(KEY_SEND);
//                        sentDataToServer();
//                    }
//                }
                break;
        }
    }

    @AfterPermissionGranted(RESULT_LOCATION_PERMISSION)
    private void handleLocationPermission() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (!statusOfGPS) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            } else {

                Intent toGeoPointActivity = new Intent(this, GeoPointActivity.class);
                startActivityForResult(toGeoPointActivity, GEOPOINT_RESULT_CODE);

            }
        } else {
            EasyPermissions.requestPermissions(this, "Provide location permission.",
                    RESULT_LOCATION_PERMISSION, Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GEOPOINT_RESULT_CODE) {
            switch (resultCode) {
                case RESULT_OK:
                    String location = data.getStringExtra(LOCATION_RESULT);

                    Log.d(TAG, "onActivityResult: " + location.toString());

                    String string = location;
                    String[] parts = string.split(" ");
                    String split_lat = parts[0]; // 004
                    String split_lon = parts[1]; // 034556

                    if (!split_lat.equals("") && !split_lon.equals("")) {
                        myLat = Double.parseDouble(split_lat);
                        myLong = Double.parseDouble(split_lon);
                        btnGPSLocation.setText("Recorded");
//                        showLoading("Please wait ... \nCalculating distance");
                    } else {
//                        showInfoToast("Cannot calculate distance");
                        Toast.makeText(this, "Cannot get location", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
                Toast.makeText(ReportActivity.this, "Image  Error Occurred", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {

                imageFilePath = imageFile.getAbsolutePath();

                int file_size = Integer.parseInt(String.valueOf(imageFile.length() / 1024));
                Log.d(TAG, "onImagePicked: file size " + file_size + " KB");


                new Compressor(ReportActivity.this)
                        .compressToFileAsFlowable(imageFile)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<File>() {
                            @Override
                            public void accept(File file) {
                                imageFileToBeUploaded = file;
                                int file_size = Integer.parseInt(String.valueOf(imageFileToBeUploaded.length() / 1024));
                                Log.d(TAG, "accept: compressed file size " + file_size + " KB");
                                Log.d(TAG, "accept: " + imageFileToBeUploaded.getAbsolutePath());

                                imageNameToBeSaved = CalendarUtils.getTimeInMilisecond();
                                LoadImageUtils.imageSaveFileToSpecificDirectory(getBitmapOfImageFile(imageFileToBeUploaded.getAbsoluteFile().getAbsolutePath()),
                                        imageNameToBeSaved, createAppMainFolderUtils.getAppMediaFolderName());

                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) {
                                throwable.printStackTrace();
//                                showError(throwable.getMessage());
                                ToastUtils.showToast(throwable.getMessage().toString());
                                imageFileToBeUploaded = imageFile;
                                imageNameToBeSaved = CalendarUtils.getTimeInMilisecond();
                                Log.d(TAG, "accept: failed to compress " + imageFileToBeUploaded.getAbsolutePath());
                                LoadImageUtils.imageSaveFileToSpecificDirectory(getBitmapOfImageFile(imageFileToBeUploaded.getAbsolutePath()),
                                        imageNameToBeSaved, createAppMainFolderUtils.getAppMediaFolderName());
                            }
                        });

                hasNewImage = true;
            }

        });
    }

    private Bitmap getBitmapOfImageFile(String absoluteImageFilePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(absoluteImageFilePath, options);
        ivImagePreview.setVisibility(View.VISIBLE);
        ivImagePreview.setImageBitmap(bitmap);
        return bitmap;
    }

    private boolean hasLocationAndImage() {
        boolean hasLocationAndImage;


        if (myLat == 0 && myLong == 0) {
            Toast.makeText(ReportActivity.this, "you need to take GPS Location first", Toast.LENGTH_SHORT).show();
            hasLocationAndImage = false;
        } else if (!hasNewImage) {
            Toast.makeText(ReportActivity.this, "you need to take Photo first", Toast.LENGTH_SHORT).show();
            hasLocationAndImage = false;
        } else {
            hasLocationAndImage = true;
        }

        return hasLocationAndImage;
    }

    private boolean validateSpinner() {
        boolean status;
        if (spnHazardType.getSelectedItemId() == 0) {
            Toast.makeText(this, "Please select incident type", Toast.LENGTH_SHORT).show();
            status = false;
        } else if (spnWardNo.getSelectedItemId() == 0) {
            Toast.makeText(this, "Please select ward", Toast.LENGTH_SHORT).show();
            status = false;
        } else {
            status = true;
        }
        return status;
    }

    String jsonInString = "";

    private void convertDataToJson(int KEY) {

        if (!validateSpinner()) {
            return;
        }
        latitude = myLat + "";
        longitude = myLong + "";

//        latitude ="27.8565454";
//        longitude =  "85.6554545";

        Gson gson = new Gson();


        if (isFromSavedForm) {
            reportDetailsEntity = new ReportDetailsEntity(unique_id, "ward", spnHazardType.getSelectedItem().toString(),"", etOccuranceDate.getText().toString(),
                    etOccuranceTime.getText().toString(), etVdcName.getText().toString(), etNameOfThePlace.getText().toString(),
                    spnWardNo.getSelectedItem().toString(), spnRiskLevel.getSelectedItem().toString(), imageNameToBeSaved,
                    spnDisasterStatus.getSelectedItem().toString(), etReporterName.getText().toString(), etReporterAddress.getText().toString(),
                    etReporterContact.getText().toString(), etMessage.getText().toString(), "0", latitude, longitude,
                    etNameOfTheWardStaff.getText().toString(), etDesignation.getText().toString(), etTotalNoOfDeath.getText().toString(),
                    etTotalNoOfInjured.getText().toString(), etAffectedPeople.getText().toString(), spnDamageOfTheInfrastructure.getSelectedItem().toString(),
                    etAffectedPeople.getText().toString(), etEstimatedLoss.getText().toString(), edited);
        }
        else if (isFromUnverifiedForm && KEY == KEY_SAVE) {
            reportDetailsEntity = new ReportDetailsEntity(unique_id,"ward", spnHazardType.getSelectedItem().toString(), "" , etOccuranceDate.getText().toString(),
                    etOccuranceTime.getText().toString(), etVdcName.getText().toString(), etNameOfThePlace.getText().toString(),
                    spnWardNo.getSelectedItem().toString(), spnRiskLevel.getSelectedItem().toString(), imageNameToBeSaved,
                    spnDisasterStatus.getSelectedItem().toString(), etReporterName.getText().toString(), etReporterAddress.getText().toString(),
                    etReporterContact.getText().toString(), etMessage.getText().toString(), "0", latitude, longitude,
                    etNameOfTheWardStaff.getText().toString(), etDesignation.getText().toString(), etTotalNoOfDeath.getText().toString(),
                    etTotalNoOfInjured.getText().toString(), etAffectedPeople.getText().toString(), spnDamageOfTheInfrastructure.getSelectedItem().toString(),
                    etAffectedPeople.getText().toString(), etEstimatedLoss.getText().toString(), edited);
        }
        else if (isFromUnverifiedForm && KEY == KEY_SEND) {
            reportDetailsEntity = new ReportDetailsEntity(unique_id,"ward", spnHazardType.getSelectedItem().toString(),"", etOccuranceDate.getText().toString(),
                    etOccuranceTime.getText().toString(), etVdcName.getText().toString(), etNameOfThePlace.getText().toString(),
                    spnWardNo.getSelectedItem().toString(), spnRiskLevel.getSelectedItem().toString(), imageNameToBeSaved,
                    spnDisasterStatus.getSelectedItem().toString(), etReporterName.getText().toString(), etReporterAddress.getText().toString(),
                    etReporterContact.getText().toString(), etMessage.getText().toString(), "1", latitude, longitude,
                    etNameOfTheWardStaff.getText().toString(), etDesignation.getText().toString(), etTotalNoOfDeath.getText().toString(),
                    etTotalNoOfInjured.getText().toString(), etAffectedPeople.getText().toString(), spnDamageOfTheInfrastructure.getSelectedItem().toString(),
                    etAffectedPeople.getText().toString(), etEstimatedLoss.getText().toString(), edited);
        }
        else {
            reportDetailsEntity = new ReportDetailsEntity(CalendarUtils.getTimeInMilisecond(),"ward",spnHazardType.getSelectedItem().toString(), "", etOccuranceDate.getText().toString(),
                    etOccuranceTime.getText().toString(), etVdcName.getText().toString(), etNameOfThePlace.getText().toString(),
                    spnWardNo.getSelectedItem().toString(), spnRiskLevel.getSelectedItem().toString(), imageNameToBeSaved,
                    spnDisasterStatus.getSelectedItem().toString(), etReporterName.getText().toString(), etReporterAddress.getText().toString(),
                    etReporterContact.getText().toString(), etMessage.getText().toString(), "0", latitude, longitude,
                    etNameOfTheWardStaff.getText().toString(), etDesignation.getText().toString(), etTotalNoOfDeath.getText().toString(),
                    etTotalNoOfInjured.getText().toString(), etAffectedPeople.getText().toString(), spnDamageOfTheInfrastructure.getSelectedItem().toString(),
                    etAffectedPeople.getText().toString(), etEstimatedLoss.getText().toString(), edited);
        }


    jsonInString =gson.toJson(reportDetailsEntity);
        Log.d(TAG,"convertDataToJson: "+jsonInString);

}
    long id;

    private void saveDataToDatabase() {
        id = reportDetailsViewModel.insert(reportDetailsEntity);

        if (id < 0) {
            DialogFactory.createCustomErrorDialog(ReportActivity.this, "Unable to save data", new DialogFactory.CustomDialogListener() {
                @Override
                public void onClick() {

                }
            }).show();

        } else {
            DialogFactory.createCustomDialog(ReportActivity.this, "Data saved successfully", new DialogFactory.CustomDialogListener() {
                @Override
                public void onClick() {
                    btnSave.setEnabled(false);

                }
            }).show();
        }

        Log.d(TAG, "saveDataToDatabase: insert " + id);


    }

    private void sentDataToServer() {

        apiInterface.getReportSendResponse(UrlClass.API_ACCESS_TOKEN, jsonInString)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableObserver<NormalResponse>() {
                    @Override
                    public void onNext(NormalResponse normalResponse) {

                        if (normalResponse.getError() == 0) {
                            DialogFactory.createCustomDialog(ReportActivity.this, normalResponse.getMessage(), new DialogFactory.CustomDialogListener() {
                                @Override
                                public void onClick() {
                                    if (isFromSavedForm || isFromUnverifiedForm) {
                                        reportDetailsViewModel.deleteSpecificRow(unique_id);
                                    }
                                    startActivity(new Intent(ReportActivity.this, SectionGridHomeActivity.class));

                                }
                            }).show();
                        }

                        if (normalResponse.getError() == 1) {
                            DialogFactory.createCustomErrorDialog(ReportActivity.this, normalResponse.getMessage(), new DialogFactory.CustomDialogListener() {
                                @Override
                                public void onClick() {

                                }
                            }).show();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        DialogFactory.createCustomErrorDialog(ReportActivity.this, e.getMessage(), new DialogFactory.CustomDialogListener() {
                            @Override
                            public void onClick() {

                            }
                        }).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        // Check for existing Google Sign In account, if the user is already signed in
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private int dbID;
    private String unique_id;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReportSavedRowItemClick(ReportSavedFormListItemEvent.ReportSavedFormListItemClick itemClick) {

        isFromSavedForm = true;
        hasNewImage = true;
        imageNameToBeSaved = itemClick.getReportDetailsEntity().getPhoto_name();
        dbID = itemClick.getReportDetailsEntity().getRid();
        unique_id = itemClick.getReportDetailsEntity().getUnique_id();

        spnHazardType.setSelection(hazardAdapter.getPosition(itemClick.getReportDetailsEntity().getIncident_type()));
        etOccuranceDate.setText(itemClick.getReportDetailsEntity().getDate());
        etOccuranceTime.setText(itemClick.getReportDetailsEntity().getTime());
        etVdcName.setText(itemClick.getReportDetailsEntity().getVdc_mun());
        spnWardNo.setSelection(wardAdapter.getPosition(itemClick.getReportDetailsEntity().getWard()));
        etNameOfThePlace.setText(itemClick.getReportDetailsEntity().getPlace_name());

        imageFileToBeUploaded = new File(createAppMainFolderUtils.getAppMediaFolderName() + "/" + itemClick.getReportDetailsEntity().getPhoto_name() + ".jpg");
        getBitmapOfImageFile(imageFileToBeUploaded.getAbsolutePath());

        myLat = Double.parseDouble(itemClick.getReportDetailsEntity().getLatitude());
        myLong = Double.parseDouble(itemClick.getReportDetailsEntity().getLongitude());
        btnGPSLocation.setText("Recorded");
        btnGPSLocation.setEnabled(false);

        etReporterName.setText(itemClick.getReportDetailsEntity().getName_reporter());
        etReporterAddress.setText(itemClick.getReportDetailsEntity().getAddress());
        etReporterContact.setText(itemClick.getReportDetailsEntity().getContact_reporter());
        etMessage.setText(itemClick.getReportDetailsEntity().getRemarks());
        etNameOfTheWardStaff.setText(itemClick.getReportDetailsEntity().getWard_staff_name());
        etDesignation.setText(itemClick.getReportDetailsEntity().getDesignation());

        spnRiskLevel.setSelection(riskLevelAdapter.getPosition(itemClick.getReportDetailsEntity().getRisk_level()));
        spnDisasterStatus.setSelection(disasterStatusAdapter.getPosition(itemClick.getReportDetailsEntity().getStatus()));

        etTotalNoOfDeath.setText(itemClick.getReportDetailsEntity().getDeath_no());
        etTotalNoOfInjured.setText(itemClick.getReportDetailsEntity().getInjured_no());
        etAffectedPeople.setText(itemClick.getReportDetailsEntity().getAffected_people_no());

        spnDamageOfTheInfrastructure.setSelection(infrastructureDamageAdapter.getPosition(itemClick.getReportDetailsEntity().getInfrastructure_damage()));

        etAnimalsAffected.setText(itemClick.getReportDetailsEntity().getAffected_animal_no());
        etEstimatedLoss.setText(itemClick.getReportDetailsEntity().getEstimated_loss());


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReportUnverifiedRowItemClick(ReportSavedFormListItemEvent.ReportUnverifiedFormListItemClick itemClick) {

        edited = "1";

        isFromUnverifiedForm = true;
        hasNewImage = true;
        imageNameToBeSaved = itemClick.getReportDetailsEntity().getPhoto_name();
        dbID = itemClick.getReportDetailsEntity().getRid();
        unique_id = itemClick.getReportDetailsEntity().getUnique_id();

        spnHazardType.setSelection(hazardAdapter.getPosition(itemClick.getReportDetailsEntity().getIncident_type()));
        etOccuranceDate.setText(itemClick.getReportDetailsEntity().getDate());
        etOccuranceTime.setText(itemClick.getReportDetailsEntity().getTime());
        etVdcName.setText(itemClick.getReportDetailsEntity().getVdc_mun());
        spnWardNo.setSelection(wardAdapter.getPosition(itemClick.getReportDetailsEntity().getWard()));
        etNameOfThePlace.setText(itemClick.getReportDetailsEntity().getPlace_name());

        imageFileToBeUploaded = new File(createAppMainFolderUtils.getAppMediaFolderName() + "/" + itemClick.getReportDetailsEntity().getPhoto_name() + ".jpg");
        getBitmapOfImageFile(imageFileToBeUploaded.getAbsolutePath());

        myLat = Double.parseDouble(itemClick.getReportDetailsEntity().getLatitude());
        myLong = Double.parseDouble(itemClick.getReportDetailsEntity().getLongitude());
        btnGPSLocation.setText("Recorded");
        btnGPSLocation.setEnabled(false);

        etReporterName.setText(itemClick.getReportDetailsEntity().getName_reporter());
        etReporterAddress.setText(itemClick.getReportDetailsEntity().getAddress());
        etReporterContact.setText(itemClick.getReportDetailsEntity().getContact_reporter());
        etMessage.setText(itemClick.getReportDetailsEntity().getRemarks());
        etNameOfTheWardStaff.setText(itemClick.getReportDetailsEntity().getWard_staff_name());
        etDesignation.setText(itemClick.getReportDetailsEntity().getDesignation());

        spnRiskLevel.setSelection(riskLevelAdapter.getPosition(itemClick.getReportDetailsEntity().getRisk_level()));
        spnDisasterStatus.setSelection(disasterStatusAdapter.getPosition(itemClick.getReportDetailsEntity().getStatus()));

        etTotalNoOfDeath.setText(itemClick.getReportDetailsEntity().getDeath_no());
        etTotalNoOfInjured.setText(itemClick.getReportDetailsEntity().getInjured_no());
        etAffectedPeople.setText(itemClick.getReportDetailsEntity().getAffected_people_no());

        spnDamageOfTheInfrastructure.setSelection(infrastructureDamageAdapter.getPosition(itemClick.getReportDetailsEntity().getInfrastructure_damage()));

        etAnimalsAffected.setText(itemClick.getReportDetailsEntity().getAffected_animal_no());
        etEstimatedLoss.setText(itemClick.getReportDetailsEntity().getEstimated_loss());


    }

}
