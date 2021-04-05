package ir.tinyroid.netapp;

import android.os.AsyncTask;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class HttpParamsActivity extends AppCompatActivity {

    public static final String URI_SHOW_PARAMS = "https://tinyroid.ir/params.php";
    TextView tv;
    ProgressBar pb;

    List<AsyncTask> tasks = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_params);
        tv = (TextView) findViewById(R.id.tv);
        pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setVisibility(View.INVISIBLE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item1 = menu.add("GET");
        item1.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        item1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                MyHttpUtils.RequestData requestData =
                        new MyHttpUtils.RequestData(URI_SHOW_PARAMS, "GET");
                requestData.setParameter("name", "MohammadMehdi");
                requestData.setParameter("code", "12234");
                requestData.setParameter("message", "Hello!");
                new MyTask().execute(requestData);

                return false;
            }
        });

        MenuItem item2 = menu.add("POST");
        item2.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        item2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                MyHttpUtils.RequestData requestData =
                        new MyHttpUtils.RequestData(URI_SHOW_PARAMS, "POST");
                requestData.setParameter("firstname", "MohammadMehdi");
                requestData.setParameter("lastname", "Alizadeh");
                requestData.setParameter("age", "23");
                new MyTask().execute(requestData);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public class MyTask extends AsyncTask<MyHttpUtils.RequestData, Void, String>{


        @Override
        protected void onPreExecute() {
            if(tasks.isEmpty()){
                pb.setVisibility(View.VISIBLE);
            }
            tasks.add(this);
        }

        @Override
        protected String doInBackground(MyHttpUtils.RequestData... params) {
            MyHttpUtils.RequestData reqData = params[0];

            return MyHttpUtils.getDataHttpUrlConnection(reqData);
        }

        @Override
        protected void onPostExecute(String result) {
            if(result == null) {
                result = "صث بعزنثی عح";
            }
            tv.setText(result);
            tasks.remove(this);
            if(tasks.isEmpty()){
                pb.setVisibility(View.INVISIBLE);
            }
        }
    }


}
