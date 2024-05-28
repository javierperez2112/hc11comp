/**
 * @author Javier Pérez
 *         Class OpcodeData represents an opcode and one of its valid directioning modes.
 * @deprecated
 */
public class OpcodeData {
    private int opcode;
    private char dirMode;

    public OpcodeData(int code, char dirMode) {
        this.opcode = code;
        this.dirMode = dirMode;
    }

    public int opcode() {
        return this.opcode;
    }

    public char modes() {
        return this.dirMode;
    }
}