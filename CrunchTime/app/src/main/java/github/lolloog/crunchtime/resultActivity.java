package github.lolloog.crunchtime;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class resultActivity extends AppCompatActivity {
    private Button returnButton;
    private double[] multiplayFacter = {3.5, 2, 2.25, .25, .25, .1, 1, .12, .20, .12, .13, .15};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        returnButton = (Button) findViewById(R.id.rtbutton);
        TextView resultText = (TextView)findViewById(R.id.resulttext);

        returnButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                returnMain();
            }
        });

        Resources res = getResources();
        Bundle bundle = getIntent().getExtras();
        int reps = bundle.getInt("reps");
        int itemIndex = bundle.getInt("itemIndex");
        int enteredCalories = bundle.getInt("calories");
        int weight = bundle.getInt("weight");

        String[] origString = res.getStringArray(R.array.workOutType);
        String[] repTypes = res.getStringArray(R.array.repType);
        String[] dispString = new String[origString.length];;

        double calorieSpend = 0;

        if (itemIndex == -1){
            System.out.println("entered the set calories");
            resultText.setText("Based on  " + enteredCalories + "  Calories. It corresponeded to:");

            calorieSpend = enteredCalories;

        } else{
            calorieSpend = reps*weight/(multiplayFacter[itemIndex]*150);

            resultText.setText("You burned  " + String.format("%.1f", calorieSpend) + "  Calories. Which is equal to:");
        }

        for(int i=0;i<origString.length;i++) {
            String eachAmount = String.format("%.1f", calorieSpend*multiplayFacter[i]);
            dispString[i] = origString[i] + " - " + eachAmount + " " + repTypes[i];
        }

        // me adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.fragment_item, dispString);
        ListView list = (ListView) findViewById(R.id.resultList);
        list.setAdapter(adapter);
    }

    private void returnMain() {
        Intent intent = new Intent(resultActivity.this, starterActivity.class);
        startActivity(intent);
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
