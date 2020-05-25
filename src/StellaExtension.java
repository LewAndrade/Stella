import java.text.DecimalFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StellaExtension {

    public static boolean isInteger(String string) {
        Scanner sc = new Scanner(string.trim());
        if (!sc.hasNextInt(10)) return false;
        sc.nextInt(10);
        return !sc.hasNext();
    }

    public static boolean isNumeric(String string) {
        try {
            Double.parseDouble(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidNameFormat(String string) {
        return string.length() <= 32 && !string.isEmpty();
    }

    public static boolean isPositiveDouble(String string) {
        if (isNumeric(string)) {
            double value = Double.parseDouble(string);
            return value > 0;
        }
        return false;
    }

    public static boolean isValidHourFormat(String string) {
        if (isNumeric(string)) {
            double value = Double.parseDouble(string);
            return !(value > 24) && !(value < 0);
        }
        return false;
    }

    public static boolean isValidAgeFormat(String string) {
        if (isNumeric(string)) {
            if (isInteger(string)) {
                double value = Double.parseDouble(string);
                return value > 0 && value < 120;
            } else {
                return false;
            }
        }
        return false;
    }

    public static TimeFormat getTimeFormat(String string) {
        List<String> hourFormats = Arrays.asList("horas", "hora", "h", "hr", "hrs", "em horas");
        List<String> minuteFormats = Arrays.asList("minutos", "minuto", "min", "m", "em minutos");
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
        private static final List<String> continueList = Arrays.asList("sim", "si", "s", "yes", "ye", "y", "zim", "yup", "ja",
                "uhum", "ss", "yip", "yabadabadoo", "okiedokie", "yeet", "okay", "okie", "ok");

        private static final Scanner scanner = new Scanner(System.in);

        public static void logo() {
            System.out.println(
                    "_____/\\\\\\\\\\\\\\\\\\\\\\_________________________________/\\\\\\\\\\\\_____/\\\\\\\\\\\\___________________        \n" +
                    " ___/\\\\\\/////////\\\\\\______________________________\\////\\\\\\____\\////\\\\\\___________________       \n" +
                    "  __\\//\\\\\\______\\///______/\\\\\\________________________\\/\\\\\\_______\\/\\\\\\___________________      \n" +
                    "   ___\\////\\\\\\__________/\\\\\\\\\\\\\\\\\\\\\\_____/\\\\\\\\\\\\\\\\_____\\/\\\\\\_______\\/\\\\\\_____/\\\\\\\\\\\\\\\\\\____     \n" +
                    "    ______\\////\\\\\\______\\////\\\\\\////____/\\\\\\/////\\\\\\____\\/\\\\\\_______\\/\\\\\\____\\////////\\\\\\___    \n" +
                    "     _________\\////\\\\\\______\\/\\\\\\_______/\\\\\\\\\\\\\\\\\\\\\\_____\\/\\\\\\_______\\/\\\\\\______/\\\\\\\\\\\\\\\\\\\\__   \n" +
                    "      __/\\\\\\______\\//\\\\\\_____\\/\\\\\\_/\\\\__\\//\\\\///////______\\/\\\\\\_______\\/\\\\\\_____/\\\\\\/////\\\\\\__  \n" +
                    "       _\\///\\\\\\\\\\\\\\\\\\\\\\/______\\//\\\\\\\\\\____\\//\\\\\\\\\\\\\\\\\\\\__/\\\\\\\\\\\\\\\\\\__/\\\\\\\\\\\\\\\\\\_\\//\\\\\\\\\\\\\\\\/\\\\_ \n" +
                    "        ___\\///////////_________\\/////______\\//////////__\\/////////__\\/////////___\\////////\\//__"
            );
        }

        public static void greetings(String username) {
            System.out.println("\nOi " + username + "!!!");

        }

        public static Option optionsMenu() {
            System.out.print("\n--- O que você vai fazer? \n" +
                    "╔═════════════════════════════════╗\n" +
                    "║[1] Adicionar um aparelho        ║\n" +
                    "║[2] Editar um aparelho           ║\n" +
                    "║[3] Deletar um aparelho          ║\n" +
                    "║[4] Consultar consumo de energia ║\n" +
                    "║[5] Descobrir como gastar menos  ║\n" +
                    "║[6] Alterar dados pessoais       ║\n" +
                    "║[0] Sair                         ║\n" +
                    "╚═════════════════════════════════╝\n");
            System.out.print("Sua escolha: ");
            String choiceBuffer = scanner.nextLine().trim().toLowerCase();

            do {
                if (isInteger(choiceBuffer)) {
                    int choice = Integer.parseInt(choiceBuffer);
                    if (Option.indexValues().contains(Integer.parseInt(choiceBuffer))) {
                        return Option.fromInt(choice);
                    }
                }
                System.out.print("\nDesculpa, mas acho que você não colocou uma das opções acima, tenta de novo? \n" +
                        "Sua escolha: ");
                choiceBuffer = scanner.nextLine().trim().toLowerCase();

            } while (true);
        }

        public static int selectOption(int numberOfOptions) {
            System.out.print("Sua escolha: ");
            String choiceBuffer = scanner.nextLine().trim();
            do {
                if (StellaExtension.isInteger(choiceBuffer)) {
                    int choice = Integer.parseInt(choiceBuffer);
                    if (choice >= 0 && choice <= numberOfOptions)
                        return choice;
                }
                StellaExtension.Ui.Error.invalidNumberFormatError();
                choiceBuffer = scanner.nextLine().trim();
            } while (true);
        }

        public static void farewell() {
            System.out.println("\nPoxa, mas ja vai? :(\n" +
                    "...tudo bem então...\n" +
                    "Saindo.....\n" +
                    "Até mais :)");
        }

        public static boolean getConsent() {
            System.out.print("\nDeseja mesmo fazer isso?\n" +
                    "[sim | nao]: ");
            return continueList.contains(scanner.nextLine().trim().toLowerCase());
        }

        public static class Error {
            public static void invalidHourFormatError() {
                System.out.print("\nDesculpa, mas parece que você não escreveu um número válido, tenta de novo vai... \n" +
                        "Lembra que só tem 24 horas em um dia, não pode passar disso, e nem ser menor que 0 ok? \n" +
                        "--- Horas em uso (h) --- : ");
            }

            public static void invalidMinuteFormatError() {
                System.out.print("\nDesculpa, mas parece que você não escreveu um número válido, tenta de novo vai... \n" +
                        "Lembra que só tem 1440 minutos em um dia, não pode passar disso, e nem ser menor que 0 ok? \n" +
                        "--- Minutos em uso (h) --- : ");
            }

            public static void invalidNumberFormatError() {
                System.out.print("\nPuts, parece que você não escreveu um número válido, tenta de novo vai...\n" +
                        "Sua escolha: ");
            }

            public static void invalidNameFormatError() {
                System.out.print("\nOpa, eu acho que você não colocou um nome válido. \n" +
                        "Tenta de novo por favor? \n" +
                        "--- Nome --- : ");
            }

            public static void invalidPowerFormatError() {
                System.out.print("\nDesculpa, mas você não escreveu um numero válido pra potencia... \n" +
                        "--- Potência (W) --- : ");
            }

            public static void invalidTimeFormatError() {
                System.out.print("\nPera não entendi, você quis dizer horas ou minutos? : ");
            }

            public static void invalidAgeFormatError() {
                System.out.print("\nEi, esa não é uma idade valida: ");

            }

            public static void emptyDeviceList() {
                System.out.println("\nPoxa, você não tem nenhum aparelho cadastrado,\n" +
                        "volta aqui depois que você tiver adicionado pelo menos um.");
            }

            public static void emptyOrZeroedList() {
                System.out.println("\nEi me diz, você não tem nenhum aparelho ou seus aparelhos não gastam nada?\n" +
                        "Volta aqui depois que você tiver adicionado pelo menos um..");
            }

            public static void unableToCreateUserFile() {
                System.out.println("\n AIAIAI,aconteceu um ERRO :( Eu não consegui salvar os dados de usuario, \n" +
                        "se isso acontecer de novo, tenta me reiniciar como administrador.");
            }

            public static void unableToReadUserFile() {
                System.out.println("\n AIAIAI, aconteceu um ERRO :( Eu não consegui ler os dados de usuario, \n" +
                        "se isso acontecer de novo, tenta me reiniciar como administrador.");
            }
        }

        public static String getDoubleString(double value) {
            return new DecimalFormat("#.##").format(value);
        }
    }
}

enum Option {
    ADD_DEVICE(1),
    EDIT_DEVICE(2),
    DELETE_DEVICE(3),
    CHECK_CONSUMPTION(4),
    CONSUMPTION_GUIDE(5),
    EDIT_PROFILE(6),
    EXIT(0);

    final int index;

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