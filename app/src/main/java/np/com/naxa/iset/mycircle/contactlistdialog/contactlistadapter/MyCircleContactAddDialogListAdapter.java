package np.com.naxa.iset.mycircle.contactlistdialog.contactlistadapter;

import androidx.annotation.Nullable;
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
import np.com.naxa.iset.event.MyCircleContactEvent;
import np.com.naxa.iset.mycircle.ContactModel;
import np.com.naxa.iset.utils.imageutils.CircleTransform;

public class MyCircleContactAddDialogListAdapter extends BaseQuickAdapter<ContactModel, BaseViewHolder> {

    public MyCircleContactAddDialogListAdapter(int layoutResId, @Nullable List<ContactModel> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, ContactModel item) {
        ToggleButton addButton = helper.getView(R.id.btnAddContactToCircle);
        ImageView imageView = helper.getView(R.id.ivContactPerson);

            helper.setText(R.id.tvContactPerson, item.getName());
//

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

        addButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    addButton.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ic_remove_circle_outline_black_24dp));
                    EventBus.getDefault().post(new MyCircleContactEvent.MyCircleContactAddClick(item, true));

                }
                else
                {
                    addButton.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ic_add_black_24dp));
                    EventBus.getDefault().post(new MyCircleContactEvent.MyCircleContactAddClick(item, false));

                }

            }
        });


        if(item.getAddToCircle() == 1){
            addButton.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ic_remove_circle_outline_black_24dp));
            addButton.setChecked(false);
        }

    }
}
