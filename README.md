# AZScrape

## What is AZScrape?

AZScrape is used to scrape data from web pages and export them into desired format such as csv, excel, json and so on.
Application can be beneficial for Data Science projects because of easy scraping. Data exchanges between different
clients to speed up the scraping process.

## Tech Stack

App Version: 3.0.0-SNAPSHOT (in development)
Programming Language: Java 17
UI: Desktop (in development), Web (not supported yet)

## Tokenizer

### Creating a Lexer (Tokenizer)

A lexer, also known as a tokenizer, is the first stage in the process of interpreting your custom file syntax. Its job is to read the raw input file and break it down into smaller, manageable pieces called tokens. Each token represents a meaningful unit in your syntax, such as a keyword, identifier, operator, or symbol.

#### Steps to Create a Lexer

1. **Define Token Types**:
    - Start by identifying the different types of tokens in your syntax. Common types include keywords (e.g., `if`, `while`), operators (e.g., `+`, `=`), identifiers (e.g., variable names), literals (e.g., numbers, strings), and special symbols (e.g., `(`, `)`).

2. **Pattern Matching**:
    - For each token type, define patterns or regular expressions that can be used to recognize instances of that token in the input file. For example, a pattern for an identifier might be a sequence of letters and numbers starting with a letter.

3. **Scanning the Input**:
    - Implement a scanner that reads the input file character by character and tries to match the characters to the patterns you’ve defined. When a match is found, a token of the corresponding type is created.

4. **Handling Whitespace and Comments**:
    - Decide how to handle whitespace and comments. Typically, these are ignored by the lexer, but you may choose to include them as tokens if they have significance in your syntax.

5. **Error Handling**:
    - Implement error handling for cases where the input doesn’t match any of your patterns. You may want to report an error and the position in the file where the unrecognized sequence occurs.

6. **Output Tokens**:
    - The lexer outputs a stream of tokens that represent the meaningful components of the input file. Each token typically contains the type, value (if applicable), and position in the file.

### Designing a Parser

A parser takes the stream of tokens produced by the lexer and analyzes them according to the rules of your syntax. The parser’s goal is to determine if the sequence of tokens is syntactically correct and to build a structure (often called a parse tree or abstract syntax tree) that represents the logical structure of the input file.

#### Steps to Design a Parser

1. **Define Grammar Rules**:
    - Start by defining the grammar of your language. Grammar rules describe how tokens can be combined to form valid statements or expressions. For example, a rule might state that an `if` statement consists of the keyword `if`, followed by an expression in parentheses, followed by a block of code.

2. **Choose a Parsing Technique**:
    - Select a parsing technique that suits your grammar. Common techniques include:
        - **Recursive Descent Parsing**: Easy to implement and understand, especially for simple, non-ambiguous grammars.
        - **LL and LR Parsing**: More complex but can handle more ambiguous and intricate grammars. Tools like ANTLR can generate these parsers.
        - **Operator Precedence Parsing**: Good for arithmetic expressions where operators have different precedence levels.

3. **Implement the Parser**:
    - The parser reads tokens and applies the grammar rules to recognize valid sequences. For each rule, the parser will look for the expected sequence of tokens. If the tokens match the rule, the parser may build a node in a syntax tree.

4. **Build a Parse Tree (or Abstract Syntax Tree)**:
    - As the parser recognizes valid sequences, it constructs a parse tree or abstract syntax tree (AST). The tree’s nodes represent the constructs in your language (e.g., expressions, statements, blocks of code), and the tree structure represents their relationships.

5. **Error Handling**:
    - Implement error handling to manage situations where the token sequence doesn’t match any rule. The parser should report syntax errors and indicate where in the input file the error occurred. You might also implement recovery strategies to continue parsing after an error.

6. **Validation and Interpretation**:
    - Once the parser successfully builds the syntax tree, further steps might include semantic validation (checking for logical errors, like using an undeclared variable) and interpreting or compiling the tree into executable code or another output.

### Summary

- **Lexer**: Breaks down raw input into tokens, handling patterns, and errors.
- **Parser**: Analyzes the token sequence using grammar rules, builds a syntax tree, and handles syntax errors.

Together, the lexer and parser form the core of your interpreter or compiler for the custom file syntax you're developing.


