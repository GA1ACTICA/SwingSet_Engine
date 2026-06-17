package gameEngine.interfaces;

/**
 * A simple notifier for importing and exporting to and from JSON files.
 */
public interface JsonNotifier {

    /**
     * Invoked when exporting to a JSON file was successfully executed.
     * 
     * @param path The path that the JSON was exported to
     */
    public void successfulExportNotification(String path);

    /**
     * Invoked when importing from a JSON file was successfully executed.
     * 
     * @param path The path that the JSON was imported from
     */
    public void successfulImportNotification(String path);
}
