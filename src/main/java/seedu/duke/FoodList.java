package seedu.duke;

import java.util.ArrayList;

public class FoodList {
    private ArrayList<Food> foodRecords = new ArrayList<>();

    /**
     * Returns food item in the food list
     *
     * @param index The index of the food item
     * @return Returns the food item with the given index
     */
    public Food get(int index) {
        return this.foodRecords.get(index);
    }

    /**
     * Returns the size of the array list
     *
     * @return Returns the size of the array list attribute
     */
    public int size() {
        return foodRecords.size();
    }

    /**
     * Adds a food item into the food list
     *
     * @param food The food class object to add
     */
    public void add(Food food) {
        this.foodRecords.add(food);
    }

    /**
     * Adds a food item according to its name and calorie into the food list
     *
     * @param foodName The name description of the food
     * @param calorie  The calorie of the food
     */
    public void add(String foodName, int calorie) {
        this.foodRecords.add(new Food(foodName, calorie));
    }

    /**
     * Prints the list of all the food items
     */
    public void printFoodList() {
        if (foodRecords.size() > 0) {
            System.out.println("You have consumed " + foodRecords.size() + " food items:");
            for (int i = 0; i < foodRecords.size(); i++) {
                System.out.println("\t" + (i + 1) + ". " + foodRecords.get(i));
            }
        } else {
            System.out.println("\tThere is no record in the food list.");
        }
    }

    /**
     * Deletes a food item according to its index number
     *
     * @param index The index of the food item
     */
    public void delete(int index) {
        Food deletedFood = foodRecords.remove(index);
        System.out.println("You have removed this food item: ");
        System.out.println("\t" + deletedFood);
        System.out.println("There are " + foodRecords.size() + " food left.");
    }

    /**
     * Deletes all the food items from the food list
     */
    public void deleteAll() {
        this.foodRecords.clear();
        System.out.println("All food items have been deleted.");
    }
}
