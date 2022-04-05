package com.example.book.domain.converter;

import com.example.book.repository.dt.BookStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class BookStatusConverter implements AttributeConverter<BookStatus,Integer> {
    @Override
    public Integer convertToDatabaseColumn(BookStatus attribute) {
        //return attribute.getCode();

        return null;
    }

    @Override
    public BookStatus convertToEntityAttribute(Integer dbData) {
        return dbData != null ? new BookStatus(dbData) :null;
    }
}
