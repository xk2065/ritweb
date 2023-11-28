package com.yuwen.rtiweb.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.yuwen.rtiweb.entity.IndexTitle;
import com.yuwen.rtiweb.service.IndexTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/idtitle")
public class IndexTitleController {
    @Autowired
    private IndexTitleService indexTitleService;

    @GetMapping("/get/{id}")
    public String get(@PathVariable("id") Long id) {
        String ritIndexTitle = indexTitleService.getRitIndexTitle(id);

        JSONObject response = new JSONObject();
        if (ritIndexTitle != null){
            response.put("id", indexTitleService.getRitIndexTitle(id));
        }else {
            response.put("id","ritIndexTitle is null");
        }
        return response.toJSONString();
    }
    @GetMapping("/all")
    public String getAllRitIndexTitles() {
        List<IndexTitle> indexTitles = indexTitleService.getAllIndexTitle();
        return JSON.toJSONString(indexTitles);
    }

}
