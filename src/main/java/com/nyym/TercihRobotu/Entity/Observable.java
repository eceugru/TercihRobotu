package com.nyym.TercihRobotu.Entity;
import com.nyym.TercihRobotu.Service.Observer;

public interface Observable {
        void addObserver(Observer observer);
        void removeObserver(Observer observer);
        void notifyObservers();


}
