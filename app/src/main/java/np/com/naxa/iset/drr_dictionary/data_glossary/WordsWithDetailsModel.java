package np.com.naxa.iset.drr_dictionary.data_glossary;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by samir on 12/18/2017.
 */

public class WordsWithDetailsModel implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("word")
    @Expose
    private String word;
    @SerializedName("meaning")
    @Expose
    private String meaning;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("error")
    @Expose
    private String error;

    public WordsWithDetailsModel(String id, String word, String meaning, String language, String comment, String image) {
        this.id = id;
        this.word = word;
        this.meaning = meaning;
        this.language = language;
        this.comment = comment;
        this.image = image;
    }

    public String getError() {
        return error;
    }

    public WordsWithDetailsModel(String error){
        this.error = error;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.word);
        dest.writeString(this.meaning);
        dest.writeString(this.language);
        dest.writeString(this.comment);
        dest.writeString(this.image);
    }

    public WordsWithDetailsModel(Parcel in) {
        this.id = in.readString();
        this.word = in.readString();
        this.meaning = in.readString();
        this.language = in.readString();
        this.comment = in.readString();
        this.image = in.readString();
    }

    public static final Parcelable.Creator<WordsWithDetailsModel> CREATOR = new Parcelable.Creator<WordsWithDetailsModel>() {
        @Override
        public WordsWithDetailsModel createFromParcel(Parcel source) {
            return new WordsWithDetailsModel(source);
        }

        @Override
        public WordsWithDetailsModel[] newArray(int size) {
            return new WordsWithDetailsModel[size];
        }
    };

    public void setError(String error) {
        this.error = error;
    }
}
