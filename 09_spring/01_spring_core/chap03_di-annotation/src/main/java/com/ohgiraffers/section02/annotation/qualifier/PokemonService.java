package com.ohgiraffers.section02.annotation.qualifier;

import com.ohgiraffers.section02.common.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("pokemonServiceQualifier")
public class PokemonService {

    //필드 주입

    @Autowired
    @Qualifier("pikachu") // Bean 이름 중에 pikachu인 Bean을 넣어줘
    private Pokemon pokemon;

    public void pokemonAttack() {
        pokemon.attack();
    }






}
