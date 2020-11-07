package np.com.naxa.iset.profile.wardprofile;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import np.com.naxa.iset.R;
import np.com.naxa.iset.profile.wardprofile.fragments.WardElectedRepresentativeFragment;
import np.com.naxa.iset.profile.wardprofile.fragments.WardStaffFragment;
import np.com.naxa.iset.profile.wardprofile.fragments.WardRiskProfileFragment;
import np.com.naxa.iset.profile.wardprofile.fragments.ViewPagerAdapter;
import np.com.naxa.iset.profile.wardprofile.fragments.WardMapFragment;

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
        adapter.addFragment(new WardMapFragment(), "Ward Map");
        adapter.addFragment(new WardStaffFragment(), "Ward Staff");
        adapter.addFragment(new WardElectedRepresentativeFragment(), "Elected Representative");
        adapter.addFragment(new WardRiskProfileFragment(), "Risk Profile");
        viewPager.setAdapter(adapter);
    }
}
