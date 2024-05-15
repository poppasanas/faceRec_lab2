public class Person {
    private ImageOfPerson[] etalons;
    private ImageOfPerson[] tests;
    private String name;

    public Person(String name, ImageOfPerson[] etalons, ImageOfPerson[] tests) {
        this.name = name;
        this.etalons = etalons;
        this.tests = tests;
    }

    public ImageOfPerson[] getEtalons() {
        return etalons;
    }

    public void setEtalons(ImageOfPerson[] etalons) {
        this.etalons = etalons;
    }

    public ImageOfPerson[] getTests() {
        return tests;
    }

    public void setTests(ImageOfPerson[] tests) {
        this.tests = tests;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}