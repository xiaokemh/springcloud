package com.geo.core.result;

/**
 * 返回数据格式工具类
 *
 * @author yyq
 */
public final class ResultUtils {

    /**
     * 返回成功，附带返回数据
     *
     * @param data
     * @return
     */
    public static Result success(Object data) {
        int code = ResultCodeEnum.SUCCESS.getCode();
        String msg = ResultCodeEnum.SUCCESS.getMessage();
        return new Result(code, data, msg);
    }

    /**
     * 返回成功，不带数据
     *
     * @return
     */
    public static Result success() {
        return success(null);
    }

    /**
     * 自定义返回结果
     *
     * @param code
     * @param data
     * @param msg
     * @return
     */
    public static Result success(int code, Object data, String msg) {
        return new Result(code, data, msg);
    }

    /**
     * 返回失败，带数据
     *
     * @param data
     * @return
     */
    public static Result error(String data) {
        int code = ResultCodeEnum.FAILED.getCode();
        String msg = ResultCodeEnum.FAILED.getMessage();
        return new Result(code, data, msg);
    }

    public static Result error() {
        return error("");
    }

    public static Result error(ResultCodeEnum resultCodeEnum) {
        return error(resultCodeEnum.getCode(), null, resultCodeEnum.getMessage());
    }

    /**
     * 自定义返回结果
     *
     * @param code
     * @param data
     * @param msg
     * @return
     */
    public static Result error(int code, Object data, String msg) {
        return new Result(code, data, msg);
    }
}
