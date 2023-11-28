package com.yuwen.rtiweb.service;

import com.yuwen.rtiweb.entity.IndexTitle;
import com.yuwen.rtiweb.mapper.IndexTitleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 */
@Service
public class IndexTitleService {

    @Autowired
    private IndexTitleMapper indexTitleMapper;

    public String getRitIndexTitle(Long id) {
        //        return indexTitleMapper.selectById(id);
        IndexTitle indexTitle = indexTitleMapper.selectById(id);
        return indexTitle != null ? indexTitle.getTitlelist() : null;
    }

    public List<IndexTitle> getAllIndexTitle() {
        return indexTitleMapper.selectList(null);
    }
}
