package np.com.naxa.iset.utils;

import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;
import java.util.Locale;

import io.reactivex.observers.DisposableObserver;
import np.com.naxa.iset.R;
import np.com.naxa.iset.drr_dictionary.JSONAssetLoadListener;
import np.com.naxa.iset.drr_dictionary.JSONAssetLoadTask;
import np.com.naxa.iset.drr_dictionary.data_glossary.DataGlossaryWordDetailsActivity;
import np.com.naxa.iset.drr_dictionary.data_glossary.WordsWithDetailsModel;

/**
 * Created by nishon on 3/3/18.
 */

public class TextViewUtils {

    private static final String TAG = "TextViewUtils";


    public static void highlightWordToBlue(List<String> wordlist, TextView textView) {
        String fullText = textView.getText().toString();
        for (String word : wordlist) {

            fullText = fullText.replaceAll(word, "<font color='blue'>" + word + "</font>");
            textView.setText(Html.fromHtml(fullText));
        }
    }
    public static void highlightURLToBlue(List<String> wordlist, TextView textView) {
        String fullText = textView.getText().toString();
        for (String word : wordlist) {

            fullText = fullText.replaceAll(word, "<font color='blue'>" + word + "</font>");
            textView.setText(Html.fromHtml(fullText));
        }
    }

    public static void linkWordToPrivacyPolicy(String[] wordlist, TextView textView) {

        String fullText = textView.getText().toString();
        SpannableStringBuilder span = new SpannableStringBuilder(fullText);


        for (String word : wordlist) {

            String testText = fullText.toLowerCase(Locale.US);
            String testTextToHighlight = word.toLowerCase(Locale.US);

            int startingIndex = testText.indexOf(testTextToHighlight);
            int endingIndex = startingIndex + testTextToHighlight.length();

            span.setSpan(new GotoPrivacyPolicySpan(word), startingIndex, endingIndex, 0);

        }


        textView.setText(span);
        textView.setMovementMethod(new LinkMovementMethod());

    }


    public static void linkWordToYoutubeActivity(String[] wordlist, TextView textView) {

        String fullText = textView.getText().toString();
        SpannableStringBuilder span = new SpannableStringBuilder(fullText);


        for (String word : wordlist) {

            String testText = fullText.toLowerCase(Locale.US);
            String testTextToHighlight = word.toLowerCase(Locale.US);

            int startingIndex = testText.indexOf(testTextToHighlight);
            int endingIndex = startingIndex + testTextToHighlight.length();

            span.setSpan(new GotoYoutubeActivity(word), startingIndex, endingIndex, 0);

        }


        textView.setText(span);
        textView.setMovementMethod(new LinkMovementMethod());

    }

    public static void linkWordsToGlossary(List<String> wordlist, TextView textView) {
        String fullText = textView.getText().toString();
        textView.setText(fullText);
        SpannableStringBuilder span = new SpannableStringBuilder(fullText);

        int prevStartingIndex = -1, prevEndingIndex = -1;


        for (String textItem : wordlist) {


            if (textItem != null && !textItem.trim().equals("") && !textItem.equalsIgnoreCase("other")) {
                //for counting start/end indexes
                String testText = fullText.toLowerCase(Locale.US);
                String testTextToBold = textItem.toLowerCase(Locale.US);
                int startingIndex = testText.indexOf(testTextToBold);
                int endingIndex = startingIndex + testTextToBold.length();



                if (startingIndex >= 0 && endingIndex >= 0) {
                    if(startingIndex != prevStartingIndex && endingIndex != prevEndingIndex) {
//                         span.setSpan(new StyleSpan(Typeface.BOLD), startingIndex, endingIndex, 0);

                    String wordWithSpace[] = {""};
                    for (int index = startingIndex - 1; index <= endingIndex; index++) {
                        if(index < testText.length() ) {
                            StringBuilder stringBuilder = new StringBuilder();
                            wordWithSpace[0] = wordWithSpace[0] + stringBuilder.append(Character.toString(testText.charAt(index)));
//                            Log.e(TAG, "linkWordsToGlossary: " + " index =  " + index);
                        }
                    }
//                    Log.e(TAG, "linkWordsToGlossary: "+wordWithSpace[0] );

                    if ((" " + textItem + " ").equalsIgnoreCase(wordWithSpace[0]) || (" " + textItem + ",").equalsIgnoreCase(wordWithSpace[0])
                            || (" " + textItem + ".").equalsIgnoreCase(wordWithSpace[0])|| ("(" + textItem + ")").equalsIgnoreCase(wordWithSpace[0])) {
                        span.setSpan(new GotoGlossarySpan(textItem), startingIndex, endingIndex, 0);

                    }
                        prevStartingIndex = startingIndex ;
                        prevEndingIndex = endingIndex ;
                    }
                }
            }
        }


        textView.setText(span);
        textView.setMovementMethod(new LinkMovementMethod());


    }

