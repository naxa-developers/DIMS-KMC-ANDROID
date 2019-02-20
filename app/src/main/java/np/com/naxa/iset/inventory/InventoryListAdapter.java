package np.com.naxa.iset.inventory;

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
import np.com.naxa.iset.inventory.model.InventoryListDetails;
import np.com.naxa.iset.utils.imageutils.LoadImageUtils;

public class InventoryListAdapter extends BaseQuickAdapter<InventoryListDetails, BaseViewHolder> {

    public InventoryListAdapter(int layoutResId, @Nullable List<InventoryListDetails> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, InventoryListDetails item) {
        RelativeLayout relativeLayout = helper.getView(R.id.inventory_list_item_layout);
        ImageView imageView = helper.getView(R.id.img_inventory_thumbnail);

        helper.setText(R.id.inventory_cat_name,item.getCategoryName());
        helper.setText(R.id.inventory_subcat_name,item.getSubcatName());
        helper.setText(R.id.inventory_email,item.getEmail());
        helper.setText(R.id.inventory_contact,item.getPhone());
        helper.setText(R.id.inventory_org_name,item.getOrgname());

        LoadImageUtils.loadImageToViewFromSrc(imageView, item.getCategoryImage());


//        if(((helper.getLayoutPosition()+1) %2) == 0){
//            Log.d(TAG, "convert: "+helper.getLayoutPosition());
//            helper.setBackgroundColor(R.id.inventory_list_item_layout, relativeLayout.getContext().getResources().getColor(R.color.listItemSecondBG));
//        }

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ToastUtils.showShortToast(item.getName()+" clicked");
//                EventBus.getDefault().post(new PublicationListItemEvent.PublicationListitemClick(item));

            }
        });

    }
}
