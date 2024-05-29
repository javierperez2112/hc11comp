import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

/**
 * @author Javier Pérez
 *         Class Translator is able to import files
 */
public class Translator {
    private HashMap<MnemonicData, Integer> opcodeTable;
    private HashSet<String> menmonicList;
    private String[] asmMap;
    private int memorySize;

    public Translator(int memory) {
        opcodeTable = new HashMap<>();
        menmonicList = new HashSet<>();
        this.memorySize = memory;
        asmMap = new String[memory];
    }

    public void readCSV(String path) throws Exception { // Read M-O .csv file.
        File CSVfile = new File(path);
        Scanner fileScanner = new Scanner(CSVfile);
        this.opcodeTable.clear();
        while (fileScanner.hasNextLine()) {
            String[] line = fileScanner.nextLine().toLowerCase().split(",");
            if (line.length != 3)
                continue; // Ignore non-fitting lines.
            if (line[0].charAt(0) == '#')
                continue; // Ignore annotations.
            String mnemonic = line[0];
            this.menmonicList.add(mnemonic);
            Integer opcode = Integer.valueOf(line[1], 16); // Must be written in hex!
            Character dirMode = line[2].toLowerCase().toCharArray()[0];
            MnemonicData newMnemonic = new MnemonicData(mnemonic, dirMode);
            this.opcodeTable.put(newMnemonic, opcode);
        }
        fileScanner.close();
    }

    // Process directives and store in asmMap.
    public void preprocessFile(String path) throws Exception {
        Path filePath = Paths.get(path);
        HashMap<String, String> equTable = new HashMap<>();
        // Scan into string.
        String text = Files.readString(filePath).toLowerCase();
        // Replace words defined with EQU.
        text.lines().forEach(line -> {
            String[] parts = line.split("[ ]+");
            if (parts.length >= 3 && parts[1].equals("equ")) {
                if (!equTable.containsKey(parts[0])) {
                    equTable.put(parts[0], parts[2]);
                }
            }
        });
        for (String key : equTable.keySet()) {
            text = text.replace(key, equTable.get(key));
        }
        // TO DO: a lot!
    }

    public void printMOlist() {
        for (MnemonicData data : this.opcodeTable.keySet()) {
            System.out.println(data + " : " + String.format("%x", this.opcodeTable.get(data)));
        }
    }

}
