package ru.cource.helper;

import java.util.HashSet;
import java.util.Set;

import ru.cource.model.domain.enumOfGenres;

/**
 * Created by user on 13.11.2019.
 */
public class GenreRepository {

    public static Set<String> getAllGenres() {
        Set<String> Genres=new HashSet<String>();
        for(enumOfGenres e:enumOfGenres.values()){
            Genres.add(e.toString());
        }
        return Genres;
    }

    public static Set<String> toSetOfString(Set<enumOfGenres> genres) {
        Set<String> Genres=new HashSet<String>();
        for(enumOfGenres e:genres){
            Genres.add(e.toString());
        }
        return Genres;
    }
}
