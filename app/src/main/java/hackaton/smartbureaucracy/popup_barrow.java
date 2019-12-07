package hackaton.smartbureaucracy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class popup_barrow extends AppCompatActivity {
    String test = "false";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_barrow);
        // test="false";
        (findViewById(R.id.cere_oferta)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //aici vom prelucra datele si le vom trimite la server
                Thread thread = new Thread(new Runnable() {
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



                            EditText amount_popup = (EditText) findViewById(R.id.amount_popup);
                            Spinner months_popup = (Spinner) findViewById(R.id.spinner1);

                            JSONObject jsonParam = new JSONObject();
                            jsonParam.put("amount", amount_popup.getText().toString());
                            jsonParam.put("period", months_popup.toString());
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
        });

    }




}
