package com.fuqqqq.common.area.cn.data.service;

import com.fuqqqq.common.area.cn.data.model.TAreaModel;
import jakarta.annotation.Nonnull;

import java.util.List;

public interface TAreaService {

    TAreaModel findOne(@Nonnull String code);

    List<TAreaModel> findAll();

    List<TAreaModel> findByLevel(int level, String codePrefix);

    void clear();

    int insertBatch(TAreaModel... models);
}
