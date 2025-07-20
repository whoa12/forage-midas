package com.jpmc.midascore.service;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.TransactionRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRepository;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;


@Service
public class KafkaService {
    RestTemplate restTemplate = new RestTemplate();

    String resourceUrl = "http://localhost:8080/incentive";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRecordRepository;

    @KafkaListener(
            topics = "${general.kafka-topic}",
            groupId = "midas-core-group",
            containerFactory = "factory"
    )
    public void publish(Transaction transaction) {
        long senderId = transaction.getSenderId();
        long recipientId = transaction.getRecipientId();
        float amount = transaction.getAmount();


        UserRecord sender = userRepository.findById(senderId);
        UserRecord recipient = userRepository.findById(recipientId);

        if (sender == null || recipient == null) return;
        if (sender.getBalance() < amount) return;

        sender.setBalance(sender.getBalance() - amount);

        Incentive incentive = getIncentive(transaction);
        if (incentive == null) return;

        float incentiveAmount = incentive.getAmount();

        recipient.setBalance(recipient.getBalance() + amount + incentiveAmount);

        userRepository.save(sender);
        userRepository.save(recipient);

        TransactionRecord transactionRecord = new TransactionRecord(sender, recipient, amount + incentiveAmount);
        transactionRecordRepository.save(transactionRecord);

    }
    public Incentive getIncentive(Transaction transaction) {
        return restTemplate.postForObject(resourceUrl, transaction, Incentive.class);
    }
}
