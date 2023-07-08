package ca.buckleupinc.it.smartaqua;

public class SmartAquaQualityData {
    String reading_TDS, status_TDS;

    public SmartAquaQualityData() {
    }

    public SmartAquaQualityData(String reading_TDS, String status_TDS) {
        this.reading_TDS = reading_TDS;
        this.status_TDS = status_TDS;
    }

    public String getReading_TDS() {
        return reading_TDS;
    }

    public void setReading_TDS(String reading_TDS) {
        this.reading_TDS = reading_TDS;
    }

    public String getStatus_TDS() {
        return status_TDS;
    }

    public void setStatus_TDS(String status_TDS) {
        this.status_TDS = status_TDS;
    }
}
