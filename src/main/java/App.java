
public class App {
    public static void main(String[] args) {
        Translator tltr = new Translator(200);
        try {
            tltr.readCSV("./68H11.csv");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
}
