import com.fasterxml.jackson.annotation.JsonIgnore;

public class Device {
    String name;
    double power;
    double hoursPerDay;
    double energyConsumption = (power * hoursPerDay) / 1000;

    public Device(){
    }

    public Device(String name, double power, double hoursPerDay) {
        this.name = name;
        this.power = power;
        this.hoursPerDay = hoursPerDay;
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
    }

    public double getHoursPerDay() {
        return hoursPerDay;
    }

    public void setHoursPerDay(double hoursPerDay) {
        this.hoursPerDay = hoursPerDay;
    }

    public double getEnergyConsumption() {
        return energyConsumption;
    }
}
