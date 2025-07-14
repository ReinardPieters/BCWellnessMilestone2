package utils;

import java.time.LocalTime;

public class TimeUtils {
    public static boolean isWithinRange(String range, LocalTime selectedTime) {
        try {
            String[] parts = range.split(" to ");
            if (parts.length != 2) {
                System.out.println("Invalid range format: " + range);
                return false;
            }

            LocalTime start = LocalTime.parse(parts[0]);
            LocalTime end = LocalTime.parse(parts[1]);
            
            System.out.println(end);
            System.out.println(start);
            
            boolean result = !selectedTime.isBefore(start) && !selectedTime.isAfter(end);
            System.out.println("Start: " + start + ", End: " + end + ", Result: " + result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}


