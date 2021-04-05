package ir.tinyroid.netapp;

import android.os.AsyncTask;
import android.os.Bundle;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AsyncTaskIntroActivity extends AppCompatActivity {
    public static final String SAMPLE_URL = "https://tinyroid.ir/pln.json";


    int counter = 0;
    List<AsyncTask> tasks = new ArrayList<>();
    TextView tv;
    ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task_intro);

        tv = (TextView) findViewById(R.id.tv);
        tv.setMovementMethod(new ScrollingMovementMethod());
        pb = (ProgressBar) findViewById(R.id.progressBar);
        findViewById(R.id.btn_intro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TaskIntro("task #" + (++counter))
//                          .execute("bank", "hostipal", "shopping");
                        .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "1", "2", "3", "4");
            }
        });
        findViewById(R.id.btn_get_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TaskGetData().execute(SAMPLE_URL);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("RESET").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                tv.setText("");
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public class TaskIntro extends AsyncTask<String, String, String> {

        String taskName = "";
        Random random ;
        TaskIntro(String taskName){
            this.taskName = taskName;
            random = new Random();
        }



        @Override
        protected void onPreExecute() {
            if(tasks.isEmpty()){
                pb.setVisibility(View.VISIBLE);
            }
            tv.append("<" + taskName + ">    Start!\n");
            tasks.add(this);
        }

        @Override
        protected String doInBackground(String... params) {
            for (String p : params){
                try {
                    Thread.sleep(random.nextInt(700) + 700);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress("working with parameter : " + p);
            }

            return "done";
        }


        @Override
        protected void onProgressUpdate(String... values) {
            tv.append("<" + taskName + ">    " + values[0] + "\n");
        }

        @Override
        protected void onPostExecute(String result) {
            tv.append("<" + taskName + ">    " + result + "\n");
            tasks.remove(this);
            if(tasks.isEmpty()){
                pb.setVisibility(View.INVISIBLE);
            }
        }
    }

    public class TaskGetData extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            if(tasks.isEmpty()){
                pb.setVisibility(View.VISIBLE);
            }
            tasks.add(this);
            tv.append("Get data ...\n\n");
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                return MyHttpUtils.getDataConnection(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "done";
        }

        @Override
        protected void onPostExecute(String result) {
            tasks.remove(this);
            if(tasks.isEmpty()){
                pb.setVisibility(View.INVISIBLE);
            }

            tv.append(result + "\n");
        }
    }



}
