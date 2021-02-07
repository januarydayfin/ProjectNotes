package com.krayapp.projectnotes.data;

import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

public class NoteSourceImpl implements NoteSource {

    private List<NoteInfo> noteStorage;

    public NoteSourceImpl() {
        noteStorage = new ArrayList<>();
    }

    public NoteSourceImpl init() {
        noteStorage.add(new NoteInfo("Еда", "Надо приготовить покушоц", "12.12.2012"));
        noteStorage.add(new NoteInfo("Покупки", "Греча, Молоко, Мыло", "15.12.2012"));
        noteStorage.add(new NoteInfo("Дела", "Украсть у кошки еду", "13.12.2012"));
        return this;
    }

    @Override
    public NoteInfo getNoteInfo(int position) {
        return noteStorage.get(position);
    }

    @Override
    public int size() {
        return noteStorage.size();
    }

    @Override
    public void deleteNoteInfo(int position) {
        noteStorage.remove(position);
    }

    @Override
    public void updateNoteInfo(int position, NoteInfo noteInfo) {
        noteStorage.set(position, noteInfo);
    }

    @Override
    public void addNoteInfo(NoteInfo noteInfo) {
        noteStorage.add(noteInfo);
    }
    @Override
    public void clearNoteInfo() {
        noteStorage.clear();
    }
}
