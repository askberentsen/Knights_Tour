package rules;

import matrix.IMatrix;

public class KnightsTourRules {
    public static final int[]   cx = {1,2, 2, 1,-1,-2,-2,-1},
                                cy = {2,1,-1,-2,-2,-1, 1, 2};
    public static final int L = cx.length;
    public static int degree(IMatrix m, int x, int y){
        int count = 0;
        for (int i = 0; i < L; ++i)
            if( isValid(m, x+cx[i],y+cy[i]) )
                ++count;

        return count;
    }
    public static boolean isValid(IMatrix m, int x, int y){
        return m.contains( x, y ) && m.get(x,y) == 0;
    }
    public static boolean isSolved( IMatrix m ){
        int sx =0, sy = 0, tx = 0, ty = 0;
        int lv = m.get(0,0 ), hv = lv;
        int x, y;
        boolean startFound = false, endFound = false;
        while( !startFound ) {
            for (int i = 0; i < L; ++i) {
                x = sx + cx[i];
                y = sy + cy[i];
                startFound = true;
                if (m.contains(x, y) && (m.get(x, y) == lv - 1)){
                    sx = x;
                    sy = y;
                    lv--;
                    startFound = false;
                    break;
                }
            }
        }
        while( !endFound ) {
            for (int i = 0; i < L; ++i) {
                x = tx + cx[i];
                y = ty + cy[i];
                endFound = true;
                if (m.contains(x, y) && (m.get(x, y) == hv + 1)){
                    tx = x;
                    ty = y;
                    hv++;
                    endFound = false;
                    break;
                }
            }
        }

        return hv == m.getSize() && lv == 1;
    }
}
