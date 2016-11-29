package com.kelimeezberimde;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import co.dift.ui.SwipeToAction;

public class FavoriWords extends AppCompatActivity implements OnInitListener {
    RecyclerView recyclerView;
    WordAdapter adapter;
    SwipeToAction swipeToAction;
    List<Words> wordsList=new ArrayList<>();
    DataBaseHelper dbHelper=new DataBaseHelper(this);
    private TextToSpeech repeatTTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favori_words);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        repeatTTS = new TextToSpeech(this, this);
        try {
            dbHelper.createDataBase();
            sorgu();

        } catch (Exception ex) {
            Log.w("hata", "Veritabanı oluşturulamadı ve kopyalanamadı!");
        }


        adapter = new WordAdapter(this.wordsList);
        recyclerView.setAdapter(adapter);
        swipeToAction = new SwipeToAction(recyclerView, new SwipeToAction.SwipeListener<Words>() {

            @Override
            public boolean swipeLeft(Words itemData) {
                displaySnackbar("\"" + itemData.getWord() + "\"" + "  Favorilere Eklendi", null, null);
//                SQLiteDatabase db=dbHelper.getWritableDatabase();
//                db.execSQL("update Words set word='absandoon' where _id=1");
//                db.close();
                return true;
            }

            @Override
            public boolean swipeRight(final Words itemData) {
                final int pos = removeWord(itemData);
                displaySnackbar("\"" + itemData.getWord() + "\"" + " Ezberleniyor...", "Geri", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addWord(pos, itemData);
                    }
                });
                return true;
            }

            @Override
            public void onClick(Words itemData) {
                displaySnackbar("\"" + itemData.getWord() + "\"" + "  Tıklandı", null, null);
                repeatTTS.speak(itemData.getWord().toString(),
                        TextToSpeech.QUEUE_FLUSH, null);
            }

            //region Uzun Basılırsa
            @Override
            public void onLongClick(Words itemData) {
                displaySnackbar(itemData.getWord() + " Uzun basıldı", null, null);

            }
            //endregion
        });
    }
    private int removeWord(Words words) {
        int pos = wordsList.indexOf(words);
        wordsList.remove(words);
        adapter.notifyItemRemoved(pos);
        return pos;
    }

    private void addWord(int pos, Words words) {
        wordsList.add(pos, words);
        adapter.notifyItemInserted(pos);
    }

    private void displaySnackbar(String text, String actionName, View.OnClickListener action) {
        Snackbar snack = Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_LONG)
                .setAction(actionName, action);

        View v = snack.getView();
        v.setBackgroundColor(getResources().getColor(R.color.wallet_hint_foreground_holo_dark));
        ((TextView) v.findViewById(android.support.design.R.id.snackbar_text)).setTextColor(Color.WHITE);
        ((TextView) v.findViewById(android.support.design.R.id.snackbar_action)).setTextColor(Color.BLACK);

        snack.show();
    }
    Cursor okunan=null;

    private void sorgu(){

        SQLiteDatabase db=dbHelper.getReadableDatabase();

        try {

//            okunan=db.query("Words",new String[] {"isFav"},"isFav=0",null,null,null,null);
            okunan= dbHelper.getReadableDatabase().
                    rawQuery("select * from Words where isFav=?", new String[]{"1"});
            while (okunan.moveToNext()){
                String word=okunan.getString(okunan.getColumnIndex("word"));
                String speak=okunan.getString(okunan.getColumnIndex("speak"));
                String mean=okunan.getString(okunan.getColumnIndex("mean"));
                this.wordsList.add(new Words(word,mean,mean));
            }
        }catch (SQLException e){
            Toast.makeText(getApplicationContext(),"hata",Toast.LENGTH_LONG);
        }

    }
    public class setDefault extends AsyncTask<String,Integer,String> {


        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }

    @Override
    public void onInit(int status) {

    }

}
