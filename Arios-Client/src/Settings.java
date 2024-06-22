import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Settings {

    private static final String SETTINGS_PATH = System.getProperty("user.home") + File.separator + ".Arios530" + File.separator + "settings.conf";
    private static final Properties properties = new Properties();

    public static boolean resizableSD = false;
    public static boolean resizableSDLogoutHD = false;

    public static void createSettingsFile() {
        File file = new File(SETTINGS_PATH);
        if (!file.exists()) {
            try {
                System.out.println("Settings file does not exist, creating settings file at " + SETTINGS_PATH + "...");
                file.getParentFile().mkdirs();
                file.createNewFile();
                saveSettings();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void loadSettings() {
        System.out.println("Loading settings file " + SETTINGS_PATH + "...");
        try (FileInputStream inputStream = new FileInputStream(SETTINGS_PATH)) {
            properties.load(inputStream);
            resizableSD = Boolean.parseBoolean(properties.getProperty("resizableSD", "false"));
            resizableSDLogoutHD = Boolean.parseBoolean(properties.getProperty("resizableSDLogoutHD", "false"));
            System.out.println("Loaded " + properties.size() + " settings from " + SETTINGS_PATH + "..." );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveSettings() {
        System.out.println("Saving settings to file " + SETTINGS_PATH + "...");
        try (FileOutputStream outputStream = new FileOutputStream(SETTINGS_PATH)) {
            properties.setProperty("resizableSD", String.valueOf(resizableSD));
            properties.setProperty("resizableSDLogoutHD", String.valueOf(resizableSDLogoutHD));
            properties.store(outputStream, null);
            System.out.println("Saved " + properties.size() + " settings to file " + SETTINGS_PATH + "...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void initialize() {
        createSettingsFile();
        loadSettings();
    }
}