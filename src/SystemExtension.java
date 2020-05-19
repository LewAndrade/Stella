import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class SystemExtension {
    public static void stop(){
        System.exit(0);
    }

    public static boolean isFileEmpty(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
        return br.readLine() == null && file.length() == 0;
    }

    public static boolean isInteger(String s, int radix) {
        Scanner sc = new Scanner(s.trim());
        if(!sc.hasNextInt(radix)) return false;
        // we know it starts with a valid int, now make sure
        // there's nothing left!
        sc.nextInt(radix);
        return !sc.hasNext();
    }

}
