public class Stella {

    public static void main(String[] args) {
        User user = initializeUser();
        if (user != null) mainMenu(user);
    }

    private static User initializeUser() {
        if (UserExtension.createUserFile() || UserExtension.isUserFileEmpty()) {
            return UserExtension.createUserData();
        } else {
            return UserExtension.loadUserData();
        }
    }

    private static void mainMenu(User user) {
        StellaExtension.Ui.logo();
        StellaExtension.Ui.greetings(user.getName());
        Option choice = StellaExtension.Ui.optionsMenu();
        while (choice != Option.EXIT) {
            switch (choice) {
                case ADD_DEVICE:
                    DeviceExtension.createNewDevice(user);
                    break;
                case EDIT_DEVICE:
                    DeviceExtension.editDevice(user);
                    break;
                case DELETE_DEVICE:
                    DeviceExtension.deleteDevice(user);
                    break;
                case CHECK_CONSUMPTION:
                    UserExtension.getEnergyConsumption(user);
                    break;
                case CONSUMPTION_GUIDE:
                    UserExtension.getConsumptionGuide(user);
                    break;
                case EDIT_PROFILE:
                    UserExtension.editUserData(user);
                    break;
            }
            choice = StellaExtension.Ui.optionsMenu();
        }
        StellaExtension.Ui.farewell();
    }
}