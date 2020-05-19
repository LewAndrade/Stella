import java.io.*;
import java.util.Scanner;

public class Stella {

    public static void main(String[] args) throws IOException {
        User user;

        if (UserExtension.userData.exists()) {
            if (StellaExtension.isFileEmpty(UserExtension.userData)) {
                user = UserExtension.createNewUser();
            } else {
                user = UserExtension.readUserFromFile();
            }
            mainMenu(user);
        } else {
            if (UserExtension.createUserDataFile()) {
                user = UserExtension.createNewUser();
                mainMenu(user);
            } else {
                StellaExtension.stop();
            }
        }
    }

    public static void mainMenu(User user) throws IOException {
        StellaExtension.Ui.greetings(user.getName());
        var choice = StellaExtension.Ui.optionsMenu();
        while (choice != Options.EXIT) {
            switch (choice) {
                case ADD:
                    user.addDevice(DeviceExtension.createNewDevice());
                    break;
                case DELETE:
                    break;
                case CHECK:
                    break;
                case GUIDE:
                    break;
                case DETAILS:
                    break;
                case EDIT:
                    break;
            }
            choice = StellaExtension.Ui.optionsMenu();
        }
        StellaExtension.Ui.farewell();
        UserExtension.writeUserInFile(user);
    }
}