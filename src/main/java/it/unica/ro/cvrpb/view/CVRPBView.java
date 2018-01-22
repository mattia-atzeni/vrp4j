package it.unica.ro.cvrpb.view;

import java.io.IOException;

public abstract class CVRPBView implements View {

    public void printLogo() {
        String logo =
                "  ______     ______  ____  ____    ____        _                \n" +
                " / ___\\ \\   / /  _ \\|  _ \\| __ )  / ___|  ___ | |_   _____ _ __ \n" +
                "| |    \\ \\ / /| |_) | |_) |  _ \\  \\___ \\ / _ \\| \\ \\ / / _ \\ '__|\n" +
                "| |___  \\ V / |  _ <|  __/| |_) |  ___) | (_) | |\\ V /  __/ |   \n" +
                " \\____|  \\_/  |_| \\_\\_|   |____/  |____/ \\___/|_| \\_/ \\___|_| \n" +
                "\n" +
                "Solving Vehicle Routing Problems with Backhauls in Java";
        System.out.println(logo);
    }

    public void clearScreen() {
        try {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (IOException | InterruptedException e) {
            // cannot clear the screen
        }
    }

}
