package owlvernyte.springfood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import owlvernyte.springfood.entity.OrderDetail;
import owlvernyte.springfood.repository.IOrderDetailRepository;

@Service
public class OrderDetailService {
    @Autowired
    private IOrderDetailRepository iOrderDetailRepository;

    public void addOrderDetail(OrderDetail orderDetail){
        iOrderDetailRepository.save(orderDetail);
    }
}
