package com.mahar.busxhacktiv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mahar.busxhacktiv.R;
import com.mahar.busxhacktiv.model.BusInfo;
import com.mahar.busxhacktiv.model.Order;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder> {
    List<Order> orderList;

    Context mContext;
    private OnItemClickListener setOnItemClickListener;
    FirebaseDatabase database;
    DatabaseReference reference;
    public OrderAdapter(List<Order> orderList, Context mContext) {
        this.orderList = orderList;
        this.mContext = mContext;
        database=FirebaseDatabase.getInstance();
        reference=database.getReference();
    }

    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_ticket,parent,false);
        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder holder, int position) {
        Order order=orderList.get(position);

        holder.orderId.setText("Book No "+order.getOrderId());
        holder.detailstatus.setText(order.getStatus());
        if(order.getStatus().equals("Paid")){
            Picasso.get().load(R.drawable.paid).into(holder.picturestatus);
        }

        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String finalAmount = formatter.format(Integer.parseInt(order.getAmount()));
        holder.amount.setText(finalAmount);
        Date tgl = new Date(order.getDate());
        SimpleDateFormat sdf=new SimpleDateFormat("EEE, d MMM yyyy",Locale.ENGLISH);
        String tanggal=sdf.format(tgl);
        holder.date.setText(tanggal);
        reference.child("Bus").child(order.getBusId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    BusInfo bus=snapshot.getValue(BusInfo.class);
                    holder.departure.setText(bus.getDeparture());
                    holder.arrival.setText(bus.getArrival());
                    holder.tDeparture.setText(bus.getTimeDeparture());
                    holder.tArrival.setText(bus.getTimeArrival());
                    holder.busName.setText(bus.getBusName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class OrderHolder extends RecyclerView.ViewHolder{
        TextView busName,tArrival,tDeparture,arrival,departure,orderId,date,amount,detailstatus;
        ImageView picturestatus;
        public OrderHolder(@NonNull View itemView) {
            super(itemView);
            busName=itemView.findViewById(R.id.busName);
            tArrival=itemView.findViewById(R.id.time_arrival);
            arrival=itemView.findViewById(R.id.arrival);
            tDeparture=itemView.findViewById(R.id.time_departure);
            departure=itemView.findViewById(R.id.departure);
            orderId=itemView.findViewById(R.id.orderId);
            date=itemView.findViewById(R.id.dateOrder);
            amount=itemView.findViewById(R.id.amount);
            detailstatus=itemView.findViewById(R.id.detailstatus);
            picturestatus=itemView.findViewById(R.id.statuspic);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();

                    if(setOnItemClickListener!=null &&  position!=RecyclerView.NO_POSITION){
                        setOnItemClickListener.onItemClick(orderList.get(position));
                    }

                }
            });

        }
    }
    public interface OnItemClickListener{
        void onItemClick(Order order);
    }
    public void setSetOnItemClickListener(OrderAdapter.OnItemClickListener setOnItemClickListener){
        this.setOnItemClickListener=setOnItemClickListener;
    }
}
