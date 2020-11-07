package com.example.complaintapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ManualActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] options;
    String[] options1;
    String[] options2;
    Button next;
    EditText other;

    private String category;
    private String dateE;
    private String timeE;

    // Button next=findViewById(R.id.nextbtn);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        Intent intent = getIntent();
        if (intent.hasExtra("category")) {
            category = intent.getStringExtra("category");
            dateE=intent.getStringExtra("date");
            timeE=intent.getStringExtra("time");
            //  Toast.makeText(this, category, Toast.LENGTH_SHORT).show();
        }


        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        other=(EditText)findViewById(R.id.other);
        // Creating ArrayAdapter using the string array and default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Districts, android.R.layout.simple_spinner_item);
        // Specify layout to be used when list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applying the adapter to our spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        options = ManualActivity.this.getResources().getStringArray(R.array.Districts);
        next=findViewById(R.id.nextbtn);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String spinD=spinner.getSelectedItem().toString();
                String spinC=spinner1.getSelectedItem().toString();
                String spinI=spinner2.getSelectedItem().toString();
                if(spinI.equals("Select Institute")){
                    spinI=other.getText().toString();
                }
                Intent intent = new Intent(ManualActivity.this, ComplaintActivity.class);
                intent.putExtra("category", category);
                intent.putExtra("date",dateE);
                intent.putExtra("time",timeE);
                intent.putExtra("district",spinD);
                intent.putExtra("city",spinC);
                intent.putExtra("insti",spinI);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);

        if(options[position].equals("Ampara")){
            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                    R.array.Ampara, android.R.layout.simple_spinner_item);
            options1 = ManualActivity.this.getResources().getStringArray(R.array.Ampara);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(adapter1);
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Ampara1, android.R.layout.simple_spinner_item);
            options2 = ManualActivity.this.getResources().getStringArray(R.array.Ampara1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(options[position].equals("Anuradhapura")){
            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                    R.array.Anuradhapura, android.R.layout.simple_spinner_item);
            options1 = ManualActivity.this.getResources().getStringArray(R.array.Anuradhapura);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(adapter1);
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Anuradhapura1, android.R.layout.simple_spinner_item);
            options2 = ManualActivity.this.getResources().getStringArray(R.array.Anuradhapura1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(options[position].equals("Badulla")){
            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                    R.array.Badulla, android.R.layout.simple_spinner_item);
            options1 = ManualActivity.this.getResources().getStringArray(R.array.Badulla);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(adapter1);
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Badulla1, android.R.layout.simple_spinner_item);
            options2 = ManualActivity.this.getResources().getStringArray(R.array.Badulla1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(options[position].equals("Batticaloa")){
            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                    R.array.Batticaloa, android.R.layout.simple_spinner_item);
            options1 = ManualActivity.this.getResources().getStringArray(R.array.Batticaloa);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(adapter1);
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Batticaloa1, android.R.layout.simple_spinner_item);
            options2 = ManualActivity.this.getResources().getStringArray(R.array.Batticaloa1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(options[position].equals("Colombo")){
            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                    R.array.Colombo, android.R.layout.simple_spinner_item);
            options1 = ManualActivity.this.getResources().getStringArray(R.array.Colombo);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(adapter1);
            //text.setText(options[position]+" , "+options1[position]);
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Colombo1, android.R.layout.simple_spinner_item);
            options2 = ManualActivity.this.getResources().getStringArray(R.array.Colombo1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(options[position].equals("Galle")){
            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                    R.array.Galle, android.R.layout.simple_spinner_item);
            options1 = ManualActivity.this.getResources().getStringArray(R.array.Galle);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(adapter1);
            //text.setText(options[position]+" , "+options1[position]);
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Galle1, android.R.layout.simple_spinner_item);
            options2 = ManualActivity.this.getResources().getStringArray(R.array.Galle1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(options[position].equals("Gampaha")){
            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                    R.array.Gampaha, android.R.layout.simple_spinner_item);
            options1 = ManualActivity.this.getResources().getStringArray(R.array.Gampaha);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(adapter1);
            //text.setText(options[position]+" , "+options1[position]);
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Gampaha1, android.R.layout.simple_spinner_item);
            options2 = ManualActivity.this.getResources().getStringArray(R.array.Gampaha1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(options[position].equals("Hambantota")){
            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                    R.array.Hambanthota, android.R.layout.simple_spinner_item);
            options1 = ManualActivity.this.getResources().getStringArray(R.array.Hambanthota);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(adapter1);
            //text.setText(options[position]+" , "+options1[position]);
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Hambanthota1, android.R.layout.simple_spinner_item);
            options2 = ManualActivity.this.getResources().getStringArray(R.array.Hambanthota1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(options[position].equals("Jaffna")){
            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                    R.array.Jaffna, android.R.layout.simple_spinner_item);
            options1 = ManualActivity.this.getResources().getStringArray(R.array.Jaffna);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(adapter1);
            //text.setText(options[position]+" , "+options1[position]);
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Jaffna1, android.R.layout.simple_spinner_item);
            options2 = ManualActivity.this.getResources().getStringArray(R.array.Jaffna1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(options[position].equals("Kalutara")){
            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                    R.array.Kaluthara, android.R.layout.simple_spinner_item);
            options1 = ManualActivity.this.getResources().getStringArray(R.array.Kaluthara);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(adapter1);
            //text.setText(options[position]+" , "+options1[position]);
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Kaluthara1, android.R.layout.simple_spinner_item);
            options2 =ManualActivity.this.getResources().getStringArray(R.array.Kaluthara1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(options[position].equals("Kandy")){
            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                    R.array.Kandy, android.R.layout.simple_spinner_item);
            options1 = ManualActivity.this.getResources().getStringArray(R.array.Kandy);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(adapter1);
            //text.setText(options[position]+" , "+options1[position]);
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Kandy1, android.R.layout.simple_spinner_item);
            options2 = ManualActivity.this.getResources().getStringArray(R.array.Kandy1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(options[position].equals("Kegalle")){
            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                    R.array.Kegalle, android.R.layout.simple_spinner_item);
            options1 = ManualActivity.this.getResources().getStringArray(R.array.Kegalle);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(adapter1);
            //text.setText(options[position]+" , "+options1[position]);
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Kegalle1, android.R.layout.simple_spinner_item);
            options2 = ManualActivity.this.getResources().getStringArray(R.array.Kegalle1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(options[position].equals("Kilinochchi")){
            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                    R.array.Kilinochchi, android.R.layout.simple_spinner_item);
            options1 = ManualActivity.this.getResources().getStringArray(R.array.Kilinochchi);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(adapter1);
            //text.setText(options[position]+" , "+options1[position]);
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Kilinochchi1, android.R.layout.simple_spinner_item);
            options2 = ManualActivity.this.getResources().getStringArray(R.array.Kilinochchi1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(options[position].equals("Kurunegala")){
            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                    R.array.Kurunegala, android.R.layout.simple_spinner_item);
            options1 = ManualActivity.this.getResources().getStringArray(R.array.Kurunegala);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(adapter1);
            //text.setText(options[position]+" , "+options1[position]);
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Kurunegala1, android.R.layout.simple_spinner_item);
            options2 = ManualActivity.this.getResources().getStringArray(R.array.Kurunegala1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(options[position].equals("Mannar")){
            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                    R.array.Mannar, android.R.layout.simple_spinner_item);
            options1 = ManualActivity.this.getResources().getStringArray(R.array.Mannar);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(adapter1);
            //text.setText(options[position]+" , "+options1[position]);
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Mannar1, android.R.layout.simple_spinner_item);
            options2 = ManualActivity.this.getResources().getStringArray(R.array.Mannar1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(options[position].equals("Matara")){
            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                    R.array.Matara, android.R.layout.simple_spinner_item);
            options1 = ManualActivity.this.getResources().getStringArray(R.array.Matara);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(adapter1);
            //text.setText(options[position]+" , "+options1[position]);
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Matara1, android.R.layout.simple_spinner_item);
            options2 = ManualActivity.this.getResources().getStringArray(R.array.Matara1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(options[position].equals("Monaragala")){
            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                    R.array.Monaragala, android.R.layout.simple_spinner_item);
            options1 = ManualActivity.this.getResources().getStringArray(R.array.Monaragala);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(adapter1);
            //text.setText(options[position]+" , "+options1[position]);
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Monaragala1, android.R.layout.simple_spinner_item);
            options2 = ManualActivity.this.getResources().getStringArray(R.array.Monaragala1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(options[position].equals("Mullaitivu")){
            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                    R.array.Mullative, android.R.layout.simple_spinner_item);
            options1 = ManualActivity.this.getResources().getStringArray(R.array.Mullative);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(adapter1);
            //text.setText(options[position]+" , "+options1[position]);
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Mullative1, android.R.layout.simple_spinner_item);
            options2 = ManualActivity.this.getResources().getStringArray(R.array.Mullative1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(options[position].equals("Nuwara_Eliya")){
            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                    R.array.Nuwara_Eliya, android.R.layout.simple_spinner_item);
            options1 = ManualActivity.this.getResources().getStringArray(R.array.Nuwara_Eliya);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(adapter1);
            //text.setText(options[position]+" , "+options1[position]);
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Nuwara_Eliya1, android.R.layout.simple_spinner_item);
            options2 = ManualActivity.this.getResources().getStringArray(R.array.Nuwara_Eliya1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(options[position].equals("Polonnaruwa")){
            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                    R.array.Polonnaruwa, android.R.layout.simple_spinner_item);
            options1 = ManualActivity.this.getResources().getStringArray(R.array.Polonnaruwa);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(adapter1);
            //text.setText(options[position]+" , "+options1[position]);
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Polonnaruwa1, android.R.layout.simple_spinner_item);
            options2 = ManualActivity.this.getResources().getStringArray(R.array.Polonnaruwa1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(options[position].equals("Puttalam")){
            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                    R.array.Puttalam, android.R.layout.simple_spinner_item);
            options1 = ManualActivity.this.getResources().getStringArray(R.array.Puttalam);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(adapter1);
            //text.setText(options[position]+" , "+options1[position]);
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Puttalam1, android.R.layout.simple_spinner_item);
            options2 = ManualActivity.this.getResources().getStringArray(R.array.Puttalam1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(options[position].equals("Ratnapura")){
            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                    R.array.Ratnapura, android.R.layout.simple_spinner_item);
            options1 = ManualActivity.this.getResources().getStringArray(R.array.Ratnapura);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(adapter1);
            //text.setText(options[position]+" , "+options1[position]);
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Ratnapura1, android.R.layout.simple_spinner_item);
            options2 = ManualActivity.this.getResources().getStringArray(R.array.Ratnapura1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(options[position].equals("Trincomalee")){
            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                    R.array.Trincomalee, android.R.layout.simple_spinner_item);
            options1 = ManualActivity.this.getResources().getStringArray(R.array.Trincomalee);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(adapter1);
            //text.setText(options[position]+" , "+options1[position]);
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Trincomalee1, android.R.layout.simple_spinner_item);
            options2 = ManualActivity.this.getResources().getStringArray(R.array.Trincomalee1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(options[position].equals("Vavuniya")){
            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                    R.array.Vavuniya, android.R.layout.simple_spinner_item);
            options1 = ManualActivity.this.getResources().getStringArray(R.array.Vavuniya);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(adapter1);
            //text.setText(options[position]+" , "+options1[position]);
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Vavuniya1, android.R.layout.simple_spinner_item);
            options2 = ManualActivity.this.getResources().getStringArray(R.array.Vavuniya1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
