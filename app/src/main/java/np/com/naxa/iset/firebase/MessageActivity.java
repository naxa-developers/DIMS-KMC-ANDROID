package np.com.naxa.iset.firebase;

import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import np.com.naxa.iset.R;
import np.com.naxa.iset.home.HomeActivity;
import np.com.naxa.iset.database.viewmodel.MessageHelperViewModel;

public class MessageActivity extends AppCompatActivity {

    Toolbar toolbar;
    @BindView(R.id.textViewNoMessage)
    TextView textViewNoMessage;
    @BindView(R.id.my_recycler_view)
    RecyclerView myRecyclerView;

    MessageHelperViewModel messageHelperViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);

        if (VSOFirebaseMessagingService.notification.equals(true)) {
            Intent intent = new Intent(MessageActivity.this, MessageActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }


        messageHelperViewModel = ViewModelProviders.of(this).get(MessageHelperViewModel.class);

        initToolbar();

        setupListRecycler();

        getAndSetDataToAdapter();
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar_general);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(MessageActivity.this.getResources().getString(R.string.messageActivity));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    private void setupListRecycler() {
        MessageListAdapter messageListAdapter = new MessageListAdapter(R.layout.inbox_list_row, null);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myRecyclerView.setAdapter(messageListAdapter);
    }


    private void getAndSetDataToAdapter(){

        List<MessageHelper> messageHelperList = new ArrayList<MessageHelper>();

        messageHelperViewModel.getAllMessageList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<List<MessageHelper>>() {
                    @Override
                    public void onNext(List<MessageHelper> messageHelpers) {
                        Log.i("firebase", "onNext: message list size"+ messageHelpers.size());

                        if(messageHelpers.size() > 0){
                            textViewNoMessage.setVisibility(View.GONE);
                        }
                        messageHelperList.addAll(messageHelpers);
                        ((MessageListAdapter) myRecyclerView.getAdapter()).replaceData(messageHelperList);


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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                startActivity(new Intent(MessageActivity.this, HomeActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
