package com.krayapp.projectnotes.data;

import com.google.firebase.Timestamp;

import java.util.HashMap;
import java.util.Map;

public class NoteDataMapping {
    public static class Fields {
        public final static String TITLE = "title";
        public final static String DESCRIPTION = "description";
        public final static String DATE = "date";
    }

    public static NoteInfo toNoteInfo(String id, Map<String, Object> doc) {
        Timestamp timeStamp = (Timestamp)doc.get(Fields.DATE);
        NoteInfo answer = new NoteInfo((String) doc.get(Fields.TITLE),
                (String) doc.get(Fields.DESCRIPTION),
                timeStamp.toDate());
        answer.setId(id);
        return answer;
    }
    public static Map<String, Object> toDocument(NoteInfo noteInfo){
        Map<String, Object> answer = new HashMap<>();
        answer.put(Fields.TITLE, noteInfo.getTitle());
        answer.put(Fields.DESCRIPTION, noteInfo.getDescription());
        answer.put(Fields.DATE, noteInfo.getDate());
        return answer;
    }



}