package com.mahar.busxhacktiv.bus;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mahar.busxhacktiv.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    ActivityResultLauncher<Intent> activityResultLauncherForLocation;
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

        RegisterActivityForLocation();
        where=binding.where;
        from=binding.from;

        where.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),ListProvinsi.class);
                activityResultLauncherForLocation.launch(i);
            }
        });
        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),ListProvinsi.class);
                activityResultLauncherForLocation.launch(i);
            }
        });
    }
    public void RegisterActivityForLocation(){
        activityResultLauncherForLocation=registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                int ResultCode=result.getResultCode();
                Intent data=result.getData();

                if(ResultCode==RESULT_OK && data !=null){
                    where.setText(data.getStringExtra("arrival"));
                    from.setText(data.getStringExtra("departure"));
                }
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


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