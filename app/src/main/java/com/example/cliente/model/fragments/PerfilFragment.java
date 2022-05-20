package com.example.cliente.model.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.cliente.R;
import com.example.cliente.model.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilFragment extends Fragment {

    private TextView txtNome, txtEmail, txtCPF, txtNumero, txtRua, txtBairro;
    FirebaseFirestore userDB = FirebaseFirestore.getInstance();
    String userID;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PerfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilFragment newInstance(String param1, String param2) {
        PerfilFragment fragment = new PerfilFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button button = (Button) view.findViewById(R.id.btnDeslogar);
        button.setOnClickListener(view1 -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(view1.getContext(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                AppCompatActivity activity = (AppCompatActivity) getContext();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(
                                R.id.frame_layout,
                                new ProdutosFragment()
                        ).addToBackStack(null).commit();
                BottomNavigationView navSecoes = activity.findViewById(R.id.navSecoes);
                navSecoes.getMenu().findItem(R.id.item_produto).setChecked(true);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        txtNome = view.findViewById(R.id.txtNome_rg);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtCPF = view.findViewById(R.id.txtCPF);
        txtNumero = view.findViewById(R.id.txtNumero);
        txtBairro = view.findViewById(R.id.txtBairro);
        txtRua = view.findViewById(R.id.txtRua);

        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

            DocumentReference docReference = userDB.collection("Usuarios").document(userID);
            docReference.addSnapshotListener((documentSnapshot, error) -> {
                if(documentSnapshot != null) {
                    txtNome.setText(documentSnapshot.getString("nome"));
                    txtEmail.setText(email);
                    txtCPF.setText(documentSnapshot.getString("cpf"));
                    txtNumero.setText(documentSnapshot.getString("numero"));
                    txtBairro.setText(documentSnapshot.getString("bairro"));
                    txtRua.setText(documentSnapshot.getString("rua"));
                }
            });
        }

        return view;
    }
}
