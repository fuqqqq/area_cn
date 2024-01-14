package com.fuqqqq.common.area.cn.data.model;

import com.fuqqqq.common.area.cn.data.model.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TConfigModel extends BaseModel {
    @Serial
    private static final long serialVersionUID = 2830899769584401067L;

    private String key;
    private String value;
    private String describe;


    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TConfigModel other = (TConfigModel) that;
        return (this.getKey() == null ? other.getKey() == null : this.getKey().equals(other.getKey()))
                && (this.getValue() == null ? other.getValue() == null : this.getValue().equals(other.getValue()))
                && (this.getDescribe() == null ? other.getDescribe() == null : this.getDescribe().equals(other.getDescribe()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getKey() == null) ? 0 : getKey().hashCode());
        result = prime * result + ((getValue() == null) ? 0 : getValue().hashCode());
        result = prime * result + ((getDescribe() == null) ? 0 : getDescribe().hashCode());
        return result;
    }
}