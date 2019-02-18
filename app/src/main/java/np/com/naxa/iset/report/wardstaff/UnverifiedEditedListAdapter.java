package np.com.naxa.iset.report.wardstaff;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import np.com.naxa.iset.R;
import np.com.naxa.iset.report.ReportActivity;
import np.com.naxa.iset.database.entity.ReportDetailsEntity;
import np.com.naxa.iset.event.ReportSavedFormListItemEvent;

public class UnverifiedEditedListAdapter extends BaseQuickAdapter<ReportDetailsEntity, BaseViewHolder> {

    public UnverifiedEditedListAdapter(int layoutResId, @Nullable List<ReportDetailsEntity> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, ReportDetailsEntity item) {
        CardView cardView = helper.getView(R.id.cardViewSavedFormList);

        helper.setText(R.id.textViewFormName,item.getIncident_type()+" ward-"+item.getWard());
        helper.setText(R.id.textViewSavedFormDate,item.getDate()+", "+item.getTime());

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, ReportActivity.class));

                final android.os.Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        EventBus.getDefault().post(new ReportSavedFormListItemEvent.ReportUnverifiedFormListItemClick(item, false));
                    }
                }, 100);

            }
        });

    }
}
