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
    public int totalProfit = 0;

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
    

//    //Brute force. Try all n! orderings. Return the schedule with the most profit
//    public Schedule bruteForceSolution()
//    {   }
//
//
    public Schedule makeScheduleEDF()
    //earliest deadline first schedule. Schedule items contributing 0 to total profit last
    { 
        Schedule theSchedule = new Schedule();
//        System.out.println("before");
//        for (int i = 0; i < jobs.length; i++)
//        {
//            System.out.println("Job no " + jobs[i].jobNumber + " : " + jobs[i].deadline);
//        }
        sortByDeadline();
//        System.out.println("after");
//        for (int i = 0; i < jobs.length; i++)
//        {
//            System.out.println("Job no " + jobs[i].jobNumber + " : " + jobs[i].deadline);
//        }
        theSchedule.add(jobs[0]);
        jobs[0].start = 0;
        jobs[0].finish = jobs[0].getLength();
        Job temp = jobs[0];
        theSchedule.profit += jobs[0].profit;
        for(int i = 1; i < jobs.length; i++)
        {
            if(!((jobs[i].deadline - jobs[i].length) < temp.finish))
            {
                System.out.println("adding " + jobs[i] + " to schedule...");
                jobs[i].start = temp.finish;
                jobs[i].finish = jobs[i].start + jobs[i].length;
                temp = jobs[i];
                theSchedule.add(jobs[i]);
                theSchedule.profit += jobs[i].profit;
            }
        }
        for(int i = 0; i < jobs.length; i++)
        {
            if(jobs[i].start == -1)
            {
                System.out.println("adding " + jobs[i] + " to schedule (no profit)...");
                jobs[i].start = temp.finish;
                jobs[i].finish = jobs[i].start + jobs[i].length;
                temp = jobs[i];
                theSchedule.add(jobs[i]);
            }
        }
        
        return theSchedule;
    }
//
    public Schedule makeScheduleSJF()
    //shortest job first schedule. Schedule items contributing 0 to total profit last
    { 
        Schedule theSchedule = new Schedule();
//        System.out.println("before");
//        for (int i = 0; i < jobs.length; i++)
//        {
//            System.out.println("Job no " + jobs[i].jobNumber + " : " + jobs[i].length);
//        }
        sortByLength();
//        System.out.println("after");
//        for (int i = 0; i < jobs.length; i++)
//        {
//            System.out.println("Job no " + jobs[i].jobNumber + " : " + jobs[i].length);
//        }
        theSchedule.add(jobs[0]);
        jobs[0].start = 0;
        jobs[0].finish = jobs[0].getLength();
        Job temp = jobs[0];
        theSchedule.profit += jobs[0].profit;
        for(int i = 1; i < jobs.length; i++)
        {
            if(!((jobs[i].deadline - jobs[i].length) < temp.finish))
            {
                System.out.println("adding " + jobs[i] + " to schedule...");
                jobs[i].start = temp.finish;
                jobs[i].finish = jobs[i].start + jobs[i].length;
                temp = jobs[i];
                theSchedule.add(jobs[i]);
                theSchedule.profit += jobs[i].profit;
            }
        }
        for(int i = 0; i < jobs.length; i++)
        {
            if(jobs[i].start == -1)
            {
                System.out.println("adding " + jobs[i] + " to schedule (no profit)...");
                jobs[i].start = temp.finish;
                jobs[i].finish = jobs[i].start + jobs[i].length;
                temp = jobs[i];
                theSchedule.add(jobs[i]);
            }
        }

        return theSchedule;
    }

    public Schedule makeScheduleHPF()
    //highest profit first schedule. Schedule items contributing 0 to total profit last
    { 
        Schedule theSchedule = new Schedule();
//        System.out.println("before");
//        for (int i = 0; i < jobs.length; i++)
//        {
//            System.out.println("Job no " + jobs[i].jobNumber + " : " + jobs[i].profit);
//        }
        sortByProfit();
//        System.out.println("after");
//        for (int i = 0; i < jobs.length; i++)
//        {
//            System.out.println("Job no " + jobs[i].jobNumber + " : " + jobs[i].profit);
//        }
        theSchedule.add(jobs[0]);
        jobs[0].start = 0;
        jobs[0].finish = jobs[0].getLength();
        Job temp = jobs[0];
        theSchedule.profit += jobs[0].profit;
        
        for(int i = 1; i < jobs.length; i++)
        {
            if(!((jobs[i].deadline - jobs[i].length) < temp.finish))
            {
                System.out.println("adding " + jobs[i] + " to schedule...");
                jobs[i].start = temp.finish;
                jobs[i].finish = jobs[i].start + jobs[i].length;
                temp = jobs[i];
                theSchedule.add(jobs[i]);
                theSchedule.profit += jobs[i].profit;
            }
        }
        for(int i = 0; i < jobs.length; i++)
        {
            if(jobs[i].start == -1)
            {
                System.out.println("adding " + jobs[i] + " to schedule (no profit)...");
                jobs[i].start = temp.finish;
                jobs[i].finish = jobs[i].start + jobs[i].length;
                temp = jobs[i];
                theSchedule.add(jobs[i]);
            }
        }
        
        
        return theSchedule;
        
    }


//    public Schedule newApproxSchedule() //Your own creation. Must be <= O(n3)
//    {  }
    public void sortByDeadline()
    {
        Job temp = new Job();
        for(int i = 1; i < jobs.length; i++) {
		for(int j = 0; j < jobs.length - i; j++) {
			if(jobs[j].deadline > jobs[j+1].deadline) {
				temp = jobs[j+1];
				jobs[j+1] = jobs[j];
				jobs[j] = temp;
			}
		}
	}
    }
    
    public void sortByLength()
    {
        Job temp = new Job();
        for(int i = 1; i < jobs.length; i++) {
		for(int j = 0; j < jobs.length - i; j++) {
			if(jobs[j].length > jobs[j+1].length) {
				temp = jobs[j+1];
				jobs[j+1] = jobs[j];
				jobs[j] = temp;
			}
		}
	}
    }
    
    public void sortByProfit()
    {
        Job temp = new Job();
        for(int i = 1; i < jobs.length; i++) {
		for(int j = 0; j < jobs.length - i; j++) {
			if(jobs[j].profit < jobs[j+1].profit) {
				temp = jobs[j+1];
				jobs[j+1] = jobs[j];
				jobs[j] = temp;
			}
		}
	}
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
