package ir.tinyroid.netapp;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onclick(View v){
        String tag = (String) v.getTag();
        switch (tag) {
            case "1" :
                startActivity(new Intent(this, FirstExampleActivity.class));
                break;
            case "2" :
                startActivity(new Intent(this, AsyncTaskIntroActivity.class));
                break;
            case "3" :
                startActivity(new Intent(this, HttpParamsActivity.class));
                break;
            case "4" :
                startActivity(new Intent(this, VolleyActivity.class));
                break;
            default: break;
        }


    }

}
