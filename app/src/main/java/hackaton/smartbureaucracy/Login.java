package hackaton.smartbureaucracy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Login extends AppCompatActivity {
    String test = "false";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       // test="false";
    }

   /* Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {

                URL url = new URL("http://ylc-blink.herokuapp.com/v1/auth/login/");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept","application/json");
                conn.setRequestProperty("Authorization", "Bearer {token}");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                TextView Email = (TextView) findViewById(R.id.email);
                TextView Password = (TextView) findViewById(R.id.password) ;

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("email", Email.getText().toString());
                jsonParam.put("password", Password.getText().toString());
                Log.i("JSON", jsonParam.toString());
                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
              //  os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                os.writeBytes(jsonParam.toString());

                //aici primesc si afisez
                InputStream in = new BufferedInputStream(conn.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line = reader.readLine();
                //System.out.println(line);

                JSONObject obj = new JSONObject(line);
              //  System.out.println(obj.getString("success"));

                //incepem impartirea

                test = obj.getString("success");
                JSONObject data = obj.getJSONObject("data");
                JSONObject user = data.getJSONObject("user");

               // String accessToken = data.getString("accessToken");
               // Integer amount = user.getInt("amount");
              //  String _id = user.getString("_id");
               // String firstName = user.getString("firstName");
              //  String lastName = user.getString("lastName");
               // String email = user.getString("email");

                if(test.equals("true")) {

                    SharedPreferences accessToken = getSharedPreferences("accessToken", MODE_PRIVATE);
                    SharedPreferences.Editor accessToken_editor = accessToken.edit();
                    accessToken_editor.putString("key", data.getString("accessToken"));
                    accessToken_editor.apply();

                    SharedPreferences amount = getSharedPreferences("amount", MODE_PRIVATE);
                    SharedPreferences.Editor amount_editor = amount.edit();
                    amount_editor.putInt("key", user.getInt("amount"));
                    amount_editor.apply();

                    SharedPreferences _id = getSharedPreferences("_id", MODE_PRIVATE);
                    SharedPreferences.Editor _id_editor = _id.edit();
                    _id_editor.putString("key", user.getString("_id"));
                    _id_editor.apply();

                    SharedPreferences firstName = getSharedPreferences("firstName", MODE_PRIVATE);
                    SharedPreferences.Editor firstName_editor = firstName.edit();
                    firstName_editor.putString("key", user.getString("firstName"));
                    firstName_editor.apply();

                    SharedPreferences lastName = getSharedPreferences("lastName", MODE_PRIVATE);
                    SharedPreferences.Editor lastName_editor = lastName.edit();
                    lastName_editor.putString("key", user.getString("lastName"));
                    lastName_editor.apply();

                    SharedPreferences email = getSharedPreferences("email", MODE_PRIVATE);
                    SharedPreferences.Editor email_editor = email.edit();
                    email_editor.putString("key", user.getString("email"));
                    email_editor.apply();

                    //System.out.println(obj);
                    Intent i = new Intent(Login.this, MainActivity.class);       // Specify any activity here e.g. home or splash or login etc
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("EXIT", true);
                    startActivity(i);
                    finish();
                }

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


*/
    public void test(View view) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    URL url = new URL("http://ylc-blink.herokuapp.com/v1/auth/login/");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                   // conn.setRequestProperty("Authorization", "Bearer {token}");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                  //  TextView Email = (TextView) findViewById(R.id.email);
                    EditText Password = (EditText) findViewById(R.id.password) ;

                    EditText Email = (EditText) findViewById(R.id.email);


                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("email", Email.getText().toString());
                    jsonParam.put("password", Password.getText().toString());
                    Log.i("JSON", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    //  os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                    os.writeBytes(jsonParam.toString());

                    //aici primesc si afisez
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    String line = reader.readLine();
                    //System.out.println(line);

                    JSONObject obj = new JSONObject(line);
                    //  System.out.println(obj.getString("success"));

                    //incepem impartirea

                    test = obj.getString("success");
                    JSONObject data = obj.getJSONObject("data");
                    JSONObject user = data.getJSONObject("user");

                    // String accessToken = data.getString("accessToken");
                    // Integer amount = user.getInt("amount");
                    //  String _id = user.getString("_id");
                    // String firstName = user.getString("firstName");
                    //  String lastName = user.getString("lastName");
                    // String email = user.getString("email");


                    //de modificat dupa debug!!!
                    test = "true";

                    if(test.equals("true")) {

                        SharedPreferences accessToken = getSharedPreferences("accessToken", MODE_PRIVATE);
                        SharedPreferences.Editor accessToken_editor = accessToken.edit();
                        accessToken_editor.putString("key", data.getString("accessToken"));
                        accessToken_editor.apply();

                        SharedPreferences amount = getSharedPreferences("amount", MODE_PRIVATE);
                        SharedPreferences.Editor amount_editor = amount.edit();
                        amount_editor.putInt("key", user.getInt("amount"));
                        amount_editor.apply();

                        SharedPreferences _id = getSharedPreferences("_id", MODE_PRIVATE);
                        SharedPreferences.Editor _id_editor = _id.edit();
                        _id_editor.putString("key", user.getString("_id"));
                        _id_editor.apply();

                        SharedPreferences firstName = getSharedPreferences("firstName", MODE_PRIVATE);
                        SharedPreferences.Editor firstName_editor = firstName.edit();
                        firstName_editor.putString("key", user.getString("firstName"));
                        firstName_editor.apply();

                        SharedPreferences lastName = getSharedPreferences("lastName", MODE_PRIVATE);
                        SharedPreferences.Editor lastName_editor = lastName.edit();
                        lastName_editor.putString("key", user.getString("lastName"));
                        lastName_editor.apply();

                        SharedPreferences email = getSharedPreferences("email", MODE_PRIVATE);
                        SharedPreferences.Editor email_editor = email.edit();
                        email_editor.putString("key", user.getString("email"));
                        email_editor.apply();

                        //System.out.println(obj);
                        Intent i = new Intent(Login.this, MainActivity.class);       // Specify any activity here e.g. home or splash or login etc
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.putExtra("EXIT", true);
                        startActivity(i);
                        finish();
                    }

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


    public void go_reg(View view) {
        Intent i = new Intent(Login.this, register.class);       // Specify any activity here e.g. home or splash or login etc
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("EXIT", true);
        startActivity(i);
        finish();
    }
}
