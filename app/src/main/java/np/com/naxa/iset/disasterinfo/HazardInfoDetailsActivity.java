package np.com.naxa.iset.disasterinfo;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import np.com.naxa.iset.R;
import np.com.naxa.iset.quiz.QuizTestActivity;
import np.com.naxa.iset.utils.sectionmultiitemUtils.DataServer;

public class HazardInfoDetailsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_general)
    Toolbar toolbar;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.tvBody)
    TextView tvBody;
    @BindView(R.id.btnPlayQuiz)
    Button btnPlayQuiz;
    @BindView(R.id.btnBeforeHappens)
    Button btnBeforeHappens;
    @BindView(R.id.btnWhenHappens)
    Button btnWhenHappens;
    @BindView(R.id.btnAfterHappens)
    Button btnAfterHappens;

    HazardListModel hazardListModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hazard_info_details);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        hazardListModel = intent.getParcelableExtra("OBJ");

        setupToolBar();
        initUI(hazardListModel);
    }

    private void setupToolBar() {
        setSupportActionBar(toolbar);
        if(hazardListModel == null){
        getSupportActionBar().setTitle("Hazard Details");
        }else {
            getSupportActionBar().setTitle(hazardListModel.getTitle());
            btnBeforeHappens.setText("Before "+hazardListModel.getTitle());

            tvTitle.setText(hazardListModel.getTitle());
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    DataServer dataServer = new DataServer();
    HazardListModel hazardListModel1 = new HazardListModel();
    private void initUI(HazardListModel hazardListModel){
        if(hazardListModel.getTitle().equals("Earthquake") || hazardListModel.getTitle().equals("Landslide")){
            if(hazardListModel.getTitle().equals("Earthquake")) {
                hazardListModel1 = dataServer.getEarthquakeDetails();
            }else {
                hazardListModel1 = dataServer.getLandslideDetails();

            }

            WindowManager mWinMgr = (WindowManager)HazardInfoDetailsActivity.this.getSystemService(Context.WINDOW_SERVICE);
            int displayWidth = mWinMgr.getDefaultDisplay().getWidth();
            Glide.with(HazardInfoDetailsActivity.this)
                    .load(hazardListModel1.getImage())
                    .override(displayWidth, 200)
                    .into(imageView);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tvBody.setText(Html.fromHtml(hazardListModel1.getDesc(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                tvBody.setText(Html.fromHtml(hazardListModel1.getDesc()));
            }
        }

//        if(hazardListModel.getTitle().equals("Landslide")){
//            this.hazardListModel = dataServer.getEarthquakeDetails();
//        }

    }


    @OnClick({R.id.btnPlayQuiz, R.id.btnBeforeHappens, R.id.btnWhenHappens, R.id.btnAfterHappens})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnPlayQuiz:
                startActivity(new Intent(HazardInfoDetailsActivity.this, QuizTestActivity.class));
                break;
            case R.id.btnBeforeHappens:
                startNewActivity();
                break;
            case R.id.btnWhenHappens:
                startNewActivity();
                break;
            case R.id.btnAfterHappens:
                startNewActivity();
                break;
        }
    }

    public void startNewActivity(){
        Intent intent = new Intent(HazardInfoDetailsActivity.this, HazardThingsToDoActivity.class);
        intent.putExtra("OBJ", hazardListModel);
        startActivity(intent);
    }
}
