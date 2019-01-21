package np.com.naxa.iset.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import np.com.naxa.iset.R;
import np.com.naxa.iset.event.MyCircleContactAddEvent;
import np.com.naxa.iset.mycircle.ContactModel;
import np.com.naxa.iset.mycircle.GetContactFromDevice;
import np.com.naxa.iset.mycircle.MyCircleContactListAdapter;
import np.com.naxa.iset.utils.DialogFactory;
import np.com.naxa.iset.utils.FieldValidatorUtils;
import np.com.naxa.iset.utils.HidekeyboardUtils;
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
    @BindView(R.id.gmail_sign_in_button)
    SignInButton gmailSignInButton;
    @BindView(R.id.iv_MyCircle)
    ImageView ivMyCircle;
    @BindView(R.id.profileLayout)
    RelativeLayout profileLayout;
    @BindView(R.id.et_reg_full_name)
    EditText etRegFullName;
    @BindView(R.id.et_reg_email)
    EditText etRegEmail;
    @BindView(R.id.et_reg_mobile_no)
    EditText etRegMobileNo;
    @BindView(R.id.spn_blood_group)
    Spinner spnBloodGroup;
    @BindView(R.id.et_reg_address)
    EditText etRegAddress;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.regestrationLayout)
    LinearLayout regestrationLayout;
    @BindView(R.id.viewSwitcher)
    ViewSwitcher viewSwitcher;


    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 1;

    String userPhotoUri = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        setupToolBar();
        setupListRecycler();

        setUpSpinner();

        setupGmailLogin();
    }

    private void setupToolBar() {
        setSupportActionBar(toolbarGeneral);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    private void initUI() {

        if (userPhotoUri != null) {
            Glide.with(this)
                    .load(userPhotoUri)
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

        tvAddress.setText(etRegAddress.getText());
        tvNumber.setText(etRegMobileNo.getText());

    }


    private void setupListRecycler() {
        MyCircleContactListAdapter myCircleContactListAdapter = new MyCircleContactListAdapter(R.layout.people_in_my_circle_item_row_layout, null);
        recyclerViewMyCircle.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMyCircle.setAdapter(myCircleContactListAdapter);

    }

    private void setUpSpinner() {
        ArrayAdapter<String> bloodAdapter = new ArrayAdapter<String>(MyCircleProfileActivity.this,
                R.layout.item_spinner, getResources().getStringArray(R.array.array_blood_group));
        bloodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnBloodGroup.setAdapter(bloodAdapter);
    }


    Dialog progressDialog;

    @OnClick({R.id.ib_setting, R.id.btn_add_people, R.id.gmail_sign_in_button, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_setting:
                break;

            case R.id.btn_add_people:
                handleContactPermission();
                break;

            case R.id.gmail_sign_in_button:
                signIn();
                break;

            case R.id.btn_register:
                HidekeyboardUtils.hideKeyboard(MyCircleProfileActivity.this);

                if(FieldValidatorUtils.validateEditText(etRegFullName) &&
                FieldValidatorUtils.validateEditText(etRegAddress) &&
                FieldValidatorUtils.validateMobileNoEditText(etRegMobileNo) &&
                FieldValidatorUtils.validateEmailPattern(etRegEmail) &&
                FieldValidatorUtils.validateSpinnerItemIsselected(spnBloodGroup , "Please select your blood group.")){

                    if (viewSwitcher.getCurrentView() != regestrationLayout){

                        viewSwitcher.showPrevious();
                    } else if (viewSwitcher.getCurrentView() != profileLayout){

                        viewSwitcher.showNext();
                    }
                    initUI();
                }


                break;
        }
    }


    // gmail Login Start
    private void setupGmailLogin() {

        gmailSignInButton.setSize(SignInButton.SIZE_STANDARD);
        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(@NonNull Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount account) {
        if (account == null) {
            return;
        }
        gmailSignInButton.setVisibility(View.GONE);
        Toast.makeText(this, "Google Sign-in complete", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "updateUI: getDisplayName " + account.getDisplayName());
        Log.d(TAG, "updateUI: getEmail " + account.getEmail());
        Log.d(TAG, "updateUI: getIdToken " + account.getIdToken());
        Log.d(TAG, "updateUI: getIdToken " + account.getIdToken());
        Log.d(TAG, "updateUI: getPhotoUrl " + account.getPhotoUrl());
        Log.d(TAG, "updateUI: getId :" + account.getId());
        Log.d(TAG, "updateUI: getGivenName " + account.getGivenName());
        Log.d(TAG, "updateUI: getFamilyName " + account.getFamilyName());

        userPhotoUri = account.getPhotoUrl().toString();

        Glide.with(this)
                .load(account.getPhotoUrl())
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

        tvName.setText(account.getDisplayName());

        etRegEmail.setText(account.getEmail());
        etRegFullName.setText(account.getDisplayName());
    }

//    gmail login end


    @AfterPermissionGranted(RESULT_CONTACT_PERMISSION)
    private void handleContactPermission() {
        if (hasStorageAndContactsPermissions()) {

            GetContactFromDevice getContactFromDevice = new GetContactFromDevice();

            progressDialog = DialogFactory.createProgressDialog(MyCircleProfileActivity.this, "Please Wait!!!");
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
        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    List<ContactModel> contactModelList = new ArrayList<ContactModel>();

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRVItemClick(MyCircleContactAddEvent.MyCircleContactAddClick itemClick) {
        String name = itemClick.getContactModel().getName();

        if (contactModelList.size() == 0) {
            contactModelList.add(itemClick.getContactModel());
        } else if (contactModelList.size() > 0) {
            boolean alreadyExist = false;
            int itemPosition = 0;
            for (int i = 0; i < contactModelList.size(); i++) {
                itemPosition = i;
                if (contactModelList.get(i).getMobileNumber().equals(itemClick.getContactModel().getMobileNumber())) {
                    alreadyExist = true;
                    break;

                } else {
                    alreadyExist = false;
                }
            }

            if (alreadyExist) {
                if (!itemClick.isAddToCircle()) {
                    contactModelList.remove(itemPosition);
                    Log.d(TAG, "onRVItemClick: Contact Removed");

                }
            }
            if (!alreadyExist) {
                if (itemClick.isAddToCircle()) {
                    contactModelList.add(itemClick.getContactModel());
                    Log.d(TAG, "onRVItemClick: Contact Added");
                }
            }

        }

//        Toast.makeText(this, "add to your circle button clicked "+name+ " --> "+itemClick.isAddToCircle(), Toast.LENGTH_SHORT).show();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDialogCloseClick(MyCircleContactAddEvent.MyCircleContactDialogCloseClick itemClick) {

        if (contactModelList != null) {
            ((MyCircleContactListAdapter) recyclerViewMyCircle.getAdapter()).replaceData(contactModelList);
        }

        Toast.makeText(this, "add to your circle dialog close clicked ", Toast.LENGTH_SHORT).show();

    }

}
