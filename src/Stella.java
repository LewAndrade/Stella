import java.io.*;

public class Stella {

    public static void main(String[] args) throws IOException {
        User user;

        if (UserExtension.userData.exists()) {
            if (SystemExtension.isFileEmpty(UserExtension.userData)) {
                user = UserExtension.createNewUser();
            } else {
                user = UserExtension.readUserFromFile();
            }
        } else {
            if (UserExtension.createUserDataFile()) {
                user = UserExtension.createNewUser();
            } else {
                SystemExtension.stop();
            }
        }
    }
}
