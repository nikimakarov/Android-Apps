package ru.surf.nikita_makarov.jotter;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Button;

public class RemovalConfirmation extends DialogFragment{

    public interface RemovalConfirmationListener {
        void onDialogPositiveClick(DialogFragment dialog);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    RemovalConfirmationListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (RemovalConfirmationListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.confirmation)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogPositiveClick(RemovalConfirmation.this);
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogNegativeClick(RemovalConfirmation.this);
                    }
                });


        final AlertDialog dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                Button negativeButton = ((AlertDialog)dialog).getButton(DialogInterface.BUTTON_NEGATIVE);
                Button positiveButton = ((AlertDialog)dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                final Drawable negativeButtonDrawable = ContextCompat.getDrawable(getActivity(),R.color.pink);
                final Drawable positiveButtonDrawable = ContextCompat.getDrawable(getActivity(),R.color.green);
                negativeButton.setBackground(negativeButtonDrawable);
                negativeButton.setTextColor(Color.WHITE);
                positiveButton.setBackground(positiveButtonDrawable);
                positiveButton.setTextColor(Color.WHITE);
                negativeButton.invalidate();
                positiveButton.invalidate();
            }
        });

        return dialog;

    }
}