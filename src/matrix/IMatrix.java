package matrix;

import java.util.function.BiFunction;

public interface IMatrix {
    void put(int x, int y, int value);
    int get(int x, int y);
    int getWidth();
    int getSize();
    default boolean contains(int x, int y){
        return (0<=x && x<getWidth() && 0<=y && y<getWidth());
    }
    default void fill(BiFunction<Integer, Integer, Integer> f){
        for( int i = 0; i < getWidth(); i++){
            for( int j = 0; j < getWidth(); j++){
                if( contains(i,j) ){
                    put(i,j, f.apply(i,j));
                }
            }
        }
    }
}
