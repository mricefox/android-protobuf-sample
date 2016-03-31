package com.mricefox.server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

/**
 * Author:zengzifeng email:zeng163mail@163.com
 * Description:
 * Date:2016/3/30
 */
public class Client {
    public static void main(String[] args) {
        try {
            SocketAddress socketAddress = new InetSocketAddress(InetAddress.getLocalHost(), 8088);
            Socket socket = new Socket();
            socket.connect(socketAddress);

            OutputStream os = socket.getOutputStream();
            os.write(new String("aaa").getBytes());
            os.close();
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
