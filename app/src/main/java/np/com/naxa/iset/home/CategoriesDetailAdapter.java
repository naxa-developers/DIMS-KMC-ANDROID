package np.com.naxa.iset.home;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import np.com.naxa.iset.R;
import np.com.naxa.iset.hospitalfilter.SortedHospitalItem;

public class CategoriesDetailAdapter extends BaseQuickAdapter<SortedHospitalItem, BaseViewHolder> {

    public CategoriesDetailAdapter(int layoutResId, @Nullable List<SortedHospitalItem> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, SortedHospitalItem item) {
        helper.setText(R.id.tv_name,item.getHospitalAndCommon().getCommonPlacesAttrb().getName())
                .setText(R.id.tv_location, item.getHospitalAndCommon().getHospitalFacilities().getType())
                .setText(R.id.tv_desciption,item.getDistance());
    }
}
