package np.com.naxa.iset.newhomepage;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import np.com.naxa.iset.R;
import np.com.naxa.iset.activity.MyCircleProfileActivity;
import np.com.naxa.iset.activity.NotifyOthersActivity;
import np.com.naxa.iset.activity.ReportActivity;
import np.com.naxa.iset.bloodrequest.BloodRequestActivity;
import np.com.naxa.iset.disasterinfo.HazardInfoActivity;
import np.com.naxa.iset.mapboxmap.OpenSpaceMapActivity;
import np.com.naxa.iset.profile.municipalityprofile.MunicipalityProfileActivity;
import np.com.naxa.iset.quiz.QuizHomeActivity;
import np.com.naxa.iset.settings.SettingsActivity;
import np.com.naxa.iset.utils.DialogFactory;
import np.com.naxa.iset.utils.imageutils.CircleTransform;
import np.com.naxa.iset.utils.recycleviewutils.LinearLayoutManagerWithSmoothScroller;
import np.com.naxa.iset.utils.recycleviewutils.RecyclerViewType;

public class SectionGridHomeActivity extends AppCompatActivity {

    protected static final String RECYCLER_VIEW_TYPE = "recycler_view_type";
    @BindView(R.id.btn_disaster_info)
    Button btnDisasterInfo;
    @BindView(R.id.btn_react_quickly)
    Button btnReactQuickly;
    @BindView(R.id.btn_info)
    Button btnDisasterNews;
    @BindView(R.id.toolbar_general)
    Toolbar toolbar;
    @Nullable
    @BindView(R.id.appbar_general)
    AppBarLayout appbar;
    @BindView(R.id.imageView7)
    ImageView imageView;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.btnAskForBlood)
    Button btnAskForBlood;
    @BindView(R.id.btnNotifyOthers)
    Button btnNotifyOthers;

    private RecyclerViewType recyclerViewType;
    private RecyclerView recyclerView;

    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;

    // index to identify current nav menu item
    public static int navItemIndex = 0;

    public static void start(Context context) {
        Intent intent = new Intent(context, SectionGridHomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_grid_home);
        ButterKnife.bind(this);

//        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        navigationView = (NavigationView) findViewById(R.id.nav_view);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);

        // load nav menu header data
        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        recyclerViewType = RecyclerViewType.GRID;
//        setUpToolbarTitle();
        setUpRecyclerView();
        populateRecyclerView();
    }


    //setup recycler view
    private void setUpRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.sectioned_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManagerWithSmoothScroller(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    //populate recycler view
    private void populateRecyclerView() {
        String[] sectionHeader = {"REACT QUICKLY", "DISASTER INFORMATION"};
//        String[] sectionChildTitle = {"FIND OPEN SPACE", "ASK FOR HELP", "Report", "NOTIFY OTHERS", "HAZARD INFO", "DRR QUIZ", "DRR Dictionary", "MAP"};
        String[] sectionChildTitle = {"FIND OPEN SPACE", "Report an incident", "My CIRCLE", "EMERGENCY NUMBERS", "HAZARD INFO", "Terminologies", "QUIZ", "Multimedia",  "Library", "Who Does What"};

        ArrayList<Drawable> gridIcon = new ArrayList<Drawable>();
        gridIcon.add(getResources().getDrawable(R.drawable.ic_open_space_grid));
        gridIcon.add(getResources().getDrawable(R.drawable.ic_ask_for_help_grid));
        gridIcon.add(getResources().getDrawable(R.drawable.ic_your_circle_grid));
        gridIcon.add(getResources().getDrawable(R.drawable.ic_call_purple_24dp));
        gridIcon.add(getResources().getDrawable(R.drawable.ic_hazard_info_grid));
        gridIcon.add(getResources().getDrawable(R.drawable.ic_drr_dictionary_grid));
        gridIcon.add(getResources().getDrawable(R.drawable.ic_quiz_grid));
        gridIcon.add(null);
        gridIcon.add(null);
        gridIcon.add(null);



        ArrayList<SectionModel> sectionModelArrayList = new ArrayList<>();
        //for loop for sections
        int sectionChildTitlePos = 0;
        for (int i = 1; i <= 2; i++) {
            ArrayList<String> itemArrayList = new ArrayList<>();
            ArrayList<Drawable> itemIconArrayList = new ArrayList<>();
            //for loop for items
            if(i ==1){
                for (int j = 1; j <= 4; j++) {
                    itemArrayList.add(sectionChildTitle[sectionChildTitlePos]);
                    itemIconArrayList.add(gridIcon.get(sectionChildTitlePos));
                    sectionChildTitlePos++;
                }
            }
             if(i==2){
            for (int j = 1; j <= 6; j++) {
                itemArrayList.add(sectionChildTitle[sectionChildTitlePos]);
                itemIconArrayList.add(gridIcon.get(sectionChildTitlePos));
                sectionChildTitlePos++;
            }
             }


            //add the section and items to array list
            sectionModelArrayList.add(new SectionModel(sectionHeader[i - 1], itemArrayList, itemIconArrayList));

        }

        SectionRecyclerViewAdapter adapter = new SectionRecyclerViewAdapter(this, recyclerViewType, sectionModelArrayList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.btn_disaster_info, R.id.btn_react_quickly, R.id.btn_info, R.id.btnAskForBlood, R.id.btnNotifyOthers})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_disaster_info:
                recyclerView.smoothScrollToPosition(1);
                break;
            case R.id.btn_react_quickly:
                recyclerView.smoothScrollToPosition(0);
                break;
            case R.id.btn_info:
                startActivity(new Intent(SectionGridHomeActivity.this, MunicipalityProfileActivity.class));
                break;

            case R.id.btnAskForBlood:
                startActivity(new Intent(SectionGridHomeActivity.this, BloodRequestActivity.class));
                break;

            case R.id.btnNotifyOthers:
                startActivity(new Intent(SectionGridHomeActivity.this, NotifyOthersActivity.class));
                break;
        }
    }

    /***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */
    private void loadNavHeader() {
        // name, website
        txtName.setText("Samir Dangal");
        txtWebsite.setText("samirdangal@gmail.com");

        // loading header background image
//        Glide.with(this).load(urlNavHeaderBg)
//                .crossFade()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(imgNavHeaderBg);

        // Loading profile image
        Glide.with(this).load("https://thumbs.dreamstime.com/b/profile-icon-male-avatar-portrait-casual-person-silhouette-face-flat-design-vector-46846328.jpg")
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);

        // showing dot next to notifications label
