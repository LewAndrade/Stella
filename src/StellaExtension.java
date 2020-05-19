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

    public static boolean isInteger(String string, int radix) {
        Scanner sc = new Scanner(string.trim());
        if (!sc.hasNextInt(radix)) return false;
        sc.nextInt(radix);
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

    public static class Ui {
        private static final Scanner scanner = new Scanner(System.in);

        public static void headerLine(int size) {
            Stream.generate(() -> "*").limit(size).forEach(System.out::print);
            System.out.println();
        }

        public static void greetings(String username) {
            StellaExtension.Ui.headerLine(70);
            System.out.println("Oi " + username + ", O que deseja fazer?");
            StellaExtension.Ui.headerLine(70);
        }

        public static Options optionsMenu() {
            System.out.print("[1] Adicionar um aparelho\n" +
                    "[2] Deletar um aparelho\n" +
                    "[3] Consultar consumo de energia\n" +
                    "[4] Descobrir como gastar menos\n" +
                    "[5] Ver detalhes completos sobre as seus aparelhos\n" +
                    "[6] Alterar dados pessoais\n" +
                    "[0] Sair\n" +
                    "Sua escolha: ");
            var choiceBuffer = scanner.nextLine().strip().toLowerCase();

            do {
                if (isInteger(choiceBuffer, 10)) {
                    int choice = Integer.parseInt(choiceBuffer);
                    if (Options.indexValues().contains(Integer.parseInt(choiceBuffer))) {
                        StellaExtension.Ui.headerLine(70);
                        return Options.fromInt(choice);
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
    }
}

enum Options {
    ADD(1),
    CHECK(2),
    DELETE(3),
    GUIDE(4),
    DETAILS(5),
    EDIT(6),
    EXIT(0);

    int index;

    Options(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    public static ArrayList<Integer> indexValues() {
        return Options.reverseLookup.values().stream().map(c -> c.index).collect(Collectors.toCollection(ArrayList::new));
    }

    public static final Map<Integer, Options> reverseLookup =
            Arrays.stream(Options.values()).collect(Collectors.toMap(Options::getIndex, Function.identity()));

    public static Options fromInt(final int id) {
        return reverseLookup.getOrDefault(id, null);
    }
}