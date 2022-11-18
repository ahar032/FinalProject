package com.mahar.busxhacktiv.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mahar.busxhacktiv.R;
import com.mahar.busxhacktiv.model.Provinsi;

import java.util.List;

public class ProvinsiAdapter extends RecyclerView.Adapter<ProvinsiAdapter.ProvinsiHolder> {
    List<Provinsi> provinsiList;
    private OnItemClickListener listener;

    FirebaseDatabase database;
    DatabaseReference reference;
    public ProvinsiAdapter(List<Provinsi> provinsiList) {
        this.provinsiList = provinsiList;

        database=FirebaseDatabase.getInstance();
        reference=database.getReference();
    }

    @NonNull
    @Override
    public ProvinsiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_provinsi,parent,false);
        return new ProvinsiHolder(view);
//        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ProvinsiHolder holder, int position) {
        ProvinsiHolder provinsiHolder=(ProvinsiHolder) holder;
        Provinsi provinsi= provinsiList.get(position);
    holder.tv.setText(provinsi.getName());
    }

    @Override
    public int getItemCount() {
        return provinsiList.size();
    }

    public class ProvinsiHolder extends RecyclerView.ViewHolder{
        TextView tv;

        public ProvinsiHolder(@NonNull View itemView) {
            super(itemView);
            tv=itemView.findViewById(R.id.tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();

                    if(listener!=null && position!=RecyclerView.NO_POSITION){
                        listener.onItemClick(provinsiList.get(position));
                    }
                }
            });
        }
    }
    public interface OnItemClickListener{
        void onItemClick(Provinsi provinsi);
    }
    public void setOnClickListener(ProvinsiAdapter.OnItemClickListener listener){
        this.listener=listener;
    }

}
