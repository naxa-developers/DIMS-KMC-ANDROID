package np.com.naxa.iset.disasterinfo;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;


import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import np.com.naxa.iset.R;
import np.com.naxa.iset.disasterinfo.imagesliderviewpager.ImageSliderViewPagerAdapter;
import np.com.naxa.iset.disasterinfo.model.DisasterInfoDetailsEntity;
import np.com.naxa.iset.utils.sectionmultiitemUtils.DataServer;
import np.com.naxa.iset.utils.sectionmultiitemUtils.SectionMultipleItem;
import np.com.naxa.iset.viewmodel.DisasterInfoDetailsViewModel;

import static android.text.Html.fromHtml;

public class HazardThingsToDoActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_general)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.btnBeforeHappens)
    Button btnBeforeHappens;
    @BindView(R.id.btnWhenHappens)
    Button btnWhenHappens;
    @BindView(R.id.btnAfterHappens)
    Button btnAfterHappens;

    //    HazardListModel hazardListModel;
    @BindView(R.id.tvThingsToDoDetails)
    TextView tvThingsToDoDetails;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.view_pager)
    ViewPager viewPager;


    private List<SectionMultipleItem> mData;
    private static final String TAG = "HazardThingsToDo";
    String category = "", subcatname = "";

    DisasterInfoDetailsViewModel disasterInfoDetailsViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hazard_things_to_do);
        ButterKnife.bind(this);

        disasterInfoDetailsViewModel = ViewModelProviders.of(this).get(DisasterInfoDetailsViewModel.class);

        Intent intent = getIntent();
        category = intent.getStringExtra("OBJ");
        subcatname = intent.getStringExtra("OBJ1");

        viewPager.setVisibility(View.GONE);

        setupToolBar(category);
        setThingsToDo(subcatname);


//        setupImageSliderViewPager();


        // 1. create entityList which item data extend SectionMultiEntity
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mData = DataServer.getThingsToDoBefore();
//        setupRecyclerView();

    }

    private void setupToolBar(String category) {
        setSupportActionBar(toolbar);
        if (category == null) {
            getSupportActionBar().setTitle("Things To Do");
        } else {
            getSupportActionBar().setTitle(category);
            btnBeforeHappens.setText("Before " + category);

//            if (hazardListModel.getTitle().equals("Earthquake") || hazardListModel.getTitle().equals("Landslide")) {
//            setupSliderLayout();
//            }
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    private void setupImageSliderViewPager() {

         String[] imageUrls = new String[]{
                "https://cdn.pixabay.com/photo/2016/11/11/23/34/cat-1817970_960_720.jpg",
                "https://cdn.pixabay.com/photo/2017/12/21/12/26/glowworm-3031704_960_720.jpg",
                "https://cdn.pixabay.com/photo/2017/12/24/09/09/road-3036620_960_720.jpg",
                "https://cdn.pixabay.com/photo/2017/11/07/00/07/fantasy-2925250_960_720.jpg",
                "https://cdn.pixabay.com/photo/2017/10/10/15/28/butterfly-2837589_960_720.jpg"
        };

//        String[] imageUrls = imageList.toArray(new String[imageList.size()]);

        ImageSliderViewPagerAdapter adapter = new ImageSliderViewPagerAdapter(this, imageUrls);
        viewPager.setAdapter(adapter);


        CirclePageIndicator indicator = (CirclePageIndicator)findViewById(R.id.indicator);

        indicator.setViewPager(viewPager);

        final float density = getResources().getDisplayMetrics().density;

        //Set circle indicator radius
        indicator.setRadius(imageUrls.length * density);


        viewPager.setVisibility(View.VISIBLE);
    }


    HazardListModel hazardListModel1 = new HazardListModel();

    @OnClick({R.id.btnBeforeHappens, R.id.btnWhenHappens, R.id.btnAfterHappens})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnBeforeHappens:
                // 1. create entityList which item data extend SectionMultiEntity
//                mData = DataServer.getThingsToDoBefore();
//                setupRecyclerView();
                setThingsToDo("before");

                break;
            case R.id.btnWhenHappens:
                // 1. create entityList which item data extend SectionMultiEntity
//                mData = DataServer.getThingsToDoWhenHappens();
//                setupRecyclerView();
                setThingsToDo("during");
                break;
            case R.id.btnAfterHappens:
                // 1. create entityList which item data extend SectionMultiEntity
//                mData = DataServer.getThingsToDoAfter();
//                setupRecyclerView();
                setThingsToDo("after");
                break;
        }
    }

    DataServer dataServer = new DataServer();

    private void setThingsToDo(String when) {

//        String todo = "";
        if ( when != null) {
            switch (when) {
                case "before":
                    getSupportActionBar().setTitle("Before "+category);
//                    todo = hazardListModel1.getBefore_incident();
                    break;

                case "during":
                    getSupportActionBar().setTitle("When "+category+ " happens");
                    break;

                case "after":
                    getSupportActionBar().setTitle("After "+category+" passes");
                    break;
            }
        }


        disasterInfoDetailsViewModel.getSpecificDisasterInfo(category, when)
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<DisasterInfoDetailsEntity>() {
                    @Override
                    public void onNext(DisasterInfoDetailsEntity disasterInfoDetailsEntity) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            tvThingsToDoDetails.setText(fromHtml(disasterInfoDetailsEntity.getDesc(), 0, new ImageGetter(), null));
                        } else {
                            tvThingsToDoDetails.setText(fromHtml(disasterInfoDetailsEntity.getDesc()));
                        }

                        setupImageSliderViewPager();

                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

//                        if (imageList != null) {
//                        }
                    }
                });
    }


    List<String> imageList = new ArrayList<String>();

    private class ImageGetter implements Html.ImageGetter {

        public Drawable getDrawable(String source) {
            int id;
            if (!source.equals("")) {
//                id = R.drawable.hughjackman;
                imageList.add(source);
                Log.d(TAG, "getDrawable: " + imageList.size() + " " + source);
            } else {
                return null;
            }
            return null;
        }
    }

//    private void setupRecyclerView() {
//
//        if (hazardListModel.getTitle().equals("Earthquake") || hazardListModel.getTitle().equals("Landslide")) {
//            recyclerView.setVisibility(View.GONE);
//            tvThingsToDoDetails.setVisibility(View.VISIBLE);
//            scrollView.setVisibility(View.VISIBLE);
//
//
//        } else {
//            recyclerView.setVisibility(View.VISIBLE);
//            tvThingsToDoDetails.setVisibility(View.GONE);
//            scrollView.setVisibility(View.GONE);
//        }
//
//        // create adapter which extend BaseSectionMultiItemQuickAdapter provide your headerResId
//        Log.d(TAG, "setupRecyclerView: " + mData.size());
//        SectionMultipleItemAdapter sectionAdapter = new SectionMultipleItemAdapter(R.layout.def_section_head, mData);
//        sectionAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                SectionMultipleItem item = (SectionMultipleItem) adapter.getData().get(position);
//                switch (view.getId()) {
//                    case R.id.card_view:
//                        if (item.getMultiItemSectionModel() != null) {
//                            Toast.makeText(HazardThingsToDoActivity.this, item.getMultiItemSectionModel().getData_key(), Toast.LENGTH_LONG).show();
//                        }
//                        break;
//                    default:
//                        Toast.makeText(HazardThingsToDoActivity.this, "OnItemChildClickListener " + position, Toast.LENGTH_LONG).show();
//                        break;
//
//                }
//            }
//        });
//        recyclerView.setAdapter(sectionAdapter);
//        Log.d(TAG, "setupRecyclerView: setAdapter ");
//    }
}
