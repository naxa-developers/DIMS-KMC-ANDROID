package np.com.naxa.iset.mycircle;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import np.com.naxa.iset.R;
import np.com.naxa.iset.event.MyCircleContactAddEvent;
import np.com.naxa.iset.utils.imageutils.CircleTransform;

public class MyCircleContactListAdapter extends BaseQuickAdapter<MyCircleContactListData, BaseViewHolder> {

    public MyCircleContactListAdapter(int layoutResId, @Nullable List<MyCircleContactListData> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, MyCircleContactListData item) {
        Button btnDelete = helper.getView(R.id.btnDeletePeopleInMyCircle);
        Button btnStatus = helper.getView(R.id.btnMyCirclePeopleStatus);
        ImageView imageView = helper.getView(R.id.ivPeopleInMyCircle);

        if(item.getName() != null) {
            helper.setText(R.id.tvPeopleInMyCircleName, item.getName());
        }

        if(item.getImgUrl() != null && !item.getImgUrl().equals("")) {
            Glide.with(mContext).load(item.getImgUrl())
                    .placeholder(mContext.getResources().getDrawable(R.drawable.ic_nav_profile))
                    .crossFade()
                    .thumbnail(0.5f)
                    .bitmapTransform(new CircleTransform(mContext))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);

        }else {
            Glide.with(mContext).load(mContext.getResources().getDrawable(R.drawable.ic_nav_profile))
                    .crossFade()
                    .thumbnail(0.5f)
                    .bitmapTransform(new CircleTransform(mContext))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }


        if(((helper.getLayoutPosition()+1) %2) == 0){
            Log.d(TAG, "convert: "+helper.getLayoutPosition());
            helper.setBackgroundColor(R.id.btnMyCirclePeopleStatus, btnStatus.getContext().getResources().getColor(R.color.isetGreen));
            helper.setText(R.id.btnMyCirclePeopleStatus, "Safe");
        }

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}
