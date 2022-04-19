package com.sp.fc.web.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaperService implements InitializingBean {

    // test용 db
    private HashMap<Long, Paper> paperDB = new HashMap<>();


    @Override
    public void afterPropertiesSet() throws Exception {

    }

    // 페이퍼를 넣는 메소드
    public void setPaper(Paper paper){
        paperDB.put(paper.getPaperId(), paper);
    }

    // student가 알 맞은 paper를 가져온다.
    public List<Paper> getMyPapers(String username) {
        return paperDB.values().stream().filter(
                paper -> paper.getStudentIds().contains(username)
        ).collect(Collectors.toList());
    }
    // 특정 페이퍼 아이디를 알았다면 접근 가능 메소드
    public Paper getPaper(Long paperId) {
        return paperDB.get(paperId);
    }
}
