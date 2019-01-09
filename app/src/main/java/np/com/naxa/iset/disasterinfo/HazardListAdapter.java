package np.com.naxa.iset.disasterinfo;

import android.content.Intent;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import np.com.naxa.iset.R;
import np.com.naxa.iset.detailspage.MarkerDetailsKeyValue;

public class HazardListAdapter extends BaseQuickAdapter<HazardListModel, BaseViewHolder> {

    public HazardListAdapter(int layoutResId, @Nullable List<HazardListModel> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, HazardListModel item) {
        LinearLayout linearLayout = helper.getView(R.id.hazard_list_item_row_layout);

        helper.setText(R.id.tv_hazard_list_title,item.getTitle());


        if(((helper.getLayoutPosition()+1) %2) == 0){
            Log.d(TAG, "convert: "+helper.getLayoutPosition());
            helper.setBackgroundColor(R.id.hazard_list_item_row_layout, linearLayout.getContext().getResources().getColor(R.color.listItemSecondBG));
        }

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(linearLayout.getContext(), HazardInfoDetailsActivity.class);
                intent.putExtra("OBJ", item);
                linearLayout.getContext().startActivity(intent);
            }
        });

    }
}
