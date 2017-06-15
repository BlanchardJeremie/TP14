package com.example.admin.projetsante;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicBoolean;


public class MainActivity extends AppCompatActivity {

private final String PROGRESS = "PROGRESS";
    ProgressBar bar;
    ProgressBar bar2;
    Handler handler = new Handler(){
        public void handleMessage(Message msg){
            int progress = msg.getData().getInt(PROGRESS);
            bar.incrementProgressBy(progress);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bar = (ProgressBar) findViewById(R.id.progressBar);
        bar2 = (ProgressBar) findViewById(R.id.progressBar2);
        bar.setMax(100);
        bar2.setMax(100);
        new MyAsyncTask().execute();
    }


    @Override
    protected void onStart(){
        super.onStart();
        bar.setProgress(0);
        bar2.setProgress(0);

        Thread thread1 = new Thread(new Runnable() {
            Bundle messageBundle = new Bundle();
            Message message ;
            @Override
            public void run() {


                try {

                    for (int i = 0 ; i <= 100;i++ ) {
                        Thread.sleep(1000);

                        message = handler.obtainMessage();
                        messageBundle.putInt(PROGRESS, i);
                        message.setData(messageBundle);
                        handler.sendMessage(message);

                    }

                } catch (Throwable t) {
                }
            }
        });
        //lance le thread
        thread1.start();

    }


    class MyAsyncTask extends AsyncTask<Void,Integer,String> {



        @Override
        protected void onCancelled(String result) {

        }

        @Override
        protected void onPostExecute(String result) {

        // UI Thread

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            bar2.setProgress(values[0]);

        }


        @Override
        protected String doInBackground(Void... arg0){
            int progress;
            for (progress = 0; progress <= 100 ; progress++){
                if(isCancelled()) break;
                try{
                    Thread.sleep(1000);
                    publishProgress(progress);
                }catch (InterruptedException e){
                }
                publishProgress(progress);
            }
            return " , dong ! ";

        }
    }

}
