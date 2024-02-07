package de.htw.product.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    @Value("add_product")
    public static String addProductQueue;

    @Value("remove_product")
    public static String removeProductQueue;

    @Value("product_exchange")
    public static String exchange;

    @Value("update_product")
    public static String updateProductQueue;

    @Value("add_product_to_basket")
    public static String addProductToBasket;

    @Value("remove_product_from_basket")
    public static String removeProductFromBasket;

    @Value("update_product_basket")
    public static String updateProductBasket;

    @Bean
    public Queue addProductQueue(){
        return new Queue(addProductQueue);
    }

    @Bean
    public Queue removeProductQueue(){
        return new Queue(removeProductQueue);
    }

    @Bean
    public Queue updateProductQueue(){
        return new Queue(updateProductQueue);
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding addItemBinding(Queue addProductQueue, TopicExchange exchange){
        return BindingBuilder
                .bind(addProductQueue).to(exchange).with(addProductToBasket);
    }
    @Bean
    public Binding removeItemBinding(Queue removeProductQueue, TopicExchange exchange){
        return BindingBuilder
                .bind(removeProductQueue).to(exchange).with(removeProductFromBasket);
    }
    @Bean
    public Binding updateItemBinding(Queue updateProductQueue, TopicExchange exchange){
        return BindingBuilder
                .bind(updateProductQueue).to(exchange).with(updateProductBasket);
    }
    @Bean
    public MessageConverter productMessageJsonConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate productTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(productMessageJsonConverter());
        return template;
    }


}