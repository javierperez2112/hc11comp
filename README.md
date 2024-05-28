# hc11comp
Compiler for RP2040-based M68HC11 (kinda) emulator


# TO DO LIST
## General
- [ ] Make mnemonic-opcode (M-O) translation table (use CSV or TSV format).
- [ ] Make preprocessor (prep) translation table (use CSV or TSV format).
 ## Classes
 - [ ] Create class to read M-O and prep from files and store as `HashMap` or similar.
 - [ ] Create class to preprocess and assemble code into hex/binary file.
 - [ ] Create user interface (CLI) class.
 - [ ] Create USB interface class to load binary to the Pico. (Hardest part)
