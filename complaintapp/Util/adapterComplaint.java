package com.example.complaintapp.Util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.complaintapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class adapterComplaint extends RecyclerView.Adapter<adapterComplaint.MyHolder> {
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Context context;
    List<complaintModel> compList;
    View view;

    //constructor


    public adapterComplaint(Context context, List<complaintModel> compList) {
        this.context = context;
        this.compList = compList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
view= LayoutInflater.from(context).inflate(R.layout.row_complaints,parent,false);
        return new MyHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int i) {

        //get data
        String catName=compList.get(i).getCategory();
        String cdate=compList.get(i).getCdate();
        String ctime=compList.get(i).getCtime();
        final String comid=compList.get(i).getComid();
       final String date=compList.get(i).getDate();
        String time=compList.get(i).getTime();
        String location=compList.get(i).getLocation();
        String institute=compList.get(i).getInstitute();
        String detail=compList.get(i).getDetails();
        String people=compList.get(i).getPeople();
        String img=compList.get(i).getImage();
        final String comments=compList.get(i).getComments();
        String status=compList.get(i).getStatus();
        String admincomments=compList.get(i).getComments_of_admin();

        //set data
        holder.catTv.setText("Category      : "+" "+catName);
        holder.cdate.setText("Complaint Date: "+" "+cdate);
        holder.ctime.setText("Complaint Time: "+" "+ctime);
        holder.dateTv.setText("Incident Date     : "+" "+date);
        holder.timeTv.setText("Incident Time    : "+" "+time);
        holder.locTv.setText("Location             : "+" "+location);
        holder.insTv.setText("Institute              : "+" "+institute);
        holder.detailTv.setText("Details                 : "+" "+detail);
        holder.peopleTv.setText("Related               : "+" "+people);
        holder.comTv.setText("Comments                     : "+" "+comments);
        holder.statusTv.setText("Status                              : "+" "+status);
        holder.adminComments.setText("Coments of the Admin: "+" "+admincomments);

        try {
            Picasso.get().load(img).placeholder(R.drawable.no_image).into(holder.avatrIv);
        }
        catch(Exception ex){

        }

        //handle item click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertbox = new AlertDialog.Builder(context);
                alertbox.setTitle("Add Your Comments");
               final EditText editText=new EditText(context);
                alertbox.setView(editText);
                alertbox.setNeutralButton("Comment",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0,
                                                int arg1) {
                                updateComment(editText.getText().toString(),comid,comments);
                            }
                        });
                alertbox.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return compList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{


        TextView catTv,dateTv,timeTv,locTv,insTv,detailTv,peopleTv,comTv,statusTv,adminComments,cdate,ctime;
        ImageView avatrIv;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            catTv=itemView.findViewById(R.id.catN);
            dateTv=itemView.findViewById(R.id.dateN);
            timeTv=itemView.findViewById(R.id.timeN);
            cdate=itemView.findViewById(R.id.cdate);
            ctime=itemView.findViewById(R.id.ctime);
            locTv=itemView.findViewById(R.id.locationN);
            insTv=itemView.findViewById(R.id.instituteN);
            detailTv=itemView.findViewById(R.id.detailN);
            peopleTv=itemView.findViewById(R.id.peopleN);
            avatrIv=itemView.findViewById(R.id.avatrIv);
            comTv=itemView.findViewById(R.id.commentN);
            statusTv=itemView.findViewById(R.id.statusN);
            adminComments=itemView.findViewById(R.id.adcomN);
        }
    }
    public void updateComment(String comment, final String comid,String EarlyComment){
        String comments=comment;
        //init database
        FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        databaseReference = database1.getReference("Complaints");

//update
        comments=EarlyComment+"\r\n"+comments;
        HashMap<String,Object> result=new HashMap<>();
        result.put("comments",comments);
        databaseReference.child(comid).updateChildren(result)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context,"Updated...",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
