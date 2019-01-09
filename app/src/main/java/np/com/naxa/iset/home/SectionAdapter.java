package np.com.naxa.iset.home;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import np.com.naxa.iset.R;
import np.com.naxa.iset.home.model.MapDataCategory;

public class SectionAdapter extends BaseSectionQuickAdapter<MySection, BaseViewHolder> {
    public SectionAdapter(int layoutResId, int sectionHeadResId, List data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MySection item) {
        MapDataCategory mapDataCategory = (MapDataCategory) item.t;
        switch (helper.getLayoutPosition() %
                2) {
            case 0:
                new AsyncTask<Void, Void, Void>() {
                    Bitmap theBitmap;

                    @Override
                    protected Void doInBackground(Void... params) {
//                        Looper.prepare();
                        try {
                            theBitmap = Glide.
                                    with(ISET.getInstance()).
                                    load(mapDataCategory.image).
                                    asBitmap().
                                    into(-1,-1).
                                    get();
                        } catch (final ExecutionException | NullPointerException e) {
                            Log.e(TAG, e.getCause()+ " , "+e.getMessage());
                        } catch (final InterruptedException e) {
                            Log.e(TAG, e.getCause()+ " , "+e.getMessage());
                        }
                        return null;
                    }
                    @Override
                    protected void onPostExecute(Void dummy) {
                        if (null != theBitmap) {
                            // The full bitmap should be available here
                            helper.setImageBitmap(R.id.iv, theBitmap);
                            Log.d(TAG, "Image loaded");
                        }
                    }
                }.execute();

                break;

            case 1:
                new AsyncTask<Void, Void, Void>() {
                    Bitmap theBitmap;

                    @Override
                    protected Void doInBackground(Void... params) {
//                        Looper.prepare();
                        try {
                            theBitmap = Glide.
                                    with(ISET.getInstance()).
                                    load(mapDataCategory.image).
                                    asBitmap().
                                    into(-1,-1).
                                    get();
                        } catch (final ExecutionException | NullPointerException e) {
                            Log.e(TAG, e.getCause()+ " , "+e.getMessage());
                        } catch (final InterruptedException e) {
                            Log.e(TAG, e.getCause()+ " , "+e.getMessage());
                        }
                        return null;
                    }
                    @Override
                    protected void onPostExecute(Void dummy) {
                        if (null != theBitmap) {
                            // The full bitmap should be available here
                            helper.setImageBitmap(R.id.iv, theBitmap);
                            Log.d(TAG, "Image loaded");
                        }
                    }
                }.execute();


                break;


        }

        helper.addOnClickListener(R.id.card_view);
        helper.setText(R.id.tv_title, mapDataCategory.getName());
        helper.setText(R.id.tv_subtitle, String.valueOf(new Random().nextInt(50) + 1));
    }

    @Override
    protected void convertHead(BaseViewHolder helper, final MySection item) {
        helper.setText(R.id.header, item.header);


    }


}