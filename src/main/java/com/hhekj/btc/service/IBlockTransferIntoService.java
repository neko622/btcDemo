package com.hhekj.btc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hhekj.btc.common.TransferStatus;
import com.hhekj.btc.model.BlockTransferInto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * anther : hux
 * datetime : 2019/11/8 18:17
 * description :
 */
public interface IBlockTransferIntoService extends IService<BlockTransferInto> {

    /**
     * 交易哈希是否已经保存过
     *
     * @param txHash 交易哈希
     * @return 是否存在
     */
    boolean existsTxHash(String txHash);

    /**
     * 更改数据
     *
     * @param into 实体
     */
    boolean updateById(BlockTransferInto into);

    /**
     * 修改状态
     * <p>
     *
     * @param id     id
     * @param status 状态
     */
    void changeStatus(Integer id, TransferStatus status);


    /**
     * 根据状态查询
     * <p>
     * 2019-11-09 10:48 hux ++
     *
     * @param status 状态
     */
    List<BlockTransferInto> listByStatus(TransferStatus status);

    /**
     * 保存充值记录
     *
     * @param userId 用户id
     * @param coinId 币种id
     * @param txHash 交易hash
     * @param amount 充值的数量
     * @param from   付款地址
     * @param to     收款地址
     */
    void save(Integer userId, Integer coinId, String txHash, BigDecimal amount, String from, String to);

    /**
     * 保存充值记录
     *
     * @param userId 用户id
     * @param coinId 币种id
     * @param txHash 交易hash
     * @param amount 充值的数量
     * @param from   付款地址
     * @param to     收款地址
     * @param audit     审核状态 audit 等待审核 ok： 审核通过 no：审核驳回
     */
    void save(Integer userId, Integer coinId, String txHash, BigDecimal amount, String from, String to, String audit);

    /**
     * 获取未做资金归集的用户
     */
    List<BlockTransferInto> userNoneCollectNum();

    /**
     * 获取单个货币未做资金归集的用户
     *
     * @param coinId 代币id
     */
    List<BlockTransferInto> userNoneCollectNum(Integer coinId);


    /**
     * 资金归集状态更改
     *
     * @param toId        用户id
     * @param coinId      代币id
     * @param collectHash 交易hash
     */
    boolean collectAsset(Integer toId, Integer coinId, Integer collect, String collectHash);

    /**
     * 资金归集状态更改
     *
     * @param hash 哈希
     */
    boolean collectAssetByHash(String hash, Integer collect);

    /**
     * 需要对账的资金归集列表
     */
    List<Map<String, Object>> collectVerify();

}
