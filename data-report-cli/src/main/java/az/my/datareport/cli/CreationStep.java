package az.my.datareport.cli;

public interface CreationStep<ENTITY> {
    ENTITY start();
}
