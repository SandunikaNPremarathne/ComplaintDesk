package com.example.complaintapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.complaintapp.Util.adapterComplaint;
import com.example.complaintapp.Util.complaintModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MyComplaintsFragment extends Fragment {

 public MyComplaintsFragment(){

    }
    RecyclerView recyclerView;
    adapterComplaint adapterComplaints;
    List<complaintModel> list;
    FirebaseUser fuser;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View myview= inflater.inflate(R.layout.my_complaints,container,false);
        recyclerView=myview.findViewById(R.id.users_recyclerView);
      //  recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        list=new ArrayList<>();
        getAllComp();
        return myview;
    }

    private void getAllComp() {
        fuser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference dref= FirebaseDatabase.getInstance().getReference("Complaints");
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    complaintModel model=new complaintModel();
                    model=ds.getValue(complaintModel.class);
                   // model.setCategory((String) ds.child("categoty").getValue());
                    model.setComid((String) ds.child("comid").getValue());
                    model.setDetails((String) ds.child("details").getValue());
                    model.setCdate((String) ds.child("c_date").getValue());
                    model.setCtime((String) ds.child("c_time").getValue());
                    model.setDate((String) ds.child("in_date").getValue());
                    model.setTime((String) ds.child("in_time").getValue());
                    model.setLocation((String) ds.child("inc_location").getValue());
                    model.setInstitute((String)ds.child("institute").getValue());
                    model.setPeople((String) ds.child("people").getValue());
                    model.setUid((String) ds.child("uid").getValue());
                    model.setImage((String) ds.child("image").getValue());
                    model.setComments((String) ds.child("comments").getValue());
                    model.setStatus((String) ds.child("status").getValue());
                    if(model.getUid().equals(fuser.getUid()) && !model.getStatus().equals("delete")){
                        list.add(model);
                    }
                    //adapter
                    adapterComplaints=new adapterComplaint(getActivity(),list);
                    //set adapters to recylclers view
                    recyclerView.setAdapter(adapterComplaints);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
