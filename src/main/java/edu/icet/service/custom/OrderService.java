package edu.icet.service.custom;

import edu.icet.dto.OrderDetailDto;
import edu.icet.dto.OrderDto;
import edu.icet.service.SuperService;

import java.util.List;

public interface OrderService extends SuperService {
    String getLastOrderID();
    boolean placeOrder(OrderDto orderDto);
    boolean saveOrderDetails(List<OrderDetailDto> orderDetailDtoList);
}
