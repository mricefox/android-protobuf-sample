package com.mricefox.server;


import com.mricefox.proto.AddressBookProtos;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8081);
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
                System.out.println("receivedBytes len:" + receivedBytes.length);
                System.out.println("receivedStr:" + receivedStr);

                AddressBookProtos.AddressBook addressBook = AddressBookProtos.AddressBook.parseFrom(receivedBytes);

                display(addressBook);

                baos.close();
                is.close();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void display(AddressBookProtos.AddressBook addressBook) {
        List<AddressBookProtos.Person> personList = addressBook.getPersonList();

        for (AddressBookProtos.Person p : personList) {
            System.out.printf("name:%1s id:%2d email:%3s \n", p.getName(), p.getId(), p.getEmail());
            for (AddressBookProtos.Person.PhoneNumber n : p.getPhoneList()) {
                System.out.printf("type:%1s number:%2s \n", n.getType().name(), n.getNumber());
            }
        }
    }
}
