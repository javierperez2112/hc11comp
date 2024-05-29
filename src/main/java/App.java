

public class App {
    public static void main(String[] args) {
        Translator tltr = new Translator(10);
        try {
            tltr.readCSV("./68H11.csv");
            tltr.preprocessFile("./testfile");
            tltr.printWithoutDirectives();
        } catch(Exception e)
        {
            System.err.println(e.getMessage());
        }

    }
}
