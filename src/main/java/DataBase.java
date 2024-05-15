import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataBase {
    private boolean isConfigured;
    private Classificator classificator;
    private Map<String, Person> persons;
    private Map<String, List<ImageOfPerson>> personsMap;
    private int countOfEtalons;
    private int countOfTests;
    private int countOfImages;

    public DataBase() {
        isConfigured = false;
        persons = new HashMap<>();
    }

    public boolean isNotEmpty() {
        return isConfigured;
    }

    public void loadDataBase(Map<String, List<ImageOfPerson>> persons, int countOfEtalons, int countOfTests) {
        this.countOfEtalons = countOfEtalons;
        this.countOfTests = countOfTests;
        personsMap = persons;
        this.persons = new HashMap<>();

        for (Map.Entry<String, List<ImageOfPerson>> entry : persons.entrySet()) {
            String key = entry.getKey();
            List<ImageOfPerson> value = entry.getValue();
            ImageOfPerson[] etalons = new ImageOfPerson[countOfEtalons];
            ImageOfPerson[] tests = new ImageOfPerson[countOfTests];

            for (int i = 0; i < countOfEtalons; i++) {
                etalons[i] = value.get(i);
            }
            for (int i = countOfEtalons; i < countOfEtalons + countOfTests; i++) {
                tests[i - countOfEtalons] = value.get(i);
            }
            persons.put(key, new Person(key, etalons, tests));
        }
        classificator = new Classificator(persons, countOfEtalons, countOfTests);
        isConfigured = true;
    }

    public List<ResultOfTest> crossValidation(int maxCountOfImages) {
        List<ResultOfTest> resultsOfCrossValidationTests = new ArrayList<>();
        ResultOfTest bestResult = null;

        Dispetcher.loadDataBase(maxCountOfImages);
        personsMap = Dispetcher.getPersons();
        int countOfEtalons = maxCountOfImages - 1;
        int countOfTestImages = 1;
        double max = 0;

        for (Map.Entry<String, List<ImageOfPerson>> entry : personsMap.entrySet()) {
            loadDataBase(personsMap, countOfEtalons, countOfTestImages);
            ResultOfTest resultOfTestValidation = Dispetcher.getClassificator().studyOnTheTests();
            resultsOfCrossValidationTests.add(resultOfTestValidation);
            int len = resultOfTestValidation.getCountOfTests() - 1;
            double[] dft = resultOfTestValidation.getResultOfDFT();
            double[] dct = resultOfTestValidation.getResultOfDCT();
            double[] seScale = resultOfTestValidation.getResultOfSeScale();
            double[] histogram = resultOfTestValidation.getResultOfHistogram();
            double[] resultOfGradient = resultOfTestValidation.getResultOfGradient();
            double sum = dft[len] + dct[len] + seScale[len] + histogram[len] + resultOfGradient[len];

            if (sum > max) {
                max = sum;
                bestResult = resultOfTestValidation;
            }
            countOfEtalons--;
            countOfTestImages++;
            if (countOfEtalons <= 0 || countOfTestImages > maxCountOfImages - 1) {
                break;
            }
        }
        List<Object> resultList = new ArrayList<>();
        resultList.add(resultsOfCrossValidationTests);
        resultList.add(bestResult);
        return resultsOfCrossValidationTests;
    }
}
