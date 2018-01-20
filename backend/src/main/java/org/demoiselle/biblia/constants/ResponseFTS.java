/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.demoiselle.biblia.constants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * @author paulo
 */
public class ResponseFTS implements Serializable {

    private static final long serialVersionUID = -1769589533175831560L;

    private List<ItemFTS> itens = new ArrayList<>();
    private Map<String, String> persons = new HashMap<>();
    private Map<String, String> categories = new HashMap<>();

    public List<ItemFTS> getItens() {
        return itens;
    }

    public void setItens(List<ItemFTS> itens) {
        this.itens = itens;
    }

    public Map<String, String> getPersons() {
        return persons;
    }

    public void setPersons(Map<String, String> persons) {
        this.persons = persons;
    }

    public Map<String, String> getCategories() {
        return categories;
    }

    public void setCategories(Map<String, String> categories) {
        this.categories = categories;
    }

    final Logger LOG = Logger.getLogger(ResponseFTS.class.getName());

}
