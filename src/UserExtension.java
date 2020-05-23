import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.stream.Stream;

class UserExtension {

    public static File userData = new File(System.getProperty("user.home") + "\\Stella\\User.json");
    private static final Scanner scanner = new Scanner(System.in);
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void setUserName(User user) {
        user.setName(scanner.nextLine().strip());
    }

    private static void setUserAge(User user) {
        String age = scanner.nextLine().strip();
        do {
            if (StellaExtension.isValidAgeFormat(age)) {
                user.setAge(Integer.parseInt(age));
                return;
            }
            StellaExtension.Ui.Error.invalidAgeFormatError();
            age = scanner.nextLine().strip();
        } while (!StellaExtension.isValidAgeFormat(age));
    }

    public static User createNewUser() throws IOException {
        User user = new User();
        StellaExtension.Ui.headerLine();
        System.out.println("Oi, eu sou a Stella, estou aqui para te ajudar a gastar menos energia, \n" +
                "assim poupando seu bolso e ajudando a salvar o planeta :)");
        System.out.print("Pra começar eu quero de saber o seu nome: ");
        setUserName(user);
        System.out.print("E agora a sua idade: ");
        setUserAge(user);
        System.out.println("Tudo certo, agora iniciando...");
        mapper.writeValue(userData, user);
        return user;
    }

    public static boolean createUserDataFile() throws IOException {
        return userData.getParentFile().mkdirs() && userData.createNewFile();
    }

    public static User readUserFromFile() throws IOException {
        return mapper.readValue(userData, User.class);
    }

    public static void writeUserInFile(User user) throws IOException {
        mapper.writeValue(userData, user);
    }
}