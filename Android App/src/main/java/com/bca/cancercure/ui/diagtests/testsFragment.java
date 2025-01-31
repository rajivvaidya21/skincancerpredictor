package com.bca.cancercure.ui.diagtests;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bca.cancercure.R;

import java.io.InputStream;

public class testsFragment extends Fragment implements View.OnClickListener {
    private testsViewModel testsViewModel;
    Button mriBtn, ctBtn, xrayBtn ;
    TextView testsText;
    protected View mView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
                testsViewModel =
                ViewModelProviders.of(this).get(testsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tests, container, false);
      /*  final TextView textView = root.findViewById(R.id.text_gallery);
        testsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        testsText = (TextView) root.findViewById(R.id.testTextView);
        //testsText.setText(testtext);

        try {
            Resources res = getResources(); InputStream in_s = res.openRawResource(R.raw.teststext);
            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            testsText.setText(new String(b));
        } catch (Exception e) {
            testsText.setText("Error: can't show.");
        }
        mriBtn= root.findViewById(R.id.mriBTN);
        //xrayBtn = root.findViewById(R.id.xrayBtn);
        ctBtn = root.findViewById(R.id.ctBtn);
        mriBtn.setOnClickListener(this);
        //xrayBtn.setOnClickListener(this);
        ctBtn.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View v) {
        char id = 'd';
        System.out.println(v.getId());
        switch(v.getId()){
            case R.id.mriBTN: id='m';
            break;
            case R.id.ctBtn: id='c';
            break;
            default:
        }
        Intent gen = new Intent(getActivity(), mri_activity.class);
        gen.putExtra("id", id);
        getActivity().startActivity(gen);
    }
}