package np.com.naxa.iset.activity;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.franmontiel.localechanger.LocaleChanger;
import com.franmontiel.localechanger.utils.ActivityRecreationHelper;

import org.json.JSONObject;

import java.io.File;
import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import np.com.naxa.iset.R;
import np.com.naxa.iset.firebase.MessageActivity;
import np.com.naxa.iset.firebase.MessageHelper;
import np.com.naxa.iset.gps.GeoPointActivity;
import np.com.naxa.iset.home.HomeActivity;
import np.com.naxa.iset.network.model.AskForHelpResponse;
import np.com.naxa.iset.network.retrofit.NetworkApiInterface;
import np.com.naxa.iset.utils.DialogFactory;
import np.com.naxa.iset.utils.SharedPreferenceUtils;
import np.com.naxa.iset.utils.ToastUtils;
import np.com.naxa.iset.database.viewmodel.MessageHelperViewModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static np.com.naxa.iset.report.ReportActivity.LOCATION_RESULT;
import static np.com.naxa.iset.network.UrlClass.REQUEST_OK;
import static np.com.naxa.iset.network.retrofit.NetworkApiClient.getAPIClient;


public class ReportActivityOld extends AppCompatActivity implements LocationListener {
    private static final String TAG = "ReportActivityOld";
    Button btnCamera, btnGetGPS;
    ImageView imagePreview;
    TextInputLayout tvName;
    TextInputLayout tvMessage;
    TextInputLayout tvContactNumber;
    Button btnSubmit;
    Button vSave;
    LocationManager DcoLocationManager;
    Toolbar toolbar;
    private boolean specifyOthers;

    public static final int GEOPOINT_RESULT_CODE = 1994;
//    public static final String LOCATION_RESULT = "LOCATION_RESULT";
    double myLat = 0.0;
    double myLong = 0.0;


    String imageFilePath = null;
    File imageFileToBeUploaded;
    Boolean hasNewImage = false;
    String full_name, contact_no, detailed_message, latitude, longitude, incident_type, incident_type_others, incident_time, ward;
    String jsonToSend = "";
    String token_id;
    SharedPreferenceUtils sharedPreferenceUtils;

    @BindView(R.id.tv_incident_time)
    TextInputLayout tvIncidentTime;
    @BindView(R.id.spinner_ward)
    Spinner spinnerWard;
    @BindView(R.id.spinner_incident_type)
    Spinner spinnerIncidentType;
    @BindView(R.id.tv_incident_type_others)
    TextInputLayout tvIncidentTypeOthers;


    MessageHelperViewModel messageHelperViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_v2);
        ButterKnife.bind(this);

        tvIncidentTypeOthers.setVisibility(View.GONE);

        sharedPreferenceUtils = new SharedPreferenceUtils(ReportActivityOld.this);

        messageHelperViewModel = ViewModelProviders.of(this).get(MessageHelperViewModel.class);

        initUI();

        initToolbar();

        setCurrentDate();

        spinnerItemSelectedListner();

//        IcLocationTracker = new LocationTracker(getApplicationContext());
        DcoLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // DcoLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ReportActivityOld);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EasyImage.openChooserWithDocuments(ReportActivityOld.this, "Take Image", 0);
            }
        });

        btnGetGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserCurrenLocation();
            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                token_id = sharedPreferenceUtils.getStringValue(SharedPreferenceUtils.TOKEN_ID, null);


                if (myLat == 0 && myLong == 0) {

                    Toast.makeText(ReportActivityOld.this, "you need to take GPS Location first", Toast.LENGTH_SHORT).show();
                    return;
                }
                incident_time = tvIncidentTime.getEditText().getText().toString();
//                incident_type = spinnerIncidentType.getSelectedItem().toString();

//                if(specifyOthers){
//                    incident_type = tvIncidentTypeOthers.getEditText().getText().toString();
//                }else {
//                    incident_type = spinnerIncidentType.getSelectedItem().toString();
                    incident_type = getIncidentTypeSpinnerData(spinnerIncidentType.getSelectedItemPosition());
//                }

                ward = getWardSpinnerData(spinnerWard.getSelectedItemPosition());
