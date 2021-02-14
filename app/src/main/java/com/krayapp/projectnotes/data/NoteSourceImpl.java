package com.krayapp.projectnotes.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NoteSourceImpl implements NoteSource {

    private List<NoteInfo> noteStorage;

    public NoteSourceImpl() {
        noteStorage = new ArrayList<>();
    }

    public NoteSource init(NotesSourceResponse notesSourceResponse) {
        noteStorage.add(new NoteInfo("Еда", "Надо приготовить покушоц", Calendar.getInstance().getTime()));
        noteStorage.add(new NoteInfo("Покупки", "Греча, Молоко, Мыло", Calendar.getInstance().getTime()));
        noteStorage.add(new NoteInfo("Дела", "Украсть у кошки еду", Calendar.getInstance().getTime()));

        if (notesSourceResponse != null) {
            notesSourceResponse.initialized(this);
        }
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
