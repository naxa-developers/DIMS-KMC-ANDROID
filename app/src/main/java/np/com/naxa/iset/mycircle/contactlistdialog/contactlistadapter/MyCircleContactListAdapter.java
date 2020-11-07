package np.com.naxa.iset.mycircle.contactlistdialog.contactlistadapter;

import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import np.com.naxa.iset.R;
import np.com.naxa.iset.event.MyCircleContactEvent;
import np.com.naxa.iset.mycircle.ContactModel;
import np.com.naxa.iset.utils.imageutils.CircleTransform;
import np.com.naxa.iset.database.viewmodel.MyCircleContactViewModel;

public class MyCircleContactListAdapter extends BaseQuickAdapter<ContactModel, BaseViewHolder> {

    MyCircleContactViewModel myCircleContactViewModel;
    public MyCircleContactListAdapter(int layoutResId, @Nullable List<ContactModel> data) {
        super(layoutResId, data);
//        this.myCircleContactViewModel = myCircleContactViewModel;
    }


    @Override
    protected void convert(BaseViewHolder helper, ContactModel item) {
        Button btnDelete = helper.getView(R.id.btnDeletePeopleInMyCircle);
        Button btnStatus = helper.getView(R.id.btnMyCirclePeopleStatus);
        ImageView imageView = helper.getView(R.id.ivPeopleInMyCircle);

        if(item.getName() != null) {
            helper.setText(R.id.tvPeopleInMyCircleName, item.getName());
        }

        if(item.getImg_url() != null && !item.getImg_url().equals("")) {
            Glide.with(mContext).load(item.getImg_url())
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
//                item.setAddToCircle(0);
//                myCircleContactViewModel.insert(item);
                EventBus.getDefault().post(new MyCircleContactEvent.MyCircleContactRemoveFromListeClick(item, helper.getLayoutPosition()));

            }
        });


    }
}
