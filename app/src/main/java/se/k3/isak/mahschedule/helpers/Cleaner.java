package se.k3.isak.mahschedule.helpers;

import android.util.Log;

import se.k3.isak.mahschedule.activities.MainActivity;

/**
 * Created by isak on 2015-04-05.
 */

public class Cleaner {

    public static String cleanKronoxResponse(String s) {
        int start1 = s.indexOf("<b>");
        int end1 = s.indexOf("<\\/b>");

        int start2 = s.indexOf(">, ");
        int end2 = s.indexOf("<\\/font");

        if(start1 == -1 || end1 == -1 || start2 == -1 || end2 == -1) {
            Log.i(MainActivity.TAG, "cleanKronoxResponse fuckar ur");
            return null;
        }

        String part1 = s.substring(start1 + 3, end1);
        String part2 = s.substring(start2 + 3, end2);

        return part2 + ",\n" + part1;
    }

}
