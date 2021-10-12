package seedu.duke.commands;

import seedu.duke.profile.exceptions.InvalidCharacteristicException;
import seedu.duke.ui.Ui;

/**
 * Represents the command that when executed, changes the value of name, height and weight in the Profile.
 */
public class ProfileCreateCommand extends Command {
    public static final String COMMAND_WORD = "profile";
    public static int COMMAND_EXPECTED_NUM_DELIMITERS = 3;
    public static final String MESSAGE_COMMAND_FORMAT = Ui.QUOTATION + COMMAND_WORD
            + " " + COMMAND_PREFIX_NAME + COMMAND_PREFIX_DELIMITER + "X "
            + COMMAND_PREFIX_HEIGHT + COMMAND_PREFIX_DELIMITER + "Y "
            + COMMAND_PREFIX_WEIGHT + COMMAND_PREFIX_DELIMITER + "Z"
            + Ui.QUOTATION + " where X is your name, Y is your height in CM and Z is your weight in KG.";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid format! "
            + "Trying to create your profile? Use this format:"
            + Ui.INDENTED_LS + MESSAGE_COMMAND_FORMAT;
    public static final String MESSAGE_SUCCESS = "Hello %1$s! Your profile has been created!"
            + Ui.INDENTED_LS + "Your height is %2$scm."
            + Ui.INDENTED_LS + "Your weight is %3$skg.";

    private final String name;
    private final double weight;
    private final double height;


    public ProfileCreateCommand(String name, double height, double weight) {
        assert name != null : "parser should have ensured name is not null";
        this.name = name;
        this.height = height;
        this.weight = weight;
    }

    @Override
    public CommandResult execute() {
        try {
            super.profile.setProfile(this.name, this.height, this.weight);
            return new CommandResult(String.format(MESSAGE_SUCCESS, this.name, this.height, this.weight));
        } catch (InvalidCharacteristicException e) {
            return new CommandResult(e.getMessage());
        }
    }
}