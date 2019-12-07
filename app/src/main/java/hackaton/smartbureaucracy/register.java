package hackaton.smartbureaucracy;

import android.content.Intent;
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

public class register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void register(View view) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    URL url = new URL("http://ylc-blink.herokuapp.com/v1/auth/register");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    // conn.setRequestProperty("Authorization", "Bearer {token}");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    //  TextView Email = (TextView) findViewById(R.id.email);
                    EditText Password = (EditText) findViewById(R.id.password) ;
                    EditText Firstname = (EditText) findViewById(R.id.firstname) ;
                    EditText Lastname = (EditText) findViewById(R.id.lastname) ;
                    EditText Email = (EditText) findViewById(R.id.email);


                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("email", Email.getText().toString());
                    jsonParam.put("password", Password.getText().toString());
                    jsonParam.put("firstName", Firstname.getText().toString());
                    jsonParam.put("lastName", Lastname.getText().toString());
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
                    //  System.out.println(obj.getString("success"));

                    //incepem impartirea

                    String test = obj.getString("success");



                    if(test.equals("true")) {


                        //System.out.println(obj);
                        Intent i = new Intent(register.this, Login.class);       // Specify any activity here e.g. home or splash or login etc
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

    public void toLogin(View view) {
        Intent i = new Intent(register.this, Login.class);       // Specify any activity here e.g. home or splash or login etc
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("EXIT", true);
        startActivity(i);
        finish();
    }
}
