# Captl
 A fun interpreter inspired by Brainfuck made in java
 
## Function
 Captl works by using letters and symbols as tokens. It also uses Capitalisation for more tokens.
 It uses a simple byte memory system for operations like Brainfuck.
 
## Tokens
The token syntax is as Simple as in Brainfuck, but some tokens have arguments
 - `H` - Highers the current byte by 1
 - `h` - Lowers the current byte by 1
 - `p` - Prints the current byte as ascii character
 - `P` - Prints all bytes in memory as ascii character
 - `N` - Switches to the next memory cell
 - `n` - Switches to the previous memory cell
 - `i` - Takes the next char from input and puts into the current cell
 - `I` - Takes the next line from input and puts into the memory
 - `e` - Empties the byte buffer
 - `E` - Empties the current memory cell
 - `V[variable identifier]` - V saves the current memory cell into the given variable 
 
#### Variables
Variables are identified by special characters such as `!"§$%&/)=?*+~'#-_.:,;<>|^°` which can only consist of only 1
of predefined characters

## Usage
Captl can be accessed via the command line like this.
- `java -jar Captl.jar -f file.captl` - Executes from the given file
- `java -jar Captl.jar -i` - Opens the live interpreting console
- `java -jar Captl.jar <Captl Code>` Directly executes the given code from command line
