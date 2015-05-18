package us.genetzky.alphaappdev.alphadroid;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by Nathansen on 5/17/2015.
 */
public class BTCommunication {
    BluetoothSocket mBluetoothSocket;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothDevice mServerDevice;
    final String serviceName = "us.genetzky.alphaappdev.alphadroid";
    final UUID serviceUUID = UUID.nameUUIDFromBytes("20cb4de0-fd16-11e4-b939-0800200c9a66".getBytes());
    ServerAcceptThread acceptThread;
    ClientConnectThread connectThread;
    Handler mHandler;
    void startConnection(Handler handler){
        mHandler = handler;
        acceptThread = new ServerAcceptThread();
        acceptThread.start();
    }
    void startConnection(BluetoothDevice bluetoothDevice,Handler handler){
        mHandler = handler;
        mServerDevice = bluetoothDevice;
        connectThread = new ClientConnectThread(bluetoothDevice);
    }
    void manageConnectedSocket(BluetoothSocket socket){
        //Socket is ready for communication
        mBluetoothSocket = socket;
    }
    void obtainMessage(String data){
        mHandler.obtainMessage(BT.BTHandler.DATA_RECEIVED,data);
    }
    private class ConnectionThread {
        final InputStream iStream;
        final OutputStream oStream;
        final BluetoothSocket mmSocket;
        ConnectionThread(BluetoothSocket bluetoothSocket){
            mmSocket = bluetoothSocket;
            InputStream tmpIn =null;//Consider BufferedInputStream
            OutputStream tmpOut=null;
            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = mmSocket.getInputStream();
                tmpOut = mmSocket.getOutputStream();
            } catch (IOException e) { }

            iStream = tmpIn;
            oStream = tmpOut;
        }
        public void run() {
            byte[] buffer = new byte[1024];  // 4 byte(1 int) buffer store for the stream
            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = iStream.read(buffer);
                    // Send the obtained bytes to the UI activity
                    obtainMessage(String.valueOf(bytes));
//                    mHandler.obtainMessage(BT.BTHandler.MESSAGE_READ, bytes, -1, buffer)
//                            .sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }

        /* Call this from the main activity to send data to the remote device */
        public void write(byte[] bytes) {
            try {
                oStream.write(bytes);
            } catch (IOException e) { }
        }

        /* Call this from the main activity to shutdown the connection */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }
    class BTHandler extends android.os.Handler{

    }
    private class ClientConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ClientConnectThread(BluetoothDevice device) {
            // Use a temporary object that is later assigned to mmSocket,
            // because mmSocket is final
            BluetoothSocket tmp = null;
            mmDevice = device;

            // Get a BluetoothSocket to connect with the given BluetoothDevice
            try {
                // MY_UUID is the app's UUID string, also used by the server code
                tmp = device.createRfcommSocketToServiceRecord(serviceUUID);
            } catch (IOException e) { }
            mmSocket = tmp;
        }

        public void run() {
            // Cancel discovery because it will slow down the connection
            mBluetoothAdapter.cancelDiscovery();

            try {
                // Connect the device through the socket. This will block
                // until it succeeds or throws an exception
                mmSocket.connect();
            } catch (IOException connectException) {
                // Unable to connect; close the socket and get out
                try {
                    mmSocket.close();
                } catch (IOException closeException) { }
                return;
            }

            // Do work to manage the connection (in a separate thread)
            manageConnectedSocket(mmSocket);
        }

        /** Will cancel an in-progress connection, and close the socket */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }
    private class ServerAcceptThread extends Thread{
        private final BluetoothServerSocket mmServerSocket;
        public ServerAcceptThread() {
            // Use a temporary object that is later assigned to mmServerSocket,
            // because mmServerSocket is final
            BluetoothServerSocket tmp = null;
            try {
                // MY_UUID is the app's UUID string, also used by the client code
                tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(serviceName,serviceUUID);
            } catch (IOException e) { }
            mmServerSocket = tmp;
        }
        public void run() {
            BluetoothSocket socket = null;
            // Keep listening until exception occurs or a socket is returned
            while (true) {
                try {
                    socket = mmServerSocket.accept();
                } catch (IOException e) {
                    break;
                }
                // If a connection was accepted
                if (socket != null) {
                    // Do work to manage the connection (in a separate thread)
                    manageConnectedSocket(socket);
                    try {
                        mmServerSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
        /** Will cancel the listening socket, and cause the thread to finish */
        public void cancel() {
            try {
                mmServerSocket.close();
            } catch (IOException e) { }
        }
    }
}
