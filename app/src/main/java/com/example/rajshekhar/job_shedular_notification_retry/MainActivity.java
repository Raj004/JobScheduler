package com.example.rajshekhar.job_shedular_notification_retry;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private JobScheduler mJobShedular;
    RadioGroup networkOption;
    RadioButton any,wifi,none;
    private Switch charging;


    private static final int JOB_ID=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        any=(RadioButton)findViewById(R.id.any);
        wifi=(RadioButton)findViewById(R.id.wifi);
        none=(RadioButton)findViewById(R.id.nonetwork);
        networkOption=(RadioGroup)findViewById(R.id.networkoption);
        charging=(Switch)findViewById(R.id.switch_bettery);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void notificationshedule(View view){
        mJobShedular=(JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        int selectedNetworkId= networkOption.getCheckedRadioButtonId();
        int selectedNetworkOption=JobInfo.NETWORK_TYPE_NONE;
        switch (selectedNetworkId){
            case R.id.nonetwork:
                selectedNetworkOption=JobInfo.NETWORK_TYPE_NONE;
                break;
            case R.id.wifi:
                selectedNetworkOption=JobInfo.NETWORK_TYPE_UNMETERED;
                break;
            case R.id.any:
                selectedNetworkOption=JobInfo.NETWORK_TYPE_ANY;
                break;
        }
        /*
        ComponentName serviceName  = new ComponentName(getPackageName(),
        MyJobService.class.getName());
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID,serviceName)
        .setRequiiredNetwork(SelectNetworkOPtion)

        * */

        ComponentName serviceName = new ComponentName(getPackageName(),
                MyJobService.class.getName());
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID,serviceName)
        .setRequiredNetworkType(selectedNetworkOption)
        .setRequiresCharging(charging.isChecked());
        boolean constaintSelect=(selectedNetworkOption!=JobInfo.NETWORK_TYPE_NONE)
                ||charging.isChecked();
        if(constaintSelect) {
            JobInfo myJobInfo = builder.build();
            mJobShedular.schedule(myJobInfo);
            Toast.makeText(this, "Job shedule-", Toast.LENGTH_SHORT).show();
        }else {

            Toast.makeText(this,"Please selcet any one radio ",Toast.LENGTH_SHORT).show();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public  void cancel(View view){
        if(mJobShedular!=null){
            mJobShedular.cancelAll();
            mJobShedular=null;
            Toast.makeText(this,"Job Cancel",Toast.LENGTH_SHORT).show();
        }

    }
}
