package Helper;

import Model.Domain.Author;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.*;

/**
 * Created by user on 14.11.2019.
 */
public class AuthorRepresentation {
    public Set<Author> getAuthorsFromString(String authors){
        Set<Author> resultAuthorSet =new HashSet<Author>();
        Set<String> namesOfAuthors=new HashSet<String>(Arrays.asList(authors.split(",")));
        if(authors!=null){
            for(String s:namesOfAuthors){
                if(s!="")
                resultAuthorSet.add(new Author(s));
            }
        }
        return resultAuthorSet;
    }
    public String getString(Set<Author> Authors){
        String result=new String();
        for(Author s:Authors){
            result+=s.getName()+",";
        }
        return (result.length()>0)?result.substring(0,result.length()-1):"";
    }
    public List<String> getListOfStrings(Set<Author> Authors){
        List<String> namesOfAutors=new ArrayList<String>();
        for(Author author:Authors){
            namesOfAutors.add(author.getName());
        }
        return namesOfAutors;
    }

    public void SplitAuthors(List<String> namesOfAutors,List<Author> WhoInDataBase,List<Author> WhoIsNotInDataBase,
                             Set<Author> authorsFromBook,Session session){
        for(int i=0;i<namesOfAutors.size();++i){
            Author author=getByName(session,namesOfAutors.get(i));
            if(author!=null){
                //REPLACE OUR EMPTY(ONLY STRING)  REAL AUTHOR FROM DATA BASE
                boolean itWas=false;
                //check whether it was or not
                for(Author a:WhoInDataBase){
                    if(a.getName().equals(author.getName())){
                        //already here shouldn't add
                        itWas=true;
                    }
                }
                if(!itWas){
                    WhoInDataBase.add(author);
                }else {
                    //should throw exception as we print author 2x times
                }
            }else{
                Author auth=null;
                for(Author a:authorsFromBook){
                    if(a.getName().equals(namesOfAutors.get(i))){
                        auth=a;
                    }
                }
                WhoIsNotInDataBase.add(auth);
            }
        }
    }
    public static Author getByName(Session session,String name) {
        Query query = session.createQuery("FROM Author A WHERE name = :paramName");
        query.setParameter("paramName", name);
        return (Author) query.uniqueResult();
    }
}
