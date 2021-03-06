package tensorMing_Fundation;

public class Initializer {

    public static int[] intArange(int shape) {
        int[] result = new int[shape];
        for (int i = 0 ; i<shape ; i++) {
            result[i] = i;
        }
        return result;
    }

    public static int[] intArange(int start, int end) {
        int[] result = new int[end-start];
        for (int i = 0 ; i<end-start ; i++) {
            result[i] = i+start;
        }
        return result;
    }

    public static float[] arange(int shape) {
        float[] result = new float[shape];
        for (int i = 0 ; i<shape ; i++) {
            result[i] = i;
        }
        return result;
    }

    public static float[] arange(int shape, float start, float end) {
        if (shape<2) {
            throw new IllegalArgumentException("The minimum shape is 2.");
        }
        float[] result = new float[shape];
        float step = (end-start)/(shape-1);
        float current = start;
        for (int i = 0 ; i<shape ; i++) {
            result[i] = current;
            current+=step;
        }
        return result;
    }

    public static int[][][] meshgrid(int[] x) {
        return meshgrid(x, x);
    }

    public static int[][][] meshgrid(int[] x, int[] y) {
        int[][][] result = new int[2][x.length][y.length];
        for (int i = 0 ; i<x.length ; i++) {
            for (int j = 0 ; j<y.length ; j++) {
                result[0][i][j] = x[i];
            }
        }
        for (int i = 0 ; i<x.length ; i++) {
            for (int j = 0 ; j<y.length ; j++) {
                result[1][i][j] = y[j];
            }
        }
        return result;
    }

    public static float[][][] meshgrid(float[] x) {
        return meshgrid(x, x);
    }

    public static float[][][] meshgrid(float[] x, float[] y) {
        float[][][] result = new float[2][x.length][y.length];
        for (int i = 0 ; i<x.length ; i++) {
            for (int j = 0 ; j<y.length ; j++) {
                result[0][i][j] = x[i];
            }
        }
        for (int i = 0 ; i<x.length ; i++) {
            for (int j = 0 ; j<y.length ; j++) {
                result[1][i][j] = y[j];
            }
        }
        return result;
    }

    public static float[] zeros1d(int shape) {
        return new float[shape];
    }

    public static float[] zeros1d(int[] shape) {
        return new float[shape[0]];
    }

    public static float[][] zeros2d(int[] shape) {
        return new float[shape[0]][shape[1]];
    }

    public static float[][][] zeros3d(int[] shape) {
        return new float[shape[0]][shape[1]][shape[2]];
    }

    public static float[][][][] zeros4d(int[] shape) {
        return new float[shape[0]][shape[1]][shape[2]][shape[3]];
    }

    public static float[] random1d(int shape) {
        float[] result = new float[shape];
        for (int i = 0 ; i<shape ; i++) {
            result[i] = (float)Math.random();
        }
        return result;
    }

    public static float[] random1d(int[] shape) {
        if (shape.length != 1) {
            throw new NumberFormatException("The dimension of the shape array does not match random1d.");
        }
        float[] result = new float[shape[0]];
        for (int i = 0 ; i<shape[0] ; i++) {
            result[i] = (float)Math.random();
        }
        return result;
    }

    public static float[][] random2d(int[] shape) {
        if (shape.length != 2) {
            throw new NumberFormatException("The dimension of the shape array does not match random2d.");
        }
        float[][] result = new float[shape[0]][shape[1]];
        for (int i = 0 ; i<shape[0] ; i++) {
            result[i] = random1d(shape[1]);
        }
        return result;
    }

    public static float[][][] random3d(int[] shape) {
        if (shape.length != 3) {
            throw new NumberFormatException("The dimension of the shape array does not match random3d.");
        }
        float[][][] result = new float[shape[0]][shape[1]][shape[2]];
        for (int i = 0 ; i<shape[0] ; i++) {
            result[i] = random2d(new int[]{shape[1],shape[2]});
        }
        return result;
    }

    public static float[][][][] random4d(int[] shape) {
        if (shape.length != 4) {
            throw new NumberFormatException("The dimension of the shape array does not match random4d.");
        }
        float[][][][] result = new float[shape[0]][shape[1]][shape[2]][shape[3]];
        for (int i = 0 ; i<shape[0] ; i++) {
            result[i] = random3d(new int[]{shape[1],shape[2],shape[3]});
        }
        return result;
    }

