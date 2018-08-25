package com.izumi.myletter.common;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * hql 工具类
 * 
 * @Title:
 * @Description:
 * @Author:Administrator
 * @Since:2018年7月3日
 * @Version:1.1.0
 */

public class HqlUtils {
    private StringBuffer condition = new StringBuffer();
    
    private boolean hasWhere = false;
    
    private String strWhere = " where ";
    
    public HqlUtils() {
    }
    
    public HqlUtils(String strWhere) {
        this.strWhere = strWhere;
    }

    //给条件语句添加and
    public void addConditionAnd(String where) {
        if (where == null || where.equals(""))
            return;
        String s;
        //单条件不加and
        if (hasWhere) {
            s = " and ";
        } else {
            s = strWhere;
            hasWhere = true;
        }
        condition.append(s + "(" + where + ")");
    }
    
    public void addConditionOr(String where) {
        if (where == null || where.equals(""))
            return;
        String s;
        if (hasWhere) {
            s = " or ";
        } else {
            s = strWhere;
            hasWhere = true;
        }
        condition.append(s + "(" + where + ")");
    }
    
    public String getCondition() {
        return condition.toString();
    }
    
    /**
     * 分页查询
     * 
     * @param currentPage
     * @param pageSize
     * @param session
     * @param hql
     * @return
     * @Description:
     */
    @SuppressWarnings("rawtypes")
    public List getPageList(int currentPage, int pageSize, Session session, String hql) {
        //hql语句的使用
        System.out.println("izumi.........:" + hql);
        Query query = session.createQuery(hql);
        if (currentPage < 1) {
            currentPage = 1;
        }
        
        if (pageSize > 100) {
            pageSize = 100;
        }
        Integer pageStart = (currentPage - 1) * pageSize;
        query.setFirstResult(pageStart);
        query.setMaxResults(pageSize);
        List list = query.list();
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }
    
    /**
     * 查询总数，该方法只适用于 完整的hql查询的方法和单表 以from开始的hql查询
     * 
     * @param session
     * @param hql
     * @return
     * @Description:
     */
    public long getCount(Session session, String hql) {
        StringBuilder stringBuilder = new StringBuilder();
        String hql_trim = hql.trim();
        if (hql_trim.startsWith("from")) {//如果是from开始
            stringBuilder.append("select count(1) ");
            stringBuilder.append(hql_trim);
        }
        Query query = session.createQuery(stringBuilder.toString());
        return (Long)query.uniqueResult();
    }
    
}
