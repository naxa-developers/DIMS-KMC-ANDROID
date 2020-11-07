package np.com.naxa.iset.network.retrofit;

import com.github.simonpercic.oklog3.OkLogInterceptor;

import np.com.naxa.iset.network.UrlClass;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Samir on 5/7/2018.
 */

public class NetworkApiClient {

    private static Retrofit retrofit = null;

    public static Retrofit getAPIClient() {

        if (retrofit == null) {
            OkLogInterceptor okLogInterceptor = OkLogInterceptor.builder().build();
            OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

            Dispatcher dispatcher = new Dispatcher();
            dispatcher.setMaxRequests(3);

            okHttpBuilder.dispatcher(dispatcher);
            okHttpBuilder.addInterceptor(okLogInterceptor);

//            Gson gson = new GsonBuilder()
//                    .setLenient()
//                    .create();

            OkHttpClient okHttpClient = okHttpBuilder.build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(UrlClass.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;

    }


//    private static OkHttpClient httpClient = new OkHttpClient();
//    private static NetworkApiClient instance = new NetworkApiClient();
//    private NetworkApiInterface service;
//
//
//    private NetworkApiClient() {
//
//        Retrofit retrofit = createAdapter().build();
//        service = retrofit.create(NetworkApiInterface.class);
//    }
//
//    public static NetworkApiClient getAPIClient() {
//        return instance;
//    }
//
//    private Retrofit.Builder createAdapter() {
//
////        httpClient.setReadTimeout(60, TimeUnit.SECONDS);
////        httpClient.setConnectTimeout(60, TimeUnit.SECONDS);
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        httpClient.interceptors().add(interceptor);
//
//        return new Retrofit.Builder()
//                .baseUrl(UrlClass.BASE_URL)
//                .client(httpClient)
//                .addConverterFactory(GsonConverterFactory.create());
//    }




}
