package com.Peter.dao;


import java.util.List;

import com.Peter.entity.SensitiveWords;
import com.Peter.entity.SensitiveWordsExample;
import org.apache.ibatis.annotations.Param;

public interface SensitiveWordsDao {
    long countByExample(SensitiveWordsExample example);

    int deleteByExample(SensitiveWordsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SensitiveWords record);

    int insertSelective(SensitiveWords record);

    List<SensitiveWords> selectByExample(SensitiveWordsExample example);

    SensitiveWords selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SensitiveWords record, @Param("example") SensitiveWordsExample example);

    int updateByExample(@Param("record") SensitiveWords record, @Param("example") SensitiveWordsExample example);

    int updateByPrimaryKeySelective(SensitiveWords record);

    int updateByPrimaryKey(SensitiveWords record);
}