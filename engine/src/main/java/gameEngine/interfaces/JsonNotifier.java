package gameEngine.interfaces;

import utils.jsonUtils.JsonBacked;

/**
 * A simple notifier for importing and exporting to and from JSON files.
 */
public interface JsonNotifier {

    /**
     * Invoked when exporting to a JSON file was successfully executed.
     * <p>
     * <b>Note:</b> This method is only supposed to be invoked by
     * {@link JsonBacked}.
     * 
     * @param path the path that the JSON was exported to
     */
    public void successfulExportNotification(String path);

    /**
     * Invoked when importing from a JSON file was successfully executed.
     * <p>
     * <b>Note:</b> This method is only supposed to be invoked by
     * {@link JsonBacked}.
     * 
     * @param path the path that the JSON was imported from
     */
    public void successfulImportNotification(String path);
}
