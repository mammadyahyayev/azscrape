package az.my.datareport.cli;

class Exit {
    static final int SUCCESS = 0;
    static final int INTERNAL_ERROR = 1;
    static final int USER_ERROR = 2;

    void exit(int status) {
        System.exit(status);
    }
}
