package com.hhekj.btc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hhekj.btc.common.DTConst;
import com.hhekj.btc.model.BlockTransferInto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * anther : hux
 * datetime : 2019/11/8 18:14
 * description :
 */
@Mapper
public interface BlockTransferIntoMapper extends BaseMapper<BlockTransferInto> {

    @Select("select coin_id,to_id,sum(amount) as amount from block_transfer_into where collect=0 and audit='ok' group by to_id,coin_id")
    List<BlockTransferInto> userNoneCollectNum();

    @Select("select to_id,sum(amount) as amount from block_transfer_into where coin_id=#{coinId} and collect=0 and audit='ok' group by to_id,coin_id")
    List<BlockTransferInto> userNoneCollectNumByCoinId(@Param("coinId") Integer coinId);

    /**
     * 资金归集成功状态更改
     *
     * @param toId   用户id
     * @param coinId 代币id
     */
    @Update("update " + DTConst.BlockInto + " set collect=#{collect},collect_hash=#{collectHash},collect_time=#{collectTime} where to_id=#{toId} and coin_id=#{coinId}")
    boolean collectAsset(@Param("toId") Integer toId, @Param("coinId") Integer coinId, @Param("collect") Integer collect, @Param("collectHash") String collectHash, @Param("collectTime") String collectTime);


    /**
     * 资金归集状态更改
     *
     * @param hash 哈希
     */
    @Update("update " + DTConst.BlockInto + " set collect=#{collect},collect_time=#{collectTime} where collect_hash=#{hash}")
    boolean collectAssetByHash(@Param("hash") String hash, @Param("collect") Integer collect, @Param("collectTime") String collectTime);

    /**
     * 需要对账的资金归集列表
     */
    @Select("select collect_hash,collect from " + DTConst.BlockInto + " where collect in(2,3) GROUP BY collect_hash")
    List<Map<String, Object>> collectVerify();

}