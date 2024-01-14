package com.fuqqqq.common.area.cn.entity.resp;

import com.fuqqqq.common.area.cn.data.model.TAreaModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AreaResp implements Serializable {
    @Serial
    private static final long serialVersionUID = -8961542534670971346L;

    private String code;
    private String name;
    private Integer level;


    public static AreaResp fromModel(TAreaModel model) {
        return AreaResp.builder()
                .code(model.getCode())
                .name(model.getName())
                .level(model.getLevel())
                .build();
    }

    public static List<AreaResp> fromModels(TAreaModel... models) {
        return Arrays.stream(models)
                .map(AreaResp::fromModel)
                .sorted(Comparator.comparing(AreaResp::getCode))
                .toList();
    }
}
