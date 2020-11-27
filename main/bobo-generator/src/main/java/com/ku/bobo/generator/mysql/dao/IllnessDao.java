package com.ku.bobo.generator.mysql.dao;

import com.ku.bobo.generator.mysql.entity.Illness;

public interface IllnessDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Illness record);

    int insertSelective(Illness record);

    Illness selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Illness record);

    int updateByPrimaryKey(Illness record);

}