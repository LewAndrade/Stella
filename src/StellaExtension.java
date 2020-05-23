import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StellaExtension {
    public static void stop() {
        System.exit(0);
    }

    public static boolean isFileEmpty(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
        return br.readLine() == null && file.length() == 0;
    }

    private static boolean isInteger(String string) {
        Scanner sc = new Scanner(string.trim());
        if (!sc.hasNextInt(10)) return false;
        sc.nextInt(10);
        return !sc.hasNext();
    }

    private static boolean isNumeric(String string) {
        try {
            Double.parseDouble(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidNameFormat(String string) {
        return string.length() <= 32 && (!string.isBlank() || !string.isEmpty());
    }

    public static boolean isValidPowerFormat(String string) {
        return isNumeric(string);
    }

    public static boolean isValidHourFormat(String string) {
        if (isNumeric(string)) {
            var value = Double.parseDouble(string);
            return !(value > 24) && !(value < 0);
        }
        return false;
    }

    public static boolean isValidAgeFormat(String string) {
        if (isNumeric(string)) {
            if (isInteger(string)) {
                var value = Double.parseDouble(string);
                return !(value > 120) && !(value < 0);
            } else {
                return false;
            }
        }
        return false;
    }

    public static TimeFormat getTimeFormat(String string) {
        var hourFormats = Arrays.asList("horas", "hora", "h", "hr", "hrs", "em horas");
        var minuteFormats = Arrays.asList("minutos", "minuto", "min", "m", "em minutos");
        if (hourFormats.contains(string)) return TimeFormat.HOUR;
        else if (minuteFormats.contains(string)) return TimeFormat.MINUTE;
        else return TimeFormat.NULL;
    }

    public static boolean isValidMinuteFormat(String string) {
        if (isNumeric(string)) {
            double value = Double.parseDouble(string);
            return !(value > 1440) && !(value < 0);
        }
        return false;
    }

    public static class Ui {
        private static final Scanner scanner = new Scanner(System.in);

        public static void headerLine() {
            Stream.generate(() -> "*").limit(138).forEach(System.out::print);
            System.out.println();
        }

        public static void greetings(String username) {
            StellaExtension.Ui.headerLine();
            System.out.println("Oi " + username + "!!!");

        }

        public static Option optionsMenu() {
            StellaExtension.Ui.headerLine();
            System.out.println("--- O que deseja fazer? \n" +
                    "[1] Adicionar um aparelho\n" +
                    "[2] Editar um aparelho\n" +
                    "[3] Deletar um aparelho\n" +
                    "[4] Descobrir como gastar menos\n" +
                    "[5] Consultar consumo de energia\n" +
                    "[6] Alterar dados pessoais\n" +
                    "[0] Sair");
            StellaExtension.Ui.headerLine();
            System.out.print("Sua escolha: ");
            var choiceBuffer = scanner.nextLine().strip().toLowerCase();

            do {
                if (isInteger(choiceBuffer)) {
                    int choice = Integer.parseInt(choiceBuffer);
                    if (Option.indexValues().contains(Integer.parseInt(choiceBuffer))) {
                        StellaExtension.Ui.headerLine();
                        return Option.fromInt(choice);
                    }
                }
                System.out.print("Desculpa, mas acho que você não colocou uma das opções acima, tenta de novo? \n" +
                        "Sua escolha: ");
                choiceBuffer = scanner.nextLine().strip().toLowerCase();

            } while (true);
        }

        public static void farewell() {
            System.out.println("Saindo.....\n" +
                    "Até mais :)");
        }

        public static class Error {
            public static void invalidHourFormatError() {
                System.out.print("Desculpa, mas parece que você não escreveu um número válido, tenta de novo vai... \n" +
                        "Lembra que só tem 24 horas em um dia, não pode passar disso, e nem ser menor que 0 ok? \n" +
                        "--- Horas em uso (h) --- : ");
            }

            public static void invalidMinuteFormatError() {
                System.out.print("Desculpa, mas parece que você não escreveu um número válido, tenta de novo vai... \n" +
                        "Lembra que só tem 1440 minutos em um dia, não pode passar disso, e nem ser menor que 0 ok? \n" +
                        "--- Minutos em uso (h) --- : ");
            }

            public static void invalidNumberFormatError() {
                System.out.println("Desculpa, mas parece que você não escreveu um número válido, tenta de novo vai...");
            }

            public static void invalidNameFormatError() {
                System.out.print("Opa, eu acho que você não colocou um nome válido \n" +
                        "Tenta de novo por favor? \n" +
                        "--- Nome --- : ");
            }

            public static void invalidPowerFormatError() {
                System.out.print("Desculpa, mas você não escreveu um numero válido pra potencia... \n" +
                        "--- Potência (W) --- : ");
            }

            public static void invalidTimeFormatError() {
                System.out.print("Desculpa não entendi, você quis dizer horas ou minutos? : ");
            }

            public static void invalidAgeFormatError() {
                System.out.print("Por favor coloque um número valido para idade: ");

            }
        }
    }
}

enum Option {
    ADD_DEVICE(1),
    EDIT_DEVICE(2),
    DELETE_DEVICE(3),
    CONSUMPTION_GUIDE(4),
    CHECK_CONSUMPTION(5),
    EDIT_PROFILE(6),
    EXIT(0);

    int index;

    Option(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    public static ArrayList<Integer> indexValues() {
        return Option.reverseLookup.values().stream().map(c -> c.index).collect(Collectors.toCollection(ArrayList::new));
    }

    public static final Map<Integer, Option> reverseLookup =
            Arrays.stream(Option.values()).collect(Collectors.toMap(Option::getIndex, Function.identity()));

    public static Option fromInt(final int id) {
        return reverseLookup.getOrDefault(id, null);
    }
}

enum TimeFormat {
    HOUR,
    MINUTE,
    NULL
}