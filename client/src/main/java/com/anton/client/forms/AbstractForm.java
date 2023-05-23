package com.anton.client.forms;


import com.anton.common.exceptions.InvalidFormException;

public abstract class AbstractForm<T> {
    public abstract T build() throws InvalidFormException;
}
