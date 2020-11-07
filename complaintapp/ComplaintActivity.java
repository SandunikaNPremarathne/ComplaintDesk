package com.example.complaintapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;

import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

import static android.media.CamcorderProfile.get;

public class ComplaintActivity extends AppCompatActivity {

    private FirebaseUser currentFirebaseUser;
    private DatabaseReference reference1;

    private FirebaseStorage storage;
    private StorageReference storageReference;

    String storagePath="complaint_imgs/";
    private String category;
    private String dateE;
    private String timeE;
    private String dE;
    private String cE;
    private String iE;
    Button submitBtn;
    private ImageView cphoto;
    private static final int IMAGE_PICK_GALLERY_REQUEST_CODE=400;
    String imagecomp;
    Uri image_uri;
    private String downloadUri;

    ProgressDialog pd;
    TextView incDate;
    //location


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        pd=new ProgressDialog(this);
       TextView date1=findViewById(R.id.date);
        TextView time1=findViewById(R.id.time);
        incDate= (TextView) findViewById(R.id.date);
        TextView loc=findViewById(R.id.location);
        TextView ins=findViewById(R.id.Institute);


        SimpleDateFormat dateF=new SimpleDateFormat("EEE, dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat timeF=new SimpleDateFormat("HH:mm",Locale.getDefault());
        String todaydate=dateF.format(Calendar.getInstance().getTime());
        String todaytime=timeF.format(Calendar.getInstance().getTime());
      //  date1.setText(date2);
     //   time1.setText(time2);


        //location




        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Intent intent = getIntent();
        if (intent.hasExtra("category")) {
            category = intent.getStringExtra("category");
            dateE=intent.getStringExtra("date");
            timeE=intent.getStringExtra("time");
            dE=intent.getStringExtra("district");
            cE=intent.getStringExtra("city");
            iE=intent.getStringExtra("insti");
            date1.setText(dateE);
            time1.setText(timeE);
            loc.setText(dE+" , "+cE);
            ins.setText(iE);
          //  Toast.makeText(this, category, Toast.LENGTH_SHORT).show();
        }
        cphoto=findViewById(R.id.cphoto);
        submitBtn = findViewById(R.id.submit);

     //   FirebaseApp.initializeApp(this);

        if (category != null) {

/*
            View.OnClickListener datechooser=new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            };
            dateEt.setOnClickListener(datechooser);

 */
            View.OnClickListener listnerUpdate = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendFeedback(category);
                }

            };

            submitBtn.setOnClickListener(listnerUpdate);

            View.OnClickListener imagelistner = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imagecomp="image";
                    pickFromGallery();
                }

            };
            cphoto.setOnClickListener(imagelistner);
            storage = FirebaseStorage.getInstance();
            storageReference = storage.getReference();




        }

    }//end Oncreate

    public void sendFeedback(String categorys) {

        //progressDialog.dismiss();
        pd.setMessage("Complaint is getting Uploaded");
        //Get user email and uid from auth
        String email = currentFirebaseUser.getEmail();
        String uid = currentFirebaseUser.getUid();


        final TextView incdate = (TextView) findViewById(R.id.date);
        String incdateS = incdate.getText().toString();

        final TextView inctime = (TextView) findViewById(R.id.time);
        String inctimeS = inctime.getText().toString();

        final TextView loca = (TextView) findViewById(R.id.location);
        String locaS = loca.getText().toString();

        final TextView insti = (TextView) findViewById(R.id.Institute);
        String institute = insti.getText().toString();

        final EditText person = (EditText) findViewById(R.id.people);
        String personS = person.getText().toString();

        final EditText detail = (EditText) findViewById(R.id.details);
        String detailS = detail.getText().toString();

        SimpleDateFormat dateF2=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String date3=dateF2.format(Calendar.getInstance().getTime());


        SimpleDateFormat timeF=new SimpleDateFormat("HH:mm",Locale.getDefault());
        String time2=timeF.format(Calendar.getInstance().getTime());


        Random ran = new Random();
        int rannum = ran.nextInt(500 - 10) + 10;
        final String comid = "com" + uid + rannum;


        final HashMap<Object, String> hashmap = new HashMap<>();

        hashmap.put("comid",comid);
        hashmap.put("uid",uid);
        hashmap.put("category",categorys);
        hashmap.put("c_date",date3);
        hashmap.put("c_time",time2);
        hashmap.put("in_date",incdateS);
        hashmap.put("in_time",inctimeS);
        hashmap.put("inc_location",locaS);
        hashmap.put("inc_dis",dE);
        hashmap.put("inc_city",cE);
        hashmap.put("institute",institute);
        hashmap.put("people",personS);
        hashmap.put("details",detailS);
        hashmap.put("comments","");
        hashmap.put("status","Submitted");
        hashmap.put("comments_of_admin","");

        String filePath = storagePath + "" + imagecomp + "_" + comid;

        FirebaseDatabase database1 = FirebaseDatabase.getInstance();

        //path to store user data named "Users"
        reference1 = database1.getReference("Complaints");


        if (image_uri != null) {

            final StorageReference storageReference2 = storageReference.child(filePath);

            // storageReference2.putFile(image_uri);
            storageReference2.putFile(image_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful()) ;
                    Uri downloadUri = uriTask.getResult();
                    if (uriTask.isSuccessful()) {
                        hashmap.put("image", downloadUri.toString());
                        pd.dismiss();
                        reference1.child(comid).setValue(hashmap);
                        //Toast.makeText(ComplaintActivity.this, "Submitted...\n"+currentFirebaseUser.getEmail(),Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ComplaintActivity.this, ThankYouActivity.class));
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Some Problem occured", Toast.LENGTH_SHORT).show();
                    }

                }


            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });




        }
        else{
            //put data within hashmap in database
            reference1.child(comid).setValue(hashmap);
            Toast.makeText(ComplaintActivity.this, "Submitted...",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ComplaintActivity.this, ThankYouActivity.class));
            finish();
        }
    }

    private void pickFromGallery() {
        Intent gintent=new Intent(Intent.ACTION_PICK);
        gintent.setType("image/*");
        //new code
        gintent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        gintent.setAction(Intent.ACTION_GET_CONTENT);

        // getActivity().startActivityForResult(gintent, IMAGE_PICK_GALLERY_REQUEST_CODE);
        startActivityForResult(gintent,IMAGE_PICK_GALLERY_REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICK_GALLERY_REQUEST_CODE && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
                        image_uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), image_uri);
                cphoto.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}