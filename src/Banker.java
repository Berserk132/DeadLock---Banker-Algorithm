import java.util.ArrayList;
import java.util.Scanner;

public class Banker {

    int processNumber = 0;
    int resourcesNumber = 0;
    ArrayList<String> finishedProcess = new ArrayList<>();
    ArrayList<Integer> available = new ArrayList<>();
    int[][] max;
    int[][] allocation;
    int[][] need;


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

        for (int row = 0; row < allocation.length; row++) {
            for (int col = 0; col < allocation[row].length; col++) {
                System.out.print(allocation[row][col]);
            }

            System.out.println("");
            // allocate the arrays.

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

    void testSafeState() {

        // test if there will be DeadLock or Not

        ArrayList<Integer> work = available;
        boolean[] finish = new boolean[processNumber];
        int counter = 0;

        // initialize the finish array by false

        for (int i = 0; i < finish.length; i++){

            finish[i] = false;
        }

        for (int loop = 0; loop < processNumber; loop++){

            for (int process = 0; process < processNumber; process++){
                if (finish[process] != true) {

                    boolean flag = false;
                    for (int res = 0; res < resourcesNumber; res++) {

                        if (need[process][res] > work.get(res)) {

                            flag = true;
                            break;
                        }
                    }

                    if (flag == false) {

                        for (int i = 0; i < resourcesNumber; i++) {

                            work.set(i, allocation[process][i] + work.get(i));
                        }

                        finish[process] = true;
                        finishedProcess.add("p" + process);
                    }
                }
            }
        }


        for (int i = 0; i < finish.length; i++){

            if (finish[i] == true) counter++;
        }

        if (counter == processNumber){

            System.out.println("The Sequence is in the safe state");
            System.out.println("The Sequence of Processess : ");
            for (int i = 0; i < finish.length; i++){

                System.out.print(finishedProcess.get(i) + " -> ");
            }
        }
        else{

            System.out.println("The Sequence is not in the safe state");

        }


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



    public static void main(String[] args) {

        Banker b = new Banker();

        b.initializeResources();

        b.testSafeState();


    }
}
