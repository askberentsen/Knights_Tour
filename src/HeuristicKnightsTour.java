import matrix.*;

import java.util.*;
import static rules.KnightsTourRules.*;

public class HeuristicKnightsTour {

    public static void main(String[] args){

        System.out.println("This algorithm is based on the Warnsdorff heuristic");
        System.out.println("It uses a recursive-iterative hybrid approach, with logarithmic recursion depth.");
        System.out.println("Use k between 1 (for a purely recursive approach) and n*n (for a purely iterative approach)");
        System.out.println("I recommend using k = 2.0");
        System.out.println("Higher values result in quicker searches, but loses accuracy");
        System.out.println("Lower values result in higher accuracy, but longer execution time");
        System.out.println("The runtime of this algorithm is dependent on n^2 and k");
        System.out.println("\nThis algorithm has been tested for values up to n=x with k=1.85 and takes about 50 seconds at values that high");
        System.out.println("values close to 2 usually works well");
        Scanner sc = new Scanner(System.in);
        System.out.print("(int) size of board: ");
        int n = sc.nextInt();
        System.out.print("(int) x: ");
        int x = sc.nextInt();
        System.out.print("(int) y: ");
        int y = sc.nextInt();
        System.out.print("(float) logarithmic scale - k: ");
        float log = sc.nextFloat();

        Matrix m = new Matrix(n);
        long startTime = System.nanoTime();
        try {
            boolean solved = solveRecursive(m, x, y, m.getSize(), log);
            long endTime = System.nanoTime();
            long time = (endTime - startTime) / 1000000;
            System.out.println(m);
            if (solved) {

                System.out.printf("Board successfully solved for %d*%d in %d ms\n", n, n, time);
                if( isSolved(m) ) System.out.println("upon closer inspection, the board is indeed solved!");
                else System.out.println("hmm... this doesn't seem right...");
            } else {
                System.out.printf("\nCould not find a solution with this algorithm for %d*%d.", n, n);
                System.out.printf("\nTime expended: %d ms\n", time);
            }
        }
        catch (StackOverflowError e){
            System.out.println("The stack got knackered, try a smaller k.");
        }
    }

    public static boolean solveRecursive(IMatrix m, int x, int y, int count, float log){
        if( m.get(x,y) == 0 ) m.put(x,y,1); //initial level

        //Check all the possible moves from position x,y
        for ( Integer[] move : nextMoves(m,x,y) ){

            //next position (base position)
            int bx = x + cx[move[0]];
            int by = y + cy[move[0]];

            //positions to be used in next level of recursion
            int nx = bx, ny = by;

            //mark position as one more than the previous position
            m.put(bx,by, m.get(x,y)+1);

            //iterative approach for (count-1)/log < i < (count-1)
            int limit = (int)((1- (1/log))*(count-1));
            Stack<Integer[]> trace = solveIterative(m,bx,by,limit);

            //if the trace is larger than 0, an iterative trial succeeded
            if( trace.size() > 0 ){
                //the next position is at the top of the stack
                nx = trace.peek()[0];
                ny = trace.peek()[1];
            }

            //next level of recursion. Cannot guarantee trace.size() is equal to limit.
            int newCount = count - (1+trace.size());
            if( solveRecursive(m, nx, ny, newCount, log) ){
                return true;
            }
            //recursion attempt failed. Backtrack the base position and the trace.
            else{
                m.put(bx,by,0);
                backTrack(m,trace);
            }
        }
        //No moves left to try. Solution completed if count is 1
        return count==1;
    }
    public static Stack<Integer[]> solveIterative(IMatrix m, int x, int y, int limit){
        //Stack to implement backtracking for an iterative approach.
        Stack<Integer[]> trace = new Stack<>();

        //next position
        int nx = x, ny = y;
        for ( int i = 0; i < limit; ++i ){

            //index of next move
            int midx = nextMove(m, nx, ny);
            if( midx != -1 ){

                //update values
                m.put( nx + cx[midx], ny + cy[midx], m.get(nx,ny)+1);
                nx += cx[midx];
                ny += cy[midx];
                trace.add( new Integer[]{nx,ny});
            }
            //no more moves left.
            else{
                break;
            }
        }
        return trace;
    }

    public static void backTrack(IMatrix m, Stack<Integer[]> trace){
        for ( Integer[] pos : trace ){
            m.put( pos[0], pos[1], 0);
        }
    }

    public static int nextMove( IMatrix m, int x, int y ){
        ArrayList<Integer[]> moves = nextMoves(m,x,y);
        return moves.size() > 0 ? moves.get(0)[0] : -1;
    }
    public static ArrayList<Integer[]> nextMoves(IMatrix m, int x, int y ){
        ArrayList<Integer[]> moves = new ArrayList<>();
        int nx, ny;
        for( int i = 0; i < L; ++i ){
            nx = x+cx[i];
            ny = y+cy[i];
            if( isValid(m, nx, ny) ) moves.add(new Integer[]{i,degree(m,nx,ny)});
        }
        moves.sort(Comparator.comparingInt(o->o[1]));
        return moves;
    }
}
