package com.example.tiktok.uberclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Comparer extends AppCompatActivity {
    private Button mCalc;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparer);



        mCalc = (Button)findViewById(R.id.Send);
        mCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText distancein = (EditText) findViewById(R.id.Distancein);
                TextView delgrores=(TextView) findViewById(R.id.delgrores);
                TextView gojekres=(TextView) findViewById(R.id.gojekres);
                TextView grabres=(TextView) findViewById(R.id.grabres);

                Double intdistance=Double.parseDouble(distancein.getText().toString());
                float distance=intdistance.floatValue();


                float delgro_fare_amount = delgro_func(distance);
                float gojek_fare_amount = gojek_func(distance);
                float grab_fare_amount = grab_func(distance);

                delgrores.setText("   S$ " +delgro_fare_amount);
                gojekres.setText("   S$ " +gojek_fare_amount);
                grabres.setText("   S$ " +grab_fare_amount);

            }
        });

    }
    public float gojek_func(float x){
        float price = 0;
        if (x >= 4.72){
            price = (float) ((0.65*x)+2.7);
        } else if (x < 4.72 & x > 0) {
            price = 6;
        }
        return price;
    }

    float grab_func(float y){
        float price = 0;
        if (y >= 4.72){
            price = (float) ((0.50*y)+2.5);
        } else if (y < 4.72 & y > 0) {
            price = 6;
        }
        return price;
    }

    public float delgro_func(float dist){ //distance in km
        float price = 0;
        if (dist <= 1){
            price = (float) 4.10;
        } else if (dist > 1 & dist <= 10){
            price = (float) (4.10 + ((dist-10)/0.45 * 0.24));
        } else if (dist > 10){
            price = (float) (4.10 + (10/0.45)*0.24 + ((dist-11)/0.45*0.24));
        }
        return price;

}
}