package io.ruiz.modules.base.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import io.ruiz.common.utils.PageUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public abstract interface BaseService<T> extends IService<T> {

    PageUtils queryPage(Map<String, Object> params);

    List<T> queryList(Map<String, Object> params);

    <V> Map<V, T> queryMap(Wrapper<T> queryWrapper, Function<? super T, V> mapper);

    <V> Map<V, T> queryMap(Collection<? extends Serializable> idList, Function<? super T, V> mapper);

    <V> Map<V, T> queryMap(List<T> entityList, Function<? super T, V> mapper);

    <V> Map<V, List<T>> queryMapList(Wrapper<T> queryWrapper, Function<? super T, V> mapper);

    <V> Map<V, List<T>> queryMapList(Collection<? extends Serializable> idList, Function<? super T, ? extends V> mapper);

    <V> Map<V, List<T>> queryMapList(List<T> entityList, Function<? super T, ? extends V> mapper);

    <V> List<V> queryValueList(List<T> entityList, Function<? super T, V> mapper);
}
