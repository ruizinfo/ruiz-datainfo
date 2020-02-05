package io.ruiz.modules.base.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import io.ruiz.common.utils.ShiroUtils;
import io.ruiz.modules.sys.entity.SysUserEntity;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class FieldMetaObjectHandler implements MetaObjectHandler {

    private final static String CREATE_DATE = "createDate";
    private final static String CREATOR = "creator";
    private final static String UPDATE_DATE = "updateDate";
    private final static String UPDATER = "updater";

    @Override
    public void insertFill(MetaObject metaObject) {
        SysUserEntity user = ShiroUtils.getUserEntity();
        if (user == null) return;
        Date date = new Date();

        //创建者
        setFieldValByName(CREATOR, user.getUserId(), metaObject);
        //创建时间
        setFieldValByName(CREATE_DATE, date, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        SysUserEntity user = ShiroUtils.getUserEntity();
        if (user == null) return;

        //更新者
        setFieldValByName(UPDATER, user.getUserId(), metaObject);
        //更新时间
        setFieldValByName(UPDATE_DATE, new Date(), metaObject);
    }
}
