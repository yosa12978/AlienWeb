package org.yosa.AlienWeb.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Client {

    public static List<Node> nodes = new ArrayList<Node>();

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) {
        try{
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            boolean isNodeExist = false;
            for(Node i : nodes){
                if(i.getAddress().equals(ip) && i.getPort() == port)
                    isNodeExist = true;
            }
            if(!isNodeExist)
                nodes.add(new Node(ip, port));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public String sendMessage(String msg) {
        out.println(msg);
        String resp = null;
        try{
            resp = in.readLine();
        }catch (IOException e){
            e.printStackTrace();
        }
        return resp;
    }

    public void broadcast(String data) {
        for (Node i : nodes) {
            startConnection(i.getAddress(), i.getPort());
            sendMessage(data);
            stopConnection();
        }
    }

    public void stopConnection() {
        try{
            in.close();
            out.close();
            clientSocket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
