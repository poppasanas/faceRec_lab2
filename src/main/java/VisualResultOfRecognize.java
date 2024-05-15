import java.awt.image.BufferedImage;
import java.util.List;

public class VisualResultOfRecognize {
    private BufferedImage testDFT;
    private BufferedImage testDCT;
    private BufferedImage testSeScale;
    private BufferedImage testImage;
    private double[] testHistogram;
    private double[] testsGradients;
    private List<BufferedImage> images;
    private List<BufferedImage> DFT;
    private List<BufferedImage> DCT;
    private List<BufferedImage> SeScale;
    private List<double[]> Histogram;
    private List<double[]> Gradient;

    public VisualResultOfRecognize(List<BufferedImage> dFT, List<BufferedImage> dCT, List<BufferedImage> seScale, List<double[]> gradient, List<double[]> histogram, BufferedImage testSeScale, BufferedImage testDCT, BufferedImage testDFT, double[] testHistogram, double[] testsGradients, List<BufferedImage> images, BufferedImage testImage) {
        DFT = dFT;
        DCT = dCT;
        SeScale = seScale;
        Gradient = gradient;
        Histogram = histogram;
        this.testSeScale = testSeScale;
        this.testDCT = testDCT;
        this.testDFT = testDFT;
        this.testHistogram = testHistogram;
        this.testsGradients = testsGradients;
        this.images = images;
        this.testImage = testImage;
    }

    public BufferedImage getTestDFT() {
        return testDFT;
    }

    public void setTestDFT(BufferedImage testDFT) {
        this.testDFT = testDFT;
    }

    public BufferedImage getTestDCT() {
        return testDCT;
    }

    public void setTestDCT(BufferedImage testDCT) {
        this.testDCT = testDCT;
    }

    public BufferedImage getTestSeScale() {
        return testSeScale;
    }

    public void setTestSeScale(BufferedImage testSeScale) {
        this.testSeScale = testSeScale;
    }

    public BufferedImage getTestImage() {
        return testImage;
    }

    public void setTestImage(BufferedImage testImage) {
        this.testImage = testImage;
    }

    public double[] getTestHistogram() {
        return testHistogram;
    }

    public void setTestHistogram(double[] testHistogram) {
        this.testHistogram = testHistogram;
    }

    public double[] getTestsGradients() {
        return testsGradients;
    }

    public void setTestsGradients(double[] testsGradients) {
        this.testsGradients = testsGradients;
    }

    public List<BufferedImage> getImages() {
        return images;
    }

    public void setImages(List<BufferedImage> images) {
        this.images = images;
    }

    public List<BufferedImage> getDFT() {
        return DFT;
    }

    public void setDFT(List<BufferedImage> DFT) {
        this.DFT = DFT;
    }

    public List<BufferedImage> getDCT() {
        return DCT;
    }

    public void setDCT(List<BufferedImage> DCT) {
        this.DCT = DCT;
    }

    public List<BufferedImage> getSeScale() {
        return SeScale;
    }

    public void setSeScale(List<BufferedImage> seScale) {
        SeScale = seScale;
    }

    public List<double[]> getHistogram() {
        return Histogram;
    }

    public void setHistogram(List<double[]> histogram) {
        Histogram = histogram;
    }

    public List<double[]> getGradient() {
        return Gradient;
    }

    public void setGradient(List<double[]> gradient) {
        Gradient = gradient;
    }
}
