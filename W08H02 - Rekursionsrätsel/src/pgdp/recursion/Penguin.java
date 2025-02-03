package pgdp.recursion;


/**
 * This class defines the penguins to be placed on the game field.
 */

public class Penguin {

    private Family family;

    private int familyNumber;
    private boolean isBest;

    public Penguin(int familyNumber, boolean isBest) {
        this.familyNumber = familyNumber;
        this.isBest = isBest;
    }

    public int getFamilyNumber() {
        return familyNumber;
    }

    public boolean isBest() {
        return isBest;
    }

    public enum Family {
        ICE_QUEENS(1, "Ice Queens"),
        SNOW_PATROL(2, "Snow Patrol"),
        FROSTY_FEET(3, "Frosty Feet"),
        GLACIER_GUARDS(4, "Glacier Guards"),
        ARCTIC_ANGELS(5, "Arctic Angels"),
        POLAR_PIONEERS(6, "Polar Pioneers"),
        CHILL_CHAMPIONS(7, "Chill Champions"),
        BLIZZARD_BRIGADE(8, "Blizzard Brigade"),
        WINTER_WARRIORS(9, "Winter Warriors");

        private final int number;
        private final String name;

        Family(int number, String name) {
            this.number = number;
            this.name = name;
        }

        public int getNumber() {
            return number;
        }

        public String getName() {
            return name;
        }

        public static Family getByNumber(int number) {
            for (Family family : Family.values()) {
                if (family.number == number) {
                    return family;
                }
            }
            throw new IllegalArgumentException("Invalid family number: " + number);
        }
    }

    public Penguin(int familyNumber) {
        this.family = Family.getByNumber(familyNumber);
    }

    public Family getFamily() {
        return family;
    }

    @Override
    public String toString() {
        return "Penguin from family: " + family.getName() + " (Family Number: " + family.getNumber() + ")";
    }

}
