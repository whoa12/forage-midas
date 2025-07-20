package com.jpmc.midascore.foundation;

import com.jpmc.midascore.entity.UserRecord;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class TransactionRecord {
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    private UserRecord sender;
    @ManyToOne
    private UserRecord reciept;
    private float amount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserRecord getSender() {
        return sender;
    }

    public void setSender(UserRecord sender) {
        this.sender = sender;
    }

    public UserRecord getReciept() {
        return reciept;
    }

    public void setReciept(UserRecord reciept) {
        this.reciept = reciept;
    }





    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }



    public TransactionRecord(UserRecord sender, UserRecord recipient, float amount) {
    }

    @Override
    public String toString() {
        return "TransactionRecord{" +
                "id=" + id +
                ", sender=" + sender +
                ", reciept=" + reciept +
                ", amount=" + amount +
                '}';
    }


}
