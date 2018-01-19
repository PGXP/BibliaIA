/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.demoiselle.biblia.constants;

import java.io.Serializable;
import java.util.logging.Logger;
import org.demoiselle.biblia.entity.Versiculo;

/**
 *
 * @author paulo
 */
public class ItemFTS implements Serializable {

    private static final long serialVersionUID = -1769589533175831560L;

    private Versiculo versiculo;
    private float ocorrencias;

    public Versiculo getVersiculo() {
        return versiculo;
    }

    public void setVersiculo(Versiculo versiculo) {
        this.versiculo = versiculo;
    }

    public float getOcorrencias() {
        return ocorrencias;
    }

    public void setOcorrencias(float ocorrencias) {
        this.ocorrencias = ocorrencias;
    }

    private static final Logger LOG = Logger.getLogger(ItemFTS.class.getName());

}
