package com.ohgiraffers.section02.annotation.collection;

import com.ohgiraffers.section02.common.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("pokemonServiceCollection")
public class PokemonService {

    private List<Pokemon> pokemonList;

    @Autowired
    public PokemonService(List<Pokemon> pokemonList) {
        this.pokemonList = pokemonList;
    }

    public void pokemonAttack(){
        pokemonList.forEach(Pokemon::attack);
        /*
        * for(Pokemon pokemon : pokemonList) {
        *   pokemon.attack();
        * }
        * */

    }




}
