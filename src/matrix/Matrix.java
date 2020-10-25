package matrix;

import java.util.StringJoiner;

public class Matrix implements IMatrix {
    final int[][] array;
    final int n;
    public Matrix(int n){
        this.array = new int[n][n];
        this.n = n;
    }

    @Override
    public void put(int x, int y, int value) {
        array[x][y] = value;
    }

    @Override
    public int get(int x, int y) {
        return array[x][y];
    }

    @Override
    public int getWidth() {
        return n;
    }

    @Override
    public int getSize() {
        return n*n;
    }

    @Override
    public String toString(){
        String padding = "%" + (int)Math.ceil(Math.log10(getSize())) + "d ";
        StringBuilder stringBuilder = new StringBuilder();
        for( int[] row : array ){
            stringBuilder.append("[ ");
            for( int num : row ){
                stringBuilder.append( String.format(padding,num) );
            }
            stringBuilder.append("]\n");
        }
        return stringBuilder.toString();
    }
}
