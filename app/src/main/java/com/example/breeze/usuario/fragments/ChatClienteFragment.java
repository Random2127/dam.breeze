package com.example.breeze.usuario.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.breeze.R;
import com.example.breeze.Utils;

public class ChatClienteFragment extends Fragment {

    public ChatClienteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_cliente, container, false);
        Utils.cambioSizeTextViews(view, getContext());
        return view;
    }
}