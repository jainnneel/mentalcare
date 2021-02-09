package com.exceptionHandle;

public class ExceptionOccured  extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ExceptionOccured() {
        super("Something Went Wrong");
    }
}
