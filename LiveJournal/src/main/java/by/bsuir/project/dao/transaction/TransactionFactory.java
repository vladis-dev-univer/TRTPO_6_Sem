package by.bsuir.project.dao.transaction;

public interface TransactionFactory {
    Transaction createTransaction();

    void close();
}
