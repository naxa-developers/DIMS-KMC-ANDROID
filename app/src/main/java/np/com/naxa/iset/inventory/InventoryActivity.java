package np.com.naxa.iset.inventory;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

    private static final String TAG = "InventoryActivity";
    NetworkApiInterface apiInterface;
    InventoryListDetailsViewModel inventoryListDetailsViewModel;
    @BindView(R.id.toolbar_general)
    Toolbar toolbarGeneral;
    @BindView(R.id.recyclerViewInventoryList)
    RecyclerView recyclerViewInventoryList;

    List<String> categoryName = new ArrayList<String>();
    List<String> subCategoryName = new ArrayList<String>();
    @BindView(R.id.fab_filter)
    FloatingActionButton fabFilter;

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
        } else {
            getAllDataFromDatabase();
            getDistinctCategorySubCategoryList();
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

    private void getAllDataFromDatabase() {
        inventoryListDetailsViewModel.getAllInventoryListDetailsList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<List<InventoryListDetails>>() {
                    @Override
                    public void onNext(List<InventoryListDetails> inventoryListDetails) {
                        ((InventoryListAdapter) recyclerViewInventoryList.getAdapter()).replaceData(inventoryListDetails);
                        Log.d(TAG, "getDistinctCategoryCategoryList: " + inventoryListDetails.size());


                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void getDistinctCategorySubCategoryList() {
        inventoryListDetailsViewModel.getDistinctCategoryList()
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<List<String>>() {
                    @Override
                    public void onNext(List<String> strings) {
                        categoryName.addAll(strings);
                        Log.d(TAG, "getDistinctCategoryCategoryList: " + categoryName.size());

                        inventoryListDetailsViewModel.getDistinctSubCategoryist()
                                .observeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new DisposableSubscriber<List<String>>() {
                                    @Override
                                    public void onNext(List<String> strings) {
                                        subCategoryName.addAll(strings);
                                        Log.d(TAG, "getDistinctCategorySubCategoryList: " + subCategoryName.size());
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
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    @OnClick(R.id.fab_filter)
    public void onViewClicked() {
//        public  Dialog createCustomDialog(@NonNull Context context, @NonNull String message, @NonNull CustomDialogListener listener) {
        final Dialog dialog = new Dialog(InventoryActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.inventory_filter_dialog_layout);

//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        lp.copyFrom(dialog.getWindow().getAttributes());
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        TextView text = (TextView) dialog.findViewById(R.id.tv_message);

        Button btnCancel = (Button) dialog.findViewById(R.id.btn_cancel);
        Button btnSearch = (Button) dialog.findViewById(R.id.btn_search);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

//        dialog.getWindow().setAttributes(lp);
        dialog.show();
//            return dialog;
//        }
    }
}
