package np.com.naxa.iset.mycircle;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

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

public class MyCircleContactAddDialogListAdapter extends BaseQuickAdapter<MyCircleContactListData, BaseViewHolder> {

    public MyCircleContactAddDialogListAdapter(int layoutResId, @Nullable List<MyCircleContactListData> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, MyCircleContactListData item) {
        ToggleButton addButton = helper.getView(R.id.btnAddContactToCircle);
        ImageView imageView = helper.getView(R.id.ivContactPerson);

        if(item.getName() != null) {
            helper.setText(R.id.tvContactPerson, item.getName());
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



//        addButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });

        addButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    addButton.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ic_remove_circle_outline_black_24dp));
                    EventBus.getDefault().post(new MyCircleContactAddEvent.MyCircleContactAddClick(item, true));

                }
                else
                {
                    addButton.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ic_add_black_24dp));
                    EventBus.getDefault().post(new MyCircleContactAddEvent.MyCircleContactAddClick(item, false));

                }

            }
        });

    }
}
