import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public class DeviceExtension {
    private static final Scanner scanner = new Scanner(System.in);

    public static String setDeviceName() {
        System.out.print("Qual vai ser o nome do aparelho? \n" +
                "Dando um nome pro aparelho vai ficar fácil de saber o que é o que no final \n" +
                "--- Nome --- : ");
        var name = scanner.nextLine().strip();
        do {
            if (StellaExtension.isValidNameFormat(name)) {
                return name;
            }
            StellaExtension.Ui.Error.invalidNameFormatError();
            name = scanner.nextLine().strip();
        } while (true);
    }

    public static Double setDevicePower() {
        System.out.print("Qual é a potência do aparelho? \n" +
                "A potencia é medida em watts (W), você pode achar ela na caixa ou no manual, \n" +
                "mas se você não conseguir encontrar o google te uma ajudinha, tenho certeza ;)\n" +
                "--- Potência (W) --- : ");
        var power = scanner.nextLine().strip();
        do {
            if (StellaExtension.isValidPowerFormat(power)) {
                return Double.parseDouble(power);
            }
            StellaExtension.Ui.Error.invalidPowerFormatError();
            power = scanner.nextLine().strip();
        } while (true);
    }

    public static Double setDeviceDailyUsage() {
        System.out.print("Ufa... Quase la, agora só falta me dizer quanto tempo o aparelho é usado,\n" +
                "Você quer me dizer em horas ou em minutos? : ");

        var formatChoice = StellaExtension.getTimeFormat(scanner.nextLine().strip().toLowerCase());
        do {
            switch (formatChoice) {
                case HOUR -> {
                    System.out.print("Certo, e por quantas horas o aparelho é usado por dia? : ");
                    var hours = scanner.nextLine().strip();
                    do {
                        if (StellaExtension.isValidHourFormat(hours)) {
                            return Double.parseDouble(hours);
                        }
                        StellaExtension.Ui.Error.invalidHourFormatError();
                        hours = scanner.nextLine().strip();
                    } while (true);
                }
                case MINUTE -> {
                    System.out.print("Certo, e por quantos minutos o aparelho é usado por dia? : ");
                    var minutes = scanner.nextLine().strip();
                    do {
                        if (StellaExtension.isValidMinuteFormat(minutes)) {
                            return Double.parseDouble(minutes) / 60;
                        }
                        StellaExtension.Ui.Error.invalidMinuteFormatError();
                        minutes = scanner.nextLine().strip();
                    } while (true);
                }
                case NULL -> {
                    StellaExtension.Ui.Error.invalidTimeFormatError();
                    formatChoice = StellaExtension.getTimeFormat(scanner.nextLine().strip().toLowerCase());
                }
            }
        } while (true);
    }

    public static Device createNewDevice() {
        return new Device(setDeviceName(), setDevicePower(), setDeviceDailyUsage());
    }

    public static void listDevices(ArrayList<Device> devices) {
        int biggestName = 0;
        int biggestPower = 0;
        int biggestConsumption = 0;
        for (Device device : devices) {
            var nameLength = device.getName().length();
            var powerLength = new DecimalFormat("#.##").format(device.getPower()).length();
            var consumptionLength = String.valueOf((int) device.getEnergyConsumption()).length() + 3;
            if (nameLength > biggestName) biggestName = nameLength;
            if (powerLength > biggestPower) biggestPower = powerLength;
            if (consumptionLength > biggestConsumption) biggestConsumption = consumptionLength;
        }

        System.out.println("--- Okay :) Aqui estão seus aparelhos:");
        var outputList = new ArrayList<String>();

        for (int i = 0; i < devices.size(); i++) {
            var device = devices.get(i);
            String usageTime;
            var hours = (int) device.getDailyUsage();
            var minutes = (int) ((device.getDailyUsage() - hours) * 60);

            if (minutes == 0) if (hours > 1) usageTime = String.format("%s horas", hours);
            else usageTime = hours == 1 ? String.format("%s hora", hours) : "";
            else if (hours > 1) usageTime = String.format("%s horas e ", hours);
            else usageTime = hours == 1 ? String.format("%s hora e ", hours) : "";
            if (minutes > 1) usageTime += String.format("%s minutos", minutes);
            else if (minutes == 1) usageTime += String.format("%s minuto", minutes);

            outputList.add(String.format("[%" + String.valueOf(devices.size()).length() + "s] " +
                            "║ Nome: %-" + biggestName + "s " +
                            "║ Potência: %" + biggestPower + "s W " +
                            "║ Tempo de uso: %-21s " +
                            "║ Consumo de energia: %" + biggestConsumption + ".2f kWh ║",
                    i + 1,
                    device.getName(),
                    new DecimalFormat("#.##").format(device.getPower()),
                    usageTime,
                    device.getEnergyConsumption()));
        }
        var menuSize = String.valueOf(devices.size()).length() + 4;
        var tableWidth = outputList.get(0).length() - 1;

        System.out.print(String.format("%" + (menuSize) + "s", "╔"));
        Stream.generate(() -> "═").limit(tableWidth - (menuSize)).forEach(System.out::print);
        System.out.println("╗");
        outputList.forEach(System.out::println);
        System.out.println(String.format("%-" + tableWidth + "s║", String.format("[%" + (menuSize - 4) + "s] ║ Voltar...", "0")));
        System.out.print(String.format("%" + (menuSize) + "s", "╚"));
        Stream.generate(() -> "═").limit(tableWidth - (menuSize)).forEach(System.out::print);
        System.out.println("╝");
        StellaExtension.Ui.headerLine();
    }
}