package com.example.cliente.model;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.cliente.R;
import com.example.cliente.model.fragments.CarrinhoFragment;
import com.example.cliente.model.fragments.PerfilFragment;
import com.example.cliente.model.fragments.ProdutosFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        mudarFragment(new ProdutosFragment());

         BottomNavigationView navSecoes = findViewById(R.id.navSecoes);
         navSecoes.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.item_produto:
                    mudarFragment(new ProdutosFragment());
                    break;
                case R.id.item_carrinho:
                    mudarFragment(new CarrinhoFragment());
                    /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new CarrinhoFragment()).commit();*/
                    break;
                case R.id.item_perfil:
                    mudarFragment(new PerfilFragment());
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

}
