package cosc202.andie;

public class ConvolveOp {
    public static double[][] convolve(double[][] input, double[][] kernel, boolean offset) {
        int inputHeight = input.length;
        int inputWidth = input[0].length;
        int kernelHeight = kernel.length;
        int kernelWidth = kernel[0].length;
        int outputHeight = inputHeight - kernelHeight + 1;
        int outputWidth = inputWidth - kernelWidth + 1;

        double[][] output = new double[outputHeight][outputWidth];

        for (int i = 0; i < outputHeight; i++) {
            for (int j = 0; j < outputWidth; j++) {
                double sum = 0;
                for (int k = 0; k < kernelHeight; k++) {
                    for (int l = 0; l < kernelWidth; l++) {
                        int rowIndex = Math.min(Math.max(i + k, 0), inputHeight - 1);
                        int colIndex = Math.min(Math.max(j + l, 0), inputWidth - 1);
                        sum += input[rowIndex][colIndex] * kernel[k][l];
                    }
                }
                if (offset) {
                    sum = Math.max(0, Math.min(127, sum));
                }
                output[i][j] = sum;
            }
        }

        return output;
    }
}

