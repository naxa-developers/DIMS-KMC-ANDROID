package np.com.naxa.iset.activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import np.com.naxa.iset.R;
import np.com.naxa.iset.event.EmergenctContactCallEvent;
import np.com.naxa.iset.event.MyCircleContactAddEvent;
import np.com.naxa.iset.mycircle.ContactModel;
import np.com.naxa.iset.mycircle.GetContactFromDevice;
import np.com.naxa.iset.newhomepage.SectionGridHomeActivity;
import np.com.naxa.iset.utils.DialogFactory;
import np.com.naxa.iset.utils.SharedPreferenceUtils;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MyCircleProfileActivity extends AppCompatActivity {

    public static final String TAG = "MyCircleProfileActivity";

    @BindView(R.id.toolbar_general)
    Toolbar toolbarGeneral;
    @BindView(R.id.appbar_general)
    AppBarLayout appbarGeneral;
    @BindView(R.id.iv_picture)
    ImageView ivPicture;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_detail)
    TextView tvDetail;
    @BindView(R.id.btn_add_people)
    Button btnRemoveWatch;
    @BindView(R.id.ib_setting)
    ImageButton ibSetting;
    @BindView(R.id.relativeLayoutBeforeCircleAdd)
    RelativeLayout relativeLayoutBeforeCircleAdd;
    @BindView(R.id.recyclerViewMyCircle)
    RecyclerView recyclerViewMyCircle;
    @BindView(R.id.relativeLayoutAfterCircleAdded)
    RelativeLayout relativeLayoutAfterCircleAdded;

    private final int RESULT_CONTACT_PERMISSION = 55;

    private static final String[] READ_STORAGE_AND_CONTACTS =
            {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_CONTACTS};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        setupToolBar();
        initUI();
    }

    private void setupToolBar() {
        setSupportActionBar(toolbarGeneral);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    private void initUI() {
        Glide.with(this)
                .load("https://thumbs.dreamstime.com/b/profile-icon-male-avatar-portrait-casual-person-silhouette-face-flat-design-vector-46846328.jpg")
                .asBitmap()
                .centerCrop()
                .into(new BitmapImageViewTarget(ivPicture) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        ivPicture.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }
    Dialog progressDialog;
    @OnClick({R.id.ib_setting, R.id.btn_add_people})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_setting:
                break;
            case R.id.btn_add_people:

                handleContactPermission();

                break;
        }
    }


    @AfterPermissionGranted(RESULT_CONTACT_PERMISSION)
    private void handleContactPermission() {
        if (hasStorageAndContactsPermissions()) {

            GetContactFromDevice getContactFromDevice = new GetContactFromDevice();

            progressDialog = DialogFactory.createProgressDialog(MyCircleProfileActivity.this, "Please Wait!!!" );
            progressDialog.show();
            getContactFromDevice.getContacts(MyCircleProfileActivity.this, progressDialog);

        } else {
            EasyPermissions.requestPermissions(this, "Provide contact access permission to add contacts.",
                    RESULT_CONTACT_PERMISSION, READ_STORAGE_AND_CONTACTS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private boolean hasStorageAndContactsPermissions() {
        return EasyPermissions.hasPermissions(this, READ_STORAGE_AND_CONTACTS);
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRVItemClick(MyCircleContactAddEvent.MyCircleContactAddClick itemClick) {
        String name = itemClick.getContactModel().getName();
        Toast.makeText(this, "add to your circle button clicked "+name, Toast.LENGTH_SHORT).show();

    }

}
