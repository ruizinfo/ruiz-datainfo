package io.ruiz.modules.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.ruiz.common.utils.PageUtils;
import io.ruiz.common.utils.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected abstract QueryWrapper<T> queryWrapper(Map<String, Object> params);

    protected abstract void handleList(Collection<T> entityList, Map<String, Object> params);

    public PageUtils queryPage(Map<String, Object> params) {
        IPage<T> page = this.page(new Query<T>().getPage(params), this.queryWrapper(params));
        this.handleList(page.getRecords(), params);
        return new PageUtils(page);
    }

    public List<T> queryList(Map<String, Object> params) {
        List<T> entityList = this.list(this.queryWrapper(params));
        this.handleList(entityList, params);
        return entityList;
    }

    public <V> Map<V, T> queryMap(Wrapper<T> queryWrapper, Function<? super T, V> mapper) {
        List<T> entityList = this.list(queryWrapper);
        this.handleList(entityList, null);
        return entityList.stream().collect(Collectors.toMap(mapper, Function.identity()));
    }

    public <V> Map<V, T> queryMap(Collection<? extends Serializable> idList, Function<? super T, V> mapper) {
        Collection<T> entityList = this.listByIds(idList);
        this.handleList(entityList, null);
        return entityList.stream().collect(Collectors.toMap(mapper, Function.identity()));
    }

    public <V> Map<V, T> queryMap(List<T> entityList, Function<? super T, V> mapper) {
        return entityList.stream().collect(Collectors.toMap(mapper, Function.identity()));
    }

    public <V> Map<V, List<T>> queryMapList(Wrapper<T> queryWrapper, Function<? super T, V> mapper) {
        List<T> entityList = this.list(queryWrapper);
        this.handleList(entityList, null);
        return entityList.stream().collect(Collectors.groupingBy(mapper));
    }

    public <V> Map<V, List<T>> queryMapList(Collection<? extends Serializable> idList, Function<? super T, ? extends V> mapper) {
        Collection<T> entityList = this.listByIds(idList);
        this.handleList(entityList, null);
        return entityList.stream().collect(Collectors.groupingBy(mapper));
    }

    public <V> Map<V, List<T>> queryMapList(List<T> entityList, Function<? super T, ? extends V> mapper) {
        return entityList.stream().collect(Collectors.groupingBy(mapper));
    }

    public <V> List<V> queryValueList(Wrapper<T> queryWrapper, Function<? super T, V> mapper) {
        return this.list(queryWrapper).stream().filter(Objects::nonNull).map(mapper).distinct().collect(Collectors.toList());
    }

    public <V> List<V> queryValueList(List<T> entityList, Function<? super T, V> mapper) {
        return entityList.stream().filter(Objects::nonNull).map(mapper).collect(Collectors.toList());
    }
}
