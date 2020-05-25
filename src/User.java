import java.util.*;

public class User {
    private String name;
    private int age;
    private double tariff;
    private double cosip;
    private double ece;
    private double pisCofins;
    private ArrayList<Device> devices;
    private Map<String, Double> greenFlag;
    private Map<String, Double> yellowFlag;
    private Map<String, Double> redFlag1;
    private Map<String, Double> redFlag2;

    public User() {
        devices = new ArrayList<>();
    }

    public void setDefaultTariffs() {
        tariff = 0.51559;
        cosip = 9.70;
        ece = 0.0035;
        pisCofins = 1.74;
        greenFlag = new HashMap<>();
        greenFlag.put("low", 1.772);
        greenFlag.put("mid", 15.93);
        greenFlag.put("high", 36.5);
        yellowFlag = new HashMap<>();
        yellowFlag.put("low", 4.68);
        yellowFlag.put("mid", 18.84);
        yellowFlag.put("high", 39.4);
        redFlag1 = new HashMap<>();
        redFlag1.put("low", 9.53);
        redFlag1.put("mid", 23.69);
        redFlag1.put("high", 44.26);
        redFlag2 = new HashMap<>();
        redFlag2.put("low", 13.4);
        redFlag2.put("mid", 27.57);
        redFlag2.put("high", 48.14);
    }

    public void deleteDevices() {
        devices = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getTariff() {
        return tariff;
    }

    public void setTariff(double tariff) {
        this.tariff = tariff;
    }

    public double getPisCofins() {
        return pisCofins;
    }

    public void setPisCofins(double pisCofins) {
        this.pisCofins = pisCofins;
    }

    public double getEce() {
        return ece;
    }

    public void setEce(double ece) {
        this.ece = ece;
    }

    public double getCosip() {
        return cosip;
    }

    public void setCosip(double cosip) {
        this.cosip = cosip;
    }

    public ArrayList<Device> getDevices() {
        return devices;
    }

    public void addDevice(Device device) {
        devices.add(device);
    }

    public Map<String, Double> getGreenFlag() {
        return greenFlag;
    }

    public Map<String, Double> getYellowFlag() {
        return yellowFlag;
    }

    public Map<String, Double> getRedFlag1() {
        return redFlag1;
    }

    public Map<String, Double> getRedFlag2() {
        return redFlag2;
    }
}