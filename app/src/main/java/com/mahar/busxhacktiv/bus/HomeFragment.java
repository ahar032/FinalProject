package com.mahar.busxhacktiv.bus;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mahar.busxhacktiv.R;
import com.mahar.busxhacktiv.databinding.FragmentHomeBinding;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

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
        date=binding.date;
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUpCalender(view);
            }
        });
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
    public void popUpCalender(View view){
        LayoutInflater inflater=(LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
        View FilterPopUp=inflater.inflate(R.layout.datepicker,null);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(FilterPopUp, width, height, focusable);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);

        DatePicker datePicker=FilterPopUp.findViewById(R.id.datepicker);
        TextView ok=FilterPopUp.findViewById(R.id.ok);
        Calendar calendar = Calendar.getInstance();
        datePicker.setMinDate(calendar.getTimeInMillis());

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int day=datePicker.getDayOfMonth();
                int month=datePicker.getMonth();
                int   year = datePicker.getYear();
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                String formatedDate = sdf.format(calendar.getTime());

                date.setText(formatedDate);
                popupWindow.dismiss();
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

}