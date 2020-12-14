package org.yosa.AlienWeb.network;

import com.google.gson.Gson;
import org.yosa.AlienWeb.AlienWebApplication;
import org.yosa.AlienWeb.domain.Chain;
import org.yosa.AlienWeb.domain.Transaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private int port;
    private static Gson gson = new Gson();

    public Server(){

    }

    public Server(int port){
        this.port = port;
    }

    @Override
    public void run() {
        runthread();
    }

    public void runthread() {
//        URL whatismyip = new URL("http://checkip.amazonaws.com");
//        BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
//        String ip = in.readLine();
        try {
            serverSocket = new ServerSocket(port);
            clientSocket = serverSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            Chain newchain = gson.fromJson(in.readLine(), Chain.class);
            if (newchain.isValid() && (long) newchain.getChain().size() > (long) AlienWebApplication.alienCash.getChain().size()) {
                List<Transaction> newTransactions = new ArrayList<Transaction>();
                newTransactions.addAll(newchain.getPendingTransactions());
                newTransactions.addAll(AlienWebApplication.alienCash.getPendingTransactions());
                newchain.setPendingTransactions(newTransactions);
                AlienWebApplication.alienCash = newchain;
                AlienWebApplication.saveBlockchain();
            }
            out.println(gson.toJson(AlienWebApplication.alienCash));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
