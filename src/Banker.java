import java.util.ArrayList;

public class Banker {

    ArrayList<Integer> available;
    int[][] max;
    int[][] allocation;
    int[][] need;


    public Banker(ArrayList<Integer> available, int[][] max, int[][] allocation, int[][] need) {
        this.available = available;
        this.max = max;
        this.allocation = allocation;
        this.need = need;
    }

    void initializeResources(){

        // allocate the arrays.
        // and get the values from the user.
    }

    void testSafeState(){

        // test if there will be DeadLock or Not
    }

    public static void main(String[] args) {

        // test your program.
    }
}
