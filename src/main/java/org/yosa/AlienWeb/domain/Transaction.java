package org.yosa.AlienWeb.domain;

import java.util.UUID;

public class Transaction {
    public String transactionId;
    public String sender;
    public String receiver;
    public double amount;

    public Transaction(String sender, String receiver, double amount) {
        this.transactionId = UUID.randomUUID().toString();
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "\nTransaction " + transactionId +
                "\nSender: " + sender +
                "\nReceiver: " + receiver +
                "\nAmount: " + amount;
    }
}
