package com.kkb.dao.impl;

import com.kkb.bean.Express;
import com.kkb.dao.BaseExpressDao;
import com.kkb.exception.DuplicateCodeException;
import junit.framework.TestCase;

public class BaseExpressDaoImplTest extends TestCase {
    BaseExpressDao dao = new BaseExpressDaoImpl();

    public void testConsole() {
    }

    public void testFindAll() {
    }

    public void testFindByNumber() {
    }

    public void testFindByCode() {
    }

    public void testFindByUserPhone() {
    }

    public void testFindBySysPhone() {
    }

    public void testInsert() throws DuplicateCodeException {
        boolean insert = false;
        try {
            for(int i=0;i<100;i++){
                Express e = new Express("12311"+i,"李伟杰","18516955565","顺丰快递","18888888888",666000+i+"");
                insert = dao.insert(e);
            }

        } catch (DuplicateCodeException duplicateCodeException) {
            System.out.println("取件码重复的异常被捕获到了");
        }
        System.out.println(insert);
    }

    public void testUpdate() {
        Express e = new Express();
        e.setNumber("321");
        e.setCompany("哈哈快递");
        e.setUsername("王二");
        e.setStatus(1);
        boolean flag = dao.update(1, e);
        System.out.println(flag);
    }

    public void testUpdateStatus() {
    }

    public void testDelete() {
    }
}