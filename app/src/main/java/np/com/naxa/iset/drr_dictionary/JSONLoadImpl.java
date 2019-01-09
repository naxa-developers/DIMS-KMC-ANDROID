package np.com.naxa.iset.drr_dictionary;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import np.com.naxa.iset.R;
import np.com.naxa.iset.drr_dictionary.data_glossary.WordsWithDetailsModel;
import np.com.naxa.iset.utils.FileUtils;


public class JSONLoadImpl {

    private static List<WordsWithDetailsModel> wordsWithDetailsList;

    public void getGlossaryObject() {

        cacheGlossaryObj()
                .flatMapIterable(new Function<List<WordsWithDetailsModel>, Iterable<WordsWithDetailsModel>>() {
                    @Override
                    public Iterable<WordsWithDetailsModel> apply(List<WordsWithDetailsModel> wordsWithDetailsModels) throws Exception {
                        return wordsWithDetailsModels;
                    }
                })
                .subscribe(new DisposableObserver<WordsWithDetailsModel>() {
                    @Override
                    public void onNext(WordsWithDetailsModel wordsWithDetailsModel) {
                        wordsWithDetailsModel.getTitle();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public static Observable<List<WordsWithDetailsModel>> cacheGlossaryObj() {

        return Observable.create(new ObservableOnSubscribe<List<WordsWithDetailsModel>>() {
            @Override
            public void subscribe(ObservableEmitter<List<WordsWithDetailsModel>> e) throws Exception {

                if (wordsWithDetailsList == null) {
                    String fileContent = FileUtils.readFromFile(R.raw.data_glossary);
                    Type listType = new TypeToken<List<WordsWithDetailsModel>>() {
                    }.getType();

                    wordsWithDetailsList = new Gson().fromJson(fileContent, listType);
                }


                e.onNext(wordsWithDetailsList);
                e.onComplete();
            }
        });

    }


    private Observable<WordsWithDetailsModel> searchAndOpenDetail(final String jsonString, final String searchString) {

        return Observable.just(jsonString)
                .flatMap(new Function<String, ObservableSource<List<WordsWithDetailsModel>>>() {
                    @Override
                    public ObservableSource<List<WordsWithDetailsModel>> apply(String s) throws Exception {
                        Type listType = new TypeToken<List<WordsWithDetailsModel>>() {
                        }.getType();
                        List<WordsWithDetailsModel> list = new Gson().fromJson(jsonString, listType);
                        return Observable.just(list);

                    }
                })
                .flatMapIterable(new Function<List<WordsWithDetailsModel>, Iterable<WordsWithDetailsModel>>() {
                    @Override
                    public Iterable<WordsWithDetailsModel> apply(List<WordsWithDetailsModel> wordsWithDetailsModels) throws Exception {
                        return wordsWithDetailsModels;
                    }
                })
                .filter(new Predicate<WordsWithDetailsModel>() {
                    @Override
                    public boolean test(WordsWithDetailsModel wordsWithDetailsModel) throws Exception {
                        return wordsWithDetailsModel.getTitle().equalsIgnoreCase(searchString.trim());

                    }
                })
                .defaultIfEmpty(new WordsWithDetailsModel("error"));
    }


}
