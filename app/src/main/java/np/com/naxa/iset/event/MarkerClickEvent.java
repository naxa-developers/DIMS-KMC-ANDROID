package np.com.naxa.iset.event;

public class MarkerClickEvent {

    public static class MarkerItemClick{
        private String markerProperties;

        public MarkerItemClick(String markerProperties) {
            this.markerProperties = markerProperties;
        }

        public String getMarkerProperties() {
            return markerProperties;
        }
    }
}
