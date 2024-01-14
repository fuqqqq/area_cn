package com.fuqqqq.common.area.cn.data.service.impl;

import com.fuqqqq.common.area.cn.data.mapper.TAreaMapper;
import com.fuqqqq.common.area.cn.data.model.TAreaModel;
import com.fuqqqq.common.area.cn.data.service.TAreaService;
import com.fuqqqq.common.area.cn.data.service.impl.base.BaseDataServiceImpl;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TAreaServiceImpl extends BaseDataServiceImpl implements TAreaService {
    @Resource
    private TAreaMapper mapper;


    @Override
    public TAreaModel findOne(@Nonnull String code) {
        return mapper.selectByPrimaryKey(code);
    }

    @Override
    public List<TAreaModel> findAll() {
        return mapper.selectAll();
    }

    @Override
    public List<TAreaModel> findByLevel(int level, String codePrefix) {
        return mapper.selectByLevel(level, codePrefix);
    }

    @Override
    public void clear() {
        mapper.truncateTable();
    }

    @Transactional
    @Override
    public int insertBatch(TAreaModel... models) {
        synchronized (this) {
            return models.length > 0 ? mapper.insertBatch(models) : 0;
        }
    }
}
