package np.com.naxa.iset.publications;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import np.com.naxa.iset.R;
import np.com.naxa.iset.event.PublicationListItemEvent;
import np.com.naxa.iset.publications.entity.PublicationsListDetails;
import np.com.naxa.iset.publications.youtubeplayer.YoutubePlayerActivity;
import np.com.naxa.iset.publications.youtubeplayer.helper.YoutubeConstants;
import np.com.naxa.iset.utils.CreateAppMainFolderUtils;
import np.com.naxa.iset.utils.imageutils.LoadImageUtils;

import static android.text.Html.fromHtml;

public class PublicationDetailsActivity extends AppCompatActivity {

    private static final String TAG = "PublicationDetails";
    @BindView(R.id.toolbar_general)
    Toolbar toolbar;
    @BindView(R.id.tv_publication_title)
    TextView tvPublicationTitle;
    @BindView(R.id.imageViewPublicationDetails)
    ImageView imageViewPublicationDetails;
    @BindView(R.id.tv_publication_desc)
    TextView tvPublicationDesc;
    @BindView(R.id.btn_view_files_video)
    Button btnViewFilesVideo;

    PublicationsListDetails publicationsListDetails;
//    @BindView(R.id.pdfView)
//    PDFView pdfView;

    private DownloadManager downloadManager;
    CreateAppMainFolderUtils createAppMainFolderUtils;

    private long pdf_DownloadId;
    private boolean isPDFView = false;
    private String PDFFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publication_details);
        ButterKnife.bind(this);

        createAppMainFolderUtils = new CreateAppMainFolderUtils(PublicationDetailsActivity.this, CreateAppMainFolderUtils.appmainFolderName);


        //set filter to only when download is complete and register broadcast receiver
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(downloadReceiver, filter);


        Intent intent = getIntent();
        publicationsListDetails = intent.getParcelableExtra(YoutubeConstants.VIDEO_KEY);
        if (publicationsListDetails.getType().equals(PublicationListItemEvent.KEY_IMAGE)) {
            btnViewFilesVideo.setVisibility(View.GONE);
        } else if (publicationsListDetails.getType().equals(PublicationListItemEvent.KEY_FILES)) {
            btnViewFilesVideo.setText("View PDF Files");
        }

        tvPublicationTitle.setText(publicationsListDetails.getTitle());


        tvPublicationDesc.setText(publicationsListDetails.getSummary());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvPublicationDesc.setText(fromHtml(publicationsListDetails.getSummary(), 0, new ImageGetter(), null));
        } else {
            tvPublicationDesc.setText(fromHtml(publicationsListDetails.getSummary()));
        }

        LoadImageUtils.loadImageToViewFromSrc(imageViewPublicationDetails, publicationsListDetails.getPhoto());

    }

    @OnClick(R.id.btn_view_files_video)
    public void onViewClicked() {

        viewFilesVideo(publicationsListDetails);
    }


    private void viewFilesVideo(@NonNull PublicationsListDetails publicationsListDetails) {
        String type = publicationsListDetails.getType();

        switch (type) {
            case PublicationListItemEvent.KEY_IMAGE:
                btnViewFilesVideo.setVisibility(View.GONE);

                break;

            case PublicationListItemEvent.KEY_VIDEO:
                Intent intent = new Intent(PublicationDetailsActivity.this, YoutubePlayerActivity.class);
                intent.putExtra(YoutubeConstants.VIDEO_KEY, publicationsListDetails);
                startActivity(intent);
                break;

            case PublicationListItemEvent.KEY_FILES:
                Log.d(TAG, "viewFilesVideo: " + publicationsListDetails.getFile());
                viewPDFData(publicationsListDetails);
                break;
        }
    }


    List<String> imageList = new ArrayList<String>();

    private class ImageGetter implements Html.ImageGetter {

        public Drawable getDrawable(String source) {
            int id;
            if (!source.equals("")) {
//                id = R.drawable.hughjackman;
                imageList.add(source);
                Log.d(TAG, "getDrawable: " + imageList.size() + " " + source);
            } else {
                return null;
            }
            return null;
        }
    }


    private void viewPDFData(@NonNull PublicationsListDetails publicationsListDetails) {
//        isPDFView = true;
        PDFFileName = publicationsListDetails.getTitle() + ".pdf";

        File targetFile = new File(createAppMainFolderUtils.getAppMediaFolderName() + File.separator + PDFFileName);
        if (targetFile.exists()) {
            viewPDFFile(createAppMainFolderUtils.getAppMediaFolderName(), PDFFileName);
        } else {
            pdf_DownloadId = DownloadData(publicationsListDetails);
        }
    }

    private long DownloadData(@NonNull PublicationsListDetails publicationsListDetails) {

        DownloadManager.Query query = null;
        Cursor c = null;


        long downloadReference;

        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(publicationsListDetails.getFile()));

        //Setting title of request
        request.setTitle(PDFFileName);

        //Setting description of request
        request.setDescription("Kathmandu Metropolitan City DRR Management System");

        //Set the local destination for the downloaded file to a path within the application's external files directory
        request.setDestinationInExternalPublicDir(CreateAppMainFolderUtils.appmainFolderName + "/" + CreateAppMainFolderUtils.mediaFolderName, publicationsListDetails.getTitle() + ".pdf");

        //Enqueue download and save the referenceId
        downloadReference = downloadManager.enqueue(request);

        return downloadReference;
    }

    private BroadcastReceiver downloadReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, @NonNull Intent intent) {

            //check if the broadcast message is for our Enqueued download
            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

            if (referenceId == pdf_DownloadId) {
                Toast toast = Toast.makeText(PublicationDetailsActivity.this,
                        "pdf Download Complete", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();

                viewPDFFile(createAppMainFolderUtils.getAppMediaFolderName(), PDFFileName);
            }

        }
    };

    private void viewPDFFile(String filePath, String fileName) {
        String path = filePath;
        File targetFile = new File(path + File.separator + fileName.trim());

        Uri targetUri1 = Uri.fromFile(targetFile.getAbsoluteFile());
        Uri targetUri;
        path = targetFile.getAbsolutePath();
        targetUri = FileProvider.getUriForFile(PublicationDetailsActivity.this,
                getString(R.string.file_provider_authority),
                targetFile);
        Intent intent;
        intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(targetUri, "application/pdf");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(intent, "Choose Viewer"));
    }
}
