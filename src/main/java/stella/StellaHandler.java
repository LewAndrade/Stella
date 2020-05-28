package main.java.stella;

import main.java.resource.TimeFormat;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class StellaHandler {

    public static boolean isInteger(String string) {
        Scanner sc = new Scanner(string.trim());
        if (!sc.hasNextInt(10)) return false;
        sc.nextInt(10);
        return !sc.hasNext();
    }

    public static boolean isNumeric(String string) {
        try {
            Double.parseDouble(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidNameFormat(String string) {
        return string.length() <= 32 && !string.isEmpty();
    }

    public static boolean isPositiveDouble(String string) {
        if (isNumeric(string)) {
            double value = Double.parseDouble(string);
            return value > 0;
        }
        return false;
    }

    public static boolean isValidHourFormat(String string) {
        if (isNumeric(string)) {
            double value = Double.parseDouble(string);
            return !(value > 24) && !(value < 0);
        }
        return false;
    }

    public static boolean isValidAgeFormat(String string) {
        if (isNumeric(string)) {
            if (isInteger(string)) {
                double value = Double.parseDouble(string);
                return value > 0 && value < 120;
            } else {
                return false;
            }
        }
        return false;
    }

    public static TimeFormat getTimeFormat(String string) {
        List<String> hourFormats = Arrays.asList("horas", "hora", "h", "hr", "hrs", "em horas", "em hora");
        List<String> minuteFormats = Arrays.asList("minutos", "minuto", "min", "m", "em minutos", "em minuto");
        if (hourFormats.contains(string)) return TimeFormat.HOUR;
        else if (minuteFormats.contains(string)) return TimeFormat.MINUTE;
        else return TimeFormat.NULL;
    }

    public static boolean isValidMinuteFormat(String string) {
        if (isNumeric(string)) {
            double value = Double.parseDouble(string);
            return !(value > 1440) && !(value < 0);
        }
        return false;
    }

}