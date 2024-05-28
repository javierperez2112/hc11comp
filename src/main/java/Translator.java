import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author Javier Pérez
 *         Class Translator is able to import files
 */
public class Translator {
    private HashMap<String, OpcodeData> opcodeTable;

    public Translator() {
        opcodeTable = new HashMap<>();
    }

    public void readCSV(String path) throws Exception { // Read M-O .csv file.
        File CSVfile = new File(path);
        Scanner fileScanner = new Scanner(CSVfile);
        this.opcodeTable.clear();
        while (fileScanner.hasNextLine()) {
            String[] line = fileScanner.nextLine().split(",");
            String mnemonic = line[0].toLowerCase();
            Integer opcode = Integer.valueOf(line[1], 16); // Must be written in hex!
            OpcodeData newOpcode = new OpcodeData(opcode);
            line[2].chars().forEach(c -> {
                newOpcode.addMode((char) c);
            });
            this.opcodeTable.put(mnemonic, newOpcode);
        }
        fileScanner.close();
    }

    // TO DO: use opcodeTable to translate from ASM to hex and export as file.

}
