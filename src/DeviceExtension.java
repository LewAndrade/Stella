import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class DeviceExtension {
    private static final Scanner scanner = new Scanner(System.in);

    public static Device createNewDevice() {

        System.out.print("Okay :)\n" +
                "Qual vai ser o nome do aparelho? \n" +
                "Dando um nome pro aparelho vai ficar fácil de saber o que é o que no final \n" +
                "--- Nome --- : ");
        String name = scanner.nextLine().strip();

        System.out.print("Qual é a potência do aparelho? \n" +
                "A potencia é medida em watts (W), você pode achar ela na caixa ou no manual, \n" +
                "mas se você não conseguir encontrar o google te uma ajudinha, tenho certeza ;)\n" +
                "--- Potência (W) --- : ");
        String powerBuffer = scanner.nextLine().strip().toLowerCase();
        while (!StellaExtension.isInteger(powerBuffer, 10)) {
            System.out.print("Desculpa, mas parece que o que você escreveu não é um número válido, tenta de novo vai. \n" +
                    "--- Potência (W) --- : ");
            powerBuffer = scanner.nextLine().strip().toLowerCase();
        }
        double power = (Integer.parseInt(powerBuffer));

        System.out.print("Ufa... Quase la, agora só falta me dizer quanto tempo o aparelho é usado,\n" +
                "Você quer me dizer em horas ou em minutos? : ");
        List<String> hourFormats = Arrays.asList("horas", "hora", "h", "hr", "em horas");
        List<String> minutesFormats = Arrays.asList("minutos", "minuto", "min", "m", "em minutos");
        String formatChoice = scanner.nextLine().strip().toLowerCase().strip().toLowerCase();
        while (!hourFormats.contains(formatChoice) && !minutesFormats.contains(formatChoice)) {
            System.out.print("Desculpa não entendi, você quis dizer horas ou minutos? : ");
            formatChoice = scanner.nextLine().strip().toLowerCase();
        }
        double hoursPerDay = 0;

        if (hourFormats.contains(formatChoice)) {
            System.out.print("Certo, e por quantas horas o aparelho é usado por dia? : ");
            String hourBuffer = scanner.nextLine().strip().toLowerCase(); //TODO: Create SystemExtension method for integer checking and attribution
            while (!StellaExtension.isNumeric(hourBuffer)) {
                System.out.print("Desculpa, mas parece que o que você escreveu não é um número válido, tenta de novo vai. \n" +
                        "--- Horas em uso (h) --- : ");
                hourBuffer = scanner.nextLine().strip().toLowerCase();
            }
            hoursPerDay = (Double.parseDouble(hourBuffer));

        } else if (minutesFormats.contains(formatChoice)) {
            System.out.print("Certo, e por quantos minutos o aparelho é usado por dia? : ");
            String minuteBuffer = scanner.nextLine().strip().toLowerCase(); //TODO: Create SystemExtension method for integer checking and attribution
            while (!StellaExtension.isNumeric(minuteBuffer)) {
                System.out.print("Desculpa, mas parece que o que você escreveu não é um número válido, tenta de novo vai. \n" +
                        "--- Minutos em uso (min) --- : ");
                minuteBuffer = scanner.nextLine().strip().toLowerCase();
            }
            hoursPerDay = (Double.parseDouble(minuteBuffer) / 60);
        }

        return new Device(name, power, hoursPerDay);
    }
}
