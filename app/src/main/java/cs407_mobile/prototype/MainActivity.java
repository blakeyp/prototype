package cs407_mobile.prototype;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

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
                Log.d("DEBUG", "Pressed Connect button");
                Log.d("DEBUG", "IP address: " + ipField.getText().toString());
                openController(ipField.getText().toString());
            }
        });

    }

    protected void openController(String address) {
        Log.d("DEBUG", "Starting Controller Activity");
        Intent intent = new Intent(this, Controller.class);
        intent.putExtra("ip",  address);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {   // on closing the app
        super.onDestroy();
    }

}