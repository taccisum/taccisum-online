package com.github.taccisum.ol.domain.repo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.taccisum.ol.dao.ThirdAccountMapper;
import com.github.taccisum.ol.domain.data.ThirdAccountDO;
import com.github.taccisum.ol.domain.entity.core.ServiceProvider;
import com.github.taccisum.ol.domain.entity.core.ThirdAccount;
import com.github.taccisum.ol.domain.exception.DataErrorException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021/12/31
 */
@Component
public class ThirdAccountRepo {
    @Resource
    private ThirdAccountMapper mapper;
    @Resource
    private Factory factory;

    public Optional<ThirdAccount> getByUsername(String name) {
        return getBySpTypeAndUsername(null, name);
    }

    public Optional<ThirdAccount> getBySpTypeAndUsername(ServiceProvider.Type type, String name) {
        LambdaQueryWrapper<ThirdAccountDO> wrapper = new LambdaQueryWrapper<ThirdAccountDO>()
                .eq(ThirdAccountDO::getUsername, name);
        if (type != null) {
            wrapper.eq(ThirdAccountDO::getSpType, type);
        }
        List<ThirdAccountDO> ls = mapper.selectList(wrapper);
        if (ls.size() > 1) {
            throw new DataErrorException("三方账号", null, String.format("账号 %s 名存在多条数据", name));
        }
        if (ls.size() == 0) {
            return Optional.empty();
        }
        ThirdAccountDO data = ls.get(0);
        return Optional.of(factory.createThirdAccount(data.getId(), data.getSpType()));
    }

    public Optional<ThirdAccount> get(long id) {
        ThirdAccountDO data = mapper.selectById(id);
        if (data == null) {
            return Optional.empty();
        }
        return Optional.of(factory.createThirdAccount(data.getId(), data.getSpType()));
    }
}
