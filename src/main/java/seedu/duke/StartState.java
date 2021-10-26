package seedu.duke;

import seedu.duke.data.profile.Profile;
import seedu.duke.data.profile.attributes.ActivityFactor;
import seedu.duke.data.profile.attributes.Age;
import seedu.duke.data.profile.attributes.CalorieGoal;
import seedu.duke.data.profile.attributes.Gender;
import seedu.duke.data.profile.attributes.Height;
import seedu.duke.data.profile.attributes.Name;
import seedu.duke.data.profile.attributes.Weight;
import seedu.duke.logic.parser.exceptions.ParamMissingException;
import seedu.duke.storage.StorageManager;
import seedu.duke.storage.exceptions.UnableToWriteFileException;
import seedu.duke.ui.Ui;

public class StartState {

    private Profile profile;
    private StorageManager storageManager;
    private Ui ui;

    public StartState(Profile profile, StorageManager storageManager, Ui ui) {
        this.profile = profile;
        this.storageManager = storageManager;
        this.ui = ui;
    }

    public static final String MESSAGE_INPUT_IS_SUCCESSFUL = "Input %s is successful.";
    public static final String MESSAGE_INVALID_POSITIVE_INT_INPUT = "Invalid input, "
            + "please input a valid positive whole number";
    public static final String MESSAGE_INVALID_POSITIVE_DOUBLE_INPUT = "Invalid input,"
            + " please input a valid positive number";
    public static final String MESSAGE_EMPTY_INPUT = "Input cannot be empty";
    public static final String MESSAGE_INTRO_CALORIE_GOAL = "Please input your net calorie goal.";
    public static final String MESSAGE_INTRO_AGE = "Please input your age.";
    public static final String MESSAGE_INTRO_GENDER = "Please input your gender below. M for male, F for female.";
    public static final String MESSAGE_INTRO_WEIGHT = "Please input your weight(in kg) below.";
    public static final String MESSAGE_INTRO_NAME = "What's your name?";
    public static final String MESSAGE_INTRO_HEIGHT = "Please input your height(in cm).";
    public static final String MESSAGE_CREATE_PROFILE_SUCCESSFUL = "Profile created successfully";
    public static final String MESSAGE_INTRO_ACTIVITY_FACTOR = "Please input your activity factor based on "
            + "the value shown on the left.\n"
            + "Below are the activity factor values and the corresponding description you can consider:\n"
            + "1 -> Sedentary - Little or no exercise\n"
            + "2 -> Lightly Active - Light exercise or sports, around 1-3 days a week\n"
            + "3 -> Moderately Active - Regular exercise or sports, around 3-5 days a week\n"
            + "4 -> Very Active - Frequent exercise or sports, around 6-7 days a week\n"
            + "5 -> If you are extra active - Sports or exercising is your passion and a physical jobscope.";

    /**
     * Check whether user's profile is complete.
     * If profile is complete, the program will exit this method.
     * If the profile is partially complete, it will assist user in completing the profile.
     * If all parameters of profile is incorrect or a new user, user is required to complete
     * all the particulars before saving their profile data.
     */
    public void checkAndCreateProfile() {
        if (profile.checkProfileComplete()) {
            return;
        }
        if (profile.checkProfilePresent()) {
            assert !profile.checkProfileComplete() : "profile is incomplete";
            repairProfile();
        } else {
            createNewProfile();
        }
        ui.formatMessageFramedWithDivider(MESSAGE_CREATE_PROFILE_SUCCESSFUL,
                ui.MESSAGE_DIRECT_HELP);
    }

    /**
     * Assists user in fixing remaining profile particulars.
     * The profile changes will be saved on every update.
     */
    private void repairProfile() {
        while (!profile.checkProfileComplete()) {
            try {
                if (!profile.getProfileName().isValid()) {
                    createNewProfileName(profile); // if user just enter and exit, it will cause his name to be null
                } else if (!profile.getProfileHeight().isValid()) {
                    createNewProfileHeight(profile);
                } else if (!profile.getProfileWeight().isValid()) {
                    createNewProfileWeight(profile);
                } else if (!profile.getProfileGender().isValid()) {
                    createNewProfileGender(profile);
                } else if (!profile.getProfileAge().isValid()) {
                    createNewProfileAge(profile);

                } else if (!profile.getProfileActivityFactor().isValid()) {
                    createNewProfileActivityFactor(profile);
                } else if (profile.getProfileCalorieGoal().isValid()) {
                    createNewProfileCalorieGoal(profile);
                }
                storageManager.saveProfile(this.profile);
            } catch (ParamMissingException e) {
                System.out.println(e.getMessage());
            } catch (UnableToWriteFileException e) {
                ui.formatMessageFramedWithDivider(e.getMessage());
            }
        }
    }

