package com.example.cliente.model.fragments;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
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
import com.example.cliente.controller.Usuarios;
import com.example.cliente.controller.carrinhoAdapter;
import com.example.cliente.model.carrinhoModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.santalu.maskara.widget.MaskEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CarrinhoFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    ArrayList<carrinhoModel> carrinhoHolder;

    TextView vTOTAL;
    MaskEditText dtaEntrega;

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

        // Aqui é resolvido o problema de apos finalizar a compra e apertar em voltar os produtos
        //  serem exibidos em cima do carrinho
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                AppCompatActivity activity = (AppCompatActivity) getContext();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.frame_layout, new ProdutosFragment());
                fragmentTransaction.commit();
                BottomNavigationView navSecoes = activity.findViewById(R.id.navSecoes);
                navSecoes.getMenu().findItem(R.id.item_produto).setChecked(true);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    FirebaseFirestore userDB = FirebaseFirestore.getInstance();
    String userID;
    String valorTotal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_carrinho, container, false);

        recyclerView = view.findViewById(R.id.rvCarrinho);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        carrinhoHolder = new ArrayList<>();

        vTOTAL = view.findViewById(R.id.txtValorTotal);
        dtaEntrega = view.findViewById(R.id.edtDataEntrega);

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
                dtaEntrega.setText("");
                if(dtaEntrega.isDone()) { // Verifica de a Data de Entrega esta setada
                    Log.d("Data", "Dia -> " + dtaEntrega.getMasked().substring(0,2)); // Recolhe o dia
                    Log.d("Data", "Mês -> " + dtaEntrega.getMasked().substring(3,5)); // Recolhe o mês

                    if(Integer.parseInt(dtaEntrega.getMasked().substring(0,2)) > 31 || Integer.parseInt(dtaEntrega.getMasked().substring(0,2)) < 1) {
                        Toast.makeText(view.getContext(), "Dia inválido", Toast.LENGTH_SHORT).show();

                    }
                    else if(Integer.parseInt(dtaEntrega.getMasked().substring(3,5)) > 12 || Integer.parseInt(dtaEntrega.getMasked().substring(3,5)) < 5 ) {

                        Toast.makeText(view.getContext(), "Meses válidos 5~12", Toast.LENGTH_SHORT).show();

                    } else {
/////////////////// Caso tudo esteja correto ////////////////////////////////////////
                        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("pedidos").push();
                        Map<String, Object> infos = new HashMap<>();

                        CollectionReference docReference = userDB.collection("Usuarios");//.document(userID);
                        docReference
                                .get()
                                .addOnCompleteListener((OnCompleteListener<QuerySnapshot>) task -> {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            //Log.d("OIAEITA", document.getId() + " => " + document.getData());
                                            if(document.getId().equals(userID)) {
                                                infos.put("nome", document.get("nome"));
                                                infos.put("email", document.get("email"));
                                                infos.put("cpf", document.get("cpf"));
                                                infos.put("numero", document.get("numero"));
                                                infos.put("bairro", document.get("bairro"));
                                                infos.put("rua", document.get("rua"));
                                                infos.put("valorTotal", valorTotal = String.format("%.2f", ItemFragment.somaTOTAL).replace(".", ","));
                                                reference.setValue(infos);

                                                Map<String, Object> pedidosCar = new HashMap<>();
                                                for(int i=0; i < ItemFragment.itemADD.size(); i++) {
                                                    pedidosCar.put("nome", ItemFragment.itemADD.get(i).getNome());
                                                    pedidosCar.put("sabor", ItemFragment.itemADD.get(i).getSabor());
                                                    pedidosCar.put("valor", ItemFragment.itemADD.get(i).getValor());
                                                    pedidosCar.put("imagem", ItemFragment.itemADD.get(i).getImagem());
                                                    reference.child("listaPedidos").push().setValue(pedidosCar);
                                                }

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
                                                break;
                                            }
                                        }
                                    } else {
                                        Log.d("OIAEITA", "Error getting documents: ", task.getException());
                                    }
                                });
                    }
                } else {
                    Toast.makeText(view.getContext(), "Preencha a data de entrega", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(view.getContext(), "Sem itens no carrinho", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
