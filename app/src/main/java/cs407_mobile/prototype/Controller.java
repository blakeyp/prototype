package cs407_mobile.prototype;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Controller extends AppCompatActivity {

    private Button buttonDisconnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);

        buttonDisconnect = (Button) findViewById(R.id.buttonDisconnect); // reference to the disconnect button

        // button press event listener
        buttonDisconnect.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Log.d("DEBUG", "Pressed Disconnect Button");
            }
        });
    }




}
