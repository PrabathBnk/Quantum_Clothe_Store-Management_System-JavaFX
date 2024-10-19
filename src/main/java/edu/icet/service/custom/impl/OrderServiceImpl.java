package edu.icet.service.custom.impl;

import edu.icet.dto.OrderDetailDto;
import edu.icet.dto.OrderDetailTblDto;
import edu.icet.dto.OrderDto;
import edu.icet.dto.ProductDto;
import edu.icet.entity.OrderDetail;
import edu.icet.entity.Orders;
import edu.icet.repository.DaoFactory;
import edu.icet.repository.custom.OrderDao;
import edu.icet.repository.custom.OrderDetailDao;
import edu.icet.repository.custom.ProductDao;
import edu.icet.repository.custom.UserLogDao;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.OrderService;
import edu.icet.service.custom.PaymentTypeService;
import edu.icet.service.custom.ProductService;
import edu.icet.util.DaoType;
import edu.icet.util.ServiceType;
import org.modelmapper.ModelMapper;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    OrderDao orderDao = DaoFactory.getInstance().getDao(DaoType.ORDERS);

    @Override
    public String getLastOrderID() {
        return orderDao.getLastId();
    }

    @Override
    public boolean placeOrder(OrderDto orderDto) {
        Orders orders = mapOrderDtoToOrders(orderDto);

        UserLogDao userLogDao = DaoFactory.getInstance().getDao(DaoType.USERLOG);
        orders.setUserId(userLogDao.getLastLogUserId());

        return orderDao.save(orders);
    }

    @Override
    public boolean saveOrderDetails(List<OrderDetailDto> orderDetailDtoList) {
        ProductDao productDao = DaoFactory.getInstance().getDao(DaoType.PRODUCT);
        OrderDetailDao orderDetailDao = DaoFactory.getInstance().getDao(DaoType.ORDER_DETAIL);
        for (OrderDetailDto orderDetailDto: orderDetailDtoList) {
            if (!orderDetailDao.save(new ModelMapper().map(orderDetailDto, OrderDetail.class))) return false;
            if (!productDao.updateQuantity(orderDetailDto.getProductID(), orderDetailDto.getQty())) return false;
        }

        return true;
    }

    @Override
    public List<OrderDto> getAllOrders() {
        PaymentTypeService paymentTypeService = ServiceFactory.getInstance().getService(ServiceType.PAYMENT_TYPE);

        List<Orders> ordersList = orderDao.getAll();
        List<OrderDto> orderDtoList = new ArrayList<>();
        for (int i = 0; i < ordersList.size(); i++) {
            orderDtoList.add(new ModelMapper().map(ordersList.get(i), OrderDto.class));
            orderDtoList.get(i).setOrderDate(ordersList.get(i).getOrderDate().toString());
            orderDtoList.get(i).setReturnDate(null!=ordersList.get(i).getReturnDate() ? ordersList.get(i).getReturnDate().toString(): null);
            orderDtoList.get(i).setNum(i);
            orderDtoList.get(i).setPaymentType(paymentTypeService.getPaymentTypeName(ordersList.get(i).getPaymentType()));
        }

        return orderDtoList;
    }

    @Override
    public List<OrderDetailTblDto> getOrderDetails(String id) {
        OrderDetailDao orderDetailDao = DaoFactory.getInstance().getDao(DaoType.ORDER_DETAIL);
        ProductService productService = ServiceFactory.getInstance().getService(ServiceType.PRODUCT);
        List<OrderDetail> orderDetailList = orderDetailDao.getAll();

        List<OrderDetailTblDto> orderDetailTblDtoList = new ArrayList<>();
        for (int i = 0, j = 0; i < orderDetailList.size(); i++) {
            if (orderDetailList.get(i).getOrderID().equals(id)) {
                ProductDto productDto = productService.getProduct(orderDetailList.get(i).getProductID());
                orderDetailTblDtoList.add(new OrderDetailTblDto(
                        ++j,
                        productDto.getProductID(),
                        productDto.getName(),
                        productDto.getPrice(),
                        orderDetailList.get(i).getQty(),
                        productDto.getPrice() * orderDetailList.get(i).getQty()
                ));
            }
        }

        return orderDetailTblDtoList;
    }

    @Override
    public boolean updateOrder(OrderDto orderDto) {

        return orderDao.update(mapOrderDtoToOrders(orderDto));
    }

    @Override
    public boolean deleteOrder(OrderDto orderDto) {

        return orderDao.delete(new ModelMapper().map(orderDto, Orders.class));
    }

    @Override
    public boolean updateOrderDetails(List<OrderDetailDto> orderDetailDtoList) {
        OrderDetailDao orderDetailDao = DaoFactory.getInstance().getDao(DaoType.ORDER_DETAIL);
        orderDetailDao.delete(new OrderDetail(orderDetailDtoList.getFirst().getOrderID(), null, null));
        ProductDao productDao = DaoFactory.getInstance().getDao(DaoType.PRODUCT);
        for (OrderDetailDto orderDetailDto: orderDetailDtoList) {
            if (!orderDetailDao.save(new ModelMapper().map(orderDetailDto, OrderDetail.class))) return false;
            if (!productDao.updateQuantity(orderDetailDto.getProductID(), orderDetailDto.getQty())) return false;
        }

        return true;
    }

    private Orders mapOrderDtoToOrders(OrderDto orderDto){
        PaymentTypeService paymentTypeService = ServiceFactory.getInstance().getService(ServiceType.PAYMENT_TYPE);

        Orders order = new ModelMapper().map(orderDto, Orders.class);
        order.setPaymentType(paymentTypeService.getPaymentTypeId(orderDto.getPaymentType()));
        order.setOrderDate(LocalDate.now());
        order.setReturnDate(null!=orderDto.getReturnDate() ? LocalDate.parse(orderDto.getReturnDate()): null);

        return order;
    }
}
