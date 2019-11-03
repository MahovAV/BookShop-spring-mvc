package DAO;

import Domain.Flat;
import Domain.tenant;
import java.util.Set;

/**
 * Created by user on 03.11.2019.
 */
public interface Many {
    void addFlat(Flat flat, Set<tenant> tenants);
    void populate(Flat flat,tenant tenant);
}
