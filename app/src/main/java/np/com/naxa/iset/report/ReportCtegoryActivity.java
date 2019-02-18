package np.com.naxa.iset.report;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import np.com.naxa.iset.R;

public class ReportCtegoryActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_general)
    Toolbar toolbarGeneral;
    @BindView(R.id.tvCivicForm)
    Button tvCivicForm;
    @BindView(R.id.tvDisasterForm)
    Button tvDisasterForm;
    @BindView(R.id.tvWardForm)
    Button tvWardForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_ctegory);
        ButterKnife.bind(this);

        setupToolBar();
    }

    private void setupToolBar() {
        setSupportActionBar(toolbarGeneral);
        getSupportActionBar().setTitle("Report");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @OnClick({R.id.tvCivicForm, R.id.tvDisasterForm, R.id.tvWardForm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvCivicForm:
                startActivity(new Intent(ReportCtegoryActivity.this, CivicReportActivity.class));
                break;
            case R.id.tvDisasterForm:
                startActivity(new Intent(ReportCtegoryActivity.this, DisasterReportActivity.class));
                break;
            case R.id.tvWardForm:
                startActivity(new Intent(ReportCtegoryActivity.this, ReportActivity.class));
                break;
        }
    }
}
