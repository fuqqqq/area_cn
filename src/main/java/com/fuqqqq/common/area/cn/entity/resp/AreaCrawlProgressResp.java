package com.fuqqqq.common.area.cn.entity.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AreaCrawlProgressResp implements Serializable {
    @Serial
    private static final long serialVersionUID = -4526669863047739878L;


    private Integer total = 0;
    private Integer step = 0;
    private String message = "[N/A]";
}