    /**
     * Creates a new profile instance for new user.
     * Profile will be lost if user exits the program without setting up the profile.
     * Upon completing profile, the profile instance in Main will be replaced and stored in storage.
     */
    private void createNewProfile() {
        // Let this method return profile
        Profile newProfile = new Profile();
        while (!newProfile.checkProfileComplete()) {
            try {
                createNewProfileName(newProfile);
                createNewProfileHeight(newProfile);
                createNewProfileWeight(newProfile);
                createNewProfileGender(newProfile);
                createNewProfileAge(newProfile);
                createNewProfileCalorieGoal(newProfile);
                createNewProfileActivityFactor(newProfile);
            } catch (ParamMissingException e) {
                System.out.println(e.getMessage());
            }
        }
        this.profile = newProfile;
        try {
            storageManager.saveProfile(this.profile);
        } catch (UnableToWriteFileException e) {
            ui.formatMessageFramedWithDivider(e.getMessage());
        }
    }

    /**
     * Creates a valid profile activity factor for the profile instance.
     *
     * @param newProfile instance of a profile class.
     * @throws ParamMissingException if user input a string of 0 characters.
     */
    private void createNewProfileActivityFactor(Profile newProfile) throws ParamMissingException {
        while (!newProfile.getProfileActivityFactor().isValid()) {
            try {
                ui.formatMessageFramedWithDivider(MESSAGE_INTRO_ACTIVITY_FACTOR);
                String userInput = ui.getUserInput();
                checkEmptyUserInput(userInput);
                int activityFactorInput = Integer.parseInt(userInput);
                newProfile.setProfileActivityFactor(new ActivityFactor(activityFactorInput));
                //TODO: add a print statement to tell user input is incorrect
                String messageValidAttribute = newProfile.getProfileActivityFactor().isValid()
                        ? printMessage("activity factor") : profile.ERROR_ACTIVITY_FACTOR;
                ui.formatMessageFramedWithDivider(messageValidAttribute);
            } catch (NumberFormatException e) {
                ui.formatMessageFramedWithDivider(MESSAGE_INVALID_POSITIVE_DOUBLE_INPUT);
            }
        }
    }

    /**
     * Creates a valid profile calorie goal for the profile instance.
     *
     * @param newProfile instance of a profile class.
     * @throws ParamMissingException if user input a string of 0 characters.
     */
    private void createNewProfileCalorieGoal(Profile newProfile) throws ParamMissingException {
        boolean checkInput = false;// check whether calorie goal has the correct input
        do {
            try {
                ui.formatMessageFramedWithDivider(MESSAGE_INTRO_CALORIE_GOAL);
                String userInput = ui.getUserInput();
                checkEmptyUserInput(userInput);
                int calorieGoalInput = Integer.parseInt(userInput);
                newProfile.setProfileCalorieGoal(new CalorieGoal(calorieGoalInput));
                //TODO: add a print statement to tell user input is incorrect
                String messageValidAttribute = newProfile.getProfileCalorieGoal().isValid()
                        ? printMessage("calorie goal") :
                        String.format(profile.ERROR_CALORIE_GOAL, calorieGoalInput);
                ui.formatMessageFramedWithDivider(messageValidAttribute);
                checkInput = true;
            } catch (NumberFormatException e) {
                ui.formatMessageFramedWithDivider(MESSAGE_INVALID_POSITIVE_INT_INPUT);
            }
        } while (!checkInput || !newProfile.getProfileCalorieGoal().isValid());
    }

    /**
     * Creates a valid profile age for the profile instance.
     *
     * @param newProfile instance of a profile class.
     * @throws ParamMissingException if user input a string of 0 characters.
     */
    private void createNewProfileAge(Profile newProfile) throws ParamMissingException {
        while (!newProfile.getProfileAge().isValid()) {
            try {
                ui.formatMessageFramedWithDivider(MESSAGE_INTRO_AGE);
                String userInput = ui.getUserInput();
                checkEmptyUserInput(userInput);
                int ageInput = Integer.parseInt(userInput);
                newProfile.setProfileAge(new Age(ageInput));
                String messageValidAttribute = newProfile.getProfileAge().isValid()
                        ? printMessage("Age") : profile.ERROR_AGE;
                ui.formatMessageFramedWithDivider(messageValidAttribute);
            } catch (NumberFormatException e) {
                ui.formatMessageFramedWithDivider(MESSAGE_INVALID_POSITIVE_INT_INPUT);
            }
        }
    }

