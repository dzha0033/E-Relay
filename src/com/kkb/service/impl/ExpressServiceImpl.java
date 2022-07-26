package com.kkb.service.impl;

import com.kkb.bean.Express;
import com.kkb.dao.BaseExpressDao;
import com.kkb.dao.impl.BaseExpressDaoImpl;
import com.kkb.exception.DuplicateCodeException;
import com.kkb.service.ExpressService;
import com.kkb.utils.RandomUtil;

import java.util.List;
import java.util.Map;

public class ExpressServiceImpl implements ExpressService {
    private static BaseExpressDao dao = new BaseExpressDaoImpl();

    @Override
    public List<Map<String, Integer>> console() {
        return dao.console();
    }

    @Override
    public List<Express> findAll(boolean limit, int offset, int pageNumber) {
        return dao.findAll(limit,offset,pageNumber);
    }

    @Override
    public Express findByNumber(String number) {
        return dao.findByNumber(number);
    }

    @Override
    public Express findByCode(String code) {
        return dao.findByCode(code);
    }

    @Override
    public List<Express> findByUserPhone(String userPhone) {
        return dao.findByUserPhone(userPhone);
    }

    @Override
    public List<Express> findBySysPhone(String sysPhone) {
        return dao.findBySysPhone(sysPhone);
    }

    @Override
    public Boolean insert(Express e) throws DuplicateCodeException {
        e.setCode(RandomUtil.getCode()+"");
        try {
            boolean flag = dao.insert(e);
            return flag;
        } catch (DuplicateCodeException duplicateCodeException) {
            return insert(e);
        }
    }

    @Override
    public Boolean update(int id, Express newExpress) throws DuplicateCodeException {
        if(newExpress.getUserPhone()!=null){
            dao.delete(id);
            return insert(newExpress);
        }else{
            boolean update = dao.update(id, newExpress);
            Express e = dao.findByNumber(newExpress.getNumber());
            if(newExpress.getStatus() == 1){
                updateStatus(e.getCode());
            }
            return update;
        }
    }

    @Override
    public Boolean updateStatus(String code) {
        return dao.updateStatus(code);
    }

    @Override
    public Boolean delete(int id) {
        return dao.delete(id);
    }
}
