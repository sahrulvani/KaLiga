package com.smpn53sby.kaliga;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

   private Context context;
   private ArrayList<DataModel> list;
   private ArrayList<DataModel> ListData;
   private DatabaseReference databaseReference;
   //private AdapterView.OnItemClickListener itemClickListener;

    /*public interface dataListener{
        void onDelete(DataModel data, int position);
    }

    dataListener listener;*/

    public RecyclerViewAdapter(Context context, ArrayList<DataModel> upload){
        this.list = upload;
        this.ListData = upload;
        this.context = context;
        //listener = (MainActivity) context;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_items, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {

        databaseReference = FirebaseDatabase.getInstance().getReference();
        Glide.with(context).load(ListData.get(position).getImageURL()).into(viewHolder.imageView);
        viewHolder.tvjudul.setText(ListData.get(position).getJudul());
        viewHolder.tvpengarang.setText(ListData.get(position).getPengarang());
        viewHolder.tvklasifikasi.setText(ListData.get(position).getKlasifikasi());


        /*viewHolder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final String[] action = {"Detail", "Delete"};
                AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                alert.setItems(action, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.create();
                alert.show();
                return true;
            }
        });*/
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                databaseReference.child("Data_KaLiga").child(ListData.get(position).getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Bundle bundle = new Bundle();
                        bundle.putString("dataJudul", ListData.get(position).getJudul());
                        bundle.putString("dataPengarang", ListData.get(position).getPengarang());
                        bundle.putString("getPK", ListData.get(position).getKey());
                        bundle.putString("dataImage", ListData.get(position).getImageURL());
                        bundle.putString("dataDesk", ListData.get(position).getDeskripsi());
                        bundle.putString("dataKlasifikasi", ListData.get(position).getKlasifikasi());
                        bundle.putString("dataTahun", ListData.get(position).getTahun());
                        bundle.putString("dataISBN", ListData.get(position).getIsbn());
                        Intent intent = new Intent(v.getContext(), DetailActivity.class);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        viewHolder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                final String[] action = {"Delete"};
                AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                alert.setItems(action, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, final int i) {
                        databaseReference.child("Data_KaLiga").child(ListData.get(position).getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()){
                                    itemSnapshot.getRef().removeValue();
                                }
                                /*switch (i){
                                    case 0:
                                        Bundle bundle = new Bundle();
                                        bundle.putString("dataJudul", ListData.get(position).getJudul());
                                        bundle.putString("dataPengarang", ListData.get(position).getPengarang());
                                        bundle.putString("getPK", ListData.get(position).getKey());
                                        bundle.putString("dataImage", ListData.get(position).getImageURL());
                                        bundle.putString("dataDesk", ListData.get(position).getDeskripsi());
                                        bundle.putString("dataKlasifikasi", ListData.get(position).getKlasifikasi());
                                        bundle.putString("dataTahun", ListData.get(position).getTahun());
                                        Intent intent = new Intent(v.getContext(), DetailActivity.class);
                                        intent.putExtras(bundle);
                                        context.startActivity(intent);
                                        break;

                                    case 1:
                                        for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()){
                                            itemSnapshot.getRef().removeValue();
                                        }
                                        break;
                                }*/
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                });
                alert.create();
                alert.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return ListData.size();
    }

    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty())
                {
                    ListData = list;
                }else{
                    ArrayList<DataModel> filterList = new ArrayList<>();
                    for (DataModel dataModel : list){
                        if (dataModel.getJudul().toLowerCase().contains(charString.toLowerCase()))
                        {
                            filterList.add(dataModel);
                        }
                    }
                    ListData = filterList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = ListData;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                ListData = (ArrayList<DataModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView tvjudul,tvpengarang,tvklasifikasi;
        private LinearLayout linearLayout;

        public ViewHolder(View v){
            super(v);
            imageView = v.findViewById(R.id.imageview);
            tvjudul = v.findViewById(R.id.tv_judul);
            tvpengarang = v.findViewById(R.id.tv_pengarang);
            tvklasifikasi = v.findViewById(R.id.tv_klasifikasi);
            linearLayout = v.findViewById(R.id.linearlayout);
        }

    }

}
