package com.mahar.busxhacktiv.bus;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mahar.busxhacktiv.adapter.OrderAdapter;
import com.mahar.busxhacktiv.databinding.FragmentDashboardBinding;
import com.mahar.busxhacktiv.model.BusInfo;
import com.mahar.busxhacktiv.model.Order;
import com.mahar.busxhacktiv.order.DetailOrderActivity;

import java.util.ArrayList;
import java.util.List;

public class TicketsFragment extends Fragment {
    RecyclerView rv;
    DatabaseReference reference;
    FirebaseAuth auth;
    FirebaseDatabase database;
    List<Order> list=new ArrayList<>();

    OrderAdapter orderAdapter;
    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        database= FirebaseDatabase.getInstance();
        reference= database.getReference();
        auth=FirebaseAuth.getInstance();
        rv=binding.rv;
        rv.setLayoutManager(new LinearLayoutManager(requireActivity()));
        list=new ArrayList<>();
        orderAdapter=new OrderAdapter(list,getContext());
        rv.setAdapter(orderAdapter);
        orderAdapter.setSetOnItemClickListener(new OrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Order order) {
                reference.child("Bus").child(order.getBusId()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            BusInfo bus=snapshot.getValue(BusInfo.class);
                            Intent i=new Intent(getContext(), DetailOrderActivity.class);
                            i.putExtra("bus",bus);
                            i.putExtra("order",order);
                            startActivity(i);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        reference.child("Orders").orderByChild("userId").equalTo(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if(snapshot.exists()){
                    for (DataSnapshot sp:snapshot.getChildren()){
                        Order order=sp.getValue(Order.class);
                        list.add(order);
                    }
                    orderAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}