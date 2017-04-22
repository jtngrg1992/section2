package com.example.jatingarg.section2;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MyService mBoundService;
    private boolean isBound = false;
    private TextView mTextView;
    private Button mGenerateBtn;
    private Button mStopBtn;
    private Button mViewDataBtn;
    private SQLiteDatabase newDB;
    private DBhelper mdbHelper;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.textView);
        mGenerateBtn = (Button) findViewById(R.id.generateBtn);
        mStopBtn = (Button)findViewById(R.id.stopServiceBtn);
        mViewDataBtn = (Button)findViewById(R.id.viewDataBtn);

        mdbHelper = new DBhelper(this);

        mGenerateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isBound){
                    Toast.makeText(MainActivity.this,"service is not running!", Toast.LENGTH_LONG).show();
                    return;
                }
                Log.d(TAG, "onClick:  inserting data");
                Integer num = mBoundService.getRandomNumber();
                mTextView.setText(Integer.toString(num));
                //inserting this number into db
                boolean isInserted = mdbHelper.insertData(num);
                Log.d(TAG, "onClick: " + isInserted);
            }
        });

        mStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isBound){
                    unbindService(mServiceConnection);
                    isBound = false;
                }
                Intent i = new Intent(MainActivity.this,MyService.class);
                stopService(i);
                Log.d(TAG, "onClick: service stopped");

            }
        });

        mViewDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cur = mdbHelper.getAllData();
                if(cur.getCount() == 0){
                    Toast.makeText(MainActivity.this,"No data found in db",Toast.LENGTH_SHORT).show();
                    return;
                }
                while (cur.moveToNext()){
                    Log.d(TAG, "onClick: " + (cur.getColumnIndex("_id")));

                }
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);

            }
        });

        //launching bound service
        Intent i = new Intent(this,MyService.class);
        bindService(i,mServiceConnection, Context.BIND_AUTO_CREATE);
        openDatabase();

    }

    private void  openDatabase() {

    }

    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.MyBinder binder = (MyService.MyBinder) service;
            mBoundService = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };
}
