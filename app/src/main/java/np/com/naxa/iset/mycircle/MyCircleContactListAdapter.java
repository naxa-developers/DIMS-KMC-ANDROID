package np.com.naxa.iset.mycircle;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import np.com.naxa.iset.R;
import np.com.naxa.iset.event.EmergenctContactCallEvent;
import np.com.naxa.iset.event.MyCircleContactAddEvent;
import np.com.naxa.iset.utils.imageutils.CircleTransform;

public class MyCircleContactListAdapter extends BaseQuickAdapter<ContactModel, BaseViewHolder> {

    public MyCircleContactListAdapter(int layoutResId, @Nullable List<ContactModel> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, ContactModel item) {
        Button addButton = helper.getView(R.id.btnAddContactToCircle);
        ImageView imageView = helper.getView(R.id.ivContactPerson);

        if(item.getName() != null) {
            helper.setText(R.id.tvContactPerson, item.getName());
        }

        if(item.getPhotoURI() != null && !item.getPhotoURI().equals("")) {
            Glide.with(mContext).load(item.photoURI)
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



        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MyCircleContactAddEvent.MyCircleContactAddClick(item));
            }
        });

    }
}
