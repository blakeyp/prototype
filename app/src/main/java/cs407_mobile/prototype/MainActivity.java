package cs407_mobile.prototype;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static String DEBUG_TAG = "MYDEBUG";

    private EditText ipField;
    private Button buttonConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {   // on opening the app
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ipField = (EditText) findViewById(R.id.editText);   // reference to the IP address text field
        buttonConnect = (Button) findViewById(R.id.buttonConnect);   // reference to the connect button

        // on clicking connect button
        buttonConnect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(DEBUG_TAG, "Pressed Connect button");
                String ipAddr = ipField.getText().toString();
                if (!ipAddr.matches("^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$"))
                    Toast.makeText(getApplicationContext(), "You did not enter a valid IP address!", Toast.LENGTH_LONG).show();
                else {
                    Log.d(DEBUG_TAG, "IP address: " + ipAddr);
                    openController(ipAddr);
                }
            }
        });

    }

    protected void openController(String address) {
        Log.d(DEBUG_TAG, "Starting Controller Activity");
        Intent intent = new Intent(this, ControllerActivity.class);
        intent.putExtra("ip",  address);
        startActivity(intent);
    }

}