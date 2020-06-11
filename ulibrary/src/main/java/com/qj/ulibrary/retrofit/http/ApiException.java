package com.qj.ulibrary.retrofit.http;

/**
 * 此类稍作修改，code码无用，直接显示请求反回的message
 */

public class ApiException extends RuntimeException {

    //    public static final int USER_NOT_EXIST = 100;
//    public static final int WRONG_PASSWORD = 101;
    public static final int UNKNOWN_ERROR = 500;
    private static String message;
    private static int code;



    public ApiException(int resultCode) {
        this(getApiExceptionMessage(resultCode));
    }

    public ApiException(String detailMessage) {
        super(detailMessage);
        this.message = detailMessage;
    }
    public ApiException(int resultCode,String detailMessage) {
        super(detailMessage);
        this.message = detailMessage;
        this.code=resultCode;
    }

    public static int getCode() {
        return code;
    }

    public static void setCode(int code) {
        ApiException.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    /**
     * (此项目未用到)
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     *
     * @param code
     * @return
     */
    private static String getApiExceptionMessage(int code) {
        switch (code) {
            case UNKNOWN_ERROR:
                message = "未知错误";
                break;
            default:
                message = "未知错误";
        }
        return message;
    }
}
