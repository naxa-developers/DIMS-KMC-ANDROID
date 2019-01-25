package np.com.naxa.iset.event;

import np.com.naxa.iset.mycircle.ContactModel;
import np.com.naxa.iset.mycircle.MyCircleContactListData;

public class MyCircleContactAddEvent {

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

}
