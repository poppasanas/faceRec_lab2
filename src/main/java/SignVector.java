import java.awt.image.BufferedImage;

public class SignVector {
    private double[] scScale;
    private BufferedImage scScaleImage;
    private double[] DFT;
    private BufferedImage DFTimage;
    private double[] DCT;
    private BufferedImage DCTImage;
    private double[] histogram;
    private double[] gradient;

    public SignVector(double[] scScale, double[] DFT, double[] DCT, double[] histogram, double[] gradient, BufferedImage scScaleImage, BufferedImage DFTimage, BufferedImage DCTImage) {
        this.scScale = scScale;
        this.DFT = DFT;
        this.DCT = DCT;
        this.histogram = histogram;
        this.gradient = gradient;
        this.scScaleImage = scScaleImage;
        this.DFTimage = DFTimage;
        this.DCTImage = DCTImage;
    }

    public double[] getScScale() {
        return scScale;
    }

    public void setScScale(double[] scScale) {
        this.scScale = scScale;
    }

    public BufferedImage getScScaleImage() {
        return scScaleImage;
    }

    public void setScScaleImage(BufferedImage scScaleImage) {
        this.scScaleImage = scScaleImage;
    }

    public double[] getDFT() {
        return DFT;
    }

    public void setDFT(double[] DFT) {
        this.DFT = DFT;
    }

    public BufferedImage getDFTimage() {
        return DFTimage;
    }

    public void setDFTimage(BufferedImage DFTimage) {
        this.DFTimage = DFTimage;
    }

    public double[] getDCT() {
        return DCT;
    }

    public void setDCT(double[] DCT) {
        this.DCT = DCT;
    }

    public BufferedImage getDCTImage() {
        return DCTImage;
    }

    public void setDCTImage(BufferedImage DCTImage) {
        this.DCTImage = DCTImage;
    }

    public double[] getHistogram() {
        return histogram;
    }

    public void setHistogram(double[] histogram) {
        this.histogram = histogram;
    }

    public double[] getGradient() {
        return gradient;
    }

    public void setGradient(double[] gradient) {
        this.gradient = gradient;
    }
}