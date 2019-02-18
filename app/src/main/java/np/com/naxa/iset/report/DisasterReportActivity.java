package np.com.naxa.iset.report;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import np.com.naxa.iset.R;
import np.com.naxa.iset.database.entity.ReportDetailsEntity;
import np.com.naxa.iset.database.viewmodel.ReportDetailsViewModel;
import np.com.naxa.iset.network.retrofit.NetworkApiClient;
import np.com.naxa.iset.network.retrofit.NetworkApiInterface;
import np.com.naxa.iset.utils.CalendarUtils;
import np.com.naxa.iset.utils.CreateAppMainFolderUtils;
import np.com.naxa.iset.utils.SharedPreferenceUtils;

public class DisasterReportActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_general)
    Toolbar toolbarGeneral;
    @BindView(R.id.spn_hazard_type)
    Spinner spnHazardType;
    @BindView(R.id.et_occurance_date)
    EditText etOccuranceDate;
    @BindView(R.id.et_occurance_time)
    EditText etOccuranceTime;
    @BindView(R.id.spn_ward_no)
    Spinner spnWardNo;
    @BindView(R.id.et_name_of_the_place)
    EditText etNameOfThePlace;
    @BindView(R.id.btn_photo)
    Button btnPhoto;
    @BindView(R.id.btn_gps_location)
    Button btnGpsLocation;
    @BindView(R.id.ivImagePreview)
    ImageView ivImagePreview;
    @BindView(R.id.et_reporter_name)
    EditText etReporterName;
    @BindView(R.id.et_reporter_address)
    EditText etReporterAddress;
    @BindView(R.id.et_reporter_contact)
    EditText etReporterContact;
    @BindView(R.id.et_message)
    EditText etMessage;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.appbar_general)
    AppBarLayout appbarGeneral;


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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disaster_report);
        ButterKnife.bind(this);

        createAppMainFolderUtils = new CreateAppMainFolderUtils(DisasterReportActivity.this, CreateAppMainFolderUtils.appmainFolderName);
        reportDetailsViewModel = ViewModelProviders.of(this).get(ReportDetailsViewModel.class);
        apiInterface = NetworkApiClient.getAPIClient().create(NetworkApiInterface.class);


        sharedPreferenceUtils = new SharedPreferenceUtils(DisasterReportActivity.this);

        setupToolBar();
        setUpSpinner();
    }


    private void setupToolBar() {
        setSupportActionBar(toolbarGeneral);
        getSupportActionBar().setTitle("Disaster Report");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setUpSpinner() {
        wardAdapter = new ArrayAdapter<String>(DisasterReportActivity.this,
                R.layout.item_spinner, getResources().getStringArray(R.array.ward_no));
        wardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnWardNo.setAdapter(wardAdapter);

        hazardAdapter = new ArrayAdapter<String>(DisasterReportActivity.this,
                R.layout.item_spinner, getResources().getStringArray(R.array.hazard_type));
        hazardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnHazardType.setAdapter(hazardAdapter);
    }


    @OnClick({R.id.et_occurance_date, R.id.et_occurance_time, R.id.btn_save, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_occurance_date:
                CalendarUtils.datePickerDialog(DisasterReportActivity.this, etOccuranceDate).show();
                break;

            case R.id.et_occurance_time:
                CalendarUtils.timePickerDialog(DisasterReportActivity.this, etOccuranceTime).show();
                break;

            case R.id.btn_save:
                break;
            case R.id.btn_submit:
                break;
        }
    }

    @OnClick(R.id.btn_gps_location)
    public void onViewClicked() {
    }
}
