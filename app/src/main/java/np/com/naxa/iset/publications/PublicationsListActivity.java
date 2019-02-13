package np.com.naxa.iset.publications;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import np.com.naxa.iset.R;
import np.com.naxa.iset.database.viewmodel.PublicationsListDetailsViewModel;
import np.com.naxa.iset.event.PublicationListItemEvent;
import np.com.naxa.iset.network.UrlClass;
import np.com.naxa.iset.network.retrofit.NetworkApiClient;
import np.com.naxa.iset.network.retrofit.NetworkApiInterface;
import np.com.naxa.iset.publications.adapter.PublicationsListItemAdapter;
import np.com.naxa.iset.publications.entity.PublicationsListDetails;
import np.com.naxa.iset.publications.entity.PublicationsListResponse;
import np.com.naxa.iset.publications.youtubeplayer.YoutubePlayerActivity;
import np.com.naxa.iset.publications.youtubeplayer.helper.YoutubeConstants;
import np.com.naxa.iset.utils.DialogFactory;
import np.com.naxa.iset.utils.NetworkUtils;
import np.com.naxa.iset.utils.ToastUtils;

public class PublicationsListActivity extends AppCompatActivity {

    private static final String TAG = "PublicationsList";
    @BindView(R.id.toolbar_general)
    Toolbar toolbar;
    @BindView(R.id.recyclerViewPublicationList)
    RecyclerView recyclerViewPublicationList;

    NetworkApiInterface apiInterface;
    PublicationsListDetailsViewModel publicationsListDetailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publications_list);
        ButterKnife.bind(this);


        setupToolBar();
        setupListRecycler();


        apiInterface = NetworkApiClient.getAPIClient().create(NetworkApiInterface.class);
        publicationsListDetailsViewModel = ViewModelProviders.of(this).get(PublicationsListDetailsViewModel.class);

        if (NetworkUtils.isNetworkAvailable()) {
            fetchPublicationsDetails();
        } else {
            getDataFromDatabase();
        }

    }

    private void setupToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Multimedia Files");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    private void setupListRecycler() {
        PublicationsListItemAdapter publicationsListItemAdapter = new PublicationsListItemAdapter(R.layout.publications_list_item_row_layout, null);
        recyclerViewPublicationList.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewPublicationList.setAdapter(publicationsListItemAdapter);

    }

    private void fetchPublicationsDetails() {

        apiInterface.getPublicationsListDetailsResponse(UrlClass.API_ACCESS_TOKEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<PublicationsListResponse>() {
                    @Override
                    public void onNext(PublicationsListResponse publicationsListResponse) {
                        if (publicationsListResponse.getError() == 1) {
                            DialogFactory.createCustomErrorDialog(PublicationsListActivity.this, publicationsListResponse.getMessage(), new DialogFactory.CustomDialogListener() {
                                @Override
                                public void onClick() {

                                }
                            }).show();
                        }
                        if (publicationsListResponse.getError() == 0) {
                            if (publicationsListResponse.getData() == null) {
                                DialogFactory.createCustomErrorDialog(PublicationsListActivity.this, "No new data found", new DialogFactory.CustomDialogListener() {
                                    @Override
                                    public void onClick() {

                                    }
                                }).show();
                            } else {
                                savePublicationSetails(publicationsListResponse.getData());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        DialogFactory.createCustomErrorDialog(PublicationsListActivity.this, e.getMessage(), new DialogFactory.CustomDialogListener() {
                            @Override
                            public void onClick() {

                            }
                        }).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }


    private void savePublicationSetails(@NonNull List<PublicationsListDetails> publicationsListDetailsList) {

        Log.d(TAG, "savePublicationSetails: " + publicationsListDetailsList.size());

        Observable.just(publicationsListDetailsList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapIterable(new Function<List<PublicationsListDetails>, List<PublicationsListDetails>>() {
                    @Override
                    public List<PublicationsListDetails> apply(List<PublicationsListDetails> publicationsListDetails) throws Exception {
                        return publicationsListDetails;
                    }
                })
                .map(new Function<PublicationsListDetails, PublicationsListDetails>() {
                    @Override
                    public PublicationsListDetails apply(PublicationsListDetails publicationsListDetails) throws Exception {
                        return publicationsListDetails;
                    }
                })
                .subscribe(new DisposableObserver<PublicationsListDetails>() {
                    @Override
                    public void onNext(PublicationsListDetails publicationsListDetails) {

                        Log.d(TAG, "onNext: " + publicationsListDetails.getName());
                        publicationsListDetailsViewModel.insert(publicationsListDetails);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                        getDataFromDatabase();
                    }
                });

    }

    private void getDataFromDatabase() {
        publicationsListDetailsViewModel.getAllReportDetailsList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<List<PublicationsListDetails>>() {
                    @Override
                    public void onNext(List<PublicationsListDetails> publicationsListDetails) {
                        if (publicationsListDetails == null) {
                            ToastUtils.showShortToast("No data found");
                        } else {
                            Log.d(TAG, "onNext: " + publicationsListDetails.size());
                            Log.d(TAG, "onNext: " + publicationsListDetails.get(0).toString());

                            ((PublicationsListItemAdapter) recyclerViewPublicationList.getAdapter()).replaceData(publicationsListDetails);


                        }
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRVItemClick(PublicationListItemEvent.PublicationListitemClick itemClick) {
        String name = itemClick.getPublicationsListDetails().getTitle();

        String type = itemClick.getPublicationsListDetails().getType();

        switch (type) {
            case PublicationListItemEvent.KEY_IMAGE:

                break;

            case PublicationListItemEvent.KEY_VIDEO:

                String temp = itemClick.getPublicationsListDetails().getVideolink().replaceAll("https://www.youtube.com/embed/", "");

                Intent intent = new Intent(PublicationsListActivity.this, YoutubePlayerActivity.class);
                intent.putExtra(YoutubeConstants.VIDEO_KEY, temp);
                startActivity(intent);
                break;

            case PublicationListItemEvent.KEY_FILES:
                break;
        }

        ToastUtils.showShortToast(name);
    }
}
