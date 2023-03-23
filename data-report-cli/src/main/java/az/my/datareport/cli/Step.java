package az.my.datareport.cli;

public interface Step<ENTITY> {
    ENTITY execute(ENTITY entity);
}
