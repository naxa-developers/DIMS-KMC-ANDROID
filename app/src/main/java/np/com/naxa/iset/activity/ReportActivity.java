package np.com.naxa.iset.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import np.com.naxa.iset.R;
import np.com.naxa.iset.utils.CalendarUtils;
import np.com.naxa.iset.utils.CreateAppMainFolderUtils;
import np.com.naxa.iset.utils.ToastUtils;
import np.com.naxa.iset.utils.imageutils.LoadImageUtils;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

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
    Button btnAudioVideo;
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

    private String imageFilePath = null;
    private File imageFileToBeUploaded;
    private Boolean hasNewImage = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);

        setupToolBar();
        setUpSpinner();
    }

    private void setupToolBar() {
        setSupportActionBar(toolbarGeneral);
        getSupportActionBar().setTitle("Report");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setUpSpinner() {
        ArrayAdapter<String> wardAdapter = new ArrayAdapter<String>(ReportActivity.this,
                R.layout.item_spinner, getResources().getStringArray(R.array.ward_no));
        wardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnWardNo.setAdapter(wardAdapter);

        ArrayAdapter<String> hazardAdapter = new ArrayAdapter<String>(ReportActivity.this,
                R.layout.item_spinner, getResources().getStringArray(R.array.hazard_type));
        hazardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnHazardType.setAdapter(hazardAdapter);

        ArrayAdapter<String> riskLevelAdapter = new ArrayAdapter<String>(ReportActivity.this,
                R.layout.item_spinner, getResources().getStringArray(R.array.risk_level));
        riskLevelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnRiskLevel.setAdapter(riskLevelAdapter);

        ArrayAdapter<String> disasterStatusAdapter = new ArrayAdapter<String>(ReportActivity.this,
                R.layout.item_spinner, getResources().getStringArray(R.array.disaster_status));
        disasterStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDisasterStatus.setAdapter(disasterStatusAdapter);


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
                break;

            case R.id.btn_save:
                break;

            case R.id.btn_submit:
                CalendarUtils.getCurrentDate();
                CalendarUtils.getCurrentTime();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
                Toast.makeText(ReportActivity.this, "Image  Error Occurred", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {

                imageFilePath = imageFile.getAbsolutePath();
                CreateAppMainFolderUtils createAppMainFolderUtils = new CreateAppMainFolderUtils(ReportActivity.this, CreateAppMainFolderUtils.appmainFolderName);

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

                                LoadImageUtils.imageSaveFileToSpecificDirectory(getBitmapOfImageFile(imageFileToBeUploaded.getAbsoluteFile().getAbsolutePath()),
                                        "Samir", createAppMainFolderUtils.getAppMediaFolderName());

                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) {
                                throwable.printStackTrace();
//                                showError(throwable.getMessage());
                                ToastUtils.showToast(throwable.getMessage().toString());
                                imageFileToBeUploaded = imageFile;
                                Log.d(TAG, "accept: failed to compress " + imageFileToBeUploaded.getAbsolutePath());
                                LoadImageUtils.imageSaveFileToSpecificDirectory(getBitmapOfImageFile(imageFileToBeUploaded.getAbsolutePath()),
                                        "Samir", createAppMainFolderUtils.getAppMediaFolderName());
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
        ivImagePreview.setImageBitmap(bitmap);
        return bitmap;
    }


    private void convertDataToJson() {

    }
}
