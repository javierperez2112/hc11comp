# hc11comp
Compiler for RP2040-based M68HC11 (kinda) emulator.
### hc11comp must be:
* Portable: run on all OS's without much work.
* Compatible: must output files that run on the [Wookie Emulator](http://vigir.ee.missouri.edu/~gdesouza/ece2210/downloads.htm).
* Modifiable: users should be able to make it compile code for other MCU's by changing a single file.
* Easy to use: implement a simple and intuitive CLI, or even better, a GUI (javafx).

# TO DO LIST
## General
- [ ] Make mnemonic-opcode (M-O) translation table (use CSV or TSV format). Columns: (mnemonic, opcode, dir mode)
 ## Classes
 - [x] Create class to read M-O from files and store as `HashMap` or similar.
 - [ ] Create class to preprocess and assemble code into hex/binary file.
 - [ ] Create user interface (CLI) class.
 - [ ] Create USB interface class to load binary to the Pico. (Hardest part)
 - [ ] Try making a GUI...
