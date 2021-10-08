package seedu.duke.profile.exceptions;

/**
 * Exception that is thrown when the input human characteristics are invalid
 */
public class InvalidCharacteristicException extends Exception {
    private static final String ERROR_MESSAGE = " cannot be of a negative value :(" + System.lineSeparator()
            + "Try a positive value instead!";

    public InvalidCharacteristicException(String characteristic) {
        super(characteristic + ERROR_MESSAGE);
    }
}
