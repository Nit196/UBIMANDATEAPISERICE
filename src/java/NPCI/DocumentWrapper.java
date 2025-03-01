/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NPCI;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlType;
import org.w3c.dom.Document;

/**
 *
 * @author Nitinv
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentWrapper")
public class DocumentWrapper implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlAnyElement
    private Document document;

    public DocumentWrapper() {
    }

    public DocumentWrapper(Document document) {
        this.document = document;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}


