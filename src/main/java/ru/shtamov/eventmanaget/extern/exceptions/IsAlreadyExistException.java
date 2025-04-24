package ru.shtamov.eventmanaget.extern.exceptions;


public class IsAlreadyExistException extends RuntimeException{
    public IsAlreadyExistException(String message) {
        super(message);
    }
}
