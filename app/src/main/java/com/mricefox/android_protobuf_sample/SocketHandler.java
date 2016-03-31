package com.mricefox.android_protobuf_sample;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Author:zengzifeng email:zeng163mail@163.com
 * Description:
 * Date:2016/3/31
 */
public class SocketHandler {
    static Executor communicateExector;


    public static void send(byte[] source) {
        if (communicateExector == null) {
            communicateExector = Executors.newCachedThreadPool();
        }
        communicateExector.execute(new Worker(source));
    }

    private static class Worker implements Runnable {
        private final byte[] source;

        Worker(byte[] source) {
            this.source = source;
        }

        @Override
        public void run() {
            Log.d("SocketHandler", "send bytes len:" + source.length);
            try {
                SocketAddress socketAddress = new InetSocketAddress("10.111.2.61", 8081);
                Socket socket = new Socket();
                socket.connect(socketAddress);

                OutputStream os = socket.getOutputStream();
                os.write(source);
                os.close();
                socket.close();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
