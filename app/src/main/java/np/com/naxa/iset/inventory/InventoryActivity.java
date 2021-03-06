package np.com.naxa.iset.inventory;

import android.app.Dialog;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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
import np.com.naxa.iset.disasterinfo.HazardInfoActivity;
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

    List<String> categoryName;
    List<String> subCategoryName;
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
            getAllCatSubCatFIlteredDataFromDatabase("All", "All");
            getDistinctCategoryList();
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
        Dialog dialog = DialogFactory.createProgressDialog(InventoryActivity.this, "Loading...");
        dialog.show();
        apiInterface.getInventoryListResponse(UrlClass.API_ACCESS_TOKEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<InventoryListResponse>() {
                    @Override
                    public void onNext(InventoryListResponse inventoryListResponse) {
                        dialog.dismiss();
                        if (inventoryListResponse.getError() == 0) {
                            if (inventoryListResponse.getData() != null) {
                                inventoryListDetailsViewModel.insertAll(inventoryListResponse.getData());
                                getDistinctCategoryList();
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
                        dialog.dismiss();
                        DialogFactory.createCustomErrorDialog(InventoryActivity.this, e.getMessage(), new DialogFactory.CustomDialogListener() {
                            @Override
                            public void onClick() {

                            }
                        }).show();
                    }

                    @Override
                    public void onComplete() {
                        dialog.dismiss();
                    }
                });

    }

    private void getDistinctCategoryList() {
        inventoryListDetailsViewModel.getDistinctCategoryList()
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<List<String>>() {
                    @Override
                    public void onNext(List<String> strings) {
                        categoryName = new ArrayList<String>();
                        categoryName.add("All");
                        for (int i = 0 ; i<strings.size() ; i++){
                            if(strings.get(i)!= null){
                                categoryName.add(strings.get(i));

                            }
                        }
                        Log.d(TAG, "getDistinctCategoryCategoryList: " + categoryName.size());

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


        TextView text = (TextView) dialog.findViewById(R.id.tv_message);
        Spinner spnCategory = (Spinner) dialog.findViewById(R.id.spn_category_type);
        Spinner spnSubCategory = (Spinner) dialog.findViewById(R.id.spn_sub_category_type);
        dialogSpinnerSetup(spnCategory, spnSubCategory);


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

                getAllCatSubCatFIlteredDataFromDatabase(spnCategory.getSelectedItem().toString(), spnSubCategory.getSelectedItem().toString());
            }
        });

        dialog.show();

    }

    ArrayAdapter<String> categoryAdapter, subCategoryAdapter;
    private void dialogSpinnerSetup(Spinner spnCategory, Spinner spnSubCategory){
        if(categoryName != null) {
            categoryAdapter = new ArrayAdapter<String>(InventoryActivity.this,
                    R.layout.item_spinner, categoryName);
            categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnCategory.setAdapter(categoryAdapter);

            spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    // your code here

                    inventoryListDetailsViewModel.getDistinctSubCategoryistFromCategory(spnCategory.getSelectedItem().toString())
                            .observeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new DisposableSubscriber<List<String>>() {
                                @Override
                                public void onNext(List<String> strings) {
                                    subCategoryName = new ArrayList<String>();
                                    subCategoryName.add("All");
                                    for (int i = 0 ; i<strings.size() ; i++){
                                        if(strings.get(i)!= null){
                                            subCategoryName.add(strings.get(i));

                                        }
                                    }
                                    Log.d(TAG, "getDistinctCategoryList: " + subCategoryName.size());
                                    if(subCategoryName != null) {
                                        subCategoryAdapter = new ArrayAdapter<String>(InventoryActivity.this,
                                                R.layout.item_spinner, subCategoryName);
                                        subCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        spnSubCategory.setAdapter(subCategoryAdapter);
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
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });
        }

    }

    private void getAllCatSubCatFIlteredDataFromDatabase(String category, String subCategory) {

        Log.d(TAG, "getAllCatSubCatFIlteredDataFromDatabase: "+category +" , "+subCategory);



        inventoryListDetailsViewModel.getCatSubCatWiseList(category, subCategory)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<List<InventoryListDetails>>() {
                    @Override
                    public void onNext(List<InventoryListDetails> inventoryListDetails) {
                        ((InventoryListAdapter) recyclerViewInventoryList.getAdapter()).replaceData(inventoryListDetails);
                        Log.d(TAG, "getAllCatSubCatFIlteredDataFromDatabase size: " + inventoryListDetails.size());


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
