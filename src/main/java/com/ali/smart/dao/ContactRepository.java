package com.ali.smart.dao;

import com.ali.smart.entities.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ContactRepository extends JpaRepository<Contact,Integer> {
//pagination

    // current page-page
    //contact per page -5
    @Query("from Contact as c where c.user.id =:userId")
    public Page<Contact> findContactsByUserId(@Param("userId") int userId, Pageable pageable);
}
