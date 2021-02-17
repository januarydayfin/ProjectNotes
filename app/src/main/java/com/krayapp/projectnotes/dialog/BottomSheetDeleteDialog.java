package com.krayapp.projectnotes.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.krayapp.projectnotes.R;

public class BottomSheetDeleteDialog extends BottomSheetDialogFragment {
    private OnDialogListener onDialogListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.delete_dialog_bottom_fragment, container, false);

        view.findViewById(R.id.confirm_delete).setOnClickListener( v -> {
            if (onDialogListener != null) {
                onDialogListener.onDialogOk();
                dismiss();
            }
        });
        view.findViewById(R.id.decline_delete).setOnClickListener(v -> {
            dismiss();
        });
        return view;
    }

    public void setOnDialogListener(OnDialogListener onDialogListener) {
        this.onDialogListener = onDialogListener;
    }
}
