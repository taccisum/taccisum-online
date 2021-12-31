package com.github.taccisum.ol.domain.entity.core;

import com.github.taccisum.domain.core.Entity;
import com.github.taccisum.ol.dao.ThirdAccountMapper;
import com.github.taccisum.ol.domain.data.ThirdAccountDO;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 三方账号
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021/12/31
 */
public class ThirdAccount extends Entity.Base<Long> {
    @Autowired
    private ThirdAccountMapper dao;

    public ThirdAccount(long id) {
        super(id);
    }

    public ThirdAccountDO data() {
        return dao.selectById(this.id());
    }
}
