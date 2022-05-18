package com.example.cliente.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cliente.R;
import com.example.cliente.model.carrinhoModel;

import java.util.ArrayList;

public class carrinhoAdapter extends RecyclerView.Adapter<carrinhoAdapter.myviewHolder>{

    ArrayList<carrinhoModel> carrinhoHolder;

    public carrinhoAdapter(ArrayList<carrinhoModel> carrinhoHolder) {
        this.carrinhoHolder = carrinhoHolder;
    }

    @NonNull
    @Override
    public myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itens_carrinho, parent, false);

        return new myviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewHolder holder, int position) {
        //holder.img.setImageResource(carrinhoHolder.get(position).getImagem());
        Glide.with(
                holder.img.getContext()
        ).load(
                carrinhoHolder.get(position).getImagem()
        ).into(holder.img);
        holder.nome.setText(carrinhoHolder.get(position).getNome());
        holder.sabor.setText(carrinhoHolder.get(position).getSabor());
        holder.valor.setText(carrinhoHolder.get(position).getValor());

    }

    @Override
    public int getItemCount() {
        return carrinhoHolder.size();
    }

    class myviewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView nome, sabor, valor;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgItensCar);
            nome = itemView.findViewById(R.id.txtNomeCar);
            sabor = itemView.findViewById(R.id.txtSaborCar);
            valor = itemView.findViewById(R.id.txtValorCar);
        }
    }
}
