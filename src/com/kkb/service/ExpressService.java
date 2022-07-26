package com.kkb.service;

import com.kkb.bean.Express;
import com.kkb.exception.DuplicateCodeException;

import java.util.List;
import java.util.Map;

public interface ExpressService {
    List<Map<String,Integer>> console();

    List<Express> findAll(boolean limit, int offset, int pageNumber);

    Express findByNumber(String number);

    Express findByCode(String code);

    List<Express> findByUserPhone(String userPhone);

    List<Express> findBySysPhone(String sysPhone);

    Boolean insert(Express e) throws DuplicateCodeException;

    Boolean update(int id, Express e) throws DuplicateCodeException;

    Boolean updateStatus(String code);

    Boolean delete(int id);
}
