import de.neeroxz.db.H2InitDatabase;
import de.neeroxz.ui.AppPanel;

/**
 * Class: App
 *
 * @author NeeroxZ
 * @date 19.10.2024
 */
public class App {
    public static void main(String[] args) {
        System.out.println("test");
        H2InitDatabase db = new H2InitDatabase();
        db.initDatabase();
        new AppPanel().showPanel();
    }
}
