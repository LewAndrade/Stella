import java.util.Scanner;

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
}