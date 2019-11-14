package Helper;

import Model.Domain.Author;
import Model.Domain.enumOfGenres;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by user on 14.11.2019.
 */
public class AuthorRepresentation {
    public Set<Author> getAuthorsFromString(String Authors){
        Set<Author> authors=new HashSet<Author>();
        Set<String> NamesOfAuthors=new HashSet<String>(Arrays.asList(Authors.split(",")));
        if(Authors!=null){
            for(String s:NamesOfAuthors){
                if(s!="")
                authors.add(new Author(s));
            }
        }
        return authors;
    }
    public String getString(Set<Author> Authors){
        String result=new String();
        for(Author s:Authors){
            result+=s.getName()+",";
        }
        return (result.length()>0)?result.substring(0,result.length()-1):"";
    }
}
