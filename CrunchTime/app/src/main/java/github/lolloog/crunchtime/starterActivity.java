package github.lolloog.crunchtime;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class starterActivity extends AppCompatActivity {
    private EditText repsText;
    private EditText calorieText;
    private EditText weightText;
    private Button goButton;
    private int itemSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);

        itemSelected = -1;

        // Get a handle to all user interface elements
        repsText = (EditText) findViewById(R.id.reps_field);
        calorieText = (EditText) findViewById(R.id.placedCalorlie);
        weightText = (EditText) findViewById(R.id.editTextWeight);

        goButton = (Button) findViewById(R.id.go_button);
        // repType = (Switch) findViewById(R.id.switch1);

        // ClickListener for go Button
        goButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                openBrowser();
            }
        });
        // urlText Listener
        repsText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    openBrowser();
                    return true;
                }
                return false;
            }
        });

        // preparing list data
        prepareListData();

        // setup the list clicks
        getListClicks();

    }

    private void prepareListData() {
        Resources res = getResources();
        // me adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.fragment_item, res.getStringArray(R.array.workOutType));

        //configure the view
        ListView list = (ListView) findViewById(R.id.simpleList);
        list.setAdapter(adapter);
    }

    private void getListClicks() {
        ListView list = (ListView) findViewById(R.id.simpleList);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                TextView eView = (TextView) viewClicked;

                itemSelected = position;

                Toast.makeText(starterActivity.this, eView.getText().toString() + " aka item " + position + " selected", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void openBrowser() {
        try {
            int calorieEntered = Integer.parseInt(calorieText.getText().toString());
            int weight = Integer.parseInt(weightText.getText().toString());

            System.out.println("Itindex " + itemSelected);

            Intent intent = new Intent(starterActivity.this, resultActivity.class);
            intent.putExtra("reps", 0);
            intent.putExtra("itemIndex", itemSelected);
            intent.putExtra("calories", calorieEntered);
            intent.putExtra("weight", weight);
            startActivity(intent);
        } catch(NumberFormatException nfe) {
            System.out.println("Doing reps instead");

            try {
                int reps = Integer.parseInt(repsText.getText().toString());
                int weight = Integer.parseInt(weightText.getText().toString());

                if (itemSelected == -1){
                    Toast.makeText(starterActivity.this, "Please select the matching workout type", Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(starterActivity.this, resultActivity.class);
                    intent.putExtra("reps", reps);
                    intent.putExtra("itemIndex", itemSelected);
                    intent.putExtra("calories", 0);
                    intent.putExtra("weight", weight);
                    startActivity(intent);
                }


            } catch (NumberFormatException nfe2) {
                Toast.makeText(starterActivity.this, "Please enter something", Toast.LENGTH_LONG).show();
                System.out.println("Could not parse " + nfe);
            }

        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }

    }
}