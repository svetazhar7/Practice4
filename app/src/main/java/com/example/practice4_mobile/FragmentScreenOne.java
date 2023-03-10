package com.example.practice4_mobile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.practice4_mobile.databinding.Screen1Binding;

public class FragmentScreenOne extends Fragment {
    Screen1Binding binding;
    public FragmentScreenOne()
    {
        super(R.layout.screen1);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = Screen1Binding.inflate(inflater, container, false);

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//для перехода на второй экран
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction().
                        setReorderingAllowed(true);
                ft.replace(R.id.fragment_view, new FragmentScreenTwo());
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//для перехода на третий экран
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction().
                        setReorderingAllowed(true);
                ft.replace(R.id.fragment_view, new FragmentScreenThree());
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        return binding.getRoot();
    }


}