//        navigationView.getMenu().getItem(3).setActionView(R.layout.menu_dot);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        navItemIndex = 0;
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_profile:
                        navItemIndex = 1;
                        startActivity(new Intent(SectionGridHomeActivity.this, MyCircleProfileActivity.class));
                        break;
                    case R.id.nav_ask_for_help:
                        navItemIndex = 2;
                        startActivity(new Intent(SectionGridHomeActivity.this, ReportActivity.class));

                        break;
                    case R.id.nav_report:
                        navItemIndex = 3;
                        startActivity(new Intent(SectionGridHomeActivity.this, ReportActivity.class));

                        break;
                    case R.id.nav_notify_oithers:
                        navItemIndex = 4;
                        startActivity(new Intent(SectionGridHomeActivity.this, NotifyOthersActivity.class));

                        break;
                    case R.id.nav_hazard_info:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(SectionGridHomeActivity.this, HazardInfoActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_play_quiz:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(SectionGridHomeActivity.this, QuizHomeActivity.class));
                        drawer.closeDrawers();
                        return true;

                    case R.id.nav_settings:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(SectionGridHomeActivity.this, SettingsActivity.class));
                        drawer.closeDrawers();
                        return true;

                    case R.id.nav_map:
                      startActivity(new Intent(SectionGridHomeActivity.this, OpenSpaceMapActivity.class));
                        drawer.closeDrawers();
                        return true;
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

}
