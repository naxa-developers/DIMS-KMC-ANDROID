package np.com.naxa.iset.bloodrequest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

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
import np.com.naxa.iset.event.GmailLoginEvent;
import np.com.naxa.iset.event.MyCircleContactAddEvent;
import np.com.naxa.iset.mycircle.MyCircleContactListAdapter;
import np.com.naxa.iset.utils.DialogFactory;

public class BloodRequestActivity extends AppCompatActivity {

    private static final String TAG = "BloodRequestActivity";
    @BindView(R.id.toolbar_general)
    Toolbar toolbar;
    @BindView(R.id.tvChooseBloodLBL)
    TextView tvChooseBloodLBL;
    @BindView(R.id.imgHeaderLBL)
    LinearLayout imgHeaderLBL;
    @BindView(R.id.blood_A_positive)
    ToggleButton toggleBloodAPositive;
    @BindView(R.id.blood_B_positive)
    ToggleButton toggleBloodBPositive;
    @BindView(R.id.blood_O_positive)
    ToggleButton toggleBloodOPositive;
    @BindView(R.id.blood_AB_positive)
    ToggleButton bloodABPositive;
    @BindView(R.id.blood_A_negative)
    ToggleButton toggleBloodANegative;
    @BindView(R.id.blood_B_negative)
    ToggleButton toggleBloodBNegative;
    @BindView(R.id.blood_O_negative)
    ToggleButton toggleBloodONegative;
    @BindView(R.id.blood_AB_negative)
    ToggleButton toggleBloodABNegative;
    @BindView(R.id.bloodGroupLayout)
    LinearLayout bloodGroupLayout;
    @BindView(R.id.tvMessageDesc)
    EditText tvMessageDesc;
    @BindView(R.id.bloodHelpText)
    LinearLayout bloodHelpText;
    @BindView(R.id.btnMakeBloodRequest)
    Button btnMakeBloodRequest;


    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 1;

    String userPhotoUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_request);
        ButterKnife.bind(this);

        setupToolBar();
        setBloodButtonList();
        setupGmailLogin();
    }


    private void setupToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Blood Request");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }


    @OnClick({R.id.blood_A_positive, R.id.blood_B_positive, R.id.blood_O_positive, R.id.blood_AB_positive, R.id.blood_A_negative, R.id.blood_B_negative, R.id.blood_O_negative, R.id.blood_AB_negative, R.id.bloodGroupLayout, R.id.btnMakeBloodRequest})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.blood_A_positive:
                setButtonChecked(R.id.blood_A_positive);
                break;

            case R.id.blood_B_positive:
                setButtonChecked(R.id.blood_B_positive);
                break;

            case R.id.blood_O_positive:
                setButtonChecked(R.id.blood_O_positive);
                break;

            case R.id.blood_AB_positive:
                setButtonChecked(R.id.blood_AB_positive);
                break;

            case R.id.blood_A_negative:
                setButtonChecked(R.id.blood_A_negative);
                break;

            case R.id.blood_B_negative:
                setButtonChecked(R.id.blood_B_negative);
                break;

            case R.id.blood_O_negative:
                setButtonChecked(R.id.blood_O_negative);
                break;

            case R.id.blood_AB_negative:
                setButtonChecked(R.id.blood_AB_negative);
                break;

            case R.id.bloodGroupLayout:
                break;

            case R.id.btnMakeBloodRequest:
                DialogFactory.createGmailLoginDialog(BloodRequestActivity.this).show();
                break;
        }
    }

    List<Integer> bloodButtonList = new ArrayList<Integer>();
    private void setBloodButtonList(){
        bloodButtonList.add(R.id.blood_A_positive);
        bloodButtonList.add(R.id.blood_B_positive);
        bloodButtonList.add(R.id.blood_O_positive);
        bloodButtonList.add(R.id.blood_AB_positive);
        bloodButtonList.add(R.id.blood_A_negative);
        bloodButtonList.add(R.id.blood_B_negative);
        bloodButtonList.add(R.id.blood_O_negative);
        bloodButtonList.add(R.id.blood_AB_negative);
    }

    private void setButtonChecked(int buttonId){
        for (int i = 0; i< bloodButtonList.size(); i++){
            ToggleButton toggleButton = (ToggleButton) findViewById(bloodButtonList.get(i));
            toggleButton.setChecked(false);
        }
        ToggleButton toggleButton = (ToggleButton) findViewById(buttonId);
        toggleButton.setChecked(true);

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

    // gmail Login Start
    private void setupGmailLogin() {

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

    }
//    gmail login end

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGmailLoginEvent(GmailLoginEvent.loginButtonClick itemClick) {
        signIn();

        Toast.makeText(this, "Gmail account Logging in", Toast.LENGTH_SHORT).show();

    }
}
