import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        ApplicationConfiguration.initialize();
        // Запуск формы
        Application.run(new Form1());
    }
}
