package np.com.naxa.iset.emergencynumbers;

import java.util.ArrayList;

/**
 * Created by samir on 01/12/18..
 */

public class SectionEmergencyNoModel {
    private String sectionLabel;
    private ArrayList<String> itemArrayList;

    public SectionEmergencyNoModel(String sectionLabel, ArrayList<String> itemArrayList) {
        this.sectionLabel = sectionLabel;
        this.itemArrayList = itemArrayList;
    }

    public String getSectionLabel() {
        return sectionLabel;
    }

    public ArrayList<String> getItemArrayList() {
        return itemArrayList;
    }


    public static ArrayList<SectionEmergencyNoModel> getEmergencyNoList() {

        String sectionHead = "";

        ArrayList<SectionEmergencyNoModel> sectionEmergencyNoModelArrayList = new ArrayList<SectionEmergencyNoModel>();
        for (int i = 0; i <= 4; i++) {
            ArrayList<String> itemArrayList1 = new ArrayList<String>();
            for (int j = 1; j < 10; j++) {
                String contact_no = "98410102" + i + "" + j;
                itemArrayList1.add(contact_no);

            }
            sectionHead = "Emergency No."+i+" ( " + (itemArrayList1.size()+1)+")" ;

            sectionEmergencyNoModelArrayList.add(new SectionEmergencyNoModel(sectionHead, itemArrayList1));
        }

        return sectionEmergencyNoModelArrayList;
    }
}
