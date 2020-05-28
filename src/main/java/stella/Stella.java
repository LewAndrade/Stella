package main.java.stella;

import main.java.device.DeviceHandler;
import main.java.resource.Option;
import main.java.resource.Ui;
import main.java.user.User;
import main.java.user.UserHandler;

public class Stella {

    public static void main(String[] args) {
        User user = initializeUser();
        if (user != null) mainMenu(user);
    }

    private static User initializeUser() {
        if (UserHandler.createUserFile() || UserHandler.isUserFileEmpty()) {
            return UserHandler.createUserData();
        } else {
            return UserHandler.loadUserData();
        }
    }

    private static void mainMenu(User user) {
        Ui.Out.logo();
        Ui.Out.greetings(user.getName());
        Option choice = Ui.In.optionsMenu();
        while (choice != Option.EXIT) {
            switch (choice) {
                case ADD_DEVICE:
                    DeviceHandler.createNewDevice(user);
                    break;
                case EDIT_DEVICE:
                    DeviceHandler.editDevice(user);
                    break;
                case DELETE_DEVICE:
                    DeviceHandler.deleteDevice(user);
                    break;
                case CHECK_CONSUMPTION:
                    UserHandler.getEnergyConsumption(user);
                    break;
                case CONSUMPTION_GUIDE:
                    UserHandler.getConsumptionGuide(user);
                    break;
                case EDIT_PROFILE:
                    UserHandler.editUserData(user);
                    break;
            }
            choice = Ui.In.optionsMenu();
        }
        Ui.Out.farewell();
    }
}