package hackaton.smartbureaucracy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void persoana_fizica(View view) {

        if(getSharedPreferences("Nume_pfiz", MODE_PRIVATE).getString("key", "").equals("") || getSharedPreferences("Prenume_pfiz", MODE_PRIVATE).getString("key", "").equals("") || getSharedPreferences("CNP_pfiz", MODE_PRIVATE).getString("key", "").equals("") || getSharedPreferences("Data_pfiz", MODE_PRIVATE).getString("key", "").equals("") || getSharedPreferences("Domiciliu_pfiz", MODE_PRIVATE).getString("key", "").equals("") || getSharedPreferences("Serie_pfiz", MODE_PRIVATE).getString("key", "").equals("") || getSharedPreferences("Nrbul_pfiz", MODE_PRIVATE).getString("key", "").equals("") || getSharedPreferences("Locnastere_pfiz", MODE_PRIVATE).getString("key", "").equals("") || getSharedPreferences("Email_pfiz", MODE_PRIVATE).getString("key", "").equals("")) {
            Intent myIntent = new Intent(MainActivity.this,
                    pfizica.class);
            startActivity(myIntent);
        } else {
            Intent myIntent = new Intent(MainActivity.this,
                    pfizica.class);
            startActivity(myIntent);
        }
    }


    public void GDPR(View view) {
        Intent myIntent = new Intent(MainActivity.this,
                borrower.class);
        startActivity(myIntent);
    }

    public void scaneaza_qr(View view) {




        Intent myIntent = new Intent(MainActivity.this,
                wallet.class);
        startActivity(myIntent);
    }

    public void ocr(View view) {


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {





                    URL url = new URL("https://ylc-blink.herokuapp.com/v1/user/get-lender-data");
                    //  URL url = new URL("https://google.com");

/*
                            URLConnection connection = new URL(url + "?" + query).openConnection();
                            HttpURLConnection conn = (HttpURLConnection) connection;
*/
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setRequestProperty("Authorization", "Bearer " + getSharedPreferences("accessToken", MODE_PRIVATE).getString("key",""));
                    //  conn.setRequestProperty("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImFuZHJlaUBldW1lby5pbyIsImlhdCI6MTU3MjAwMTczOSwiZXhwIjoxNTc0NTkzNzM5fQ.QuDRQt4Db8gmJ8gDbFHvuZcw6T_kBNJ2pj-kXepv5Jc");
                    // conn.setDoOutput(true);
                    conn.setDoInput(true);
                    System.out.println(conn.getRequestMethod());
                    System.out.println(conn.getURL());
                    System.out.println(conn.getResponseCode());


                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    String line = reader.readLine();
                    JSONObject obj = new JSONObject(line);

                    final String test = obj.getString("success");

                    System.out.println(line);





                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG" , conn.getResponseMessage());
                    conn.disconnect();

                    if(test.equals("true")){
                        JSONObject data = obj.getJSONObject("data");
                        Integer cash = data.getInt("cash");
                        Integer investedAmount = data.getInt("investedAmount");
                        JSONObject offer = data.getJSONObject("offer");
                        String rata = offer.getString("rate");
                        String maxPeriod = offer.getString("maxPeriod");
                        String minCreditScore = offer.getString("minCreditScore");



                        //memoram ce avem nevoie ulterior
                        SharedPreferences rate2 = getSharedPreferences("rate2", MODE_PRIVATE);
                        SharedPreferences.Editor rate2_editor = rate2.edit();
                        rate2_editor.putString("key", rata);
                        rate2_editor.apply();

                        SharedPreferences maxperiod1 = getSharedPreferences("maxperiod", MODE_PRIVATE);
                        SharedPreferences.Editor maxperiod_editor = maxperiod1.edit();
                        maxperiod_editor.putString("key", maxPeriod);
                        maxperiod_editor.apply();

                        SharedPreferences minCreditScore1 = getSharedPreferences("minCreditScore", MODE_PRIVATE);
                        SharedPreferences.Editor minCreditScore1_editor = minCreditScore1.edit();
                        minCreditScore1_editor.putString("key", minCreditScore);
                        minCreditScore1_editor.apply();


                        SharedPreferences av_cash = getSharedPreferences("av_cash", MODE_PRIVATE);
                        SharedPreferences.Editor av_cash_editor = av_cash.edit();
                        av_cash_editor.putInt("key", cash);
                        av_cash_editor.apply();

                        SharedPreferences investedAmount1 = getSharedPreferences("investedAmount1", MODE_PRIVATE);
                        SharedPreferences.Editor investedAmount1_editor = investedAmount1.edit();
                        investedAmount1_editor.putInt("key", investedAmount);
                        investedAmount1_editor.apply();





                    }
                    Thread.sleep(10000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();


        Intent myIntent = new Intent(MainActivity.this,
                lender.class);
        startActivity(myIntent);
    }
}
