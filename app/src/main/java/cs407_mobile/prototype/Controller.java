package cs407_mobile.prototype;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static cs407_mobile.prototype.MainActivity.DEBUG_TAG;

public class Controller extends AppCompatActivity {

    private ConnectionService connectionService;
    //private Button buttonJump;
    //private Button buttonDisconnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);

        Intent intent = getIntent();
        String ipAddr = intent.getStringExtra("ip");   // get input IP address

        connectionService = new ConnectionService();
        connectionService.connectToIP(ipAddr);

        TextView ipPlaceholder = (TextView) findViewById(R.id.ipAddress);
        ipPlaceholder.setText("Connected to: " + ipAddr);   // fill IP address placeholder with connected IP address

        Button buttonJump = (Button) findViewById(R.id.buttonJump);   // reference to the disconnect button

        // button press event listener
        buttonJump.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Log.d(DEBUG_TAG, "Pressed Jump button");
                connectionService.sendMessage("jump");
            }
        });

        Button buttonDisconnect = (Button) findViewById(R.id.buttonDisconnect);   // reference to the disconnect button

        buttonDisconnect.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Log.d("DEBUG", "Pressed Disconnect button");
                connectionService.closeConnection();
            }
        });

    }

    @Override
    protected void onDestroy() {
        connectionService.closeConnection();
        super.onDestroy();
    }

}
