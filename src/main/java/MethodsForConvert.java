import jdk.internal.net.http.common.Pair;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MethodsForConvert {
    // Параметры по умолчанию:
    public static int l = 2;
    public static int pDft = 25;
    public static int pDct = 12;
    public static int histSize = 128;
    public static int W = 1;


    // Sc-scale
    public static Pair<double[], BufferedImage> ScScale(Mat src) {
        Imgproc.cvtColor(src, src, Imgproc.COLOR_BGR2GRAY);
        Mat face = src;
        int M = face.cols();
        int N = face.rows();
        int n = N / l;
        int m = M / l;
        double[] resultArray = new double[m * n];
        int counter = 0;
        Mat result = new Mat(n, m, CvType.CV_8U);
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                double resultOfMiddleSquare = MiddleOfSquare(face, (i - 1) * l, i * l, (j - 1) * l, j * l, l);
                resultArray[counter++] = resultOfMiddleSquare;
            }
        }
        BufferedImage scaledImage = Utils.matToBufferedImage(face.resize(1.0 / l));
        return new Pair<>(resultArray, scaledImage);
    }

    private static double MiddleOfSquare(Mat src, int rowStart, int rowEnd, int colStart, int colEnd, int l) {
        double sum = 0;
        for (int i = rowStart; i < rowEnd; i++) {
            for (int j = colStart; j < colEnd; j++) {
                sum += src.get(i, j)[0];
            }
        }
        return sum / (l * l);
    }

    public static Mat DFTForRecognizeResults(Mat src) {
        int optimalRows = Core.getOptimalDFTSize(src.rows());
        int optimalCols = Core.getOptimalDFTSize(src.cols());
        Scalar s0 = new Scalar(0, 0, 0);
        // Подгоняем размеры изображения под DFT
        Mat padded = new Mat();
        Core.copyMakeBorder(src, padded, 0, optimalRows - src.rows(), 0, optimalCols - src.cols(), Core.BORDER_CONSTANT, s0);
        // Производим манипуляции для отделения комплексной и вещественной части изображения
        Mat plane0 = new Mat();
        padded.convertTo(plane0, CvType.CV_32F);
        plane0 = plane0.submat(new Rect(0, 0, plane0.cols() & -2, plane0.rows() & -2));
        int cx = plane0.cols() / 2;
        int cy = plane0.rows() / 2;
        Mat q0 = plane0.submat(new Rect(0, 0, cx, cy));
        Mat q1 = plane0.submat(new Rect(cx, 0, cx, cy));
        Mat q2 = plane0.submat(new Rect(0, cy, cx, cy));
        Mat q3 = plane0.submat(new Rect(cx, cy, cx, cy));
        Mat tmp = new Mat();
        q0.copyTo(tmp);
        q3.copyTo(q0);
        tmp.copyTo(q3);
        q1.copyTo(tmp);
        q2.copyTo(q1);
        tmp.copyTo(q2);
        return plane0;
    }
    public static Mat DFTForVisualResult(Mat src) {
        int optimalRows = Core.getOptimalDFTSize(src.rows());
        int optimalCols = Core.getOptimalDFTSize(src.cols());
        Scalar s0 = new Scalar(0, 0, 0);

        // Подгоняем размеры изображения под DFT
        Mat padded = new Mat();
        Core.copyMakeBorder(src, padded, 0, optimalRows - src.rows(), 0, optimalCols - src.cols(), Core.BORDER_CONSTANT, s0);

        // Производим манипуляции для отделения комплексной и вещественной части изображения
        Mat plane0 = new Mat();
        padded.convertTo(plane0, CvType.CV_32F);
        List<Mat> planes = new ArrayList<>();
        Mat complexI = new Mat();
        Mat plane1 = Mat.zeros(padded.size(), CvType.CV_32F);
        planes.add(plane0);
        planes.add(plane1);
        Core.merge(planes, complexI);

        // Производим быстрое преобразование Фурье
        Core.dft(complexI, complexI);

        // Разделяем канал с вещественной и мнимой частями
        List<Mat> complexPlanes = new ArrayList<>();
        Core.split(complexI, complexPlanes);
        Mat magnitude = new Mat();
        Core.cartToPolar(complexPlanes.get(0), complexPlanes.get(1), magnitude, new Mat());

        // Логарифмируем значение, чтобы получить чёрно-белое изображение
        Core.add(magnitude, Mat.ones(magnitude.size(), CvType.CV_32F), magnitude);
        Core.log(magnitude, magnitude);

        // Делаем циклический сдвиг
        magnitude = magnitude.submat(new Rect(0, 0, magnitude.cols() & -2, magnitude.rows() & -2));
        int cx = magnitude.cols() / 2;
        int cy = magnitude.rows() / 2;
        Mat q0 = magnitude.submat(new Rect(0, 0, cx, cy));
        Mat q1 = magnitude.submat(new Rect(cx, 0, cx, cy));
        Mat q2 = magnitude.submat(new Rect(0, cy, cx, cy));
        Mat q3 = magnitude.submat(new Rect(cx, cy, cx, cy));
        Mat tmp = new Mat();
        q0.copyTo(tmp);
        q3.copyTo(q0);
        tmp.copyTo(q3);
        q1.copyTo(tmp);
        q2.copyTo(q1);
        tmp.copyTo(q2);

        // Нормализуем изображение в оттенки серого
        Core.normalize(magnitude, magnitude, 0, 255, Core.NORM_MINMAX, CvType.CV_8U);

        return magnitude;
    }

    private static double[][] getAreaOfImage(Mat img, int p) {
        double[][] result = new double[p][p];
        int startPosX = img.rows() / 2 - p;
        int startPosY = img.cols() / 2 - p;
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpg", img, matOfByte);
        byte[] byteArray = matOfByte.toArray();
        Mat image = Imgcodecs.imdecode(new MatOfByte(byteArray), Imgcodecs.IMREAD_GRAYSCALE);
        for (int x = startPosX; x < p + startPosX; x++) {
            for (int y = startPosY; y < p + startPosY; y++) {
                result[x - startPosX][y - startPosY] = image.get(x, y)[0];
            }
        }
        return result;
    }

    private static double[][] getLeftSquareOfImage(Mat img, int p) {
        double[][] result = new double[p][p];
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpg", img, matOfByte);
        byte[] byteArray = matOfByte.toArray();
        Mat image = Imgcodecs.imdecode(new MatOfByte(byteArray), Imgcodecs.IMREAD_GRAYSCALE);
        for (int x = 0; x < p; x++) {
            for (int y = 0; y < p; y++) {
                result[x][y] = image.get(x, y)[0];
            }
        }
        return result;
    }

    private static double[] getSquareVector(double[][] C) {
        int p = 0;
        double[] result = new double[C.length * C[0].length];
        for (double[] doubles : C) {
            for (int y = 0; y < C[0].length; y++) {
                result[p++] = doubles[y];
            }
        }
        return result;
    }

    private static double[] getDiagonalVector(double[][] C) {
        int tek = -1;
        int p = C.length;
        double[] line = new double[p * (p + 1) / 2];
        for (int y = 0; y < p; y++) {
            for (int k = y; k > -1; k--) {
                tek++;
                line[tek] = C[y - k][k];
            }
        }
        return line;
    }

    public static double[][] DCT(Mat src) {
        Imgproc.cvtColor(src, src, Imgproc.COLOR_BGR2GRAY);
        Mat resultOfConvert = new Mat();
        src.convertTo(resultOfConvert, CvType.CV_32F);
        Core.dct(resultOfConvert, resultOfConvert);
        Mat result = new Mat();
        Core.normalize(resultOfConvert, result, 0, 255, Core.NORM_MINMAX, CvType.CV_8U);
        BufferedImage watch = OpenCVTools.matToBufferedImage(result);
        return new double[][] { getDiagonalVector(getLeftSquareOfImage(resultOfConvert, pDct)), BufferedImageToDoubleArray(watch) };
    }

    private static double[] BufferedImageToDoubleArray(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        double[] result = new double[width * height];
        int index = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                result[index++] = image.getRGB(x, y) & 0xFF; // Извлечение значения яркости пикселя
            }
        }
        return result;
    }

    public static double[] Histogram(Mat src) {
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);

        MatOfFloat range = new MatOfFloat(0f, 256f);
        MatOfInt histSizeMat = new MatOfInt(histSize);
        boolean accumulate = false;
        Mat hist = new Mat();
        Imgproc.calcHist(Arrays.asList(gray), new MatOfInt(0), new Mat(), hist, histSizeMat, range, accumulate);

        float[] histData = new float[histSize];
        hist.get(0, 0, histData);

        int N = src.rows();
        int M = src.cols();
        double[] histogram = new double[histSize];
        for (int i = 0; i < histSize; i++) {
            histogram[i] = histData[i] / (N * M);
        }
        return histogram;
    }

    public static double[] Gradient(Mat src) {
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);

        Mat img = new Mat(gray.rows(), gray.cols(), CvType.CV_8UC1);
        gray.copyTo(img);

        int startRow = img.height() / 2;
        int endRow = img.height() - startRow;
        int toUpRow = startRow / W;
        int endUpRow = endRow / W;
        int counter = Math.min(toUpRow, endUpRow);
        double[] resultOfGradient = new double[counter - 1];
        double summ = 0;

        for (int i = 0; i < counter - 1; i++) {
            double[][] sumDown = getBorderOfGradient(img, i * W + startRow, (i + 1) * W + startRow - 1);
            double[][] sumUp = getBorderOfGradient(img, startRow - (i + 1) * W, startRow - i * W - 1);
            int rowLen = sumDown.length;
            int columnLen = sumDown[0].length;

            for (int x = 0; x < rowLen; x++) {
                for (int y = 0; y < columnLen; y++) {
                    double value = Math.abs(sumDown[x][y] - sumUp[rowLen - 1 - x][y]);
                    resultOfGradient[i] += value;
                    summ += value;
                }
            }
        }
        return resultOfGradient;
    }

    private static double[][] getBorderOfGradient(Mat img, int startRowPos, int endRowPos) {
        int numberOfCols = img.cols();
        int rowLen = endRowPos - startRowPos + 1;
        double[][] result = new double[rowLen][numberOfCols];

        for (int i = startRowPos; i <= endRowPos; i++) {
            for (int j = 0; j < numberOfCols; j++) {
                result[i - startRowPos][j] = img.get(i, j)[0];
            }
        }
        return result;
    }

}

