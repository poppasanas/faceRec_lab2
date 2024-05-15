import java.util.List;

public class ResultOfTest {
    private List<VisualResultOfRecognize> results;
    private int countOfTests;
    private double[] resultOfDFT;
    private double[] resultOfDCT;
    private double[] resultOfSeScale;
    private double[] resultOfHistogram;
    private double[] resultOfGradient;
    private double[] resultOfCascade;

    public ResultOfTest(int countOfEtalons, double[] resultOfDFT, double[] resultOfDCT, double[] resultOfSeScale, double[] resultOfHistogram, double[] resultOfGradient, List<VisualResultOfRecognize> results) {
        this.countOfTests = countOfEtalons;
        this.resultOfDFT = resultOfDFT;
        this.resultOfDCT = resultOfDCT;
        this.resultOfSeScale = resultOfSeScale;
        this.resultOfHistogram = resultOfHistogram;
        this.resultOfGradient = resultOfGradient;
        this.results = results;
    }

    public List<VisualResultOfRecognize> getResults() {
        return results;
    }

    public void setResults(List<VisualResultOfRecognize> results) {
        this.results = results;
    }

    public int getCountOfTests() {
        return countOfTests;
    }

    public void setCountOfTests(int countOfTests) {
        this.countOfTests = countOfTests;
    }

    public double[] getResultOfDFT() {
        return resultOfDFT;
    }

    public void setResultOfDFT(double[] resultOfDFT) {
        this.resultOfDFT = resultOfDFT;
    }

    public double[] getResultOfDCT() {
        return resultOfDCT;
    }

    public void setResultOfDCT(double[] resultOfDCT) {
        this.resultOfDCT = resultOfDCT;
    }

    public double[] getResultOfSeScale() {
        return resultOfSeScale;
    }

    public void setResultOfSeScale(double[] resultOfSeScale) {
        this.resultOfSeScale = resultOfSeScale;
    }

    public double[] getResultOfHistogram() {
        return resultOfHistogram;
    }

    public void setResultOfHistogram(double[] resultOfHistogram) {
        this.resultOfHistogram = resultOfHistogram;
    }

    public double[] getResultOfGradient() {
        return resultOfGradient;
    }

    public void setResultOfGradient(double[] resultOfGradient) {
        this.resultOfGradient = resultOfGradient;
    }

    public double[] getResultOfCascade() {
        return resultOfCascade;
    }

    public void setResultOfCascade(double[] resultOfCascade) {
        this.resultOfCascade = resultOfCascade;
    }
}