    private static float[] xavier1d(int shape, float objStd) {
        if (shape == 1) {
            return random1d(shape);
        }
        if (shape<1) throw new IllegalArgumentException("Illegal shape.");
        float[] result = random1d(shape);
        float mean = NdArrayUtils.mean(result);
        float std = NdArrayUtils.std(result);
        for (int i = 0 ; i<result.length ; i++) {
            result[i] = objStd*(result[i]-mean)/std;
        }
        return result;
    }

    private static float[] xavier1d(int[] shape, float objStd) {
        if (shape.length != 1) {
            throw new NumberFormatException("The dimension of the shape array does not match xavier1d.");
        }
        if (shape[0] == 1) {
            return random1d(shape);
        }
        if (shape[0]<1) throw new IllegalArgumentException("Illegal shape.");
        float[] result = random1d(shape);
        float mean = NdArrayUtils.mean(result);
        float std = NdArrayUtils.std(result);
        for (int i = 0 ; i<result.length ; i++) {
            result[i] = objStd*(result[i]-mean)/std;
        }
        return result;
    }

    private static float[][] xavier2d(int shape[], float objStd) {
        if (shape.length != 2) {
            throw new NumberFormatException("The dimension of the shape array does not match xavier2d.");
        }
        float[][] result = new float[shape[0]][shape[1]];
        for (int i = 0 ; i<result.length ; i++) {
            result[i] = xavier1d(shape[1], objStd);
        }
        return result;
    }

    private static float[][][] xavier3d(int shape[], float objStd) {
        if (shape.length != 3) {
            throw new NumberFormatException("The dimension of the shape array does not match xavier3d.");
        }
        float[][][] result = new float[shape[0]][shape[1]][shape[2]];
        for (int i = 0 ; i<result.length ; i++) {
            result[i] = xavier2d(new int[]{shape[1],shape[2]}, objStd);
        }
        return result;
    }

    private static float[][][][] xavier4d(int shape[], float objStd) {
        if (shape.length != 4) {
            throw new NumberFormatException("The dimension of the shape array does not match xavier4d.");
        }
        float[][][][] result = new float[shape[0]][shape[1]][shape[2]][shape[3]];
        for (int i = 0 ; i<result.length ; i++) {
            result[i] = xavier3d(new int[]{shape[1],shape[2],shape[3]}, objStd);
        }
        return result;
    }

    public static float[] xavier1d(int shape) {
        if (shape == 1) {
            return random1d(shape);
        }
        if (shape<1) throw new IllegalArgumentException("Illegal shape.");
        float[] result = random1d(shape);
        float mean = NdArrayUtils.mean(result);
        float std = NdArrayUtils.std(result);
        for (int i = 0 ; i<result.length ; i++) {
            result[i] = (result[i]-mean)/std;
        }
        return result;
    }

    public static float[] xavier1d(int[] shape) {
        if (shape.length != 1) {
            throw new NumberFormatException("The dimension of the shape array does not match xavier1d.");
        }
        if (shape[0] == 1) {
            return random1d(shape);
        }
        if (shape[0]<1) throw new IllegalArgumentException("Illegal shape.");
        float[] result = random1d(shape);
        float mean = NdArrayUtils.mean(result);
        float std = NdArrayUtils.std(result);
        for (int i = 0 ; i<result.length ; i++) {
            result[i] = (result[i]-mean)/std;
        }
        return result;
    }

    public static float[][] xavier2d(int[] shape) {
        if (shape.length != 2) {
            throw new NumberFormatException("The dimension of the shape array does not match xavier2d.");
        }
        float objStd = (float)1.0/shape[0];
        float[][] result = new float[shape[0]][shape[1]];
        for (int i = 0 ; i<result.length ; i++) {
            result[i] = xavier1d(shape[1], objStd);
        }
        return result;
    }

    public static float[][][] xavier3d(int[] shape) {
        if (shape.length != 3) {
            throw new NumberFormatException("The dimension of the shape array does not match xavier3d.");
        }
        float objStd = (float)1.0/(shape[0]*shape[1]);
        float[][][] result = new float[shape[0]][shape[1]][shape[2]];
        for (int i = 0 ; i<result.length ; i++) {
            result[i] = xavier2d(new int[]{shape[1],shape[2]}, objStd);
        }
        return result;
    }

    public static float[][][][] xavier4d(int[] shape) {
        if (shape.length != 4) {
            throw new NumberFormatException("The dimension of the shape array does not match xavier4d.");
        }
        float objStd = (float)1.0/(shape[0]*shape[1]*shape[2]);
        float[][][][] result = new float[shape[0]][shape[1]][shape[2]][shape[3]];
        for (int i = 0 ; i<result.length ; i++) {
            result[i] = xavier3d(new int[]{shape[1],shape[2],shape[3]}, objStd);
        }
        return result;
    }
}
