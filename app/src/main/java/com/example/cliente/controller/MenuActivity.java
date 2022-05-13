package com.example.cliente.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.TextView;

import com.example.cliente.R;
import com.example.cliente.controller.fragments.CarrinhoFragment;
import com.example.cliente.controller.fragments.PerfilFragment;
import com.example.cliente.controller.fragments.ProdutosFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

public class MenuActivity extends AppCompatActivity {

    private TextView txtNome, txtEmail;

    FirebaseFirestore userDB = FirebaseFirestore.getInstance();
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mudarFragment(new ProdutosFragment());

        txtNome = findViewById(R.id.txtNome);
        txtEmail = findViewById(R.id.txtEmail);

        BottomNavigationView navSecoes = findViewById(R.id.navSecoes);


        navSecoes.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.item_produto:
                    mudarFragment(new ProdutosFragment());
                    break;
                case R.id.item_carrinho:
                    mudarFragment(new CarrinhoFragment());
                    break;
                case R.id.item_perfil:
                    mudarFragment(new PerfilFragment());
                    /*Button btnDeslogar = btnDeslogar.findViewById();
                    btnDeslogar.setOnClickListener((View view) -> {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    });*/
                    break;
            }
            return true;
        });


    }

    private void mudarFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

       // mudarFragment(new ProdutosFragment());

// Para funcionar a tela em questão precisa compor de txtNome e txtEmail, do contrario sempre havera erros
// Passar como um metodo para a Classe Usuario
        /*String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference docReference = userDB.collection("Usuarios").document(userID);
        docReference.addSnapshotListener((documentSnapshot, error) -> {
            if(documentSnapshot != null) {
                txtNome.setText(documentSnapshot.getString("nome"));
                txtEmail.setText(email);
            }
        });*/
// Fim do metodo
    }
}
