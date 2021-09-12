package com.emerge.emart.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emerge.emart.user.entity.Wishlist;
@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, String> {

    
}
