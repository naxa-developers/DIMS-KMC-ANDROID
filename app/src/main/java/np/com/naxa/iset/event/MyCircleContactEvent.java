package np.com.naxa.iset.event;

import np.com.naxa.iset.mycircle.ContactModel;
import np.com.naxa.iset.mycircle.MyCircleContactListData;

public class MyCircleContactEvent {

    public static class MyCircleContactAddClick{
        private ContactModel myCircleContactListData;
        private boolean addToCircle;

        public MyCircleContactAddClick(ContactModel myCircleContactListData, boolean addToCircle) {
            this.myCircleContactListData = myCircleContactListData;
            this.addToCircle = addToCircle;
        }


        public ContactModel getContactModel() {
            return myCircleContactListData;
        }

        public void setContactModel(ContactModel myCircleContactListData) {
            this.myCircleContactListData = myCircleContactListData;
        }

        public boolean isAddToCircle() {
            return addToCircle;
        }

        public void setAddToCircle(boolean addToCircle) {
            this.addToCircle = addToCircle;
        }
    }

    public static class MyCircleContactDialogCloseClick{

        public MyCircleContactDialogCloseClick() {
        }
    }


    public static class MyCircleContactRemoveFromListeClick{
        public int position;
        private ContactModel myCircleContactListData;

        public MyCircleContactRemoveFromListeClick(ContactModel myCircleContactListData, int position) {
            this.position = position;
            this.myCircleContactListData = myCircleContactListData;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public ContactModel getMyCircleContactListData() {
            return myCircleContactListData;
        }

        public void setMyCircleContactListData(ContactModel myCircleContactListData) {
            this.myCircleContactListData = myCircleContactListData;
        }
    }


}
