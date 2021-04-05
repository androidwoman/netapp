package ir.tinyroid.netapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;


public class FirstExampleActivity extends AppCompatActivity {

    public static final String SAMPLE_URL = "https://tinyroid.ir/pln.json";

    TextView tv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_exmaple);

        tv = (TextView) findViewById(R.id.tv);
        tv.setMovementMethod(new ScrollingMovementMethod());
    }


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuItem item = menu.add("GET");
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Thread thread = new Thread(new Runnable() {

                    Handler handler = new Handler(){

                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            String content = (String) msg.getData().get("content");
                            tv.setText(content);
                        }
                    };

                    @Override
                    public void run() {
                        String content = null;
                        try {
                            content = MyHttpUtils.getDataConnection(SAMPLE_URL);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("content", content);
                        message.setData(bundle);
                        handler.sendMessage(message);
                    }
                });
                thread.start();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private String getData()  {   // needs  INTERNET permission
        HttpClient client = new DefaultHttpClient();
        HttpGet method = new HttpGet(SAMPLE_URL);
        try {
            HttpResponse response = client.execute(method);
            String content = MyHttpUtils.inputStreamToString(response.getEntity().getContent());
            Log.i("getData", content);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }





}
