package np.com.naxa.iset.event;

public class EmergenctContactCallEvent {

    public static class ContactItemClick{
        private String contactNo;

        public ContactItemClick(String contactNo) {
            this.contactNo = contactNo;
        }

        public String getContactNo() {
            return contactNo;
        }
    }
}
