package jobScheduler;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// Driver program here

        Scanner reader = new Scanner(System.in);    //reader for Input
        int[] job_length;
        int[] job_deadline;
        int[] job_profit;

        //testing the Job & JobScheduler classes-----
        System.out.print("How many jobs would you like to enter?: ");
        int total_jobs = reader.nextInt();
        job_length = new int[total_jobs];
        job_deadline = new int[total_jobs];
        job_profit = new int[total_jobs];

        for(int i=0; i< total_jobs; i++){
            System.out.println("\n\nJob #"+ (i + 1) + " -----");
            System.out.print("\nJob length: ");
            int length = reader.nextInt();
            System.out.print("\nJob deadline: ");
            int deadline = reader.nextInt();
            System.out.print("\nJob profit: ");
            int profit = reader.nextInt();

            //Add the job to the JobScheduler arrays
            job_length[i] = length;
            job_deadline[i] = deadline;
            job_profit[i] = profit;
        }

        //create the JobScheduler object
        JobScheduler schedule = new JobScheduler(job_length, job_deadline, job_profit);
        System.out.println("\nHere are the Jobs you wish to schedule" +
                            "\n[Format: Job#(length, deadline, profit, start, finish)]");
        schedule.printJobs();
        //--------------------------

        //Testing mergeSort by Deadline
        System.out.println("\nTesting mergeSort by Deadline");

        int[] sortedJobs = schedule.mergeSort("deadline");
        boolean sortTest = schedule.isSorted(sortedJobs);
        if(sortTest){
            System.out.println("Jobs were successfully sorted by Deadline");
        }
        else{
            System.out.println("!!!mergeSort by Deadline Failed!");
        }

        //Testing mergeSort by Length
        System.out.println("\nTesting mergeSort by Length");
        sortedJobs = schedule.mergeSort("length");
        sortTest = schedule.isSorted(sortedJobs);
        if(sortTest){
            System.out.println("Jobs were successfully sorted by Length");
        }
        else{
            System.out.println("!!!mergeSort by Length Failed!");
        }

        //Testing mergeSort by Profit
        System.out.println("\nTesting mergeSort by Profit");
        sortedJobs = schedule.mergeSort("profit");
        sortTest = schedule.isSorted(sortedJobs);
        if(sortTest){
            System.out.println("Jobs were successfully sorted by Profit");
        }
        else{
            System.out.println("!!!mergeSort by Profit Failed!");
        }

        //



    }
}
