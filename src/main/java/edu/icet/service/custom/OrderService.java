package edu.icet.service.custom;

import edu.icet.dto.OrderDetailDto;
import edu.icet.dto.OrderDetailTblDto;
import edu.icet.dto.OrderDto;
import edu.icet.service.SuperService;

import java.util.List;

public interface OrderService extends SuperService {
    String getLastOrderID();
    boolean placeOrder(OrderDto orderDto);
    boolean saveOrderDetails(List<OrderDetailDto> orderDetailDtoList);
    List<OrderDto> getAllOrders();
    List<OrderDetailTblDto> getOrderDetails(String id);
    boolean updateOrder(OrderDto orderDto);
    boolean deleteOrder(OrderDto orderDto);
    boolean updateOrderDetails(List<OrderDetailDto> orderDetailDtoList);
}
