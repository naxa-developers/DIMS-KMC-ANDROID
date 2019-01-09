package np.com.naxa.iset.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Switch;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import np.com.naxa.iset.R;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static np.com.naxa.iset.R.drawable.btn_circular_active;
import static np.com.naxa.iset.R.drawable.btn_circular_passive;

public class NotifyOthersActivity extends AppCompatActivity {


    private final int RESULT_CAMERA = 150;
    @BindView(R.id.toolbar_general)
    Toolbar toolbarGeneral;
    @BindView(R.id.appbar_general)
    AppBarLayout appbarGeneral;
    @BindView(R.id.iv_alert)
    ImageButton ivAlert;
    @BindView(R.id.iv_torch_light)
    ImageButton ivStrobeLight;
    @BindView(R.id.sb_strobe_strength)
    SeekBar sbStrobeStrength;
    @BindView(R.id.switch_strobe)
    Switch switchStrobe;

    private MutableLiveData<Boolean> torchButtonStatus;
    private MutableLiveData<Boolean> strobeButtonStatus;
    private int strobeValue;
    private boolean lightStatus;

    public static void start(Context context) {
        Intent intent = new Intent(context, NotifyOthersActivity.class);
        context.startActivity(intent);
    }



    static Camera camera = null;

    @Override
    protected void onStop() {
        super.onStop();

        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
        ButterKnife.bind(this);

        initValues();
        setupToolBar();

        requestPermissionForTorch();

        sbStrobeStrength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                strobeValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        switchStrobe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                strobeButtonStatus.setValue(isChecked);
            }
        });

    }

    private void setupToolBar() {
        setSupportActionBar(toolbarGeneral);
        getSupportActionBar().setTitle("Notify Others");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void initValues() {
        torchButtonStatus = new MutableLiveData<>();
        strobeButtonStatus = new MutableLiveData<>();
        torchButtonStatus.setValue(false);
        strobeButtonStatus.setValue(false);
        lightStatus = false;
        strobeValue = 0;
    }

    @OnClick({R.id.iv_alert, R.id.iv_torch_light})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_alert:
                setWhistleTest();
                break;

            case R.id.iv_torch_light:
                if (torchButtonStatus.getValue()) {
                    torchButtonStatus.setValue(false);
                } else {
                    torchButtonStatus.setValue(true);
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(RESULT_CAMERA)
    private void requestPermissionForTorch() {
        String camera = Manifest.permission.CAMERA;
        if (EasyPermissions.hasPermissions(this, camera)) {
            torchButtonStatus.observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(@Nullable Boolean aBoolean) {
                    if (aBoolean) {
                        ivStrobeLight.setImageDrawable(getDrawable(btn_circular_active));
                        if (strobeButtonStatus.getValue()) {
                            setStrobeLight();
                        } else {
                            setTorchLightState(true);
                        }
                    } else {
                        ivStrobeLight.setImageDrawable(getDrawable(btn_circular_passive));
                        setTorchLightState(false);
                    }
                }
            });

            strobeButtonStatus.observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(@Nullable Boolean aBoolean) {
                    if (aBoolean) {
                        sbStrobeStrength.setEnabled(true);
                        if (torchButtonStatus.getValue()) {
                            setTorchLightState(false);
                            setStrobeLight();
                        }
                    } else {
                        sbStrobeStrength.setEnabled(false);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (torchButtonStatus.getValue()) {
                                    setTorchLightState(true);
                                }
                            }
                        }, 100);

                    }
                }
            });

        } else {
            EasyPermissions.requestPermissions(this, "Provide Camera Permission",
                    RESULT_CAMERA, camera);
        }
    }


    @SuppressLint("NewApi")
    private void toggleFlashLightNew(boolean value) {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, value);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void toggleFlashLightOld(boolean value) {
        if (camera != null) {
            camera.release();
            camera = null;
        }
        camera = Camera.open();
        Camera.Parameters parameters = camera.getParameters();
        if (value) {
            //Check Whether device supports AutoFlash, If you YES then set AutoFlash
            List<String> flashModes = parameters.getSupportedFlashModes();
            if (flashModes.contains(Camera.Parameters.FLASH_MODE_TORCH))
            {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            }
            camera.setParameters(parameters);
            camera.startPreview();
        } else {
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(parameters);
            camera.stopPreview();
            camera.release();
        }
    }

    private void setStrobeLight() {

        Boolean stopStrobe = !torchButtonStatus.getValue() || !strobeButtonStatus.getValue();

        if (stopStrobe) return;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (lightStatus) {
                    lightStatus = false;
                    setTorchLightState(false);
                } else {
                    lightStatus = true;
                    setTorchLightState(true);
                }
                setStrobeLight();
            }
        }, (100 - strobeValue) * 10);
    }

    private void setTorchLightState(boolean value) {
        boolean higherSdkVerison = Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1;
        if (higherSdkVerison) {
            toggleFlashLightNew(value);
        } else {
            toggleFlashLightOld(value);
        }
    }

    private void setWhistleTest() {

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.whistle);

        if (mp.isPlaying()) {
            mp.stop();
        } else {
            mp.start();
        }

    }
}
