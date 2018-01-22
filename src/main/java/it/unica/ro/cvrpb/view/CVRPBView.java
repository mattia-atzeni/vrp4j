package it.unica.ro.cvrpb.view;

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

}
