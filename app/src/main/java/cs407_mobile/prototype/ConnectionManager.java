package cs407_mobile.prototype;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by u1421499 on 17/10/17.
 */
class ConnectionManager {

    MakeConnection connection;
    private PrintWriter printwriter;

    protected void connectToIP(String ipAddress) {
        connection = new MakeConnection(ipAddress);
        connection.execute();
    }

    protected void sendMessage(String message) {
        SendMessage newMessage = new SendMessage(message);
        newMessage.execute();
    }

    protected void closeConnection() {
        Log.d("DEBUG", "Closing stuff");
        printwriter.close();   // doesn't seem to work ?? just blocks - maybe already closed on server?
        try {
            connection.client.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("DEBUG", "Closed stuff");
    }

    private class MakeConnection extends AsyncTask<Void, Void, Void> {

        private String ipAddress;
        private Socket client;

        public MakeConnection(String ipAddress) {
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

        private String message;

        public SendMessage(String message) {
            this.message = message;
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.d("DEBUG", "Sending message");
            printwriter.println("Jump");
            printwriter.flush();
            Log.d("DEBUG", "Sent message");
            return null;
        }

    }
}
