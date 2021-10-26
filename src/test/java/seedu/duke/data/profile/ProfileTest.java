package seedu.duke.data.profile;

import org.junit.jupiter.api.Test;
import seedu.duke.data.profile.exceptions.InvalidCharacteristicException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class ProfileTest {

    @Test
    void calculateBmi_twoDoubleInputs_expectDoubleReturned() throws InvalidCharacteristicException {
        final double height = 171.2;
        final double weight = 59.8;
        assertEquals(20.4, Profile.calculateBmi(height, weight));
    }

    @Test
    void calculateBmi_negativeHeightInput_expectInvalidCharacteristicException() {
        final double height = -171.2;
        final double weight = 59.8;
        assertThrows(InvalidCharacteristicException.class,
            () -> Profile.calculateBmi(height, weight));
    }

    @Test
    void calculateBmi_negativeWeightInput_expectInvalidCharacteristicException() {
        final double height = 171.2;
        final double weight = -59.8;
        assertThrows(InvalidCharacteristicException.class,
            () -> Profile.calculateBmi(height, weight));
    }

    @Test
    void retrieveBmiStatus_validBmiInputs_expectCorrectStatuses() {
        final String expectedStatusUnderweight = "Underweight";
        final String expectedStatusHealthy = "Healthy";
        final String expectedStatusOverweight = "Overweight";
        final String expectedStatusObese = "Obese";
        final double bmiUnderweight = 10.5;
        final double bmiHealthy = 22.5;
        final double bmiOverweight = 25.5;
        final double bmiObese = 30.0;

        assertEquals(expectedStatusUnderweight, Profile.retrieveBmiStatus(bmiUnderweight));
        assertEquals(expectedStatusHealthy, Profile.retrieveBmiStatus(bmiHealthy));
        assertEquals(expectedStatusOverweight, Profile.retrieveBmiStatus(bmiOverweight));
        assertEquals(expectedStatusObese, Profile.retrieveBmiStatus(bmiObese));
    }

    @Test
    void getBmrValuesMen_validInputs_expectCorrectBmrValues() {
        Profile p = new Profile();
        final String name = "John";
        final double height = 170.1;
        final double weight = 60;
        final int calorieGoal = 300;
        final char gender = 'M';
        final int age = 22;
        final int activityFactor = 1;
        final double baseBmr = 1583.5979;
        final int bmrSedentary = (int) Math.round(baseBmr * 1.2);
        final int bmrLight = (int) Math.round(baseBmr * 1.375);
        final int bmrModerate = (int) Math.round(baseBmr * 1.55);
        final int bmrIntense = (int) Math.round(baseBmr * 1.725);
        final int bmrExtreme = (int) Math.round(baseBmr * 1.9);

        p.setProfileWithRawInputs(name, height, weight, gender, age, calorieGoal, activityFactor);
        assertEquals(bmrSedentary, p.getBmr());
        p.getProfileActivityFactor().setActivityFactor(2);
        assertEquals(bmrLight, p.getBmr());
        p.getProfileActivityFactor().setActivityFactor(3);
        assertEquals(bmrModerate, p.getBmr());
        p.getProfileActivityFactor().setActivityFactor(4);
        assertEquals(bmrIntense, p.getBmr());
        p.getProfileActivityFactor().setActivityFactor(5);
        assertEquals(bmrExtreme, p.getBmr());
    }

    @Test
    void getBmrValuesFemale_validInputs_expectCorrectBmrValues() {
        Profile p = new Profile();
        final String name = "Mary";
        final double height = 160.1;
        final double weight = 45.2;
        final int calorieGoal = 300;
        final char gender = 'F';
        final int age = 20;
        final int activityFactor = 1;
        final double baseBmr = 1274.9472;
        final int bmrSedentary = (int) Math.round(baseBmr * 1.2);
        final int bmrLight = (int) Math.round(baseBmr * 1.375);
        final int bmrModerate = (int) Math.round(baseBmr * 1.55);
        final int bmrIntense = (int) Math.round(baseBmr * 1.725);
        final int bmrExtreme = (int) Math.round(baseBmr * 1.9);

        p.setProfileWithRawInputs(name, height, weight, gender, age, calorieGoal, activityFactor);
        assertEquals(bmrSedentary, p.getBmr());
        p.getProfileActivityFactor().setActivityFactor(2);
        assertEquals(bmrLight, p.getBmr());
        p.getProfileActivityFactor().setActivityFactor(3);
        assertEquals(bmrModerate, p.getBmr());
        p.getProfileActivityFactor().setActivityFactor(4);
        assertEquals(bmrIntense, p.getBmr());
        p.getProfileActivityFactor().setActivityFactor(5);
        assertEquals(bmrExtreme, p.getBmr());
    }

    @Test
    void toFileTextString_validInputs_expectCorrectString() {
        Profile p = new Profile();
        String name = "John";
        double height = 170.1;
        double weight = 60;
        int calorieGoal = 300;
        char gender = 'M';
        int age = 22;
        int activityFactor = 1;

        p.setProfileWithRawInputs(name, height, weight, gender, age, calorieGoal, activityFactor);
        String correctOutput = "John|170.1|60.0|M|22|300|1";
        assertEquals(correctOutput, p.toFileTextString());
    }

}