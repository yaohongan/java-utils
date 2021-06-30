package com.yaohongan.utils;


import com.yaohongan.entity.JsonMetaNode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * @Auther: yaohongan
 * @Description //生成sql语句工具类
 * @Date： 2021/3/29 18:25
 */
@Service
public class GenerationSQLUtil {




    public static String INTEGER = "java.lang.Integer";
    public static String LONG = "java.lang.Long";
    public static String STRING = "java.lang.String";
    public static String JSONOBJECT = "com.alibaba.fastjson.JSONObject";
    public static String FLOAT = "java.lang.Float";
    public static String DOUBLE = "java.lang.Double";
    public static String BIG_DECIMAL = "java.math.BigDecimal";
    public static String DATE = "java.util.Date";






    /**
     * @Auther: yaohongan
     * @Description //建表语句
     * @Date： 2021/4/8 11:18
     */
    public  void createTable(String tableName, List<JsonMetaNode> jsonMetaNodeList) {
        String sqlCreate = "CREATE TABLE " + tableName + "(\n" + getRowName(jsonMetaNodeList)+");";
        System.out.println(sqlCreate);
    }


    /**
     * 获取建表语句的列名
     *
     * @author sql
     * @date 10/23/17 3:43 PM
     */
    public  String getRowName(List<JsonMetaNode> jsonMetaNodeList) {
        StringBuffer sqlRowNameBuffer = new StringBuffer();
        boolean hasId = false;
        for (JsonMetaNode jsonMetaNode : jsonMetaNodeList) {
            String key ="";
            if(jsonMetaNode.getKey()!=null){
                if(jsonMetaNode.getKey().equals("value")||jsonMetaNode.getKey().equals("key")){
                    key = jsonMetaNode.getKey()+"_";
                }else {
                    key = jsonMetaNode.getKey();
                }
            }
            String valueType = jsonMetaNode.getValueType();
            String type = "";
            //判断id是否存在
            if (key.equals("id")){
                hasId = true;
            }
            if ("Integer".equals(valueType)) {
                type = "int(10) NULL";
            } else if ("Long".equals(valueType)) {
                type = "bigint(0)";
            } else if ("String".equals(valueType)) {
                type = "varchar(255)";
            } else if (BIG_DECIMAL.equals(valueType)) {
                type = "decimal(18,8)";
            } else if ("Float".equals(valueType)) {
                type = "float(100,10)";
            } else if ("Double".equals(valueType)) {
                type = "double(100,10)";
            } else if ("Daye".equals(valueType)) {
                type = "datetime";
            } else {
                type = "varchar(255)";
            }
            sqlRowNameBuffer.append(key).append(" ").append(type);
            sqlRowNameBuffer.append(",");
        }
        if (!hasId){
            sqlRowNameBuffer.append("id").append(" ").append("int(10)").append(" ").append("NOT NULL").append(" ").append("AUTO_INCREMENT,").append(" PRIMARY KEY (`id`)\n");
        }
        if(sqlRowNameBuffer!=null&&sqlRowNameBuffer.length()>0){
            sqlRowNameBuffer.deleteCharAt(sqlRowNameBuffer.length() - 1);
        }
        String sqlRowName = sqlRowNameBuffer.toString();
        return sqlRowName;
    }


    /**
     * @Auther: yaohongan
     * @Description //获取插入sql语句
     * @Date： 2021/4/2 15:32
     */
    public void insertSql(String tableName,List<JsonMetaNode> jsonMetaNodeList){
        String insertSql ="";
        String columnName = getColumnName(jsonMetaNodeList);
        String value = getValue(jsonMetaNodeList);
        if(StringUtils.isNotEmpty(columnName)&&StringUtils.isNotEmpty(value)){
            insertSql = "INSERT INTO " + tableName + " (\n"+columnName+")VALUES(\n"+value+");";
        }


    }



   /**
    * @Auther: yaohongan
    * @Description //获取插入数据字段
    * @Date： 2021/4/6 10:49
    */
   public  String getColumnName(List<JsonMetaNode> jsonMetaNodes) {
       StringBuffer sqlColumnNameBuffer = new StringBuffer();
       for (JsonMetaNode jsonMetaNode : jsonMetaNodes) {
           String key =CamelAndUnderLineConverter.camelToUnderline(jsonMetaNode.getKey());
           if(jsonMetaNode.getKey()!=null){
              sqlColumnNameBuffer.append(key).append(",");
//               if(jsonMetaNode.getKey().equals("value")||jsonMetaNode.getKey().equals("key")){
//                   key = jsonMetaNode.getKey()+"_";
//               }else {
//                   key = jsonMetaNode.getKey();
//               }
//

           }

       }
       if(sqlColumnNameBuffer!=null&&sqlColumnNameBuffer.length()>0){
           sqlColumnNameBuffer.deleteCharAt(sqlColumnNameBuffer.length() - 1);
       }
       String sqlRowName = sqlColumnNameBuffer.toString();
       return sqlRowName;





    }

        /**
         * @Auther: yaohongan
         * @Description //获取插入数据表数据语句
         * @Date： 2021/4/6 10:49
         */
        public  String getValue(List<JsonMetaNode> jsonMetaNodeList) {
            StringBuffer sqlValueBuffer = new StringBuffer();
            for (JsonMetaNode jsonMetaNode : jsonMetaNodeList) {
                Object value = jsonMetaNode.getValue();
                String valueType = jsonMetaNode.getValueType();
                String type = "";
                if (INTEGER.equals(valueType)) {
                    type = "int(10) NULL";
                } else if (LONG.equals(valueType)) {
                    type = "bigint(100)";
                } else if (STRING.equals(valueType)) {
                    type = "varchar(255)";
                } else if (BIG_DECIMAL.equals(valueType)) {
                    type = "decimal(18,8)";
                } else if (FLOAT.equals(valueType)) {
                    type = "float(100,10)";
                } else if (DOUBLE.equals(valueType)) {
                    type = "double(100,10)";
                } else if (DATE.equals(valueType)) {
                    type = "datetime";
                }else {
                    type = "varchar(255)";
                }
                if(type.equals("varchar(255)")){
                    if(value.getClass().getName().equals("java.lang.String")){
                       value = value.toString().trim().replaceAll("\\\\","");
                    }
                    sqlValueBuffer.append("'"+value+"'").append(",");
                }else{
                    sqlValueBuffer.append(value).append(",");
                }
            }
            if(sqlValueBuffer!=null&&sqlValueBuffer.length()>0){
                sqlValueBuffer.deleteCharAt(sqlValueBuffer.length() - 1);
            }
            String sqlRowName = sqlValueBuffer.toString();
            return sqlRowName;
        }





        
}
