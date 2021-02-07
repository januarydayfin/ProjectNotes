package com.krayapp.projectnotes.data;

public interface NoteSource {
    NoteInfo getNoteInfo(int position);
    int size();
    void deleteNoteInfo(int position);
    void updateNoteInfo(int position, NoteInfo cardData);
    void addNoteInfo(NoteInfo noteInfo);
    void clearNoteInfo();
}
