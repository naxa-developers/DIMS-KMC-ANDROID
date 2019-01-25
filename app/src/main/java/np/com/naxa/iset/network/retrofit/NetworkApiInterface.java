package np.com.naxa.iset.network.retrofit;



import io.reactivex.Observable;
import np.com.naxa.iset.mycircle.MyCircleContactListResponse;
import np.com.naxa.iset.mycircle.registeruser.RegisterResponse;
import np.com.naxa.iset.network.UrlClass;
import np.com.naxa.iset.network.model.AskForHelpResponse;
import np.com.naxa.iset.network.model.GeoJsonCategoryDetails;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Samir on 5/7/2018.
 */

public interface NetworkApiInterface {


    @Multipart
    @POST("ReportController/report_insert_api")
    Call<AskForHelpResponse> getAskForHelpResponse(@Part MultipartBody.Part file,
                                                     @Part("data") RequestBody jsonToSend);

    @GET("MapApi/get_category")
    Observable<GeoJsonCategoryDetails> getGeoJsonCategoryDetails();

    @POST("MapApi/geojson")
    @FormUrlEncoded
    Observable<ResponseBody> getGeoJsonDetails(@Field("table") String table_name);


    @POST("check_registration")
    @FormUrlEncoded
    Observable<RegisterResponse> getRegisterResponse(@Field("api_key") String api_key,
                                                     @Field("data") String jsonData);

    @POST("loginCheck")
    @FormUrlEncoded
    Observable<RegisterResponse> getLoginResponse(@Field("api_key") String api_key,
                                                     @Field("data") String jsonData);

    @POST("check_registered_num")
    @FormUrlEncoded
    Observable<MyCircleContactListResponse> getContactListResponse(@Field("api_key") String api_key,
                                                             @Field("data") String jsonData);



}

