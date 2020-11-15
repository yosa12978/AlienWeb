package org.yosa.AlienWeb.domain;

import com.google.gson.Gson;
import org.yosa.AlienWeb.services.LogService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Chain {
    private List<Block> chain;
    private List<Transaction> pendingTransactions;

    public Chain() {
        initializeChain();
    }

    public void initializeChain(){
        chain = new ArrayList<Block>();
        pendingTransactions = new ArrayList<Transaction>();
        chain.add(new Block(null, new ArrayList<Transaction>(), new Date().getTime()));
    }

    public List<Block> getChain() {
        return chain;
    }

    public List<Transaction> getPendingTransactions() {
        return pendingTransactions;
    }

    public Block getLatest(){
        return chain.get(chain.size() - 1);
    }

    public void addBlock(Block block){
        block.setPreviousHash(getLatest().getHash());
        chain.add(block);
    }

    public boolean isValid(){
        for(int i = 1; i < chain.size(); i++){
            Block current = chain.get(i);
            Block previous = chain.get(i-1);
            if(!current.getHash().equals(current.computeHash()))
                return false;
            if(!current.getPreviousHash().equals(previous.computeHash()))
                return false;
        }
        return true;
    }

    public double getBalance(String address){
        double balance = 0;
        for(Block block : chain)
            for(Transaction transaction : block.getTransactions()){
                String sender = transaction.sender;
                String receiver = transaction.receiver;
                if(address.equals(sender))
                    balance-=transaction.amount;
                if(address.equals(receiver))
                    balance+=transaction.amount;
            }
        return balance;
    }

    public void createTransaction(Transaction transaction){
        pendingTransactions.add(transaction);
    }

    public void mineBlock(String minerAddress){
        double leftLimit = 1D;
        double rightLimit = 3D;
        double generatedReward = leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
        Transaction rewardTransaction = new Transaction("null", minerAddress, generatedReward);
        createTransaction(rewardTransaction);
        Block finalBlock = new Block(getLatest().getHash(), pendingTransactions, new Date().getTime());
        finalBlock.mineBlock(4);
        addBlock(finalBlock);
        pendingTransactions = new ArrayList<Transaction>();
    }
}
