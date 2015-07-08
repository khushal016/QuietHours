package com.hidtechs.quiethours;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by napster on 6/7/15.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>  {
    private LayoutInflater inflater;
    List<Information> data=new ArrayList<>();

    public MyAdapter(Context context,List<Information> data)
    {
         inflater = LayoutInflater.from(context);
         this.data=data;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.contacts_row,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }
//   public void add(Information item, int position) {
//        data.add(position, item);
//       notifyItemInserted(position);
//   }

    public void delete(int position)
    {
        data.remove(position);
        notifyItemRemoved(position);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Information current = data.get(position);
        holder.itemView.setTag(data.get(position));
        holder.name.setText(current.name);
        holder.minus.setImageResource(current.iconId);
        holder.number.setText(current.number);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView name;
        TextView number;
        ImageView minus;
        public MyViewHolder(View itemView) {
            super(itemView);
            name= (TextView) itemView.findViewById(R.id.contact_name);
            number= (TextView) itemView.findViewById(R.id.contact_no);
            minus= (ImageView) itemView.findViewById(R.id.minus_icon);
            minus.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            delete(getAdapterPosition());
        }
    }
}
