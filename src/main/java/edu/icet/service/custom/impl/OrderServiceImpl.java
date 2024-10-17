package edu.icet.service.custom.impl;

import edu.icet.dto.OrderDetailDto;
import edu.icet.dto.OrderDto;
import edu.icet.entity.OrderDetail;
import edu.icet.entity.Orders;
import edu.icet.repository.DaoFactory;
import edu.icet.repository.custom.OrderDao;
import edu.icet.repository.custom.OrderDetailDao;
import edu.icet.repository.custom.UserLogDao;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.OrderService;
import edu.icet.service.custom.PaymentTypeService;
import edu.icet.util.DaoType;
import edu.icet.util.ServiceType;
import org.modelmapper.ModelMapper;


import java.util.List;

public class OrderServiceImpl implements OrderService {

    OrderDao orderDao = DaoFactory.getInstance().getDao(DaoType.ORDERS);

    @Override
    public String getLastOrderID() {
        return orderDao.getLastId();
    }

    @Override
    public boolean placeOrder(OrderDto orderDto) {
        PaymentTypeService paymentTypeService = ServiceFactory.getInstance().getService(ServiceType.PAYMENT_TYPE);

        Orders orders = new ModelMapper().map(orderDto, Orders.class);
        orders.setPaymentType(paymentTypeService.getPaymentTypeId(orderDto.getPaymentType()));

        UserLogDao userLogDao = DaoFactory.getInstance().getDao(DaoType.USERLOG);
        orders.setUserId(userLogDao.getLastLogUserId());

        return orderDao.save(orders);
    }

    @Override
    public boolean saveOrderDetails(List<OrderDetailDto> orderDetailDtoList) {

        OrderDetailDao orderDetailDao = DaoFactory.getInstance().getDao(DaoType.ORDER_DETAIL);
        for (OrderDetailDto orderDetailDto: orderDetailDtoList) {
            if (!orderDetailDao.save(new ModelMapper().map(orderDetailDto, OrderDetail.class))) return false;
        }

        return true;
    }
}
