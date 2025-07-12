package com.emirkoral.deliveryapp.payment;

import com.emirkoral.deliveryapp.exception.ResourceNotFoundException;
import com.emirkoral.deliveryapp.order.OrderRepository;
import com.emirkoral.deliveryapp.payment.dto.PaymentRequest;
import com.emirkoral.deliveryapp.payment.dto.PaymentResponse;
import com.emirkoral.deliveryapp.payment.mapper.PaymentMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final OrderRepository orderRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentMapper paymentMapper, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.orderRepository = orderRepository;
    }

    @Override
    public List<PaymentResponse> findAllPayments() {
        return paymentRepository.findAll().stream()
                .map(paymentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentResponse findPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
        return paymentMapper.toResponse(payment);
    }

    @Override
    public PaymentResponse findPaymentByOrderId(Long orderId) {
        Payment payment = paymentRepository.findOrderById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found for order id: " + orderId));
        return paymentMapper.toResponse(payment);
    }

    @Override
    public PaymentResponse savePayment(PaymentRequest paymentRequest) {
        Payment payment = buildPaymentFromRequest(paymentRequest);
        Payment saved = paymentRepository.save(payment);
        return paymentMapper.toResponse(saved);
    }

    @Override
    public PaymentResponse updatePayment(Long id, PaymentRequest request) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
        
        paymentMapper.updateEntityFromRequest(request, payment);
        payment.setOrder(orderRepository.findById(request.orderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + request.orderId())));
        
        Payment updated = paymentRepository.save(payment);
        return paymentMapper.toResponse(updated);
    }

    @Override
    public PaymentResponse deletePaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
        paymentRepository.deleteById(id);
        return paymentMapper.toResponse(payment);
    }

    @Override
    public List<PaymentResponse> searchPayments(Double amount, Payment.PaymentMethod paymentMethod, Payment.Status status, String transactionId, Long orderId) {
        return paymentRepository.findAll().stream()
                .filter(payment -> amount == null || payment.getAmount().doubleValue() == amount)
                .filter(payment -> paymentMethod == null || payment.getPaymentMethod() == paymentMethod)
                .filter(payment -> status == null || payment.getStatus() == status)
                .filter(payment -> transactionId == null || (payment.getTransactionId() != null && payment.getTransactionId().contains(transactionId)))
                .filter(payment -> orderId == null || (payment.getOrder() != null && payment.getOrder().getId().equals(orderId)))
                .map(paymentMapper::toResponse)
                .collect(Collectors.toList());
    }

    private Payment buildPaymentFromRequest(PaymentRequest request) {
        Payment payment = paymentMapper.toEntity(request);
        payment.setOrder(orderRepository.findById(request.orderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + request.orderId())));
        payment.setStatus(Payment.Status.PENDING);
        payment.setPaymentDate(java.time.LocalDateTime.now());
        return payment;
    }
}
