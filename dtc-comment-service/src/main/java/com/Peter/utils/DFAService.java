package com.Peter.utils;

import com.Peter.dto.SensitiveWordsDto;
import com.Peter.entity.SensitiveWordsExample;
import com.Peter.service.SensitiveService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 构建DFA敏感词过滤算法
 * 应该放在service层
 *  它依赖 SensitiveService（同在 service 模块）
 *  Controller 可以通过依赖 service 模块来使用它
 * 符合分层架构：Controller → Service → ORM
 */
@Service
@Slf4j
public class DFAService implements InitializingBean {
    @Autowired
    private SensitiveService sensitiveService;

    private final State startState=new State();
    @Override
    public void afterPropertiesSet()throws Exception{
        //1.从数据库查询敏感词
        SensitiveWordsExample sensitiveWordsExample=new SensitiveWordsExample();
        sensitiveWordsExample.setLimit(1000);
        List<SensitiveWordsDto> sensitiveWordsDtos=sensitiveService.queryByParam(sensitiveWordsExample);
        //构建敏感词算法-DFA
        List<String> words=sensitiveWordsDtos.stream().map(x->x.getWord()).collect(Collectors.toList());
        //增加敏感词
        addWords( words);

    }

    /**
     * 批量构建
     * @param words
     */

    private void addWords(List<String> words) {
        if(CollectionUtils.isEmpty( words)){
            return;
        }
        for(String word:words){
            addWord( word);
        }
    }

    /**
     * 单个构建
     * @param word
     */
    //DFA：确定有限自动机
    private void addWord(String word) {
        log.info("DFAService-添加敏感词-入参：{}",word);
        State currentState=startState;
        char[] charArray=word.toCharArray();
        for(char c:charArray){
            State nextState=currentState.nextState(c);
            /*
              尝试从当前状态节点获取下一个字符 c 对应的状态
              如果这个字符路径已经存在，返回已有的状态节点
              如果不存在，返回 null
              这段代码确保在构建 DFA 树时，只为不存在的字符路径创建新节点，
              已存在的路径会被复用，避免重复创建，实现高效的敏感词匹配结构。
              添加"赌博"后：
              startState --'赌'--> State1 --'博'--> State2(终止)
              添加"赌球"时：
              - 处理'赌'：nextState 不为null（已存在State1），直接复用
              - 处理'球'：nextState 为null，创建 State3
                startState --'赌'--> State1 --'博'--> State2(终止)
                                           \--'球'--> State3(终止)
             */
            if(nextState==null){
                nextState=new State();
                currentState.add(c,nextState);
            }
            currentState = nextState;
        }
        currentState.setTerminal(true);
    }
    /**
     * 敏感词匹配
     * 如果匹配到敏感词，把敏感词替换为*；否则返回原字符串
     */
    public String checkSensitiveWord(String word,char replacechar){
        log.info("DFAService-敏感词匹配-入参：{}",word);
        if(StringUtils.isEmpty(word)){
            return word;
        }
        StringBuilder result=new StringBuilder();//存储结果
        int length=word.length();
        for(int i=0;i<length;){
            State currentState=startState;
            int j=i;
            while(currentState != null&&j< length){
                currentState=currentState.nextState(word.charAt(j));
                //匹配到了敏感词
                if(currentState != null && currentState.isTerminal()){
                    //替换敏感词
                    for(int k=i;k<=j;k++){
                        result.setCharAt(k,replacechar);
                    }
                    i=j+1;
                    break;
                }
                j++;
            }
            if(currentState== null){
                i++;
            }
        }
      return result.toString();
    }
    private static class State{
        private final Map<Character,State>transitions=new HashMap<>();

        private boolean isTerminal=false;

        public State nextState(char c){
          return   transitions.getOrDefault(c,null);
        }
        public void add(char c,State nextState){
            transitions.put(c,nextState);
        }
        public boolean isTerminal(){
            return isTerminal;
        }

        public void setTerminal(boolean terminal){
            isTerminal= terminal;
        }
    }
}
