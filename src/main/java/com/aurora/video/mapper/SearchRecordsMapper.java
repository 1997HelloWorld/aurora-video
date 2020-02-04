package com.aurora.video.mapper;


import com.aurora.video.pojo.SearchRecords;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchRecordsMapper {
    int deleteByPrimaryKey(String id);

    @Insert("insert into search_records (content) values (#{content})")
    int insert(String content);
    @Select("SELECT content FROM `search_records` GROUP BY content ORDER BY COUNT(content) DESC")
    List<String> getHotWordsList();
}