//                incident_type_others = tvIncidentTypeOthers.getEditText().getText().toString();
                full_name = tvName.getEditText().getText().toString();
                contact_no = tvContactNumber.getEditText().getText().toString();
                detailed_message = tvMessage.getEditText().getText().toString();
                latitude = myLat + "";
                longitude = myLong + "";

                try {
                    JSONObject header = new JSONObject();

                    header.put("status", "pending");
                    header.put("token_id", token_id);
                    header.put("incident_time", incident_time);
                    header.put("incident_type", incident_type);
                    header.put("ward", ward);
                    header.put("name", full_name);
                    header.put("contact_no", contact_no);
                    header.put("latitude", latitude);
                    header.put("longitude", longitude);
                    header.put("message", detailed_message);

                    jsonToSend = header.toString();
                    Log.d(TAG, "onClick: " + jsonToSend);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                sendDataToServer(jsonToSend);

            }
        });
    }

    private String getIncidentTypeSpinnerData( int pos){
        String incidentType = "";
        String[] incidentTypeArray = getResources().getStringArray(R.array.array_incident_type_no_locale);

        incidentType = incidentTypeArray[pos];
        return incidentType;
    }

    private String getWardSpinnerData( int pos){
        String ward = "";
        String[] incidentTypeArray = getResources().getStringArray(R.array.array_ward_no_locale);

        ward = incidentTypeArray[pos];
        return ward;
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar_general);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(ReportActivityOld.this.getResources().getString(R.string.ask_for_help));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void spinnerItemSelectedListner (){
        spinnerIncidentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = parent.getItemAtPosition(position).toString();
                Log.d(TAG, "onItemSelected: "+selectedItem);
//                if(selectedItem.equals("Others"))
//                {
//                    specifyOthers = true;
//                    tvIncidentTypeOthers.getEditText().setVisibility(View.VISIBLE);
//                    incident_type = tvIncidentTypeOthers.getEditText().getText().toString();
//                }else {
//                    specifyOthers = false;
//                    tvIncidentTypeOthers.getEditText().setVisibility(View.GONE);
//                    tvIncidentTypeOthers.getEditText().setText("");
//                    incident_type = spinnerIncidentType.getSelectedItem().toString();
//                }
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }

    private void initUI() {
        tvName = findViewById(R.id.editText);
        tvMessage = findViewById(R.id.message);
        tvContactNumber = findViewById(R.id.editText4);
        btnCamera = findViewById(R.id.camera_icon);
        btnGetGPS = findViewById(R.id.takeGps_button);
        imagePreview = (ImageView) findViewById(R.id.imagePreview);
        btnSubmit = findViewById(R.id.imageButton);
//        vSave = findViewById(R.id.imageButton2);
    }

    private void getUserCurrenLocation() {
        Intent toGeoPointActivity = new Intent(this, GeoPointActivity.class);
        startActivityForResult(toGeoPointActivity, GEOPOINT_RESULT_CODE);
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
                        btnGetGPS.setText("Recorded");
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
                Toast.makeText(ReportActivityOld.this, "Image  Error Occurred", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {

                imageFilePath = imageFile.getAbsolutePath();

                int file_size = Integer.parseInt(String.valueOf(imageFile.length()/1024));
                Log.d(TAG, "onImagePicked: "+file_size+" KB");


                new Compressor(ReportActivityOld.this)
                        .compressToFileAsFlowable(imageFile)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<File>() {
                            @Override
                            public void accept(File file) {
                                imageFileToBeUploaded = file;
                                int file_size = Integer.parseInt(String.valueOf(imageFileToBeUploaded.length()/1024));
                                Log.d(TAG, "onImagePicked: "+file_size+" KB");

                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) {
                                throwable.printStackTrace();
//                                showError(throwable.getMessage());
                                ToastUtils.showToast(throwable.getMessage().toString());
                                imageFileToBeUploaded = imageFile;
                            }
                        });

                Glide
                        .with(getApplicationContext())
                        .load(imageFilePath)
                        .into(imagePreview);
                imagePreview.setVisibility(View.VISIBLE);

                hasNewImage = true;

            }

        });
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public static void start(Context context) {
        Intent intent = new Intent(context, ReportActivityOld.class);
        context.startActivity(intent);
    }

    File imageFile;
    MultipartBody.Part body = null;
    Bitmap bitmap;

    private void sendDataToServer(String jsonData) {

        ProgressDialog progressDialog = DialogFactory.createProgressDialog(this, "Please wait!!!\nSending... ");
        progressDialog.show();

        if (hasNewImage) {

//            imageFile = new File(imageFilePath);
//            if (imageFile.exists()) {
            Log.d(TAG, "sendDataToServer: image file exist");
            RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), imageFileToBeUploaded);
            body = MultipartBody.Part.createFormData("photo", imageFileToBeUploaded.getName(), surveyBody);

        } else {
            body = null;
        }


        RequestBody data = RequestBody.create(MediaType.parse("text/plain"), jsonData);
        NetworkApiInterface apiService = getAPIClient().create(NetworkApiInterface.class);
        Call<AskForHelpResponse> call = apiService.getAskForHelpResponse(body, data);


        call.enqueue(new Callback<AskForHelpResponse>() {
            @Override
            public void onResponse(Call<AskForHelpResponse> call, Response<AskForHelpResponse> response) {
                if (response.body() == null) {
                    Toast.makeText(ReportActivityOld.this, "null response", Toast.LENGTH_SHORT).show();
                    if (progressDialog.isShowing() && progressDialog != null) {
                        progressDialog.dismiss();
                    }
                    return;
                }

                handleProfileUpdateResponse(response.body());
                Log.d("", "onResponse: got response data " + response.body().getData().toString());
                Log.d("", "onResponse: got response status" + response.body().getStatus().toString());
            }

            private void handleProfileUpdateResponse(AskForHelpResponse askForHelpResponse) {
                switch (askForHelpResponse.getStatus()) {
                    case REQUEST_OK:
                        if (progressDialog.isShowing() && progressDialog != null) {
                            progressDialog.dismiss();
                        }
                        handleSuccess(askForHelpResponse);
                        Log.d("", "handleReportResponse: 200");
                        break;

                    default:
                        Toast.makeText(ReportActivityOld.this, askForHelpResponse.getData(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }


            private void handleSuccess(AskForHelpResponse askForHelpResponse) {
                DialogFactory.createMessageDialog(ReportActivityOld.this, "Success", "Data sent successfully").show();
//                HomeActivity.start(ReportActivityOld.this);

                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-4:00"));
                Date currentLocalTime = cal.getTime();
                DateFormat date1 = new SimpleDateFormat("KK:mm:ss");
                date1.setTimeZone(TimeZone.getTimeZone("GMT+5:45"));
                String localTime = date1.format(currentLocalTime);

                String messageToSave = "Ward no : "+ward +" \n "+ "Incident type : "+incident_type+ "\n" +detailed_message;


                messageHelperViewModel.insert(new MessageHelper(incident_time, localTime, messageToSave, 0 ));

                tvName.getEditText().setText("");
                tvContactNumber.getEditText().setText("");
                tvMessage.getEditText().setText("");

                startActivity( new Intent(ReportActivityOld.this, HomeActivity.class));
                finish();

            }

            @Override
            public void onFailure(Call<AskForHelpResponse> call, Throwable t) {
                if (progressDialog.isShowing() && progressDialog != null) {
                    progressDialog.dismiss();
                }
                String message = "Error uploading data!, please try again later.\n"+t.getMessage();
                Log.d("", "onFailure: "+t.getMessage().toString());

                if (t instanceof SocketTimeoutException) {
                    message = "Slow internet connection, please try again later";
                }

                DialogFactory.createSimpleOkErrorDialog(ReportActivityOld.this, "Sending Failed!!", message).show();
                Toast.makeText(ReportActivityOld.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @OnClick(R.id.tv_incident_time)
    public void onViewClicked() {
    }

    private void setCurrentDate() {

        Date date = Calendar.getInstance(new Locale("en", "US")).getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String formattedDate = df.format(date);
        tvIncidentTime.getEditText().setText(formattedDate);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_switch_to_english:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    LocaleChanger.setLocale(new Locale("en", "US"));
                    ActivityRecreationHelper.recreate(ReportActivityOld.this, true);
                }
                return true;
            case R.id.action_switch_to_nepali:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    LocaleChanger.setLocale(new Locale("ne", "NP"));
                    ActivityRecreationHelper.recreate(ReportActivityOld.this, true);
                }
                return true;
            case R.id.action_view_message:
                   startActivity(new Intent(ReportActivityOld.this, MessageActivity.class));
//                   finish();
                return true;

            case android.R.id.home:
                startActivity(new Intent(ReportActivityOld.this, HomeActivity.class));
                   finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        newBase = LocaleChanger.configureBaseContext(newBase);
        super.attachBaseContext(newBase);
    }
}
