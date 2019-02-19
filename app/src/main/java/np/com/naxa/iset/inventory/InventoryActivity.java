package np.com.naxa.iset.inventory;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import np.com.naxa.iset.R;
import np.com.naxa.iset.database.viewmodel.InventoryListDetailsViewModel;
import np.com.naxa.iset.inventory.model.InventoryListResponse;
import np.com.naxa.iset.network.UrlClass;
import np.com.naxa.iset.network.retrofit.NetworkApiClient;
import np.com.naxa.iset.network.retrofit.NetworkApiInterface;
import np.com.naxa.iset.utils.DialogFactory;

public class InventoryActivity extends AppCompatActivity {

    NetworkApiInterface apiInterface;
    InventoryListDetailsViewModel inventoryListDetailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        apiInterface = NetworkApiClient.getAPIClient().create(NetworkApiInterface.class);
        inventoryListDetailsViewModel = ViewModelProviders.of(this).get(InventoryListDetailsViewModel.class);

        fetchDataFromServer();

    }


    private void fetchDataFromServer(){

        apiInterface.getInventoryListResponse(UrlClass.API_ACCESS_TOKEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<InventoryListResponse>() {
                    @Override
                    public void onNext(InventoryListResponse inventoryListResponse) {

                        if(inventoryListResponse.getError() == 0){
                            if(inventoryListResponse.getData() != null) {
                                inventoryListDetailsViewModel.insertAll(inventoryListResponse.getData());
                            }
                        }

                        if(inventoryListResponse.getError() == 1){
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
}
