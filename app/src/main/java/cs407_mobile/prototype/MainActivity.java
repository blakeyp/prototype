package cs407_mobile.prototype;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Socket client;
    private PrintWriter printwriter;
    private EditText ipField;
    private Button button;
    private Button buttonConnect;
    private Button buttonDisconnect;
    private String message = "jump";

    @Override
    protected void onCreate(Bundle savedInstanceState) {   // on opening the app
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ipField = (EditText) findViewById(R.id.editText); // reference to the text field
        button = (Button) findViewById(R.id.button1); // reference to the send button
        buttonConnect = (Button) findViewById(R.id.buttonConnect); // reference to the connect button
        buttonDisconnect = (Button) findViewById(R.id.buttonDisconnect); // reference to the connect button

        // button press event listener
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
            Log.d("DEBUG", "Pressed Jump button");
            //message = textField.getText().toString();   // get the text message on the text field
            //textField.setText("");   // reset the text field to blank
            SendMessage sendMessageTask = new SendMessage();
            sendMessageTask.execute();
            }
        });

        // button press event listener
        buttonConnect.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Log.d("DEBUG", "Pressed Connect Button");
                Log.d("DEBUG", ipField.getText().toString());
                //message = textField.getText().toString();   // get the text message on the text field
                //textField.setText("");   // reset the text field to blank
                // start a new thread to make a connection to the server socket
                MakeConnection makeConnection = new MakeConnection(ipField.getText().toString());
                makeConnection.execute();
            }
        });

        // button press event listener
        buttonDisconnect.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Log.d("DEBUG", "Pressed Disconnect Button");
                closeConnection();
            }
        });
    }

    @Override
    protected void onDestroy() {   // on closing the app
        super.onDestroy();
        closeConnection();
    }

    protected void closeConnection() {
        Log.d("DEBUG", "Closing stuff");
        printwriter.close();   // doesn't seem to work ?? just blocks - maybe already closed on server?
        try {
            client.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("DEBUG", "Closed stuff");
    }

    private class MakeConnection extends AsyncTask<Void, Void, Void> {

        private String ipAddress;

        public MakeConnection (String ipAddress) {
            this.ipAddress = ipAddress;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                client = new Socket(ipAddress, 9000);   // connect to the server
                Log.d("DEBUG", "Connection made");
                printwriter = new PrintWriter(client.getOutputStream(), true);
                printwriter.println("connected");   // write message to output stream with EOL char
                printwriter.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    private class SendMessage extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Log.d("DEBUG", "Sending message");
            printwriter.println(message);
            printwriter.flush();
            Log.d("DEBUG", "Sent message");
            return null;
        }

    }

}