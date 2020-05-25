import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Stream;

public class DeviceExtension {
    private static final Scanner scanner = new Scanner(System.in);

    public static void createNewDevice(User user) {
        System.out.println("\nOkay :) Vamos adicionar um aparelho então.");
        user.addDevice(new Device(setDeviceName(), setDevicePower(), setDeviceDailyUsage()));
        UserExtension.saveUserData(user);
    }

    public static void editDevice(User user) {
        ArrayList<Device> userDevices = user.getDevices();
        if (!listDevices(userDevices)) return;
        userDevices = user.getDevices();
        do {
            int index = StellaExtension.Ui.selectOption(userDevices.size());
            if (index == 0) {
                return;
            } else {
                Device device = userDevices.get(index - 1);
                System.out.println(String.format("\nEditando: %s, o que deseja mudar?\n" +
                        "[1] Nome\n" +
                        "[2] Potência\n" +
                        "[3] Tempo de uso\n" +
                        "[0] Voltar", device.getName()));

                int item = StellaExtension.Ui.selectOption(3);
                switch (item) {
                    case 1:
                        device.setName(setDeviceName());
                        break;
                    case 2:
                        device.setPower(setDevicePower());
                        break;
                    case 3:
                        device.setDailyUsage(setDeviceDailyUsage());
                        break;
                    case 0:
                        UserExtension.saveUserData(user);
                        break;
                }
            }
            UserExtension.saveUserData(user);
            listDevices(userDevices);

        } while (true);
    }

    public static void deleteDevice(User user) {
        ArrayList<Device> userDevices = user.getDevices();
        if (!listDevices(userDevices)) return;
        int index = StellaExtension.Ui.selectOption(userDevices.size());
        if (index != 0) {
            if (StellaExtension.Ui.getConsent()) {
                user.getDevices().remove(index - 1);
                UserExtension.saveUserData(user);
            }
        }
    }

    private static String setDeviceName() {
        System.out.print("\nQual vai ser o nome do aparelho? \n" +
                "Dando um nome pro aparelho vai ficar fácil de saber o que é o que no final \n" +
                "--- Nome --- : ");
        String name = scanner.nextLine().trim();
        do {
            if (StellaExtension.isValidNameFormat(name)) {
                return name;
            }
            StellaExtension.Ui.Error.invalidNameFormatError();
            name = scanner.nextLine().trim();
        } while (true);
    }

    private static Double setDevicePower() {
        System.out.print("\nQual é a potência do aparelho? \n" +
                "A potencia é medida em watts (W), você pode achar ela na caixa ou no manual, \n" +
                "mas se você não conseguir encontrar o google te uma ajudinha, tenho certeza ;)\n" +
                "--- Potência (W) --- : ");
        String power = scanner.nextLine().trim();
        do {
            if (StellaExtension.isPositiveDouble(power)) {
                return Double.parseDouble(power);
            }
            StellaExtension.Ui.Error.invalidPowerFormatError();
            power = scanner.nextLine().trim();
        } while (true);
    }

    private static Double setDeviceDailyUsage() {
        System.out.print("\nOkay então me diz, por quanto tempo o aparelho é usado,\n" +
                "Você quer me dizer em horas ou em minutos? : ");

        TimeFormat formatChoice = StellaExtension.getTimeFormat(scanner.nextLine().trim().toLowerCase());
        do {
            switch (formatChoice) {
                case HOUR:
                    System.out.print("Certo, e por quantas horas o aparelho é usado por dia? : ");
                    do {
                        String hours = scanner.nextLine().trim();
                        if (StellaExtension.isValidHourFormat(hours)) {
                            return Double.parseDouble(hours);
                        }
                        StellaExtension.Ui.Error.invalidHourFormatError();
                    } while (true);
                case MINUTE:
                    System.out.print("Certo, e por quantos minutos o aparelho é usado por dia? : ");
                    do {
                        String minutes = scanner.nextLine().trim();
                        if (StellaExtension.isValidMinuteFormat(minutes)) {
                            return Double.parseDouble(minutes) / 60;
                        }
                        StellaExtension.Ui.Error.invalidMinuteFormatError();
                    } while (true);
                case NULL:
                    StellaExtension.Ui.Error.invalidTimeFormatError();
                    formatChoice = StellaExtension.getTimeFormat(scanner.nextLine().trim().toLowerCase());
                    break;
            }
        } while (true);
    }

    private static boolean listDevices(ArrayList<Device> devices) {
        if (devices.isEmpty()) {
            StellaExtension.Ui.Error.emptyDeviceList();
            return false;
        }
        int biggestName = 0;
        int biggestPower = 0;
        int biggestConsumption = 0;
        int biggestUsageTime = 0;
        for (Device device : devices) {
            int nameLength = device.getName().length();
            int powerLength = StellaExtension.Ui.getDoubleString(device.getPower()).length();
            int consumptionLength = String.valueOf((int) device.getEnergyConsumption()).length() + 3;
            int usageTime = getUsageTime(device).length();
            if (nameLength > biggestName) biggestName = nameLength;
            if (powerLength > biggestPower) biggestPower = powerLength;
            if (consumptionLength > biggestConsumption) biggestConsumption = consumptionLength;
            if (usageTime > biggestUsageTime) biggestUsageTime = usageTime;
        }

        System.out.println("\nOkay :) Aqui estão seus aparelhos:");
        ArrayList<String> outputList = new ArrayList<>();

        for (int i = 0; i < devices.size(); i++) {
            Device device = devices.get(i);

            outputList.add(String.format("[%" + String.valueOf(devices.size()).length() + "s] " +
                            "║ Nome: %-" + biggestName + "s " +
                            "║ Potência: %" + biggestPower + "s W " +
                            "║ Tempo de uso: %-" + biggestUsageTime + "s " +
                            "║ Consumo de energia: %" + biggestConsumption + ".2f kWh ║",
                    i + 1,
                    device.getName(),
                    StellaExtension.Ui.getDoubleString(device.getPower()),
                    getUsageTime(device),
                    device.getEnergyConsumption()));
        }
        int menuSize = String.valueOf(devices.size()).length() + 4;
        int tableWidth = outputList.get(0).length() - 1;

        System.out.print(String.format("%" + (menuSize) + "s", "╔"));
        Stream.generate(() -> "═").limit(tableWidth - (menuSize)).forEach(System.out::print);
        System.out.println("╗");
        outputList.forEach(System.out::println);
        System.out.println(String.format("%-" + tableWidth + "s║", String.format("[%" + (menuSize - 4) + "s] ║ Voltar...", "0")));
        System.out.print(String.format("%" + (menuSize) + "s", "╚"));
        Stream.generate(() -> "═").limit(tableWidth - (menuSize)).forEach(System.out::print);
        System.out.println("╝");
        return true;
    }

    private static String getUsageTime(Device device) {
        String usageTime;
        int hours = (int) device.getDailyUsage();
        int minutes = (int) ((device.getDailyUsage() - hours) * 60);

        if (minutes == 0) if (hours > 1) usageTime = String.format("%s horas", hours);
        else usageTime = hours == 1 ? String.format("%s hora", hours) : "";
        else if (hours > 1) usageTime = String.format("%s horas e ", hours);
        else usageTime = hours == 1 ? String.format("%s hora e ", hours) : "";
        if (minutes > 1) usageTime += String.format("%s minutos", minutes);
        else if (minutes == 1) usageTime += String.format("%s minuto", minutes);
        return usageTime;
    }


}