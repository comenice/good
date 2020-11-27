package com.ku.bobo.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ku.bobo.util.OptionalUtils;

import java.util.List;

/**
 * @Date 2020/4/29 16:51
 * @Created by xb
 * 通用返回类型
 */
public class CommonResult<T> {

    private String msg ;
    private Integer code ;
    private T data;


    /**
     * 适用于mybatis-plus page空否结果
     */
    public static class Pages {

        public static <T> CommonResult<List<T>> list( Page<T> t ){
            OptionalUtils.ofCollect( t.getRecords() );
            return CommonResult.success( t.getRecords() );
//            List<T> list = t.getRecords();
        }

        public static <T> CommonResult<List<T>> list(List<T> t){
            OptionalUtils.ofCollect( t );
            return CommonResult.success( t );
        }
    }

    /**
     * 适用于布尔值真假返回结果
     */
   public static class Booleans {

        public static <T> CommonResult<T> delete( Boolean b ){
            return CommonResult.is( b , "删除成功" , "删除失败"  );
        }
        public static <T> CommonResult<T> mdelete( Boolean b ){
            return CommonResult.is( b , "批量删除成功" , "批量删除失败"  );
        }

        public static <T> CommonResult<T> save( Boolean b ){
            return CommonResult.is( b , "新增成功" , "新增失败"  );
        }

        public static <T> CommonResult<T> msave( Boolean b ){
            return CommonResult.is( b , "批量新增成功" , "批量新增失败"  );
        }

        public static  <T> CommonResult<T> update( Boolean b ){
            return CommonResult.is( b , "修改成功" , "修改失败" );
        }

        public static  <T> CommonResult<T> mupdate( Boolean b ){
            return CommonResult.is( b , "批量修改成功" , "批量修改失败" );
        }

    }

    /**
     * 提取了controller常见的执行结果条件判断
     * @param b 一些返回true或则是false的执行结果
     * @param successMsg 执行成功要返回的msg 如: 新增用户成功
     * @param failedMsg  执行失败药返回的msg 如: 新增用户失败
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> is( Boolean b , String successMsg , String failedMsg ){
        if ( b ){
            return new CommonResult<>( successMsg , CodeInfoEnum.SUCCESS.getCode() , null );
        }else{
            return new CommonResult<>( failedMsg , CodeInfoEnum.FAILED.getCode() , null );
        }
    }

    /**
     * 返回成功结果
     */
    public static <T> CommonResult<T> success( T data ){
        return new CommonResult<>( CodeInfoEnum.SUCCESS.getMsg() ,
                CodeInfoEnum.SUCCESS.getCode() , data );
    }  /**
     * 返回成功结果
     */
    public static <T> CommonResult<T> success( String msg ){
        return new CommonResult<>( CodeInfoEnum.SUCCESS.getMsg() ,
                CodeInfoEnum.SUCCESS.getCode() , null );
    }
    /**
     * 返回成功结果
     */
    public static <T> CommonResult<T> success( String msg , T data ){
        return new CommonResult<>( msg , CodeInfoEnum.SUCCESS.getCode() , data );
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     */
    public static <T> CommonResult<T> failed(ICodeInfo errorCode) {
        return new CommonResult<T>( errorCode.getMsg(), errorCode.getCode(), null);
    }


    /**
     * @param msg 自定义错误消息
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> failed(String msg) {
        return new CommonResult<T>( msg , CodeInfoEnum.FAILED.getCode(), null);
    }

    /**
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> failed( ){
        return failed( CodeInfoEnum.FAILED );
    }


    public CommonResult(String msg, Integer code, T data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
