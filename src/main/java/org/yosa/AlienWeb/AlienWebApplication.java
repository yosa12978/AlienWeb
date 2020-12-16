package org.yosa.AlienWeb;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.yosa.AlienWeb.domain.Block;
import org.yosa.AlienWeb.domain.Chain;
import org.yosa.AlienWeb.domain.Transaction;
import org.yosa.AlienWeb.network.Client;
import org.yosa.AlienWeb.network.Node;
import org.yosa.AlienWeb.network.Server;
import org.yosa.AlienWeb.network.Wallet;
import org.yosa.AlienWeb.services.DrawService;
import org.yosa.AlienWeb.services.HashService;
import org.yosa.AlienWeb.services.LogService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;
import java.util.UUID;

public class AlienWebApplication {
    private static LogService logger = new LogService();
    private static DrawService drawService = new DrawService();
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static  Chain alienCash = new Chain();
    private static Client client = new Client();

    public static void loadBlockchain() throws Exception{
        try {
            File chainFile = new File("blockchain.json");
            Scanner fileReader = new Scanner(chainFile);
            String jsonStruct = "";
            while (fileReader.hasNextLine()) {
                String data = fileReader.nextLine();
                jsonStruct+=data;
            }
            alienCash = gson.fromJson(jsonStruct, Chain.class);
            fileReader.close();
        } catch (FileNotFoundException e) {
            File newBlockchain = new File("blockchain.json");
            if (newBlockchain.createNewFile()) {
                FileWriter myWriter = new FileWriter(newBlockchain.getAbsoluteFile());
                myWriter.write(new Gson().toJson(alienCash));
                myWriter.close();
            }
        }
    }

    public static void saveBlockchain() throws Exception{
        try {
            File blockchain = new File("blockchain.json");
            FileWriter myWriter = new FileWriter(blockchain.getAbsoluteFile());
            myWriter.write(new Gson().toJson(alienCash));
            myWriter.close();
        } catch (FileNotFoundException e) {
            File newBlockchain = new File("blockchain.json");
            if (newBlockchain.createNewFile()) {
                FileWriter myWriter = new FileWriter(newBlockchain.getAbsoluteFile());
                myWriter.write(new Gson().toJson(alienCash));
                myWriter.close();
            }
        }
    }

    public static void main(String[] args) throws Exception {

        int userPort = 3639;
        Wallet user = null;
        loadBlockchain();
        try {
            File walletFile = new File("wallet.json");
            Scanner fileReader = new Scanner(walletFile);
            String jsonStruct = "";
            while (fileReader.hasNextLine()) {
                String data = fileReader.nextLine();
                jsonStruct+=data;
            }
            user = gson.fromJson(jsonStruct, Wallet.class);
            fileReader.close();
        } catch (FileNotFoundException e) {
            File newWallet = new File("wallet.json");
            if (newWallet.createNewFile()) {
                FileWriter myWriter = new FileWriter(newWallet.getAbsoluteFile());
                Wallet newUser = new Wallet(HashService.getHash(UUID.randomUUID().toString()), HashService.getHash(UUID.randomUUID().toString()));
                myWriter.write(new Gson().toJson(newUser));
                myWriter.close();
                user = newUser;
            }
        }

        Server serverThread = new Server(userPort);
        serverThread.start();

        while(true){
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

            Scanner scan = new Scanner(System.in);
            drawService.drawLogo();
            logger.log("Your address: " + user.getPublicKey());
            logger.log("Balance: "+alienCash.getBalance(user.getPublicKey()) + " ACH");

            drawService.drawMenu();
            int option = Integer.parseInt(logger.read(scan,"Option: "));

            if(option == 1){
                logger.log(gson.toJson(alienCash));
            }else if (option == 2){
                if(alienCash.getBalance(user.getPublicKey()) <= 0)
                    continue;
                int amount = Integer.parseInt(logger.read(scan, "Enter Amount: "));
                String receiverAddress = logger.read(scan, "Receiver Address: ");
                alienCash.createTransaction(new Transaction(user.getPublicKey(), receiverAddress, amount));
                client.broadcast(gson.toJson(alienCash));
            }else if (option == 3){
                String ip = logger.read(scan, "IP Address: ");
                int port = Integer.parseInt(logger.read(scan, "Port: "));
                client.startConnection(ip, port);
                logger.log("Connection established!");
            }else if (option == 4){
                alienCash.mineBlock(user.getPublicKey());
                client.broadcast(gson.toJson(alienCash));
            }else if(option == 5) {
                try{
                    for(int i = 0; i <= Client.nodes.size(); i++)
                    logger.log("----------" +
                            "Node" + i +
                            "Address: " + Client.nodes.get(i) +
                            "Port: " + Client.nodes.get(i) +
                            "----------");
                }catch(Exception e){
                    logger.log("No nodes connected yet.");
                }
            }else if (option == 6){
                saveBlockchain();
                System.exit(0);
            }

            logger.read(scan, "Press ENTER to continue...");
        }
    }
}
