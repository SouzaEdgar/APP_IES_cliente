package com.example.cliente.viewModel.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cliente.R;
import com.example.cliente.model.Usuarios;

public class CarrinhoVazioFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public CarrinhoVazioFragment() {
        // Required empty public constructor
    }

    public static CarrinhoVazioFragment newInstance(String param1, String param2) {
        CarrinhoVazioFragment fragment = new CarrinhoVazioFragment();
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

            Usuarios a = new Usuarios();
            String c="1",d="1",e="1",f="1",g="1",h="1",i="1",j="1",k="1",l="1";
            Usuarios b = new Usuarios(c,d,e,f,g,h,i);

            a.getCpf();a.getBairro(); a.getEmail(); a.getNome(); a.getNumero(); a.getRua(); a.getValorTotal();
            a.setBairro(c); a.setCpf(c); a.setEmail(c); a.setNome(c);a.setNumero(c);a.setRua(c);a.setValorTotal(c);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_carrinho_vazio, container, false);
    }
}
