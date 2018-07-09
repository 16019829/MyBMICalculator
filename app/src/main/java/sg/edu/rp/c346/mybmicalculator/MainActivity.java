package sg.edu.rp.c346.mybmicalculator;

        import android.content.SharedPreferences;
        import android.preference.PreferenceManager;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

     EditText weight;
     EditText height;
     Button calc;
     Button reset;
     TextView date;
     TextView BMI;
     TextView result;

    Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
     String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
            (now.get(Calendar.MONTH)+1) + "/" +
            now.get(Calendar.YEAR) + " " +
            now.get(Calendar.HOUR_OF_DAY) + ":" +
            now.get(Calendar.MINUTE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weight = findViewById(R.id.editTextWeight);
        height = findViewById(R.id.editTextHeight);
        calc = findViewById(R.id.buttonCalculate);
        reset = findViewById(R.id.buttonReset);
        date = findViewById(R.id.textViewLastDate);
        BMI = findViewById(R.id.textViewLastBMI);
        result = findViewById(R.id.textViewResult);

        calc.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                Float bmi = Float.parseFloat(weight.getText().toString()) / (Float.parseFloat(height.getText().toString()) * Float.parseFloat(height.getText().toString()));
                date.setText("Last Calculated Date: " + datetime);
                BMI.setText("Last Calculated BMI: " + Float.toString(bmi));
                 height.setHint(R.string.height);
                 weight.setHint(R.string.weight);

                if(bmi < 18.5){
                    result.setText("You are underweight!");
                }
                else if (bmi < 24.9){
                    result.setText("Your BMI is normal.");
                }
                else if (bmi < 29.9){
                    result.setText("You are overweight!");
                }
                else if (bmi >= 30){
                    result.setText("You are obese!");
                }
            }
         });
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        reset.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                height.setText("");
                weight.setText("");
                height.setHint(R.string.height);
                weight.setHint(R.string.weight);
                BMI.setText("Last Calculated BMI: ");
                date.setText("Last Calculated Date: ");
                result.setText("");
                SharedPreferences.Editor prefEdit = prefs.edit();
                prefEdit.clear().commit();
            }
         });
    }
    @Override
     protected void onPause() {
        super.onPause();
        if (!height.getText().toString().isEmpty() && !weight.getText().toString().isEmpty()){
            String Date = date.getText().toString();
            String bmi = BMI.getText().toString();
            String Result = result.getText().toString();
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor prefEdit = prefs.edit();
            prefEdit.putString("date",Date);
            prefEdit.putString("result",Result);
            prefEdit.putString("bmi",bmi);
            prefEdit.commit();
        }
    }
    @Override
     protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String Date = prefs.getString("date","Last calculated date: ");
        String bmi = prefs.getString("bmi","Last calculated BMI: 0.0");
        String Result = prefs.getString("result","");
        date.setText(Date);
        result.setText(Result);
        BMI.setText(bmi);
    }
}
