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
                    var repeatEdit = true;
                    while (repeatEdit) {
                        DeviceExtension.listDevices(user.getDevices());
                        var editedDevice = DeviceExtension.editDevice(user);
                        UserExtension.writeUserInFile(user);
                        repeatEdit = editedDevice && StellaExtension.Ui.setEditRepeat();
                    }
                    break;
                case DELETE_DEVICE:
                    var repeatDelete = true;
                    while(repeatDelete) {
                        DeviceExtension.listDevices(user.getDevices());
                        var deletedDevice = DeviceExtension.deleteDevice(user);
                        UserExtension.writeUserInFile(user);
                        repeatDelete = deletedDevice && StellaExtension.Ui.setDeleteRepeat();
                    }
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