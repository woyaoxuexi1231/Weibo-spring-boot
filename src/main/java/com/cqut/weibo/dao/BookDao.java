package com.cqut.weibo.dao;

import com.cqut.weibo.pojo.Book;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
@CacheConfig(cacheNames = {"memberService"})
public class BookDao {

    @Cacheable
    public Book getBookById(Integer id) {
        System.out.println("getBookById");
        Book book = new Book();
        book.setId(id);
        book.setName("三國演義");
        book.setAuthor("羅貫中");
        return book;
    }
}
