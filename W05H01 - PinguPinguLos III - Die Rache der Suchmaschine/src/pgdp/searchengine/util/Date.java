package pgdp.searchengine.util;

public class Date {
    private final int day;
    private final int month;
    private final int year;

    public Date(int day, int month, int year) {
        if (!isValidDate(day, month, year)) {
            this.day = this.month = this.year = -1;
            System.out.println("Der " + day + "." + month + "." + year + " ist kein gültiges Datum!");
            return;
        }
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public static boolean isLeapYear(int year) {
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
    }

    public static int daysInYear(int year) {
        return isLeapYear(year) ? 366 : 365;
    }

    public static int daysInMonth(int month, int year) {
        return switch (month) {
            case 1, 3, 5, 7, 8, 10, 12 -> 31;
            case 4, 6, 9, 11 -> 30;
            case 2 -> isLeapYear(year) ? 29 : 28;
            default -> -1;
        };
    }

    public static boolean isValidDate(int day, int month, int year) {
        return 1 <= month && month <= 12
                && 1 <= day && day <= daysInMonth(month, year);

    }


    public int daysLeftThisYear() {
        if (day == -1) {
            System.out.println("Methode auf ungültigem Datum aufgerufen!");
            return 0;
        }

        int days = daysInMonth(month, year) - day;
        for (int currentMonth = this.month + 1; currentMonth <= 12; currentMonth++) {
            days += daysInMonth(currentMonth, year);
        }
        return days;
    }

    public int daysPassedThisYear() {
        if (day == -1) {
            System.out.println("Methode auf ungültigem Datum aufgerufen!");
            return 0;
        }

        int days = 0;
        for (int currentMonth = 1; currentMonth < month; currentMonth++) {
            days += daysInMonth(currentMonth, year);
        }
        return days + day;
    }


    public int yearsUntil(Date other) {
        if (day == -1 || other.day == -1) {
            System.out.println("Methode auf ungültigem Datum aufgerufen!");
            return 0;
        }

        int diff = other.year - year;
        if (month > other.month) {
            return diff - 1;
        } else if (month < other.month) {
            return diff;
        } else if (day > other.day) {
            return diff - 1;
        } else {
            return diff;
        }
    }

    public int daysUntil(Date other) {
        if (day == -1 || other.day == -1) {
            System.out.println("Methode auf ungültigem Datum aufgerufen!");
            return 0;
        }

        if (year < other.year) {
            int days = daysLeftThisYear() + other.daysPassedThisYear();
            for (int currentYear = year + 1; currentYear < other.year; currentYear++)
                days += daysInYear(currentYear);
            return days;
        } else if (year == other.year) {
            return daysLeftThisYear() + other.daysPassedThisYear() - daysInYear(year);
        } else {
            int days = -daysPassedThisYear() - other.daysLeftThisYear();
            for (int currentYear = year - 1; currentYear > other.year; currentYear--)
                days -= daysInYear(currentYear);
            return days;
        }
    }

    public static Date dateMillisecondsAfterNewYear1970(long millis) {
        if (millis >= 0) {
            int days = (int) (millis / 86_400_000);
            int currentYear = 1970;
            while (days >= daysInYear(currentYear)) {
                days -= daysInYear(currentYear);
                currentYear++;
            }
            int currentMonth = 1;
            while (days >= daysInMonth(currentMonth, currentYear)) {
                days -= daysInMonth(currentMonth, currentYear);
                currentMonth++;
            }
            int currentDay = days + 1;
            return new Date(currentDay, currentMonth, currentYear);
        } else {
            int days = (int) ((-millis + 1) / 86_400_000);
            int currentYear = 1969;
            while (days >= daysInYear(currentYear)) {
                days -= daysInYear(currentYear);
                currentYear--;
            }
            int currentMonth = 12;
            while (days >= daysInMonth(currentMonth, currentYear)) {
                days -= daysInMonth(currentMonth, currentYear);
                currentMonth--;
            }
            int currentDay = daysInMonth(currentMonth, currentYear) - days;

            return new Date(currentDay, currentMonth, currentYear);
        }
    }

    public static Date today() {
        return dateMillisecondsAfterNewYear1970(System.currentTimeMillis());
    }

    public boolean equals(Date other) {
        return day == other.day && month == other.month && year == other.year;
    }

    @Override
    public String toString() {
        return day + "." + month + "." + year;
    }

    //Getter
    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }
}
