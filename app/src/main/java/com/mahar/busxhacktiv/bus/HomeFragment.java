package com.mahar.busxhacktiv.bus;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mahar.busxhacktiv.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    Button btn;
    TextView date,passagers,where,from;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        where=binding.where;
        from=binding.from;

        where.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),ListProvinsi.class);
                i.putExtra("where","-");
                startActivity(i);
            }
        });
        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),ListProvinsi.class);
                i.putExtra("from","-");
                startActivity(i);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onStart() {
        super.onStart();
//        TODO: SALAH mungkin pakai acitivyforlaucher
//        Intent i=getActivity().getIntent();
//        if(i!=null){
//            if(getActivity().getIntent().getStringExtra("where")!=null && !getActivity().getIntent().getStringExtra("where").equals("-")){
//                where.setText(getActivity().getIntent().getStringExtra("where"));
//            } else if(getActivity().getIntent().getStringExtra("from")!=null && !getActivity().getIntent().getStringExtra("from").equals("-")){
//                from.setText(getActivity().getIntent().getStringExtra("from"));
//            }
//        }

    }
}