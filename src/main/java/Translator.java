import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author Javier Pérez
 *         Class Translator is able to import files
 */
public class Translator {
    private HashMap<MnemonicData, Integer> opcodeTable;

    public Translator() {
        opcodeTable = new HashMap<>();
    }

    public void readCSV(String path) throws Exception { // Read M-O .csv file.
        File CSVfile = new File(path);
        Scanner fileScanner = new Scanner(CSVfile);
        this.opcodeTable.clear();
        while (fileScanner.hasNextLine()) {
            String[] line = fileScanner.nextLine().replace(" ", "").split(",");
            if (line.length != 3)
                continue; // Ignore non-fitting lines.
            if (line[0].charAt(0) == '#')
                continue; // Ignore annotations.
            String mnemonic = line[0];
            Integer opcode = Integer.valueOf(line[1], 16); // Must be written in hex!
            Character dirMode = line[2].toLowerCase().toCharArray()[0];
            MnemonicData newMnemonic = new MnemonicData(mnemonic, dirMode);
            this.opcodeTable.put(newMnemonic, opcode);
        }
        fileScanner.close();
    }

    // TO DO: use opcodeTable to translate from ASM to hex and export as file.
    public void translateFile() {

    }

}
