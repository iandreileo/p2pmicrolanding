package hackaton.smartbureaucracy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class barrow_offert extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barrow_offert);
        TextView textView = (TextView) findViewById(R.id.rate);
        textView.setText(getSharedPreferences("rate", MODE_PRIVATE).getString("key",""));

        TextView textView1 = (TextView) findViewById(R.id.payback);
        textView1.setText(getSharedPreferences("paybackAmount", MODE_PRIVATE).getString("key",""));
    }

    public void accept(View view) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {



                    String param1 = getSharedPreferences("_idborrower", MODE_PRIVATE).getString("key","");

                    URL url = new URL("https://ylc-blink.herokuapp.com/v1/loan/approve/");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setRequestProperty("Authorization", "Bearer " + getSharedPreferences("accessToken", MODE_PRIVATE).getString("key",""));
                    conn.setDoOutput(true);
                    conn.setDoInput(true);



                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("loanId", param1);

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

                    String test = obj.getString("success");
                    System.out.println(line);

                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG" , conn.getResponseMessage());
                    conn.disconnect();

                    if(test.equals("true")){

                      //  Toast.makeText(barrow_offert.this, "Operation successfully completed!", Toast.LENGTH_SHORT).show();
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {




                                    //  JSONObject jsonParam = new JSONObject();
                                    //  jsonParam.put("amount", amount_popup.getText().toString());
                                    //  jsonParam.put("period", months_popup.getSelectedItem().toString());
                                    String charset = "UTF-8";  // Or in Java 7 and later, use the constant: java.nio.charset.StandardCharsets.UTF_8.name()

                                    URL url = new URL("https://ylc-blink.herokuapp.com/v1/user/get-data");
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

                                    //refresh amount
                                    JSONObject data = obj.getJSONObject("data");
                                    JSONObject user = data.getJSONObject("user");
                                    int newAmount = user.getInt("amount");


                                    SharedPreferences newAmount1 = getSharedPreferences("amount", MODE_PRIVATE);
                                    SharedPreferences.Editor newAmount1_editor = newAmount1.edit();
                                    newAmount1_editor.putInt("key", newAmount);
                                    newAmount1_editor.apply();




                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        thread.start();



                            Intent i = new Intent(barrow_offert.this, MainActivity.class);       // Specify any activity here e.g. home or splash or login etc
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.putExtra("EXIT", true);
                            startActivity(i);
                            finish();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public void decline(View view) {
        Toast.makeText(barrow_offert.this, "Operation failed!", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(barrow_offert.this, MainActivity.class);       // Specify any activity here e.g. home or splash or login etc
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("EXIT", true);
        startActivity(i);
        finish();
    }
}

