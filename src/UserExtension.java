import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.stream.Stream;

class UserExtension {

    public static File userData = new File(".\\data\\User.json");
    private static final Scanner scanner = new Scanner(System.in);
    private static final ObjectMapper mapper = new ObjectMapper();

    public static User createNewUser() throws IOException {
        User user = new User();
        Stream.generate(() -> "*").limit(70).forEach(System.out::print);
        System.out.println();
        System.out.println("Oi, eu sou a Stella, estou aqui para te ajudar a gastar menos energia, \n" +
                "assim poupando seu bolso e ajudando a salvar o planeta :)");
        System.out.print("Pra começar eu gostaria de saber o seu nome: ");
        user.setName(scanner.nextLine());
        System.out.print("E agora a sua idade: ");
        String age = scanner.nextLine();
        while (!SystemExtension.isInteger(age, 10)) {
            System.out.print("Por favor coloque um número valido para idade: ");
            age = scanner.nextLine();
        }
        user.setAge(Integer.parseInt(age));
        user.setState(1);
        System.out.println("Tudo certo, agora iniciando...");
        Stream.generate(() -> "*").limit(70).forEach(System.out::print);
        System.out.println();
        mapper.writeValue(userData, user);
        return user;
    }

    public static boolean createUserDataFile() throws IOException {
        return userData.getParentFile().mkdirs() && userData.createNewFile();
    }

    public static User readUserFromFile() throws IOException {
        return mapper.readValue(userData, User.class);
    }
}