package timeline.lizimumu.com.t.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.io.File;
import java.util.List;

import timeline.lizimumu.com.t.R;
import timeline.lizimumu.com.t.data.HistoryItem;
import timeline.lizimumu.com.t.db.DbHistoryExecutor;
import timeline.lizimumu.com.t.util.PreferenceManager;

public class SettingsActivity extends AppCompatActivity {

    Switch mSwitchSystem;
    Switch mSwitchUninstall;
   // private static final String STRING_ARRAY_SAMPLE = "./string-array-sample.csv";

    private static final String OBJECT_LIST_SAMPLE = "./object-list-sample.csv";
    //String csv = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/AllRecords.csv"); // Here csv file name is MyCsvFile.csv

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        DbHistoryExecutor records = new DbHistoryExecutor();
        List<HistoryItem> items = records.getAllItems();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.settings);
        }

        //try {

////          CSVWriter writer = null;
//           Writer writer = Files.newBufferedWriter(Paths.get(OBJECT_LIST_SAMPLE));
////            StatefulBeanToCsv<HistoryItem> beanToCsv = new StatefulBeanToCsvBuilder(writer)
////                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
////                    .build();

            //writer = new CSVWriter(new FileWriter(csv));
//            writer.write(String.valueOf(items)); // data is adding to csv
//            writer.close();
//            beanToCsv.write(items);
       // } catch (IOException e) {
          //  e.printStackTrace();
       // }
       // catch (CsvRequiredFieldEmptyException e) {
        //    e.printStackTrace();
        //} catch (CsvDataTypeMismatchException e) {
        //    e.printStackTrace();
       // }

        // hide system
        mSwitchSystem = findViewById(R.id.switch_system_apps);
        mSwitchSystem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (PreferenceManager.getInstance().getSystemSettings(PreferenceManager.PREF_SETTINGS_HIDE_SYSTEM_APPS) != b) {
                    PreferenceManager.getInstance().putBoolean(PreferenceManager.PREF_SETTINGS_HIDE_SYSTEM_APPS, b);
                    setResult(1);
                }
            }
        });

        findViewById(R.id.group_system).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSwitchSystem.performClick();
            }
        });

        // hide uninstall
        mSwitchUninstall = findViewById(R.id.switch_uninstall_appps);
        mSwitchUninstall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (PreferenceManager.getInstance().getUninstallSettings(PreferenceManager.PREF_SETTINGS_HIDE_UNINSTALL_APPS) != b) {
                    PreferenceManager.getInstance().putBoolean(PreferenceManager.PREF_SETTINGS_HIDE_UNINSTALL_APPS, b);
                    setResult(1);
                }
            }
        });

        findViewById(R.id.group_uninstall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSwitchUninstall.performClick();
            }
        });

        // ignore list
        findViewById(R.id.group_ignore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this, IgnoreActivity.class));
            }
        });

        // about
        findViewById(R.id.group_about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this, AboutActivity.class));
            }
        });

        // share
        findViewById(R.id.group_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String shareText = getResources().getString(R.string.share_desc);

                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"email@example.com"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "subject here");
                emailIntent.putExtra(Intent.EXTRA_TEXT, OBJECT_LIST_SAMPLE);

                File file = new File(OBJECT_LIST_SAMPLE);
                Uri uri = Uri.fromFile(file);
                emailIntent.putExtra(Intent.EXTRA_STREAM, OBJECT_LIST_SAMPLE);
                startActivity(Intent.createChooser(emailIntent, "Pick an Email provider"));
            }
        });

        restoreStatus();
    }

    private void restoreStatus() {
        mSwitchSystem.setChecked(PreferenceManager.getInstance().getSystemSettings(PreferenceManager.PREF_SETTINGS_HIDE_SYSTEM_APPS));
        mSwitchUninstall.setChecked(PreferenceManager.getInstance().getUninstallSettings(PreferenceManager.PREF_SETTINGS_HIDE_UNINSTALL_APPS));
    }
}
