package com.example.complaintapp;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;
import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class ProfileFragment extends Fragment {

   // private static final int PICK_IMAGE_REQUEST = 71;
    //firebase
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    //storage
    StorageReference storageReference;
    //path where user profile and cover photo will be stored
    String storagePath="Users_profile_cover_imgs/";
    //views from xml
    ImageView avatarIv;
    TextView nameTv, emailTv,phoneTv, addressTv,NICTv;
    FloatingActionButton fab;

    private Uri filePath;
    private ImageView photoPreview;
    //progress dialog
    ProgressDialog pd;
    Button logout;

    private static final String TAG = "ProfileFragment";

    //permission constants
    private static final int CAMERA_REQUEST_CODE=100;
    private static final int STORAGE_REQUEST_CODE=200;
    private static final int IMAGE_PICK_GALLERY_REQUEST_CODE=300;
    private static final int IMAGE_PICKCAMERA_REQUEST_CODE=400;

    //ARRAYS OF PERMISSION TO BE REQUESTED
    String cameraPermissions[];
    String storagePermissions[];

    //url of picked image
    Uri image_uri;

    //for checking profile or cover photo
    String ProfilrorCoverPhoto;
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view=inflater.inflate(R.layout.profie, container, false);


        //init database
        firebaseAuth=FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Users");
        storageReference=getInstance().getReference();
        //init arrays of permission
      //  cameraPermissions=new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions=new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        //init views
        avatarIv=view.findViewById(R.id.avatarIv);
//        coverIv=view.findViewById(R.id.coverTv);
        nameTv=view.findViewById(R.id.nameTv);
        emailTv=view.findViewById(R.id.emailTv);
        phoneTv=view.findViewById(R.id.phoneTv);
        addressTv=view.findViewById(R.id.addressTv);
        NICTv=view.findViewById(R.id.NICTv);
        fab=view.findViewById(R.id.fab);
        logout=view.findViewById(R.id.yes);
        //init progress dialog
        pd=new ProgressDialog(getActivity());

        Query query=databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //check until required data get
                for(DataSnapshot ds:dataSnapshot.getChildren()){

                    //get data
                    String name=""+ds.child("name").getValue();
                    String email=""+ds.child("email").getValue();
                    String phone=""+ds.child("phone").getValue();
                    String address=""+ds.child("Address").getValue();
                    String nic=""+ds.child("NIC").getValue();
                    String image=""+ds.child("image").getValue();
                  //  String cover=""+ds.child("cover").getValue();

                    //set data
                    nameTv.setText(name);
                    emailTv.setText(email);
                    phoneTv.setText(phone);
                    addressTv.setText(address);
                    NICTv.setText(nic);
                    try{
                        Picasso.get().load(image).into(avatarIv);
                    }
                    catch (Exception e){
                        Picasso.get().load(R.drawable.clip_log).into(avatarIv);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //fab button click
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditProfileDialog();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                checkuserStatus();
            }
        });

return  view;
    }

