package np.com.naxa.iset.youtubeplayer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import np.com.naxa.iset.R;
import np.com.naxa.iset.youtubeplayer.helper.YoutubeDataModel;

public class YoutubeVideoListActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtuve_video_list);

        recyclerView = (RecyclerView) findViewById(R.id.videoListRecycler);
        setupListRecycler();
    }


    private void setupListRecycler() {
            YoutubeVideoListAdapter youtubeVideoListAdapter = new YoutubeVideoListAdapter(R.layout.youtube_cardview, null);
            recyclerView.setLayoutManager(new LinearLayoutManager(YoutubeVideoListActivity.this));
            recyclerView.setAdapter(youtubeVideoListAdapter);

            setVideoDataList();
    }

    private void setVideoDataList() {
        ArrayList<YoutubeDataModel> youtubeDataModelArrayList = new ArrayList<YoutubeDataModel>();

        for (int i = 0; i < 6; i++) {

            YoutubeDataModel youtubeDataModel = new YoutubeDataModel("3AtDnEC4zak", " Video Title " + i,
                    "Video Description" + i,
                    "https://img.youtube.com/vi/3AtDnEC4zak/0.jpg");

            youtubeDataModelArrayList.add(youtubeDataModel);
        }

        ((YoutubeVideoListAdapter) recyclerView.getAdapter()).replaceData(youtubeDataModelArrayList);
        }
}
