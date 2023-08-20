package com.example.javarestcodingexercise.database;

import com.example.javarestcodingexercise.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepository extends JpaRepository<Account, Long> {

    @Modifying(clearAutomatically = true)
    @Query("update accounts a set a.balance = :balance where a.id = :id")
    void updateAccountBalance(@Param(value = "id") long id, @Param(value = "balance") double balance);
}
