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
}
