public class MonthlyUsageAnalyzer {

    public static void main(String[] args) {

        int[] usageData = {
                120, 150, 100, 180,
                200, 170, 160, 190,
                210, 220, 180, 250
        };

        int sum = 0;
        int lowest = usageData[0];
        int highest = usageData[0];

        for (int value : usageData) {

            sum += value;

            if (value < lowest) {
                lowest = value;
            }

            if (value > highest) {
                highest = value;
            }
        }

        double averageUsage = (double) sum / Constants.MONTHS;

        System.out.println("===== Monthly Usage Analyzer =====");

        System.out.println("Total Usage   : " + sum);
        System.out.println("Average Usage : " + averageUsage);
        System.out.println("Minimum Usage : " + lowest);
        System.out.println("Maximum Usage : " + highest);

        int maximumInteger = Integer.MAX_VALUE;

        System.out.println("\n===== Integer Overflow =====");

        System.out.println("Maximum int : " + maximumInteger);
        System.out.println("After +1   : " + (maximumInteger + 1));

        long overflowSafeValue = (long) Integer.MAX_VALUE + 1;

        System.out.println("\n===== Fixed Using long =====");

        System.out.println("Safe value  : " + overflowSafeValue);

        int number = 100;
        long convertedLong = number;

        long value = 1000L;
        int convertedInt = (int) value;

        System.out.println("\n===== Type Casting =====");

        System.out.println("Widening int -> long : " + convertedLong);
        System.out.println("Narrowing long -> int : " + convertedInt);

        double firstValue = 0.1;
        double secondValue = 0.2;

        System.out.println("\n===== Floating Point Precision =====");

        System.out.println("0.1 + 0.2 = " + (firstValue + secondValue));

        int[][] weeklyHouseUsage = {
                {10, 12, 11, 15, 13, 14, 16},
                {20, 18, 22, 21, 19, 23, 20},
                {8, 9, 10, 7, 11, 12, 10}
        };

        System.out.println("\n===== Weekly Usage =====");

        for (int i = 0; i < weeklyHouseUsage.length; i++) {

            System.out.print("House " + (i + 1) + ": ");

            for (int j = 0; j < weeklyHouseUsage[i].length; j++) {
                System.out.print(weeklyHouseUsage[i][j] + " ");
            }

            System.out.println();
        }
    }
}
