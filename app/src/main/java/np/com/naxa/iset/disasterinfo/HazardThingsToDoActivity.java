package np.com.naxa.iset.disasterinfo;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import np.com.naxa.iset.R;
import np.com.naxa.iset.disasterinfo.model.DisasterInfoDetailsEntity;
import np.com.naxa.iset.utils.sectionmultiitemUtils.DataServer;
import np.com.naxa.iset.utils.sectionmultiitemUtils.SectionMultipleItem;
import np.com.naxa.iset.utils.sectionmultiitemUtils.SectionMultipleItemAdapter;
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


    private List<SectionMultipleItem> mData;
    private static final String TAG = "HazardThingsToDo";
    String category = "";

    DisasterInfoDetailsViewModel disasterInfoDetailsViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hazard_things_to_do);
        ButterKnife.bind(this);

        disasterInfoDetailsViewModel = ViewModelProviders.of(this).get(DisasterInfoDetailsViewModel.class);

        Intent intent = getIntent();
        category = intent.getStringExtra("OBJ");

        setupToolBar();

        // 1. create entityList which item data extend SectionMultiEntity
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mData = DataServer.getThingsToDoBefore();
//        setupRecyclerView();

    }

    private void setupToolBar() {
        setSupportActionBar(toolbar);
        if (category == null) {
            getSupportActionBar().setTitle("Things To Do");
        } else {
            getSupportActionBar().setTitle(category);
            btnBeforeHappens.setText("Before " +category);

//            if (hazardListModel.getTitle().equals("Earthquake") || hazardListModel.getTitle().equals("Landslide")) {
                setThingsToDo(category);
//            }
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    HazardListModel hazardListModel1 = new HazardListModel();

    @OnClick({R.id.btnBeforeHappens, R.id.btnWhenHappens, R.id.btnAfterHappens})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnBeforeHappens:
                // 1. create entityList which item data extend SectionMultiEntity
                mData = DataServer.getThingsToDoBefore();
//                setupRecyclerView();
                setThingsToDo("before");

                break;
            case R.id.btnWhenHappens:
                // 1. create entityList which item data extend SectionMultiEntity
                mData = DataServer.getThingsToDoWhenHappens();
//                setupRecyclerView();
                setThingsToDo("during");
                break;
            case R.id.btnAfterHappens:
                // 1. create entityList which item data extend SectionMultiEntity
                mData = DataServer.getThingsToDoAfter();
//                setupRecyclerView();
                setThingsToDo("after");
                break;
        }
    }

    DataServer dataServer = new DataServer();
    private void setThingsToDo(String when) {

//        String todo = "";
//        if (category != null && when != null) {
//            switch (when) {
//                case "before":
//                    todo = hazardListModel1.getBefore_incident();
//                    break;
//
//                case "during":
//                    todo = hazardListModel1.getDuring_incident();
//                    break;
//
//                case "after":
//                    todo = hazardListModel1.after_incident;
//                    break;
//            }
//        }


        disasterInfoDetailsViewModel.getSpecificDisasterInfo(category, when)
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<DisasterInfoDetailsEntity>() {
                    @Override
                    public void onNext(DisasterInfoDetailsEntity disasterInfoDetailsEntity) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            tvThingsToDoDetails.setText(fromHtml(disasterInfoDetailsEntity.getDesc(),0 ,new ImageGetter(), null));
                        } else {
                            tvThingsToDoDetails.setText(fromHtml(disasterInfoDetailsEntity.getDesc()));
                        }
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            tvThingsToDoDetails.setText(Html.fromHtml(todo, Html.FROM_HTML_MODE_COMPACT));
//        } else {
//            tvThingsToDoDetails.setText(Html.fromHtml(todo));
//        }
//


    }


    List<String> imageList = new ArrayList<String>();
    private class ImageGetter implements Html.ImageGetter {

        public Drawable getDrawable(String source) {
            int id;
            if (!source.equals("")) {
//                id = R.drawable.hughjackman;
                imageList.add(source);
                Log.d(TAG, "getDrawable: "+imageList.size()+" "+source);
            }
            else {
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
