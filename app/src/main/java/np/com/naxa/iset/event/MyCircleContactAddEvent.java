package np.com.naxa.iset.event;

import np.com.naxa.iset.mycircle.ContactModel;

public class MyCircleContactAddEvent {

    public static class MyCircleContactAddClick{
        private ContactModel contactModel;
        private boolean addToCircle;

        public MyCircleContactAddClick(ContactModel contactModel, boolean addToCircle) {
            this.contactModel = contactModel;
            this.addToCircle = addToCircle;
        }


        public ContactModel getContactModel() {
            return contactModel;
        }

        public void setContactModel(ContactModel contactModel) {
            this.contactModel = contactModel;
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
