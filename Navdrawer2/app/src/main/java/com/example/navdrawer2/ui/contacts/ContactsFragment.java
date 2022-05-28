package com.example.navdrawer2.ui.contacts;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.navdrawer2.R;
import com.example.navdrawer2.databinding.FragmentContactsBinding;
import com.example.navdrawer2.nextactivity;

public class ContactsFragment extends Fragment {
    Activity context;


    private FragmentContactsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ContactsViewModel ContactsViewModel =
                new ViewModelProvider(this).get(ContactsViewModel.class);

        context = getActivity();

        binding = FragmentContactsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    public void onStart(){
        super.onStart();
        Button btn = (Button) context.findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, nextactivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}