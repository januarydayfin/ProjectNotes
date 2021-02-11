package com.krayapp.projectnotes.observer;

import com.krayapp.projectnotes.data.NoteInfo;

public interface Observer {
    void updateNoteInfo(NoteInfo noteInfo);
}
