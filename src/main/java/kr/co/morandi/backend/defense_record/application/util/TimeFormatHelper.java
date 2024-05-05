package kr.co.morandi.backend.defense_record.application.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeFormatHelper {

    public static String solvedTimeToString(Long solvedTime) {
        return String.format("%02d:%02d:%02d", solvedTime / 3600, (solvedTime % 3600) / 60, solvedTime % 60);
    }

    public static Long stringToSolvedTime(String solvedTime) {
        String[] time = solvedTime.split(":");
        return Long.parseLong(time[0]) * 3600 + Long.parseLong(time[1]) * 60 + Long.parseLong(time[2]);
    }
}
