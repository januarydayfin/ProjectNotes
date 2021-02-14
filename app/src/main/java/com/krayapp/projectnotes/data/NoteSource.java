package com.krayapp.projectnotes.data;

public interface NoteSource {
    NoteSource init(NotesSourceResponse notesSourceResponse);
    NoteInfo getNoteInfo(int position);
    int size();
    void deleteNoteInfo(int position);
    void updateNoteInfo(int position, NoteInfo cardData);
    void addNoteInfo(NoteInfo noteInfo);
    void clearNoteInfo();
}
