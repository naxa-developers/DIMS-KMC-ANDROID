package np.com.naxa.iset.disasterinfo;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.arlib.floatingsearchview.FloatingSearchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import np.com.naxa.iset.R;
import np.com.naxa.iset.disasterinfo.model.DisasterInfoDetailsEntity;
import np.com.naxa.iset.disasterinfo.model.DisasterInfoListResponse;
import np.com.naxa.iset.network.UrlClass;
import np.com.naxa.iset.network.retrofit.NetworkApiClient;
import np.com.naxa.iset.network.retrofit.NetworkApiInterface;
import np.com.naxa.iset.utils.DialogFactory;
import np.com.naxa.iset.database.viewmodel.DisasterInfoDetailsViewModel;

public class HazardInfoActivity extends AppCompatActivity {

    private static final String TAG = "HazardInfoActivity";
    @BindView(R.id.toolbar_general)
    Toolbar toolbar;
    @BindView(R.id.floating_search_hazards)
    FloatingSearchView floatingSearchHazards;
    @BindView(R.id.hazardListrecycler)
    RecyclerView hazardListrecycler;

    NetworkApiInterface apiInterface;
    DisasterInfoDetailsViewModel disasterInfoDetailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hazard_info);
        ButterKnife.bind(this);

        apiInterface = NetworkApiClient.getAPIClient().create(NetworkApiInterface.class);
        disasterInfoDetailsViewModel = ViewModelProviders.of(this).get(DisasterInfoDetailsViewModel.class);
        setupToolBar();
        setupListRecycler();

        getDRRData();
    }

    private void setupToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Hazard Info");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }


    private void setupListRecycler() {
        HazardListAdapter hazardListAdapter = new HazardListAdapter(R.layout.hazard_list_item_row_layout, null);
        hazardListrecycler.setLayoutManager(new LinearLayoutManager(this));
        hazardListrecycler.setAdapter(hazardListAdapter);

        loadDataToList();
    }

    private void getDRRData() {
        Dialog dialog = DialogFactory.createProgressDialog(HazardInfoActivity.this, "Loading...");
        dialog.show();
        apiInterface.getDisasterInfoResponse(UrlClass.API_ACCESS_TOKEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapIterable(new Function<DisasterInfoListResponse, Iterable<DisasterInfoDetailsEntity>>() {
                    @Override
                    public Iterable<DisasterInfoDetailsEntity> apply(DisasterInfoListResponse disasterInfoListResponse) throws Exception {
                        if (disasterInfoListResponse.getError() == 0) {
                            return disasterInfoListResponse.getData();

                        } else {
                            DialogFactory.createCustomErrorDialog(HazardInfoActivity.this, disasterInfoListResponse.getMessage(), new DialogFactory.CustomDialogListener() {
                                @Override
                                public void onClick() {
                                    dialog.dismiss();
                                }
                            }).show();
                            return null;
                        }
                    }
                })
                .map(new Function<DisasterInfoDetailsEntity, DisasterInfoDetailsEntity>() {
                    @Override
                    public DisasterInfoDetailsEntity apply(DisasterInfoDetailsEntity disasterInfoDetailsEntity) throws Exception {
                        return disasterInfoDetailsEntity;
                    }
                })
                .subscribe(new DisposableObserver<DisasterInfoDetailsEntity>() {
                    @Override
                    public void onNext(DisasterInfoDetailsEntity disasterInfoDetailsEntity) {
                        if (disasterInfoDetailsEntity != null) {
                            long id = disasterInfoDetailsViewModel.insert(disasterInfoDetailsEntity);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onComplete() {
                        dialog.dismiss();
                    }
                });
    }

    private void loadDataToList() {

        ArrayList<HazardListModel> hazardListModelArrayList = new ArrayList<HazardListModel>();
//
//        hazardListModelArrayList.add(new HazardListModel("Earthquake"));
//        hazardListModelArrayList.add(new HazardListModel("Landslide"));
//        hazardListModelArrayList.add(new HazardListModel("Flood"));
//        hazardListModelArrayList.add(new HazardListModel("Wild Fire"));
//        hazardListModelArrayList.add(new HazardListModel("Glacial Lake Outburst Flood (GLOFs)"));
//        hazardListModelArrayList.add(new HazardListModel("Drought"));
//        hazardListModelArrayList.add(new HazardListModel("Animal Attack"));
//        hazardListModelArrayList.add(new HazardListModel("Avalanche"));
//        hazardListModelArrayList.add(new HazardListModel("Epidemics"));
//        hazardListModelArrayList.add(new HazardListModel("Road Accident"));
//        hazardListModelArrayList.add(new HazardListModel("Electricity Outrage"));
//        hazardListModelArrayList.add(new HazardListModel("Heavy Rainfall"));
//        hazardListModelArrayList.add(new HazardListModel("Lightening"));
//        hazardListModelArrayList.add(new HazardListModel("Cold Wave"));
//        hazardListModelArrayList.add(new HazardListModel("Heat Waves"));
//        hazardListModelArrayList.add(new HazardListModel("Flash Flood"));
//        hazardListModelArrayList.add(new HazardListModel("Thunder Storm"));
//        hazardListModelArrayList.add(new HazardListModel("Wind Storm"));
//        hazardListModelArrayList.add(new HazardListModel("Hail Stone"));
//        hazardListModelArrayList.add(new HazardListModel("Extreme Temperature"));
//        hazardListModelArrayList.add(new HazardListModel("Snake Bite"));
//        hazardListModelArrayList.add(new HazardListModel("Leak"));
//        hazardListModelArrayList.add(new HazardListModel("Sedimentation"));
//        hazardListModelArrayList.add(new HazardListModel("Biological"));
//        hazardListModelArrayList.add(new HazardListModel("Pollution"));
//        hazardListModelArrayList.add(new HazardListModel("Famine"));
//        hazardListModelArrayList.add(new HazardListModel("Frost"));
//        hazardListModelArrayList.add(new HazardListModel("Panic"));
//        hazardListModelArrayList.add(new HazardListModel("Thunderstorms"));
//        hazardListModelArrayList.add(new HazardListModel("Strong Wind"));
//        hazardListModelArrayList.add(new HazardListModel("Snow Storm"));
//        hazardListModelArrayList.add(new HazardListModel("Plague"));
//        hazardListModelArrayList.add(new HazardListModel("Structure Collapse"));
//        hazardListModelArrayList.add(new HazardListModel("Bridge Collapse"));
//        hazardListModelArrayList.add(new HazardListModel("Air Crash"));
//        hazardListModelArrayList.add(new HazardListModel("Boat Capsize"));
//        hazardListModelArrayList.add(new HazardListModel("High Altitude"));

//        ((HazardListAdapter) hazardListrecycler.getAdapter()).replaceData(hazardListModelArrayList);

        disasterInfoDetailsViewModel.getAllDistinctCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<List<String>>() {
                    @Override
                    public void onNext(List<String> strings) {
//                        Log.d(TAG, "onNext: "+strings.size());
                        ((HazardListAdapter) hazardListrecycler.getAdapter()).replaceData(strings);

                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }
}
