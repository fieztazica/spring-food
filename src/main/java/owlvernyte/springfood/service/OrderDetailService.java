package owlvernyte.springfood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import owlvernyte.springfood.entity.OrderDetail;
import owlvernyte.springfood.repository.IOrderDetailRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailService {
    @Autowired
    private IOrderDetailRepository orderDetailRepository;

    public void addOrderDetail(OrderDetail orderDetail){
        orderDetailRepository.save(orderDetail);
    }

    public void deleteOrderDetail(Long id){
        orderDetailRepository.deleteById(id);
    }

    public List<OrderDetail> getOrderDetailByOrderId(Long id){
        List<OrderDetail> list = orderDetailRepository.findAll();
        list.removeIf(orderDetail -> !orderDetail.getOrder().getId().equals(id));
        return list;
    }
}
