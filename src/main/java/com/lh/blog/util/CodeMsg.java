package com.lh.blog.util;

import java.io.Serializable;

/**
 * 状态码
 */
public class CodeMsg implements Serializable {
    private int code;
    private String msg;

    /**
     * 通用异常
     */
    public static CodeMsg SUCCESS = new CodeMsg(0, "success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
    public static CodeMsg SERVER_PAGE_ERROR = new CodeMsg(500100, "%s页面异常");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数%s校验异常：%s");
    public static CodeMsg REQUEST_ILLEGAL = new CodeMsg(500102, "请求非法");
    public static CodeMsg VERITF_FAIL = new CodeMsg(500103, "校验失败，请重新输入表达式结果或刷新校验码重新输入");
    public static CodeMsg ACCESS_LIMIT_REACHED = new CodeMsg(500104, "访问太频繁！");
    public static CodeMsg INSERT_FAIL = new CodeMsg(500104, "添加 %s 失败");
    public static CodeMsg DELETE_FAIL = new CodeMsg(500104, "删除 %s 失败");
    public static CodeMsg UPDATE_FAIL = new CodeMsg(500104, "更新 %s 失败");
    public static CodeMsg GET_FAIL = new CodeMsg(500104, "查找 %s 失败");
    public static CodeMsg MODULE_FAIL = new CodeMsg(500105, " %s 模块出现异常");

    /**
     * 文章模块 5002XX
     */
    public static CodeMsg COMMIT_ERROR = new CodeMsg(500201, "评论失败");
    public static CodeMsg START_SUCCESS = new CodeMsg(500202, "收藏成功");
    public static CodeMsg UNSTART_SUCCESS = new CodeMsg(500203, "取消收藏成功");
    public static CodeMsg START_ERROR = new CodeMsg(500204, "收藏失败");
    public static CodeMsg UNSTART_ERROR = new CodeMsg(500205, "取消收藏失败");
    public static CodeMsg LIKE_SUCCESS = new CodeMsg(500206, "点赞成功");
    public static CodeMsg UNLIKE_SUCCESS = new CodeMsg(500207, "取消点赞成功");
    public static CodeMsg LIKE_ERROR = new CodeMsg(500208, "点赞失败");
    public static CodeMsg UNLIKE_ERROR = new CodeMsg(500209, "取消点赞失败");
    public static CodeMsg LIKE_COMMENT_SUCCESS = new CodeMsg(500210, "点赞评论成功");
    public static CodeMsg UNLIKE_COMMENT_SUCCESS = new CodeMsg(500211, "取消点赞评论成功");
    public static CodeMsg LIKE_COMMENT_ERROR = new CodeMsg(500212, "点赞评论失败");
    public static CodeMsg UNLIKE_COMMENT_ERROR = new CodeMsg(500213, "取消点赞评论失败");

    /**
     * 信息模块 5003XX
     */
    public static CodeMsg SEND_SUCCESS = new CodeMsg(500301, "发送信息成功");
    public static CodeMsg SEND_ERROR = new CodeMsg(500302, "发送信息失败");

    /**
     * 用户模块 5004XX
     */
    public static CodeMsg FOLLOW_ERROR = new CodeMsg(500402, "关注失败");
    public static CodeMsg UNFOLLOW_ERROR = new CodeMsg(500403, "取消关注失败");
    public static CodeMsg SENDMSG_SUCCESS = new CodeMsg(500404, "发送私信成功");
    public static CodeMsg SENDMSG_ERROR = new CodeMsg(500405, "发送私信失败");
    public static CodeMsg DELETE_INTERESTTAG_SUCCESS = new CodeMsg(500406, "删除感兴趣标签成功");
    public static CodeMsg ADD_INTERESTTAG_OVER = new CodeMsg(500407, "非会员用户可添加感兴趣的标签不能超过%s个");
    public static CodeMsg ADD_INTERESTTAG_SUCCESS = new CodeMsg(500408, "添加感兴趣标签成功");
    public static CodeMsg ADD_INTERESTTAG_ERROR = new CodeMsg(500409, "添加感兴趣标签失败");
    public static CodeMsg DELETE_INTERESTTAG_ERROR = new CodeMsg(500410, "删除感兴趣标签失败");
    public static CodeMsg APPLY_AUTHORIZED_SUCCESS = new CodeMsg(500411, "申请认证成功");
    public static CodeMsg APPLY_AUTHORIZED_EOORO = new CodeMsg(500412, "申请认证失败");

    /**
     * 权限模块 5005XX
     */
    public static CodeMsg USER_EMPTY = new CodeMsg(500501, "用户未登录");
    public static CodeMsg MEMBER_EMPTY = new CodeMsg(500502, "用户非会员");
    public static CodeMsg ACCESS_ARTICLE_SUCCESS = new CodeMsg(500503, "有权访问权威文章");
    public static CodeMsg ACCESS_ARTICLE_ERROR = new CodeMsg(500504, "访问权威文章失败");
    public static CodeMsg USER_ONLINE = new CodeMsg(500505, "用户已登录");
    public static CodeMsg USER_ERROR = new CodeMsg(500506, "校验用户状态异常");
    public static CodeMsg USER_NOTEXIST = new CodeMsg(500507, "用户不存在");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500508, "密码错误");
    public static CodeMsg LOGIN_ERROR = new CodeMsg(500509, "校验登录异常");
    public static CodeMsg LOGIN_SUCCESS = new CodeMsg(500510, "校验登录成功");
    public static CodeMsg LOGIN_MANAGER_ERROR = new CodeMsg(500511, "校验管理员登录异常");
    public static CodeMsg LOGIN_MANAGER_SUCCESS = new CodeMsg(500512, "管理员登录成功");
    public static CodeMsg MANAGER_NOTEXIST = new CodeMsg(500513, "管理员不存在");

