package np.com.naxa.iset;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import np.com.naxa.iset.utils.sectionmultiitemUtils.DataServer;

public class TerminologiesActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_general)
    Toolbar toolbarGeneral;
    @BindView(R.id.tvTerminologies)
    TextView tvTerminologies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminologies);
        ButterKnife.bind(this);


        setupToolBar();

        setTvTerminologies();

    }

    private void setupToolBar() {
        setSupportActionBar(toolbarGeneral);

            getSupportActionBar().setTitle("Terminologies");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    private void setTvTerminologies (){
        DataServer dataServer = new DataServer();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvTerminologies.setText(Html.fromHtml(dataServer.getTerminologies(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            tvTerminologies.setText(Html.fromHtml(dataServer.getTerminologies()));
        }
    }
}
