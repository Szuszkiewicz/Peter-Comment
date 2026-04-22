package com.Peter.service.Impl;

import com.Peter.dao.SensitiveWordsDao;
import com.Peter.dto.SensitiveWordsDto;
import com.Peter.entity.SensitiveWords;
import com.Peter.entity.SensitiveWordsExample;
import com.Peter.service.SensitiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SensitiveServiceImpl implements SensitiveService {
    @Autowired
    private SensitiveWordsDao sensitiveWordsDao;
    @Override
    public int insert(SensitiveWordsDto sensitiveWordsDto) {
        SensitiveWords sensitiveWords=new SensitiveWords();
        sensitiveWords.setWord(sensitiveWordsDto.getWord());
        sensitiveWords.setCategory(sensitiveWordsDto.getCategory());
        return sensitiveWordsDao.insertSelective(sensitiveWords);//只添加指定字段，其他字段为默认
    }

    @Override
    public List<SensitiveWordsDto> queryByParam(SensitiveWordsExample sensitiveWordsExample) {
        List<SensitiveWords> sensitiveWords=sensitiveWordsDao.selectByExample(sensitiveWordsExample);
       if(CollectionUtils.isEmpty(sensitiveWords)){
           return new ArrayList<>();
       }
        List<SensitiveWordsDto> resultList=buildSensitiveWordsDtos(sensitiveWords);
       return resultList;
    }
    /**
     * 结果集转换
     * @param sensitiveWords
     * @return
     */

    private List<SensitiveWordsDto> buildSensitiveWordsDtos(List<SensitiveWords> sensitiveWords) {
       List<SensitiveWordsDto> resultList=new ArrayList<>();
        for(int i=0;i<sensitiveWords.size();i++){
            SensitiveWords sensitiveWord=sensitiveWords.get(i);
            if(sensitiveWord== null){
                continue;
            }
            SensitiveWordsDto wordDto=new SensitiveWordsDto();
            wordDto.setWord(sensitiveWord.getWord());
            wordDto.setCategory(sensitiveWord.getCategory());
            resultList.add(wordDto);
        }
        return resultList;
    }
}

