package com.example.cliente.model.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cliente.R;
import com.example.cliente.controller.carrinhoAdapter;
import com.example.cliente.model.carrinhoModel;

import java.util.ArrayList;

public class CarrinhoFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    ArrayList<carrinhoModel> carrinhoHolder;

    public CarrinhoFragment() {
        // Required empty public constructor
    }

    public static CarrinhoFragment newInstance(String param1, String param2) {
        CarrinhoFragment fragment = new CarrinhoFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_carrinho, container, false);

        recyclerView = view.findViewById(R.id.rvCarrinho);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        carrinhoHolder = new ArrayList<>();
/* IMAGEM DO BOLO
https://firebasestorage.googleapis.com/v0/b/ies-cliente.appspot.com/o/bolo.jpg?alt=media&token=1e12974c-18aa-45fd-ab7e-ae75216fb1a4
 */
        String imagemLink = "https://firebasestorage.googleapis.com/v0/b/ies-cliente.appspot.com/o/bolo.jpg?alt=media&token=1e12974c-18aa-45fd-ab7e-ae75216fb1a4";

        carrinhoModel ob1 = new carrinhoModel(imagemLink, "Bolo Murango", "Murango", "minha paz");
        carrinhoHolder.add(ob1);

        carrinhoModel ob2 = new carrinhoModel(imagemLink, "Bolo Coco", "Coco", "meu português");
        carrinhoHolder.add(ob2);

        carrinhoModel ob3 = new carrinhoModel(imagemLink, "Bolo Laranja", "Laranja", "minha fome");
        carrinhoHolder.add(ob3);

        carrinhoModel ob4 = new carrinhoModel(imagemLink, "Bolo Limão", "Limão", "minha alma");
        carrinhoHolder.add(ob4);

        carrinhoModel ob5 = new carrinhoModel(imagemLink, "Bolo Chocolate", "Chocolate", "chocolate :D");
        carrinhoHolder.add(ob5);

        recyclerView.setAdapter(new carrinhoAdapter(carrinhoHolder));

        return view;
    }
}