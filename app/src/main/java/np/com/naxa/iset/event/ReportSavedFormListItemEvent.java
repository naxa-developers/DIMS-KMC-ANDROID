package np.com.naxa.iset.event;

import np.com.naxa.iset.database.entity.ReportDetailsEntity;

public class ReportSavedFormListItemEvent {

    public static class ReportSavedFormListItemClick{
       private ReportDetailsEntity reportDetailsEntity;

        public ReportSavedFormListItemClick(ReportDetailsEntity reportDetailsEntity) {
            this.reportDetailsEntity = reportDetailsEntity;
        }

        public ReportDetailsEntity getReportDetailsEntity() {
            return reportDetailsEntity;
        }

        public void setReportDetailsEntity(ReportDetailsEntity reportDetailsEntity) {
            this.reportDetailsEntity = reportDetailsEntity;
        }
    }

    public static class ReportUnverifiedFormListItemClick{
        private ReportDetailsEntity reportDetailsEntity;
        private boolean isNormal;

        public ReportUnverifiedFormListItemClick(ReportDetailsEntity reportDetailsEntity, boolean isNormal) {
            this.reportDetailsEntity = reportDetailsEntity;
            this.isNormal = isNormal;
        }


        public ReportDetailsEntity getReportDetailsEntity() {
            return reportDetailsEntity;
        }

        public void setReportDetailsEntity(ReportDetailsEntity reportDetailsEntity) {
            this.reportDetailsEntity = reportDetailsEntity;
        }

        public boolean getIsNormal() {
            return isNormal;
        }
    }
}
