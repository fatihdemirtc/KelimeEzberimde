package com.kelimeezberimde;

import android.app.Activity;
import android.content.ContentValues;
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

import java.util.ArrayList;
import java.util.List;

import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.widget.TextView;
import android.widget.Toast;

import co.dift.ui.SwipeToAction;


public class CompletedWords extends Activity implements OnInitListener {
    RecyclerView recyclerView;
    WordAdapter adapter;
    SwipeToAction swipeToAction;
    List<Words> wordsList = new ArrayList<>();
    DataBaseHelper dbHelper = new DataBaseHelper(this);
    private TextToSpeech repeatTTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_words);

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
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("isFav", "1");
                db.update("Words", values, "word = ?",
                        new String[]{itemData.getWord()});
                db.close();
                return true;
            }

            //region Sağdan Çekince Kaldırılacak
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
            //endregion

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

    Cursor okunan = null;

    private void sorgu() {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            //okunan = db.query("Words", new String[]{"isFav"}, "isFav=0", null, null, null, null);
            okunan = dbHelper.getReadableDatabase().
                    rawQuery("select w.word,w.speak,w.mean from Words w INNER JOIN Group_Items g ON w.group_item=g._id where g.count=?", new String[]{"5"});
            while (okunan.moveToNext()) {
                String word = okunan.getString(okunan.getColumnIndex("word"));
                String speak = okunan.getString(okunan.getColumnIndex("speak"));
                String mean = okunan.getString(okunan.getColumnIndex("mean"));
                this.wordsList.add(new Words(word, mean, mean));
            }
        } catch (SQLException e) {
            Toast.makeText(getApplicationContext(), "hata", Toast.LENGTH_LONG);
        }
    }

    //region Async
    public class setDefault extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }


    public void onInit(int status) {

    }
    //endregion


}



