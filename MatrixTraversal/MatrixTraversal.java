package com.company;

//Eoin Wickens
//R00151204
//Eoin.Wickens@mycit.ie


public class RobotMoving {

    public static void main(String[] args) {
        int matrix_size = 10; //Size of matrix

        double[][] matrix = new double[matrix_size][matrix_size];

        double[][] cost_matrix_one = { //First cost matrix
                {-1, 1.1},
                {1.3, 2.5}
        };
        double[][] cost_matrix_two = { //second cost matrix
                {-1, 1.5},
                {1.2, 2.3}
        };

        //Initialized Matrix
        System.out.println("Empty Matrix:");
        System.out.println(toString(matrix));
        System.out.println("Matrix length set to: " + matrix_size);


        System.out.println("Cost Matrix 1:");
        double[][] matrix_one_result = calculate_cost(matrix, cost_matrix_one);
        System.out.println(toString(matrix_one_result));
        double[][] path_matrix = best_path(matrix_one_result, cost_matrix_one);
        System.out.println("Path Matrix 1:");

        //Retrieves best path from the calculated matrix
        System.out.println(toString(path_matrix));
        System.out.println("Best Cost for Matrix 1: " + String.format("%.1f\n", matrix_one_result[matrix_size - 1][matrix_size - 1]));


        //Calculates second cost matrix
        System.out.println("Cost Matrix 2:");
        double[][] matrix_two_result = calculate_cost(matrix, cost_matrix_two);
        System.out.println(toString(matrix_two_result));
        //Calculates the path used to get best cost
        path_matrix = best_path(matrix_two_result, cost_matrix_two);

        System.out.println("Path Matrix 2:");
        System.out.println(toString(path_matrix));

        //Retrieves best cost from second calculated cost matrix
        System.out.println("Best Cost for Matrix 2: " + String.format("%.1f\n", matrix_two_result[matrix_size - 1][matrix_size - 1]));


    }
    //Function to calculate the min of three values and return it
    private static double min(double x, double y, double z) {
        if (x < y)
            return Math.min(x, z);
        else
            return Math.min(y, z);
    }

    //Calculates the larger cost matrix using dynamic programming
    public static double[][] calculate_cost(double[][] matrix, double[][] cost) {

        int i, j;
        int m = matrix.length;
        int n = matrix.length;

        double[][] temp = new double[m][n];
        temp[0][0] = matrix[0][0];

        //initialize first row
        for (j = 1; j < m; j++)
            temp[0][j] = temp[0][j - 1] + cost[0][1];

        // initialize rest of array
        for (i = 1; i < m; i++) {
            temp[i][0] = temp[i - 1][0] + cost[1][0];

            for (j = 1; j < m; j++) {
                temp[i][j] = min((temp[i][j - 1] + cost[0][1]),
                        (temp[i - 1][j] + cost[1][0]),
                        (temp[i - 1][j - 1]) + cost[1][1]);
            }
        }

        return temp;
    }
    public static double[][] best_path(double[][]cost, double[][] original_cost){
        int i,j;
        int m = cost.length;
        double[][] sol_matrix = new double[m][m];

        i = 0;
        j = 0;

        double min = 0;

        while(i < m && j < m){

            //Case for last index - Reaching the end
            if(i == m-1 && j == m-1){
                sol_matrix[i][j] = cost[i][j];
                break;
            }
            if(i == m-1){ // if we've reached the bottom row - stops oob
                j++; //increments J to move down as we cannot move back or up.
            }
            else if(j == m-1){// if we've reached the end col - stops oob
                i++; //increments J to move right as we cannot move back or up.
            }
            else{
                //Gets the min of the possible moves
                min = min((cost[i][j+1]),
                        (cost[i+1][j]),
                        (cost[i+1][j+1]));

                //This is used to check if a diagonal is weighted lower than to go right then down.
                if((original_cost[1][1]) < (cost[1][0] + cost[0][1])){
                    min =cost[i+1][j+1];
                }
                //if min is equal to the calc cost matrix increment j to move to that index
                if(min == cost[i][j+1]) {
                    //Right
                    j++;
                }
                //if min is equal to the calc cost matrix increment i to move to that index
                else if(min == cost[i+1][j]) {
                    //Down
                    i++;
                }
                //if min is equal to the calc cost matrix increment i to move to that index
                else if(min == cost[i+1][i+1]) {
                    //Right-Down
                    i++;
                    j++;
                }
            }
            sol_matrix[i][j] = cost[i][j];
        }
        return sol_matrix;
    }
    //builds the matrix
    public static String toString(double[][] matrix){
        StringBuilder toString = new StringBuilder();

        for(int i = 0; i<matrix.length; i++){
            for (int j = 0; j<matrix.length; j++){
                toString.append("[").append(String.format("%.1f",matrix[i][j])).append("]");
            }
            toString.append("\n");
        }
        return toString.toString();
    }
}
