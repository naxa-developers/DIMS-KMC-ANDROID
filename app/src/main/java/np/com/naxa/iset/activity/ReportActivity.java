package np.com.naxa.iset.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import np.com.naxa.iset.utils.ToastUtils;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class ReportActivity extends AppCompatActivity {
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
    @BindView(R.id.btn_audio_video)
    Button btnAudioVideo;
    @BindView(R.id.et_message)
    EditText etMessage;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

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
    }

    @OnClick({R.id.btn_photo, R.id.btn_audio_video, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_photo:
                EasyImage.openChooserWithDocuments(ReportActivity.this, "Take Image", 0);
                break;
            case R.id.btn_audio_video:

                break;
            case R.id.btn_submit:

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

                int file_size = Integer.parseInt(String.valueOf(imageFile.length() / 1024));


                new Compressor(ReportActivity.this)
                        .compressToFileAsFlowable(imageFile)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<File>() {
                            @Override
                            public void accept(File file) {
                                imageFileToBeUploaded = file;
                                int file_size = Integer.parseInt(String.valueOf(imageFileToBeUploaded.length() / 1024));

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

                hasNewImage = true;

            }

        });
    }
}
