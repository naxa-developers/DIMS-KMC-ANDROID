package np.com.naxa.iset.inventory;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import np.com.naxa.iset.R;
import np.com.naxa.iset.database.viewmodel.InventoryListDetailsViewModel;
import np.com.naxa.iset.inventory.model.InventoryListDetails;
import np.com.naxa.iset.inventory.model.InventoryListResponse;
import np.com.naxa.iset.network.UrlClass;
import np.com.naxa.iset.network.retrofit.NetworkApiClient;
import np.com.naxa.iset.network.retrofit.NetworkApiInterface;
import np.com.naxa.iset.utils.DialogFactory;
import np.com.naxa.iset.utils.NetworkUtils;

public class InventoryActivity extends AppCompatActivity {

    NetworkApiInterface apiInterface;
    InventoryListDetailsViewModel inventoryListDetailsViewModel;
    @BindView(R.id.toolbar_general)
    Toolbar toolbarGeneral;
    @BindView(R.id.recyclerViewInventoryList)
    RecyclerView recyclerViewInventoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        ButterKnife.bind(this);

        apiInterface = NetworkApiClient.getAPIClient().create(NetworkApiInterface.class);
        inventoryListDetailsViewModel = ViewModelProviders.of(this).get(InventoryListDetailsViewModel.class);

        setupToolBar();
        setupListRecycler();

        if (NetworkUtils.isNetworkAvailable()) {
            fetchDataFromServer();
        }else {
            getDataFromDatabase();
        }

    }

    private void setupToolBar() {
        setSupportActionBar(toolbarGeneral);
        getSupportActionBar().setTitle("Emergency Materials");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }


    private void setupListRecycler() {
        InventoryListAdapter inventoryListAdapter = new InventoryListAdapter(R.layout.inventory_list_item_row_layout, null);
        recyclerViewInventoryList.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewInventoryList.setAdapter(inventoryListAdapter);

    }


    private void fetchDataFromServer() {

        apiInterface.getInventoryListResponse(UrlClass.API_ACCESS_TOKEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<InventoryListResponse>() {
                    @Override
                    public void onNext(InventoryListResponse inventoryListResponse) {

                        if (inventoryListResponse.getError() == 0) {
                            if (inventoryListResponse.getData() != null) {
                                inventoryListDetailsViewModel.insertAll(inventoryListResponse.getData());
                                ((InventoryListAdapter) recyclerViewInventoryList.getAdapter()).replaceData(inventoryListResponse.getData());

                            }
                        }

                        if (inventoryListResponse.getError() == 1) {
                            DialogFactory.createCustomErrorDialog(InventoryActivity.this, inventoryListResponse.getMessage(), new DialogFactory.CustomDialogListener() {
                                @Override
                                public void onClick() {

                                }
                            }).show();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        DialogFactory.createCustomErrorDialog(InventoryActivity.this, e.getMessage(), new DialogFactory.CustomDialogListener() {
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

    private void getDataFromDatabase(){
        inventoryListDetailsViewModel.getAllInventoryListDetailsList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<List<InventoryListDetails>>() {
                    @Override
                    public void onNext(List<InventoryListDetails> inventoryListDetails) {
                        ((InventoryListAdapter) recyclerViewInventoryList.getAdapter()).replaceData(inventoryListDetails);

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
