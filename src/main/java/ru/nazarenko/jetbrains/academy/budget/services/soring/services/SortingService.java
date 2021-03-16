package ru.nazarenko.jetbrains.academy.budget.services.soring.services;

import ru.nazarenko.jetbrains.academy.budget.domain.Purchase;

import java.util.ArrayList;

public interface SortingService {
    ArrayList<Purchase> sort(ArrayList<Purchase> purchases);

    default void print(ArrayList<Purchase> purchases) throws SortingServiceException { }
}

