package pgdp.searchengine.util;

public class Date {
    private final int day;
    private final int month;
    private final int year;

    public Date(int day, int month, int year) {
        if (isValidDate(day, month, year)){
            this.day = day;
            this.month = month;
            this.year = year;
        }
        else {
            this.day = -1;
            this.month = -1;
            this.year = -1;
            System.out.println("Der "+day + "." + month + "." + year+" ist kein valides Datum");

        }

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


    public static boolean isLeapYear(int year){
        if (year == 0){
            return true;
        }
        if ((year % 4 == 0) && !(year % 100 == 0)){
            return true;
        }
        return year % 400 == 0;

    }
    public static int daysInYear(int year){
        if (isLeapYear(year)){
            return 366;
        }
        return 365;
    }

    public static int daysInMonth(int month, int year){
        int days;
        switch (month) {
            case 4, 6, 9, 11:
                days = 30;
                break;
            case 2:
                if (isLeapYear(year)) {
                    days = 29;
                }
                else days = 28;
                break;
            default:
                days = 31;
                break;
        }
        return days;
        }

        public static boolean isValidDate(int day, int month, int year) {
        if (!isLeapYear(year)){
            if (month == 2 && day == 29){
                return false;
            }
        }
        if (day > 0 && day <= 31) {
                if (month > 0 && month <= 12){
                    return daysInMonth(month, year) >= day;
                }
            }
        return false;
        }
        public int daysLeftThisYear(){
            if (!isValidDate(day, month, year)){
                System.out.println("Methode auf ungültigem Datum aufgerufen!");
            }
        int days = 0;
        for(int i = 1; i < month; i++){
            days += daysInMonth(i, this.getYear());
        }
        return daysInYear(this.year) - (days +day);
        }

        public int daysPassedThisYear(){
            if (!isValidDate(day, month, year)){
                System.out.println("Methode auf ungültigem Datum aufgerufen!");
            }
            int days = 0;
            for(int i = 1; i < month; i++){
                days += daysInMonth(i, this.getYear());
            }
            return days += day;
        }

        public int yearsUntil(Date other) {
            if (!isValidDate(other.getDay(), other.getMonth(), other.getYear())){
                System.out.println("Methode auf ungültigem Datum aufgerufen!");
            }
            if (!isValidDate(day, month, year)){
                System.out.println("Methode auf ungültigem Datum aufgerufen!");
            }
            int yearUntil = other.getYear() - year;
            if (other.getMonth() < month) {
                yearUntil -= 1;
            } else if (other.day < day && yearUntil >= 0) {
                yearUntil -= 1;
            }
                return yearUntil;
        }
        public int daysUntil(Date other){
        if (!isValidDate(other.getDay(), other.getMonth(), other.getYear())){
            System.out.println("Methode auf ungültigem Datum aufgerufen!");
        }
            if (!isValidDate(day, month, year)){
                System.out.println("Methode auf ungültigem Datum aufgerufen!");
            }
        int daysUntil = 0;
            int yearsDays = 0;
            if (year != other.getYear()){
                for (int i = Math.min(other.getYear(), year); i < Math.max(other.getYear(), year)-1; i++){
                yearsDays += daysInYear(i);}
                System.out.println(yearsDays);
                if (year > other.getYear() || year == other.getYear() && month >= other.getMonth() && day > other.getDay()) {
                    return -1*(this.daysPassedThisYear() + other.daysLeftThisYear() + yearsDays);
                }
                else return this.daysLeftThisYear() + other.daysPassedThisYear() + yearsDays;
           }
            if (year > other.getYear() || year == other.getYear() && month >= other.getMonth() && day > other.getDay()) {
                return  other.daysPassedThisYear()- this.daysPassedThisYear();
            }
            return -1* (this.daysPassedThisYear() - other.daysPassedThisYear());
        }

        public static Date dateMillisecondsAfterNewYear1970(long millis){
            long days = 0;
            if (millis < 0) {
                days = (millis - 86399999) / 86400000;
            } else days = millis / 86400000;
            System.out.println(days);
            int dayOut = 1;
            int monOut = 1;
            int yearOut = 1970;

            while (days > 0) {
                if (dayOut == daysInMonth(monOut, yearOut)) {
                    if (monOut == 12) {
                        monOut = 1;
                        yearOut += 1;}
                    else {
                        monOut += 1;}
                    dayOut = 1;}
                else {
                    dayOut += 1;}
                days -= 1;}

            while (days < 0) {
                if (dayOut == 1) {
                    if (monOut == 1) {
                        dayOut = 31;
                        monOut = 12;
                        yearOut -= 1;
                    } else {
                        monOut -= 1;
                        dayOut = daysInMonth(monOut, yearOut);}
                } else {
                    dayOut -= 1;}
                days += 1;}
            
        return new Date (dayOut, monOut, yearOut);
        }

        public static Date today(){
        return dateMillisecondsAfterNewYear1970(System.currentTimeMillis());
        }

    public static void main (String[] args){
    }
}
