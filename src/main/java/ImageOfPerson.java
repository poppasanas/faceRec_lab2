import org.opencv.core.Mat;

public class ImageOfPerson {
    private String name;
    private Mat img;
    private SignVector signVector;

    public ImageOfPerson(String name, Mat img, SignVector vector) {
        this.name = name;
        this.img = img;
        this.signVector = vector;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Mat getImg() {
        return img;
    }

    public void setImg(Mat img) {
        this.img = img;
    }

    public SignVector getSignVector() {
        return signVector;
    }

    public void setSignVector(SignVector signVector) {
        this.signVector = signVector;
    }
}
