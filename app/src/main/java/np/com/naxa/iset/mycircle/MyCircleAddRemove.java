package np.com.naxa.iset.mycircle;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import np.com.naxa.iset.mycircle.contactlistdialog.contactlistadapter.MyCircleContactListAdapter;
import np.com.naxa.iset.mycircle.registeruser.NormalResponse;
import np.com.naxa.iset.mycircle.registeruser.UserModel;
import np.com.naxa.iset.network.UrlClass;
import np.com.naxa.iset.network.retrofit.NetworkApiInterface;
import np.com.naxa.iset.utils.DialogFactory;
import np.com.naxa.iset.utils.JsonGsonConverterUtils;
import np.com.naxa.iset.utils.NetworkUtils;
import np.com.naxa.iset.utils.SharedPreferenceUtils;
import np.com.naxa.iset.database.viewmodel.MyCircleContactViewModel;

public class MyCircleAddRemove {
    SharedPreferenceUtils sharedPreferenceUtils;

    public void addMyCircleList(Context context, NetworkApiInterface apiInterface, List<ContactModel> contactModelList, RecyclerView recyclerView) {
        sharedPreferenceUtils = new SharedPreferenceUtils(context);
        UserModel userModel = JsonGsonConverterUtils.getUserFromJson(sharedPreferenceUtils.getStringValue(SharedPreferenceUtils.USER_DETAILS, null));

        apiInterface.getCircleListUpdateResponse(UrlClass.API_ACCESS_TOKEN,
                userModel.getEmail(),
                JsonGsonConverterUtils.getJsonFromGson(contactModelList))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableObserver<NormalResponse>() {
                    @Override
                    public void onNext(NormalResponse normalResponse) {

                        if (normalResponse.getError() == 0) {
                            DialogFactory.createCustomDialog(context, normalResponse.getMessage(), new DialogFactory.CustomDialogListener() {
                                @Override
                                public void onClick() {
                                    ((MyCircleContactListAdapter) recyclerView.getAdapter()).replaceData(contactModelList);

                                }
                            }).show();
                        }

                        if (normalResponse.getError() == 1) {
                            DialogFactory.createCustomErrorDialog(context, normalResponse.getMessage(), new DialogFactory.CustomDialogListener() {
                                @Override
                                public void onClick() {

                                }
                            }).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        DialogFactory.createCustomErrorDialog(context, e.getMessage(), new DialogFactory.CustomDialogListener() {
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

    public void removeFromMyCircleList(Context context, NetworkApiInterface apiInterface, ContactModel myCircleContactListData, RecyclerView recyclerView, MyCircleContactViewModel myCircleContactViewModel) {
        sharedPreferenceUtils = new SharedPreferenceUtils(context);
        UserModel userModel = JsonGsonConverterUtils.getUserFromJson(sharedPreferenceUtils.getStringValue(SharedPreferenceUtils.USER_DETAILS, null));

        apiInterface.getCircleListDeleteResponse(UrlClass.API_ACCESS_TOKEN,
                userModel.getEmail(), myCircleContactListData.getMobileNumber())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableObserver<NormalResponse>() {
                    @Override
                    public void onNext(NormalResponse normalResponse) {

                        if (normalResponse.getError() == 0) {
                            DialogFactory.createCustomDialog(context, normalResponse.getMessage(), new DialogFactory.CustomDialogListener() {
                                @Override
                                public void onClick() {
                                    updateMyCircleRecycleerListAfterItemRemoved(context, myCircleContactViewModel, recyclerView,myCircleContactListData);
                                }
                            }).show();
                        }

                        if (normalResponse.getError() == 1) {
                            DialogFactory.createCustomErrorDialog(context, normalResponse.getMessage(), new DialogFactory.CustomDialogListener() {
                                @Override
                                public void onClick() {

                                }
                            }).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        DialogFactory.createCustomErrorDialog(context, e.getMessage(), new DialogFactory.CustomDialogListener() {
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

    private void updateMyCircleRecycleerListAfterItemRemoved(Context context, @NonNull MyCircleContactViewModel myCircleContactViewModel, RecyclerView recyclerView, ContactModel contactModel1) {
        myCircleContactViewModel.getAllMyCircleContacts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<List<ContactModel>>() {
                    @Override
                    public void onNext(List<ContactModel> contactModels) {
                        if (NetworkUtils.isNetworkAvailable()) {

                            ContactModel contactModel = contactModel1;
                            contactModel.setAddToCircle(0);
                            myCircleContactViewModel.insert(contactModel);

                            ((MyCircleContactListAdapter) recyclerView.getAdapter()).replaceData(contactModels);

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

}
