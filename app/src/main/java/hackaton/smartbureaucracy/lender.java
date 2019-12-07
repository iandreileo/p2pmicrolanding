package hackaton.smartbureaucracy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class lender extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lender);


        TextView textView1 = (TextView)findViewById(R.id.amount);
        textView1.setText(String.valueOf(getSharedPreferences("amount", MODE_PRIVATE).getInt("key",0)));

        TextView textView2 = (TextView)findViewById(R.id.invested);
        textView2.setText(String.valueOf(getSharedPreferences("investedAmount1", MODE_PRIVATE).getInt("key",0)));

        Spinner spinner1=(Spinner) findViewById(R.id.risk_factor);
        String myString = String.valueOf(getSharedPreferences("minCreditScore", MODE_PRIVATE).getString("key","")); //the value you want the position for

        ArrayAdapter myAdap = (ArrayAdapter) spinner1.getAdapter(); //cast to an ArrayAdapter

        int spinnerPosition = myAdap.getPosition(myString);

//set the default according to value
        spinner1.setSelection(spinnerPosition);

        //al doilea spinner

        Spinner spinner2=(Spinner) findViewById(R.id.Period);
        String myString2 = String.valueOf(getSharedPreferences("maxperiod", MODE_PRIVATE).getString("key","")); //the value you want the position for

        ArrayAdapter myAdap2 = (ArrayAdapter) spinner2.getAdapter(); //cast to an ArrayAdapter

        int spinnerPosition2 = myAdap2.getPosition(myString2);

//set the default according to value
        spinner2.setSelection(spinnerPosition2);

        //
        EditText editText = (EditText) findViewById(R.id.dobanda);
        editText.setText(getSharedPreferences("rate2", MODE_PRIVATE).getString("key",""));

    }

    public void modify(View view) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    URL url = new URL("http://ylc-blink.herokuapp.com/v1/offer/update");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setRequestProperty("Authorization", "Bearer " + getSharedPreferences("accessToken", MODE_PRIVATE).getString("key",""));
                    conn.setDoOutput(true);
                    conn.setDoInput(true);



                    EditText editText = (EditText) findViewById(R.id.dobanda);
                    Spinner spinner1 = (Spinner) findViewById(R.id.risk_factor);
                    Spinner spinner2 = (Spinner) findViewById(R.id.Period);




                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("rate", editText.getText());
                    jsonParam.put("minCreditScore", spinner1.getSelectedItem());
                    jsonParam.put("maxPeriod", spinner2.getSelectedItem());
                    Log.i("JSON", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    //  os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                    os.writeBytes(jsonParam.toString());
                    System.out.println(jsonParam.toString());

                    //aici primesc si afisez
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    String line = reader.readLine();
                    System.out.println(line);






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
