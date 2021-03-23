/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.agendaclient.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author viter
 */
@XmlRootElement
@XmlSeeAlso(Entrada.class)
public class Entradas extends ArrayList<Entrada> {

    public Entradas() {
        super();
    }

    public Entradas(Collection<? extends Entrada> c) {
        super(c);
    }

    @XmlElement(name = "entrada")
    public List<Entrada> getEntradas() {
        return this;
    }

    public void setEntradas(List<Entrada> entradas) {
        this.addAll(entradas);
    }
}
