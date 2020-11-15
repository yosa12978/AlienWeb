package org.yosa.AlienWeb;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.yosa.AlienWeb.domain.Block;
import org.yosa.AlienWeb.domain.Chain;
import org.yosa.AlienWeb.domain.Transaction;
import org.yosa.AlienWeb.services.HashService;
import org.yosa.AlienWeb.services.LogService;

public class AlienWebApplication {
    private static LogService logger = new LogService();
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) {
        String wallet1 = "15e2b0d3c33891ebb0f1ef609ec419420c20e320ce94c65fbc8c3312448eb225";
        String wallet2 = "8a9bcf1e51e812d0af8465a8dbcc9f741064bf0af3b3d08e6b0246437c19f7fb";
        Chain alienCash = new Chain();
        alienCash.createTransaction(new Transaction(wallet1, wallet2, 1));
        alienCash.createTransaction(new Transaction(wallet2, wallet1, 1));

        alienCash.mineBlock(wallet1);

        logger.log(gson.toJson(alienCash.getChain()));
        logger.log(gson.toJson(alienCash.getBalance(wallet1)));
        logger.log(gson.toJson(alienCash.isValid()));

//        logger.log(HashService.getHash("123456789"));
//        logger.log(HashService.getHash("987654321"));

    }
}
