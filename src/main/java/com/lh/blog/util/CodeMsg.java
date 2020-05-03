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
    public static CodeMsg BIND_PARAM_ERROR = new CodeMsg(500101, "参数个数校验异常");
    public static CodeMsg REQUEST_ILLEGAL = new CodeMsg(500102, "请求非法");
    public static CodeMsg VERITF_FAIL = new CodeMsg(500103, "校验失败，请重新输入表达式结果或刷新校验码重新输入");
    public static CodeMsg ACCESS_LIMIT_REACHED = new CodeMsg(500104, "访问太频繁！");
    public static CodeMsg INSERT_FAIL = new CodeMsg(500104, "添加 %s 失败");
    public static CodeMsg DELETE_FAIL = new CodeMsg(500104, "删除 %s 失败");
    public static CodeMsg UPDATE_FAIL = new CodeMsg(500104, "更新 %s 失败");
    public static CodeMsg GET_FAIL = new CodeMsg(500104, "查找 %s 失败");
    public static CodeMsg MODULE_FAIL = new CodeMsg(500105, " %s 模块出现异常");
    public static CodeMsg PIC_UPLOAD_ERROR = new CodeMsg(500106, "%s图片上传异常");

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
    public static CodeMsg ARTICLE_PUBLISH_ERROR = new CodeMsg(500214, "创建文章失败");
    public static CodeMsg ARTICLE_PUBLISH_SUCCESS = new CodeMsg(500215, "创建文章成功");
    public static CodeMsg ARTICLE_DELETE_SUCCESS = new CodeMsg(500216, "删除文章成功");
    public static CodeMsg ARTICLE_DELETE_ERROR = new CodeMsg(500217, "删除文章失败");
    public static CodeMsg ARTICLE_UPDATE_SUCCESS = new CodeMsg(500218, "更新文章成功");
    public static CodeMsg ARTICLE_UPDATE_ERROR = new CodeMsg(500219, "更新文章失败");
    public static CodeMsg CHICKEN_ERROR = new CodeMsg(500220, "赞赏失败");
    public static CodeMsg CHICKEN_SUCCESS = new CodeMsg(500221, "赞赏成功");


    /**
     * 信息模块 5003XX
     */
    public static CodeMsg SEND_MESSAGE_SUCCESS = new CodeMsg(500301, "发送信息成功");
    public static CodeMsg SEND_MESSAGE_ERROR = new CodeMsg(500302, "发送信息失败");
    public static CodeMsg SEND_EMAIL_ERROR = new CodeMsg(500303, "发送邮件失败");
    public static CodeMsg CHECK_MSG_ERROR = new CodeMsg(500304, "私信审核失败");
    public static CodeMsg CHECK_MSG_SUCCESS = new CodeMsg(500305, "私信审核成功");
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
    public static CodeMsg APPLY_AUTHORIZED_ERROR = new CodeMsg(500412, "申请认证失败");
    public static CodeMsg GET_RANDOM_ERROR = new CodeMsg(500413, "获取验证码失败");
    public static CodeMsg MODIFY_PASSWORD_ERROR = new CodeMsg(500414, "重置密码失败");
    public static CodeMsg MODIFY_PASSWORD_SUCCESS = new CodeMsg(500415, "重置密码成功");
    public static CodeMsg VALIDATE_RANDOM_ERROR = new CodeMsg(500416, "校验验证码失败");
    public static CodeMsg GET_RANDOM_SUCCESS = new CodeMsg(500417, "获取验证码成功");
    public static CodeMsg REGISTER_SUCCESS = new CodeMsg(500418, "用户注册成功");
    public static CodeMsg REGISTER_ERROR = new CodeMsg(500419, "用户注册失败");
    public static CodeMsg MODIFY_USER_SUCCESS = new CodeMsg(500420, "更改用户成功");
    public static CodeMsg MODIFY_USER_ERROR = new CodeMsg(500421, "更改用户失败");

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

    /**
     * 其他模块 5006XX
     */
    public static CodeMsg DELETE_HISTORY_ERROR = new CodeMsg(500601, "历史记录删除失败");
    public static CodeMsg DELETE_HISTORY_SUCCESS = new CodeMsg(500602, "历史记录删除成功");
    public static CodeMsg DELETE_START_ERROR = new CodeMsg(500603, "收藏删除失败");
    public static CodeMsg DELETE_START_SUCCESS = new CodeMsg(500604, "收藏删除成功");

    /**
     * 标签模块 5007XX
     */
    public static CodeMsg TAG_ADD_ERROR = new CodeMsg(500701, "为文章添加标签失败");
    public static CodeMsg TAG_SEARCH_ERROR = new CodeMsg(500702, "搜索标签失败");
    public static CodeMsg TAG_INSERT_ERROR = new CodeMsg(500703, "自定义标签失败");
    public static CodeMsg TAG_INSERT_SYM = new CodeMsg(500704, "存在同义词语:%s");
    public static CodeMsg TAG_INSERT_OVER = new CodeMsg(500705, "自定义标签不能超过%s个");
    public static CodeMsg TAG_INSERT_SUCCESS = new CodeMsg(500706, "自定义标签成功");
    public static CodeMsg TAG_DELETE_SUCCESS = new CodeMsg(500707, "删除标签成功");
    public static CodeMsg TAG_DELETE_ERROR = new CodeMsg(500708, "删除标签异常");

    /**
     * 分类模块 5008XX
     */
    public static CodeMsg CATE_GET_ERROR = new CodeMsg(500801, "获取分类失败");
    public static CodeMsg CATE_INSERT_OVER = new CodeMsg(500802, "自定义分类不能超过%s个");
    public static CodeMsg CATE_INSERT_SUCCESS = new CodeMsg(500803, "自定义分类成功");
    public static CodeMsg CATE_INSERT_ERROR = new CodeMsg(500804, "自定义分类失败");
    public static CodeMsg CATE_DELETE_ERROR = new CodeMsg(500805, "删除分类失败");
    public static CodeMsg CATE_DELETE_SUCCESS = new CodeMsg(500806, "删除分类成功");
    public static CodeMsg CATE_UPDATE_ERROR = new CodeMsg(500807, "更新分类失败");
    public static CodeMsg CATE_UPDATE_SUCCESS = new CodeMsg(500808, "更新分类成功");

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
