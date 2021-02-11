package com.krayapp.projectnotes.observer;

import com.krayapp.projectnotes.data.NoteInfo;

import java.util.ArrayList;
import java.util.List;

public class Publisher {
    private List<Observer> observers;   // Все обозреватели

    public Publisher() {
        observers = new ArrayList<>();
    }

    // Подписать
    public void subscribe(Observer observer) {
        observers.add(observer);
    }
    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    // Разослать событие
    public void notifySingle(NoteInfo noteInfo) {
        for (Observer observer : observers) {
            observer.updateNoteInfo(noteInfo);
            unsubscribe(observer);
        }
    }

}

