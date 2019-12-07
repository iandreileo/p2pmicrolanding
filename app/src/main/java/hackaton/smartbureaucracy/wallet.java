package hackaton.smartbureaucracy;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class wallet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        TextView textView4 = (TextView)findViewById(R.id.funds);
        textView4.setText(String.valueOf(getSharedPreferences("amount", MODE_PRIVATE).getInt("key",0)));

    }

    public void add_funds(View view) {
        EditText editText = (EditText) findViewById(R.id.funds_availb);
        if(!(editText.getText().toString().equals(""))){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        URL url = new URL("http://ylc-blink.herokuapp.com/v1/user/top-up");
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                        conn.setRequestProperty("Accept","application/json");
                        conn.setRequestProperty("Authorization", "Bearer " + getSharedPreferences("accessToken", MODE_PRIVATE).getString("key",""));
                        conn.setDoOutput(true);
                        conn.setDoInput(true);



                        EditText editText = (EditText) findViewById(R.id.funds_availb);


                        JSONObject jsonParam = new JSONObject();
                        jsonParam.put("amount", editText.getText());
                        Log.i("JSON", jsonParam.toString());
                        DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                        //  os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                        os.writeBytes(jsonParam.toString());

                        //aici primesc si afisez
                        InputStream in = new BufferedInputStream(conn.getInputStream());
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                        String line = reader.readLine();
                        System.out.println(line);

                      JSONObject obj = new JSONObject(line);
                        JSONObject data = obj.getJSONObject("data");
                        Integer newAmount = data.getInt("newAmount");
                        //System.out.println(obj.getString("success"));

                        //incepem impartirea
                        SharedPreferences amount = getSharedPreferences("amount", MODE_PRIVATE);
                        SharedPreferences.Editor amount_editor = amount.edit();
                        amount_editor.putInt("key", newAmount);
                        amount_editor.apply();
                        amount_editor.commit();


                        TextView textView4 = (TextView)findViewById(R.id.funds);
                        textView4.setText(String.valueOf(getSharedPreferences("amount", MODE_PRIVATE).getInt("key",0)));


                        os.flush();
                        os.close();


                        Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                        Log.i("MSG" , conn.getResponseMessage());
                        conn.disconnect();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();

        }
    }

    public void withdraw(View view) {
        EditText editText = (EditText) findViewById(R.id.funds_availb);
        if(Integer.parseInt(editText.getText().toString()) <= getSharedPreferences("amount", MODE_PRIVATE).getInt("key",0)){

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        URL url = new URL("http://ylc-blink.herokuapp.com/v1/user/withdraw");
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                        conn.setRequestProperty("Accept","application/json");
                        conn.setRequestProperty("Authorization", "Bearer " + getSharedPreferences("accessToken", MODE_PRIVATE).getString("key",""));
                        conn.setDoOutput(true);
                        conn.setDoInput(true);



                        EditText editText = (EditText) findViewById(R.id.funds_availb);


                        JSONObject jsonParam = new JSONObject();
                        jsonParam.put("amount", editText.getText());
                        Log.i("JSON", jsonParam.toString());
                        DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                        //  os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                        os.writeBytes(jsonParam.toString());

                        //aici primesc si afisez
                        InputStream in = new BufferedInputStream(conn.getInputStream());
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                        String line = reader.readLine();
                        System.out.println(line);

                        JSONObject obj = new JSONObject(line);
                        JSONObject data = obj.getJSONObject("data");
                        Integer newAmount = data.getInt("newAmount");
                        //System.out.println(obj.getString("success"));

                        //incepem impartirea
                        SharedPreferences amount = getSharedPreferences("amount", MODE_PRIVATE);
                        SharedPreferences.Editor amount_editor = amount.edit();
                        amount_editor.putInt("key", newAmount);
                        amount_editor.apply();
                        amount_editor.commit();


                        TextView textView4 = (TextView)findViewById(R.id.funds);
                        textView4.setText(String.valueOf(getSharedPreferences("amount", MODE_PRIVATE).getInt("key",0)));


                        os.flush();
                        os.close();


                        Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                        Log.i("MSG" , conn.getResponseMessage());
                        conn.disconnect();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();

        }
    }
}

