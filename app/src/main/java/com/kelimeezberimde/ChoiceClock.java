package com.kelimeezberimde;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChoiceClock extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_clock);

        Button btnclc1,btnclc2,btnclc3,btnclc4,btnok;
        btnclc1=(Button) findViewById(R.id.btnclc1);
        btnclc2=(Button) findViewById(R.id.btnclc2);
        btnclc3=(Button) findViewById(R.id.btnclc3);
        btnclc4=(Button) findViewById(R.id.btnclc4);
        btnok=(Button) findViewById(R.id.btnok);

//        btnfav.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                Intent intentfav;
//                intentfav = new Intent(view.getContext(), FavoriWords.class);
//                startActivity(intentfav);
//            }
//        });
//        btncontinuing.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                Intent intentcontinuing;
//                intentcontinuing = new Intent(view.getContext(), ContinuingWords.class);
//                startActivity(intentcontinuing);
//            }
//        });
//        btncompleted.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                Intent intentcompleted;
//                intentcompleted = new Intent(view.getContext(), CompletedWords.class);
//                startActivity(intentcompleted);
//            }
//        });
//        btntestyourself.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                Intent intenttest;
//                intenttest = new Intent(view.getContext(), TestYourself.class);
//                startActivity(intenttest);
//            }
//        });
    }
}
