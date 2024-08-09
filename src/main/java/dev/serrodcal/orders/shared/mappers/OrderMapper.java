package dev.serrodcal.orders.shared.mappers;

import dev.serrodcal.orders.application.dtos.OrderDTO;
import dev.serrodcal.orders.domain.Order;
import dev.serrodcal.orders.infrastructure.OrderDBO;
import dev.serrodcal.orders.infrastructure.dtos.OrderResponse;
import dev.serrodcal.orders.infrastructure.dtos.UpdateOrderRequest;
import dev.serrodcal.shared.mappers.QuarkusMappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = QuarkusMappingConfig.class)
public interface OrderMapper {

    OrderDTO mapUpdateOrderRequestToOrderDTO(UpdateOrderRequest updateOrderRequest);

    Order mapOrderDTOToOrder(OrderDTO orderDTO);

    OrderDTO mapOrderToOrderDTO(Order order);

    OrderResponse mapOrderDTOToOrderResponse(OrderDTO orderDTO);

    @Mapping(source = "metadata.createdAt", target = "createdAt")
    @Mapping(source = "metadata.updatedAt", target = "updatedAt")
    Order mapOrderDBOToOrder(OrderDBO dbo);

}
