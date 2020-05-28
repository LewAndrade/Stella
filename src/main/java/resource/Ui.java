package main.java.resource;

import main.java.stella.StellaHandler;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Ui {

    public static String getDoubleString(double value) {
        return new DecimalFormat("#.##").format(value);
    }

    public static class In {
        private static final List<String> continueList = Arrays.asList("sim", "si", "s", "yes", "ye", "y", "zim", "yup", "ja",
                "uhum", "ss", "yip", "yabadabadoo", "okiedokie", "yeet", "okay", "okie", "ok");
        private static final Scanner scanner = new Scanner(System.in);

        public static boolean getConsent() {
            System.out.print("\nDeseja mesmo fazer isso?\n" +
                    "[sim | nao]: ");
            return continueList.contains(scanner.nextLine().trim().toLowerCase());
        }

        public static int selectOption(int numberOfOptions) {
            System.out.print("Sua escolha: ");
            String choiceBuffer = scanner.nextLine().trim();
            do {
                if (StellaHandler.isInteger(choiceBuffer)) {
                    int choice = Integer.parseInt(choiceBuffer);
                    if (choice >= 0 && choice <= numberOfOptions)
                        return choice;
                }
                Error.invalidNumberFormatError();
                choiceBuffer = scanner.nextLine().trim();
            } while (true);
        }

        public static Option optionsMenu() {
            System.out.print("\n" +
                    "-----  O que você vai fazer?  -----\n" +
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
            do {
                String choiceBuffer = scanner.nextLine().trim().toLowerCase();
                if (StellaHandler.isInteger(choiceBuffer)) {
                    int choice = Integer.parseInt(choiceBuffer);
                    if (Option.indexValues().contains(Integer.parseInt(choiceBuffer))) {
                        return Option.fromInt(choice);
                    }
                }
                System.out.print("\nDesculpa, mas acho que você não colocou uma das opções acima, tenta de novo? \n" +
                        "Sua escolha: ");

            } while (true);
        }
    }

    public static class Out {

        public static void logo() {
            System.out.println("\n" +
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
            System.out.println("\nOi " + username + "!!! Tudo bem?");

        }

        public static void farewell() {
            System.out.println("\nPoxa, mas ja vai? :(\n" +
                    "...tudo bem então...\n" +
                    "Saindo.....\n" +
                    "Até mais :)");
        }
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
            System.out.println("\nAIAIAI,aconteceu um ERRO :( Eu não consegui salvar os dados de usuario, \n" +
                    "se isso acontecer de novo, tenta me reiniciar como administrador.");
        }

        public static void unableToReadUserFile() {
            System.out.println("\nAIAIAI, aconteceu um ERRO :( Eu não consegui ler os dados de usuario, \n" +
                    "se isso acontecer de novo, tenta me reiniciar como administrador.");
        }
    }
}