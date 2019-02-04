package np.com.naxa.iset.report.wardstaff;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import np.com.naxa.iset.R;
import np.com.naxa.iset.profile.wardprofile.fragments.ManagementBoardFragment;
import np.com.naxa.iset.profile.wardprofile.fragments.PeopleFragment;
import np.com.naxa.iset.profile.wardprofile.fragments.RiskHazardFragment;
import np.com.naxa.iset.profile.wardprofile.fragments.ViewPagerAdapter;
import np.com.naxa.iset.profile.wardprofile.fragments.WardProfileFragment;

public class UnverifiedReportFormListActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_general)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unverified_report_form_list);
        ButterKnife.bind(this);
        setupToolBar();

        setupViewPager(viewpager);

    }

    private void setupToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Unverified Forms" );
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new UnverifiedNormalFormListFragment(), "Normal");
        adapter.addFragment(new UnverifiedEditedFormListFragment(), "Edited");
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);

    }
}
