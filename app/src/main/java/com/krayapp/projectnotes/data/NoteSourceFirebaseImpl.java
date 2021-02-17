package com.krayapp.projectnotes.data;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NoteSourceFirebaseImpl implements NoteSource {
    private static final String NOTES_COLLECTION = "notes";
    private static final String TAG = "[NoteSourceFirebaseImpl]";
    private FirebaseFirestore store = FirebaseFirestore.getInstance();
    private CollectionReference collection = store.collection(NOTES_COLLECTION);
    private List<NoteInfo> notesData = new ArrayList<>();

    @Override
    public NoteSource init(NotesSourceResponse notesSourceResponse) {
        collection.orderBy(NoteDataMapping.Fields.DATE, Query.Direction.DESCENDING).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        notesData = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> doc = document.getData();
                            String id = document.getId();
                            NoteInfo noteInfo = NoteDataMapping.toNoteInfo(id, doc);
                            notesData.add(noteInfo);
                        }
                        Log.d(TAG, "success " + notesData.size() + " qnt");
                        notesSourceResponse.initialized(NoteSourceFirebaseImpl.this);
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                })
                .addOnFailureListener(e -> Log.d(TAG, "get failed with ", e));
        return this;
    }

    @Override
    public NoteInfo getNoteInfo(int position) {
        return notesData.get(position);
    }

    @Override
    public int size() {
        if (notesData != null) {
            return notesData.size();
        } else {
            return 0;
        }
    }

    @Override
    public void deleteNoteInfo(int position) {
        collection.document(notesData.get(position).getId()).delete();
        notesData.remove(position);
    }

    @Override
    public void updateNoteInfo(int position, NoteInfo noteInfo) {
        String id = noteInfo.getId();
        collection.document(id).set(NoteDataMapping.toDocument(noteInfo));
    }

    @Override
    public void addNoteInfo(NoteInfo noteInfo) {
        collection.add(NoteDataMapping.toDocument(noteInfo)).addOnSuccessListener(documentReference -> noteInfo.setId(documentReference.getId()));
    }
    @Override
    public void clearNoteInfo() {
        for (NoteInfo noteInfo: notesData) {
            collection.document(noteInfo.getId()).delete();
        }
        notesData = new ArrayList<NoteInfo>();
    }
}
