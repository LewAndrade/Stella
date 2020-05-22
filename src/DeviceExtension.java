import java.util.Scanner;

public class DeviceExtension {
    private static final Scanner scanner = new Scanner(System.in);

    public static void setDeviceName(Device device) {
        System.out.print("Qual vai ser o nome do aparelho? \n" +
                "Dando um nome pro aparelho vai ficar fácil de saber o que é o que no final \n" +
                "--- Nome --- : ");
        var name = scanner.nextLine().strip();
        do {
            if (StellaExtension.isValidNameFormat(name)) device.setName(name);
            StellaExtension.Ui.Error.invalidNameFormatError();
            name = scanner.nextLine().strip();
        } while (!StellaExtension.isValidNameFormat(name));
    }

    public static void setDevicePower(Device device) {
        System.out.print("Qual é a potência do aparelho? \n" +
                "A potencia é medida em watts (W), você pode achar ela na caixa ou no manual, \n" +
                "mas se você não conseguir encontrar o google te uma ajudinha, tenho certeza ;)\n" +
                "--- Potência (W) --- : ");
        var power = scanner.nextLine().strip();
        do {
            if (StellaExtension.isValidPowerFormat(power)) device.setPower(Double.parseDouble(power));
        } while (!StellaExtension.isValidPowerFormat(power));
    }

    public static void setDeviceHourUsage(Device device) {
        System.out.print("Ufa... Quase la, agora só falta me dizer quanto tempo o aparelho é usado,\n" +
                "Você quer me dizer em horas ou em minutos? : ");

        var formatChoice = StellaExtension.getTimeFormat(scanner.nextLine().strip().toLowerCase());
        do {
            switch (formatChoice) {
                case HOUR -> {
                    System.out.print("Certo, e por quantas horas o aparelho é usado por dia? : ");
                    var hours = scanner.nextLine().strip();
                    do {
                        if (StellaExtension.isValidHourFormat(hours))
                            device.setHoursPerDay(Double.parseDouble(hours));
                        StellaExtension.Ui.Error.invalidHourFormatError();
                        hours = scanner.nextLine().strip();
                    } while (!StellaExtension.isValidHourFormat(hours));
                    return;
                }
                case MINUTE -> {
                    System.out.print("Certo, e por quantos minutos o aparelho é usado por dia? : ");
                    var minutes = scanner.nextLine().strip();
                    do {
                        if (StellaExtension.isValidMinuteFormat(minutes))
                            device.setHoursPerDay(Double.parseDouble(minutes) / 60);
                        StellaExtension.Ui.Error.invalidMinuteFormatError();
                        minutes = scanner.nextLine().strip();
                    } while (!StellaExtension.isValidMinuteFormat(minutes));
                    return;
                }
                case NULL -> {
                    StellaExtension.Ui.Error.invalidTimeFormatError();
                    formatChoice = StellaExtension.getTimeFormat(scanner.nextLine().strip().toLowerCase());
                }
            }
        } while (formatChoice == TimeFormat.NULL);
    }

    public static Device createNewDevice() {
        var device = new Device();
        setDeviceName(device);
        setDevicePower(device);
        setDeviceHourUsage(device);
        return device;
    }
}