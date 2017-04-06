/**
 COMP496 - 11am - CSUN Spring 2017
 Project 2 - JobScheduler
 Team Members:
 Mikael A. Mendoza
 Jonathan Villegas
 */

package jobScheduler;

import java.util.ArrayList;
import java.util.Arrays;

public class JobScheduler
{
    private int nJobs;
    private Job[]  jobs;
    private static Schedule best = new Schedule();  //keeps track of Best Schedule for Brutal Force solution

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
        Job[] jobList = jobs.clone();

        Schedule BFSchedule = new Schedule();
        for(int i = 0; i < nJobs; i++)
        {
            jobList[i].finish = -1;
            jobList[i].start = -1;
        }

        ArrayList<Job> arr = new ArrayList<Job>(Arrays.asList(jobList));
        BFSchedule.addMultiple( permute(arr, 0));   //call to permute() to recursively evaluate all n! orderings
        BFSchedule = createSchedule(BFSchedule.getJobs());

        return BFSchedule;
    }


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

    public Schedule newApproxSchedule() //Your own creation. Must be <= O(n3)
    {
        Schedule NASSchedule;

        Job[] jobList = sortByProfitOverLength();

        NASSchedule = createSchedule(jobList);

        return NASSchedule;

    }


    /* HELPER METHODS
       The methods below are called by the Scheduling Algorithm to perform Sorts and creating Schedules
       These were added by the Programmers to simplify and reuse certain methods across the Scheduling algorithms
     */

    //this method recursively  produces all Permutations of jobs[]
    private Job[] permute(java.util.List<Job> arr, int k){

        Job[] temp = new Job[arr.size()];
        Schedule tempSchedule;

        for(int i = k; i < arr.size(); i++){
            java.util.Collections.swap(arr, i, k);
            temp = permute(arr, k+1);
            java.util.Collections.swap(arr, k, i);
        }
        if (k == arr.size() -1) {

            Job[] theJobs = arr.toArray(new Job[arr.size()]);
            for (int i = 0; i < nJobs; i++) {
                theJobs[i].start = -1;
                theJobs[i].finish = -1;
            }
            tempSchedule= createSchedule(theJobs);

            if (tempSchedule.getProfit() > best.getProfit()) {
                best = tempSchedule;
            }
        }
        return best.getJobs();
    }

    private Job[] sortByDeadline()
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

    private Job[] sortByLength()
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

    private Job[] sortByProfit()
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

    //This custom Sorting would make the Greedy Choice of maximizing Profit per Length
    //Short and Profitable jobs will have priority
    private Job[] sortByProfitOverLength(){
        //make a clone of 'jobs'
        Job[] sortedJobs = jobs.clone();

        Job temp ; //= new Job();
        for(int i = 1; i < sortedJobs.length; i++) {
            for(int j = 0; j < sortedJobs.length - i; j++) {
                if((sortedJobs[j].profit / sortedJobs[j].length) < (sortedJobs[j+1].profit / sortedJobs[j+1].length)) {
                    temp = sortedJobs[j+1];
                    sortedJobs[j+1] = sortedJobs[j];
                    sortedJobs[j] = temp;
                }
            }
        }

        return sortedJobs;
    }

    //this method is called by the scheduling algorithms after sorting 'jobs'
    private Schedule createSchedule(Job[] sortedJobs){
        if(sortedJobs == null){
            return null;
        }

        for(int i = 0; i < sortedJobs.length; i++)
        {
            sortedJobs[i].finish = -1;
            sortedJobs[i].start = -1;
        }

        Schedule theSchedule = new Schedule();
        sortedJobs[0].start = 0;
        sortedJobs[0].finish = sortedJobs[0].getLength();
        theSchedule.add(sortedJobs[0]);

        Job temp = sortedJobs[0];
        theSchedule.profit += sortedJobs[0].profit;

        for(int i = 1; i < sortedJobs.length; i++)
        {
            if(!((sortedJobs[i].deadline - sortedJobs[i].length) < temp.finish))
            {
                sortedJobs[i].start = temp.finish;
                sortedJobs[i].finish = sortedJobs[i].start + sortedJobs[i].length;
                temp = sortedJobs[i];
                theSchedule.add(sortedJobs[i]);
                theSchedule.profit += sortedJobs[i].profit;
            }
        }
        for(int i = 1; i < sortedJobs.length; i++)
        {
            if(sortedJobs[i].start  == -1 || sortedJobs[i].finish == -1)
            {
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

    public int getLength()
    {
        return length;
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
    public void addMultiple(Job[] newJobs) {
        for (int i = 0; i < newJobs.length; i++) {
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

    public Job[] getJobs(){
        return schedule.toArray(new Job[schedule.size()]);
    }
}