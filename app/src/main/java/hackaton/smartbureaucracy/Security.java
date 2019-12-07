package hackaton.smartbureaucracy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Security extends AppCompatActivity {

    TextView textLight, textMedium;
    EditText scOne, scTwo, scThree;
    Button btnPhone, btnVerify;
    ImageView icprotection;
    Animation iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);

        iv = AnimationUtils.loadAnimation(this, R.anim.iv);

        textLight = (TextView) findViewById(R.id.textLight);
        textMedium = (TextView) findViewById(R.id.textMedium);

        icprotection = (ImageView) findViewById(R.id.icprotection);
        icprotection.setVisibility(View.GONE);

        scOne = (EditText) findViewById(R.id.scOne);
        scTwo = (EditText) findViewById(R.id.scTwo);
        scThree = (EditText) findViewById(R.id.scThree);

        btnPhone = (Button) findViewById(R.id.btnPhone);
        btnVerify = (Button) findViewById(R.id.btnVerify);

        Typeface lightx = Typeface.createFromAsset(getAssets(), "fonts/MontserratLight.ttf");
        Typeface mediumx = Typeface.createFromAsset(getAssets(), "fonts/MontserratMedium.ttf");

        textLight.setTypeface(lightx);
        textMedium.setTypeface(mediumx);

        scOne.setTypeface(mediumx);
        scTwo.setTypeface(mediumx);
        scThree.setTypeface(mediumx);

        btnVerify.setAlpha(0);
        btnVerify.setTranslationY(80);

        btnPhone.setTypeface(lightx);

        scThree.setOnEditorActionListener(new EditText.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
                if(actionId == EditorInfo.IME_ACTION_DONE) {
                    doValidate();
                    return false;
                } else {
                    return false;
                }
            }
        });



    }

    public void doValidate(){
        String answerx;
       if (!getSharedPreferences("Parola", MODE_PRIVATE).getString("key", "").equals("")){
           answerx = getSharedPreferences("Parola", MODE_PRIVATE).getString("key","");
      } else {
            answerx = "000";
        }


        String onex = scOne.getText().toString();
        //int finalOnex = Integer.parseInt(onex);

        String twox = scTwo.getText().toString();
      //  int finalTwox = Integer.parseInt(twox);

        String threex = scThree.getText().toString();
      //  int finalThreex = Integer.parseInt(threex);

        String holderx = onex + twox + threex;

        if (holderx.equals(answerx)) {

            icprotection.setVisibility(View.VISIBLE);

            icprotection.startAnimation(iv);

            scOne.animate().translationY(350).setDuration(600).start();
            scThree.animate().translationY(350).setDuration(600).start();
            scTwo.animate().translationY(350).setDuration(600).start();

            textLight.setText("Codul tău de securitate e corect!");
            textMedium.setText("Perfect!");

            textLight.animate().translationY(500).setDuration(600).start();
            textMedium.animate().translationY(500).setDuration(600).start();

            btnPhone.animate().alpha(0).translationY(100).setDuration(600).setStartDelay(300).start();
            btnVerify.animate().alpha(1).translationY(0).setDuration(600).setStartDelay(300).start();
        } else {
            gresit();
            Toast.makeText(getApplicationContext(),"Cod gresit. Codul initial este 000.", Toast.LENGTH_SHORT).show();
        }

    }

    public void continua(View view) {

        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else{
            connected = false;
        }

            if(connected) {


                //mergem la prima activitate
                Intent i = new Intent(Security.this, MainActivity.class);       // Specify any activity here e.g. home or splash or login etc
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("EXIT", true);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(getApplicationContext(),"Pentru a intra în aplicație ai nevoie de internet.", Toast.LENGTH_SHORT).show();
            }
    }



    public void gresit(){
        scOne.setText("");
        scTwo.setText("");
        scThree.setText("");

    }

    public void verifica(View view) {
        doValidate();
    }
}












