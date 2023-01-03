package com.kharim.orderservice.services;

import com.kharim.orderservice.dto.InventoryResponse;
import com.kharim.orderservice.dto.OrderRequest;
import com.kharim.orderservice.dto.OrderedItemsDto;
import com.kharim.orderservice.models.Order;
import com.kharim.orderservice.models.OrderedItems;
import com.kharim.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public void placeOrder(OrderRequest orderRequest) {
        Order order1 = new Order();
        order1.setOrderNumber(UUID.randomUUID().toString());

        List<OrderedItems> orderedItemsList = orderRequest.getOrderedItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        order1.setOrderedItemsList(orderedItemsList);

        List<String> skuCodes = order1.getOrderedItemsList().stream()
                .map(OrderedItems::getSkuCode)
                .collect(Collectors.toList());

//        Call Inventory service to check availability of a product and place an order.
        InventoryResponse[] inventoryResponses = webClient.get()
                .uri("/http://127.0.0.1:8875", uriBuilder -> uriBuilder
                        .queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        try {

            if (null != inventoryResponses) {
                boolean allProductsInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::getIsInStock);

                if (allProductsInStock) {
                    orderRepository.save(order1);
                } else {
                    throw new IllegalArgumentException("Product not in stock");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        orderRepository.save(order1);
    }

    private OrderedItems mapToDto(OrderedItemsDto orderedItemsDto) {
        OrderedItems orderedItems2 = new OrderedItems();
        orderedItems2.setPrice(orderedItemsDto.getPrice());
        orderedItems2.setQuantity(orderedItemsDto.getQuantity());
        orderedItems2.setSkuCode(orderedItemsDto.getSkuCode());

        return orderedItems2;
    }
}
