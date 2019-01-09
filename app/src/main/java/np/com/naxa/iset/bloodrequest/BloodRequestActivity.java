package np.com.naxa.iset.bloodrequest;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import np.com.naxa.iset.R;

public class BloodRequestActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_request);
        ButterKnife.bind(this);

        setupToolBar();
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
                break;
            case R.id.blood_B_positive:
                break;
            case R.id.blood_O_positive:
                break;
            case R.id.blood_AB_positive:
                break;
            case R.id.blood_A_negative:
                break;
            case R.id.blood_B_negative:
                break;
            case R.id.blood_O_negative:
                break;
            case R.id.blood_AB_negative:
                break;
            case R.id.bloodGroupLayout:
                break;
            case R.id.btnMakeBloodRequest:
                break;
        }
    }
}
