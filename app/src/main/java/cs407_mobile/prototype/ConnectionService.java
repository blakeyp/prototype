package cs407_mobile.prototype;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import static cs407_mobile.prototype.MainActivity.DEBUG_TAG;

public class ConnectionService {

    MakeConnection connection;
    private PrintWriter printwriter;

    public ControllerActivity controller;

    protected void connectToIP(String ipAddr, ControllerActivity ctrl) {
        controller = ctrl;
        connection = new MakeConnection(ipAddr);
        connection.execute();
    }

    protected void sendMessage(String message) {
        SendMessage newMessage = new SendMessage(message);
        newMessage.execute();
    }

    protected void closeConnection() {
        Log.d(DEBUG_TAG, "Closing stuff");
        printwriter.close();
        try {
            connection.client.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(DEBUG_TAG, "Closed stuff");
    }

    private class MakeConnection extends AsyncTask<Void, Void, Void> {

        private String ipAddr;
        private Socket client;

        public MakeConnection(String ipAddr) {
            this.ipAddr = ipAddr;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                client = new Socket(ipAddr, 9000);   // connect to the server
                Log.d(DEBUG_TAG, "Connection made");
                printwriter = new PrintWriter(client.getOutputStream(), true);
                printwriter.println("connected");   // write message to output stream with EOL char
                printwriter.flush();
            } catch (IOException e) {
                Log.d(DEBUG_TAG, "Connection refused!!!");
                // reject and return to start screen
                //controller.finish();
                //Toast.makeText(getApplicationContext(), "You did not enter a valid IP address!", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            Log.d(DEBUG_TAG, "Finished connecting");
            controller.setConnectStatus("Connected to: " + ipAddr);
            controller.buttonJump.setClickable(true);
            controller.buttonJump.setAlpha(1);
            controller.buttonDisconnect.setClickable(true);
            controller.buttonDisconnect.setAlpha(1);
        }

    }

    private class SendMessage extends AsyncTask<Void, Void, Void> {

        private String message;

        public SendMessage(String message) {
            this.message = message;
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.d(DEBUG_TAG, "Sending message");
            printwriter.println(message);
            printwriter.flush();
            Log.d(DEBUG_TAG, "Sent message");
            return null;
        }

    }
}
