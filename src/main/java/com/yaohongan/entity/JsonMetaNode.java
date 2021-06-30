package com.yaohongan.entity;

import lombok.Data;

import java.util.List;

/**
 * @Auther: yaohongan
 * @Date: 2021/3/29 18:11
 * @Description:
 */
@Data
public  class JsonMetaNode {
    private String key;
    private Object value;
    private String valueType;
    //数据库中的列名
    private String dbColName;
    private List<JsonMetaNode> children;

}