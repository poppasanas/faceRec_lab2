import jdk.internal.net.http.common.Pair;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Classificator
{
    private List<ImageOfPerson> _etalons;
    private List<ImageOfPerson> _tests;
    private int _countOfEtalonForPerson;
    private int _countOfTestsForPerson;
    public Classificator(Dictionary<string,Person> persons,int countOfEtalonForPerson, int countOfTestsForPerson)
    {
        _countOfEtalonForPerson = countOfEtalonForPerson;
        _countOfTestsForPerson = countOfTestsForPerson;
        _etalons = persons.Select(person => person.Value.Etalons).SelectMany(etalon => etalon).ToList();
        _tests = persons.Select(person => person.Value.Tests).SelectMany(test => test).ToList();
    }
    public ResultOfTest StudyOnTheTests() //Здесь остановились
    {
        int countOfSeScale = 0;
        int countOfDFT = 0;
        int countOfDCT = 0;
        int countOfHistogram = 0;
        int countOfGradient = 0;
        List<VisualResultOfRecognize> visualResults = new List<VisualResultOfRecognize>();
        int allEtalonsCount = _tests.Count;

        var DCTRecognize = new double[allEtalonsCount];
        var DFTRecognize = new double[allEtalonsCount];
        var SeScaleRecognize = new double[allEtalonsCount];
        var HistogramRecognize = new double[allEtalonsCount];
        var GradientRecognize = new double[allEtalonsCount];
        ProgressBarForm form = new ProgressBarForm(allEtalonsCount);
        form.Show();
        for (int i = 0; i < allEtalonsCount; i++)
        {
            List<Bitmap> visualReslutsOfDFT = new List<Bitmap>();
            List<Bitmap> visualReslutsOfDCT = new List<Bitmap>();
            List<Bitmap> visualResultsOfSeScale = new List<Bitmap>();
            List<Bitmap> images = new List<Bitmap>();
            List<double[]> resultsOfHistogram = new List<double[]>();
            List<double[]> resultsOfGradient = new List<double[]>();
            var resultOfDFT = GetDFTResultRecognition(_tests[i].signVector.DFT, visualReslutsOfDFT, images);
            var resultOfDCT = GetDCTResultRecognition(_tests[i].signVector.DCT, visualReslutsOfDCT);
            var resultOfSeScale = GetSeScaleRecognition(_tests[i].signVector.ScScale, visualResultsOfSeScale);
            var reslutOfHistogram = GetHistogramRecognition(_tests[i].signVector.Histogram, resultsOfHistogram);
            var reslutOfGradient = GetGradientRecognition(_tests[i].signVector.Gradient, resultsOfGradient);
            //Визуальная часть:
            visualResults.Add(new VisualResultOfRecognize(visualReslutsOfDFT, visualReslutsOfDCT, visualResultsOfSeScale, resultsOfGradient, resultsOfHistogram,
                    _tests[i].signVector.ScScaleImage, _tests[i].signVector.DCTImage, _tests[i].signVector.DFTimage, _tests[i].signVector.Histogram, _tests[i].signVector.Gradient, images, _tests[i].Img.ToBitmap()));
            //Результат распознования:
            if (resultOfDFT.Item2.Name == _tests[i].Name) countOfDFT++;
            if (resultOfDCT.Item2.Name == _tests[i].Name) countOfDCT++;
            if (resultOfSeScale.Item2.Name == _tests[i].Name) countOfSeScale++;
            if (reslutOfHistogram.Item2.Name == _tests[i].Name) countOfHistogram++;
            if (reslutOfGradient.Item2.Name == _tests[i].Name) countOfGradient++;
            DFTRecognize[i] = (double)countOfDFT / (i + 1);
            DCTRecognize[i] = (double)countOfDCT / (i + 1);
            SeScaleRecognize[i] = (double)countOfSeScale / (i + 1);
            HistogramRecognize[i] = (double)countOfHistogram / (i + 1);
            GradientRecognize[i] = (double)countOfGradient / (i + 1);
            form.IncrementBar();
        }
        form.Close();
        return new ResultOfTest(allEtalonsCount, DFTRecognize, DCTRecognize, SeScaleRecognize, HistogramRecognize, GradientRecognize, visualResults);
    }
    public ResultOfTest StudyOnTheEtalons() //Здесь остановились
    {
        int countOfSeScale = 0;
        int countOfDFT = 0;
        int countOfDCT = 0;
        int countOfHistogram = 0;
        int countOfGradient = 0;
        List<VisualResultOfRecognize> visualResults = new List<VisualResultOfRecognize>();
        int allEtalonsCount = _etalons.Count;

        var DCTRecognize = new double[allEtalonsCount];
        var DFTRecognize = new double[allEtalonsCount];
        var SeScaleRecognize = new double[allEtalonsCount];
        var HistogramRecognize = new double[allEtalonsCount];
        var GradientRecognize = new double[allEtalonsCount];
        ProgressBarForm form = new ProgressBarForm(allEtalonsCount);
        form.Show();
        for (int i = 0; i < allEtalonsCount; i++)
        {
            List<Bitmap> visualReslutsOfDFT = new List<Bitmap>();
            List<Bitmap> visualReslutsOfDCT = new List<Bitmap>();
            List<Bitmap> visualResultsOfSeScale = new List<Bitmap>();
            List<Bitmap> images = new List<Bitmap>();
            List<double[]> resultsOfHistogram = new List<double[]>();
            List<double[]> resultsOfGradient = new List<double[]>();
            var resultOfDFT = GetDFTResultRecognition(_etalons[i].signVector.DFT, visualReslutsOfDFT,images);
            var resultOfDCT = GetDCTResultRecognition(_etalons[i].signVector.DCT, visualReslutsOfDCT);
            var resultOfSeScale = GetSeScaleRecognition(_etalons[i].signVector.ScScale, visualResultsOfSeScale);
            var reslutOfHistogram = GetHistogramRecognition(_etalons[i].signVector.Histogram, resultsOfHistogram);
            var reslutOfGradient = GetGradientRecognition(_etalons[i].signVector.Gradient, resultsOfGradient);
            //Визуальная часть:
            visualResults.Add(new VisualResultOfRecognize(visualReslutsOfDFT, visualReslutsOfDCT, visualResultsOfSeScale, resultsOfGradient, resultsOfHistogram,
                    _etalons[i].signVector.ScScaleImage, _etalons[i].signVector.DCTImage, _etalons[i].signVector.DFTimage, _etalons[i].signVector.Histogram, _etalons[i].signVector.Gradient, images, _etalons[i].Img.ToBitmap()));
            //Результат распознования:
            if (resultOfDFT.Item2.Name == _etalons[i].Name)
            {
                countOfDFT++;
                DFTRecognize[i] = (double)countOfDFT / (i + 1);
            }
            if (resultOfDCT.Item2.Name == _etalons[i].Name)
            {
                countOfDCT++;
                DCTRecognize[i] = (double)countOfDCT / (i + 1);
            }
            if (resultOfSeScale.Item2.Name == _etalons[i].Name)
            {
                countOfSeScale++;
                SeScaleRecognize[i] = (double)countOfSeScale / (i + 1);
            }
            if (reslutOfHistogram.Item2.Name == _etalons[i].Name)
            {
                countOfHistogram++;
                HistogramRecognize[i] = (double)countOfHistogram / (i + 1);
            }
            if (reslutOfGradient.Item2.Name == _etalons[i].Name)
            {
                countOfGradient++;
                GradientRecognize[i] = (double)countOfGradient / (i + 1);
            }
            form.IncrementBar();
        }
        form.Close();
        return new ResultOfTest(allEtalonsCount, DFTRecognize, DCTRecognize, SeScaleRecognize, HistogramRecognize, GradientRecognize, visualResults);
    }
    public ResultOfTest StudyOfCascade()
    {
        List<VisualResultOfRecognize> visualResults = new List<VisualResultOfRecognize>();
        int allEtalonsCount = _tests.Count;
        var countOfRecognize = 0.0;
        var recognize = new double[allEtalonsCount];
        ProgressBarForm form = new ProgressBarForm(allEtalonsCount);
        form.Show();
        for (int i = 0; i < allEtalonsCount; i++)
        {
            var countOfMethodsRecognize = 0;
            List<Bitmap> visualReslutsOfDFT = new List<Bitmap>();
            List<Bitmap> visualReslutsOfDCT = new List<Bitmap>();
            List<Bitmap> visualResultsOfSeScale = new List<Bitmap>();
            List<Bitmap> images = new List<Bitmap>();
            List<double[]> resultsOfHistogram = new List<double[]>();
            List<double[]> resultsOfGradient = new List<double[]>();
            var resultOfDFT = GetDFTResultRecognition(_tests[i].signVector.DFT, visualReslutsOfDFT, images);
            var resultOfDCT = GetDCTResultRecognition(_tests[i].signVector.DCT, visualReslutsOfDCT);
            var resultOfSeScale = GetSeScaleRecognition(_tests[i].signVector.ScScale, visualResultsOfSeScale);
            var reslutOfHistogram = GetHistogramRecognition(_tests[i].signVector.Histogram, resultsOfHistogram);
            var reslutOfGradient = GetGradientRecognition(_tests[i].signVector.Gradient, resultsOfGradient);
            //Визуальная часть:
            visualResults.Add(new VisualResultOfRecognize(visualReslutsOfDFT, visualReslutsOfDCT, visualResultsOfSeScale, resultsOfGradient, resultsOfHistogram,
                    _tests[i].signVector.ScScaleImage, _tests[i].signVector.DCTImage, _tests[i].signVector.DFTimage, _tests[i].signVector.Histogram, _tests[i].signVector.Gradient, images, _tests[i].Img.ToBitmap()));
            //Результат распознования:
            if (resultOfDFT.Item2.Name == _tests[i].Name) countOfMethodsRecognize++;
            if (resultOfDCT.Item2.Name == _tests[i].Name) countOfMethodsRecognize++;
            if (resultOfSeScale.Item2.Name == _tests[i].Name) countOfMethodsRecognize++;
            if (reslutOfHistogram.Item2.Name == _tests[i].Name) countOfMethodsRecognize++;
            if (reslutOfGradient.Item2.Name == _tests[i].Name) countOfMethodsRecognize++;
            if (countOfMethodsRecognize >= 3) countOfRecognize++;
            recognize[i] = countOfRecognize / (i + 1);
            form.IncrementBar();
        }
        var result =  new ResultOfTest(allEtalonsCount, null, null, null, null, null, visualResults);
        result.ResultOfCascade = recognize;
        form.Close();
        return result;
    }
    public ResultOfTest CrossValidation()
    {
        //Получение всех значений
        List<ImageOfPerson> allImages = new List<ImageOfPerson>();
        allImages.AddRange(_etalons);
        allImages.AddRange(_tests);
        var firstPart = allImages.Count / 2;

        //Осуществляем кросс-валидацию:

        //Первый результат кросс-валидации:
        _etalons = allImages.Take(firstPart).ToList();
        _tests = allImages.Skip(firstPart).ToList();
        var resultOfFirstExperiment = StudyOnTheTests();

        //Второй результат кросс-валидации:
        var t = _tests;
        _tests = _etalons;
        _etalons = t;
        var resultOfSecondExperiment = StudyOnTheTests();

        var middleResultOfSeScale = GetMiddleResultOfCrossValidationMethod(resultOfFirstExperiment.ResultOfSeScale, resultOfSecondExperiment.ResultOfSeScale);
        var middleResultOfDFT = GetMiddleResultOfCrossValidationMethod(resultOfFirstExperiment.ResultOfDFT,resultOfSecondExperiment.ResultOfDFT);
        var middleResultOfDCT = GetMiddleResultOfCrossValidationMethod(resultOfFirstExperiment.ResultOfDCT,resultOfSecondExperiment.ResultOfDCT);
        var middleResultOfHistogram = GetMiddleResultOfCrossValidationMethod(resultOfFirstExperiment.ResultOfHistogram, resultOfSecondExperiment.ResultOfHistogram);
        var middleResultOfGradient = GetMiddleResultOfCrossValidationMethod(resultOfSecondExperiment.ResultOfGradient, resultOfFirstExperiment.ResultOfGradient);

        return new ResultOfTest(firstPart, middleResultOfDFT, middleResultOfDCT, middleResultOfSeScale, middleResultOfHistogram, middleResultOfGradient, resultOfFirstExperiment.Results);
    }
    public ResultOfTest RecognizeFace(string pathToPhoto)
    {
        List<Bitmap> visualReslutsOfDFT = new List<Bitmap>();
        List<Bitmap> visualReslutsOfDCT = new List<Bitmap>();
        List<Bitmap> visualResultsOfSeScale = new List<Bitmap>();
        List<Bitmap> images = new List<Bitmap>();
        List<double[]> resultsOfHistogram = new List<double[]>();
        List<double[]> resultsOfGradient = new List<double[]>();

        var resultOfDFT = MethodsForConvert.DFT(new Mat(pathToPhoto));
        var resultOfDCT = MethodsForConvert.DCT(new Mat(pathToPhoto));
        var resultOfSeScale = MethodsForConvert.ScScale(new Mat(pathToPhoto));
        var resultOfHistogram = MethodsForConvert.Histogram(new Mat(pathToPhoto));
        var resultOfGradient = MethodsForConvert.Gradient(new Mat(pathToPhoto));

        var resultOfDFTRecognition = GetDFTResultRecognition(resultOfDFT.Item1,visualReslutsOfDFT,images );
        var resultOfDCTRecognition = GetDCTResultRecognition(resultOfDCT.Item1,visualReslutsOfDCT);
        var resultOfSeScaleRecognition = GetSeScaleRecognition(resultOfSeScale.Item1,visualResultsOfSeScale);
        var resultOfHistogramRecognition = GetHistogramRecognition(resultOfHistogram,resultsOfHistogram);
        var resultOfGradientRecognition = GetGradientRecognition(resultOfGradient, resultsOfGradient);
        List<(double, ImageOfPerson)> resultsOfRecognition = new List<(double, ImageOfPerson)>() { resultOfDFTRecognition, resultOfDCTRecognition, resultOfSeScaleRecognition, resultOfHistogramRecognition, resultOfGradientRecognition };

        var names = resultsOfRecognition.GroupBy(x => x.Item2.Name).OrderByDescending(g => g.Key).Where(g => g.Count() >= 3).ToLookup(f => f).SelectMany(t => t).SelectMany(t => t).ToList();
        if (names.Count() > 0)
        {
            CvInvoke.Imshow("Результат распознования: ", names[0].Item2.Img);
        }
        var result = new VisualResultOfRecognize(visualReslutsOfDFT, visualReslutsOfDCT, visualResultsOfSeScale, resultsOfGradient, resultsOfHistogram,
                resultOfSeScale.Item2, resultOfDCT.Item2, resultOfDFT.Item2, resultOfHistogram, resultOfGradient, images, new Mat(pathToPhoto).ToBitmap());
        return new ResultOfTest(_etalons.Count, null, null, null, null, null, new List<VisualResultOfRecognize>() { result});
    }
    private double[] GetMiddleResultOfCrossValidationMethod(double[] firstResult, double[] secondResult)
    {
        return firstResult.Select((r, i) => (firstResult[i] + secondResult[i]) / 2).ToArray();
    }

    //Методы для определения дистанций
    private (double, ImageOfPerson) GetDFTResultRecognition(double[] resultOfDFT,List<Bitmap> visualResults,List<Bitmap> images)
{
    List<(double, ImageOfPerson)> result = new List<(double, ImageOfPerson)>();
    List<double> substracts = new List<double>();
    foreach (var etalon in _etalons)
    {
        for (int i = 0; i < etalon.signVector.DFT.Length; i++)
            substracts.Add(Math.Abs(resultOfDFT[i] - etalon.signVector.DFT[i]));
        visualResults.Add(etalon.signVector.DFTimage);
        images.Add(etalon.Img.ToBitmap());
        result.Add((substracts.Sum(), etalon));
        substracts.Clear();
    }
    return result.OrderBy(x => x.Item1).ToList()[0];
}
    private (double, ImageOfPerson) GetDCTResultRecognition(double[] resultOfDCT, List<Bitmap> visualResults)
{
    List<(double, ImageOfPerson)> result = new List<(double, ImageOfPerson)>();
    List<double> substracts = new List<double>();
    foreach (var etalon in _etalons)
    {
        for (int i = 0; i < etalon.signVector.DCT.Length; i++)
            substracts.Add(Math.Abs(resultOfDCT[i] - etalon.signVector.DCT[i]));
        visualResults.Add(etalon.signVector.DCTImage);
        result.Add((substracts.Sum(), etalon));
        substracts.Clear();
    }
    return result.OrderBy(x => x.Item1).ToList()[0];
}
    private (double, ImageOfPerson) GetSeScaleRecognition(double[] resultOfSeScale,List<Bitmap> visualResults)
{
    List<(double, ImageOfPerson)> result = new List<(double, ImageOfPerson)>();
    List<double> substracts = new List<double>();
    foreach (var etalon in _etalons)
    {
        for (int i = 0; i < etalon.signVector.ScScale.Length; i++)
            substracts.Add(Math.Abs(resultOfSeScale[i] - etalon.signVector.ScScale[i]));
        visualResults.Add(etalon.signVector.ScScaleImage);
        result.Add((substracts.Sum(), etalon));
        substracts.Clear();
    }
    return result.OrderBy(x => x.Item1).ToList()[0];
}
    private (double, ImageOfPerson) GetHistogramRecognition(double[] resultOfHistogram, List<double[]> resultsOfHistogram)
{
    List<(double, ImageOfPerson)> result = new List<(double, ImageOfPerson)>();
    List<double> substracts = new List<double>();
    foreach (var etalon in _etalons)
    {
        for (int i = 0; i < etalon.signVector.Histogram.Length; i++)
            substracts.Add(Math.Abs(resultOfHistogram[i] - etalon.signVector.Histogram[i]));
        result.Add((substracts.Sum(), etalon));
        resultsOfHistogram.Add(etalon.signVector.Histogram);
        substracts.Clear();
    }
    return result.OrderBy(x => x.Item1).ToList()[0];
}
    private (double, ImageOfPerson) GetGradientRecognition(double[] resultOfGradient, List<double[]> resultsOfGradient)
{
    List<(double, ImageOfPerson)> result = new List<(double, ImageOfPerson)>();
    List<double> substracts = new List<double>();
    foreach (var etalon in _etalons)
    {
        for (int i = 0; i < etalon.signVector.Gradient.Length; i++)
            substracts.Add(Math.Abs(resultOfGradient[i] - etalon.signVector.Gradient[i]));
        resultsOfGradient.Add(etalon.signVector.Gradient);
        result.Add((substracts.Sum(), etalon));
        substracts.Clear();
    }
    return result.OrderBy(x => x.Item1).ToList()[0];
}

}
}
