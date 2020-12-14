package com.example.td1_asl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        // Configuration le temp 24h pour la fonctionalle TimePicker
        TimePicker simpleTimePicker = findViewById(R.id.simpleTimePicker); // initiate a time picker
        simpleTimePicker.setIs24HourView(true); // set 24 hours mode for the time picker

        // Des buttons permettent d'applier des fonctionalités
        final Button sendMessage = findViewById(R.id.buttonDisplayCourses);
        final Button quitter = findViewById(R.id.exit);
        final Button newActivity = findViewById(R.id.faireLesCourses);
        final Button preference = findViewById(R.id.preference);


        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Log.d("Preference", prefs.getString("login", ""));
        TextView editText = findViewById(R.id.editTextLogin);
        editText.setText(prefs.getString("login", ""));


        /** Exercice 7: Afficher le texte saisi dans le champ
         * La button est à gauche de l'interface
         * @Button sendMessage
         */

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = findViewById(R.id.displayTextCourses);
                TextView editText = findViewById(R.id.editTextLogin);
                TimePicker time = findViewById(R.id.simpleTimePicker);
                tv.setText(editText.getText() + " doit faire les course à " + time.getHour() + ":" + time.getMinute());
            }
        });

        /** Exercice 8: Quitter l'appicaltion
         * Le button est à bas de l'interface
         * @Button quitter
         */
        quitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        /** Exercice 9: Créer une second activité ListeActivity
         * Quand on click le button "FAIRE LES COURSES", il affiche une nouvelle activité
         * @Button newActivity
         */
        newActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editText = findViewById(R.id.editTextLogin);
                String text = editText.getText().toString();
                Intent myIntent = new Intent(MainActivity.this, ListeActivity.class);
                myIntent.putExtra("testname", text);
                startActivity(myIntent);

            }
        });

        /** Exercice 15, 16, 17: persisantance des données
         * Le button qui permet d'accéder à la liste des courses, sauvegardez le login dans les préférences partagées.
         * @Button preference
         */
        preference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent pref = new Intent(MainActivity.this, PreferenceActitivy.class);
                TextView editText = findViewById(R.id.editTextLogin);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("login", editText.getEditableText().toString());
                editText.setText(prefs.getString("login", ""));
                editor.commit();
                Toast.makeText(getApplicationContext(), "Login" , Toast.LENGTH_SHORT).show();
                startActivity(pref);

            }
        });
    }


}
