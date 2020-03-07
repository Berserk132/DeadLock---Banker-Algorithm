import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Banker {

    int processNumber = 0;
    int resourcesNumber = 0;

    ArrayList<Integer> available = new ArrayList<>();
    int[][] max;
    int[][] allocation;
    int[][] need;
    String processRequest = new String() ;
    int processNum = 0;
    Console con = System.console();


    public Banker() {
    }

    void initializeResources() {

        // and get the values from the user.

        Scanner sc = new Scanner(System.in);

        // processess Number.
        System.out.print("Enter The Number of Processes : ");
        processNumber = sc.nextInt();

        // Resources Number

        System.out.print("Enter The Number of Resources : ");
        resourcesNumber = sc.nextInt();

        // Allocate The Allocation Matrix

        this.allocation = allocateAllocationMatrix(this.allocation, this.processNumber, this.resourcesNumber, sc);

        // Allocate The Max Matrix
        this.max = allocateMaxMatrix(this.max, this.processNumber, this.resourcesNumber, sc);

        // Allocate The Need Matrix
        this.need = allocateNeedMatrix(this.need, this.processNumber, this.resourcesNumber, sc);

        for (int i = 0; i < resourcesNumber; i++){

            available.add(sc.nextInt());
        }

        for (int row = 0; row < this.resourcesNumber; row++) {

            int counter  = 0;
            for (int col = 0; col < this.processNumber; col++) {

                counter += this.allocation[col][row];
            }


            this.available.set(row, this.available.get(row) - counter);

        }

    }



    private int[][] allocateNeedMatrix(int[][] need, int processNumber, int resourcesNumber, Scanner sc) {

        need = new int[processNumber][resourcesNumber];

        for (int row = 0; row < need.length; row++) {

            for (int col = 0; col < need[row].length; col++) {

                need[row][col] = this.max[row][col] - this.allocation[row][col];
            }

            System.out.println();
        }

        return need;
    }

    void testSafeState(int[][] alloc, ArrayList<Integer> w, int[][] n) {

        // test if there will be DeadLock or Not
        ArrayList<String> finishedProcess = new ArrayList<>();
        ArrayList<Integer> work = new ArrayList<>();
        int[][] allocation = Arrays.stream(this.allocation).map(int[]::clone).toArray(int[][]::new);
        int[][] need = Arrays.stream(this.need).map(int[]::clone).toArray(int[][]::new);

        for(int i = 0; i < available.size(); i++){

            work.add(w.get(i));
        }

        boolean[] finish = new boolean[processNumber];
        int counter = 0;


        // initialize the finish array by false

        for (int i = 0; i < finish.length; i++){

            finish[i] = false;
        }



        print2DArray(need);
        print2DArray(allocation);
        print1DArray(work);

        // this is the main for loop
        for (int loop = 0; loop < processNumber; loop++) {

            // for each increament in loop var we pass through all the processess and check every single one.
            System.out.println("Know We are in the loop number " + loop);
            for (int process = 0; process < processNumber; process++) {
                if (finish[process] != true) {

                    // we check the need of process against the available

                    boolean flag = false;
                    for (int res = 0; res < resourcesNumber; res++) {
                        System.out.println("Know Comparing " +
                                need[process][res] + " and " + work.get(res)
                        + "for process P(" + process + ")");
                        if (need[process][res] > work.get(res)) {
                            System.out.println("------------------can't satisfy the process request--------------");
                            flag = true;
                            break;
                        }
                    }

                    // if the need is less than the available we open the path to the process to work

                    if (flag == false) {

                        System.out.println("We Can Satisfy the request of the process P(" + process + ")");
                        for (int i = 0; i < resourcesNumber; i++) {

                            work.set(i, allocation[process][i] + work.get(i));
                        }

                        finish[process] = true;
                        finishedProcess.add("p" + process);
                    }
                }
                else{
                    continue;
                }
            }
        }


        // check if all processes worked

        for (int i = 0; i < finish.length; i++) {

            if (finish[i] == true) counter++;
        }


        // if all processes worked then print the sequence and the system in the safe state
        if (counter == processNumber) {

            System.out.println("The Sequence is in the safe state");
            System.out.println("The Sequence of Processes : ");
            for (int i = 0; i < finish.length; i++) {

                System.out.print(finishedProcess.get(i) + " -> ");
            }


        }
        // the system in the DeadLock State
        else {

            System.out.println("The Sequence is not in the safe state");

        }

        System.out.println("");


    }

    void addRequest(int[][] allocationArray, int[][] needArray) throws IOException {


        int[][] alloc = allocationArray;
        int[][] n = needArray;

        print2DArray(n);
        print2DArray(alloc);

        ArrayList<Integer> w = new ArrayList<>();

        for(int i = 0; i < available.size(); i++){

            w.add(available.get(i));
        }

        //System.out.println("");

            String[] resources = processRequest.split(" ");

            for (int i = 0; i < resources.length; i++) {

                System.out.println(resources[i]);
            }

            processNum = Integer.parseInt(resources[1]);

            System.out.println("processNum = " + processNum);

            for (int i = 2; i < this.resourcesNumber + 2; i++) {

                if (Integer.parseInt(resources[i]) > w.get(i - 2)){

                    System.out.println("Cannot Accept This Request");
                    return;
                }

                alloc[processNum][i - 2] += Integer.parseInt(resources[i]);
                w.set(i - 2, w.get(i - 2) - Integer.parseInt(resources[i]));
                n[processNum][i - 2] -= Integer.parseInt(resources[i]);
            }
            System.out.println("Request Accepted");
            testSafeState(alloc, w, n);


    }

    int[][] allocateAllocationMatrix(int[][] allocation, int process, int resource, Scanner sc){

        allocation = new int[process][resource];

        for (int row = 0; row < allocation.length; row++) {

            System.out.println("Enter the Allocated Resources for the process (P" + row + ") : ");
            for (int col = 0; col < allocation[row].length; col++) {

                allocation[row][col] = sc.nextInt();
            }

            System.out.println();
        }

        return allocation;
    }

    int[][] allocateMaxMatrix(int[][] max, int process, int resource, Scanner sc){

        max = new int[process][resource];

        for (int row = 0; row < max.length; row++) {

            System.out.println("Enter the Max Resources for the process (P" + row + ") : ");
            for (int col = 0; col < max[row].length; col++) {

                max[row][col] = sc.nextInt();
            }

            System.out.println();
        }

        return max;
    }

    void print2DArray(int[][] arr) {

        for (int row = 0; row < this.processNumber; row++) {

            System.out.print("P(" + row + ")  ");
            for (int col = 0; col < this.resourcesNumber; col++) {

                System.out.print(arr[row][col] + "  ");
            }
            System.out.println(" ");
        }

        System.out.println(" ");
    }

    void print1DArray(ArrayList<Integer> arr){

        for (int i = 0; i < arr.size(); i++) {

            System.out.print(arr.get(i) + "  ");
        }
    }





    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);

        Banker b = new Banker();

        b.initializeResources();

        b.testSafeState(b.allocation, b.available, b.need);

        while (true){

            System.out.println("Do you want to Assign Resources to any Process ????, Please Enter Process Name or Enter (Quit) : ");
            b.processRequest =  sc.nextLine();//"RQ 0 0 2 0";

            if (b.processRequest.equals("Quit")) break;

            b.addRequest(b.allocation.clone(), b.need.clone());
        }




    }


}
