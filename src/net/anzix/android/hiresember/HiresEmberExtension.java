package net.anzix.android.hiresember;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import com.google.android.apps.dashclock.api.DashClockExtension;
import com.google.android.apps.dashclock.api.ExtensionData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 */
public class HiresEmberExtension extends DashClockExtension {

    @Override
    protected void onUpdateData(int i) {
        Date no = new Date();
        String title = "Not found";
        String description = "Not found";
        Calendar cal = Calendar.getInstance();
        String day = "" + cal.get(Calendar.DAY_OF_MONTH);
        String month = "" + (cal.get(Calendar.MONTH) + 1);


        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getApplicationContext().getResources().openRawResource(R.raw.people)));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts[1].trim().equals(month) && parts[2].trim().equals(day)) {
                    title = parts[3];
                    int age = cal.get(Calendar.YEAR) - Integer.parseInt(parts[0]);
                    description = age + " éve született (" + parts[0] + ")";
                }

            }
            reader.close();
        } catch (Exception ex) {
            title = "ERROR";
            description = ex.getMessage();

        }


        publishUpdate(new ExtensionData()
                .visible(true)
                .icon(R.drawable.ic_extension_hiresember)
                .status(title.split(" ")[0])
                .expandedTitle(title)
                .expandedBody(description)
                .clickIntent(new Intent(Intent.ACTION_VIEW, Uri.parse("http://hu.wikipedia.org/w/index.php?search=" + title.replace(' ', '+')))));
//
    }
}
