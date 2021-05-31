package us.ystar.demo.exceptions;

public class PitchFileStorageException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String msg;

    public PitchFileStorageException(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }
}
