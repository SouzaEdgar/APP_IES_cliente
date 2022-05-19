package com.example.cliente.model.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

    TextView vTOTAL;

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

        vTOTAL = view.findViewById(R.id.txtValorTotal);

        if(ItemFragment.itemADD.size() != 0) {
            recyclerView.setAdapter(new carrinhoAdapter(ItemFragment.itemADD));
            String s = String.format("%.2f", ItemFragment.somaTOTAL).replace(".", ",");
            vTOTAL.setText(s);

            view.findViewById(R.id.txtMsgBoloTriste).setVisibility(view.INVISIBLE);
            view.findViewById(R.id.imgBoloTriste).setVisibility(view.INVISIBLE);
            view.findViewById(R.id.txtMsgBoloFeliz).setVisibility(view.INVISIBLE);
            view.findViewById(R.id.imgBoloFeliz).setVisibility(view.INVISIBLE);
        } else {
            recyclerView.setVisibility(view.INVISIBLE);
            String s = String.format("%.2f", ItemFragment.somaTOTAL).replace(".", ",");
            vTOTAL.setText(s);
            view.findViewById(R.id.txtMsgBoloTriste).setVisibility(view.VISIBLE);
            view.findViewById(R.id.imgBoloTriste).setVisibility(view.VISIBLE);
            view.findViewById(R.id.txtMsgBoloFeliz).setVisibility(view.INVISIBLE);
            view.findViewById(R.id.imgBoloFeliz).setVisibility(view.INVISIBLE);
        }

        Button finalizar = view.findViewById(R.id.btnFinalizar);
        finalizar.setOnClickListener(v -> {
            if(ItemFragment.itemADD.size() != 0) {
                Toast.makeText(view.getContext(), "Pedido enviado", Toast.LENGTH_SHORT).show();
                /////// Limpar a lista //////////
                ItemFragment.itemADD = new ArrayList<>();

                /////// Zerar a somaTOTAL ///////
                Log.d("OIAaaa", "antes de zerar -> "+ItemFragment.somaTOTAL);
                ItemFragment.somaTOTAL = 00.00;
                String s = String.format("%.2f", ItemFragment.somaTOTAL).replace(".", ",");
                vTOTAL.setText(s);

                recyclerView.setAdapter(new carrinhoAdapter(ItemFragment.itemADD));
                view.findViewById(R.id.txtMsgBoloFeliz).setVisibility(view.VISIBLE);
                view.findViewById(R.id.imgBoloFeliz).setVisibility(view.VISIBLE);
                view.findViewById(R.id.txtMsgBoloTriste).setVisibility(view.INVISIBLE);
                view.findViewById(R.id.imgBoloTriste).setVisibility(view.INVISIBLE);
            } else {
                Toast.makeText(view.getContext(), "Sem itens no carrinho", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
