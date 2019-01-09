package np.com.naxa.iset.youtubeplayer;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import np.com.naxa.iset.R;
import np.com.naxa.iset.youtubeplayer.helper.YoutubeConstants;
import np.com.naxa.iset.youtubeplayer.helper.YoutubeDataModel;

public class YoutubeVideoListAdapter extends BaseQuickAdapter<YoutubeDataModel, BaseViewHolder> {

    public YoutubeVideoListAdapter(int layoutResId, @Nullable List<YoutubeDataModel> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, YoutubeDataModel item) {

        ImageView imageView = helper.getView(R.id.img_thumbnail);

        helper.setText(R.id.textView_VideoTitle, item.getVideoTitle())
                .setText(R.id.tv_desc_youtube, "" + item.getVideoDesc());

        Glide.with(helper.itemView.getContext())
                .load(item.getVideoThumbnail())
                .override(300, 200)
                .into(imageView);




        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        Intent intent = new Intent(helper.itemView.getContext(), YoutubePlayerActivity.class);
                        intent.putExtra(YoutubeConstants.VIDEO_KEY, item.getVideoId());
                        helper.itemView.getContext().startActivity(intent);

            }
        });

    }
}