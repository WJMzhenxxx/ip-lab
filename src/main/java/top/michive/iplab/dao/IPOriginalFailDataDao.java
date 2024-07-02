package top.michive.iplab.dao;

import top.michive.iplab.entity.IPOriginalFailData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPOriginalFailDataDao {

    IPOriginalFailData getById(Long id);

    List<IPOriginalFailData> getAllByLimit(@Param("ip_original_fail_data") IPOriginalFailData ipOriginalFailData, @Param("offset") Integer offset, @Param("limit") Integer limit);

    long count(IPOriginalFailData ipOriginalFailData);

    int insert(IPOriginalFailData ipOriginalFailData);

    int insertBatch(@Param("entities") List<IPOriginalFailData> entities);

}

