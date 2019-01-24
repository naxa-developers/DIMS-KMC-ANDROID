package np.com.naxa.iset.event;

import np.com.naxa.iset.mycircle.ContactModel;
import np.com.naxa.iset.mycircle.MyCircleContactListData;

public class MyCircleContactAddEvent {

    public static class MyCircleContactAddClick{
        private MyCircleContactListData myCircleContactListData;
        private boolean addToCircle;

        public MyCircleContactAddClick(MyCircleContactListData myCircleContactListData, boolean addToCircle) {
            this.myCircleContactListData = myCircleContactListData;
            this.addToCircle = addToCircle;
        }


        public MyCircleContactListData getContactModel() {
            return myCircleContactListData;
        }

        public void setContactModel(MyCircleContactListData myCircleContactListData) {
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
