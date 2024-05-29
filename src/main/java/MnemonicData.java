/**
 * @author Javier Pérez
 *         Class MnemonicData stores a mnemonic and a directioning mode.
 */
public class MnemonicData {
    private String mnemonic;
    private char dirMode;

    public MnemonicData(String mnem, char dir) {
        this.mnemonic = mnem;
        this.dirMode = dir;
    }

    public String mnemonic() {
        return this.mnemonic;
    }

    public char dirMode() {
        return this.dirMode();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MnemonicData)) {
            return false;
        }
        MnemonicData comp = (MnemonicData) o;
        if (comp.mnemonic.equals(this.mnemonic) && comp.dirMode() == this.dirMode) {
            return true;
        }
        return false;
    }
    @Override
    public String toString()
    {
        return this.mnemonic;
    }
}
