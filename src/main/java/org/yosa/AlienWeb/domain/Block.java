package org.yosa.AlienWeb.domain;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.yosa.AlienWeb.services.HashService;
import org.yosa.AlienWeb.services.LogService;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Block {
    private String blockId;
    private String hash;
    private String previousHash;
    private List<Transaction> transactions;
    private long nonce;
    private long timestamp;

    public Block(String previousHash, List<Transaction> transactions, long timestamp) {
        this.blockId = UUID.randomUUID().toString();
        this.previousHash = previousHash;
        this.transactions = transactions;
        this.nonce = 0;
        this.timestamp = timestamp;
        this.hash = computeHash();
    }

    public String computeHash(){
        String hashReadyString = blockId +"-"+ previousHash +"-"+ new Gson().toJson(transactions, new TypeToken<List<Transaction>>(){}.getType()) +"-"+ nonce + "-"+ timestamp;
        return HashService.getHash(hashReadyString);
    }

    public String mineBlock(int prefix) {
        String prefixString = new String(new char[prefix]).replace('\0', '0');
        while (!hash.substring(0, prefix).equals(prefixString)) {
            nonce++;
            hash = computeHash();
        }
        return hash;
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public long getNonce() {
        return nonce;
    }

    public void setNonce(long nonce) {
        this.nonce = nonce;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
