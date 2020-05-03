package com.lh.blog.exception;


import com.lh.blog.util.CodeMsg;

/**
 * 全局异常
 * @author linhao
 */
public class GlobalException  extends RuntimeException {
    private CodeMsg codeMsg;

    /**
     * 使用构造器接收CodeMsg
     *
     * @param codeMsg
     */
    public GlobalException(CodeMsg codeMsg) {
        this.codeMsg = codeMsg;
    }

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }
}
