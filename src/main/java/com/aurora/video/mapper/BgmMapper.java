package com.aurora.video.mapper;


import com.aurora.video.pojo.Bgm;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BgmMapper {
    int deleteByPrimaryKey(String id);

    int insert(Bgm record);

    int insertSelective(Bgm record);

    @Select("Select * from bgm where id = #{id}")
    Bgm selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Bgm record);

    int updateByPrimaryKey(Bgm record);

    @Select("select * from bgm")
    List<Bgm> selectAll();
}