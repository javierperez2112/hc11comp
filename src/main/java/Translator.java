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

    // Read M-O .csv file.
    public boolean readCSV(String path) throws Exception {
        File CSVfile = new File(path);
        Scanner fileScanner = new Scanner(CSVfile);
        this.opcodeTable.clear();
        try {
            while (fileScanner.hasNextLine()) {
                String[] line = fileScanner.nextLine().toLowerCase().split(",");
                if (line.length != 3)
                    continue; // Ignore non-fitting lines.
                if (line[0].charAt(0) == '#')
                    continue; // Ignore annotations.
                String mnemonic = line[0];
                this.menmonicList.add(mnemonic);
                Integer opcode = Integer.parseInt(line[1], 16); // Must be written in hex!
                Character dirMode = line[2].toLowerCase().toCharArray()[0];
                MnemonicData newMnemonic = new MnemonicData(mnemonic, dirMode);
                this.opcodeTable.put(newMnemonic, opcode);
            }
            fileScanner.close();
            return true;
        } catch (Exception e) {
            System.err.println("CSV error: " + e.getMessage());
            fileScanner.close();
            return false;
        }
    }

    // Process directives and store in asmMap.
    public boolean preprocessFile(String path) throws Exception {
        Path filePath = Paths.get(path);
        HashMap<String, String> equTable = new HashMap<>();
        for (int i = 0; i < this.memorySize; i++)
            this.asmMap[i] = null; // Scan into string.
        String text = Files.readString(filePath).toLowerCase();
        // Replace words defined with EQU.
        for (String line : text.lines().toList()) {
            String[] parts = line.split("[ ]+");
            if (parts.length >= 3 && parts[1].equals("equ")) {
                if (this.menmonicList.contains(parts[0])) {
                    System.err.println("Careful! Don't use EQU with reserved words!");
                    return false;
                } else {
                    equTable.putIfAbsent(parts[0], parts[2]);
                }
            }
        }
        for (String key : equTable.keySet()) {
            text = text.replace(key, equTable.get(key));
        }
        // Fill asmMap.
        int index = 0;
        for (String line : text.lines().toList()) {
            if (line.isEmpty())
                continue;
            String[] parts = line.split("[ ]+");
            if (parts[0].equals("org")) {
                index = getArgument(parts[1]);
                continue;
            }
            if (this.menmonicList.contains(parts[0])) {
                this.asmMap[index] = line;
                index++;
            }
        }
        return true;
    }

    // Store ASM->hex translation as string.
    public String translateAsm() {
        return new String();
    }

    public void printMOlist() {
        for (MnemonicData data : this.opcodeTable.keySet()) {
            System.out.println(data + " : " + String.format("%x", this.opcodeTable.get(data)));
        }
    }

    public void printAsm() {
        for (int i = 0; i < this.memorySize; i++) {
            if (this.asmMap[i] != null)
                System.out.println(String.format("%x", i) + ": " + this.asmMap[i]);
        }
    }

    private int getArgument(String arg) {
        int index = 0;
        int retValue;
        if (arg.charAt(0) == '#') {
            index++;
        }
        if (arg.charAt(index) == '$') {
            index++;
            retValue = Integer.parseInt(arg.substring(index), 16);
        } else {
            retValue = Integer.parseInt(arg.substring(index), 10);
        }
        return retValue;
    }

}
