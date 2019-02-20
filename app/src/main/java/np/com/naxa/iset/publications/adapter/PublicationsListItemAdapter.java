package np.com.naxa.iset.publications.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import np.com.naxa.iset.R;
import np.com.naxa.iset.event.PublicationListItemEvent;
import np.com.naxa.iset.publications.entity.PublicationsListDetails;
import np.com.naxa.iset.utils.imageutils.LoadImageUtils;

public class PublicationsListItemAdapter extends BaseQuickAdapter<PublicationsListDetails, BaseViewHolder> {

    public PublicationsListItemAdapter(int layoutResId, @Nullable List<PublicationsListDetails> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, PublicationsListDetails item) {
        RelativeLayout relativeLayout = helper.getView(R.id.publication_item_layout);
        ImageView imageView = helper.getView(R.id.img_publication_thumbnail);

        helper.setText(R.id.publication_title,item.getTitle());
        helper.setText(R.id.publication_name,item.getName());
        helper.setText(R.id.publication_type,item.getType());

        LoadImageUtils.loadImageToViewFromSrc(imageView, item.getPhoto());


//        if(((helper.getLayoutPosition()+1) %2) == 0){
//            Log.d(TAG, "convert: "+helper.getLayoutPosition());
//            helper.setBackgroundColor(R.id.publication_item_layout, relativeLayout.getContext().getResources().getColor(R.color.listItemSecondBG));
//        }

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ToastUtils.showShortToast(item.getName()+" clicked");
                EventBus.getDefault().post(new PublicationListItemEvent.PublicationListitemClick(item));

            }
        });

    }
}
