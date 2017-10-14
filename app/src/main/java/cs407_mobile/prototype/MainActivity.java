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
    private EditText textField;
    private Button button;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {   // on opening the app
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textField = (EditText) findViewById(R.id.editText1); // reference to the text field
        button = (Button) findViewById(R.id.button1); // reference to the send button

        // start a new thread to make a connection to the server socket
        MakeConnection makeConnection = new MakeConnection();
        makeConnection.execute();

        // button press event listener
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
            Log.d("DEBUG", "Pressed button");
            message = textField.getText().toString();   // get the text message on the text field
            textField.setText("");   // reset the text field to blank
            SendMessage sendMessageTask = new SendMessage();
            sendMessageTask.execute();
            }
        });
    }

    @Override
    protected void onDestroy() {   // on closing the app
        super.onDestroy();
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

        @Override
        protected Void doInBackground(Void... params) {
            try {
                client = new Socket("172.31.223.242", 80);   // connect to the server
                printwriter = new PrintWriter(client.getOutputStream(), true);
                printwriter.println("connected");   // write message to output stream with EOL char
                printwriter.flush();
                Log.d("DEBUG", "Connection made");
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