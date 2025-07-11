import gui.InventoryFrame;

public class Main {
    public static void main(String[] args) {
        // Start the Swing GUI safely on the Event Dispatch Thread (EDT)
        javax.swing.SwingUtilities.invokeLater(() -> new InventoryFrame());
    }
}
