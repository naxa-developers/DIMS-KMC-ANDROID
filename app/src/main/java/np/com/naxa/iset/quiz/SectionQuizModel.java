package np.com.naxa.iset.quiz;

import java.util.ArrayList;

/**
 * Created by samir on 01/12/18..
 */

public class SectionQuizModel {
    private String sectionLabel;
    private ArrayList<String> itemArrayList;

    public SectionQuizModel(String sectionLabel, ArrayList<String> itemArrayList) {
        this.sectionLabel = sectionLabel;
        this.itemArrayList = itemArrayList;
    }

    public String getSectionLabel() {
        return sectionLabel;
    }

    public ArrayList<String> getItemArrayList() {
        return itemArrayList;
    }


    public static ArrayList<SectionQuizModel> getQuizList() {

        String sectionHead = "";
        ArrayList<SectionQuizModel> sectionEmergencyNoModelArrayList = new ArrayList<SectionQuizModel>();
        ArrayList<String> itemArrayList1 = new ArrayList<String>();


        itemArrayList1.add("Earthquake");
        itemArrayList1.add("Landslides");
        itemArrayList1.add("Flood");
        itemArrayList1.add("Heat Waves");
        itemArrayList1.add("Sink Waves");
        itemArrayList1.add("Fire");
        itemArrayList1.add("Cold Waves");
        itemArrayList1.add("Forest Fire");
        itemArrayList1.add("Volcano");
        itemArrayList1.add("Thunderstorm");
        itemArrayList1.add("Wind");
        itemArrayList1.add("Heavy Rainfall");

        sectionEmergencyNoModelArrayList.add(new SectionQuizModel(sectionHead, itemArrayList1));

        return sectionEmergencyNoModelArrayList;
    }
}
