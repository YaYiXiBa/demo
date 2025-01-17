package cyber.data.regulation.utils;

import cyber.data.regulation.entity.Result;

public class ResultUtils {

    private static final Integer SUCCESS_CODE = 200;
    private static final Integer ERROR_CODE = 500;

    /**
     * 成功返回结果
     */
    public static <T> Result<T> success() {
        return new Result<>(SUCCESS_CODE, "操作成功", null);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(SUCCESS_CODE, "操作成功", data);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     * @param message 提示信息
     */
    public static <T> Result<T> success(T data, String message) {
        return new Result<>(SUCCESS_CODE, message, data);
    }

    /**
     * 失败返回结果
     * @param message 提示信息
     */
    public static <T> Result<T> error(String message) {
        return new Result<>(ERROR_CODE, message, null);
    }

    /**
     * 失败返回结果
     * @param code 状态码
     * @param message 提示信息
     */
    public static <T> Result<T> error(Integer code, String message) {
        return new Result<>(code, message, null);
    }


    public static <T> Result<T> result(T flag) {
        if (flag == null) {
            return new Result<>(ERROR_CODE, "操作失败", null);
        }
        if(flag instanceof Boolean) {
            if((Boolean) flag) {
                return new Result<>(SUCCESS_CODE, "操作成功", null);
            }else{
                return new Result<>(ERROR_CODE, "操作失败", null);
            }
        }
        return new Result<>(SUCCESS_CODE, "成功", flag);
    }
}
