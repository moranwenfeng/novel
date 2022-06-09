package io.github.mo.novel.manager.mq;

import io.github.mo.novel.core.common.constant.CommonConsts;
import io.github.mo.novel.core.constant.AmqpConsts;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Objects;

/**
 * Amqp消息管理类
 *
 * @author mo
 * @date 2022/6/4 14:46
 */
@Component
@RequiredArgsConstructor
public class AmqpMsgManager {

    private final AmqpTemplate amqpTemplate;

    @Value("${spring.amqp.enable}")
    private String enableAmqp;

    /**
     * 发送小说信息改编消息
     */
    public void sendBookChangeMsg(Long bookId) {
        if(Objects.equals(enableAmqp, CommonConsts.TRUE)) {
            sendAmqpMessage(amqpTemplate, AmqpConsts.BookChangeMq.EXCHANGE_NAME, null , bookId);
        }
    }

    private void sendAmqpMessage(AmqpTemplate amqpTemplate, String exchange, String routingKey, Object message) {
        // 如果在事务中则在事务执行完成后再发送，否则可以直接发送
        if(TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    amqpTemplate.convertAndSend(exchange, routingKey, message);
                }
            });
            return;
        }
        amqpTemplate.convertAndSend(exchange,routingKey,message);

    }



}
