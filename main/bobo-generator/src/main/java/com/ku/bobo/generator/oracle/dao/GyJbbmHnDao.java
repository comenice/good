package com.ku.bobo.generator.oracle.dao;

import com.ku.bobo.generator.oracle.entity.GyJbbmHn;
import com.ku.bobo.generator.oracle.entity.GyJbbmHnExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GyJbbmHnDao {
    long countByExample(GyJbbmHnExample example);

    int deleteByExample(GyJbbmHnExample example);

    int insert(GyJbbmHn record);

    int insertSelective(GyJbbmHn record);

    List<GyJbbmHn> selectByExample(GyJbbmHnExample example);

    int updateByExampleSelective(@Param("record") GyJbbmHn record, @Param("example") GyJbbmHnExample example);

    int updateByExample(@Param("record") GyJbbmHn record, @Param("example") GyJbbmHnExample example);
}