//On fab click
    private void showEditProfileDialog() {
        //Options to show in dialod
        String options[]={"Edit Profile Picture","Edit Name","Edit Mobile","Edit Address"};

        //alert dialog
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        //set title
        builder.setTitle("Choose Action");
        //set items to dialog
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //handle dialog item clicks
                if(which==0){
                    pd.setMessage("Updateing Profile Picture");
                    ProfilrorCoverPhoto = "image";
                    pickFromGallery();
                }

                else if(which==1){
                    pd.setMessage("Updateing Name");
                    showNamePhoneUpdateDialog("name");
                }
                else if(which==2){
                    pd.setMessage("Updateing Mobile");
                    showNamePhoneUpdateDialog("phone");
                }
                else if(which==3){
                    pd.setMessage("Updateing Address");
                    showNamePhoneUpdateDialog("Address");
                }
            }
        });
        //create show dialog
        builder.create().show();
    }

    private void showNamePhoneUpdateDialog(final String key) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Update "+key);
        LinearLayout linearLayout=new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(10,10,10,10);
        final EditText editText=new EditText(getActivity());
        editText.setHint("Enter "+key);
        linearLayout.addView(editText);
        builder.setView(linearLayout);
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String value=editText.getText().toString().trim();
                if (!TextUtils.isEmpty(value)){
                    pd.show();
                    HashMap<String,Object> result=new HashMap<>();
                    result.put(key,value);
                    databaseReference.child(user.getUid()).updateChildren(result)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    pd.dismiss();
                                    Toast.makeText(getActivity(),"Updated...",Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();
                                    Toast.makeText(getActivity(),""+e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            });
                }

                else{
                    Toast.makeText(getActivity(),"Please Enter"+key,Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
/*showimagedialog
    private void showImagePicDialog() {
        String options[]={"Gallery"};

        //alert dialog
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        //set title
        builder.setTitle("Pick Image from");
        //set items to dialog
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //handle dialog item clicks
                if(which==0){
                    //camera clicked
                 //   if(!checkCameraPermission()){
                 //       checkCameraPermission();
                 //   }
                //    else{
                 //       pickFromCamera();
                 //   }
              //  }
               // else if(which==1){
                    //Gallery clicked
                    if(!checkStoragePermission()){
                        checkStoragePermission();
                    }
                    else{
                        pickFromGallery();
                    }
                }

            }
        });
        //create show dialog
        builder.create().show();
    }

 */
/*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch(requestCode) {
            case CAMERA_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && writeStorageAccepted) {
                      //  pickFromCamera();
                    } else {
                        Toast.makeText(getActivity(), "Please enable Camera and Storage Permission", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            break;
            case STORAGE_REQUEST_CODE:{
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (writeStorageAccepted) {
                        pickFromGallery();
                    } else {
                        Toast.makeText(getActivity(), "Please enable Storage Permission", Toast.LENGTH_SHORT).show();
                    }

                }

            }
            break;
        }
    }


 */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK){
            if(requestCode==IMAGE_PICK_GALLERY_REQUEST_CODE){
                //image is picked from gallery, get uri of image
                image_uri=data.getData();
                uploadProfileCoverPhoto(image_uri);

            }
            if(requestCode==IMAGE_PICKCAMERA_REQUEST_CODE){
                //image is picked from camara
                uploadProfileCoverPhoto(image_uri);
            }

        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void uploadProfileCoverPhoto(Uri uri) {
        //show progress
        pd.show();
        //path and name of image to be stored in the firebase
        String filePathAndName=storagePath+""+ProfilrorCoverPhoto+"_"+user.getUid();

        StorageReference storageReference2=storageReference.child(filePathAndName);


        storageReference2.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isSuccessful());
                Uri downloadUri=uriTask.getResult();
                if(uriTask.isSuccessful()){
                    HashMap<String, Object> results=new HashMap<>();
                    results.put(ProfilrorCoverPhoto,downloadUri.toString());
                    databaseReference.child(user.getUid()).updateChildren(results)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    pd.dismiss();
                                    Toast.makeText(getActivity(),"Image Updated...",Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(getActivity(),"Error in Image Updating...",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    pd.dismiss();Toast.makeText(getActivity(),"Some Problem occured",Toast.LENGTH_SHORT).show();
                }

            }


        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

    }

   /*
    private void pickFromCamera() {

        ContentValues values=new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"Temp pic");
        values.put(MediaStore.Images.Media.DESCRIPTION,"Temp Description");
        //put image uri
        image_uri=getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
        startActivityForResult(cameraIntent,IMAGE_PICKCAMERA_REQUEST_CODE);
    }
*/
    private void pickFromGallery() {
        Intent gintent=new Intent(Intent.ACTION_PICK);
        gintent.setType("image/*");
       // getActivity().startActivityForResult(gintent, IMAGE_PICK_GALLERY_REQUEST_CODE);
       startActivityForResult(gintent,IMAGE_PICK_GALLERY_REQUEST_CODE);
    }

    private void checkuserStatus() {
        FirebaseUser user=firebaseAuth.getCurrentUser();
        if(user!=null){

        }
        else{
            //go to main activity
            startActivity(new Intent(getActivity(), MainActivity.class));
        }
    }
}
