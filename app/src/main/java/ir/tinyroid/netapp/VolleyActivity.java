package ir.tinyroid.netapp;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class VolleyActivity extends AppCompatActivity {

    public static final String URI_SHOW_PARAMS = "https://tinyroid.ir/params.php";
    RequestQueue requestQueue;
    ProgressDialog pdialog;

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);

        imageView = (ImageView) findViewById(R.id.imageview);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        pdialog = new ProgressDialog(this);
        pdialog.setTitle("wait ...");
        pdialog.setMessage("");
        pdialog.setCancelable(true);

    }

    private void sendParamsPost(){
        final StringRequest request = new StringRequest(
                Request.Method.POST,
                URI_SHOW_PARAMS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pdialog.dismiss();
                        new AlertDialog.Builder(VolleyActivity.this)
                                .setTitle("Response ")
                                .setMessage(response)
                                .setPositiveButton("OK", null)
                                .show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pdialog.dismiss();
                        new AlertDialog.Builder(VolleyActivity.this)
                                .setTitle("Error")
                                .setMessage(error.getMessage())
                                .setPositiveButton("OK", null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                }
        ){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("firstname", "MohammadMehdi");
                params.put("lastname", "Alizadeh");
                return params;
            }

        };

        pdialog.show();
        requestQueue.add(request);
    }

    private void sendParamsGet(){
        Map<String, String> params = new HashMap<>();
        params.put("city", "Tehran");
        params.put("country", "Iran");
        String uri = URI_SHOW_PARAMS + "?" + MyHttpUtils.encodeParameters(params);

        final StringRequest request = new StringRequest(
                Request.Method.GET,
                uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pdialog.dismiss();
                        new AlertDialog.Builder(VolleyActivity.this)
                                .setTitle("Response ")
                                .setMessage(response)
                                .setPositiveButton("OK", null)
                                .show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pdialog.dismiss();
                        new AlertDialog.Builder(VolleyActivity.this)
                                .setTitle("Error")
                                .setMessage(error.getMessage())
                                .setPositiveButton("OK", null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                }
        );

        pdialog.show();
        requestQueue.add(request);
    }

    private void loadImage(){
        String url = "https://helpx.adobe.com/content/dam/help/en/stock/how-to/visual-reverse-image-search/jcr_content/main-pars/image/visual-reverse-image-search-v2_intro.jpg";
        ImageRequest request = new ImageRequest(
                url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        Toast.makeText(VolleyActivity.this, "done", Toast.LENGTH_SHORT).show();
                        imageView.setImageBitmap(response);
                    }
                },
                200, 200,
                ImageView.ScaleType.FIT_CENTER,
                Bitmap.Config.ARGB_8888,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(VolleyActivity.this, "Error : " + error.getMessage() ,
                                Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(request);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item1 = menu.add("GET");
        item1.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                sendParamsGet();
                return false;
            }
        });

        MenuItem item2 = menu.add("POST");
        item2.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                sendParamsPost();
                return false;
            }
        });

        MenuItem item3 = menu.add("Image");
        item3.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        item3.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                loadImage();
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }
}
