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
//    {
//
//    }
//
//
//    public Schedule newApproxSchedule() //Your own creation. Must be <= O(n3)
//    {  }

    public int[] mergeSort(String attribute)
    /* This mergeSort sorts the jobs[] from lowest to highest according to the attribute specified
       valid attribute values are 'length', 'deadline' and 'profit'
       It then mergesorts the jobs[] array indexes into an int array and returns it
    */
    {
        int size = jobs.length;
        int[] indexes = new int[size];

        //error-handling for invalid 'attribute' values
        if(attribute != "length" && attribute != "deadline" && attribute != "profit"){
            System.out.println("Invalid 'attribute' value!!");
            return null;
        }

        //mergesort indexes
        recursiveMergeSort(0,(size -1), attribute);

        return indexes;
    }

    private int[] recursiveMergeSort(int lowerIndex,int upperIndex, String attribute)
    //recursive function for MergeSort. It returns a fully sorted index array
    {
        int size = upperIndex - lowerIndex;
        int[] indexes;
        int middle = (lowerIndex + upperIndex)/2;// split array in half
        int[] firstHalf;
        int[] secondHalf;

        //progress print (a period each time this function is called
        System.out.println(".");

        //Base case
        if(upperIndex > lowerIndex) {  //else there are more than 1 element in the sub-array

            //mergesort each half Recursively
            firstHalf = recursiveMergeSort(lowerIndex, middle, attribute);
            secondHalf = recursiveMergeSort((middle+1), upperIndex, attribute);

            //merge both halves, choose 'merge' according to 'attribute'
            switch(attribute){
                case "length":
                    indexes = mergeLengths(lowerIndex, middle, upperIndex);
                    break;

                case "deadline":
                    indexes = mergeDeadlines(lowerIndex, middle, upperIndex);
                    break;

                case "profit":
                    indexes = mergeProfits(lowerIndex, middle, upperIndex);
                    break;

                default:
                    indexes = null; //Error-handling
                    break;
            }
        }
        else { // base case (k==i) -Do Nothing
            indexes =  new int[]{lowerIndex};
        }

        return indexes;
    }

    private int[] mergeLengths(int lowerIndex, int middle , int upperIndex)
    //performs the merge function for mergeSort according to 'length' values
    {
        int size = upperIndex - lowerIndex;
        int lowerPointer =lowerIndex;
        int upperPointer = upperIndex +1;
        int i =0;
        int[] indexes = new int[size];

        //iterate through both 'halves'
        while(lowerPointer <= middle && upperPointer <= upperIndex){

            if(jobs[lowerPointer].length <= jobs[upperPointer].length ){
                indexes[i] = lowerPointer;
                lowerPointer++;
            }
            else{
                indexes[i] = upperPointer;
                upperPointer++;
            }
            i++;
        }

        //add remainder of each half (if there are elements left in either)
        if(lowerPointer <= middle){
            for(int j= lowerIndex; lowerPointer<(middle+1); lowerPointer++){
                indexes[j] = lowerPointer;
                j++;
            }

        }
        else if (upperPointer <= upperIndex){
            for(int j= lowerIndex; upperPointer<(upperIndex+1); upperPointer++){
                indexes[j] = upperPointer;
                j++;
            }
        }

        else{} //done merging

        return indexes;
    }

    private int[] mergeDeadlines(int lowerIndex, int middle , int upperIndex)
    //performs the merge function for mergeSort according to 'deadline' values
    {
        int size = upperIndex - lowerIndex;
        int lowerPointer =lowerIndex;
        int upperPointer = upperIndex +1;
        int i =0;
        int[] indexes = new int[size];

        //iterate through both 'halves'
        while(lowerPointer <= middle && upperPointer <= upperIndex){

            if(jobs[lowerPointer].deadline <= jobs[upperPointer].deadline ){
                indexes[i] = lowerPointer;
                lowerPointer++;
            }
            else{
                indexes[i] = upperPointer;
                upperPointer++;
            }
            i++;
        }

        //add remainder of each half (if there are elements left in either)
        if(lowerPointer <= middle){
            for(; lowerPointer<(middle+1); lowerPointer++){
                indexes[i] = lowerPointer;
                i++;
            }

        }
        else if (upperPointer <= upperIndex){
            for(; upperPointer<(upperIndex+1); upperPointer++){
                indexes[i] = upperPointer;
                i++;
            }
        }

        else{} //done merging

        return indexes;
    }

    private int[] mergeProfits(int lowerIndex, int middle , int upperIndex)
    //performs the merge function for mergeSort according to 'length' values
    {
        int size = upperIndex - lowerIndex;
        int lowerPointer =lowerIndex;
        int upperPointer = upperIndex +1;
        int i =0;
        int[] indexes = new int[size];

        //iterate through both 'halves'
        while(lowerPointer <= middle && upperPointer <= upperIndex){

            if(jobs[lowerPointer].profit <= jobs[upperPointer].profit ){
                indexes[i] = lowerPointer;
                lowerPointer++;
            }
            else{
                indexes[i] = upperPointer;
                upperPointer++;
            }
            i++;
        }

        //add remainder of each half (if there are elements left in either)
        if(lowerPointer <= middle){
            for(; lowerPointer<(middle+1); lowerPointer++){
                indexes[i] = lowerPointer;
                i++;
            }

        }
        else if (upperPointer <= upperIndex){
            for(; upperPointer<(upperIndex+1); upperPointer++){
                indexes[i] = upperPointer;
                i++;
            }
        }

        else{} //done merging

        return indexes;
    }

    //This function checks if the array a[] is sorted or not (TESTED)
    public static boolean isSorted( int[] a) {
        boolean sorted = true;
        int index = 0;
        int arraySize = a.length;

        //Check if is sorted, breaking out as soon as 2 elements are not in order
        while(sorted && (index + 1)< arraySize){
            if(a[index] < a[index + 1] || a[index] == a[index+1]){
                //still sorted
            } else {    //(a[index] > a[index+1])
                sorted = false;
            }
            index++;
        }

        boolean answer = sorted;    //'sorted' will be true/false depending on while loop

        return answer;
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

