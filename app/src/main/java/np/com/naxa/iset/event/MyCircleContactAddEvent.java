package np.com.naxa.iset.event;

import np.com.naxa.iset.mycircle.ContactModel;

public class MyCircleContactAddEvent {

    public static class MyCircleContactAddClick{
        private ContactModel contactModel;

        public MyCircleContactAddClick(ContactModel contactModel) {
            this.contactModel = contactModel;
        }


        public ContactModel getContactModel() {
            return contactModel;
        }

        public void setContactModel(ContactModel contactModel) {
            this.contactModel = contactModel;
        }
    }

}
