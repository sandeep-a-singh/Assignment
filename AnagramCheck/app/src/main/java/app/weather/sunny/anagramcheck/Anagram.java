package app.weather.sunny.anagramcheck;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Anagram extends AppCompatActivity {
    EditText phrase1;
    EditText phrase2;
    Button check_phrase;
    TextView show_result;
Animation animate_slide;
    RelativeLayout content_holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anagram);
        initialize();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        animate_slide= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide);
        content_holder.startAnimation(animate_slide);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phrase1.setText("");
                phrase2.setText("");
            }
        });

        check_phrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phraseWord1 = null;
                String phraseWord2 = null;
                try {
                    phraseWord1 = phrase1.getText().toString();
                    phraseWord2 = phrase2.getText().toString();
                    ;
                } catch (NullPointerException e) {
                    //EditText is Empty
                }
                if (checkString(phraseWord1, phraseWord2)) {

                    if (isAnagram(phraseWord1, phraseWord2)) {
                        //Anagram
                        show_result.setText("Its an Anagram");
                        show_result.setVisibility(View.VISIBLE);

                    } else {
                        show_result.setText("Not an Anagram");
                        show_result.setVisibility(View.VISIBLE);


                        //Not an Anagram
                    }

                } else {
                    //Some Error Occured
                }


            }
        });

    }

    public void initialize() {
        content_holder= (RelativeLayout) findViewById(R.id.main_content);
        phrase1 = (EditText) findViewById(R.id.editText);
        phrase2 = (EditText) findViewById(R.id.editText2);
        check_phrase = (Button) findViewById(R.id.check);
        show_result = (TextView) findViewById(R.id.result);
    }

    private boolean checkString(String word1, String word2) {
        boolean value = true;
        if (word1.isEmpty() || word2.isEmpty()) {
            value = false;
            //Some EditTest is Empty
        } else if (word1 == "" || word2 == "") {
            value = false;
            //Some EditTest is Empty
        }

        return value;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_anagram, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    public static boolean isAnagram(String first, String second) {
    String positive = first.toLowerCase().trim();
    String negative = second.toLowerCase().trim();

    if (positive.length() != negative.length()) {
        return false;
    }

    int[] counts = new int[26];

    int diff = 0;

    for (int i = 0; i < positive.length(); i++) {
        int pos = (int) positive.charAt(i) - 97; // convert the char into an array index
        if (counts[pos] >= 0) { // the other string doesn't have this
            diff++; // an increase in differences
        } else { // it does have it
            diff--; // a decrease in differences
        }
        counts[pos]++; // track it

        int neg = (int) negative.charAt(i) - 97;
        if (counts[neg] <= 0) { // the other string doesn't have this
            diff++; // an increase in differences
        } else { // it does have it
            diff--; // a decrease in differences
        }
        counts[neg]--; // track it
    }

    return diff == 0;



}
}