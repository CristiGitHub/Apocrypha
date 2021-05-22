package com.Apocrypha.Apocrypha.DBConnection;

import org.hibernate.HibernateException;
import org.hibernate.dialect.SQLServerDialect;

import java.beans.BeanProperty;
import java.sql.Types;

public class SQLServerNativeDialect {//extends SQLServerDialect {
//    public SQLServerNativeDialect() {
//        super();
//        registerColumnType(Types.VARCHAR, "nvarchar($l)");
//        registerColumnType(Types.CLOB, "nvarchar(max)");
//    }
//
//    public String getTypeName(int code, int length, int precision, int scale) throws HibernateException {
//        if(code != 2005) {
//            return super.getTypeName(code, length, precision, scale);
//        } else {
//            return "ntext";
//        }
//    }
}