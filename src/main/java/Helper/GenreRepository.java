package Helper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import Model.Domain.enumOfGenres;

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
