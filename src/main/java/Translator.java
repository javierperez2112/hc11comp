import java.io.File;
import java.io.FileWriter;
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
    private String processedText;
    private StringBuilder s19file;
    private int memorySize;

    public Translator(int memory) {
        opcodeTable = new HashMap<>();
        menmonicList = new HashSet<>();
        this.s19file = new StringBuilder();
        this.memorySize = memory;
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

    // Process directives and store in text.
    public void preprocessFile(String path) throws Exception {
        Path filePath = Paths.get(path);
        HashMap<String, String> equTable = new HashMap<>();
        String text = Files.readString(filePath).toLowerCase();
        // Replace words defined with EQU.
        int lineNum = 0;
        for (String line : text.lines().toList()) {
            String[] parts = line.split("[ ]+");
            if (parts.length >= 3 && parts[1].equals("equ")) {
                if (this.menmonicList.contains(parts[0]) || parts[0].equals("org")) {
                    throw new Exception("Used EQU with a reserved word in line " + lineNum);
                } else if (equTable.containsKey(parts[0])) {
                    throw new Exception("Repeated EQU statement in line " + lineNum);
                } else {
                    equTable.putIfAbsent(parts[0], parts[2]);
                }
            }
            lineNum++;
        }

        for (String key : equTable.keySet()) {
            text = text.replace(key, equTable.get(key));
        }
        this.processedText = text;
    }

    public void makeS19() throws Exception {
        if (this.processedText.isEmpty()) {
            throw new Exception("No preprocessed file.");
        }
        this.s19file = new StringBuilder();
        this.s19file.append("S00D48433131434F4D502D49544241 + checksum"); // Checksum logic pending.
    }

    public void printMOlist() {
        for (MnemonicData data : this.opcodeTable.keySet()) {
            System.out.println(data + " : " + String.format("%x", this.opcodeTable.get(data)));
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

    public void makeS19File(String name) throws Exception {
        if (!name.contains(".s19")) {
            name = name + ".s19";
        }
        File newFile = new File(name);
        if(!newFile.createNewFile())
        {
            throw new Exception("File creation failed for " + name);
        }
        FileWriter writer = new FileWriter(newFile);
        writer.append(this.s19file.toString());
        writer.close();
    }
}
