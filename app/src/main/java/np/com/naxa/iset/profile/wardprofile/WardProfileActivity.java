package np.com.naxa.iset.profile.wardprofile;

import android.content.Intent;
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

public class WardProfileActivity extends AppCompatActivity {

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    String ward = "Profile";
    @BindView(R.id.toolbar_general)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ward_profile);
        ButterKnife.bind(this);


        try {
            Intent intent = getIntent();
            ward = intent.getStringExtra("ward");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        setupToolBar();

        setupViewPager(viewpager);

        tabs.setupWithViewPager(viewpager);
    }

    private void setupToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Ward No " + ward);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new WardProfileFragment(), "Ward Profile");
        adapter.addFragment(new PeopleFragment(), "People");
        adapter.addFragment(new ManagementBoardFragment(), "Management Board");
        adapter.addFragment(new RiskHazardFragment(), "Risk Hazard");
        viewPager.setAdapter(adapter);
    }
}
