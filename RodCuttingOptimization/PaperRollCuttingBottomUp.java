package com.company;

//Eoin Wickens
//R00151204
//Eoin.Wickens@mycit.ie

public class PaperRollCuttingBottomUp {

    //costs of each cut
    static double [] costs = new double[] {0.0, 1.2, 3.0, 5.8, 0.0, 10.1};

    static int roll_length = 7;

    //array to hold the location of the cuts

    static int[] loc_of_cuts = new int[roll_length+1]; //As number of cuts can only be so much

    public static void main(String[] args) {

        System.out.println("Best Cost: " + roll_cut(costs, roll_length) + "\n");

        System.out.println("Roll lengths: ");

        //works through the cuts by removing them from the roll_length each loop
        while(roll_length > 0){
            System.out.print(loc_of_cuts[roll_length] + " ");
            roll_length = roll_length - loc_of_cuts[roll_length];
        }
    }

    public static double roll_cut(double[] costs, int roll_length){

        double[] solutions = new double[roll_length+1]; //Plus 1 is for the sum length otherwise we will get a null pointer
        boolean[] check_index = new boolean[roll_length+2];//accounts for 4 being 0.0 otherwise we get an index out of bounds on check_index

        //Sets the values in the check_index array to true if the costs[i] is not 0.
        //This ensures that the loc of cuts array is in line and does not give out answers >costs.length over 40
        for(int i = 1; i < costs.length; i++){
            if(costs[i] != 0.0){
                check_index[i] = true;
            }
        }
        solutions[0] = 0;
        double x = Double.MIN_VALUE;

        if (roll_length == 0){ //Base case - If length is zero there's nothing to return
            System.out.println("Rod length is 0");
            return solutions[0];
        }


        for(int i = 1; i <= roll_length; i++) {
            double calc_price = 0;

            for (int j = 1; j <= i; j++) {
                //If the index is false then skip this iteration to prevent errors in vals over 40
                if(!check_index[j])
                    continue;
                //calcs 1-5
                if(i < costs.length){
                    x = costs[j] + solutions[i - j];
                }
                //calcs 6 and on using the precalculated costs
                else{
                    x = solutions[j] + solutions[i - j];
                }

                //Checks to ensure that the calculated price is the best price
                if (calc_price < x) {
                    calc_price = Double.max(calc_price, x);
                    System.out.println("First Loop: j: " + j + " i: " + i + " i-j: " + (i-j) + " Cost: " + x);
                    loc_of_cuts[i] = (j);
                }
            }
            solutions[i] = calc_price;
        }
        return solutions[roll_length];
    }
}
