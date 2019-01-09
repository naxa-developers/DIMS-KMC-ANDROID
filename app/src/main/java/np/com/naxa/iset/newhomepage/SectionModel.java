package np.com.naxa.iset.newhomepage;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

/**
 * Created by samir on 01/12/18..
 */

public class SectionModel {
    private String sectionLabel;
    private ArrayList<String> itemArrayList;
    private ArrayList<Drawable> itemDrawableArrayList;

    public SectionModel(String sectionLabel, ArrayList<String> itemArrayList) {
        this.sectionLabel = sectionLabel;
        this.itemArrayList = itemArrayList;
    }

    public SectionModel(String sectionLabel, ArrayList<String> itemArrayList, ArrayList<Drawable> itemDrawableArrayList) {
        this.sectionLabel = sectionLabel;
        this.itemArrayList = itemArrayList;
        this.itemDrawableArrayList = itemDrawableArrayList;
    }

    public String getSectionLabel() {
        return sectionLabel;
    }

    public ArrayList<String> getItemArrayList() {
        return itemArrayList;
    }

    public ArrayList<Drawable> getItemDrawableArrayList() {
        return itemDrawableArrayList;
    }

    public void setItemDrawableArrayList(ArrayList<Drawable> itemDrawableArrayList) {
        this.itemDrawableArrayList = itemDrawableArrayList;
    }
}
