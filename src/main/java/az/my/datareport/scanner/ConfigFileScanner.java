package az.my.datareport.scanner;

import az.my.datareport.ast.DataAST;

public interface ConfigFileScanner {
    DataAST read(String filaPath);
}
