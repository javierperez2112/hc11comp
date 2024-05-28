import java.util.HashSet;

public class OpcodeData {
    private int opcode;
    private  HashSet<Character> dirModes;
    
    public OpcodeData(int code)
    {
        this.opcode = code;
        this.dirModes = new HashSet<>();
    }

    public void addMode(char mode)
    {
        if(!this.dirModes.contains(mode))
        {
            this.dirModes.add(mode);
        }
    }

    public int opcode()
    {
        return this.opcode;
    }

    public HashSet<Character> modes()
    {
        return this.dirModes;
    }
}