package com.spring.boot.restaurant.mappers;

import com.spring.boot.restaurant.controllers.vm.ResponseOrderVm;
import com.spring.boot.restaurant.models.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {

    OrderMapper ORDER_MAPPER = Mappers.getMapper(OrderMapper.class);

    ResponseOrderVm toResponseOrderVm(Order order);
}