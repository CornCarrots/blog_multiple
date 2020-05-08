package com.lh.blog.enums;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: linhao
 * @date: 2020/05/03/10:27
 * @description:
 */
public enum RuleEnum {

    /**
     * 值不为空
     */
    IsNotNull("值不为空"),


    /**
     * 值为数字
     */
    IsNum("值为数字");

    /**
     * 规则名称
     */
    private String rule;

    RuleEnum(String rule){
        this.rule = rule;
    }
}
