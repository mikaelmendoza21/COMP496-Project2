/**
 COMP496 - 11am - CSUN Spring 2017
 Project 2 - JobScheduler
 Team Members:
 Mikael A. Mendoza
 Jonathan Villegas
 */

package jobScheduler;

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
            jobs[i] =  new Job(i+1, joblength[i], deadline[i], profit[i]);
        }
    }

    public void printJobs()  //prints the array jobs
    {
        for(int i = 0; i < nJobs; i++){
            System.out.print("\n" + jobs[i].toString());
        }
    }

//    //Brute force. Try all n! orderings. Return the schedule with the most profit
//    public Schedule bruteForceSolution()
//    {   }
//
//
//    public Schedule makeScheduleEDF()
//    //earliest deadline first schedule. Schedule items contributing 0 to total profit last
//    {  }
//
//    public Schedule makeScheduleSJF()
//    //shortest job first schedule. Schedule items contributing 0 to total profit last
//    {  }
//
//    public Schedule makeScheduleHPF()
//    //highest profit first schedule. Schedule items contributing 0 to total profit last
//    {  }
//
//
//    public Schedule newApproxSchedule() //Your own creation. Must be <= O(n3)
//    {  }

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

    //this method outputs the Job as a String specifying its attributes
    public String toString()
    {
        return "#" + jobNumber + ":(" + length + ","
                + deadline + "," + profit +
                "," + start + "," + finish + ")";
    }

}//end of Job class



// ----------------------------------------------------
class Schedule
{
    ArrayList<Job> schedule;
    int profit;

    public Schedule()
    {
        profit = 0;
        schedule = new ArrayList<Job>();
    }

    public void add(Job job)
    {
        schedule.add(job);  //add job to schedule
    }


    public int getProfit()
    {
        return profit;
    }

    public String toString()
    {
        String s = "Schedule Profit = " + profit ;
        for(int k = 0 ; k < schedule.size(); k++)
        {
            s = s + "\n"  + schedule.get(k);

        }

        return s;
    }
}// end of Schedule class

