package cs407_mobile.prototype;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Controller extends AppCompatActivity {

    private Button buttonDisconnect;
    private Button buttonJump;
    private ConnectionService connectionService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);

        Intent intent = getIntent();
        String address = intent.getStringExtra("ip");

        connectionService = new ConnectionService();
        connectionService.connectToIP(address);

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("Connected to: " + address);



        buttonDisconnect = (Button) findViewById(R.id.buttonDisconnect); // reference to the disconnect button

        // button press event listener
        buttonDisconnect.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Log.d("DEBUG", "Pressed Disconnect Button");
            }
        });

        buttonJump = (Button) findViewById(R.id.button1); // reference to the disconnect button

        // button press event listener
        buttonJump.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Log.d("DEBUG", "Pressed Jump Button");
                connectionService.sendMessage("jump");
            }
        });


    }

    @Override
    protected void onDestroy() {
        connectionService.closeConnection();
        super.onDestroy();
    }
}
