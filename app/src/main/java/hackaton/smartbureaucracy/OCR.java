package hackaton.smartbureaucracy;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.net.Uri;

import android.support.annotation.NonNull;

import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextDetector;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;


public class OCR extends BaseActivity implements View.OnClickListener {

    private Bitmap myBitmap;
    private ImageView myImageView;
    private TextView myTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr);

        myTextView = findViewById(R.id.textView);
        myImageView = findViewById(R.id.imageView);
        findViewById(R.id.checkText).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.checkText:
                if (myBitmap != null) {
                    runTextRecog();
                }
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case WRITE_STORAGE:
                    checkPermission(requestCode);
                    break;
                case SELECT_PHOTO:
                    Uri dataUri = data.getData();
                    String path = MyHelper.getPath(this, dataUri);
                    if (path == null) {
                        myBitmap = MyHelper.resizePhoto(photo, this, dataUri, myImageView);
                    } else {
                        myBitmap = MyHelper.resizePhoto(photo, path, myImageView);
                    }
                    if (myBitmap != null) {
                        myTextView.setText(null);
                      //  myImageView.setImageBitmap(myBitmap);
                    }
                    break;

            }
        }
    }

    private void runTextRecog() {
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(myBitmap);
        FirebaseVisionTextDetector detector = FirebaseVision.getInstance().getVisionTextDetector();
        detector.detectInImage(image).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText texts) {
                processExtractedText(texts);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure
                    (@NonNull Exception exception) {
                Toast.makeText(OCR.this,
                        "Exception", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void processExtractedText(FirebaseVisionText firebaseVisionText) {
        myTextView.setText(null);
        if (firebaseVisionText.getBlocks().size() == 0) {
            myTextView.setText("No text");
            return;
        }
        for (FirebaseVisionText.Block block : firebaseVisionText.getBlocks()) {
          //  myTextView.append(block.getText());
            String current = block.getText();


            System.out.println("--------------------------------------------");
          //  System.out.println(current);




            if(current.contains("SERIA")){

                final String[] parts = current.split(" ");

                System.out.println("Seria buletin: ");
                System.out.println(parts[3]);//seria buletin

                SharedPreferences serie_pfiz = getSharedPreferences("Serie_pfiz", MODE_PRIVATE);
                SharedPreferences.Editor seriepfiz_editor = serie_pfiz.edit();
                seriepfiz_editor.putString("key", parts[3]);
                seriepfiz_editor.apply();

                System.out.println("NR buletin: ");
                System.out.println(parts[5]);//NR buletin

                SharedPreferences Nrbul_pfiz = getSharedPreferences("Nrbul_pfiz", MODE_PRIVATE);
                SharedPreferences.Editor nrbulpfiz_editor = Nrbul_pfiz.edit();
                nrbulpfiz_editor.putString("key", parts[5]);
                nrbulpfiz_editor.apply();

                myTextView.append("Seria ta de buletin este:" + parts[3]);
                myTextView.append("\n");
                myTextView.append("Numărul tau de buletin este:" + parts[5]);
                myTextView.append("\n");
            } // e bine
            if(current.contains("CNP")){

                System.out.println(current);
                System.out.println("--------------------------------------------");
                System.out.println("CNP: ");
                final String[] CNP1 = current.split(" ");
                final String[] CNP = CNP1[1].split("Nume");
                System.out.println(CNP[0]);//CNP --- e bine
                String CNP_FINAL = CNP[0].replace("\n", "");

                SharedPreferences CNP_pfiz = getSharedPreferences("CNP_pfiz", MODE_PRIVATE);
                SharedPreferences.Editor cnppfiz_editor = CNP_pfiz.edit();
                cnppfiz_editor.putString("key", CNP_FINAL);
                cnppfiz_editor.apply();

                myTextView.append("CNP-ul tau este:" + CNP_FINAL);
                myTextView.append("\n");

                System.out.println("--------------------------------------------");

                final String[] nume1 = current.split(" name");
                final String[] nume2 = nume1[1].split("Pre");
                System.out.println(nume2[0]);//nume
                String NUME_FINAL = nume2[0].replace("\n", "");

                SharedPreferences nume_pfiz = getSharedPreferences("Nume_pfiz", MODE_PRIVATE);
                SharedPreferences.Editor numepfiz_editor = nume_pfiz.edit();
                numepfiz_editor.putString("key", NUME_FINAL);
                numepfiz_editor.apply();

                myTextView.append("Numele tău este:" + NUME_FINAL);
                myTextView.append("\n");

                final String[] nume3 = nume1[2].split("Cet");
                System.out.println(nume3[0]);//prenume
                String PRENUME_FINAL = nume3[0].replace("\n", "");
                SharedPreferences prenume_pfiz = getSharedPreferences("Prenume_pfiz", MODE_PRIVATE);
                SharedPreferences.Editor prenumepfiz_editor = prenume_pfiz.edit();
                prenumepfiz_editor.putString("key", PRENUME_FINAL);
                prenumepfiz_editor.apply();

                myTextView.append("Prenumele tău este:" + PRENUME_FINAL);
                myTextView.append("\n");



            }

            if(current.contains("Str") || current.contains("Sat")){
                System.out.println("Domiciliu tau este");
                System.out.println(block.getText());

                myTextView.append("Domiciliul tău este:" + block.getText());



            } // e bine

          //  System.out.println(current);


        }
    }



}

