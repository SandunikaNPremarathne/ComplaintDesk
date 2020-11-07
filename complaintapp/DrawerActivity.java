package com.example.complaintapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    TextView click;

    private AppBarConfiguration mAppBarConfiguration;

    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        firebaseAuth= FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Users");
        click=(TextView)findViewById(R.id.click);
        Spanned Text = Html.fromHtml("<a href='https://www.google.com'>Terms & Conditions</a>");
        click.setMovementMethod(LinkMovementMethod.getInstance());
        click.setText(Text);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_newc, R.id.nav_myc, R.id.nav_profile,R.id.nav_about,R.id.nav_use)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        View headview=navigationView.getHeaderView(0);
        final ImageView profile=headview.findViewById(R.id.imageView);
        final TextView nameTv=headview.findViewById(R.id.textView1);
        final TextView emailTv=headview.findViewById(R.id.textView2);


        Query query=databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //check until required data get
                for(DataSnapshot ds:dataSnapshot.getChildren()){

                    //get data
                    String name=""+ds.child("name").getValue();
                    String email=""+ds.child("email").getValue();
                    String image=""+ds.child("image").getValue();
                    //  String cover=""+ds.child("cover").getValue();

                    //set data
                    nameTv.setText(name);
                    emailTv.setText(email);

                    try{
                        Picasso.get().load(image).into(profile);
                    }
                    catch (Exception e){
                        Picasso.get().load(R.drawable.clip_log).into(profile);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

 */

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        pd=new ProgressDialog(this);
        pd.setMessage("Logging out...");
        int id=item.getItemId();
        FragmentManager fragmentManager=getSupportFragmentManager();
        if(id==R.id.nav_newc){
            fragmentManager.beginTransaction().replace(R.id.content_frame, new NewComplaintFragment()).commit();
        }
        else if(id==R.id.nav_myc){
            fragmentManager.beginTransaction().replace(R.id.content_frame, new MyComplaintsFragment()).commit();
        }
        else if(id==R.id.nav_profile){
            Intent intent = new Intent(DrawerActivity.this,ProfileFragment.class);
            startActivity(intent);
        }
        else if(id==R.id.nav_about){
            fragmentManager.beginTransaction().replace(R.id.content_frame, new AboutFragment()).commit();
        }
        else if(id==R.id.nav_use){
            fragmentManager.beginTransaction().replace(R.id.content_frame, new UseFragment()).commit();
        }
        DrawerLayout drawer=(DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    /*
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //get item id
        int id=item.getItemId();
        if(id==R.id.action_logout){
            firebaseAuth.signOut();
            checkuserStatus();
        }
        return super.onOptionsItemSelected(item);
    }


     */
    private void checkuserStatus(){
        FirebaseUser user=firebaseAuth.getCurrentUser();
        if(user!=null){
            //user is logged in, stay here
            //set email of logged in user
            //mProfileTV.setText(user.getEmail());
        }
        else{
            //go to main activity
            startActivity(new Intent(DrawerActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onStart() {
        //check on start of app
        checkuserStatus();
        super.onStart();
    }
}
