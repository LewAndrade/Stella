import java.io.*;

public class Stella {

    public static void main(String[] args) throws IOException {
        User user;

        if (UserExtension.userData.exists()) {
            if (StellaExtension.isFileEmpty(UserExtension.userData)) {
                user = UserExtension.createNewUser();
            } else {
                user = UserExtension.readUserFromFile();
            }
        } else {
            UserExtension.createUserDataFile();
            user = UserExtension.createNewUser();
        }
        mainMenu(user);
    }

    public static void mainMenu(User user) throws IOException {
        StellaExtension.Ui.greetings(user.getName());
        var choice = StellaExtension.Ui.optionsMenu();
        while (choice != Option.EXIT) {
            switch (choice) {
                case ADD_DEVICE:
                    user.addDevice(DeviceExtension.createNewDevice());
                    UserExtension.writeUserInFile(user);
                    break;
                case EDIT_DEVICE:
                    DeviceExtension.listDevices(user.getDevices());
                    break;
                case DELETE_DEVICE:
                    break;
                case CONSUMPTION_GUIDE:
                    break;
                case CHECK_CONSUMPTION:
                    break;
                case EDIT_PROFILE:
                    break;
            }
            choice = StellaExtension.Ui.optionsMenu();
        }
        StellaExtension.Ui.farewell();
    }
}