package com.example.complaintapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.complaintapp.Util.Constants;

import java.util.zip.Inflater;

public class NewComplaintFragment extends Fragment {
    @Nullable

    View myview;

    private CardView agri,bank,regi,media,edu,emp,env,health,house,law,busi,travel;

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myview= inflater.inflate(R.layout.new_complaint,container,false);
        agri  = myview.findViewById(R.id.agri);
        bank  = myview.findViewById(R.id.bank);
        regi  = myview.findViewById(R.id.regi);
        media  = myview.findViewById(R.id.media);
        edu  = myview.findViewById(R.id.edu);
        emp  = myview.findViewById(R.id.emp);
        env  = myview.findViewById(R.id.env);
        health  = myview.findViewById(R.id.health);
        house  = myview.findViewById(R.id.house);
        law = myview.findViewById(R.id.law);
        busi  = myview.findViewById(R.id.busi);
        travel  = myview.findViewById(R.id.travel);

        View.OnClickListener listener  = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DatetimeActivity.class);
                if (v == agri)
                    intent.putExtra("category", Constants.CATEGORY_AGRICULTURE);
                else if(v==bank)
                    intent.putExtra("category", Constants.CATEGORY_BANK);
                else if(v==regi)
                    intent.putExtra("category", Constants.CATEGORY_REGISTER);
                else if(v==media)
                    intent.putExtra("category", Constants.CATEGORY_MEDIA);
                else if(v==edu)
                    intent.putExtra("category", Constants.CATEGORY_EDUCATION);
                else if(v==emp)
                    intent.putExtra("category", Constants.CATEGORY_EMPLOYMENT);
                else if(v==env)
                    intent.putExtra("category", Constants.CATEGORY_ENVIRONMENT);
                else if(v==health)
                    intent.putExtra("category", Constants.CATEGORY_HEALTH);
                else if(v==house)
                    intent.putExtra("category", Constants.CATEGORY_HOUSE);
                else if(v==law)
                    intent.putExtra("category", Constants.CATEGORY_LAW);
                else if(v==busi)
                    intent.putExtra("category", Constants.CATEGORY_TRADE);
                else if(v==travel)
                    intent.putExtra("category", Constants.CATEGORY_TRAVEL);
                    startActivity(intent);


            }
        };

        agri.setOnClickListener(listener);
        bank.setOnClickListener(listener);
        regi.setOnClickListener(listener);
        media.setOnClickListener(listener);
        edu.setOnClickListener(listener);
        emp.setOnClickListener(listener);
        env.setOnClickListener(listener);
        health.setOnClickListener(listener);
        house.setOnClickListener(listener);
        law.setOnClickListener(listener);
        busi.setOnClickListener(listener);
        travel.setOnClickListener(listener);
        return myview;
    }


}
