package np.com.naxa.iset.mapboxmap.openspace;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import np.com.naxa.iset.R;

public class MapCategoryListAdapter extends BaseQuickAdapter<MapCategoryModel, BaseViewHolder> {

    public MapCategoryListAdapter(int layoutResId, @Nullable List<MapCategoryModel> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, MapCategoryModel item) {

        helper.setImageDrawable(R.id.ivMapcategories,item.getDrawablecategoryIcon());
        helper.setText(R.id.tvMapCategoryName,item.getCategoryName());


    }
}
