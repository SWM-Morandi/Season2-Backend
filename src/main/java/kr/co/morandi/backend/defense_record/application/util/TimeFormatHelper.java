package kr.co.morandi.backend.defense_record.application.util;

public class TimeFormatHelper {

    public static String solvedTimeToString(Long solvedTime) {
        return String.format("%02d:%02d:%02d", solvedTime / 3600, (solvedTime % 3600) / 60, solvedTime % 60);
    }
}
