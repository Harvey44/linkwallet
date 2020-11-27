package com.mobile.linkwallet;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialogFragment;

public class Bottomsheet extends RoundedBottomSheetDialogFragment {
    private ItemClickListener mListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.validatephrase, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setupDialog(@NonNull Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(),R.layout.validatephrase, null);
        dialog.setContentView(contentView);
        //  RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ItemClickListener) {
            mListener = (ItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ItemClickListener");
        }
    }



    public interface ItemClickListener {
        void onItemClick(String item);
    }
}
