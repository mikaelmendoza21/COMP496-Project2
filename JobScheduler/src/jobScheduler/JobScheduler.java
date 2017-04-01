/**
 COMP496 - 11am - CSUN Spring 2017
 Project 2 - JobScheduler
 Team Members:
 Mikael A. Mendoza
 Jonathan Villegas
 */

package jobScheduler;

import jdk.nashorn.internal.scripts.JO;

import java.util.ArrayList;

public class JobScheduler
{
    private int nJobs;
    private Job[]  jobs;

    public JobScheduler( int[] joblength, int[] deadline, int[] profit)
    {
        //Set nJobs
        nJobs = joblength.length;

        //Fill jobs array. The kth job entered has JobNo = k;
        jobs = new Job[nJobs];
        for(int i = 0; i < nJobs; i++){
            jobs[i] =  new Job(i, joblength[i], deadline[i], profit[i]);
        }
    }

    public void printJobs()  //prints the array jobs
    {
        for(int i = 0; i < nJobs; i++){
            System.out.print("\n" + jobs[i].toString());
        }
    }

        //Brute force. Try all n! orderings. Return the schedule with the most profit
    public Schedule bruteForceSolution()
    {
        Job[] initialJobs = jobs.clone();
        Job[] temp = new Job[nJobs];

        //create initial schedule
        Schedule initialSchedule = createSchedule(initialJobs);
        int max = initialSchedule.getProfit();

        Job[] jobsInSchedule = recursiveBruteForce(0, initialJobs, temp, max);

        //temp
        System.out.println("---jobsInSchedule size = " + jobsInSchedule.length);
        //

        //Create Schedule
        Schedule BFSchedule = createSchedule(jobsInSchedule);
        //temp
        System.out.println("---"+ BFSchedule.toString());
        //

        return BFSchedule;
    }

    public Job[] recursiveBruteForce(int index, Job[] initialJobs, Job[] temp, int max){

        Job[] currentBest = initialJobs;

        if(index >= nJobs - 1){ //If we are at the last element - nothing left to permute
//            //Print the array
//            System.out.print("[");
//            for(int i = 0; i < nJobs - 1; i++){
//                System.out.print(temp[i] + ", ");
//            }
//            if(temp.length > 0)
//                System.out.print(temp[temp.length - 1]);
//            System.out.println("]");

        }
        else{

            for(int i = index; i < nJobs; i++) { //For each index in the sub array arr[index...end]

                //Swap the elements at indices index and i
                Job t = initialJobs[index];

                temp[index] = temp[i];
                temp[i] = t;

                //Recurse on the sub array arr[index+1...end]
                temp = recursiveBruteForce(index + 1, initialJobs, temp, max);

                //Swap the elements back
                t = temp[index];
                temp[index] = temp[i];
                temp[i] = t;

                //Create a Schedule from current Permutation
                Schedule currentPermutation = createSchedule(temp);

                if(currentPermutation.getProfit() > max){
                    currentBest = temp;
                    max = currentPermutation.getProfit();
                }
            }
        }


        return currentBest;
    }

//
    public Schedule makeScheduleEDF()
    //earliest deadline first schedule. Schedule items contributing 0 to total profit last
    {
        Job[] sortedJobs = sortByDeadline();

        return createSchedule(sortedJobs);
    }
    //
    public Schedule makeScheduleSJF()
    //shortest job first schedule. Schedule items contributing 0 to total profit last
    {

        Job[] sortedJobs = sortByLength();

        return createSchedule(sortedJobs);
    }


    public Schedule makeScheduleHPF()
    //highest profit first schedule. Schedule items contributing 0 to total profit last
    {

        Job[] sortedJobs = sortByProfit();

        return createSchedule(sortedJobs);

    }

    //    public Schedule newApproxSchedule() //Your own creation. Must be <= O(n3)
//    {  }

    public Job[] sortByDeadline()
    {
        //make a clone of 'jobs'
        Job[] sortedJobs = jobs.clone();

        Job temp ; //= new Job();
        for(int i = 1; i < sortedJobs.length; i++) {
            for(int j = 0; j < sortedJobs.length - i; j++) {
                if(sortedJobs[j].deadline > sortedJobs[j+1].deadline) {
                    temp = sortedJobs[j+1];
                    sortedJobs[j+1] = sortedJobs[j];
                    sortedJobs[j] = temp;
                }
            }
        }

        return sortedJobs;
    }

