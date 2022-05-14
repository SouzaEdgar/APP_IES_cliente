package com.example.cliente.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cliente.R;

import java.util.ArrayList;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.MyViewHolder> {

    Context context;
    ArrayList<Produtos> lista;

    public ProdutoAdapter(Context context, ArrayList<Produtos> lista) {
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.itens_produto, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Produtos produto = lista.get(position);
        holder.nome.setText(produto.getNome());
        holder.sabor.setText(produto.getSabor());
        holder.valor.setText(produto.getValor());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nome, sabor, valor;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.txtNome_itens);
            sabor = itemView.findViewById(R.id.txtSabor_itens);
            valor = itemView.findViewById(R.id.txtValor_itens);
        }
    }
}