import java.io.File;
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

    public void preprocessFile(String path) throws Exception {
        File asmFile = new File(path);
        Scanner fileScanner = new Scanner(asmFile);
        int index = 0;
        while (fileScanner.hasNextLine()) {
            String wholeLine = fileScanner.nextLine().toLowerCase().replace("  ", "").replace("\t", "");
            String[] line = wholeLine.split(" "); // Don't use tabs!!
            if (this.menmonicList.contains(line[0])) // Store valid asm line.
            {
                this.asmMap[index] = wholeLine;
                index++;
            } else {
                // TO DO: process compiler directives (hardcode).
            }
        }
        fileScanner.close();
    }

    public void printWithoutDirectives()
    {
        for(int i = 0; i < this.memorySize; i++)
        {
            System.out.println(i + ": " + this.asmMap[i]);
        }
    }

    public void printMnemList()
    {
        for(String mnem : this.menmonicList)
        {
            System.out.println(mnem);
        }
    }

    public void printMOlist()
    {
        for(MnemonicData data : this.opcodeTable.keySet())
        {
            System.out.println(data + " : " + String.format("%x", this.opcodeTable.get(data)));
        }
    }

}
