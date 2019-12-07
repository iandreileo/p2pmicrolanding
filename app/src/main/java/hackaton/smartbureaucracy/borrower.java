package hackaton.smartbureaucracy;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class borrower extends AppCompatActivity {
    Dialog myDialog;
    public String test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrower);
        myDialog = new Dialog(this);

    }

    @Override public void onStop() {
        super.onStop();
        myDialog.dismiss();
    }

    @Override public void onDestroy() {
        super.onDestroy();
        myDialog.dismiss();
    }
    @Override public void onPause() {
        super.onPause();
        myDialog.dismiss();
    }

    public void cere_oferta(View view) {
        LayoutInflater inflater = (LayoutInflater) borrower.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Inflate the view from a predefined XML layout (no need for root id, using entire layout)
        final View layout = inflater.inflate(R.layout.popup_barrow,null);
        float density=borrower.this.getResources().getDisplayMetrics().density;
        // create a focusable PopupWindow with the given layout and correct size
        final PopupWindow pw = new PopupWindow(layout, (int)density*240, (int)density*285, true);
        //Button to close the pop-up



        (layout.findViewById(R.id.close)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pw.dismiss();
            }
        });

        //Set up touch closing outside of pop-up
        pw.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                pw.setTouchInterceptor(new View.OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent event) {
                        if(event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                            pw.dismiss();
                            return true;
                        }
                        return false;
                    }
                });

        pw.setOutsideTouchable(true);
        // display the pop-up in the center
        pw.showAtLocation(layout, Gravity.CENTER, 0, 0);


        (layout.findViewById(R.id.cere_oferta)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //aici vom prelucra datele si le vom trimite la server




                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {


                            //aici stocam spinnerul si textul
                            EditText amount_popup = (EditText) layout.findViewById(R.id.amount_popup);
                            Spinner months_popup = (Spinner) layout.findViewById(R.id.spinner1);

                          //  JSONObject jsonParam = new JSONObject();
                          //  jsonParam.put("amount", amount_popup.getText().toString());
                          //  jsonParam.put("period", months_popup.getSelectedItem().toString());
                            String charset = "UTF-8";  // Or in Java 7 and later, use the constant: java.nio.charset.StandardCharsets.UTF_8.name()
                            String param1 = amount_popup.getText().toString();
                            String param2 = months_popup.getSelectedItem().toString();
                            String query = String.format("amount=%s&period=%s",
                                    URLEncoder.encode(param1, charset),
                                    URLEncoder.encode(param2, charset));
                          URL url = new URL("https://ylc-blink.herokuapp.com/v1/loan/get-offer" + "?" + query);
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

                         // final String test = obj.getString("success");

                            String test = obj.getString("success");

                            System.out.println(line);



                            Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                            Log.i("MSG" , conn.getResponseMessage());
                            conn.disconnect();

                            if(test.equals("true")){
                                JSONObject data = obj.getJSONObject("data");
                                JSONObject loan = data.getJSONObject("loan");
                                // String accessToken = data.getString("accessToken");
                                String rate = loan.getString("rate");
                                String paybackAmount = loan.getString("paybackAmount");
                                String _id = loan.getString("_id");


                                //memoram ce avem nevoie ulterior
                                SharedPreferences rate1 = getSharedPreferences("rate", MODE_PRIVATE);
                                SharedPreferences.Editor rate_editor = rate1.edit();
                                rate_editor.putString("key", rate);
                                rate_editor.apply();

                                SharedPreferences _id1 = getSharedPreferences("_idborrower", MODE_PRIVATE);
                                SharedPreferences.Editor _id1_editor = _id1.edit();
                                _id1_editor.putString("key", _id);
                                _id1_editor.apply();

                                SharedPreferences paybackAmount1 = getSharedPreferences("paybackAmount", MODE_PRIVATE);
                                SharedPreferences.Editor paybackAmount1_editor = paybackAmount1.edit();
                                paybackAmount1_editor.putString("key", paybackAmount);
                                paybackAmount1_editor.apply();

                                System.out.println(paybackAmount1.getString("key", ""));
                                System.out.println(_id1.getString("key", ""));



                            }
                            Thread.sleep(10000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();


                    close();
                    pw.dismiss();

            }
        });

    }

    public void close(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        myDialog.dismiss();
        myDialog.cancel();
        //pw.dismiss();


        //deschidem alt popup
        Intent i = new Intent(borrower.this, barrow_offert.class);       // Specify any activity here e.g. home or splash or login etc
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("EXIT", true);
        startActivity(i);
        finish();
    }

    public void error(){
        //deschidem alt popup

        Toast.makeText(getApplicationContext(),
                "No offer for you!", Toast.LENGTH_LONG).show();

        Intent i = new Intent(borrower.this, MainActivity.class);       // Specify any activity here e.g. home or splash or login etc
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("EXIT", true);
        startActivity(i);
        finish();
    }

    public void accept(View view) {

    }
}

