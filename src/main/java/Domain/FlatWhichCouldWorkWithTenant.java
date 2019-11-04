package Domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by user on 03.11.2019.
 */

@Entity
public class FlatWhichCouldWorkWithTenant {
    @Id
    @GeneratedValue
    private int id;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "flat")
    private Set<tenant> tenants=new HashSet<tenant>();

    public void addTenn(Set<tenant> tenants){
        this.tenants.addAll(tenants);
    }

    FlatWhichCouldWorkWithTenant(){}

    private String adress;

    public FlatWhichCouldWorkWithTenant(String adress){
        this.adress=adress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTenants(Set<tenant> tenants) {
        this.tenants = tenants;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Set<tenant> getTenants(){
        return tenants;
    }
}
