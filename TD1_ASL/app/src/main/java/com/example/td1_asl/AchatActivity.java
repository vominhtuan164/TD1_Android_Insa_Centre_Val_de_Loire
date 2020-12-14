package com.example.td1_asl;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class AchatActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.achat);
        TextView achat = (TextView) findViewById(R.id.achat);
        achat.setText(getIntent().getStringExtra("achat_name"));
    }

    /**
     * Insérer la nouvelle demande dans le database
     *
     * @param view
     */
    public void onClickButtonOui(View view) {
        DatabaseHandler db = new DatabaseHandler(this);

        // Inserting Contacts
        Log.d("Insert: ", "Inserting ..");
        db.addAchat(new Achat(getIntent().getStringExtra("name"), getIntent().getStringExtra("achat_name")));
        // Reading all achats
        List<Achat> contacts = db.getAllAchats();
        for (Achat cn : contacts) {
            String log = "Id: " + cn.getID() + " ,Name : " + cn.getName() + " , Item: " +
                    cn.getItem();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }

    }

    /**
     *
     * @param view
     */

    public void onClickButtonNon(View view) {

    }
}
