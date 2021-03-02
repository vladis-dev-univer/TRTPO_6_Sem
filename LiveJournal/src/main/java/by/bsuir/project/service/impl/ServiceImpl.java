package by.bsuir.project.service.impl;

import by.bsuir.project.dao.transaction.Transaction;

public abstract class ServiceImpl {
    protected Transaction transaction;

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
