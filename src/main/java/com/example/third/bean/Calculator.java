package com.example.third.bean;

import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.ManagedBean;

/**
 * @author Natalia Nikonova
 */
@ManagedBean
public class Calculator {
    @Getter
    @Setter
    private int firstNumber = 0;
    @Getter
    @Setter
    private int secondNumber = 0;

    @Getter
    @Setter
    private int result = 0;

    public void add() {
        result = firstNumber + secondNumber;
    }
}
