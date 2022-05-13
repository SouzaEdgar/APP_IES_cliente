package com.example.cliente.model;

public class Usuarios {
    /*
    Necessario puxar os dados dos usuarios no DB para deixar pronto caso necessario a verificação
     */

    public Boolean verificaUser(String email, String senha) {
        Boolean valido = false;
/*
    ( Processo de validação )
    >> Primeiro é verificado se o email existe no DB
        >> Se o email for verdadeiro, então é verificado a senha
            >> Caso a senha for VERDADEIRA então o Usuario é VALIDO
            >> Caso a senha for FALSA então o Usuario deve ser informado

 */
        if(email.equals("usuario@cliente.com") || email.equals("usuario")) {
            if( senha.equals("123456") || senha.equals("123")) valido = true;
        } else valido = false;

        return valido;
    }
}