    public static CodeMsg SESSION_ERROR = new CodeMsg(500201, "Session不存在或者已经失效，请返回登录！");
    public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500202, "登录密码不能为空");
    public static CodeMsg MOBILE_EMPTY = new CodeMsg(500203, "手机号不能为空");
    public static CodeMsg MOBILE_ERROR = new CodeMsg(500204, "手机号格式错误");
    public static CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500205, "手机号不存在");
    public static CodeMsg USER_EXIST = new CodeMsg(500207, "用户已经存在，无需重复注册");
    public static CodeMsg REGISTER_SUCCESS = new CodeMsg(500208, "注册成功");
    public static CodeMsg REGISTER_FAIL = new CodeMsg(500209, "注册异常");
    public static CodeMsg FILL_REGISTER_INFO = new CodeMsg(500210, "请填写注册信息");
    public static CodeMsg WAIT_REGISTER_DONE = new CodeMsg(500211, "等待注册完成");

    public static CodeMsg PRODUCT_NOT_EXIST = new CodeMsg(500300, "商品不存在");
    public static CodeMsg PRODUCT_EMPTY = new CodeMsg(500301, "商品列表为空");
    public static CodeMsg SECKILL_PRODUCT_EMPTY = new CodeMsg(500301, "秒杀商品列表为空");
    //订单模块 5004XX
    public static CodeMsg ORDER_NOT_EXIST = new CodeMsg(500400, "订单不存在");
    public static CodeMsg ORDERITEM_NOT_EXIST = new CodeMsg(500401, "订单项不存在");

    /**
     * 秒杀模块 5005XX
     */
    public static CodeMsg SECKILL_OVER = new CodeMsg(500500, "商品库存不足");
    public static CodeMsg REPEATE_SECKILL = new CodeMsg(500501, "不能重复秒杀");
    public static CodeMsg SECKILL_FAIL = new CodeMsg(500502, "秒杀失败");
    public static CodeMsg SECKILL_PARM_ILLEGAL = new CodeMsg(500503, "秒杀请求参数异常：%s");
    public static CodeMsg SECKILL_SUCCESS = new CodeMsg(500504, "秒杀成功");

    public CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 动态地填充msg字段 arg格式化到msg中，组合成一个message
     *
     * @param args
     * @return
     */
    public CodeMsg fillArgs(Object... args) {
        int code = this.code;
        String message = String.format(this.msg, args);
        return new CodeMsg(code, message);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "CodeMsg{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
