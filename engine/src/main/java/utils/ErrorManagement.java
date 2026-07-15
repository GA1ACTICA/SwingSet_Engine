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

/**
 * Utility methods for exception operations.
 */
public class ErrorManagement extends Utils {
    private ErrorManagement() {
    }

    /**
     * Throws an exception in a clear to understand manner.
     * <p>
     * Throws an exception with highlighting and a extra message on what went wrong
     * and exits the program with the code {@code 1}.
     * 
     * @param exception exception to be thrown
     * 
     * @param message   extra message for clarification
     * 
     */
    public static void throwError(Exception exception, String message) {
        throwError(exception, message, 1);
    }

    /**
     * Throws an exception in a clear to understand manner.
     * <p>
     * Throws an exception with highlighting and a extra message on what went wrong
     * and exits the program.
     * 
     * @param exception exception to be thrown
     * 
     * @param message   extra message for clarification
     * 
     * @param exitCode  the exit code the program exits with
     */
    private static void throwError(Exception exception, String message, int exitCode) {
        System.err.println(
                '\n' + Utils.ConsoleRED + message + ": " + Utils.ConsoleYELLOW + exception.getMessage()
                        + Utils.ConsoleRESET + '\n');

        exception.printStackTrace();
        System.exit(exitCode);
    }

    /**
     * Reports an error in a clear to understand manner.
     * <p>
     * Reports an exception with highlighting and a extra message on what went
     * wrong.
     * 
     * @param exception exception to be reported
     * 
     * @param message   extra message for clarification
     */
    public static void reportError(Exception exception, String message) {
        System.err.println(
                '\n' + Utils.ConsoleRED + message + ": " + Utils.ConsoleYELLOW + exception.getMessage()
                        + Utils.ConsoleRESET + '\n');

        exception.printStackTrace();
    }

}
