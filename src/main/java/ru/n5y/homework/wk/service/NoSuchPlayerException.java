package ru.n5y.homework.wk.service;

public class NoSuchPlayerException extends RuntimeException {
  public NoSuchPlayerException(String message) {
    super(message);
  }
}