    /**
     * Creates a valid profile gender for the profile instance.
     *
     * @param newProfile instance of a profile class.
     * @throws ParamMissingException if user input a string of 0 characters.
     */
    private void createNewProfileGender(Profile newProfile) {
        while (!newProfile.getProfileGender().isValid()) {
            ui.formatMessageFramedWithDivider(MESSAGE_INTRO_GENDER);
            String userInput = ui.getUserInput();
            if (userInput.length() == 1) {
                char genderInput = userInput.charAt(0);
                newProfile.setProfileGender(new Gender(genderInput));
            }
            String messageValidAttribute = newProfile.getProfileGender().isValid()
                    ? printMessage("gender") : profile.ERROR_GENDER;
            ui.formatMessageFramedWithDivider(messageValidAttribute);
        }
    }

    /**
     * Creates a valid profile weight for the profile instance.
     *
     * @param newProfile instance of a profile class.
     * @throws ParamMissingException if user input a string of 0 characters.
     */
    private void createNewProfileWeight(Profile newProfile) throws ParamMissingException {
        while (!newProfile.getProfileWeight().isValid()) {
            try {
                ui.formatMessageFramedWithDivider(MESSAGE_INTRO_WEIGHT);
                String userInput = ui.getUserInput();
                checkEmptyUserInput(userInput);
                double weightInput = Double.parseDouble(userInput);
                newProfile.setProfileWeight(new Weight(weightInput));
                String messageValidAttribute = newProfile.getProfileWeight().isValid()
                        ? printMessage("weight") : profile.ERROR_WEIGHT;
                ui.formatMessageFramedWithDivider(messageValidAttribute);
            } catch (NumberFormatException e) {
                ui.formatMessageFramedWithDivider("Invalid input, please input a valid positive number");
            }
        }
    }

    /**
     * Creates a valid profile height for the profile instance.
     *
     * @param newProfile instance of a profile class.
     * @throws ParamMissingException if user input a string of 0 characters.
     */
    private void createNewProfileHeight(Profile newProfile) throws ParamMissingException {
        while (!newProfile.getProfileHeight().isValid()) {
            try {
                ui.formatMessageFramedWithDivider(MESSAGE_INTRO_HEIGHT);
                String userInput = ui.getUserInput();
                checkEmptyUserInput(userInput);
                double heightInput = Double.parseDouble(userInput);
                newProfile.setProfileHeight(new Height(heightInput));
                String messageValidAttribute = newProfile.getProfileHeight().isValid()
                        ? printMessage("height") : profile.ERROR_HEIGHT;
                ui.formatMessageFramedWithDivider(messageValidAttribute);
            } catch (NumberFormatException e) {
                ui.formatMessageFramedWithDivider(MESSAGE_INVALID_POSITIVE_DOUBLE_INPUT);
            }
        }
    }

    /**
     * Creates a valid profile name for the profile instance.
     *
     * @param newProfile instance of a profile class.
     * @throws ParamMissingException if user input a string of 0 characters.
     */
    private void createNewProfileName(Profile newProfile) throws ParamMissingException {
        while (!newProfile.getProfileName().isValid()) {
            ui.formatMessageFramedWithDivider(MESSAGE_INTRO_NAME);
            String userInput = ui.getUserInput();
            checkEmptyUserInput(userInput);
            newProfile.setProfileName(new Name(userInput));
            String messageValidAttribute = newProfile.getProfileName().isValid()
                    ? printMessage("name") : profile.ERROR_NAME;
            ui.formatMessageFramedWithDivider(messageValidAttribute);
        }
        assert newProfile.getProfileName().isValid() : " name is valid";
    }

    /**
     * Checks if user input is empty.
     *
     * @param userInput input from the user.
     * @throws ParamMissingException if input length is 0 (missing).
     */
    private void checkEmptyUserInput(String userInput) throws ParamMissingException {
        if (userInput.length() == 0) {
            throw new ParamMissingException(MESSAGE_EMPTY_INPUT);
        }
    }

    private String printMessage(String attribute) {
        return String.format(MESSAGE_INPUT_IS_SUCCESSFUL, attribute);
    }

}