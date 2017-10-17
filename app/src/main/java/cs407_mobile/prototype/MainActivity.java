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
    private Button button;
    private Button buttonConnect;
    public ConnectionManager connectionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {   // on opening the app
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectionManager = new ConnectionManager();

        ipField = (EditText) findViewById(R.id.editText); // reference to the text field
        //button = (Button) findViewById(R.id.button1); // reference to the send button
        buttonConnect = (Button) findViewById(R.id.buttonConnect); // reference to the connect button

//        // button press event listener
//        button.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//            Log.d("DEBUG", "Pressed Jump button");
//            //message = textField.getText().toString();   // get the text message on the text field
//            //textField.setText("");   // reset the text field to blank
//            connectionManager.sendMessage("jump");
//            }
//        });

        // button press event listener
        buttonConnect.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Log.d("DEBUG", "Pressed Connect Button");
                Log.d("DEBUG", ipField.getText().toString());
                //openController();
                //message = textField.getText().toString();   // get the text message on the text field
                //textField.setText("");   // reset the text field to blank
                // start a new thread to make a connection to the server socket
                connectionManager.connectToIP(ipField.getText().toString());
            }
        });

    }

    protected void openController() {
        Log.d("DEBUG", "Starting Controller Activity");
        Intent intent = new Intent(this, Controller.class);
        intent.putExtra();
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {   // on closing the app
        super.onDestroy();
        connectionManager.closeConnection();
    }

}