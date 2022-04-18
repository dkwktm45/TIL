package com.sp.fc.web.service;

import com.sp.fc.web.paper.Paper;
import com.sp.fc.web.paper.PaperRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaperService {

    private final PaperRepository paperRepository;


    public PaperService(PaperRepository paperRepository) {
        this.paperRepository = paperRepository;
    }
    public void setPaper(Paper paper){
        paperRepository.save(paper);
    }
    public Optional<Paper> getPaper(Long paperId){return paperRepository.findById(paperId);}
}

