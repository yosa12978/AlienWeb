package org.yosa.AlienWeb.services;

public class DrawService {
    private static LogService logger = new LogService();

    public void drawLogo(){
        logger.log("  /$$$$$$  /$$ /$$                     /$$      /$$           /$$      \n" +
                " /$$__  $$| $$|__/                    | $$  /$ | $$          | $$      \n" +
                "| $$  \\ $$| $$ /$$  /$$$$$$  /$$$$$$$ | $$ /$$$| $$  /$$$$$$ | $$$$$$$ \n" +
                "| $$$$$$$$| $$| $$ /$$__  $$| $$__  $$| $$/$$ $$ $$ /$$__  $$| $$__  $$\n" +
                "| $$__  $$| $$| $$| $$$$$$$$| $$  \\ $$| $$$$_  $$$$| $$$$$$$$| $$  \\ $$\n" +
                "| $$  | $$| $$| $$| $$_____/| $$  | $$| $$$/ \\  $$$| $$_____/| $$  | $$\n" +
                "| $$  | $$| $$| $$|  $$$$$$$| $$  | $$| $$/   \\  $$|  $$$$$$$| $$$$$$$/\n" +
                "|__/  |__/|__/|__/ \\_______/|__/  |__/|__/     \\__/ \\_______/|_______/ \n" +
                "                                                                           \n" +
                "\n");
    }

    public void drawMenu(){
        logger.log("------------\n" +
                "||| MENU |||\n" +
                "------------\n" +
                "| 1. Get Blockchain\n" +
                "| 2. Create Transaction\n" +
                "| 3. Connect To Server\n" +
                "| 4. Mine Block\n" +
                "| 5. Show Nodes\n" +
                "| 6. Save And Exit\n"+ "------------ ");
    }
}
