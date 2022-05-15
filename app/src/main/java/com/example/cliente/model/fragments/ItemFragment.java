package com.example.cliente.model.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cliente.R;

public class ItemFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    String nome, sabor, valor, imagem;

    public ItemFragment() {
    }

    public ItemFragment(String nome, String sabor, String valor, String imagem) {
        this.nome = nome;
        this.sabor = sabor;
        this.valor = valor;
        this.imagem = imagem;
    }

    public static ItemFragment newInstance(String param1, String param2) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_item, container, false);

        ImageView imgHolder = view.findViewById(R.id.imgItem);
        TextView nomeHolder = view.findViewById(R.id.txtNome_ProdItem);
        TextView saborHolder = view.findViewById(R.id.txtSabor_ProdItem);
        TextView valorHolder = view.findViewById(R.id.txtValor_ProdItem);

        nomeHolder.setText(nome);
        saborHolder.setText(sabor);
        valorHolder.setText(valor);
        Glide.with(getContext()).load(imagem).into(imgHolder);

        return view;
    }

    public void onBackPressed() {
        AppCompatActivity activity = (AppCompatActivity) getContext();
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(
                        R.id.frame_layout,
                        new ProdutosFragment()
                ).addToBackStack(null).commit();
    }
}