    private static SpannableStringBuilder makeSectionOfTextBold(String text, List<String> textToBold) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);

        for (String textItem : textToBold) {
            if (textItem != null && !textItem.trim().equals("")) {
                //for counting start/end indexes
                String testText = text.toLowerCase(Locale.US);
                String testTextToBold = textItem.toLowerCase(Locale.US);
                int startingIndex = testText.indexOf(testTextToBold);
                int endingIndex = startingIndex + testTextToBold.length();

                if (startingIndex >= 0 && endingIndex >= 0) {
                    //builder.setSpan(new StyleSpan(Typeface.BOLD), startingIndex, endingIndex, 0);
                    builder.setSpan(new GotoGlossarySpan(textItem), startingIndex, endingIndex, 0);

                }
            }
        }
        return builder;
    }


    private static class GotoPrivacyPolicySpan extends ClickableSpan {

        private String text;

        public GotoPrivacyPolicySpan(String text) {
            Log.d("TextViewUtils", "GotoPrivacyPolicySpan: " + text);
            this.text = text;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(Color.BLUE);
        }

        @Override
        public void onClick(View view) {

//            Intent intent = new Intent(view.getContext(), PrivacyPolicyActivity.class);
//            view.getContext().startActivity(intent);
        }
    }

    private static class GotoGlossarySpan extends ClickableSpan {

        String selectedString;

        public GotoGlossarySpan(String s) {
            selectedString = s;

            Log.d(TAG, "GotoGlossarySpan: " + selectedString);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(Color.BLUE);
        }

        @Override
        public void onClick(final View view) {

            Log.e(TAG, "linkWordsToGlossary: Span ::: " + selectedString );

            new JSONAssetLoadTask(R.raw.data_glossary, new JSONAssetLoadListener() {
                @Override
                public void onFileLoadComplete(String fullText) {


                    new GlossaryDao().searchAndOpenDetail(fullText, selectedString)
                            .subscribe(new DisposableObserver<WordsWithDetailsModel>() {
                                @Override
                                public void onNext(WordsWithDetailsModel wordsWithDetailsModel) {
                                    if (!TextUtils.isEmpty(wordsWithDetailsModel.getError())) {
                                        //todo ask what to do
                                        Toast.makeText(view.getContext(),
                                                "Error", Toast.LENGTH_SHORT).show();
                                        return;
                                    }


                                    Intent intent = new Intent(view.getContext(), DataGlossaryWordDetailsActivity.class);
                                    intent.putExtra("wordsWithDetails", wordsWithDetailsModel);
                                    view.getContext().startActivity(intent);

                                }

                                @Override
                                public void onError(Throwable e) {
                                    e.printStackTrace();
                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }

                @Override
                public void onFileLoadError(String errorMsg) {

                }
            }).execute();
        }
    }

    private static class GotoYoutubeActivity extends ClickableSpan {

        private String text;

        public GotoYoutubeActivity(String text) {
            Log.d("TextViewUtils", "GotoYoutubeActivitySpan: " + text);
            this.text = text;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(Color.BLUE);
        }

        @Override
        public void onClick(View view) {

//            Intent intent = new Intent(view.getContext(), YoutubeWebViewActivity.class);
//            intent.putExtra("url", text);
//            view.getContext().startActivity(intent);
        }
    }
}
