package com.futurmap.contactsqlite.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.futurmap.contactsqlite.R;
import com.futurmap.contactsqlite.model.UserContact;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    public List<UserContact> contactList;
    public Context context;
    public DetailsAdapterListener listener;
    boolean isEnable = false;


    public ContactAdapter(Context context, List<UserContact> contactList, DetailsAdapterListener listener) {
        this.contactList = contactList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_contact, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.lbl_name.setText(this.contactList.get(position).getName());
        holder.lbl_num.setText(this.contactList.get(position).getPhoneNumber());
        holder.item_check.setVisibility(isEnable ? View.VISIBLE : View.GONE);
        holder.btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.handleCall(view, holder.getAdapterPosition(), contactList.get(holder.getAdapterPosition()));
            }
        });

        holder.btn_mess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.handleMessage(view, holder.getAdapterPosition(), contactList.get(holder.getAdapterPosition()));
            }
        });
        holder.item.setOnClickListener(v -> listener.delete(v, holder.getAdapterPosition(), contactList.get(holder.getAdapterPosition())));
        holder.item_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    holder.item.setBackgroundResource(R.color.greyVeryLight);
                } else {
                    holder.item.setBackgroundResource(R.color.white);
                }
                listener.checkChanged(b, holder.getAdapterPosition(), contactList.get(holder.getAdapterPosition()));
            }
        });
        holder.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                isEnable = true;
                notifyDataSetChanged();
                Log.i("long", isEnable + "");
                return listener.longClick(view, holder.getAdapterPosition());
            }
        });

        holder.btn_more.setOnClickListener(v -> {
            Log.i("btn", "" + holder.btn_call.getVisibility());
//            holder.btn_call.setVisibility(holder.btn_call.get);
        });
    }

    @Override
    public int getItemCount() {
        return this.contactList.size();
    }

    public interface DetailsAdapterListener {
        void delete(View v, int position, UserContact userContact);

        void handleCall(View v, int position, UserContact userContact);

        void handleMessage(View v, int position, UserContact userContact);

        boolean longClick(View v, int position);

        void checkChanged(boolean b, int position, UserContact userContact);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView lbl_name;
        private final TextView lbl_num;
        private final ImageView btn_addImage;
        private final View item;
        private final CheckBox item_check;
        private final ImageButton btn_mess;
        private final ImageButton btn_call;
        private final ImageButton btn_more;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lbl_name = itemView.findViewById(R.id.lbl_item_name);
            lbl_num = itemView.findViewById(R.id.lbl_item_num);
            btn_addImage = itemView.findViewById(R.id.item_img);
            item_check = itemView.findViewById(R.id.item_check);
            btn_mess = itemView.findViewById(R.id.btn_mess);
            btn_call = itemView.findViewById(R.id.btn_call);
            btn_more = itemView.findViewById(R.id.btn_more);

            item = itemView;

        }
    }
}
