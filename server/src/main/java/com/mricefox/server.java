package com.mricefox;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8088);
            while (true) {
                System.out.println("Server listening...");

                Socket socket = serverSocket.accept();

                System.out.println("accept...");

                InputStream is = socket.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buf = new byte[512];
                int offset = -1;
                while ((offset = is.read(buf)) != -1) {
                    baos.write(buf, 0, offset);
                }

//                baos.close();
//                is.close();
//                socket.close();

                byte[] receivedBytes = baos.toByteArray();

                String receivedStr = new String(receivedBytes);
                System.out.println("receivedBytes:" + receivedBytes.length);
                System.out.println("receivedStr:" + receivedStr);

                baos.close();
                is.close();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
