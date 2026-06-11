/**
 * Project: SwingSet_Engine
 *
 * Author: Galactica
 *
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 *
 * Copyright © 2026 Galactica
 */

package utils;

public class ErrorManagement extends Utils {

    /**
     * @param exception
     * @param message
     * 
     */
    public static void throwError(Exception exception, String message) {
        System.err.println(
                '\n' + Utils.RED + message + ": " + Utils.YELLOW + exception.getMessage()
                        + Utils.RESET + '\n');

        exception.printStackTrace();
        System.exit(1);
    }

    /**
     * @param exception
     * @param message
     * 
     */
    public static void reportError(Exception exception, String message) {
        System.err.println(
                '\n' + Utils.RED + message + ": " + Utils.YELLOW + exception.getMessage()
                        + Utils.RESET + '\n');

        exception.printStackTrace();
    }

}
