package com.hhekj.btc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhekj.btc.common.TransferStatus;
import com.hhekj.btc.mapper.BlockTransferIntoMapper;
import com.hhekj.btc.model.BlockTransferInto;
import com.hhekj.btc.service.IBlockTransferIntoService;
import com.hhekj.btc.tool.NewDateKit;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * anther : hux
 * datetime : 2019/11/8 18:19
 * description :
 */
@Service
public class BlockTransferIntoServiceImpl
        extends ServiceImpl<BlockTransferIntoMapper, BlockTransferInto> implements IBlockTransferIntoService {

    @Resource
    private BlockTransferIntoMapper mapper;


    @Override
    public boolean existsTxHash(String txHash) {
        return lambdaQuery().eq(BlockTransferInto::getTxHash, txHash).count() > 0;
    }

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
    public void save(Integer userId, Integer coinId, String txHash, BigDecimal amount, String from, String to) {
        if (existsTxHash(txHash)) {
            return;
        }
        BlockTransferInto record = new BlockTransferInto();
        record.setCoinId(coinId);
        record.setTxHash(txHash);
        record.setCollect(0);
        record.setStatus(0);
        record.setToAddress(to);
        record.setToId(userId);
        record.setFromAddress(from);
        record.setCreateTime(NewDateKit.now());
        record.setAmount(amount);
        save(record);
    }

    @Override
    public void save(Integer userId, Integer coinId, String txHash, BigDecimal amount, String from, String to, String audit) {
        if (existsTxHash(txHash)) {
            return;
        }
        BlockTransferInto record = new BlockTransferInto();
        record.setCoinId(coinId);
        record.setTxHash(txHash);
        record.setCollect(0);
        record.setStatus(0);
        record.setToAddress(to);
        record.setToId(userId);
        record.setFromAddress(from);
        record.setCreateTime(NewDateKit.now());
        record.setAmount(amount);
        record.setAudit(audit);
        save(record);
    }

    @Override
    public List<BlockTransferInto> userNoneCollectNum() {
        return mapper.userNoneCollectNum();
    }

    @Override
    public List<BlockTransferInto> userNoneCollectNum(Integer coinId) {
        return mapper.userNoneCollectNumByCoinId(coinId);
    }

    @Override
    public boolean collectAsset(Integer toId, Integer coinId, Integer collect, String collectHash) {
        return mapper.collectAsset(toId, coinId, collect, collectHash, NewDateKit.now());
    }

    @Override
    public boolean collectAssetByHash(String hash, Integer collect) {
        return mapper.collectAssetByHash(hash, collect, NewDateKit.now());
    }

    @Override
    public List<Map<String, Object>> collectVerify() {

        return mapper.collectVerify();
    }
}