    public Job[] sortByLength()
    {
        //make a clone of 'jobs'
        Job[] sortedJobs = jobs.clone();

        Job temp ; //= new Job();
        for(int i = 1; i < sortedJobs.length; i++) {
            for(int j = 0; j < sortedJobs.length - i; j++) {
                if(sortedJobs[j].length > sortedJobs[j+1].length) {
                    temp = sortedJobs[j+1];
                    sortedJobs[j+1] = sortedJobs[j];
                    sortedJobs[j] = temp;
                }
            }
        }

        return sortedJobs;
    }

    public Job[] sortByProfit()
    {
        //make a clone of 'jobs'
        Job[] sortedJobs = jobs.clone();

        Job temp ; //= new Job();
        for(int i = 1; i < sortedJobs.length; i++) {
            for(int j = 0; j < sortedJobs.length - i; j++) {
                if(sortedJobs[j].profit < sortedJobs[j+1].profit) {
                    temp = sortedJobs[j+1];
                    sortedJobs[j+1] = sortedJobs[j];
                    sortedJobs[j] = temp;
                }
            }
        }

        return sortedJobs;
    }

    //this method is called by the scheduling algorithms after sorting 'jobs'
    protected Schedule createSchedule(Job[] sortedJobs){
        if(sortedJobs == null){
            return null;
        }

        Schedule theSchedule = new Schedule();

        theSchedule.add(sortedJobs[0]);
        sortedJobs[0].start = 0;
        sortedJobs[0].finish = sortedJobs[0].getLength();
        Job temp = sortedJobs[0];
        theSchedule.profit += sortedJobs[0].profit;

        for(int i = 1; i < nJobs; i++)
        {
            if(!((sortedJobs[i].deadline - sortedJobs[i].length) < temp.finish))
            {
                System.out.println("adding " + sortedJobs[i] + " to schedule...");
                sortedJobs[i].start = temp.finish;
                sortedJobs[i].finish = sortedJobs[i].start + sortedJobs[i].length;
                temp = sortedJobs[i];
                theSchedule.add(sortedJobs[i]);
                theSchedule.profit += sortedJobs[i].profit;
            }
        }
        for(int i = 0; i < nJobs; i++)
        {
            if(sortedJobs[i].start == -1)
            {
                System.out.println("adding " + jobs[i] + " to schedule (no profit)...");
                sortedJobs[i].start = temp.finish;
                sortedJobs[i].finish = sortedJobs[i].start + sortedJobs[i].length;
                temp = sortedJobs[i];
                theSchedule.add(sortedJobs[i]);
            }
        }

        return theSchedule;
    }

}//end of JobScheduler class

//---------------------------Include Job and Schedule classes in JobScheduler. java-----------------------------
class Job
{
    int jobNumber;
    int length;
    int deadline;
    int profit;
    int start;
    int finish;

    //constructor
    public Job( int jn , int len, int d, int p)
    {
        jobNumber = jn; length = len; deadline = d;
        profit = p;  start = -1;  finish = -1;
    }
    public Job()
    {
        jobNumber = -1;
        length = -1;
        deadline = -1;
        profit = -1;
        start = -1;
        finish = -1;
    }

    //this method outputs the Job as a String specifying its attributes
    public String toString()
    {
        return "#" + jobNumber + ":(" + length + ","
                + deadline + "," + profit +
                "," + start + "," + finish + ")";
    }

    public int getDeadline()
    {
        return deadline;
    }

    public int getLength()
    {
        return length;
    }

    public int getJobNumber()
    {
        return jobNumber;
    }

    public void setStart(int theStart)
    {
        this.start = theStart;
    }

    public void setFinish(int theFinish)
    {
        this.finish = theFinish;
    }
    public void zeroProfit()
    {
        profit = 0;
    }



}//end of Job class



// ----------------------------------------------------
class Schedule {
    ArrayList<Job> schedule;
    int profit;

    public Schedule() {
        profit = 0;
        schedule = new ArrayList<Job>();
    }

    public void add(Job job) {
        schedule.add(job);  //add job to schedule
    }

    //add multiple Jobs to Schedule
    public void addMultiple(Job[] newJobs){
        for(int i=0; i< newJobs.length; i++){
            schedule.add(newJobs[i]);
        }
    }


    public int getProfit() {
        return profit;
    }

    public String toString() {
        String s = "Schedule Profit = " + profit;
        for (int k = 0; k < schedule.size(); k++) {
            s = s + "\n" + schedule.get(k);

        }

        return s;
    }
}