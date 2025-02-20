package main.java.device;

public class Device {
    private String name;
    private double power;
    private double dailyUsage;
    private double energyConsumption;

    public Device() {
    }

    public Device(String name, double power, double dailyUsage) {
        this.name = name;
        this.power = power;
        this.dailyUsage = dailyUsage;
        this.energyConsumption = (power * dailyUsage) / 1000;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
        this.energyConsumption = (power * dailyUsage) / 1000;
    }

    public double getDailyUsage() {
        return dailyUsage;
    }

    public void setDailyUsage(double dailyUsage) {
        this.dailyUsage = dailyUsage;
        this.energyConsumption = (power * dailyUsage) / 1000;
    }

    public double getEnergyConsumption() {
        return energyConsumption;
    }
}
