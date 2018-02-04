package server.flashflood.com.Model;

/**
 * Created by Majedur Rahman on 2/4/2018.
 */

public class SMSItem {
    private String AreaId;
    private String DeviceId;
    private String WaterLevel;
    private String DateTime;

    public SMSItem() {

    }

    public SMSItem(String areaId, String deviceId, String waterLevel, String time) {
        AreaId = areaId;
        DeviceId = deviceId;
        WaterLevel = waterLevel;
        DateTime = time;
    }

    public String getAreaId() {
        return AreaId;
    }

    public void setAreaId(String areaId) {
        AreaId = areaId;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public String getWaterLevel() {
        return WaterLevel;
    }

    public void setWaterLevel(String waterLevel) {
        WaterLevel = waterLevel;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    @Override
    public String toString() {
        return "onReceive: " + AreaId + " " + DeviceId + " " + " " + WaterLevel + " " + DateTime;
    }
}
